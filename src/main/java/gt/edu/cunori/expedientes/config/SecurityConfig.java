package gt.edu.cunori.expedientes.config;

import gt.edu.cunori.expedientes.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Configuración principal de Spring Security.
 *
 * <p>Define:
 * <ul>
 *   <li>Política de sesiones: STATELESS (sin sesión HTTP, solo JWT).</li>
 *   <li>Rutas públicas y protegidas por rol.</li>
 *   <li>CORS para permitir peticiones desde el frontend React.</li>
 *   <li>Proveedor de autenticación con BCrypt.</li>
 * </ul>
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity          // Habilita @PreAuthorize en controllers y servicios
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    /**
     * Configura la cadena de filtros de seguridad HTTP.
     * Define qué rutas son públicas y cuáles requieren autenticación o rol específico.
     *
     * @param http objeto de configuración de Spring Security
     * @return cadena de filtros configurada
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilitar CSRF — no aplica para API REST stateless con JWT
            .csrf(AbstractHttpConfigurer::disable)

            // Configurar CORS con el bean definido más abajo
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Política de sesiones: completamente stateless
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Reglas de autorización por ruta y rol
            .authorizeHttpRequests(auth -> auth

                // ── Rutas públicas ──────────────────────────────────────────
                .requestMatchers("/api/auth/**").permitAll()

                // ── Catálogos: lectura pública, escritura solo ADMIN ────────
                .requestMatchers(HttpMethod.GET,
                        "/api/catalogos/**",
                        "/api/carreras/**").hasAnyRole("ADMIN", "COORDINADOR", "ESTUDIANTE")
                .requestMatchers(HttpMethod.POST,
                        "/api/catalogos/**",
                        "/api/carreras/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,
                        "/api/catalogos/**",
                        "/api/carreras/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,
                        "/api/catalogos/**",
                        "/api/carreras/**").hasRole("ADMIN")

                // ── Usuarios: solo ADMIN ────────────────────────────────────
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")

                // ── Estudiantes: ADMIN y COORDINADOR ven todos,
                //    ESTUDIANTE solo accede a su propio expediente (validado en el servicio)
                .requestMatchers("/api/estudiantes/**")
                        .hasAnyRole("ADMIN", "COORDINADOR", "ESTUDIANTE")

                // ── Justificaciones: todos los roles autenticados ───────────
                .requestMatchers("/api/justificaciones/**")
                        .hasAnyRole("ADMIN", "COORDINADOR", "ESTUDIANTE")

                // ── Notificaciones: todos los roles autenticados ────────────
                .requestMatchers("/api/notificaciones/**")
                        .hasAnyRole("ADMIN", "COORDINADOR", "ESTUDIANTE")

                // ── Cualquier otra ruta requiere autenticación ───────────────
                .anyRequest().authenticated()
            )

            // Registrar el filtro JWT antes del filtro estándar de autenticación
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configura CORS para permitir peticiones desde el frontend React (Vite en puerto 5173).
     * En producción se debe cambiar el origen permitido a la URL del frontend desplegado.
     *
     * @return fuente de configuración CORS
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos: desarrollo local y producción
        config.setAllowedOrigins(List.of(
                "http://localhost:5173",   // Vite dev server
                "http://localhost:3000",
                "https://expedientes-frontend-gamma.vercel.app"    
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L); // Cache preflight por 1 hora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    /**
     * Proveedor de autenticación que usa la base de datos y BCrypt para verificar contraseñas.
     *
     * @return proveedor de autenticación configurado
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Expone el {@link AuthenticationManager} como bean para ser inyectado
     * en el {@code AuthService} durante el proceso de login.
     *
     * @param config configuración de autenticación de Spring
     * @return manager de autenticación
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Encoder de contraseñas usando BCrypt con factor de coste por defecto (10).
     *
     * @return encoder BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}