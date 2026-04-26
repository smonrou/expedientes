package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.justificacion.CambioEstadoRequest;
import gt.edu.cunori.expedientes.api.dto.justificacion.JustificacionDtos;
import gt.edu.cunori.expedientes.api.dto.justificacion.JustificacionRequest;
import gt.edu.cunori.expedientes.api.mapper.JustificacionMapper;
import gt.edu.cunori.expedientes.domain.entity.*;
import gt.edu.cunori.expedientes.domain.enums.EstadoJustificacion;
import gt.edu.cunori.expedientes.domain.repository.*;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Servicio para la gestión de justificaciones de inasistencia.
 * Maneja el flujo de estados, notificaciones automáticas y documentos adjuntos
 * como BLOB.
 *
 * Flujo de estados:
 * PRESENTADA → EN_REVISION → APROBADA
 * → RECHAZADA
 */
@Service
public class JustificacionService {

    private final JustificacionInasistenciaRepository justificacionRepository;
    private final EstudianteRepository estudianteRepository;
    private final MotivoInasistenciaRepository motivoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FechaInasistenciaRepository fechaRepository;
    private final DocumentoJustificacionRepository documentoRepository;
    private final NotificacionRepository notificacionRepository;
    private final JustificacionMapper mapper;

    public JustificacionService(
            JustificacionInasistenciaRepository justificacionRepository,
            EstudianteRepository estudianteRepository,
            MotivoInasistenciaRepository motivoRepository,
            UsuarioRepository usuarioRepository,
            FechaInasistenciaRepository fechaRepository,
            DocumentoJustificacionRepository documentoRepository,
            NotificacionRepository notificacionRepository,
            JustificacionMapper mapper) {
        this.justificacionRepository = justificacionRepository;
        this.estudianteRepository = estudianteRepository;
        this.motivoRepository = motivoRepository;
        this.usuarioRepository = usuarioRepository;
        this.fechaRepository = fechaRepository;
        this.documentoRepository = documentoRepository;
        this.notificacionRepository = notificacionRepository;
        this.mapper = mapper;
    }

    // ─────────────────────────────────────────
    // CONSULTAS
    // ─────────────────────────────────────────

    /**
     * Retorna todas las justificaciones de un estudiante específico.
     *
     * @param estudianteId identificador del estudiante
     * @return lista de justificaciones en formato resumen
     * @throws ResourceNotFoundException si no existe el estudiante
     */
    @Transactional(readOnly = true)
    public List<JustificacionDtos.JustificacionResumenResponse> listarPorEstudiante(Long estudianteId) {
        if (!estudianteRepository.existsById(estudianteId)) {
            throw new ResourceNotFoundException("Estudiante", "id", estudianteId);
        }
        return justificacionRepository.findByEstudianteId(estudianteId)
                .stream()
                .map(mapper::toResumen)
                .toList();
    }

    /**
     * Retorna todas las justificaciones de una carrera, con filtro opcional por
     * estado.
     * Usado por coordinadores para revisar las justificaciones de su carrera.
     *
     * @param carreraId identificador de la carrera
     * @param estado    estado para filtrar (opcional, puede ser null)
     * @return lista de justificaciones en formato resumen
     */
    @Transactional(readOnly = true)
    public List<JustificacionDtos.JustificacionResumenResponse> listarPorCarrera(Long carreraId,
            EstadoJustificacion estado) {
        List<JustificacionInasistencia> resultados;
        if (estado != null) {
            resultados = justificacionRepository.findByCarreraIdAndEstado(carreraId, estado);
        } else {
            resultados = justificacionRepository.findByCarreraId(carreraId);
        }
        return resultados.stream().map(mapper::toResumen).toList();
    }

    /**
     * Retorna el detalle completo de una justificación por su identificador.
     *
     * @param id identificador de la justificación
     * @return la justificación completa como DTO de respuesta
     * @throws ResourceNotFoundException si no existe la justificación
     */
    @Transactional(readOnly = true)
    public JustificacionDtos.JustificacionResponse buscarPorId(Long id) {
        return justificacionRepository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("JustificacionInasistencia", "id", id));
    }

    // ─────────────────────────────────────────
    // PRESENTAR JUSTIFICACIÓN
    // ─────────────────────────────────────────

    /**
     * Presenta una nueva justificación de inasistencia.
     * Valida que no existan fechas duplicadas en otras justificaciones activas del
     * mismo estudiante.
     * Genera automáticamente una notificación para los coordinadores de la carrera.
     *
     * @param estudianteId identificador del estudiante que presenta la
     *                     justificación
     * @param request      datos de la justificación (motivo, descripción, fechas)
     * @return la justificación creada como DTO de respuesta completo
     * @throws ResourceNotFoundException si no existe el estudiante o el motivo
     * @throws BusinessException         si alguna fecha ya está cubierta por otra
     *                                   justificación activa
     */
    @Transactional
    public JustificacionDtos.JustificacionResponse presentar(Long estudianteId, JustificacionRequest request) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", estudianteId));

        MotivoInasistencia motivo = motivoRepository.findById(request.getMotivoId())
                .orElseThrow(() -> new ResourceNotFoundException("MotivoInasistencia", "id", request.getMotivoId()));

        // Validar fechas duplicadas en justificaciones activas del mismo estudiante
        for (java.time.LocalDate fecha : request.getFechas()) {
            if (fechaRepository.existsByEstudianteIdAndFecha(estudianteId, fecha)) {
                throw new BusinessException("La fecha " + fecha + " ya está cubierta por otra justificación activa.");
            }
        }

        // Crear justificación
        JustificacionInasistencia justificacion = new JustificacionInasistencia();
        justificacion.setEstudiante(estudiante);
        justificacion.setMotivo(motivo);
        justificacion.setDescripcion(request.getDescripcion());
        justificacion.setEstado(EstadoJustificacion.PRESENTADA);
        justificacionRepository.save(justificacion);

        // Persistir fechas de inasistencia
        for (java.time.LocalDate fecha : request.getFechas()) {
            FechaInasistencia fi = new FechaInasistencia();
            fi.setJustificacion(justificacion);
            fi.setFecha(fecha);
            fechaRepository.save(fi);
        }

        // Notificar a los coordinadores de la carrera
        notificarCoordinadoresDeCarrera(justificacion, estudiante,
                "Nueva justificación presentada por " + estudiante.getNombres() + " "
                        + estudiante.getApellidos() + " (Carné: " + estudiante.getNumeroCarne() + ").");

        return mapper.toResponse(justificacionRepository.findById(justificacion.getId().longValue()).orElseThrow());
    }

    // ─────────────────────────────────────────
    // CAMBIO DE ESTADO
    // ─────────────────────────────────────────

    /**
     * Cambia el estado de una justificación siguiendo el flujo definido.
     * Registra el usuario que realizó la revisión y la fecha de revisión.
     * Genera una notificación al estudiante informando el nuevo estado.
     *
     * Transiciones válidas:
     * PRESENTADA → EN_REVISION
     * EN_REVISION → APROBADA | RECHAZADA
     *
     * @param id            identificador de la justificación
     * @param revisadoPorId identificador del usuario (coordinador/admin) que revisa
     * @param request       DTO con el nuevo estado
     * @return la justificación actualizada como DTO de respuesta completo
     * @throws ResourceNotFoundException si no existe la justificación o el usuario
     *                                   revisor
     * @throws BusinessException         si la transición de estado no es válida
     */
    @Transactional
    public JustificacionDtos.JustificacionResponse cambiarEstado(Long id, Long revisadoPorId,
            CambioEstadoRequest request) {
        JustificacionInasistencia justificacion = justificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("JustificacionInasistencia", "id", id));

        Usuario revisor = usuarioRepository.findById(revisadoPorId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", revisadoPorId));

        validarTransicion(justificacion.getEstado(), request.getNuevoEstado());

        justificacion.setEstado(request.getNuevoEstado());
        justificacion.setRevisadoPor(revisor);
        justificacion.setFechaRevision(LocalDateTime.now());
        justificacionRepository.save(justificacion);

        // Notificar al estudiante del cambio de estado
        crearNotificacion(justificacion, justificacion.getEstudiante().getUsuario(),
                "Tu justificación ha cambiado al estado: " + request.getNuevoEstado().name());

        return mapper.toResponse(justificacionRepository.findById(justificacion.getId().longValue()).orElseThrow());
    }

    // ─────────────────────────────────────────
    // DOCUMENTOS BLOB
    // ─────────────────────────────────────────

    /**
     * Agrega un documento adjunto a una justificación almacenándolo como BLOB en la
     * base de datos.
     * Solo se permite adjuntar documentos si la justificación está en estado
     * PRESENTADA o EN_REVISION.
     * Tipos de archivo permitidos: PDF, imágenes (JPG, PNG), Word (DOC, DOCX).
     *
     * @param justificacionId identificador de la justificación
     * @param archivo         archivo recibido como multipart
     * @return la justificación actualizada como DTO de respuesta completo
     * @throws ResourceNotFoundException si no existe la justificación
     * @throws BusinessException         si la justificación ya fue resuelta, el
     *                                   archivo está vacío
     *                                   o el tipo MIME no está permitido
     */
    @Transactional
    public JustificacionDtos.JustificacionResponse agregarDocumento(Long justificacionId, MultipartFile archivo) {
        JustificacionInasistencia justificacion = justificacionRepository.findById(justificacionId)
                .orElseThrow(() -> new ResourceNotFoundException("JustificacionInasistencia", "id", justificacionId));

        if (justificacion.getEstado() == EstadoJustificacion.APROBADA
                || justificacion.getEstado() == EstadoJustificacion.RECHAZADA) {
            throw new BusinessException("No se pueden agregar documentos a una justificación ya resuelta.");
        }

        if (archivo.isEmpty()) {
            throw new BusinessException("El archivo no puede estar vacío.");
        }

        String tipoMime = archivo.getContentType();
        validarTipoMime(tipoMime);

        try {
            DocumentoJustificacion documento = new DocumentoJustificacion();
            documento.setJustificacion(justificacion);
            documento.setNombreOriginal(archivo.getOriginalFilename());
            documento.setTipoMime(tipoMime);
            documento.setContenido(archivo.getBytes());
            documentoRepository.save(documento);
        } catch (IOException e) {
            throw new BusinessException("Error al leer el archivo: " + e.getMessage());
        }

        return mapper.toResponse(justificacionRepository.findById(justificacion.getId().longValue()).orElseThrow());
    }

    /**
     * Retorna el contenido binario de un documento junto con su tipo MIME.
     * Usado por el endpoint de descarga para servir el archivo al cliente.
     *
     * @param documentoId identificador del documento
     * @return la entidad completa con el BLOB para que el controller construya la
     *         respuesta HTTP
     * @throws ResourceNotFoundException si no existe el documento
     */
    @Transactional(readOnly = true)
    public DocumentoJustificacion descargarDocumento(Long documentoId) {
        return documentoRepository.findById(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentoJustificacion", "id", documentoId));
    }

    /**
     * Elimina un documento adjunto de una justificación.
     * Solo se puede eliminar si la justificación aún no ha sido resuelta.
     *
     * @param justificacionId identificador de la justificación
     * @param documentoId     identificador del documento a eliminar
     * @throws ResourceNotFoundException si no existe el documento
     * @throws BusinessException         si el documento no pertenece a la
     *                                   justificación indicada
     *                                   o si la justificación ya fue resuelta
     */
    @Transactional
    public void eliminarDocumento(Long justificacionId, Long documentoId) {
        DocumentoJustificacion documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new ResourceNotFoundException("DocumentoJustificacion", "id", documentoId));

        if (!documento.getJustificacion().getId().equals(justificacionId)) {
            throw new BusinessException("El documento no pertenece a la justificación indicada.");
        }

        if (documento.getJustificacion().getEstado() == EstadoJustificacion.APROBADA
                || documento.getJustificacion().getEstado() == EstadoJustificacion.RECHAZADA) {
            throw new BusinessException("No se pueden eliminar documentos de una justificación ya resuelta.");
        }

        documentoRepository.deleteById(documentoId);
    }

    // ─────────────────────────────────────────
    // MÉTODOS PRIVADOS
    // ─────────────────────────────────────────

    /**
     * Valida que el tipo MIME del archivo sea uno de los permitidos.
     * Tipos permitidos: PDF, JPG, PNG, DOC, DOCX.
     *
     * @param tipoMime el tipo MIME a validar
     * @throws BusinessException si el tipo MIME no está permitido
     */
    private void validarTipoMime(String tipoMime) {
        List<String> tiposPermitidos = List.of(
                "application/pdf",
                "image/jpeg",
                "image/png",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        if (tipoMime == null || !tiposPermitidos.contains(tipoMime)) {
            throw new BusinessException(
                    "Tipo de archivo no permitido. Se aceptan: PDF, JPG, PNG, DOC, DOCX.");
        }
    }

    /**
     * Valida que la transición de estado sea permitida según el flujo definido.
     *
     * @param estadoActual el estado actual de la justificación
     * @param nuevoEstado  el estado al que se desea cambiar
     * @throws BusinessException si la transición no es válida
     */
    private void validarTransicion(EstadoJustificacion estadoActual, EstadoJustificacion nuevoEstado) {
        boolean valida = switch (estadoActual) {
            case PRESENTADA -> nuevoEstado == EstadoJustificacion.EN_REVISION;
            case EN_REVISION -> nuevoEstado == EstadoJustificacion.APROBADA
                    || nuevoEstado == EstadoJustificacion.RECHAZADA;
            case APROBADA, RECHAZADA -> false;
        };

        if (!valida) {
            throw new BusinessException(
                    "Transición de estado no permitida: " + estadoActual.name() + " → " + nuevoEstado.name());
        }
    }

    /**
     * Crea una notificación para un usuario específico relacionada con una
     * justificación.
     *
     * @param justificacion la justificación relacionada
     * @param destinatario  el usuario que recibirá la notificación
     * @param mensaje       el texto del mensaje
     */
    private void crearNotificacion(JustificacionInasistencia justificacion, Usuario destinatario, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setJustificacion(justificacion);
        notificacion.setUsuarioDestinatario(destinatario);
        notificacion.setMensaje(mensaje);
        notificacion.setLeida(false);
        notificacionRepository.save(notificacion);
    }

    /**
     * Notifica a todos los usuarios con rol COORDINADOR sobre una nueva
     * justificación presentada.
     *
     * @param justificacion la justificación recién presentada
     * @param estudiante    el estudiante que presentó la justificación
     * @param mensaje       el texto del mensaje de notificación
     */
    private void notificarCoordinadoresDeCarrera(JustificacionInasistencia justificacion,
            Estudiante estudiante, String mensaje) {
        List<Usuario> coordinadores = usuarioRepository.findByRol(
                gt.edu.cunori.expedientes.domain.enums.Rol.COORDINADOR);

        for (Usuario coordinador : coordinadores) {
            crearNotificacion(justificacion, coordinador, mensaje);
        }
    }
}