package gt.edu.cunori.expedientes.api.dto.notificacion;

/**
 * DTO de respuesta para el conteo de notificaciones no leídas.
 * Usado por el frontend para mostrar el badge en el ícono de notificaciones.
 */
public class ConteoNoLeidasResponse {

    private long total;

    public ConteoNoLeidasResponse() {
    }

    public ConteoNoLeidasResponse(long total) {
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}