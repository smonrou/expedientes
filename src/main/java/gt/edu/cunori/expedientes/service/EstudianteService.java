package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.estudiante.*;
import gt.edu.cunori.expedientes.api.mapper.EstudianteMapper;
import gt.edu.cunori.expedientes.domain.entity.*;
import gt.edu.cunori.expedientes.domain.enums.Rol;
import gt.edu.cunori.expedientes.domain.repository.*;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión completa del expediente estudiantil.
 * Maneja creación, actualización, búsquedas y gestión de sub-entidades
 * (teléfonos, condiciones médicas, alergias, discapacidades, contactos de
 * emergencia).
 */
@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final TipoSangreRepository tipoSangreRepository;
    private final AlergiaRepository alergiaRepository;
    private final TipoDiscapacidadRepository tipoDiscapacidadRepository;
    private final TelefonoEstudianteRepository telefonoRepository;
    private final CondicionMedicaRepository condicionMedicaRepository;
    private final EstudianteAlergiaRepository estudianteAlergiaRepository;
    private final EstudianteDiscapacidadRepository estudianteDiscapacidadRepository;
    private final ContactoEmergenciaRepository contactoRepository;
    private final EstudianteMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public EstudianteService(
            EstudianteRepository estudianteRepository,
            UsuarioRepository usuarioRepository,
            CarreraRepository carreraRepository,
            TipoSangreRepository tipoSangreRepository,
            AlergiaRepository alergiaRepository,
            TipoDiscapacidadRepository tipoDiscapacidadRepository,
            TelefonoEstudianteRepository telefonoRepository,
            CondicionMedicaRepository condicionMedicaRepository,
            EstudianteAlergiaRepository estudianteAlergiaRepository,
            EstudianteDiscapacidadRepository estudianteDiscapacidadRepository,
            ContactoEmergenciaRepository contactoRepository,
            EstudianteMapper mapper,
            PasswordEncoder passwordEncoder) {
        this.estudianteRepository = estudianteRepository;
        this.usuarioRepository = usuarioRepository;
        this.carreraRepository = carreraRepository;
        this.tipoSangreRepository = tipoSangreRepository;
        this.alergiaRepository = alergiaRepository;
        this.tipoDiscapacidadRepository = tipoDiscapacidadRepository;
        this.telefonoRepository = telefonoRepository;
        this.condicionMedicaRepository = condicionMedicaRepository;
        this.estudianteAlergiaRepository = estudianteAlergiaRepository;
        this.estudianteDiscapacidadRepository = estudianteDiscapacidadRepository;
        this.contactoRepository = contactoRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retorna todos los estudiantes en formato resumido para listados.
     *
     * @return lista de estudiantes como DTOs de resumen
     */
    @Transactional(readOnly = true)
    public List<EstudianteResumenResponse> listarTodos() {
        return estudianteRepository.findAll()
                .stream()
                .map(mapper::toResumen)
                .toList();
    }

    /**
     * Busca estudiantes por término (nombre, apellido, carné) con filtro opcional
     * de carrera.
     *
     * @param termino   texto a buscar
     * @param carreraId id de carrera para filtrar (opcional, puede ser null)
     * @return lista de estudiantes que coinciden con el criterio
     */
    @Transactional(readOnly = true)
    public List<EstudianteResumenResponse> buscar(String termino, Long carreraId) {
        List<Estudiante> resultados;
        if (carreraId != null) {
            resultados = estudianteRepository.buscarPorTerminoYCarrera(termino, carreraId);
        } else {
            resultados = estudianteRepository.buscarPorTermino(termino);
        }
        return resultados.stream().map(mapper::toResumen).toList();
    }

    /**
     * Retorna el expediente completo de un estudiante por su id.
     *
     * @param id identificador del estudiante
     * @return el expediente completo como DTO de respuesta
     * @throws ResourceNotFoundException si no existe un estudiante con ese id
     */
    @Transactional(readOnly = true)
    public EstudianteResponse buscarPorId(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));
        return mapper.toResponse(estudiante);
    }

    /**
     * Retorna el expediente completo de un estudiante por el id de su usuario.
     * Usado por el propio estudiante autenticado para consultar su expediente.
     *
     * @param usuarioId identificador del usuario asociado
     * @return el expediente completo como DTO de respuesta
     * @throws ResourceNotFoundException si no existe expediente para ese usuario
     */
    @Transactional(readOnly = true)
    public EstudianteResponse buscarPorUsuarioId(Long usuarioId) {
        Estudiante estudiante = estudianteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "usuarioId", usuarioId));
        return mapper.toResponse(estudiante);
    }

    /**
     * Crea un nuevo expediente estudiantil junto con su usuario de acceso.
     * Valida unicidad de carné, CUI y correo institucional.
     * Crea el usuario con rol ESTUDIANTE y lo asocia al expediente.
     *
     * @param request datos del nuevo estudiante
     * @return el expediente creado como DTO de respuesta completo
     * @throws BusinessException si el carné, CUI, correo o nombre de usuario ya
     *                           están en uso
     */
    @Transactional
    public EstudianteResponse crear(EstudianteCreateRequest request) {
        // Validaciones de unicidad
        if (estudianteRepository.existsByNumeroCarne(request.getNumeroCarne())) {
            throw new BusinessException("Ya existe un estudiante con el carné: " + request.getNumeroCarne());
        }
        if (estudianteRepository.existsByCui(request.getCui())) {
            throw new BusinessException("Ya existe un estudiante con el CUI: " + request.getCui());
        }
        if (estudianteRepository.existsByCorreoInstitucional(request.getCorreoInstitucional())) {
            throw new BusinessException(
                    "Ya existe un estudiante con el correo institucional: " + request.getCorreoInstitucional());
        }
        if (usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new BusinessException("Ya existe un usuario con el nombre: " + request.getNombreUsuario());
        }

        // Crear usuario de acceso
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setCorreo(request.getCorreoInstitucional());
        usuario.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));
        usuario.setRol(Rol.ESTUDIANTE);
        usuario.setActivo(true);
        usuarioRepository.save(usuario);

        // Resolver relaciones
        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new ResourceNotFoundException("Carrera", "id", request.getCarreraId()));

        TipoSangre tipoSangre = null;
        if (request.getTipoSangreId() != null) {
            tipoSangre = tipoSangreRepository.findById(request.getTipoSangreId())
                    .orElseThrow(() -> new ResourceNotFoundException("TipoSangre", "id", request.getTipoSangreId()));
        }
        Estudiante estudiante = new Estudiante();
        estudiante.setUsuario(usuario);
        estudiante.setCarrera(carrera);
        estudiante.setTipoSangre(tipoSangre);
        estudiante.setNumeroCarne(request.getNumeroCarne());
        estudiante.setNombres(request.getNombres());
        estudiante.setApellidos(request.getApellidos());
        estudiante.setCui(request.getCui());
        estudiante.setFechaNacimiento(request.getFechaNacimiento());
        estudiante.setGenero(request.getGenero());
        estudiante.setCorreoInstitucional(request.getCorreoInstitucional());
        estudiante.setCorreoPersonal(request.getCorreoPersonal());
        estudiante.setAnioIngreso(request.getAnioIngreso());
        estudiante.setDireccion(request.getDireccion());
        estudiante.setInscrito(true);
        estudiante.setPensumCerrado(false);
        estudianteRepository.save(estudiante);

        // Persistir sub-entidades
        persistirTelefonos(estudiante, request.getTelefonos());
        persistirCondiciones(estudiante, request.getCondicionesMedicas());
        persistirAlergias(estudiante, request.getAlergias());
        persistirDiscapacidades(estudiante, request.getDiscapacidades());
        persistirContactos(estudiante, request.getContactosEmergencia());

        return mapper.toResponse(estudianteRepository.findById(estudiante.getId().longValue()).orElseThrow());
    }

    /**
     * Actualiza los datos de un estudiante existente.
     * No modifica datos de acceso (usuario/contraseña) ni sub-entidades.
     *
     * @param id      identificador del estudiante
     * @param request nuevos datos del estudiante
     * @return el expediente actualizado como DTO de respuesta
     * @throws ResourceNotFoundException si no existe el estudiante, carrera o tipo
     *                                   de sangre
     * @throws BusinessException         si el nuevo correo institucional ya está en
     *                                   uso
     */
    @Transactional
    public EstudianteResponse actualizar(Long id, EstudianteUpdateRequest request) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));

        if (!estudiante.getCorreoInstitucional().equals(request.getCorreoInstitucional())
                && estudianteRepository.existsByCorreoInstitucional(request.getCorreoInstitucional())) {
            throw new BusinessException(
                    "Ya existe un estudiante con el correo institucional: " + request.getCorreoInstitucional());
        }

        Carrera carrera = carreraRepository.findById(request.getCarreraId())
                .orElseThrow(() -> new ResourceNotFoundException("Carrera", "id", request.getCarreraId()));

        TipoSangre tipoSangre = null;
        if (request.getTipoSangreId() != null) {
            tipoSangre = tipoSangreRepository.findById(request.getTipoSangreId())
                    .orElseThrow(() -> new ResourceNotFoundException("TipoSangre", "id", request.getTipoSangreId()));
        }

        estudiante.setCarrera(carrera);
        estudiante.setTipoSangre(tipoSangre);
        estudiante.setNombres(request.getNombres());
        estudiante.setApellidos(request.getApellidos());
        estudiante.setFechaNacimiento(request.getFechaNacimiento());
        estudiante.setGenero(request.getGenero());
        estudiante.setCorreoInstitucional(request.getCorreoInstitucional());
        estudiante.setCorreoPersonal(request.getCorreoPersonal());
        estudiante.setDireccion(request.getDireccion());
        estudiante.setInscrito(request.getInscrito());
        estudiante.setPensumCerrado(request.getPensumCerrado());
        estudiante.setFechaCierrePensum(request.getFechaCierrePensum());

        return mapper.toResponse(estudianteRepository.save(estudiante));
    }

    // ─────────────────────────────────────────
    // TELÉFONOS
    // ─────────────────────────────────────────

    /**
     * Reemplaza todos los teléfonos de un estudiante con la lista proporcionada.
     *
     * @param id        identificador del estudiante
     * @param telefonos nueva lista de teléfonos
     * @throws ResourceNotFoundException si no existe el estudiante
     */
    @Transactional
    public EstudianteResponse actualizarTelefonos(Long id, List<EstudianteSubDtos.TelefonoRequest> telefonos) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));

        telefonoRepository.deleteByEstudianteId(id);
        persistirTelefonos(estudiante, telefonos);

        return mapper.toResponse(estudianteRepository.findById(estudiante.getId().longValue()).orElseThrow());
    }

    // ─────────────────────────────────────────
    // CONDICIONES MÉDICAS
    // ─────────────────────────────────────────

    /**
     * Reemplaza todas las condiciones médicas de un estudiante con la lista
     * proporcionada.
     *
     * @param id          identificador del estudiante
     * @param condiciones nueva lista de condiciones médicas
     * @throws ResourceNotFoundException si no existe el estudiante
     */
    @Transactional
    public EstudianteResponse actualizarCondiciones(Long id,
            List<EstudianteSubDtos.CondicionMedicaRequest> condiciones) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));

        condicionMedicaRepository.deleteByEstudianteId(id);
        persistirCondiciones(estudiante, condiciones);

        return mapper.toResponse(estudianteRepository.findById(estudiante.getId().longValue()).orElseThrow());
    }

    // ─────────────────────────────────────────
    // ALERGIAS
    // ─────────────────────────────────────────

    /**
     * Reemplaza todas las alergias de un estudiante con la lista proporcionada.
     *
     * @param id       identificador del estudiante
     * @param alergias nueva lista de alergias con observaciones
     * @throws ResourceNotFoundException si no existe el estudiante o alguna alergia
     *                                   referenciada
     */
    @Transactional
    public EstudianteResponse actualizarAlergias(Long id, List<EstudianteSubDtos.AlergiaRequest> alergias) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));

        estudianteAlergiaRepository.deleteByIdEstudianteId(id);
        persistirAlergias(estudiante, alergias);

        return mapper.toResponse(estudianteRepository.findById(estudiante.getId().longValue()).orElseThrow());
    }

    // ─────────────────────────────────────────
    // DISCAPACIDADES
    // ─────────────────────────────────────────

    /**
     * Reemplaza todas las discapacidades de un estudiante con la lista
     * proporcionada.
     *
     * @param id             identificador del estudiante
     * @param discapacidades nueva lista de discapacidades con observaciones
     * @throws ResourceNotFoundException si no existe el estudiante o algún tipo de
     *                                   discapacidad referenciado
     */
    @Transactional
    public EstudianteResponse actualizarDiscapacidades(Long id,
            List<EstudianteSubDtos.DiscapacidadRequest> discapacidades) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));

        estudianteDiscapacidadRepository.deleteByIdEstudianteId(id);
        persistirDiscapacidades(estudiante, discapacidades);

        return mapper.toResponse(estudianteRepository.findById(estudiante.getId().longValue()).orElseThrow());
    }

    // ─────────────────────────────────────────
    // CONTACTOS DE EMERGENCIA
    // ─────────────────────────────────────────

    /**
     * Reemplaza todos los contactos de emergencia de un estudiante con la lista
     * proporcionada.
     *
     * @param id        identificador del estudiante
     * @param contactos nueva lista de contactos de emergencia
     * @throws ResourceNotFoundException si no existe el estudiante
     */
    @Transactional
    public EstudianteResponse actualizarContactos(Long id,
            List<EstudianteSubDtos.ContactoEmergenciaRequest> contactos) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));

        contactoRepository.deleteByEstudianteId(id);
        persistirContactos(estudiante, contactos);

        return mapper.toResponse(estudianteRepository.findById(estudiante.getId().longValue()).orElseThrow());
    }

    // ─────────────────────────────────────────
    // MÉTODOS PRIVADOS DE PERSISTENCIA
    // ─────────────────────────────────────────

    /** Persiste la lista de teléfonos para el estudiante dado. */
    private void persistirTelefonos(Estudiante estudiante, List<EstudianteSubDtos.TelefonoRequest> lista) {
        for (EstudianteSubDtos.TelefonoRequest dto : lista) {
            TelefonoEstudiante tel = new TelefonoEstudiante();
            tel.setEstudiante(estudiante);
            tel.setNumero(dto.getNumero());
            tel.setTipo(dto.getTipo());
            telefonoRepository.save(tel);
        }
    }

    /** Persiste la lista de condiciones médicas para el estudiante dado. */
    private void persistirCondiciones(Estudiante estudiante, List<EstudianteSubDtos.CondicionMedicaRequest> lista) {
        for (EstudianteSubDtos.CondicionMedicaRequest dto : lista) {
            CondicionMedica condicion = new CondicionMedica();
            condicion.setEstudiante(estudiante);
            condicion.setDescripcion(dto.getDescripcion());
            condicionMedicaRepository.save(condicion);
        }
    }

    /** Persiste la lista de alergias para el estudiante dado. */
    private void persistirAlergias(Estudiante estudiante, List<EstudianteSubDtos.AlergiaRequest> lista) {
        for (EstudianteSubDtos.AlergiaRequest dto : lista) {
            Alergia alergia = alergiaRepository.findById(dto.getAlergiaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Alergia", "id", dto.getAlergiaId()));

            EstudianteAlergia ea = new EstudianteAlergia();
            ea.getId().setEstudianteId(estudiante.getId());
            ea.getId().setAlergiaId(alergia.getId());
            ea.setEstudiante(estudiante);
            ea.setAlergia(alergia);
            ea.setObservaciones(dto.getObservaciones());
            estudianteAlergiaRepository.save(ea);
        }
    }

    /** Persiste la lista de discapacidades para el estudiante dado. */
    private void persistirDiscapacidades(Estudiante estudiante, List<EstudianteSubDtos.DiscapacidadRequest> lista) {
        for (EstudianteSubDtos.DiscapacidadRequest dto : lista) {
            TipoDiscapacidad tipo = tipoDiscapacidadRepository.findById(dto.getTipoDiscapacidadId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("TipoDiscapacidad", "id", dto.getTipoDiscapacidadId()));

            EstudianteDiscapacidad ed = new EstudianteDiscapacidad();
            ed.getId().setEstudianteId(estudiante.getId());
            ed.getId().setTipoDiscapacidadId(tipo.getId());
            ed.setEstudiante(estudiante);
            ed.setTipoDiscapacidad(tipo);
            ed.setObservaciones(dto.getObservaciones());
            estudianteDiscapacidadRepository.save(ed);
        }
    }

    /** Persiste la lista de contactos de emergencia para el estudiante dado. */
    private void persistirContactos(Estudiante estudiante, List<EstudianteSubDtos.ContactoEmergenciaRequest> lista) {
        for (EstudianteSubDtos.ContactoEmergenciaRequest dto : lista) {
            ContactoEmergencia contacto = new ContactoEmergencia();
            contacto.setEstudiante(estudiante);
            contacto.setNombreCompleto(dto.getNombreCompleto());
            contacto.setParentesco(dto.getParentesco());
            contacto.setDireccion(dto.getDireccion());
            contactoRepository.save(contacto);
        }
    }
}