CREATE TABLE IF NOT EXISTS producto (
  id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
  nombre VARCHAR(150) NOT NULL,
  descripcion TEXT,
  precio DECIMAL(10,2) NOT NULL,
  categoria VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS inventario (
  id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
  producto_id CHAR(36) NOT NULL,
  stock_actual INT NOT NULL,
  umbral_minimo INT NOT NULL,
  fecha_actualizacion DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS historico_inventario (
  id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
  producto_id CHAR(36) NOT NULL,
  cantidad_anterior INT NOT NULL,
  cantidad_nueva INT NOT NULL,
  motivo VARCHAR(150),
  fecha_cambio DATETIME NOT NULL
);
