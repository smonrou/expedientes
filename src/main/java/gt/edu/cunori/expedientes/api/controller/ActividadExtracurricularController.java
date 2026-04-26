package gt.edu.cunori.expedientes.api.controller;

import gt.edu.cunori.expedientes.api.dto.actividad.ActividadRequest;
import gt.edu.cunori.expedientes.api.dto.actividad.ActividadResponse;
import gt.edu.cunori.expedientes.service.ActividadExtracurricularService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de actividades extracurriculares.
 * Las actividades están anidadas bajo el expediente del estudiante.
 * - ADMIN y COORDINADOR: lectura de cualquier estudiante.
 * - ESTUDIANTE: gestión de sus propias actividades.
 */
@RestController
@RequestMapping("/api/estudiantes/{estudianteId}/actividades")
public class ActividadExtracurricularController {

    private final ActividadExtracurricularService actividadService;

    public ActividadExtracurricularController(ActividadExtracurricularService actividadService) {
        this.actividadService = actividadService;
    }

    /** Retorna todas las actividades extracurriculares de un estudiante. */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<List<ActividadResponse>> listar(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(actividadService.listarPorEstudiante(estudianteId));
    }

    /** Retorna una actividad por su id. */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<ActividadResponse> obtener(
            @PathVariable Long estudianteId,
            @PathVariable Long id) {
        return ResponseEntity.ok(actividadService.buscarPorId(id));
    }

    /** Crea una nueva actividad extracurricular para el estudiante. Solo ADMIN o el propio ESTUDIANTE. */
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<ActividadResponse> crear(
            @PathVariable Long estudianteId,
            @Valid @RequestBody ActividadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(actividadService.crear(estudianteId, request));
    }

    /** Actualiza una actividad extracurricular existente. Solo ADMIN o el propio ESTUDIANTE. */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<ActividadResponse> actualizar(
            @PathVariable Long estudianteId,
            @PathVariable Long id,
            @Valid @RequestBody ActividadRequest request) {
        return ResponseEntity.ok(actividadService.actualizar(estudianteId, id, request));
    }

    /** Elimina una actividad extracurricular. Solo ADMIN o el propio ESTUDIANTE. */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long estudianteId,
            @PathVariable Long id) {
        actividadService.eliminar(estudianteId, id);
        return ResponseEntity.noContent().build();
    }
}