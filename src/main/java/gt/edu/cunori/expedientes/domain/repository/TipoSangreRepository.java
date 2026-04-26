package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.TipoSangre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para el catálogo de tipos de sangre.
 * Permite buscar por nombre para evitar duplicados al cargar datos iniciales.
 */
@Repository
public interface TipoSangreRepository extends JpaRepository<TipoSangre, Long> {

    /** Busca un tipo de sangre por su nombre (ej. "A+", "O-"). */
    Optional<TipoSangre> findByNombre(String nombre);

    /** Verifica si ya existe un tipo de sangre con el nombre dado. */
    boolean existsByNombre(String nombre);
}