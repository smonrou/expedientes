package gt.edu.cunori.expedientes.api.mapper;

import gt.edu.cunori.expedientes.api.dto.catalogo.CarreraResponse;
import gt.edu.cunori.expedientes.domain.entity.Carrera;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para convertir la entidad Carrera a DTO de respuesta.
 */
@Mapper(componentModel = "spring")
public interface CarreraMapper {

    /** Convierte Carrera a CarreraResponse. */
    CarreraResponse toResponse(Carrera entity);
}