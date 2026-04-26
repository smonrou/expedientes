package gt.edu.cunori.expedientes.api.mapper;

import gt.edu.cunori.expedientes.api.dto.justificacion.JustificacionDtos;
import gt.edu.cunori.expedientes.domain.entity.DocumentoJustificacion;
import gt.edu.cunori.expedientes.domain.entity.FechaInasistencia;
import gt.edu.cunori.expedientes.domain.entity.JustificacionInasistencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper MapStruct para convertir JustificacionInasistencia y sus sub-entidades a DTOs.
 */
@Mapper(componentModel = "spring")
public interface JustificacionMapper {

    /**
     * Convierte una JustificacionInasistencia a su DTO de respuesta completo.
     */
    @Mapping(target = "id", expression = "java(j.getId() != null ? j.getId().longValue() : null)")
    @Mapping(target = "estudianteId", expression = "java(j.getEstudiante() != null && j.getEstudiante().getId() != null ? j.getEstudiante().getId().longValue() : null)")
    @Mapping(target = "estudianteNombre", expression = "java(j.getEstudiante() != null ? j.getEstudiante().getNombres() + ' ' + j.getEstudiante().getApellidos() : null)")
    @Mapping(target = "estudianteNumeroCarne", source = "estudiante.numeroCarne")
    @Mapping(target = "motivoId", expression = "java(j.getMotivo() != null && j.getMotivo().getId() != null ? j.getMotivo().getId().longValue() : null)")
    @Mapping(target = "motivoNombre", source = "motivo.nombre")
    @Mapping(target = "revisadoPorId", expression = "java(j.getRevisadoPor() != null && j.getRevisadoPor().getId() != null ? j.getRevisadoPor().getId().longValue() : null)")
    @Mapping(target = "revisadoPorNombre", source = "revisadoPor.nombreUsuario")
    @Mapping(target = "fechas", source = "fechas")
    @Mapping(target = "documentos", source = "documentos")
    JustificacionDtos.JustificacionResponse toResponse(JustificacionInasistencia j);

    /**
     * Convierte una JustificacionInasistencia a su DTO de resumen para listados.
     */
    @Mapping(target = "id", expression = "java(j.getId() != null ? j.getId().longValue() : null)")
    @Mapping(target = "estudianteId", expression = "java(j.getEstudiante() != null && j.getEstudiante().getId() != null ? j.getEstudiante().getId().longValue() : null)")
    @Mapping(target = "estudianteNombre", expression = "java(j.getEstudiante() != null ? j.getEstudiante().getNombres() + ' ' + j.getEstudiante().getApellidos() : null)")
    @Mapping(target = "estudianteNumeroCarne", source = "estudiante.numeroCarne")
    @Mapping(target = "motivoNombre", source = "motivo.nombre")
    @Mapping(target = "totalFechas", expression = "java(j.getFechas() != null ? j.getFechas().size() : 0)")
    JustificacionDtos.JustificacionResumenResponse toResumen(JustificacionInasistencia j);

    /**
     * Convierte una FechaInasistencia a su DTO de respuesta.
     */
    @Mapping(target = "id", expression = "java(f.getId() != null ? f.getId().longValue() : null)")
    JustificacionDtos.FechaInasistenciaResponse fechaToResponse(FechaInasistencia f);

    /**
     * Convierte un DocumentoJustificacion a su DTO de respuesta.
     * No incluye el contenido binario — ese se sirve por el endpoint de descarga.
     */
    @Mapping(target = "id", expression = "java(d.getId() != null ? d.getId().longValue() : null)")
    @Mapping(target = "nombreOriginal", source = "nombreOriginal")
    @Mapping(target = "tipoMime", source = "tipoMime")
    @Mapping(target = "subidoEn", source = "subidoEn")
    JustificacionDtos.DocumentoResponse documentoToResponse(DocumentoJustificacion d);
}