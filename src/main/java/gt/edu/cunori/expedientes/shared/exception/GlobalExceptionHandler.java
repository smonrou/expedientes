package gt.edu.cunori.expedientes.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Manejador global de excepciones para toda la API REST.
 * Intercepta excepciones lanzadas en controllers y servicios,
 * y las transforma en respuestas JSON con el formato {@link ApiError}.
 *
 * <p>Excepciones manejadas:
 * <ul>
 *   <li>{@link ResourceNotFoundException} → 404 Not Found</li>
 *   <li>{@link BusinessException} → 409 Conflict</li>
 *   <li>{@link MethodArgumentNotValidException} → 400 Bad Request (con detalle por campo)</li>
 *   <li>{@link BadCredentialsException} → 401 Unauthorized</li>
 *   <li>{@link DisabledException} → 401 Unauthorized</li>
 *   <li>{@link AccessDeniedException} → 403 Forbidden</li>
 *   <li>{@link Exception} → 500 Internal Server Error (fallback)</li>
 * </ul>
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ── 404 Not Found ────────────────────────────────────────────────────────

    /**
     * Maneja recursos no encontrados en la base de datos.
     * Lanzado desde los servicios con {@link ResourceNotFoundException}.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(
            ResourceNotFoundException ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // ── 409 Conflict ─────────────────────────────────────────────────────────

    /**
     * Maneja violaciones de reglas de negocio.
     * Lanzado desde los servicios con {@link BusinessException}.
     * Ejemplos: carné duplicado, correo ya registrado, transición de estado inválida.
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // ── 400 Bad Request (Bean Validation) ────────────────────────────────────

    /**
     * Maneja errores de validación de DTOs anotados con {@code @Valid}.
     * Retorna la lista de todos los campos que fallaron la validación,
     * con su mensaje de error específico.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<ApiError.CampoError> erroresCampos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map((FieldError fe) -> new ApiError.CampoError(
                        fe.getField(),
                        fe.getDefaultMessage()
                ))
                .toList();

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "La solicitud contiene campos inválidos.",
                request.getRequestURI()
        );
        error.setErroresCampos(erroresCampos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // ── 401 Unauthorized ─────────────────────────────────────────────────────

    /**
     * Maneja credenciales incorrectas durante el login.
     * Lanzado por Spring Security cuando el correo o la contraseña no coinciden.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(
            BadCredentialsException ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Correo o contraseña incorrectos.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Maneja intentos de login de usuarios desactivados en el sistema.
     * Lanzado por {@link gt.edu.cunori.expedientes.security.UserDetailsServiceImpl}.
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> handleDisabledUser(
            DisabledException ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "La cuenta está desactivada. Contacte al administrador.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    // ── 403 Forbidden ────────────────────────────────────────────────────────

    /**
     * Maneja accesos a recursos sin el rol necesario.
     * Lanzado por Spring Security cuando el usuario está autenticado
     * pero no tiene permisos para el endpoint solicitado.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                "No tiene permisos para acceder a este recurso.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // ── 500 Internal Server Error (fallback) ─────────────────────────────────

    /**
     * Captura cualquier excepción no manejada explícitamente.
     * Evita que Spring retorne HTML de error en lugar de JSON.
     * En producción, el mensaje interno no se expone al cliente.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex, HttpServletRequest request) {
        ex.printStackTrace();//Solo para debuggear
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "Ocurrió un error inesperado. Intente de nuevo más tarde.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    
}