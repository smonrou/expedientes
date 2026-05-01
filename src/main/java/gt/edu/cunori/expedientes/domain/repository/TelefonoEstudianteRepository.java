package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.TelefonoEstudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para los teléfonos del estudiante.
 * Un estudiante puede tener múltiples números registrados.
 */
@Repository
public interface TelefonoEstudianteRepository extends JpaRepository<TelefonoEstudiante, Long> {

    /** Retorna todos los teléfonos registrados para un estudiante. */
    List<TelefonoEstudiante> findByEstudianteId(Long estudianteId);

    /**
     * Elimina todos los teléfonos de un estudiante (usado al actualizar la lista
     * completa).
     */
    void deleteByEstudianteId(Long estudianteId);
}
