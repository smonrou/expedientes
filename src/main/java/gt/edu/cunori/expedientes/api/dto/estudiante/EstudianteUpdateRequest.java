package gt.edu.cunori.expedientes.api.dto.estudiante;

import gt.edu.cunori.expedientes.domain.enums.Genero;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/**
 * DTO de solicitud para actualizar los datos de un estudiante existente.
 * No incluye campos de acceso (usuario/contraseña) ni sub-entidades —
 * esas se gestionan por endpoints separados.
 */
public class EstudianteUpdateRequest {

    @NotNull(message = "El id de carrera es obligatorio")
    private Long carreraId;

    private Long tipoSangreId;

    @NotBlank(message = "Los nombres no pueden estar vacíos")
    @Size(max = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 100)
    private String apellidos;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El género es obligatorio")
    private Genero genero;

    @NotBlank(message = "El correo institucional no puede estar vacío")
    @Email
    @Size(max = 150)
    private String correoInstitucional;

    @Email
    @Size(max = 150)
    private String correoPersonal;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(max = 255)
    private String direccion;

    @NotNull(message = "El campo inscrito es obligatorio")
    private Boolean inscrito;

    @NotNull(message = "El campo pensum cerrado es obligatorio")
    private Boolean pensumCerrado;

    private LocalDate fechaCierrePensum;

    @Size(max = 255)
    private String rutaFotografia;

    public EstudianteUpdateRequest() {
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public Long getTipoSangreId() {
        return tipoSangreId;
    }

    public void setTipoSangreId(Long tipoSangreId) {
        this.tipoSangreId = tipoSangreId;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getCorreoInstitucional() {
        return correoInstitucional;
    }

    public void setCorreoInstitucional(String correoInstitucional) {
        this.correoInstitucional = correoInstitucional;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean getInscrito() {
        return inscrito;
    }

    public void setInscrito(Boolean inscrito) {
        this.inscrito = inscrito;
    }

    public Boolean getPensumCerrado() {
        return pensumCerrado;
    }

    public void setPensumCerrado(Boolean pensumCerrado) {
        this.pensumCerrado = pensumCerrado;
    }

    public LocalDate getFechaCierrePensum() {
        return fechaCierrePensum;
    }

    public void setFechaCierrePensum(LocalDate fechaCierrePensum) {
        this.fechaCierrePensum = fechaCierrePensum;
    }

    public String getRutaFotografia() {
        return rutaFotografia;
    }

    public void setRutaFotografia(String rutaFotografia) {
        this.rutaFotografia = rutaFotografia;
    }
}