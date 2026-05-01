package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.DocumentoJustificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para los documentos adjuntos de una justificación de
 * inasistencia.
 * Los archivos se almacenan en disco; aquí solo se persiste la ruta y
 * metadatos.
 */
@Repository
public interface DocumentoJustificacionRepository extends JpaRepository<DocumentoJustificacion, Long> {

    /** Retorna todos los documentos adjuntos de una justificación. */
    List<DocumentoJustificacion> findByJustificacionId(Long justificacionId);

    /** Elimina todos los documentos de una justificación. */
    void deleteByJustificacionId(Long justificacionId);
}