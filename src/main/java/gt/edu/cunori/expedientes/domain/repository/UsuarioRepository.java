package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.Usuario;
import gt.edu.cunori.expedientes.domain.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para los usuarios del sistema.
 * Incluye métodos de búsqueda por correo (usado por Spring Security)
 * y filtros por rol para administración.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo electrónico.
     * Usado por {@code UserDetailsServiceImpl} para la autenticación JWT.
     */
    Optional<Usuario> findByCorreo(String correo);

    /** Verifica si ya existe un usuario registrado con el correo dado. */
    boolean existsByCorreo(String correo);

    /** Verifica si ya existe un usuario registrado con el nombre de usuario dado. */
    boolean existsByNombreUsuario(String nombreUsuario);

    /** Retorna todos los usuarios que tienen el rol especificado. */
    List<Usuario> findByRol(Rol rol);

    /** Retorna todos los usuarios activos o inactivos según el parámetro. */
    List<Usuario> findByActivo(boolean activo);
}