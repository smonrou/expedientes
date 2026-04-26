package gt.edu.cunori.expedientes.api.controller;

import gt.edu.cunori.expedientes.api.dto.justificacion.CambioEstadoRequest;
import gt.edu.cunori.expedientes.api.dto.justificacion.DocumentoRequest;
import gt.edu.cunori.expedientes.api.dto.justificacion.JustificacionDtos;
import gt.edu.cunori.expedientes.api.dto.justificacion.JustificacionRequest;
import gt.edu.cunori.expedientes.domain.enums.EstadoJustificacion;
import gt.edu.cunori.expedientes.service.JustificacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de justificaciones de inasistencia.
 *
 * Roles:
 * - ESTUDIANTE: presenta justificaciones y agrega documentos a las suyas.
 * - COORDINADOR: consulta y cambia estado de justificaciones de su carrera.
 * - ADMIN: acceso completo.
 */
@RestController
@RequestMapping("/api/justificaciones")
public class JustificacionController {

    private final JustificacionService justificacionService;

    public JustificacionController(JustificacionService justificacionService) {
        this.justificacionService = justificacionService;
    }

    /**
     * Retorna todas las justificaciones de un estudiante.
     * El estudiante solo puede ver las suyas; ADMIN y COORDINADOR pueden ver cualquier expediente.
     */
    @GetMapping("/estudiante/{estudianteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<List<JustificacionDtos.JustificacionResumenResponse>> listarPorEstudiante(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(justificacionService.listarPorEstudiante(estudianteId));
    }

    /**
     * Retorna todas las justificaciones de una carrera con filtro opcional por estado.
     * Solo ADMIN y COORDINADOR.
     */
    @GetMapping("/carrera/{carreraId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<List<JustificacionDtos.JustificacionResumenResponse>> listarPorCarrera(
            @PathVariable Long carreraId,
            @RequestParam(required = false) EstadoJustificacion estado) {
        return ResponseEntity.ok(justificacionService.listarPorCarrera(carreraId, estado));
    }

    /**
     * Retorna el detalle completo de una justificación por su id.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<JustificacionDtos.JustificacionResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(justificacionService.buscarPorId(id));
    }

    /**
     * Presenta una nueva justificación de inasistencia.
     * Solo el propio ESTUDIANTE o un ADMIN pueden presentar justificaciones.
     */
    @PostMapping("/estudiante/{estudianteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<JustificacionDtos.JustificacionResponse> presentar(
            @PathVariable Long estudianteId,
            @Valid @RequestBody JustificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(justificacionService.presentar(estudianteId, request));
    }

    /**
     * Cambia el estado de una justificación.
     * El parámetro revisadoPorId indica el usuario que realiza la revisión.
     * Solo ADMIN y COORDINADOR.
     */
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<JustificacionDtos.JustificacionResponse> cambiarEstado(
            @PathVariable Long id,
            @RequestParam Long revisadoPorId,
            @Valid @RequestBody CambioEstadoRequest request) {
        return ResponseEntity.ok(justificacionService.cambiarEstado(id, revisadoPorId, request));
    }

    /**
     * Agrega un documento adjunto a una justificación.
     * Solo el ESTUDIANTE dueño o un ADMIN pueden adjuntar documentos.
     */
    @PostMapping("/{justificacionId}/documentos")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<JustificacionDtos.JustificacionResponse> agregarDocumento(
            @PathVariable Long justificacionId,
            @Valid @RequestBody DocumentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(justificacionService.agregarDocumento(justificacionId, request));
    }

    /**
     * Elimina un documento adjunto de una justificación.
     * Solo ADMIN o el ESTUDIANTE dueño de la justificación.
     */
    @DeleteMapping("/{justificacionId}/documentos/{documentoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<Void> eliminarDocumento(
            @PathVariable Long justificacionId,
            @PathVariable Long documentoId) {
        justificacionService.eliminarDocumento(justificacionId, documentoId);
        return ResponseEntity.noContent().build();
    }
}
