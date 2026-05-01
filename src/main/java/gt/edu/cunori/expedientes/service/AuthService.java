package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.auth.LoginRequest;
import gt.edu.cunori.expedientes.api.dto.auth.LoginResponse;
import gt.edu.cunori.expedientes.domain.entity.Usuario;
import gt.edu.cunori.expedientes.domain.repository.UsuarioRepository;
import gt.edu.cunori.expedientes.security.JwtUtil;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación del sistema.
 * Delega la verificación de credenciales a Spring Security y genera el token
 * JWT
 * una vez confirmada la identidad del usuario.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

        private final AuthenticationManager authenticationManager;
        private final UsuarioRepository usuarioRepository;
        private final JwtUtil jwtUtil;

        /**
         * Autentica al usuario con su correo y contraseña, y retorna un token JWT
         * junto con los datos básicos del usuario para inicializar la sesión en el
         * frontend.
         *
         * <p>
         * Flujo:
         * <ol>
         * <li>Spring Security verifica las credenciales contra la base de datos.</li>
         * <li>Si son correctas, se carga el usuario completo desde el repositorio.</li>
         * <li>Se genera y retorna el token JWT firmado.</li>
         * </ol>
         * </p>
         *
         * @param request DTO con correo y contraseña del usuario
         * @return {@link LoginResponse} con el token JWT y datos del usuario
         * @throws org.springframework.security.authentication.BadCredentialsException si
         *                                                                             las
         *                                                                             credenciales
         *                                                                             son
         *                                                                             incorrectas
         * @throws org.springframework.security.authentication.DisabledException       si
         *                                                                             el
         *                                                                             usuario
         *                                                                             está
         *                                                                             inactivo
         */
        public LoginResponse login(LoginRequest request) {
                // Spring Security valida credenciales — lanza excepción si fallan
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getCorreo(),
                                                request.getContrasena()));

                // Si llegamos aquí, las credenciales son correctas
                Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "correo",
                                                request.getCorreo()));

                String token = jwtUtil.generarToken(
                                usuario.getCorreo(),
                                usuario.getRol().name());

                return new LoginResponse(
                                token,
                                usuario.getCorreo(),
                                usuario.getNombreUsuario(),
                                usuario.getRol().name(),
                                usuario.getId());
        }
}