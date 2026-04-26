package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoRequest;
import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoResponse;
import gt.edu.cunori.expedientes.api.mapper.CatalogoSimpleMapper;
import gt.edu.cunori.expedientes.domain.entity.MotivoInasistencia;
import gt.edu.cunori.expedientes.domain.repository.MotivoInasistenciaRepository;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión del catálogo de motivos de inasistencia.
 * Permite listar, crear, actualizar y eliminar motivos de inasistencia.
 */
@Service
public class MotivoInasistenciaService {

    private final MotivoInasistenciaRepository repository;
    private final CatalogoSimpleMapper mapper;

    public MotivoInasistenciaService(MotivoInasistenciaRepository repository, CatalogoSimpleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retorna todos los motivos de inasistencia registrados.
     *
     * @return lista de motivos como DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<CatalogoResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Crea un nuevo motivo de inasistencia.
     * Valida que no exista otro con el mismo nombre.
     *
     * @param request datos del nuevo motivo
     * @return el motivo creado como DTO de respuesta
     * @throws BusinessException si ya existe un motivo con ese nombre
     */
    @Transactional
    public CatalogoResponse crear(CatalogoRequest request) {
        if (repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe un motivo de inasistencia con el nombre: " + request.getNombre());
        }
        MotivoInasistencia entidad = new MotivoInasistencia();
        entidad.setNombre(request.getNombre());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Actualiza el nombre de un motivo de inasistencia existente.
     *
     * @param id      identificador del motivo a actualizar
     * @param request nuevos datos
     * @return el motivo actualizado como DTO de respuesta
     * @throws ResourceNotFoundException si no se encuentra el registro con el id dado
     * @throws BusinessException         si el nuevo nombre ya está en uso por otro registro
     */
    @Transactional
    public CatalogoResponse actualizar(Long id, CatalogoRequest request) {
        MotivoInasistencia entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MotivoInasistencia", "id", id));

        if (!entidad.getNombre().equals(request.getNombre()) && repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe un motivo de inasistencia con el nombre: " + request.getNombre());
        }
        entidad.setNombre(request.getNombre());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Elimina un motivo de inasistencia por su identificador.
     *
     * @param id identificador del motivo a eliminar
     * @throws ResourceNotFoundException si no se encuentra el registro con el id dado
     */
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("MotivoInasistencia", "id", id);
        }
        repository.deleteById(id);
    }
}
