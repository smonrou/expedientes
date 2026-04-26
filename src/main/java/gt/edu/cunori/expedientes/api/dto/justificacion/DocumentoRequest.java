package gt.edu.cunori.expedientes.api.dto.justificacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO de solicitud para registrar un documento adjunto en una justificación.
 * Almacena la ruta del archivo y el nombre original.
 * La subida física del archivo se maneja por separado con FileStorageService.
 */
public class DocumentoRequest {

    @NotBlank(message = "La ruta del archivo no puede estar vacía")
    @Size(max = 255)
    private String rutaArchivo;

    @NotBlank(message = "El nombre original no puede estar vacío")
    @Size(max = 255)
    private String nombreOriginal;

    public DocumentoRequest() {}

    public String getRutaArchivo() { return rutaArchivo; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }

    public String getNombreOriginal() { return nombreOriginal; }
    public void setNombreOriginal(String nombreOriginal) { this.nombreOriginal = nombreOriginal; }
}