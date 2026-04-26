package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para las carreras de ingeniería del CUNORI.
 */
@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    /** Busca una carrera por su nombre exacto. */
    Optional<Carrera> findByNombre(String nombre);

    /** Verifica si ya existe una carrera con el nombre dado. */
    boolean existsByNombre(String nombre);
}