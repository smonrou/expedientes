package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para el catálogo estandarizado de alergias.
 */
@Repository
public interface AlergiaRepository extends JpaRepository<Alergia, Long> {

    /** Busca una alergia por su nombre exacto. */
    Optional<Alergia> findByNombre(String nombre);

    /** Verifica si ya existe una alergia con el nombre dado. */
    boolean existsByNombre(String nombre);
}
