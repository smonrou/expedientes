package gt.edu.cunori.expedientes.domain.entity;

import gt.edu.cunori.expedientes.domain.enums.TipoTelefono;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "telefono_contacto")
public class TelefonoContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contacto_id", nullable = false)
    private ContactoEmergencia contacto;

    @Column(nullable = false, length = 20)
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoTelefono tipo = TipoTelefono.CELULAR;
}