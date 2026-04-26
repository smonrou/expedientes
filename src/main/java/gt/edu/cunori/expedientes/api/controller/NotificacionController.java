package gt.edu.cunori.expedientes.api.controller;

import gt.edu.cunori.expedientes.api.dto.notificacion.ConteoNoLeidasResponse;
import gt.edu.cunori.expedientes.api.dto.notificacion.NotificacionResponse;
import gt.edu.cunori.expedientes.service.NotificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la consulta y gestión de notificaciones internas.
 * Cada usuario solo puede consultar y gestionar sus propias notificaciones.
 */
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    public NotificacionController(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    /**
     * Retorna todas las notificaciones del usuario indicado.
     * Las no leídas aparecen primero.
     */
    @GetMapping("/usuario/{destinatarioId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<List<NotificacionResponse>> listarTodas(@PathVariable Long destinatarioId) {
        return ResponseEntity.ok(notificacionService.listarPorUsuario(destinatarioId));
    }

    /**
     * Retorna solo las notificaciones no leídas del usuario indicado.
     */
    @GetMapping("/usuario/{destinatarioId}/no-leidas")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<List<NotificacionResponse>> listarNoLeidas(@PathVariable Long destinatarioId) {
        return ResponseEntity.ok(notificacionService.listarNoLeidas(destinatarioId));
    }

    /**
     * Retorna el conteo de notificaciones no leídas del usuario.
     * Usado por el frontend para el badge en el ícono de notificaciones.
     */
    @GetMapping("/usuario/{destinatarioId}/conteo")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<ConteoNoLeidasResponse> contarNoLeidas(@PathVariable Long destinatarioId) {
        return ResponseEntity.ok(notificacionService.contarNoLeidas(destinatarioId));
    }

    /**
     * Marca una notificación específica como leída.
     */
    @PatchMapping("/{id}/leida")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<Void> marcarComoLeida(@PathVariable Long id) {
        notificacionService.marcarComoLeida(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Marca todas las notificaciones del usuario como leídas.
     * Se invoca cuando el usuario abre el panel de notificaciones.
     */
    @PatchMapping("/usuario/{destinatarioId}/marcar-todas-leidas")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<Void> marcarTodasComoLeidas(@PathVariable Long destinatarioId) {
        notificacionService.marcarTodasComoLeidas(destinatarioId);
        return ResponseEntity.noContent().build();
    }
}