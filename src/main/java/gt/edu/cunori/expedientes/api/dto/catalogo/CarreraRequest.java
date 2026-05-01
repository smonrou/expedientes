package gt.edu.cunori.expedientes.api.dto.catalogo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para crear o actualizar una Carrera.
 * Incluye todos los campos de la entidad Carrera.
 */
public class CarreraRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
    private String nombre;

    @NotBlank(message = "El código no puede estar vacío")
    @Size(max = 20, message = "El código no puede superar los 20 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre del coordinador no puede estar vacío")
    @Size(max = 150, message = "El nombre del coordinador no puede superar los 150 caracteres")
    private String nombreCoordinador;

    public CarreraRequest() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreCoordinador() {
        return nombreCoordinador;
    }

    public void setNombreCoordinador(String nombreCoordinador) {
        this.nombreCoordinador = nombreCoordinador;
    }
}