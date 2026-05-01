package gt.edu.cunori.expedientes.api.dto.actividad;

import java.time.LocalDate;

/**
 * DTO de respuesta para una actividad extracurricular.
 */
public class ActividadResponse {

    private Long id;
    private Long estudianteId;
    private Long tipoActividadId;
    private String tipoActividadNombre;
    private String nombre;
    private String institucion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String observaciones;

    public ActividadResponse() {
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

    public Long getTipoActividadId() {
        return tipoActividadId;
    }

    public void setTipoActividadId(Long tipoActividadId) {
        this.tipoActividadId = tipoActividadId;
    }

    public String getTipoActividadNombre() {
        return tipoActividadNombre;
    }

    public void setTipoActividadNombre(String tipoActividadNombre) {
        this.tipoActividadNombre = tipoActividadNombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}