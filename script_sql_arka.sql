
CREATE TABLE Clientes (
    id INT PRIMARY KEY,
    nombre VARCHAR(100),
    correo VARCHAR(100) UNIQUE,
    contraseña VARCHAR(100),
    dirección TEXT
);

CREATE TABLE Productos (
    id INT PRIMARY KEY,
    nombre VARCHAR(100),
    descripción TEXT,
    precio DECIMAL(10,2),
    stock INT
);

CREATE TABLE Pedidos (
    id INT PRIMARY KEY,
    cliente_id INT,
    fecha DATE,
    estado VARCHAR(50),
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);

CREATE TABLE DetallePedido (
    id INT PRIMARY KEY,
    pedido_id INT,
    producto_id INT,
    cantidad INT,
    precio_unitario DECIMAL(10,2),
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(id),
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);

CREATE TABLE Carrito (
    id INT PRIMARY KEY,
    cliente_id INT,
    fecha_creación DATE,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);

CREATE TABLE CarritoDetalle (
    id INT PRIMARY KEY,
    carrito_id INT,
    producto_id INT,
    cantidad INT,
    FOREIGN KEY (carrito_id) REFERENCES Carrito(id),
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);

CREATE TABLE Notificaciones (
    id INT PRIMARY KEY,
    cliente_id INT,
    tipo VARCHAR(50),
    mensaje TEXT,
    fecha_envío DATE,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(id)
);

CREATE TABLE Reportes (
    id INT PRIMARY KEY,
    fecha_inicio DATE,
    fecha_fin DATE,
    total_ventas DECIMAL(10,2)
);

CREATE TABLE Abastecimientos (
    id INT PRIMARY KEY,
    producto_id INT,
    cantidad INT,
    fecha DATE,
    FOREIGN KEY (producto_id) REFERENCES Productos(id)
);
