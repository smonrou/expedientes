package gt.edu.cunori.expedientes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fecha_inasistencia", uniqueConstraints = @UniqueConstraint(columnNames = { "justificacion_id",
        "fecha" }))
public class FechaInasistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "justificacion_id", nullable = false)
    private JustificacionInasistencia justificacion;

    @Column(nullable = false)
    private LocalDate fecha;
}
