package gt.edu.cunori.expedientes.service;

import gt.edu.cunori.expedientes.api.dto.catalogo.CarreraRequest;
import gt.edu.cunori.expedientes.api.dto.catalogo.CarreraResponse;
import gt.edu.cunori.expedientes.api.mapper.CarreraMapper;
import gt.edu.cunori.expedientes.domain.entity.Carrera;
import gt.edu.cunori.expedientes.domain.repository.CarreraRepository;
import gt.edu.cunori.expedientes.shared.exception.BusinessException;
import gt.edu.cunori.expedientes.shared.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Servicio para la gestión del catálogo de carreras de ingeniería.
 * Permite listar, crear, actualizar y eliminar carreras.
 */
@Service
public class CarreraService {

    private final CarreraRepository repository;
    private final CarreraMapper mapper;

    public CarreraService(CarreraRepository repository, CarreraMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Retorna todas las carreras registradas.
     *
     * @return lista de carreras como DTOs de respuesta
     */
    @Transactional(readOnly = true)
    public List<CarreraResponse> listarTodas() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    /**
     * Busca una carrera por su identificador.
     *
     * @param id identificador de la carrera
     * @return la carrera como DTO de respuesta
     * @throws ResourceNotFoundException si no se encuentra una carrera con el id dado
     */
    @Transactional(readOnly = true)
    public CarreraResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera", "id", id));
    }

    /**
     * Crea una nueva carrera.
     * Valida que no exista otra con el mismo nombre.
     *
     * @param request datos de la nueva carrera
     * @return la carrera creada como DTO de respuesta
     * @throws BusinessException si ya existe una carrera con ese nombre
     */
    @Transactional
    public CarreraResponse crear(CarreraRequest request) {
        if (repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe una carrera con el nombre: " + request.getNombre());
        }
        Carrera entidad = new Carrera();
        entidad.setNombre(request.getNombre());
        entidad.setCodigo(request.getCodigo());
        entidad.setNombreCoordinador(request.getNombreCoordinador());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Actualiza los datos de una carrera existente.
     *
     * @param id      identificador de la carrera a actualizar
     * @param request nuevos datos
     * @return la carrera actualizada como DTO de respuesta
     * @throws ResourceNotFoundException si no se encuentra la carrera con el id dado
     * @throws BusinessException         si el nuevo nombre ya está en uso por otra carrera
     */
    @Transactional
    public CarreraResponse actualizar(Long id, CarreraRequest request) {
        Carrera entidad = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carrera", "id", id));

        if (!entidad.getNombre().equals(request.getNombre()) && repository.existsByNombre(request.getNombre())) {
            throw new BusinessException("Ya existe una carrera con el nombre: " + request.getNombre());
        }
        entidad.setNombre(request.getNombre());
        entidad.setCodigo(request.getCodigo());
        entidad.setNombreCoordinador(request.getNombreCoordinador());
        return mapper.toResponse(repository.save(entidad));
    }

    /**
     * Elimina una carrera por su identificador.
     *
     * @param id identificador de la carrera a eliminar
     * @throws ResourceNotFoundException si no se encuentra la carrera con el id dado
     */
    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Carrera", "id", id);
        }
        repository.deleteById(id);
    }
}