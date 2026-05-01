package gt.edu.cunori.expedientes.api.dto.estudiante;

import gt.edu.cunori.expedientes.domain.enums.Genero;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO de respuesta completo para el expediente estudiantil.
 * Incluye datos personales, académicos, médicos y contactos de emergencia.
 */
public class EstudianteResponse {

    private Long id;
    private Long usuarioId;
    private String nombreUsuario;
    private String correoUsuario;

    private String numeroCarne;
    private String nombres;
    private String apellidos;
    private String cui;
    private LocalDate fechaNacimiento;
    private Genero genero;
    private String correoInstitucional;
    private String correoPersonal;
    private Integer anioIngreso;
    private Boolean inscrito;
    private Boolean pensumCerrado;
    private LocalDate fechaCierrePensum;
    private String direccion;
    private String rutaFotografia;

    private Long carreraId;
    private String carreraNombre;

    private Long tipoSangreId;
    private String tipoSangreNombre;

    private List<EstudianteSubDtos.TelefonoResponse> telefonos = new ArrayList<>();
    private List<EstudianteSubDtos.CondicionMedicaResponse> condicionesMedicas = new ArrayList<>();
    private List<EstudianteSubDtos.AlergiaResponse> alergias = new ArrayList<>();
    private List<EstudianteSubDtos.DiscapacidadResponse> discapacidades = new ArrayList<>();
    private List<EstudianteSubDtos.ContactoEmergenciaResponse> contactosEmergencia = new ArrayList<>();

    public EstudianteResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getNumeroCarne() {
        return numeroCarne;
    }

    public void setNumeroCarne(String numeroCarne) {
        this.numeroCarne = numeroCarne;
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

    public Integer getAnioIngreso() {
        return anioIngreso;
    }

    public void setAnioIngreso(Integer anioIngreso) {
        this.anioIngreso = anioIngreso;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRutaFotografia() {
        return rutaFotografia;
    }

    public void setRutaFotografia(String rutaFotografia) {
        this.rutaFotografia = rutaFotografia;
    }

    public Long getCarreraId() {
        return carreraId;
    }

    public void setCarreraId(Long carreraId) {
        this.carreraId = carreraId;
    }

    public String getCarreraNombre() {
        return carreraNombre;
    }

    public void setCarreraNombre(String carreraNombre) {
        this.carreraNombre = carreraNombre;
    }

    public Long getTipoSangreId() {
        return tipoSangreId;
    }

    public void setTipoSangreId(Long tipoSangreId) {
        this.tipoSangreId = tipoSangreId;
    }

    public String getTipoSangreNombre() {
        return tipoSangreNombre;
    }

    public void setTipoSangreNombre(String tipoSangreNombre) {
        this.tipoSangreNombre = tipoSangreNombre;
    }

    public List<EstudianteSubDtos.TelefonoResponse> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(List<EstudianteSubDtos.TelefonoResponse> telefonos) {
        this.telefonos = telefonos;
    }

    public List<EstudianteSubDtos.CondicionMedicaResponse> getCondicionesMedicas() {
        return condicionesMedicas;
    }

    public void setCondicionesMedicas(List<EstudianteSubDtos.CondicionMedicaResponse> condicionesMedicas) {
        this.condicionesMedicas = condicionesMedicas;
    }

    public List<EstudianteSubDtos.AlergiaResponse> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<EstudianteSubDtos.AlergiaResponse> alergias) {
        this.alergias = alergias;
    }

    public List<EstudianteSubDtos.DiscapacidadResponse> getDiscapacidades() {
        return discapacidades;
    }

    public void setDiscapacidades(List<EstudianteSubDtos.DiscapacidadResponse> discapacidades) {
        this.discapacidades = discapacidades;
    }

    public List<EstudianteSubDtos.ContactoEmergenciaResponse> getContactosEmergencia() {
        return contactosEmergencia;
    }

    public void setContactosEmergencia(List<EstudianteSubDtos.ContactoEmergenciaResponse> contactosEmergencia) {
        this.contactosEmergencia = contactosEmergencia;
    }
}