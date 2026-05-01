package gt.edu.cunori.expedientes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro JWT que se ejecuta una sola vez por request (extiende
 * {@link OncePerRequestFilter}).
 *
 * <p>
 * Proceso:
 * <ol>
 * <li>Lee el header {@code Authorization: Bearer <token>}.</li>
 * <li>Valida el token con {@link JwtUtil}.</li>
 * <li>Carga el usuario desde la base de datos con
 * {@link UserDetailsServiceImpl}.</li>
 * <li>Si todo es válido, registra la autenticación en el
 * {@link SecurityContextHolder}.</li>
 * </ol>
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * Intercepta cada petición HTTP y autentica al usuario si el token JWT es
     * válido.
     *
     * @param request     petición HTTP entrante
     * @param response    respuesta HTTP saliente
     * @param filterChain cadena de filtros de Spring Security
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // Si no hay header o no empieza con "Bearer ", continuar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7); // Quitar el prefijo "Bearer "

        // Validar token antes de procesarlo
        if (!jwtUtil.esTokenValido(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String correo = jwtUtil.extraerCorreo(token);

        // Solo autenticar si hay un correo válido y no hay autenticación previa en el
        // contexto
        if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(correo);

            UsernamePasswordAuthenticationToken autenticacion = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities());

            autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Registrar la autenticación en el contexto de seguridad de Spring
            SecurityContextHolder.getContext().setAuthentication(autenticacion);
        }

        filterChain.doFilter(request, response);
    }
}