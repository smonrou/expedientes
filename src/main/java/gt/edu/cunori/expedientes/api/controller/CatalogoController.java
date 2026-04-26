package gt.edu.cunori.expedientes.api.controller;

import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoRequest;
import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoResponse;
import gt.edu.cunori.expedientes.api.dto.catalogo.CarreraRequest;
import gt.edu.cunori.expedientes.api.dto.catalogo.CarreraResponse;
import gt.edu.cunori.expedientes.service.AlergiaService;
import gt.edu.cunori.expedientes.service.CarreraService;
import gt.edu.cunori.expedientes.service.MotivoInasistenciaService;
import gt.edu.cunori.expedientes.service.TipoActividadService;
import gt.edu.cunori.expedientes.service.TipoDiscapacidadService;
import gt.edu.cunori.expedientes.service.TipoSangreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de los catálogos del sistema.
 * Agrupa los endpoints de: TipoSangre, Alergia, TipoDiscapacidad,
 * TipoActividad, MotivoInasistencia y Carrera.
 *
 * Acceso de lectura (GET): todos los roles autenticados.
 * Escritura (POST, PUT, DELETE): solo ADMIN.
 */
@RestController
@RequestMapping("/api/catalogos")
public class CatalogoController {

    private final TipoSangreService tipoSangreService;
    private final AlergiaService alergiaService;
    private final TipoDiscapacidadService tipoDiscapacidadService;
    private final TipoActividadService tipoActividadService;
    private final MotivoInasistenciaService motivoInasistenciaService;
    private final CarreraService carreraService;

    public CatalogoController(
            TipoSangreService tipoSangreService,
            AlergiaService alergiaService,
            TipoDiscapacidadService tipoDiscapacidadService,
            TipoActividadService tipoActividadService,
            MotivoInasistenciaService motivoInasistenciaService,
            CarreraService carreraService) {
        this.tipoSangreService = tipoSangreService;
        this.alergiaService = alergiaService;
        this.tipoDiscapacidadService = tipoDiscapacidadService;
        this.tipoActividadService = tipoActividadService;
        this.motivoInasistenciaService = motivoInasistenciaService;
        this.carreraService = carreraService;
    }

    // ─────────────────────────────────────────
    // TIPO SANGRE
    // ─────────────────────────────────────────

    /** Retorna todos los tipos de sangre. */
    @GetMapping("/tipo-sangre")
    public ResponseEntity<List<CatalogoResponse>> listarTiposSangre() {
        return ResponseEntity.ok(tipoSangreService.listarTodos());
    }

    /** Crea un nuevo tipo de sangre. Solo ADMIN. */
    @PostMapping("/tipo-sangre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> crearTipoSangre(@Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoSangreService.crear(request));
    }

    /** Actualiza un tipo de sangre existente. Solo ADMIN. */
    @PutMapping("/tipo-sangre/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> actualizarTipoSangre(
            @PathVariable Long id,
            @Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.ok(tipoSangreService.actualizar(id, request));
    }

    /** Elimina un tipo de sangre. Solo ADMIN. */
    @DeleteMapping("/tipo-sangre/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarTipoSangre(@PathVariable Long id) {
        tipoSangreService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────
    // ALERGIA
    // ─────────────────────────────────────────

    /** Retorna todas las alergias. */
    @GetMapping("/alergias")
    public ResponseEntity<List<CatalogoResponse>> listarAlergias() {
        return ResponseEntity.ok(alergiaService.listarTodas());
    }

    /** Crea una nueva alergia. Solo ADMIN. */
    @PostMapping("/alergias")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> crearAlergia(@Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alergiaService.crear(request));
    }

    /** Actualiza una alergia existente. Solo ADMIN. */
    @PutMapping("/alergias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> actualizarAlergia(
            @PathVariable Long id,
            @Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.ok(alergiaService.actualizar(id, request));
    }

    /** Elimina una alergia. Solo ADMIN. */
    @DeleteMapping("/alergias/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarAlergia(@PathVariable Long id) {
        alergiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────
    // TIPO DISCAPACIDAD
    // ─────────────────────────────────────────

    /** Retorna todos los tipos de discapacidad. */
    @GetMapping("/tipo-discapacidad")
    public ResponseEntity<List<CatalogoResponse>> listarTiposDiscapacidad() {
        return ResponseEntity.ok(tipoDiscapacidadService.listarTodos());
    }

    /** Crea un nuevo tipo de discapacidad. Solo ADMIN. */
    @PostMapping("/tipo-discapacidad")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> crearTipoDiscapacidad(@Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoDiscapacidadService.crear(request));
    }

    /** Actualiza un tipo de discapacidad existente. Solo ADMIN. */
    @PutMapping("/tipo-discapacidad/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> actualizarTipoDiscapacidad(
            @PathVariable Long id,
            @Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.ok(tipoDiscapacidadService.actualizar(id, request));
    }

    /** Elimina un tipo de discapacidad. Solo ADMIN. */
    @DeleteMapping("/tipo-discapacidad/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarTipoDiscapacidad(@PathVariable Long  id) {
        tipoDiscapacidadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────
    // TIPO ACTIVIDAD
    // ─────────────────────────────────────────

    /** Retorna todos los tipos de actividad extracurricular. */
    @GetMapping("/tipo-actividad")
    public ResponseEntity<List<CatalogoResponse>> listarTiposActividad() {
        return ResponseEntity.ok(tipoActividadService.listarTodos());
    }

    /** Crea un nuevo tipo de actividad. Solo ADMIN. */
    @PostMapping("/tipo-actividad")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> crearTipoActividad(@Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoActividadService.crear(request));
    }

    /** Actualiza un tipo de actividad existente. Solo ADMIN. */
    @PutMapping("/tipo-actividad/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> actualizarTipoActividad(
            @PathVariable Long id,
            @Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.ok(tipoActividadService.actualizar(id, request));
    }

    /** Elimina un tipo de actividad. Solo ADMIN. */
    @DeleteMapping("/tipo-actividad/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarTipoActividad(@PathVariable Long id) {
        tipoActividadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────
    // MOTIVO INASISTENCIA
    // ─────────────────────────────────────────

    /** Retorna todos los motivos de inasistencia. */
    @GetMapping("/motivos-inasistencia")
    public ResponseEntity<List<CatalogoResponse>> listarMotivosInasistencia() {
        return ResponseEntity.ok(motivoInasistenciaService.listarTodos());
    }

    /** Crea un nuevo motivo de inasistencia. Solo ADMIN. */
    @PostMapping("/motivos-inasistencia")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> crearMotivoInasistencia(@Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(motivoInasistenciaService.crear(request));
    }

    /** Actualiza un motivo de inasistencia existente. Solo ADMIN. */
    @PutMapping("/motivos-inasistencia/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CatalogoResponse> actualizarMotivoInasistencia(
            @PathVariable Long id,
            @Valid @RequestBody CatalogoRequest request) {
        return ResponseEntity.ok(motivoInasistenciaService.actualizar(id, request));
    }

    /** Elimina un motivo de inasistencia. Solo ADMIN. */
    @DeleteMapping("/motivos-inasistencia/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarMotivoInasistencia(@PathVariable Long id) {
        motivoInasistenciaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────────────────
    // CARRERA
    // ─────────────────────────────────────────

    /** Retorna todas las carreras de ingeniería. */
    @GetMapping("/carreras")
    public ResponseEntity<List<CarreraResponse>> listarCarreras() {
        return ResponseEntity.ok(carreraService.listarTodas());
    }

    /** Retorna una carrera por su id. */
    @GetMapping("/carreras/{id}")
    public ResponseEntity<CarreraResponse> obtenerCarrera(@PathVariable Long id) {
        return ResponseEntity.ok(carreraService.buscarPorId(id));
    }

    /** Crea una nueva carrera. Solo ADMIN. */
    @PostMapping("/carreras")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarreraResponse> crearCarrera(@Valid @RequestBody CarreraRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraService.crear(request));
    }

    /** Actualiza una carrera existente. Solo ADMIN. */
    @PutMapping("/carreras/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarreraResponse> actualizarCarrera(
            @PathVariable Long id,
            @Valid @RequestBody CarreraRequest request) {
        return ResponseEntity.ok(carreraService.actualizar(id, request));
    }

    /** Elimina una carrera. Solo ADMIN. */
    @DeleteMapping("/carreras/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminarCarrera(@PathVariable Long id) {
        carreraService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
