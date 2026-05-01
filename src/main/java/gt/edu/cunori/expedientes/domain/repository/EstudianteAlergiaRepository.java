package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.EstudianteAlergia;
import gt.edu.cunori.expedientes.domain.entity.EstudianteAlergia.EstudianteAlergiaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la relación N:M entre estudiante y alergia.
 * Usa clave compuesta {@link EstudianteAlergiaId} definida como clase interna
 * de {@link EstudianteAlergia}.
 */
@Repository
public interface EstudianteAlergiaRepository extends JpaRepository<EstudianteAlergia, EstudianteAlergiaId> {

    /** Retorna todas las alergias registradas para un estudiante. */
    List<EstudianteAlergia> findByIdEstudianteId(Long estudianteId);

    /**
     * Elimina todas las alergias de un estudiante (usado al reemplazar la lista).
     */
    void deleteByIdEstudianteId(Long estudianteId);
}