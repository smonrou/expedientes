package gt.edu.cunori.expedientes.api.controller;

import gt.edu.cunori.expedientes.api.dto.auth.LoginRequest;
import gt.edu.cunori.expedientes.api.dto.auth.LoginResponse;
import gt.edu.cunori.expedientes.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller para autenticación de usuarios.
 * Ruta base: {@code /api/auth}
 *
 * <p>
 * Endpoints:
 * <ul>
 * <li>{@code POST /api/auth/login} — autentica y retorna token JWT</li>
 * <li>{@code GET  /api/auth/debug-usuario} — TEMPORAL: verifica usuario en
 * BD</li>
 * <li>{@code GET  /api/auth/hash} — TEMPORAL: genera hash BCrypt</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Autentica un usuario con correo y contraseña.
     * Retorna el token JWT y los datos básicos del usuario si las credenciales son
     * válidas.
     *
     * @param request body con {@code correo} y {@code contrasena}
     * @return 200 OK con {@link LoginResponse} — token + datos del usuario
     *         400 Bad Request si el body no supera la validación
     *         401 Unauthorized si las credenciales son incorrectas
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}