package gt.edu.cunori.expedientes.api.dto.actividad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * DTO de solicitud para crear o actualizar una actividad extracurricular.
 */
public class ActividadRequest {

    @NotNull(message = "El id del tipo de actividad es obligatorio")
    private Long tipoActividadId;

    @NotBlank(message = "El nombre de la actividad no puede estar vacío")
    @Size(max = 200, message = "El nombre no puede superar los 200 caracteres")
    private String nombre;

    @Size(max = 200, message = "La institución no puede superar los 200 caracteres")
    private String institucion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String observaciones;

    public ActividadRequest() {}

    public Long getTipoActividadId() { return tipoActividadId; }
    public void setTipoActividadId(Long tipoActividadId) { this.tipoActividadId = tipoActividadId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getInstitucion() { return institucion; }
    public void setInstitucion(String institucion) { this.institucion = institucion; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}