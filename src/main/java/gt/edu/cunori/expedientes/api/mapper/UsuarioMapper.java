package gt.edu.cunori.expedientes.api.mapper;

import gt.edu.cunori.expedientes.api.dto.usuario.UsuarioResponse;
import gt.edu.cunori.expedientes.domain.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper MapStruct para convertir la entidad Usuario a DTO de respuesta.
 * El campo id se convierte de Integer (entidad) a Long (response).
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", expression = "java(entity.getId() != null ? entity.getId().longValue() : null)")
    UsuarioResponse toResponse(Usuario entity);
}