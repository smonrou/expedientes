package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.actividad.ActividadRequest;
import gt.edu.cunori.expedientes.api.dto.actividad.ActividadResponse;
import gt.edu.cunori.expedientes.api.mapper.ActividadMapper;
import gt.edu.cunori.expedientes.domain.entity.ActividadExtracurricular;
import gt.edu.cunori.expedientes.domain.entity.Estudiante;
import gt.edu.cunori.expedientes.domain.entity.TipoActividad;
import gt.edu.cunori.expedientes.domain.repository.ActividadExtracurricularRepository;
import gt.edu.cunori.expedientes.domain.repository.EstudianteRepository;
import gt.edu.cunori.expedientes.domain.repository.TipoActividadRepository;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión de actividades extracurriculares de los estudiantes.
 * Permite listar, crear, actualizar y eliminar actividades asociadas a un expediente.
 */
@Service
public class ActividadExtracurricularService {

    private final ActividadExtracurricularRepository actividadRepository;
    private final EstudianteRepository estudianteRepository;
    private final TipoActividadRepository tipoActividadRepository;
    private final ActividadMapper mapper;

    public ActividadExtracurricularService(
            ActividadExtracurricularRepository actividadRepository,
            EstudianteRepository estudianteRepository,
            TipoActividadRepository tipoActividadRepository,
            ActividadMapper mapper) {
        this.actividadRepository = actividadRepository;
        this.estudianteRepository = estudianteRepository;
        this.tipoActividadRepository = tipoActividadRepository;
        this.mapper = mapper;
    }

    /**
     * Retorna todas las actividades extracurriculares de un estudiante.
     *
     * @param estudianteId identificador del estudiante
     * @return lista de actividades como DTOs de respuesta
     * @throws ResourceNotFoundException si no existe el estudiante
     */
    @Transactional(readOnly = true)
    public List<ActividadResponse> listarPorEstudiante(Long estudianteId) {
        if (!estudianteRepository.existsById(estudianteId)) {
            throw new ResourceNotFoundException("Estudiante", "id", estudianteId);
        }
        return actividadRepository.findByEstudianteId(estudianteId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Retorna una actividad extracurricular por su identificador.
     *
     * @param id identificador de la actividad
     * @return la actividad como DTO de respuesta
     * @throws ResourceNotFoundException si no existe la actividad
     */
    @Transactional(readOnly = true)
    public ActividadResponse buscarPorId(Long id) {
        return actividadRepository.findById(id
            
        )
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ActividadExtracurricular", "id", id));
    }

    /**
     * Crea una nueva actividad extracurricular para un estudiante.
     * Valida que la fecha de fin sea posterior a la fecha de inicio si se proporciona.
     *
     * @param estudianteId identificador del estudiante
     * @param request      datos de la nueva actividad
     * @return la actividad creada como DTO de respuesta
     * @throws ResourceNotFoundException si no existe el estudiante o el tipo de actividad
     * @throws BusinessException         si la fecha de fin es anterior a la fecha de inicio
     */
    @Transactional
    public ActividadResponse crear(Long estudianteId, ActividadRequest request) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante", "id", estudianteId));

        TipoActividad tipoActividad = tipoActividadRepository.findById(request.getTipoActividadId())
                .orElseThrow(() -> new ResourceNotFoundException("TipoActividad", "id", request.getTipoActividadId()));

        if (request.getFechaFin() != null && request.getFechaFin().isBefore(request.getFechaInicio())) {
            throw new BusinessException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        ActividadExtracurricular entidad = new ActividadExtracurricular();
        entidad.setEstudiante(estudiante);
        entidad.setTipoActividad(tipoActividad);
        entidad.setNombre(request.getNombre());
        entidad.setInstitucion(request.getInstitucion());
        entidad.setFechaInicio(request.getFechaInicio());
        entidad.setFechaFin(request.getFechaFin());
        entidad.setObservaciones(request.getObservaciones());

        return mapper.toResponse(actividadRepository.save(entidad));
    }

    /**
     * Actualiza una actividad extracurricular existente.
     * Valida que la actividad pertenezca al estudiante indicado.
     *
     * @param estudianteId identificador del estudiante dueño de la actividad
     * @param id           identificador de la actividad a actualizar
     * @param request      nuevos datos de la actividad
     * @return la actividad actualizada como DTO de respuesta
     * @throws ResourceNotFoundException si no existe la actividad o el tipo de actividad
     * @throws BusinessException         si la actividad no pertenece al estudiante, o si la fecha de fin es inválida
     */
    @Transactional
    public ActividadResponse actualizar(Long estudianteId, Long id, ActividadRequest request) {
        ActividadExtracurricular entidad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActividadExtracurricular", "id", id));

        if (!entidad.getEstudiante().getId().equals(estudianteId)) {
            throw new BusinessException("La actividad no pertenece al estudiante indicado.");
        }

        TipoActividad tipoActividad = tipoActividadRepository.findById(request.getTipoActividadId())
                .orElseThrow(() -> new ResourceNotFoundException("TipoActividad", "id", request.getTipoActividadId()));

        if (request.getFechaFin() != null && request.getFechaFin().isBefore(request.getFechaInicio())) {
            throw new BusinessException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }

        entidad.setTipoActividad(tipoActividad);
        entidad.setNombre(request.getNombre());
        entidad.setInstitucion(request.getInstitucion());
        entidad.setFechaInicio(request.getFechaInicio());
        entidad.setFechaFin(request.getFechaFin());
        entidad.setObservaciones(request.getObservaciones());

        return mapper.toResponse(actividadRepository.save(entidad));
    }

    /**
     * Elimina una actividad extracurricular.
     * Valida que la actividad pertenezca al estudiante indicado.
     *
     * @param estudianteId identificador del estudiante dueño de la actividad
     * @param id           identificador de la actividad a eliminar
     * @throws ResourceNotFoundException si no existe la actividad
     * @throws BusinessException         si la actividad no pertenece al estudiante indicado
     */
    @Transactional
    public void eliminar(Long estudianteId, Long id) {
        ActividadExtracurricular entidad = actividadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ActividadExtracurricular", "id", id));

        if (!entidad.getEstudiante().getId().equals(estudianteId)) {
            throw new BusinessException("La actividad no pertenece al estudiante indicado.");
        }

        actividadRepository.deleteById(id);
    }
}