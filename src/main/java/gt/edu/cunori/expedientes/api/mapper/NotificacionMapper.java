package gt.edu.cunori.expedientes.api.mapper;

import gt.edu.cunori.expedientes.api.dto.notificacion.NotificacionResponse;
import gt.edu.cunori.expedientes.domain.entity.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper MapStruct para convertir la entidad Notificacion a su DTO de respuesta.
 */
@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    /**
     * Convierte una Notificacion a su DTO de respuesta.
     * El repositorio ya usa Long como tipo de id, por lo que no se requiere conversión.
     */
    @Mapping(target = "destinatarioId", source = "usuarioDestinatario.id")
    @Mapping(target = "justificacionId", expression = "java(n.getJustificacion() != null && n.getJustificacion().getId() != null ? n.getJustificacion().getId().longValue() : null)")
    NotificacionResponse toResponse(Notificacion n);
}