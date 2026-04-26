package gt.edu.cunori.expedientes.api.dto.justificacion;

import gt.edu.cunori.expedientes.domain.enums.EstadoJustificacion;
import jakarta.validation.constraints.NotNull;

/**
 * DTO de solicitud para que el coordinador cambie el estado de una justificación.
 * Solo permite transiciones válidas según el flujo definido:
 * PRESENTADA → EN_REVISION → APROBADA | RECHAZADA
 */
public class CambioEstadoRequest {

    @NotNull(message = "El nuevo estado es obligatorio")
    private EstadoJustificacion nuevoEstado;

    public CambioEstadoRequest() {}

    public EstadoJustificacion getNuevoEstado() { return nuevoEstado; }
    public void setNuevoEstado(EstadoJustificacion nuevoEstado) { this.nuevoEstado = nuevoEstado; }
}