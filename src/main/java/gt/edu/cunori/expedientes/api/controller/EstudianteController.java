package gt.edu.cunori.expedientes.api.controller;

import gt.edu.cunori.expedientes.api.dto.estudiante.*;
import gt.edu.cunori.expedientes.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión del expediente estudiantil.
 * - ADMIN y COORDINADOR: acceso completo a todos los expedientes.
 * - ESTUDIANTE: solo acceso a su propio expediente.
 */
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    /**
     * Retorna todos los estudiantes en formato resumido. Solo ADMIN y COORDINADOR.
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<List<EstudianteResumenResponse>> listarTodos() {
        return ResponseEntity.ok(estudianteService.listarTodos());
    }

    /**
     * Busca estudiantes por término (nombre, apellido o carné).
     * Acepta filtro opcional por carrera.
     * Solo ADMIN y COORDINADOR.
     */
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<List<EstudianteResumenResponse>> buscar(
            @RequestParam String termino,
            @RequestParam(required = false) Long carreraId) {
        return ResponseEntity.ok(estudianteService.buscar(termino, carreraId));
    }

    /**
     * Retorna el expediente completo de un estudiante por su id. Solo ADMIN y
     * COORDINADOR.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<EstudianteResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estudianteService.buscarPorId(id));
    }

    /**
     * Retorna el expediente del estudiante autenticado.
     * El estudiante pasa su usuarioId como parámetro.
     * Solo ESTUDIANTE (acceso a su propio expediente).
     */
    @GetMapping("/mi-expediente")
    @PreAuthorize("hasRole('ESTUDIANTE')")
    public ResponseEntity<EstudianteResponse> miExpediente(@RequestParam Long usuarioId) {
        return ResponseEntity.ok(estudianteService.buscarPorUsuarioId(usuarioId));
    }

    /** Crea un nuevo expediente estudiantil junto con su usuario. Solo ADMIN. */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstudianteResponse> crear(@Valid @RequestBody EstudianteCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estudianteService.crear(request));
    }

    /** Actualiza los datos del expediente de un estudiante. Solo ADMIN. */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EstudianteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteUpdateRequest request) {
        return ResponseEntity.ok(estudianteService.actualizar(id, request));
    }

    /** Reemplaza los teléfonos de un estudiante. ADMIN o el propio ESTUDIANTE. */
    @PutMapping("/{id}/telefonos")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<EstudianteResponse> actualizarTelefonos(
            @PathVariable Long id,
            @Valid @RequestBody List<EstudianteSubDtos.TelefonoRequest> telefonos) {
        return ResponseEntity.ok(estudianteService.actualizarTelefonos(id, telefonos));
    }

    /**
     * Reemplaza las condiciones médicas de un estudiante. ADMIN o el propio
     * ESTUDIANTE.
     */
    @PutMapping("/{id}/condiciones-medicas")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<EstudianteResponse> actualizarCondiciones(
            @PathVariable Long id,
            @Valid @RequestBody List<EstudianteSubDtos.CondicionMedicaRequest> condiciones) {
        return ResponseEntity.ok(estudianteService.actualizarCondiciones(id, condiciones));
    }

    /** Reemplaza las alergias de un estudiante. ADMIN o el propio ESTUDIANTE. */
    @PutMapping("/{id}/alergias")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<EstudianteResponse> actualizarAlergias(
            @PathVariable Long id,
            @Valid @RequestBody List<EstudianteSubDtos.AlergiaRequest> alergias) {
        return ResponseEntity.ok(estudianteService.actualizarAlergias(id, alergias));
    }

    /**
     * Reemplaza las discapacidades de un estudiante. ADMIN o el propio ESTUDIANTE.
     */
    @PutMapping("/{id}/discapacidades")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<EstudianteResponse> actualizarDiscapacidades(
            @PathVariable Long id,
            @Valid @RequestBody List<EstudianteSubDtos.DiscapacidadRequest> discapacidades) {
        return ResponseEntity.ok(estudianteService.actualizarDiscapacidades(id, discapacidades));
    }

    /**
     * Reemplaza los contactos de emergencia de un estudiante. ADMIN o el propio
     * ESTUDIANTE.
     */
    @PutMapping("/{id}/contactos-emergencia")
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<EstudianteResponse> actualizarContactos(
            @PathVariable Long id,
            @Valid @RequestBody List<EstudianteSubDtos.ContactoEmergenciaRequest> contactos) {
        return ResponseEntity.ok(estudianteService.actualizarContactos(id, contactos));
    }
}