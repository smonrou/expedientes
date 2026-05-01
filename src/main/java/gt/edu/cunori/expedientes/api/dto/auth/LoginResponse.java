package gt.edu.cunori.expedientes.api.dto.auth;

/**
 * DTO de respuesta tras un login exitoso.
 * Contiene el token JWT y los datos básicos del usuario autenticado
 * para que el frontend pueda inicializar la sesión sin una llamada extra.
 */
public class LoginResponse {

    /** Token JWT firmado. El frontend lo almacena y lo envía en cada request. */
    private String token;

    /** Tipo de token — siempre "Bearer" para JWT. */
    private String tipo = "Bearer";

    /** Correo electrónico del usuario autenticado. */
    private String correo;

    /** Nombre de usuario del usuario autenticado. */
    private String nombreUsuario;

    /** Rol del usuario: ADMIN, COORDINADOR o ESTUDIANTE. */
    private String rol;

    /**
     * ID del usuario autenticado.
     * El frontend lo usa para construir rutas como /api/estudiantes/{id}.
     */
    private Integer usuarioId;

    // ── Constructor ──────────────────────────────────────────────────────────

    public LoginResponse(String token, String correo, String nombreUsuario,
            String rol, Integer usuarioId) {
        this.token = token;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.usuarioId = usuarioId;
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getRol() {
        return rol;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }
}