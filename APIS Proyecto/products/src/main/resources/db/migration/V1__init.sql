CREATE TABLE IF NOT EXISTS productos (
	id_producto BIGINT auto_increment NOT NULL,
	nombre varchar(100) NOT NULL,
	descripcion varchar(250) NOT NULL,
	precio DECIMAL(10,2) NOT NULL,
	stock_Actual INT NOT NULL,
	categoria varchar(100) NOT NULL,
	estado TINYINT NULL,
	CONSTRAINT products_pk PRIMARY KEY (id_producto)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;