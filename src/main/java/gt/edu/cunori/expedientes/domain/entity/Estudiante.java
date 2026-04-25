package gt.edu.cunori.expedientes.domain.entity;

import gt.edu.cunori.expedientes.domain.enums.Genero;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_sangre_id")
    private TipoSangre tipoSangre;

    @Column(name = "numero_carne", nullable = false, unique = true, length = 12)
    private String numeroCarne;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 13)
    private String cui;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private Genero genero;

    @Column(name = "correo_institucional", nullable = false, unique = true, length = 150)
    private String correoInstitucional;

    @Column(name = "correo_personal", length = 150)
    private String correoPersonal;

    @Column(name = "anio_ingreso", nullable = false)
    private Integer anioIngreso;

    @Column(nullable = false)
    private Boolean inscrito = true;

    @Column(name = "pensum_cerrado", nullable = false)
    private Boolean pensumCerrado = false;

    @Column(name = "fecha_cierre_pensum")
    private LocalDate fechaCierrePensum;

    @Column(name = "ruta_fotografia", length = 255)
    private String rutaFotografia;

    @Column(nullable = false, length = 255)
    private String direccion;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TelefonoEstudiante> telefonos = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CondicionMedica> condicionesMedicas = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstudianteAlergia> alergias = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstudianteDiscapacidad> discapacidades = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactoEmergencia> contactosEmergencia = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActividadExtracurricular> actividades = new ArrayList<>();

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JustificacionInasistencia> justificaciones = new ArrayList<>();
}
