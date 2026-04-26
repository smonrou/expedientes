package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.CondicionMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para las condiciones médicas preexistentes de un estudiante.
 */
@Repository
public interface CondicionMedicaRepository extends JpaRepository<CondicionMedica, Long> {

    /** Retorna todas las condiciones médicas registradas para un estudiante. */
    List<CondicionMedica> findByEstudianteId(Long estudianteId);

    /** Elimina todas las condiciones médicas de un estudiante. */
    void deleteByEstudianteId(Long estudianteId);
}