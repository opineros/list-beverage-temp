-- --------------------------------------------------
-- Script: 001_create_table_users.sql
-- --------------------------------------------------

CREATE TABLE users (
    -- Clave primaria interna
    id SERIAL PRIMARY KEY,

    -- Datos del usuario
    name            VARCHAR(100)           NULL,
    last_name       VARCHAR(100)           NULL,
    email           VARCHAR(255)           NULL UNIQUE,
    nationality     VARCHAR(100)           NULL,
    age             INT                    NULL,

    -- Referencia al ID del usuario en auth-service
    user_id         BIGINT                 NOT NULL,

    -- Auditoría
    created_by      BIGINT                 NULL,
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    last_modified_by BIGINT                NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);

-- Índices recomendados
CREATE INDEX idx_users_user_id ON users(user_id);
CREATE INDEX idx_users_created_by ON users(created_by);
CREATE INDEX idx_users_last_modified_by ON users(last_modified_by);

-- --------------------------------------------------
-- Trigger para mantener last_modified_date actualizado
-- --------------------------------------------------

-- Función auxiliar
CREATE OR REPLACE FUNCTION public.set_last_modified()
RETURNS TRIGGER AS $$
BEGIN
   NEW.last_modified_date = now();
   RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Elimina trigger previo si existiera
DROP TRIGGER IF EXISTS trg_set_last_modified ON public.users;

-- Crea el trigger antes de cada UPDATE
CREATE TRIGGER trg_set_last_modified
  BEFORE UPDATE ON public.users
  FOR EACH ROW
  EXECUTE FUNCTION public.set_last_modified();