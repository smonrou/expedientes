package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.MotivoInasistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para el catálogo de motivos de inasistencia.
 * Este catálogo es configurable por el administrador.
 */
@Repository
public interface MotivoInasistenciaRepository extends JpaRepository<MotivoInasistencia, Long> {

    /** Busca un motivo de inasistencia por su nombre exacto. */
    Optional<MotivoInasistencia> findByNombre(String nombre);

    /** Verifica si ya existe un motivo con el nombre dado. */
    boolean existsByNombre(String nombre);
}
