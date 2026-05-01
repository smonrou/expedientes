# Expedientes

``` markdown
expedientes
├─ .dockerfile
├─ .dockerignore
├─ .mvn
│  └─ wrapper
│     └─ maven-wrapper.properties
├─ certs
│  └─ ca.pem
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
   │  │              │  │  ├─ ActividadExtracurricularController.java
   │  │              │  │  ├─ AuthController.java
   │  │              │  │  ├─ CatalogoController.java
   │  │              │  │  ├─ EstudianteController.java
   │  │              │  │  ├─ JustificacionController.java
   │  │              │  │  ├─ NotificacionController.java
   │  │              │  │  └─ UsuarioController.java
   │  │              │  ├─ dto
   │  │              │  │  ├─ actividad
   │  │              │  │  │  ├─ ActividadRequest.java
   │  │              │  │  │  └─ ActividadResponse.java
   │  │              │  │  ├─ auth
   │  │              │  │  │  ├─ LoginRequest.java
   │  │              │  │  │  └─ LoginResponse.java
   │  │              │  │  ├─ catalogo
   │  │              │  │  │  ├─ CarreraRequest.java
   │  │              │  │  │  ├─ CarreraResponse.java
   │  │              │  │  │  ├─ CatalogoRequest.java
   │  │              │  │  │  └─ CatalogoResponse.java
   │  │              │  │  ├─ estudiante
   │  │              │  │  │  ├─ EstudianteCreateRequest.java
   │  │              │  │  │  ├─ EstudianteResponse.java
   │  │              │  │  │  ├─ EstudianteResumenResponse.java
   │  │              │  │  │  ├─ EstudianteSubDtos.java
   │  │              │  │  │  └─ EstudianteUpdateRequest.java
   │  │              │  │  ├─ justificacion
   │  │              │  │  │  ├─ CambioEstadoRequest.java
   │  │              │  │  │  ├─ JustificacionDtos.java
   │  │              │  │  │  └─ JustificacionRequest.java
   │  │              │  │  ├─ notificacion
   │  │              │  │  │  ├─ ConteoNoLeidasResponse.java
   │  │              │  │  │  └─ NotificacionResponse.java
   │  │              │  │  └─ usuario
   │  │              │  │     ├─ CambioContrasenaRequest.java
   │  │              │  │     ├─ UsuarioCreateRequest.java
   │  │              │  │     ├─ UsuarioResponse.java
   │  │              │  │     └─ UsuarioUpdateRequest.java
   │  │              │  └─ mapper
   │  │              │     ├─ ActividadMapper.java
   │  │              │     ├─ CarreraMapper.java
   │  │              │     ├─ CatalogoSimpleMapper.java
   │  │              │     ├─ EstudianteMapper.java
   │  │              │     ├─ JustificacionMapper.java
   │  │              │     ├─ NotificacionMapper.java
   │  │              │     └─ UsuarioMapper.java
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
   │  │              │  ├─ ActividadExtracurricularService.java
   │  │              │  ├─ AlergiaService.java
   │  │              │  ├─ AuthService.java
   │  │              │  ├─ CarreraService.java
   │  │              │  ├─ EstudianteService.java
   │  │              │  ├─ JustificacionService.java
   │  │              │  ├─ MotivoInasistenciaService.java
   │  │              │  ├─ NotificacionService.java
   │  │              │  ├─ TipoActividadService.java
   │  │              │  ├─ TipoDiscapacidadService.java
   │  │              │  ├─ TipoSangreService.java
   │  │              │  └─ UsuarioService.java
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
   │     │     ├─ V1__esquema.sql
   │     │     └─ V2__blob_documentos.sql
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
