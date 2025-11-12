CREATE TABLE `Inventario` (
  `id` int UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `producto_id` int,
  `stock_actual` int,
  `umbral_minimo` int,
  `fecha_actualizacion` datetime
);

CREATE TABLE `Producto` (
  `id` int UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(150),
  `descripcion` text,
  `precio` decimal,
  `categoria` varchar(100)
);

CREATE TABLE `Historico_Inventario` (
  `id` int UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `producto_id` int,
  `cantidad_anterior` int,
  `cantidad_nueva` int,
  `motivo` varchar(150),
  `fecha` datetime
);

CREATE TABLE `detalleorden` (
  `id` int UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `orden_id` int,
  `producto_id` int,
  `cantidad` int,
  `precio_unitario` decimal(10,2)
);

CREATE TABLE `orden` (
  `id` int UNIQUE PRIMARY KEY AUTO_INCREMENT,
  `correo` varchar(100),
  `nombre_cliente` varchar(100),
  `direccion_entrega` varchar(200),
  `estado` varchar(15),
  `subtotal` decimal(10,2),
  `fecha_creacion` datetime,
  `fecha_venta` datetime
);

ALTER TABLE `Inventario` ADD FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`);

ALTER TABLE `Historico_Inventario` ADD FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`);

ALTER TABLE `detalleorden` ADD FOREIGN KEY (`orden_id`) REFERENCES `orden` (`id`);

ALTER TABLE `detalleorden` ADD FOREIGN KEY (`producto_id`) REFERENCES `Producto` (`id`);
