package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.ContactoEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para los contactos de emergencia de un estudiante.
 * Un estudiante puede tener múltiples contactos registrados.
 */
@Repository
public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Long> {

    /** Retorna todos los contactos de emergencia de un estudiante. */
    List<ContactoEmergencia> findByEstudianteId(Long estudianteId);

    /** Elimina todos los contactos de emergencia de un estudiante. */
    void deleteByEstudianteId(Long estudianteId);
}