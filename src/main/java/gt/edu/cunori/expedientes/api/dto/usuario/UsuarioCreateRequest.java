package gt.edu.cunori.expedientes.api.dto.usuario;

import gt.edu.cunori.expedientes.domain.enums.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear un nuevo usuario.
 * La contraseña se recibe en texto plano y se hashea en el servicio.
 */
public class UsuarioCreateRequest {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 60, message = "El nombre de usuario no puede superar los 60 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 150, message = "El correo no puede superar los 150 caracteres")
    private String correo;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;

    public UsuarioCreateRequest() {}

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}