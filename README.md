# Expedientes

``` markdown
expedientes
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ mvnw
├─ mvnw.cmd
├─ pom.xml
├─ README.md
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ gt
   │  │     └─ edu
   │  │        └─ cunori
   │  │           └─ expedientes
   │  │              ├─ api
   │  │              │  ├─ controller
   │  │              │  │  └─ AuthController.java
   │  │              │  └─ dto
   │  │              │     └─ auth
   │  │              │        ├─ LoginRequest.java
   │  │              │        └─ LoginResponse.java
   │  │              ├─ config
   │  │              │  └─ SecurityConfig.java
   │  │              ├─ domain
   │  │              │  ├─ entity
   │  │              │  │  ├─ ActividadExtracurricular.java
   │  │              │  │  ├─ Alergia.java
   │  │              │  │  ├─ Carrera.java
   │  │              │  │  ├─ CondicionMedica.java
   │  │              │  │  ├─ ContactoEmergencia.java
   │  │              │  │  ├─ DocumentoJustificacion.java
   │  │              │  │  ├─ Estudiante.java
   │  │              │  │  ├─ EstudianteAlergia.java
   │  │              │  │  ├─ EstudianteDiscapacidad.java
   │  │              │  │  ├─ FechaInasistencia.java
   │  │              │  │  ├─ JustificacionInasistencia.java
   │  │              │  │  ├─ MotivoInasistencia.java
   │  │              │  │  ├─ Notificacion.java
   │  │              │  │  ├─ TelefonoContacto.java
   │  │              │  │  ├─ TelefonoEstudiante.java
   │  │              │  │  ├─ TipoActividad.java
   │  │              │  │  ├─ TipoDiscapacidad.java
   │  │              │  │  ├─ TipoSangre.java
   │  │              │  │  └─ Usuario.java
   │  │              │  ├─ enums
   │  │              │  │  ├─ EstadoJustificacion.java
   │  │              │  │  ├─ Genero.java
   │  │              │  │  ├─ Rol.java
   │  │              │  │  └─ TipoTelefono.java
   │  │              │  └─ repository
   │  │              │     ├─ ActividadExtracurricularRepository.java
   │  │              │     ├─ AlergiaRepository.java
   │  │              │     ├─ CarreraRepository.java
   │  │              │     ├─ CondicionMedicaRepository.java
   │  │              │     ├─ ContactoEmergenciaRepository.java
   │  │              │     ├─ DocumentoJustificacionRepository.java
   │  │              │     ├─ EstudianteAlergiaRepository.java
   │  │              │     ├─ EstudianteDiscapacidadRepository.java
   │  │              │     ├─ EstudianteRepository.java
   │  │              │     ├─ FechaInasistenciaRepository.java
   │  │              │     ├─ JustificacionInasistenciaRepository.java
   │  │              │     ├─ MotivoInasistenciaRepository.java
   │  │              │     ├─ NotificacionRepository.java
   │  │              │     ├─ TelefonoContactoRepository.java
   │  │              │     ├─ TelefonoEstudianteRepository.java
   │  │              │     ├─ TipoActividadRepository.java
   │  │              │     ├─ TipoDiscapacidadRepository.java
   │  │              │     ├─ TipoSangreRepository.java
   │  │              │     └─ UsuarioRepository.java
   │  │              ├─ ExpedientesApplication.java
   │  │              ├─ security
   │  │              │  ├─ JwtFilter.java
   │  │              │  ├─ JwtUtil.java
   │  │              │  └─ UserDetailsServiceImpl.java
   │  │              ├─ service
   │  │              │  └─ AuthService.java
   │  │              └─ shared
   │  │                 └─ exception
   │  │                    ├─ ApiError.java
   │  │                    ├─ BusinessException.java
   │  │                    ├─ GlobalExceptionHandler.java
   │  │                    └─ ResourceNotFoundException.java
   │  └─ resources
   │     ├─ application.properties
   │     ├─ db
   │     │  └─ migration
   │     │     └─ V1__esquema.sql
   │     ├─ static
   │     └─ templates
   └─ test
      └─ java
         └─ gt
            └─ edu
               └─ cunori
                  └─ expedientes
                     └─ ExpedientesApplicationTests.java

```
