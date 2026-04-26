package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repositorio para las notificaciones internas del sistema.
 * Las notificaciones se generan automáticamente al presentar o cambiar el estado
 * de una justificación de inasistencia.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    /**
     * Retorna todas las notificaciones de un usuario ordenadas por fecha descendente.
     * Las no leídas aparecen primero gracias al orden secundario.
     */
    List<Notificacion> findByDestinatarioIdOrderByLeidaAscCreadaEnDesc(Long destinatarioId);

    /** Retorna solo las notificaciones no leídas de un usuario. */
    List<Notificacion> findByDestinatarioIdAndLeidaFalse(Long destinatarioId);

    /** Cuenta las notificaciones no leídas de un usuario (para el badge en la UI). */
    long countByDestinatarioIdAndLeidaFalse(Long destinatarioId);

    /**
     * Marca todas las notificaciones de un usuario como leídas.
     * Usado cuando el usuario abre el panel de notificaciones.
     */
    @Modifying
    @Transactional
    @Query("UPDATE Notificacion n SET n.leida = true WHERE n.destinatario.id = :destinatarioId AND n.leida = false")
    void marcarTodasComoLeidas(@Param("destinatarioId") Long destinatarioId);
}
