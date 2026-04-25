
--  Sistema de Expedientes Estudiantiles - CUNORI
DROP DATABASE IF EXISTS SEEC;
CREATE DATABASE SEEC;
USE SEEC;
SET FOREIGN_KEY_CHECKS = 0;
SET NAMES utf8mb4;


-- 1. CATALOGOS BASE


CREATE TABLE tipo_sangre (
    id   TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(5) NOT NULL,
    CONSTRAINT pk_tipo_sangre PRIMARY KEY (id),
    CONSTRAINT uq_tipo_sangre_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Catálogo fijo de tipos de sangre (A+, A-, B+, B-, AB+, AB-, O+, O-)';

-- -------------------------------------------------------------

CREATE TABLE alergia (
    id     SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    CONSTRAINT pk_alergia PRIMARY KEY (id),
    CONSTRAINT uq_alergia_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Catálogo estandarizado de alergias conocidas';

-- -------------------------------------------------------------

CREATE TABLE tipo_discapacidad (
    id     SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    CONSTRAINT pk_tipo_discapacidad PRIMARY KEY (id),
    CONSTRAINT uq_tipo_discapacidad_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Catálogo de tipos de discapacidad organizado por categoría';

-- -------------------------------------------------------------

CREATE TABLE tipo_actividad (
    id     SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    activo TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_tipo_actividad PRIMARY KEY (id),
    CONSTRAINT uq_tipo_actividad_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Tipos de actividad extracurricular configurables por el administrador';

-- -------------------------------------------------------------

CREATE TABLE motivo_inasistencia (
    id     SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    activo TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT pk_motivo_inasistencia PRIMARY KEY (id),
    CONSTRAINT uq_motivo_inasistencia_nombre UNIQUE (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Catálogo configurable de motivos de inasistencia';


-- 2. CARRERA


CREATE TABLE carrera (
    id                  SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nombre              VARCHAR(150) NOT NULL,
    codigo              VARCHAR(20)  NOT NULL,
    nombre_coordinador  VARCHAR(150) NOT NULL,
    CONSTRAINT pk_carrera   PRIMARY KEY (id),
    CONSTRAINT uq_carrera_codigo UNIQUE (codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Carreras de ingeniería del CUNORI';


-- 3. USUARIO


CREATE TABLE usuario (
    id               INT UNSIGNED  NOT NULL AUTO_INCREMENT,
    nombre_usuario   VARCHAR(60)   NOT NULL,
    contrasena_hash  VARCHAR(255)  NOT NULL,
    correo           VARCHAR(150)  NOT NULL,
    rol              ENUM('ADMIN','COORDINADOR','ESTUDIANTE') NOT NULL,
    activo           TINYINT(1)    NOT NULL DEFAULT 1,
    creado_en        DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_usuario            PRIMARY KEY (id),
    CONSTRAINT uq_usuario_nombre     UNIQUE (nombre_usuario),
    CONSTRAINT uq_usuario_correo     UNIQUE (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Usuarios del sistema con sus roles';


-- 4. ESTUDIANTE


CREATE TABLE estudiante (
    id                   INT UNSIGNED     NOT NULL AUTO_INCREMENT,
    usuario_id           INT UNSIGNED     NOT NULL,
    carrera_id           SMALLINT UNSIGNED NOT NULL,
    tipo_sangre_id       TINYINT UNSIGNED  NULL,
    numero_carne         VARCHAR(12)      NOT NULL,
    nombres              VARCHAR(100)     NOT NULL,
    apellidos            VARCHAR(100)     NOT NULL,
    cui                  VARCHAR(13)      NOT NULL,
    fecha_nacimiento     DATE             NOT NULL,
    genero               ENUM('MASCULINO','FEMENINO','OTRO') NOT NULL,
    correo_institucional VARCHAR(150)     NOT NULL,
    correo_personal      VARCHAR(150)     NULL,
    anio_ingreso         YEAR             NOT NULL,
    inscrito             TINYINT(1)       NOT NULL DEFAULT 1,
    pensum_cerrado       TINYINT(1)       NOT NULL DEFAULT 0,
    fecha_cierre_pensum  DATE             NULL,
    ruta_fotografia      VARCHAR(255)     NULL,
    direccion            VARCHAR(255)     NOT NULL,
    CONSTRAINT pk_estudiante           PRIMARY KEY (id),
    CONSTRAINT uq_estudiante_usuario   UNIQUE (usuario_id),
    CONSTRAINT uq_estudiante_carne     UNIQUE (numero_carne),
    CONSTRAINT uq_estudiante_cui       UNIQUE (cui),
    CONSTRAINT uq_estudiante_correo_inst UNIQUE (correo_institucional),
    CONSTRAINT fk_estudiante_usuario   FOREIGN KEY (usuario_id)
        REFERENCES usuario (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_estudiante_carrera   FOREIGN KEY (carrera_id)
        REFERENCES carrera (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_estudiante_tipo_sangre FOREIGN KEY (tipo_sangre_id)
        REFERENCES tipo_sangre (id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Perfil académico y personal del estudiante (extiende usuario)';


-- 5. TELÉFONOS


CREATE TABLE telefono_estudiante (
    id            INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estudiante_id INT UNSIGNED NOT NULL,
    numero        VARCHAR(20)  NOT NULL,
    tipo          ENUM('CASA','CELULAR','TRABAJO') NOT NULL DEFAULT 'CELULAR',
    CONSTRAINT pk_telefono_estudiante PRIMARY KEY (id),
    CONSTRAINT fk_tel_est_estudiante  FOREIGN KEY (estudiante_id)
        REFERENCES estudiante (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Números de teléfono del estudiante (puede tener varios)';


-- 6. INFORMACIÓN DE EMERGENCIA


CREATE TABLE condicion_medica (
    id            INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estudiante_id INT UNSIGNED NOT NULL,
    descripcion   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_condicion_medica    PRIMARY KEY (id),
    CONSTRAINT fk_condicion_estudiante FOREIGN KEY (estudiante_id)
        REFERENCES estudiante (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Condiciones médicas preexistentes del estudiante';

-- -------------------------------------------------------------

CREATE TABLE estudiante_alergia (
    estudiante_id INT UNSIGNED    NOT NULL,
    alergia_id    SMALLINT UNSIGNED NOT NULL,
    observaciones VARCHAR(255)    NULL,
    CONSTRAINT pk_estudiante_alergia  PRIMARY KEY (estudiante_id, alergia_id),
    CONSTRAINT fk_est_alergia_est     FOREIGN KEY (estudiante_id)
        REFERENCES estudiante (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_est_alergia_alergia FOREIGN KEY (alergia_id)
        REFERENCES alergia (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Relación N:M entre estudiante y alergias conocidas';

-- -------------------------------------------------------------

CREATE TABLE estudiante_discapacidad (
    estudiante_id        INT UNSIGNED     NOT NULL,
    tipo_discapacidad_id SMALLINT UNSIGNED NOT NULL,
    observaciones        VARCHAR(255)     NULL,
    CONSTRAINT pk_estudiante_discapacidad  PRIMARY KEY (estudiante_id, tipo_discapacidad_id),
    CONSTRAINT fk_est_disc_est             FOREIGN KEY (estudiante_id)
        REFERENCES estudiante (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_est_disc_tipo            FOREIGN KEY (tipo_discapacidad_id)
        REFERENCES tipo_discapacidad (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Relación N:M entre estudiante y tipos de discapacidad';

-- -------------------------------------------------------------

CREATE TABLE contacto_emergencia (
    id             INT UNSIGNED NOT NULL AUTO_INCREMENT,
    estudiante_id  INT UNSIGNED NOT NULL,
    nombre_completo VARCHAR(150) NOT NULL,
    parentesco     VARCHAR(80)  NOT NULL,
    direccion      VARCHAR(255) NULL,
    CONSTRAINT pk_contacto_emergencia  PRIMARY KEY (id),
    CONSTRAINT fk_contacto_estudiante  FOREIGN KEY (estudiante_id)
        REFERENCES estudiante (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Contactos de emergencia del estudiante (al menos uno requerido)';

-- -------------------------------------------------------------

CREATE TABLE telefono_contacto (
    id          INT UNSIGNED NOT NULL AUTO_INCREMENT,
    contacto_id INT UNSIGNED NOT NULL,
    numero      VARCHAR(20)  NOT NULL,
    tipo        ENUM('CASA','CELULAR','TRABAJO') NOT NULL DEFAULT 'CELULAR',
    CONSTRAINT pk_telefono_contacto   PRIMARY KEY (id),
    CONSTRAINT fk_tel_cont_contacto   FOREIGN KEY (contacto_id)
        REFERENCES contacto_emergencia (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Teléfonos del contacto de emergencia (puede tener varios)';


-- 7. ACTIVIDADES EXTRACURRICULARES


CREATE TABLE actividad_extracurricular (
    id               INT UNSIGNED      NOT NULL AUTO_INCREMENT,
    estudiante_id    INT UNSIGNED      NOT NULL,
    tipo_actividad_id SMALLINT UNSIGNED NOT NULL,
    nombre           VARCHAR(200)      NOT NULL,
    institucion      VARCHAR(200)      NULL,
    fecha_inicio     DATE              NOT NULL,
    fecha_fin        DATE              NULL,
    observaciones    TEXT              NULL,
    CONSTRAINT pk_actividad            PRIMARY KEY (id),
    CONSTRAINT fk_act_estudiante       FOREIGN KEY (estudiante_id)
        REFERENCES estudiante (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_act_tipo_actividad   FOREIGN KEY (tipo_actividad_id)
        REFERENCES tipo_actividad (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Participación del estudiante en actividades fuera del aula';


-- 8. JUSTIFICACIONES DE INASISTENCIA

CREATE TABLE justificacion_inasistencia (
    id                  INT UNSIGNED      NOT NULL AUTO_INCREMENT,
    estudiante_id       INT UNSIGNED      NOT NULL,
    motivo_id           SMALLINT UNSIGNED NOT NULL,
    descripcion         TEXT              NOT NULL,
    estado              ENUM('PRESENTADA','EN_REVISION','APROBADA','RECHAZADA')
                        NOT NULL DEFAULT 'PRESENTADA',
    fecha_presentacion  DATETIME          NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_revision      DATETIME          NULL,
    revisado_por_id     INT UNSIGNED      NULL,
    CONSTRAINT pk_justificacion          PRIMARY KEY (id),
    CONSTRAINT fk_just_estudiante        FOREIGN KEY (estudiante_id)
        REFERENCES estudiante (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_just_motivo            FOREIGN KEY (motivo_id)
        REFERENCES motivo_inasistencia (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT fk_just_revisor           FOREIGN KEY (revisado_por_id)
        REFERENCES usuario (id) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Justificaciones presentadas por los estudiantes con su flujo de estados';

-- -------------------------------------------------------------

CREATE TABLE fecha_inasistencia (
    id                      INT UNSIGNED NOT NULL AUTO_INCREMENT,
    justificacion_id        INT UNSIGNED NOT NULL,
    fecha                   DATE         NOT NULL,
    CONSTRAINT pk_fecha_inasistencia     PRIMARY KEY (id),
    CONSTRAINT uq_fecha_justificacion    UNIQUE (justificacion_id, fecha),
    CONSTRAINT fk_fecha_justificacion    FOREIGN KEY (justificacion_id)
        REFERENCES justificacion_inasistencia (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Fechas específicas de ausencia cubiertas por una justificación';

-- -------------------------------------------------------------

CREATE TABLE documento_justificacion (
    id               INT UNSIGNED NOT NULL AUTO_INCREMENT,
    justificacion_id INT UNSIGNED NOT NULL,
    ruta_archivo     VARCHAR(255) NOT NULL,
    nombre_original  VARCHAR(255) NOT NULL,
    subido_en        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_documento_justificacion  PRIMARY KEY (id),
    CONSTRAINT fk_doc_justificacion        FOREIGN KEY (justificacion_id)
        REFERENCES justificacion_inasistencia (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Documentos de respaldo adjuntos a una justificación';


-- 9. NOTIFICACIONES


CREATE TABLE notificacion (
    id                      INT UNSIGNED NOT NULL AUTO_INCREMENT,
    usuario_destinatario_id INT UNSIGNED NOT NULL,
    justificacion_id        INT UNSIGNED NOT NULL,
    mensaje                 VARCHAR(255) NOT NULL,
    leida                   TINYINT(1)   NOT NULL DEFAULT 0,
    creada_en               DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_notificacion           PRIMARY KEY (id),
    CONSTRAINT fk_noti_destinatario      FOREIGN KEY (usuario_destinatario_id)
        REFERENCES usuario (id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_noti_justificacion     FOREIGN KEY (justificacion_id)
        REFERENCES justificacion_inasistencia (id) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Notificaciones internas generadas por eventos del sistema';


-- 10. ÍNDICES ADICIONALES PARA CONSULTAS FRECUENTES


CREATE INDEX idx_estudiante_carrera     ON estudiante (carrera_id);
CREATE INDEX idx_estudiante_anio        ON estudiante (anio_ingreso);
CREATE INDEX idx_estudiante_inscrito    ON estudiante (inscrito);
CREATE INDEX idx_justificacion_estado   ON justificacion_inasistencia (estado);
CREATE INDEX idx_justificacion_est      ON justificacion_inasistencia (estudiante_id);
CREATE INDEX idx_notificacion_dest      ON notificacion (usuario_destinatario_id, leida);

-- -------------------------------------------------------------

SET FOREIGN_KEY_CHECKS = 1;