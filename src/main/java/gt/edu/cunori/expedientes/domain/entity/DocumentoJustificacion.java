package gt.edu.cunori.expedientes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "documento_justificacion")
public class DocumentoJustificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "justificacion_id", nullable = false)
    private JustificacionInasistencia justificacion;

    @Column(name = "nombre_original", nullable = false, length = 255)
    private String nombreOriginal;

    @Column(name = "tipo_mime", nullable = false, length = 100)
    private String tipoMime;

    @Lob
    @Column(name = "contenido", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] contenido;

    @Column(name = "subido_en", nullable = false, updatable = false)
    private LocalDateTime subidoEn;

    @PrePersist
    protected void onCreate() {
        this.subidoEn = LocalDateTime.now();
    }
}