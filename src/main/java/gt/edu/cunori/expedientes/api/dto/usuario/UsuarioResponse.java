package gt.edu.cunori.expedientes.api.dto.usuario;

import gt.edu.cunori.expedientes.domain.enums.Rol;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para la entidad Usuario.
 * No expone la contraseña hash.
 */
public class UsuarioResponse {

    private Long id;
    private String nombreUsuario;
    private String correo;
    private Rol rol;
    private Boolean activo;
    private LocalDateTime creadoEn;

    public UsuarioResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(LocalDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}