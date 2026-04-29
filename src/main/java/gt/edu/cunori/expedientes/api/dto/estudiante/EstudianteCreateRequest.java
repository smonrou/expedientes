package gt.edu.cunori.expedientes.api.dto.estudiante;

import gt.edu.cunori.expedientes.domain.enums.Genero;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO de solicitud para crear un nuevo expediente estudiantil.
 * Incluye los datos personales, académicos y médicos del estudiante.
 * El usuario asociado se crea automáticamente en el servicio.
 */
public class EstudianteCreateRequest {

    // ── Datos de acceso ──────────────────────
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 60)
    private String nombreUsuario;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasena;

    // ── Datos académicos ─────────────────────
    @NotBlank(message = "El número de carné no puede estar vacío")
    @Size(max = 12)
    private String numeroCarne;

    @NotNull(message = "El id de carrera es obligatorio")
    private Long carreraId;

    @NotNull(message = "El año de ingreso es obligatorio")
    @Min(value = 2000, message = "El año de ingreso no es válido")
    private Integer anioIngreso;

    private Long tipoSangreId;

    // ── Datos personales ─────────────────────
    @NotBlank(message = "Los nombres no pueden estar vacíos")
    @Size(max = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos no pueden estar vacíos")
    @Size(max = 100)
    private String apellidos;

    @NotBlank(message = "El CUI no puede estar vacío")
    @Size(min = 13, max = 13, message = "El CUI debe tener exactamente 13 caracteres")
    private String cui;

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

    @Size(max = 255)
    private Boolean pensumCerrado = false;

    private LocalDate fechaCierrePensum;

    @Size(max = 255)
    private String rutaFotografia;

    // ── Sub-entidades opcionales ─────────────
    @Valid
    private List<EstudianteSubDtos.TelefonoRequest> telefonos = new ArrayList<>();

    @Valid
    private List<EstudianteSubDtos.CondicionMedicaRequest> condicionesMedicas = new ArrayList<>();

    @Valid
    private List<EstudianteSubDtos.AlergiaRequest> alergias = new ArrayList<>();

    @Valid
    private List<EstudianteSubDtos.DiscapacidadRequest> discapacidades = new ArrayList<>();

    @Valid
    private List<EstudianteSubDtos.ContactoEmergenciaRequest> contactosEmergencia = new ArrayList<>();

    public EstudianteCreateRequest() {
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNumeroCarne() {
        return numeroCarne;
    }

    public void setNumeroCarne(String numeroCarne) {
        this.numeroCarne = numeroCarne;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
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

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
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

    public List<EstudianteSubDtos.TelefonoRequest> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<EstudianteSubDtos.TelefonoRequest> telefonos) {
        this.telefonos = telefonos;
    }

    public List<EstudianteSubDtos.CondicionMedicaRequest> getCondicionesMedicas() {
        return condicionesMedicas;
    }

    public void setCondicionesMedicas(List<EstudianteSubDtos.CondicionMedicaRequest> condicionesMedicas) {
        this.condicionesMedicas = condicionesMedicas;
    }

    public List<EstudianteSubDtos.AlergiaRequest> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<EstudianteSubDtos.AlergiaRequest> alergias) {
        this.alergias = alergias;
    }

    public List<EstudianteSubDtos.DiscapacidadRequest> getDiscapacidades() {
        return discapacidades;
    }

    public void setDiscapacidades(List<EstudianteSubDtos.DiscapacidadRequest> discapacidades) {
        this.discapacidades = discapacidades;
    }

    public List<EstudianteSubDtos.ContactoEmergenciaRequest> getContactosEmergencia() {
        return contactosEmergencia;
    }

    public void setContactosEmergencia(List<EstudianteSubDtos.ContactoEmergenciaRequest> contactosEmergencia) {
        this.contactosEmergencia = contactosEmergencia;
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