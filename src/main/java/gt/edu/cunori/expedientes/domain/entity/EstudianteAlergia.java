package gt.edu.cunori.expedientes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estudiante_alergia")
public class EstudianteAlergia {

    @EmbeddedId
    private EstudianteAlergiaId id = new EstudianteAlergiaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("estudianteId")
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("alergiaId")
    @JoinColumn(name = "alergia_id")
    private Alergia alergia;

    @Column(length = 255)
    private String observaciones;

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    public static class EstudianteAlergiaId implements java.io.Serializable {
        @Column(name = "estudiante_id")
        private Integer estudianteId;

        @Column(name = "alergia_id")
        private Integer alergiaId;
    }
}