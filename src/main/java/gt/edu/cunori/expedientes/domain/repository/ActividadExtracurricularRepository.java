package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.ActividadExtracurricular;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para las actividades extracurriculares de un estudiante.
 */
@Repository
public interface ActividadExtracurricularRepository extends JpaRepository<ActividadExtracurricular, Long> {

    /** Retorna todas las actividades extracurriculares de un estudiante. */
    List<ActividadExtracurricular> findByEstudianteId(Long estudianteId);

    /** Retorna las actividades de un estudiante filtradas por tipo. */
    List<ActividadExtracurricular> findByEstudianteIdAndTipoActividadId(Long estudianteId, Long tipoActividadId);
}