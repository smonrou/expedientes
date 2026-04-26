package gt.edu.cunori.expedientes.security;

import gt.edu.cunori.expedientes.domain.entity.Usuario;
import gt.edu.cunori.expedientes.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de {@link UserDetailsService} que carga el usuario desde la base de datos
 * usando el correo electrónico como identificador (username en términos de Spring Security).
 *
 * <p>Spring Security llama a este servicio durante la autenticación para obtener
 * los detalles del usuario y verificar credenciales.</p>
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    /**
     * Carga un usuario por su correo electrónico.
     * Verifica además que el usuario esté activo en el sistema.
     *
     * @param correo correo electrónico del usuario (usado como username)
     * @return objeto {@link UserDetails} con credenciales y autoridades
     * @throws UsernameNotFoundException si el correo no existe o el usuario está inactivo
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado con correo: " + correo));

        if (!usuario.getActivo()) {
            throw new UsernameNotFoundException(
                    "El usuario con correo " + correo + " está desactivado.");
        }

        // El rol se almacena como "ADMIN", "COORDINADOR" o "ESTUDIANTE".
        // Spring Security requiere el prefijo "ROLE_" para el uso con hasRole().
        String autoridad = "ROLE_" + usuario.getRol().name();

        return new User(
                usuario.getCorreo(),
                usuario.getContrasenaHash(),
                List.of(new SimpleGrantedAuthority(autoridad))
        );
    }
}