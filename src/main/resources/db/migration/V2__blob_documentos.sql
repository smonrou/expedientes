ALTER TABLE documento_justificacion
    DROP COLUMN ruta_archivo,
    ADD COLUMN tipo_mime VARCHAR(100) NOT NULL AFTER nombre_original,
    ADD COLUMN contenido LONGBLOB NOT NULL AFTER tipo_mime;