package gt.edu.cunori.expedientes.api.dto.justificacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO de solicitud para presentar una nueva justificación de inasistencia.
 * El estudiante indica el motivo, descripción y las fechas que desea justificar.
 * Los documentos se adjuntan por separado mediante un endpoint dedicado.
 */
public class JustificacionRequest {

    @NotNull(message = "El id del motivo es obligatorio")
    private Long motivoId;

    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    @NotEmpty(message = "Debe indicar al menos una fecha de inasistencia")
    private List<LocalDate> fechas;

    public JustificacionRequest() {}

    public Long getMotivoId() { return motivoId; }
    public void setMotivoId(Long motivoId) { this.motivoId = motivoId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<LocalDate> getFechas() { return fechas; }
    public void setFechas(List<LocalDate> fechas) { this.fechas = fechas; }
}