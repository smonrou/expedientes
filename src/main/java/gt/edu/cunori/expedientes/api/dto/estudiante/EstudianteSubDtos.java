package gt.edu.cunori.expedientes.api.dto.estudiante;

import gt.edu.cunori.expedientes.domain.enums.TipoTelefono;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTOs anidados para las sub-entidades del expediente estudiantil.
 * Se agrupan aquí para reducir el número de archivos.
 */
public class EstudianteSubDtos {

    // ─────────────────────────────────────────
    // TELÉFONO
    // ─────────────────────────────────────────

    /** DTO de solicitud para un teléfono del estudiante. */
    public static class TelefonoRequest {

        @NotBlank(message = "El número de teléfono no puede estar vacío")
        @Size(max = 20, message = "El número no puede superar los 20 caracteres")
        private String numero;

        @NotNull(message = "El tipo de teléfono es obligatorio")
        private TipoTelefono tipo;

        public TelefonoRequest() {}

        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }

        public TipoTelefono getTipo() { return tipo; }
        public void setTipo(TipoTelefono tipo) { this.tipo = tipo; }
    }

    /** DTO de respuesta para un teléfono del estudiante. */
    public static class TelefonoResponse {
        private Long id;
        private String numero;
        private TipoTelefono tipo;

        public TelefonoResponse() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNumero() { return numero; }
        public void setNumero(String numero) { this.numero = numero; }

        public TipoTelefono getTipo() { return tipo; }
        public void setTipo(TipoTelefono tipo) { this.tipo = tipo; }
    }

    // ─────────────────────────────────────────
    // CONDICIÓN MÉDICA
    // ─────────────────────────────────────────

    /** DTO de solicitud para una condición médica. */
    public static class CondicionMedicaRequest {

        @NotBlank(message = "La descripción no puede estar vacía")
        @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
        private String descripcion;

        public CondicionMedicaRequest() {}

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }

    /** DTO de respuesta para una condición médica. */
    public static class CondicionMedicaResponse {
        private Long id;
        private String descripcion;

        public CondicionMedicaResponse() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    }

    // ─────────────────────────────────────────
    // ALERGIA
    // ─────────────────────────────────────────

    /** DTO de solicitud para asociar una alergia al estudiante. */
    public static class AlergiaRequest {

        @NotNull(message = "El id de la alergia es obligatorio")
        private Long alergiaId;

        @Size(max = 255, message = "Las observaciones no pueden superar los 255 caracteres")
        private String observaciones;

        public AlergiaRequest() {}

        public Long getAlergiaId() { return alergiaId; }
        public void setAlergiaId(Long alergiaId) { this.alergiaId = alergiaId; }

        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }

    /** DTO de respuesta para una alergia del estudiante. */
    public static class AlergiaResponse {
        private Long alergiaId;
        private String nombre;
        private String observaciones;

        public AlergiaResponse() {}

        public Long getAlergiaId() { return alergiaId; }
        public void setAlergiaId(Long alergiaId) { this.alergiaId = alergiaId; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }

    // ─────────────────────────────────────────
    // DISCAPACIDAD
    // ─────────────────────────────────────────

    /** DTO de solicitud para asociar una discapacidad al estudiante. */
    public static class DiscapacidadRequest {

        @NotNull(message = "El id del tipo de discapacidad es obligatorio")
        private Long tipoDiscapacidadId;

        @Size(max = 255, message = "Las observaciones no pueden superar los 255 caracteres")
        private String observaciones;

        public DiscapacidadRequest() {}

        public Long getTipoDiscapacidadId() { return tipoDiscapacidadId; }
        public void setTipoDiscapacidadId(Long tipoDiscapacidadId) { this.tipoDiscapacidadId = tipoDiscapacidadId; }

        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }

    /** DTO de respuesta para una discapacidad del estudiante. */
    public static class DiscapacidadResponse {
        private Long tipoDiscapacidadId;
        private String nombre;
        private String observaciones;

        public DiscapacidadResponse() {}

        public Long getTipoDiscapacidadId() { return tipoDiscapacidadId; }
        public void setTipoDiscapacidadId(Long tipoDiscapacidadId) { this.tipoDiscapacidadId = tipoDiscapacidadId; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }

    // ─────────────────────────────────────────
    // CONTACTO DE EMERGENCIA
    // ─────────────────────────────────────────

    /** DTO de solicitud para un contacto de emergencia. */
    public static class ContactoEmergenciaRequest {

        @NotBlank(message = "El nombre completo no puede estar vacío")
        @Size(max = 150, message = "El nombre no puede superar los 150 caracteres")
        private String nombreCompleto;

        @NotBlank(message = "El parentesco no puede estar vacío")
        @Size(max = 80, message = "El parentesco no puede superar los 80 caracteres")
        private String parentesco;

        @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
        private String direccion;

        public ContactoEmergenciaRequest() {}

        public String getNombreCompleto() { return nombreCompleto; }
        public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

        public String getParentesco() { return parentesco; }
        public void setParentesco(String parentesco) { this.parentesco = parentesco; }

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
    }

    /** DTO de respuesta para un contacto de emergencia. */
    public static class ContactoEmergenciaResponse {
        private Long id;
        private String nombreCompleto;
        private String parentesco;
        private String direccion;

        public ContactoEmergenciaResponse() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getNombreCompleto() { return nombreCompleto; }
        public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

        public String getParentesco() { return parentesco; }
        public void setParentesco(String parentesco) { this.parentesco = parentesco; }

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }
    }
}
