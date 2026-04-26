package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.FechaInasistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio para las fechas cubiertas por una justificación de inasistencia.
 * La tabla tiene constraint UNIQUE (justificacion_id, fecha) para evitar duplicados.
 */
@Repository
public interface FechaInasistenciaRepository extends JpaRepository<FechaInasistencia, Long> {

    /** Retorna todas las fechas de una justificación. */
    List<FechaInasistencia> findByJustificacionId(Long justificacionId);

    /**
     * Verifica si una fecha específica ya está registrada dentro de una justificación.
     * Útil para validar antes de insertar y dar un mensaje claro al usuario.
     */
    boolean existsByJustificacionIdAndFecha(Long justificacionId, LocalDate fecha);

    /** Elimina todas las fechas de una justificación (usado al editar). */
    void deleteByJustificacionId(Long justificacionId);

    /** Elimina una fecha específica de una justificación. */
    void deleteByJustificacionIdAndFecha(Long justificacionId, LocalDate fecha);

    boolean existsByEstudianteIdAndFecha(Long estudianteId, LocalDate fecha);
}