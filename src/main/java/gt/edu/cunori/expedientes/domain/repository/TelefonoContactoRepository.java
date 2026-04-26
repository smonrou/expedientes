package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.TelefonoContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para los teléfonos de los contactos de emergencia.
 */
@Repository
public interface TelefonoContactoRepository extends JpaRepository<TelefonoContacto, Long> {

    /** Retorna todos los teléfonos de un contacto de emergencia. */
    List<TelefonoContacto> findByContactoId(Long contactoId);

    /** Elimina todos los teléfonos de un contacto de emergencia. */
    void deleteByContactoId(Long contactoId);
}
