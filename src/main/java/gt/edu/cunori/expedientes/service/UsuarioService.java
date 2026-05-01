package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.usuario.CambioContrasenaRequest;
import gt.edu.cunori.expedientes.api.dto.usuario.UsuarioCreateRequest;
import gt.edu.cunori.expedientes.api.dto.usuario.UsuarioResponse;
import gt.edu.cunori.expedientes.api.dto.usuario.UsuarioUpdateRequest;
import gt.edu.cunori.expedientes.api.mapper.UsuarioMapper;
import gt.edu.cunori.expedientes.domain.entity.Usuario;
import gt.edu.cunori.expedientes.domain.repository.UsuarioRepository;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión de usuarios del sistema.
 * Permite listar, crear, actualizar, cambiar contraseña y activar/desactivar
 * usuarios.
 */
@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Retorna todos los usuarios registrados en el sistema.
     *
     * @return lista de usuarios como DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Busca un usuario por su identificador.
     *
     * @param id identificador del usuario
     * @return el usuario como DTO de respuesta
     * @throws ResourceNotFoundException si no existe un usuario con ese id
     */
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));
    }

    /**
     * Crea un nuevo usuario en el sistema.
     * Valida unicidad de correo y nombre de usuario.
     * La contraseña se hashea con BCrypt antes de persistirse.
     *
     * @param request datos del nuevo usuario
     * @return el usuario creado como DTO de respuesta
     * @throws BusinessException si el correo o nombre de usuario ya están en uso
     */
    @Transactional
    public UsuarioResponse crear(UsuarioCreateRequest request) {
        if (repository.existsByCorreo(request.getCorreo())) {
            throw new BusinessException("Ya existe un usuario con el correo: " + request.getCorreo());
        }
        if (repository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new BusinessException("Ya existe un usuario con el nombre de usuario: " + request.getNombreUsuario());
        }

        Usuario entidad = new Usuario();
        entidad.setNombreUsuario(request.getNombreUsuario());
        entidad.setCorreo(request.getCorreo());
        entidad.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));
        entidad.setRol(request.getRol());
        entidad.setActivo(true);

        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Actualiza los datos de un usuario existente.
     * No modifica la contraseña — usar {@link #cambiarContrasena} para eso.
     *
     * @param id      identificador del usuario a actualizar
     * @param request nuevos datos del usuario
     * @return el usuario actualizado como DTO de respuesta
     * @throws ResourceNotFoundException si no existe un usuario con ese id
     * @throws BusinessException         si el nuevo correo o nombre de usuario ya
     *                                   están en uso
     */
    @Transactional
    public UsuarioResponse actualizar(Long id, UsuarioUpdateRequest request) {
        Usuario entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        if (!entidad.getCorreo().equals(request.getCorreo()) && repository.existsByCorreo(request.getCorreo())) {
            throw new BusinessException("Ya existe un usuario con el correo: " + request.getCorreo());
        }
        if (!entidad.getNombreUsuario().equals(request.getNombreUsuario())
                && repository.existsByNombreUsuario(request.getNombreUsuario())) {
            throw new BusinessException("Ya existe un usuario con el nombre de usuario: " + request.getNombreUsuario());
        }

        entidad.setNombreUsuario(request.getNombreUsuario());
        entidad.setCorreo(request.getCorreo());
        entidad.setRol(request.getRol());
        entidad.setActivo(request.getActivo());

        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Cambia la contraseña de un usuario.
     * La nueva contraseña se hashea con BCrypt antes de persistirse.
     *
     * @param id      identificador del usuario
     * @param request DTO con la nueva contraseña en texto plano
     * @throws ResourceNotFoundException si no existe un usuario con ese id
     */
    @Transactional
    public void cambiarContrasena(Long id, CambioContrasenaRequest request) {
        Usuario entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        entidad.setContrasenaHash(passwordEncoder.encode(request.getNuevaContrasena()));
        repository.save(entidad);
    }

    /**
     * Desactiva un usuario del sistema (baja lógica).
     * No elimina el registro de la base de datos.
     *
     * @param id identificador del usuario a desactivar
     * @throws ResourceNotFoundException si no existe un usuario con ese id
     * @throws BusinessException         si el usuario ya está inactivo
     */
    @Transactional
    public void desactivar(Long id) {
        Usuario entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        if (!entidad.getActivo()) {
            throw new BusinessException("El usuario ya está inactivo.");
        }
        entidad.setActivo(false);
        repository.save(entidad);
    }

    /**
     * Reactiva un usuario previamente desactivado.
     *
     * @param id identificador del usuario a reactivar
     * @throws ResourceNotFoundException si no existe un usuario con ese id
     * @throws BusinessException         si el usuario ya está activo
     */
    @Transactional
    public void activar(Long id) {
        Usuario entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", id));

        if (entidad.getActivo()) {
            throw new BusinessException("El usuario ya está activo.");
        }
        entidad.setActivo(true);
        repository.save(entidad);
    }
}