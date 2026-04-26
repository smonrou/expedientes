package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.JustificacionInasistencia;
import gt.edu.cunori.expedientes.domain.enums.EstadoJustificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para las justificaciones de inasistencia.
 * Soporta el flujo de estados: PRESENTADA → EN_REVISION → APROBADA / RECHAZADA.
 */
@Repository
public interface JustificacionInasistenciaRepository extends JpaRepository<JustificacionInasistencia, Long> {

    /** Retorna todas las justificaciones presentadas por un estudiante. */
    List<JustificacionInasistencia> findByEstudianteId(Long estudianteId);

    /** Retorna las justificaciones de un estudiante filtradas por estado. */
    List<JustificacionInasistencia> findByEstudianteIdAndEstado(Long estudianteId, EstadoJustificacion estado);

    /**
     * Retorna todas las justificaciones de los estudiantes de una carrera específica.
     * Usado por coordinadores para ver las justificaciones de su carrera.
     */
    @Query("""
            SELECT j FROM JustificacionInasistencia j
            WHERE j.estudiante.carrera.id = :carreraId
            ORDER BY j.fechaPresentacion DESC
            """)
    List<JustificacionInasistencia> findByCarreraId(@Param("carreraId") Long carreraId);

    /**
     * Retorna las justificaciones de una carrera filtradas por estado.
     * Usado por coordinadores para gestionar el flujo de revisión.
     */
    @Query("""
            SELECT j FROM JustificacionInasistencia j
            WHERE j.estudiante.carrera.id = :carreraId
              AND j.estado = :estado
            ORDER BY j.fechaPresentacion DESC
            """)
    List<JustificacionInasistencia> findByCarreraIdAndEstado(@Param("carreraId") Long carreraId,
                                                              @Param("estado") EstadoJustificacion estado);
}