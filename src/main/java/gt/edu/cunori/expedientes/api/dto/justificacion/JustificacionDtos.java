package gt.edu.cunori.expedientes.api.dto.justificacion;

import gt.edu.cunori.expedientes.domain.enums.EstadoJustificacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTOs de respuesta para el módulo de justificaciones de inasistencia.
 * Se agrupan aquí para reducir el número de archivos.
 */
public class JustificacionDtos {

    // ─────────────────────────────────────────
    // RESPUESTA COMPLETA
    // ─────────────────────────────────────────

    /**
     * DTO de respuesta completo para una justificación de inasistencia.
     * Incluye fechas justificadas y documentos adjuntos.
     */
    public static class JustificacionResponse {

        private Long id;
        private Long estudianteId;
        private String estudianteNombre;
        private String estudianteNumeroCarne;
        private Long motivoId;
        private String motivoNombre;
        private String descripcion;
        private EstadoJustificacion estado;
        private LocalDateTime fechaPresentacion;
        private LocalDateTime fechaRevision;
        private Long revisadoPorId;
        private String revisadoPorNombre;
        private List<FechaInasistenciaResponse> fechas = new ArrayList<>();
        private List<DocumentoResponse> documentos = new ArrayList<>();

        public JustificacionResponse() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getEstudianteId() {
            return estudianteId;
        }

        public void setEstudianteId(Long estudianteId) {
            this.estudianteId = estudianteId;
        }

        public String getEstudianteNombre() {
            return estudianteNombre;
        }

        public void setEstudianteNombre(String estudianteNombre) {
            this.estudianteNombre = estudianteNombre;
        }

        public String getEstudianteNumeroCarne() {
            return estudianteNumeroCarne;
        }

        public void setEstudianteNumeroCarne(String estudianteNumeroCarne) {
            this.estudianteNumeroCarne = estudianteNumeroCarne;
        }

        public Long getMotivoId() {
            return motivoId;
        }

        public void setMotivoId(Long motivoId) {
            this.motivoId = motivoId;
        }

        public String getMotivoNombre() {
            return motivoNombre;
        }

        public void setMotivoNombre(String motivoNombre) {
            this.motivoNombre = motivoNombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public EstadoJustificacion getEstado() {
            return estado;
        }

        public void setEstado(EstadoJustificacion estado) {
            this.estado = estado;
        }

        public LocalDateTime getFechaPresentacion() {
            return fechaPresentacion;
        }

        public void setFechaPresentacion(LocalDateTime fechaPresentacion) {
            this.fechaPresentacion = fechaPresentacion;
        }

        public LocalDateTime getFechaRevision() {
            return fechaRevision;
        }

        public void setFechaRevision(LocalDateTime fechaRevision) {
            this.fechaRevision = fechaRevision;
        }

        public Long getRevisadoPorId() {
            return revisadoPorId;
        }

        public void setRevisadoPorId(Long revisadoPorId) {
            this.revisadoPorId = revisadoPorId;
        }

        public String getRevisadoPorNombre() {
            return revisadoPorNombre;
        }

        public void setRevisadoPorNombre(String revisadoPorNombre) {
            this.revisadoPorNombre = revisadoPorNombre;
        }

        public List<FechaInasistenciaResponse> getFechas() {
            return fechas;
        }

        public void setFechas(List<FechaInasistenciaResponse> fechas) {
            this.fechas = fechas;
        }

        public List<DocumentoResponse> getDocumentos() {
            return documentos;
        }

        public void setDocumentos(List<DocumentoResponse> documentos) {
            this.documentos = documentos;
        }
    }

    // ─────────────────────────────────────────
    // RESUMEN PARA LISTADOS
    // ─────────────────────────────────────────

    /**
     * DTO de respuesta resumido para listados de justificaciones.
     */
    public static class JustificacionResumenResponse {

        private Long id;
        private Long estudianteId;
        private String estudianteNombre;
        private String estudianteNumeroCarne;
        private String motivoNombre;
        private EstadoJustificacion estado;
        private LocalDateTime fechaPresentacion;
        private int totalFechas;

        public JustificacionResumenResponse() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getEstudianteId() {
            return estudianteId;
        }

        public void setEstudianteId(Long estudianteId) {
            this.estudianteId = estudianteId;
        }

        public String getEstudianteNombre() {
            return estudianteNombre;
        }

        public void setEstudianteNombre(String estudianteNombre) {
            this.estudianteNombre = estudianteNombre;
        }

        public String getEstudianteNumeroCarne() {
            return estudianteNumeroCarne;
        }

        public void setEstudianteNumeroCarne(String estudianteNumeroCarne) {
            this.estudianteNumeroCarne = estudianteNumeroCarne;
        }

        public String getMotivoNombre() {
            return motivoNombre;
        }

        public void setMotivoNombre(String motivoNombre) {
            this.motivoNombre = motivoNombre;
        }

        public EstadoJustificacion getEstado() {
            return estado;
        }

        public void setEstado(EstadoJustificacion estado) {
            this.estado = estado;
        }

        public LocalDateTime getFechaPresentacion() {
            return fechaPresentacion;
        }

        public void setFechaPresentacion(LocalDateTime fechaPresentacion) {
            this.fechaPresentacion = fechaPresentacion;
        }

        public int getTotalFechas() {
            return totalFechas;
        }

        public void setTotalFechas(int totalFechas) {
            this.totalFechas = totalFechas;
        }
    }

    // ─────────────────────────────────────────
    // FECHA INASISTENCIA
    // ─────────────────────────────────────────

    /** DTO de respuesta para una fecha de inasistencia. */
    public static class FechaInasistenciaResponse {
        private Long id;
        private LocalDate fecha;

        public FechaInasistenciaResponse() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public LocalDate getFecha() {
            return fecha;
        }

        public void setFecha(LocalDate fecha) {
            this.fecha = fecha;
        }
    }

    // ─────────────────────────────────────────
    // DOCUMENTO
    // ─────────────────────────────────────────

    /**
     * DTO de respuesta para un documento adjunto.
     * No incluye el contenido binario — ese se sirve por el endpoint de descarga.
     */
    public static class DocumentoResponse {
        private Long id;
        private String nombreOriginal;
        private String tipoMime;
        private LocalDateTime subidoEn;

        public DocumentoResponse() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombreOriginal() {
            return nombreOriginal;
        }

        public void setNombreOriginal(String nombreOriginal) {
            this.nombreOriginal = nombreOriginal;
        }

        public String getTipoMime() {
            return tipoMime;
        }

        public void setTipoMime(String tipoMime) {
            this.tipoMime = tipoMime;
        }

        public LocalDateTime getSubidoEn() {
            return subidoEn;
        }

        public void setSubidoEn(LocalDateTime subidoEn) {
            this.subidoEn = subidoEn;
        }
    }
}