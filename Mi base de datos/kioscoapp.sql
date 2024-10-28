-- Configuración inicial
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Crear base de datos
DROP DATABASE IF EXISTS kioscoapp;
CREATE DATABASE kioscoapp;
USE kioscoapp;

-- Eliminar tablas en orden inverso a sus dependencias
DROP TABLE IF EXISTS `detalleitem`;
DROP TABLE IF EXISTS `detallepedido`;
DROP TABLE IF EXISTS `pedido`;
DROP TABLE IF EXISTS `usuario`;
DROP TABLE IF EXISTS `producto`;
DROP TABLE IF EXISTS `deposito`;
DROP TABLE IF EXISTS `cliente`;

-- Eliminar triggers existentes
DROP TRIGGER IF EXISTS before_producto_insert_update;
DROP TRIGGER IF EXISTS before_pedido_insert_update;
DROP TRIGGER IF EXISTS before_detalleitem_insert_update;

-- Estructura de tabla para tabla `cliente`
CREATE TABLE `cliente` (
  `idCliente` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  PRIMARY KEY (`idCliente`),
  INDEX `idx_cliente_nombre_apellido` (`nombre`, `apellido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estructura de tabla para tabla `deposito`
CREATE TABLE `deposito` (
  `idDeposito` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  PRIMARY KEY (`idDeposito`),
  UNIQUE INDEX `idx_deposito_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estructura de tabla para tabla `producto`
CREATE TABLE `producto` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL,
  `precioUnitario` decimal(10,2) NOT NULL,
  `stock` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idProducto`),
  INDEX `idx_producto_descripcion` (`descripcion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estructura de tabla para tabla `usuario`
CREATE TABLE `usuario` (
  `idUsuario` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `rol` ENUM('admin', 'vendedor', 'supervisor') NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `idDeposito` int DEFAULT NULL,
  `ultimo_login` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idUsuario`),
  UNIQUE INDEX `idx_usuario_email` (`email`),
  KEY `idDeposito` (`idDeposito`),
  CONSTRAINT `usuario_ibfk_1` FOREIGN KEY (`idDeposito`) REFERENCES `deposito` (`idDeposito`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estructura de tabla para tabla `pedido`
CREATE TABLE `pedido` (
  `idPedido` int NOT NULL AUTO_INCREMENT,
  `idCliente` int NOT NULL,
  `fecha` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `estado` enum('Pendiente','En proceso','Terminado','Cancelado') DEFAULT 'Pendiente',
  `idDeposito` int NOT NULL,
  `precioTotalPedido` decimal(10,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idPedido`),
  KEY `idCliente` (`idCliente`),
  KEY `idDeposito` (`idDeposito`),
  INDEX `idx_pedido_fecha` (`fecha`),
  INDEX `idx_pedido_estado` (`estado`),
  CONSTRAINT `pedido_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `pedido_ibfk_2` FOREIGN KEY (`idDeposito`) REFERENCES `deposito` (`idDeposito`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estructura de tabla para tabla `detallepedido`
CREATE TABLE `detallepedido` (
  `idDetallePedido` int NOT NULL AUTO_INCREMENT,
  `idPedido` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idDetallePedido`),
  KEY `idPedido` (`idPedido`),
  CONSTRAINT `detallepedido_ibfk_1` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estructura de tabla para tabla `detalleitem`
CREATE TABLE `detalleitem` (
  `idDetalleItem` int NOT NULL AUTO_INCREMENT,
  `idDetallePedido` int NOT NULL,
  `idProducto` int NOT NULL,
  `cantidad` int NOT NULL,
  `precioUnitario` decimal(10,2) NOT NULL,
  `precioTotal` decimal(10,2) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idDetalleItem`),
  KEY `idDetallePedido` (`idDetallePedido`),
  KEY `idProducto` (`idProducto`),
  CONSTRAINT `detalleitem_ibfk_1` FOREIGN KEY (`idDetallePedido`) REFERENCES `detallepedido` (`idDetallePedido`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `detalleitem_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `producto` (`idProducto`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Crear triggers para validaciones
DELIMITER //

-- Trigger para INSERT
CREATE TRIGGER before_producto_insert 
BEFORE INSERT ON producto
FOR EACH ROW
BEGIN
    IF NEW.precioUnitario <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El precio unitario debe ser mayor que 0';
    END IF;
    IF NEW.stock < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El stock no puede ser negativo';
    END IF;
END//

-- Trigger para UPDATE
CREATE TRIGGER before_producto_update
BEFORE UPDATE ON producto
FOR EACH ROW
BEGIN
    IF NEW.precioUnitario <= 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El precio unitario debe ser mayor que 0';
    END IF;
    IF NEW.stock < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'El stock no puede ser negativo';
    END IF;
END//

DELIMITER ;

-- Insertar datos de prueba
INSERT INTO `cliente` VALUES 
(1,'Juan','Martinez','Av. Rivadavia 1234','1123456789'),
(2,'Maria','Gonzalez','San Martin 567','1187654321'),
(3,'Carlos','Rodriguez','Belgrano 890','1145678923'),
(4,'Ana','Fernandez','Mitre 432','1156789234');

INSERT INTO `deposito` VALUES 
(1,'Depósito Central','Av. San Juan 1500'),
(2,'Sucursal Norte','Cabildo 2800'),
(3,'Sucursal Sur','Av. Pavón 3200');

INSERT INTO `producto` (`idProducto`, `descripcion`, `precioUnitario`, `stock`) VALUES 
(1001,'Alfajor Milka',150.00,100),
(1002,'Coca Cola 500ml',200.00,150),
(1003,'Papas Lay\'s',80.00,200),
(1004,'Chocolate Aguila',120.00,80),
(1005,'Galletitas Oreo',90.00,120),
(1006,'Agua mineral 500ml',180.00,180),
(1007,'Caramelos Sugus',50.00,300),
(1008,'Chicles Beldent',70.00,250);

INSERT INTO `usuario` (`idUsuario`, `nombre`, `apellido`, `email`, `rol`, `contrasena`, `idDeposito`) VALUES 
(1,'Admin','System','admin@kioscoapp.com','admin','admin123',1),
(2,'Pedro','Lopez','pedro.lopez@kioscoapp.com','vendedor','vend123',2),
(3,'Laura','Garcia','laura.garcia@kioscoapp.com','vendedor','vend456',3),
(4,'Miguel','Torres','miguel.torres@kioscoapp.com','supervisor','super789',1);

INSERT INTO `pedido` (`idPedido`, `idCliente`, `fecha`, `estado`, `idDeposito`, `precioTotalPedido`) VALUES 
(1,1,CURRENT_TIMESTAMP,'En proceso',1,1350.00),
(2,2,CURRENT_TIMESTAMP,'Pendiente',2,1520.00),
(3,3,CURRENT_TIMESTAMP,'Terminado',1,1440.00),
(4,4,CURRENT_TIMESTAMP,'Pendiente',3,2100.00);

INSERT INTO `detallepedido` (`idDetallePedido`, `idPedido`) VALUES 
(1,1),(2,2),(3,3),(4,4);

INSERT INTO `detalleitem` (`idDetalleItem`, `idDetallePedido`, `idProducto`, `cantidad`, `precioUnitario`, `precioTotal`) VALUES 
(1,1,1001,5,150.00,750.00),
(2,1,1002,3,200.00,600.00),
(3,2,1003,10,80.00,800.00),
(4,2,1004,6,120.00,720.00),
(5,3,1005,8,90.00,720.00),
(6,3,1006,4,180.00,720.00);

-- Crear vista para resumen de pedidos
CREATE OR REPLACE VIEW vw_resumen_pedidos AS
SELECT 
    p.idPedido,
    p.fecha,
    p.estado,
    CONCAT(c.nombre, ' ', c.apellido) as cliente,
    d.nombre as deposito,
    COUNT(di.idDetalleItem) as cantidad_items,
    p.precioTotalPedido
FROM pedido p
JOIN cliente c ON p.idCliente = c.idCliente
JOIN deposito d ON p.idDeposito = d.idDeposito
JOIN detallepedido dp ON p.idPedido = dp.idPedido
LEFT JOIN detalleitem di ON dp.idDetallePedido = di.idDetallePedido
GROUP BY p.idPedido, p.fecha, p.estado, cliente, deposito, p.precioTotalPedido;

COMMIT;