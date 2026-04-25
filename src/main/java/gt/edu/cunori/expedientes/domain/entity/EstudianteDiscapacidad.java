package gt.edu.cunori.expedientes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estudiante_discapacidad")
public class EstudianteDiscapacidad {

    @EmbeddedId
    private EstudianteDiscapacidadId id = new EstudianteDiscapacidadId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("estudianteId")
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("tipoDiscapacidadId")
    @JoinColumn(name = "tipo_discapacidad_id")
    private TipoDiscapacidad tipoDiscapacidad;

    @Column(length = 255)
    private String observaciones;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class EstudianteDiscapacidadId implements java.io.Serializable {
        @Column(name = "estudiante_id")
        private Integer estudianteId;

        @Column(name = "tipo_discapacidad_id")
        private Integer tipoDiscapacidadId;
    }
}