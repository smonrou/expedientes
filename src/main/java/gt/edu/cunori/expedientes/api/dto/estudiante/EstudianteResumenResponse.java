package gt.edu.cunori.expedientes.api.dto.estudiante;

/**
 * DTO de respuesta resumido para listados y búsquedas de estudiantes.
 * No incluye sub-entidades para mantener respuestas ligeras en consultas masivas.
 */
public class EstudianteResumenResponse {

    private Long id;
    private String numeroCarne;
    private String nombres;
    private String apellidos;
    private String correoInstitucional;
    private String carreraNombre;
    private Boolean inscrito;
    private Integer anioIngreso;

    public EstudianteResumenResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroCarne() { return numeroCarne; }
    public void setNumeroCarne(String numeroCarne) { this.numeroCarne = numeroCarne; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreoInstitucional() { return correoInstitucional; }
    public void setCorreoInstitucional(String correoInstitucional) { this.correoInstitucional = correoInstitucional; }

    public String getCarreraNombre() { return carreraNombre; }
    public void setCarreraNombre(String carreraNombre) { this.carreraNombre = carreraNombre; }

    public Boolean getInscrito() { return inscrito; }
    public void setInscrito(Boolean inscrito) { this.inscrito = inscrito; }

    public Integer getAnioIngreso() { return anioIngreso; }
    public void setAnioIngreso(Integer anioIngreso) { this.anioIngreso = anioIngreso; }
}