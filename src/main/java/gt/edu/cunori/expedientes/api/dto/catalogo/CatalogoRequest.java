package gt.edu.cunori.expedientes.api.dto.catalogo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud genérico para catálogos simples (solo campo nombre).
 * Usado por: TipoSangre, Alergia, TipoDiscapacidad, TipoActividad, MotivoInasistencia.
 */
public class CatalogoRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String nombre;

    public CatalogoRequest() {}

    public CatalogoRequest(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}