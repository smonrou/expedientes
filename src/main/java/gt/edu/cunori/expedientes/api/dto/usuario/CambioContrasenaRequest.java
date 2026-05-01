package gt.edu.cunori.expedientes.api.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para cambiar la contraseña de un usuario.
 * Se usa tanto por el ADMIN (reset) como por el propio usuario.
 */
public class CambioContrasenaRequest {

    @NotBlank(message = "La nueva contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String nuevaContrasena;

    public CambioContrasenaRequest() {
    }

    public String getNuevaContrasena() {
        return nuevaContrasena;
    }

    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }
}