package gt.edu.cunori.expedientes.api.controller;

import gt.edu.cunori.expedientes.api.dto.justificacion.CambioEstadoRequest;
import gt.edu.cunori.expedientes.api.dto.justificacion.JustificacionDtos;
import gt.edu.cunori.expedientes.api.dto.justificacion.JustificacionRequest;
import gt.edu.cunori.expedientes.domain.entity.DocumentoJustificacion;
import gt.edu.cunori.expedientes.domain.enums.EstadoJustificacion;
import gt.edu.cunori.expedientes.service.JustificacionService;
import jakarta.validation.Valid;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controlador REST para la gestión de justificaciones de inasistencia.
 *
 * Roles:
 * - ESTUDIANTE: presenta justificaciones y sube documentos a las suyas.
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
     * Retorna todas las justificaciones de un estudiante en formato resumen.
     */
    @GetMapping("/estudiante/{estudianteId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<List<JustificacionDtos.JustificacionResumenResponse>> listarPorEstudiante(
            @PathVariable Long estudianteId) {
        return ResponseEntity.ok(justificacionService.listarPorEstudiante(estudianteId));
    }

    /**
     * Retorna todas las justificaciones de una carrera con filtro opcional por
     * estado.
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
     * Solo ADMIN o el propio ESTUDIANTE.
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
     * Cambia el estado de una justificación siguiendo el flujo definido.
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
     * Sube un documento adjunto a una justificación como BLOB en la base de datos.
     * Recibe el archivo como multipart/form-data.
     * Solo ADMIN o el propio ESTUDIANTE.
     */
    @PostMapping(value = "/{justificacionId}/documentos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'ESTUDIANTE')")
    public ResponseEntity<JustificacionDtos.JustificacionResponse> subirDocumento(
            @PathVariable Long justificacionId,
            @RequestParam("archivo") MultipartFile archivo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(justificacionService.agregarDocumento(justificacionId, archivo));
    }

    /**
     * Descarga un documento adjunto desde la base de datos.
     * Retorna el contenido binario con el Content-Type y Content-Disposition
     * correctos.
     */
    @GetMapping("/documentos/{documentoId}/descargar")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR', 'ESTUDIANTE')")
    public ResponseEntity<byte[]> descargarDocumento(@PathVariable Long documentoId) {
        DocumentoJustificacion documento = justificacionService.descargarDocumento(documentoId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(documento.getTipoMime()));
        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename(documento.getNombreOriginal())
                        .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(documento.getContenido());
    }

    /**
     * Elimina un documento adjunto de una justificación.
     * Solo ADMIN o el propio ESTUDIANTE.
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