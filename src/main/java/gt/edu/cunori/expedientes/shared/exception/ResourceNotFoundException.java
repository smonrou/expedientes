package gt.edu.cunori.expedientes.shared.exception;

/**
 * Excepción lanzada cuando un recurso solicitado no existe en la base de datos.
 * Resulta en una respuesta HTTP 404 Not Found.
 *
 * <p>Uso típico en servicios:</p>
 * <pre>
 *   Estudiante estudiante = estudianteRepository.findById(id)
 *       .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", id));
 * </pre>
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construye el mensaje con el formato: "{recurso} no encontrado con {campo}: {valor}"
     *
     * @param recurso nombre de la entidad (ej. "Estudiante", "Carrera")
     * @param campo   nombre del campo buscado (ej. "id", "carné")
     * @param valor   valor que no se encontró
     */
    public ResourceNotFoundException(String recurso, String campo, Object valor) {
        super(String.format("%s no encontrado con %s: %s", recurso, campo, valor));
    }
}