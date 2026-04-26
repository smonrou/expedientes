package gt.edu.cunori.expedientes.api.mapper;

import gt.edu.cunori.expedientes.api.dto.actividad.ActividadResponse;
import gt.edu.cunori.expedientes.domain.entity.ActividadExtracurricular;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper MapStruct para convertir ActividadExtracurricular a su DTO de respuesta.
 */
@Mapper(componentModel = "spring")
public interface ActividadMapper {

    /**
     * Convierte una ActividadExtracurricular a su DTO de respuesta.
     * Mapea los ids de relaciones anidadas con conversión Integer → Long.
     */
    @Mapping(target = "id", expression = "java(a.getId() != null ? a.getId().longValue() : null)")
    @Mapping(target = "estudianteId", expression = "java(a.getEstudiante() != null && a.getEstudiante().getId() != null ? a.getEstudiante().getId().longValue() : null)")
    @Mapping(target = "tipoActividadId", expression = "java(a.getTipoActividad() != null && a.getTipoActividad().getId() != null ? a.getTipoActividad().getId().longValue() : null)")
    @Mapping(target = "tipoActividadNombre", source = "tipoActividad.nombre")
    ActividadResponse toResponse(ActividadExtracurricular a);
}