package gt.edu.cunori.expedientes.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
 
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tipo_sangre")
public class TipoSangre {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
 
    @Column(nullable = false, unique = true, length = 5)
    private String nombre;
}
