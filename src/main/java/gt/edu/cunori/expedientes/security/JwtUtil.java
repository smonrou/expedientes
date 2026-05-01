package gt.edu.cunori.expedientes.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilidad para la generación, firma y validación de tokens JWT.
 * Usa el algoritmo HMAC-SHA256 con la clave definida en application.properties.
 */
@Component
public class JwtUtil {

    private final SecretKey claveFirma;
    private final long expiracionMs;

    /**
     * Constructor que inicializa la clave de firma y el tiempo de expiración
     * a partir de los valores definidos en application.properties.
     *
     * @param secret       clave secreta en texto plano (mínimo 256 bits
     *                     recomendado)
     * @param expiracionMs tiempo de vida del token en milisegundos
     */
    public JwtUtil(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms}") long expiracionMs) {
        this.claveFirma = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiracionMs = expiracionMs;
    }

    /**
     * Genera un token JWT firmado para el usuario autenticado.
     * El subject es el correo electrónico del usuario.
     *
     * @param correo correo del usuario autenticado
     * @param rol    rol del usuario (ADMIN, COORDINADOR, ESTUDIANTE)
     * @return token JWT como cadena de texto
     */
    public String generarToken(String correo, String rol) {
        Date ahora = new Date();
        Date expiracion = new Date(ahora.getTime() + expiracionMs);

        return Jwts.builder()
                .subject(correo)
                .claim("rol", rol)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(claveFirma)
                .compact();
    }

    /**
     * Extrae el correo electrónico (subject) del token JWT.
     *
     * @param token token JWT
     * @return correo del usuario
     */
    public String extraerCorreo(String token) {
        return extraerClaims(token).getSubject();
    }

    /**
     * Extrae el rol del usuario del token JWT.
     *
     * @param token token JWT
     * @return rol como cadena de texto
     */
    public String extraerRol(String token) {
        return extraerClaims(token).get("rol", String.class);
    }

    /**
     * Valida que el token sea auténtico, no haya sido manipulado y no esté vencido.
     *
     * @param token token JWT a validar
     * @return {@code true} si el token es válido, {@code false} en caso contrario
     */
    public boolean esTokenValido(String token) {
        try {
            extraerClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Parsea y retorna todos los claims del token JWT.
     * Lanza excepción si el token está malformado, manipulado o vencido.
     *
     * @param token token JWT
     * @return objeto {@link Claims} con todos los datos del payload
     */
    private Claims extraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(claveFirma)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}