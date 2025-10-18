CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT auto_increment NOT NULL,
	nombres varchar(100) NOT NULL,
	apellidos varchar(100) NOT NULL,
	edad INT NULL,
	fecha_nacimiento DATE NULL,
	pais varchar(50) NOT NULL,
	provincia varchar(50) NOT NULL,
	direccion varchar(100) NOT NULL,
	telefono varchar(20) NULL,
	correo varchar(50) NOT NULL,
	estado TINYINT NOT NULL,
	CONSTRAINT clientes_pk PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;