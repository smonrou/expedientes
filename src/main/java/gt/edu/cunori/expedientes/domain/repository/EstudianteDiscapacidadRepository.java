package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.EstudianteDiscapacidad;
import gt.edu.cunori.expedientes.domain.entity.EstudianteDiscapacidad.EstudianteDiscapacidadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la relación N:M entre estudiante y tipo de discapacidad.
 * Usa clave compuesta {@link EstudianteDiscapacidadId}.
 */
@Repository
public interface EstudianteDiscapacidadRepository
        extends JpaRepository<EstudianteDiscapacidad, EstudianteDiscapacidadId> {

    /** Retorna todas las discapacidades registradas para un estudiante. */
    List<EstudianteDiscapacidad> findByIdEstudianteId(Long estudianteId);

    /**
     * Elimina todas las discapacidades de un estudiante (usado al reemplazar la
     * lista).
     */
    void deleteByIdEstudianteId(Long estudianteId);
}