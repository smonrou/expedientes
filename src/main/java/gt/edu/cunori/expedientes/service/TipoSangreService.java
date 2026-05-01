package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoRequest;
import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoResponse;
import gt.edu.cunori.expedientes.api.mapper.CatalogoSimpleMapper;
import gt.edu.cunori.expedientes.domain.entity.TipoSangre;
import gt.edu.cunori.expedientes.domain.repository.TipoSangreRepository;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión del catálogo de tipos de sangre.
 * Permite listar, crear, actualizar y eliminar tipos de sangre.
 */
@Service
public class TipoSangreService {

    private final TipoSangreRepository repository;
    private final CatalogoSimpleMapper mapper;

    public TipoSangreService(TipoSangreRepository repository, CatalogoSimpleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retorna todos los tipos de sangre registrados.
     *
     * @return lista de tipos de sangre como DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<CatalogoResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Crea un nuevo tipo de sangre.
     * Valida que no exista otro con el mismo nombre.
     *
     * @param request datos del nuevo tipo de sangre
     * @return el tipo de sangre creado como DTO de respuesta
     * @throws BusinessException si ya existe un tipo de sangre con ese nombre
     */
    @Transactional
    public CatalogoResponse crear(CatalogoRequest request) {
        if (repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe un tipo de sangre con el nombre: " + request.getNombre());
        }
        TipoSangre entidad = new TipoSangre();
        entidad.setNombre(request.getNombre());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Actualiza el nombre de un tipo de sangre existente.
     *
     * @param id      identificador del tipo de sangre a actualizar
     * @param request nuevos datos
     * @return el tipo de sangre actualizado como DTO de respuesta
     * @throws ResourceNotFoundException si no se encuentra el registro con el id
     *                                   dado
     * @throws BusinessException         si el nuevo nombre ya está en uso por otro
     *                                   registro
     */
    @Transactional
    public CatalogoResponse actualizar(Long id, CatalogoRequest request) {
        TipoSangre entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoSangre", "id", id));

        if (!entidad.getNombre().equals(request.getNombre()) && repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe un tipo de sangre con el nombre: " + request.getNombre());
        }
        entidad.setNombre(request.getNombre());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Elimina un tipo de sangre por su identificador.
     *
     * @param id identificador del tipo de sangre a eliminar
     * @throws ResourceNotFoundException si no se encuentra el registro con el id
     *                                   dado
     */
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("TipoSangre", "id", id);
        }
        repository.deleteById(id);
    }
}