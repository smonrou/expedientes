package gt.edu.cunori.expedientes.api.dto.catalogo;

/**
 * DTO de respuesta para la entidad Carrera.
 */
public class CarreraResponse {

    private Integer id;
    private String nombre;
    private String codigo;
    private String nombreCoordinador;

    public CarreraResponse() {}

    public CarreraResponse(Integer id, String nombre, String codigo, String nombreCoordinador) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.nombreCoordinador = nombreCoordinador;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombreCoordinador() { return nombreCoordinador; }
    public void setNombreCoordinador(String nombreCoordinador) { this.nombreCoordinador = nombreCoordinador; }
}