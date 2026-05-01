package gt.edu.cunori.expedientes.api.dto.notificacion;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para una notificación interna del sistema.
 */
public class NotificacionResponse {

    private Long id;
    private Long destinatarioId;
    private Long justificacionId;
    private String mensaje;
    private Boolean leida;
    private LocalDateTime creadaEn;

    public NotificacionResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(Long destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public Long getJustificacionId() {
        return justificacionId;
    }

    public void setJustificacionId(Long justificacionId) {
        this.justificacionId = justificacionId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public LocalDateTime getCreadaEn() {
        return creadaEn;
    }

    public void setCreadaEn(LocalDateTime creadaEn) {
        this.creadaEn = creadaEn;
    }
}