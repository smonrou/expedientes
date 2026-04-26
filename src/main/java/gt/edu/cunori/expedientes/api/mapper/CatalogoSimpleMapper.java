package gt.edu.cunori.expedientes.api.mapper;

import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoResponse;
import gt.edu.cunori.expedientes.domain.entity.Alergia;
import gt.edu.cunori.expedientes.domain.entity.MotivoInasistencia;
import gt.edu.cunori.expedientes.domain.entity.TipoActividad;
import gt.edu.cunori.expedientes.domain.entity.TipoDiscapacidad;
import gt.edu.cunori.expedientes.domain.entity.TipoSangre;
import org.mapstruct.Mapper;

/**
 * Mapper MapStruct para convertir entidades de catálogos simples a DTOs de respuesta.
 * Los catálogos simples comparten la misma estructura: id + nombre.
 */
@Mapper(componentModel = "spring")
public interface CatalogoSimpleMapper {

    /** Convierte TipoSangre a CatalogoResponse. */
    CatalogoResponse toResponse(TipoSangre entity);

    /** Convierte Alergia a CatalogoResponse. */
    CatalogoResponse toResponse(Alergia entity);

    /** Convierte TipoDiscapacidad a CatalogoResponse. */
    CatalogoResponse toResponse(TipoDiscapacidad entity);

    /** Convierte TipoActividad a CatalogoResponse. */
    CatalogoResponse toResponse(TipoActividad entity);

    /** Convierte MotivoInasistencia a CatalogoResponse. */
    CatalogoResponse toResponse(MotivoInasistencia entity);
}