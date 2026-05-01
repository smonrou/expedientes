package gt.edu.cunori.expedientes.api.mapper;

import gt.edu.cunori.expedientes.api.dto.estudiante.EstudianteResumenResponse;
import gt.edu.cunori.expedientes.api.dto.estudiante.EstudianteResponse;
import gt.edu.cunori.expedientes.api.dto.estudiante.EstudianteSubDtos;
import gt.edu.cunori.expedientes.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper MapStruct para convertir la entidad Estudiante y sus sub-entidades a
 * DTOs.
 */
@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    /**
     * Convierte un Estudiante a su DTO de respuesta completo.
     * Mapea manualmente los campos de relaciones anidadas.
     */
    @Mapping(target = "id", expression = "java(e.getId() != null ? e.getId().longValue() : null)")
    @Mapping(target = "usuarioId", expression = "java(e.getUsuario() != null && e.getUsuario().getId() != null ? e.getUsuario().getId().longValue() : null)")
    @Mapping(target = "nombreUsuario", source = "usuario.nombreUsuario")
    @Mapping(target = "correoUsuario", source = "usuario.correo")
    @Mapping(target = "carreraId", expression = "java(e.getCarrera() != null && e.getCarrera().getId() != null ? e.getCarrera().getId().longValue() : null)")
    @Mapping(target = "carreraNombre", source = "carrera.nombre")
    @Mapping(target = "tipoSangreId", expression = "java(e.getTipoSangre() != null && e.getTipoSangre().getId() != null ? e.getTipoSangre().getId().longValue() : null)")
    @Mapping(target = "tipoSangreNombre", source = "tipoSangre.nombre")
    @Mapping(target = "telefonos", source = "telefonos")
    @Mapping(target = "condicionesMedicas", source = "condicionesMedicas")
    @Mapping(target = "alergias", source = "alergias")
    @Mapping(target = "discapacidades", source = "discapacidades")
    @Mapping(target = "contactosEmergencia", source = "contactosEmergencia")
    EstudianteResponse toResponse(Estudiante e);

    /**
     * Convierte un Estudiante a su DTO de respuesta resumido para listados.
     */
    @Mapping(target = "id", expression = "java(e.getId() != null ? e.getId().longValue() : null)")
    @Mapping(target = "carreraNombre", source = "carrera.nombre")
    EstudianteResumenResponse toResumen(Estudiante e);

    /**
     * Convierte un TelefonoEstudiante a su DTO de respuesta.
     */
    @Mapping(target = "id", expression = "java(t.getId() != null ? t.getId().longValue() : null)")
    EstudianteSubDtos.TelefonoResponse telefonoToResponse(TelefonoEstudiante t);

    /**
     * Convierte una CondicionMedica a su DTO de respuesta.
     */
    @Mapping(target = "id", expression = "java(c.getId() != null ? c.getId().longValue() : null)")
    EstudianteSubDtos.CondicionMedicaResponse condicionToResponse(CondicionMedica c);

    /**
     * Convierte un EstudianteAlergia a su DTO de respuesta.
     */
    @Mapping(target = "alergiaId", expression = "java(a.getAlergia() != null && a.getAlergia().getId() != null ? a.getAlergia().getId().longValue() : null)")
    @Mapping(target = "nombre", source = "alergia.nombre")
    @Mapping(target = "observaciones", source = "observaciones")
    EstudianteSubDtos.AlergiaResponse alergiaToResponse(EstudianteAlergia a);

    /**
     * Convierte un EstudianteDiscapacidad a su DTO de respuesta.
     */
    @Mapping(target = "tipoDiscapacidadId", expression = "java(d.getTipoDiscapacidad() != null && d.getTipoDiscapacidad().getId() != null ? d.getTipoDiscapacidad().getId().longValue() : null)")
    @Mapping(target = "nombre", source = "tipoDiscapacidad.nombre")
    @Mapping(target = "observaciones", source = "observaciones")
    EstudianteSubDtos.DiscapacidadResponse discapacidadToResponse(EstudianteDiscapacidad d);

    /**
     * Convierte un ContactoEmergencia a su DTO de respuesta.
     */
    @Mapping(target = "id", expression = "java(c.getId() != null ? c.getId().longValue() : null)")
    EstudianteSubDtos.ContactoEmergenciaResponse contactoToResponse(ContactoEmergencia c);
}