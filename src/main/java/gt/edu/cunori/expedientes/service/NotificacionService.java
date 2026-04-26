package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.notificacion.ConteoNoLeidasResponse;
import gt.edu.cunori.expedientes.api.dto.notificacion.NotificacionResponse;
import gt.edu.cunori.expedientes.api.mapper.NotificacionMapper;
import gt.edu.cunori.expedientes.domain.repository.NotificacionRepository;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la consulta y gestión de notificaciones internas.
 * Las notificaciones se crean automáticamente desde JustificacionService —
 * este servicio solo expone la lectura y el marcado como leídas.
 */
@Service
public class NotificacionService {

    private final NotificacionRepository repository;
    private final NotificacionMapper mapper;

    public NotificacionService(NotificacionRepository repository, NotificacionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retorna todas las notificaciones de un usuario ordenadas por fecha descendente.
     * Las no leídas aparecen primero.
     *
     * @param destinatarioId identificador del usuario destinatario
     * @return lista de notificaciones como DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarPorUsuario(Long destinatarioId) {
        return repository.findByUsuarioDestinatarioIdOrderByLeidaAscCreadaEnDesc(destinatarioId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Retorna solo las notificaciones no leídas de un usuario.
     *
     * @param destinatarioId identificador del usuario destinatario
     * @return lista de notificaciones no leídas como DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarNoLeidas(Long destinatarioId) {
        return repository.findByUsuarioDestinatario_IdAndLeidaFalse(destinatarioId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Retorna el conteo de notificaciones no leídas de un usuario.
     * Usado por el frontend para mostrar el badge en el ícono de notificaciones.
     *
     * @param destinatarioId identificador del usuario destinatario
     * @return DTO con el total de notificaciones no leídas
     */
    @Transactional(readOnly = true)
    public ConteoNoLeidasResponse contarNoLeidas(Long destinatarioId) {
        return new ConteoNoLeidasResponse(
                repository.countByUsuarioDestinatarioIdAndLeidaFalse(destinatarioId));
    }

    /**
     * Marca una notificación específica como leída.
     *
     * @param id identificador de la notificación
     * @throws ResourceNotFoundException si no existe la notificación
     */
    @Transactional
    public void marcarComoLeida(Long id) {
        var notificacion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion", "id", id));
        notificacion.setLeida(true);
        repository.save(notificacion);
    }

    /**
     * Marca todas las notificaciones de un usuario como leídas.
     * Se invoca cuando el usuario abre el panel de notificaciones.
     *
     * @param destinatarioId identificador del usuario destinatario
     */
    @Transactional
    public void marcarTodasComoLeidas(Long destinatarioId) {
        repository.marcarTodasComoLeidas(destinatarioId);
    }
}