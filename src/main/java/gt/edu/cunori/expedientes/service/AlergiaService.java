package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoRequest;
import gt.edu.cunori.expedientes.api.dto.catalogo.CatalogoResponse;
import gt.edu.cunori.expedientes.api.mapper.CatalogoSimpleMapper;
import gt.edu.cunori.expedientes.domain.entity.Alergia;
import gt.edu.cunori.expedientes.domain.repository.AlergiaRepository;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión del catálogo de alergias.
 * Permite listar, crear, actualizar y eliminar alergias.
 */
@Service
public class AlergiaService {

    private final AlergiaRepository repository;
    private final CatalogoSimpleMapper mapper;

    public AlergiaService(AlergiaRepository repository, CatalogoSimpleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retorna todas las alergias registradas.
     *
     * @return lista de alergias como DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<CatalogoResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Crea una nueva alergia.
     * Valida que no exista otra con el mismo nombre.
     *
     * @param request datos de la nueva alergia
     * @return la alergia creada como DTO de respuesta
     * @throws BusinessException si ya existe una alergia con ese nombre
     */
    @Transactional
    public CatalogoResponse crear(CatalogoRequest request) {
        if (repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe una alergia con el nombre: " + request.getNombre());
        }
        Alergia entidad = new Alergia();
        entidad.setNombre(request.getNombre());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Actualiza el nombre de una alergia existente.
     *
     * @param id      identificador de la alergia a actualizar
     * @param request nuevos datos
     * @return la alergia actualizada como DTO de respuesta
     * @throws ResourceNotFoundException si no se encuentra el registro con el id dado
     * @throws BusinessException         si el nuevo nombre ya está en uso por otro registro
     */
    @Transactional
    public CatalogoResponse actualizar(Long id, CatalogoRequest request) {
        Alergia entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alergia", "id", id));

        if (!entidad.getNombre().equals(request.getNombre()) && repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe una alergia con el nombre: " + request.getNombre());
        }
        entidad.setNombre(request.getNombre());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Elimina una alergia por su identificador.
     *
     * @param id identificador de la alergia a eliminar
     * @throws ResourceNotFoundException si no se encuentra el registro con el id dado
     */
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Alergia", "id", id);
        }
        repository.deleteById(id);
    }
}