package gt.edu.cunori.expedientes.domain.repository;

import gt.edu.cunori.expedientes.domain.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para los expedientes estudiantiles.
 * Contiene consultas para búsqueda y filtrado por carrera, carné y nombre.
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

        /** Busca un estudiante por su número de carné universitario. */
        Optional<Estudiante> findByNumeroCarne(String carne);

        /** Verifica si ya existe un estudiante con el carné dado. */
        boolean existsByNumeroCarne(String carne);

        /** Retorna todos los estudiantes inscritos en una carrera específica. */
        List<Estudiante> findByCarreraId(Long carreraId);

        /**
         * Busca estudiantes cuyo nombre, apellido o carné contenga el texto dado.
         * Útil para el buscador general del módulo de estudiantes.
         */
        @Query("""
                            SELECT e FROM Estudiante e
                            JOIN e.usuario u
                            WHERE LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :termino, '%'))
                               OR LOWER(u.correo)        LIKE LOWER(CONCAT('%', :termino, '%'))
                               OR LOWER(e.numeroCarne)         LIKE LOWER(CONCAT('%', :termino, '%'))
                        """)
        List<Estudiante> buscarPorTermino(@Param("termino") String termino);

        /**
         * Busca estudiantes de una carrera específica que coincidan con el término de
         * búsqueda.
         * Usado por coordinadores que solo ven su carrera.
         */
        @Query("""
                            SELECT e FROM Estudiante e
                            JOIN e.usuario u
                            WHERE e.carrera.id = :carreraId
                              AND (
                                   LOWER(u.nombreUsuario) LIKE LOWER(CONCAT('%', :termino, '%'))
                                OR LOWER(u.correo)        LIKE LOWER(CONCAT('%', :termino, '%'))
                                OR LOWER(e.numeroCarne)         LIKE LOWER(CONCAT('%', :termino, '%'))
                              )
                        """)
        List<Estudiante> buscarPorTerminoYCarrera(
                        @Param("termino") String termino,
                        @Param("carreraId") Long carreraId);

        /** Busca el estudiante asociado a un usuario específico. */
        Optional<Estudiante> findByUsuarioId(Long usuarioId);
}