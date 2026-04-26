package gt.edu.cunori.expedientes.shared.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Estructura estándar de respuesta para todos los errores de la API.
 * Todos los endpoints retornan este objeto cuando ocurre un error,
 * garantizando un formato consistente para el frontend.
 *
 * <p>Ejemplo de respuesta:</p>
 * <pre>
 * {
 *   "timestamp": "2025-05-01T10:30:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "mensaje": "Estudiante no encontrado con id: 99",
 *   "ruta": "/api/estudiantes/99"
 * }
 * </pre>
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // Omite campos nulos en el JSON
public class ApiError {

    /** Fecha y hora en que ocurrió el error. */
    private LocalDateTime timestamp;

    /** Código HTTP del error (400, 401, 403, 404, 409, 422, 500...). */
    private int status;

    /** Descripción corta del tipo de error (ej. "Not Found", "Bad Request"). */
    private String error;

    /** Mensaje legible para el usuario o el desarrollador frontend. */
    private String mensaje;

    /** Ruta del endpoint que generó el error. */
    private String ruta;

    /**
     * Lista de errores de validación de campos.
     * Solo se incluye cuando el error es 400 (Bad Request) por Bean Validation.
     */
    private List<CampoError> erroresCampos;

    // ── Constructores ────────────────────────────────────────────────────────

    public ApiError() {
        this.timestamp = LocalDateTime.now();
    }

    public ApiError(int status, String error, String mensaje, String ruta) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.ruta = ruta;
    }

    // ── Clase interna para errores de validación por campo ───────────────────

    /**
     * Representa el error de validación de un campo específico del request body.
     * Se incluye en la lista {@code erroresCampos} cuando falla Bean Validation.
     */
    public static class CampoError {

        /** Nombre del campo que falló la validación (ej. "correo", "carne"). */
        private String campo;

        /** Mensaje descriptivo del error (ej. "no debe estar vacío"). */
        private String mensaje;

        public CampoError(String campo, String mensaje) {
            this.campo = campo;
            this.mensaje = mensaje;
        }

        public String getCampo() { return campo; }
        public String getMensaje() { return mensaje; }
    }

    // ── Getters y Setters ────────────────────────────────────────────────────

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getError() { return error; }
    public void setError(String error) { this.error = error; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getRuta() { return ruta; }
    public void setRuta(String ruta) { this.ruta = ruta; }

    public List<CampoError> getErroresCampos() { return erroresCampos; }
    public void setErroresCampos(List<CampoError> erroresCampos) { this.erroresCampos = erroresCampos; }
}