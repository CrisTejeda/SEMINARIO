-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema SistemaKiosco
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema SistemaKiosco
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SistemaKiosco` DEFAULT CHARACTER SET utf8 ;
USE `SistemaKiosco` ;

-- -----------------------------------------------------
-- Table `SistemaKiosco`.`Clientes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`Clientes` (
  `rut` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`rut`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`categorias`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`categorias` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `descripcion` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`proveedores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`proveedores` (
  `rut` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(45) NULL,
  `pagina web` VARCHAR(45) NULL,
  PRIMARY KEY (`rut`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`productos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`productos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `precio` DECIMAL(10,2) NOT NULL,
  `stock` INT NOT NULL,
  `Codigo de Barra` VARCHAR(45) NOT NULL,
  `Fecha_vencimiento` DATE NOT NULL,
  `Fecha_elaboracion` VARCHAR(45) NOT NULL,
  `categorias_idcategorias` INT NOT NULL,
  `proveedores_idproveedores` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_productos_categorias_idx` (`categorias_idcategorias` ASC) VISIBLE,
  INDEX `fk_productos_proveedores1_idx` (`proveedores_idproveedores` ASC) VISIBLE,
  CONSTRAINT `fk_productos_categorias`
    FOREIGN KEY (`categorias_idcategorias`)
    REFERENCES `SistemaKiosco`.`categorias` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_productos_proveedores1`
    FOREIGN KEY (`proveedores_idproveedores`)
    REFERENCES `SistemaKiosco`.`proveedores` (`rut`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`direccion_proveedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`direccion_proveedor` (
  `proveedores_rut` INT NOT NULL,
  `calle` VARCHAR(45) NOT NULL,
  `numero` VARCHAR(45) NULL,
  `ciudad` VARCHAR(45) NULL,
  `comuna` VARCHAR(45) NULL,
  PRIMARY KEY (`proveedores_rut`),
  CONSTRAINT `fk_direccion_proveedor_proveedores1`
    FOREIGN KEY (`proveedores_rut`)
    REFERENCES `SistemaKiosco`.`proveedores` (`rut`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`direccion_cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`direccion_cliente` (
  `calle` VARCHAR(45) NOT NULL,
  `numero` VARCHAR(45) NULL,
  `ciudad` VARCHAR(45) NULL,
  `comuna` VARCHAR(45) NULL,
  `Clientes_rut` INT NOT NULL,
  PRIMARY KEY (`Clientes_rut`),
  CONSTRAINT `fk_direccion_cliente_Clientes1`
    FOREIGN KEY (`Clientes_rut`)
    REFERENCES `SistemaKiosco`.`Clientes` (`rut`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`telefonos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`telefonos` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `numero` VARCHAR(45) NOT NULL,
  `Clientes_rut` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_telefonos_Clientes1_idx` (`Clientes_rut` ASC) VISIBLE,
  CONSTRAINT `fk_telefonos_Clientes1`
    FOREIGN KEY (`Clientes_rut`)
    REFERENCES `SistemaKiosco`.`Clientes` (`rut`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`Vendedor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`Vendedor` (
  `idVendedor` INT NOT NULL AUTO_INCREMENT,
  `Nombre` VARCHAR(45) NOT NULL,
  `telefono` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`idVendedor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`ventas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`ventas` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATE NOT NULL,
  `descuento` DECIMAL(10,2) NULL,
  `Clientes_rut` INT NOT NULL,
  `Vendedor_idVendedor` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_ventas_Clientes1_idx` (`Clientes_rut` ASC) VISIBLE,
  INDEX `fk_ventas_Vendedor1_idx` (`Vendedor_idVendedor` ASC) VISIBLE,
  CONSTRAINT `fk_ventas_Clientes1`
    FOREIGN KEY (`Clientes_rut`)
    REFERENCES `SistemaKiosco`.`Clientes` (`rut`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ventas_Vendedor1`
    FOREIGN KEY (`Vendedor_idVendedor`)
    REFERENCES `SistemaKiosco`.`Vendedor` (`idVendedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SistemaKiosco`.`detalle_venta`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SistemaKiosco`.`detalle_venta` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `precio` DECIMAL(10,2) NOT NULL,
  `cantidad` INT NOT NULL,
  `ventas_idventas` INT NOT NULL,
  `productos_idproductos` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_datalle_venta_ventas1_idx` (`ventas_idventas` ASC) VISIBLE,
  INDEX `fk_detalle_venta_productos1_idx` (`productos_idproductos` ASC) VISIBLE,
  CONSTRAINT `fk_datalle_venta_ventas1`
    FOREIGN KEY (`ventas_idventas`)
    REFERENCES `SistemaKiosco`.`ventas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_venta_productos1`
    FOREIGN KEY (`productos_idproductos`)
    REFERENCES `SistemaKiosco`.`productos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
