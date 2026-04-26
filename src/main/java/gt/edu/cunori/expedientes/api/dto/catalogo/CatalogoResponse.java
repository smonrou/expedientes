package gt.edu.cunori.expedientes.api.dto.catalogo;

/**
 * DTO de respuesta genérico para catálogos simples (id + nombre).
 * Usado por: TipoSangre, Alergia, TipoDiscapacidad, TipoActividad, MotivoInasistencia.
 */
public class CatalogoResponse {

    private Integer id;
    private String nombre;

    public CatalogoResponse() {}

    public CatalogoResponse(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
