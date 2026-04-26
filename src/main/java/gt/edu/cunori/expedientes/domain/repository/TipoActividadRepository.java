package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.TipoActividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para el catálogo de tipos de actividad extracurricular.
 * Este catálogo es configurable por el administrador.
 */
@Repository
public interface TipoActividadRepository extends JpaRepository<TipoActividad, Long> {

    /** Busca un tipo de actividad por su nombre exacto. */
    Optional<TipoActividad> findByNombre(String nombre);

    /** Verifica si ya existe un tipo de actividad con el nombre dado. */
    boolean existsByNombre(String nombre);
}