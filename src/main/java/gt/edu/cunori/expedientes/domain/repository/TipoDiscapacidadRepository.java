package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.TipoDiscapacidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para el catálogo de tipos de discapacidad.
 */
@Repository
public interface TipoDiscapacidadRepository extends JpaRepository<TipoDiscapacidad, Long> {

    /** Busca un tipo de discapacidad por su nombre exacto. */
    Optional<TipoDiscapacidad> findByNombre(String nombre);

    /** Verifica si ya existe un tipo de discapacidad con el nombre dado. */
    boolean existsByNombre(String nombre);
}