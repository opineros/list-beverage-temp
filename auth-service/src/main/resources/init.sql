-- init.sql: Scripts de inicialización de la base de datos para auth-service

-- 1. Tabla de roles
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Insertar roles predeterminados
INSERT INTO roles (name) VALUES
    ('ROLE_USER'),
    ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- 2. Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id INT NOT NULL REFERENCES roles(id),
    created_by VARCHAR(50),
    created_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    last_modified_by VARCHAR(50),
    last_modified_date TIMESTAMP WITHOUT TIME ZONE
);

-- 3. Tabla de logs de auditoría
CREATE TABLE IF NOT EXISTS audit_logs (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50),
    path VARCHAR(255),
    method VARCHAR(10),
    timestamp TIMESTAMP WITHOUT TIME ZONE
);

