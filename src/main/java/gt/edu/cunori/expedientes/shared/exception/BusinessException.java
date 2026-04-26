package gt.edu.cunori.expedientes.shared.exception;

/**
 * Excepción lanzada cuando se viola una regla de negocio del sistema.
 * Resulta en una respuesta HTTP 409 Conflict.
 *
 * <p>Ejemplos de uso:</p>
 * <pre>
 *   // Carné duplicado
 *   if (estudianteRepository.existsByCarne(dto.getCarne())) {
 *       throw new BusinessException("Ya existe un estudiante con el carné: " + dto.getCarne());
 *   }
 *
 *   // Transición de estado inválida
 *   if (justificacion.getEstado() != EstadoJustificacion.PRESENTADA) {
 *       throw new BusinessException("Solo se pueden revisar justificaciones en estado PRESENTADA.");
 *   }
 * </pre>
 */
public class BusinessException extends RuntimeException {

    /**
     * @param mensaje descripción clara del conflicto de negocio
     */
    public BusinessException(String mensaje) {
        super(mensaje);
    }
}