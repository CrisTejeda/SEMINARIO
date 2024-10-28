package com.kioscoapp.controllers;

import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.dao.interfaces.ProductoDAO;
import com.kioscoapp.dao.impl.ProductoDAOImpl;
import com.kioscoapp.model.Producto;
import com.kioscoapp.model.Deposito;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para la gestión del inventario de productos.
 */
public class InventarioController {
    private static final Logger logger = LoggerFactory.getLogger(InventarioController.class);
    private final ProductoDAO productoDAO;

    public InventarioController() {
        this.productoDAO = new ProductoDAOImpl();
    }

    /**
     * Registra un nuevo producto en el inventario
     * @param producto Producto a registrar
     * @return true si se registró correctamente, false en caso contrario
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public boolean registrarProducto(Producto producto) throws DAOException {
        try {
            if (!validarProducto(producto)) {
                logger.warn("Intento de registro de producto con datos inválidos: {}", producto);
                return false;
            }

            // Verificar si ya existe un producto con el mismo código
            if (productoDAO.buscarPorCodigo(producto.getCodigo()) != null) {
                logger.warn("Intento de registro de producto con código duplicado: {}", producto.getCodigo());
                return false;
            }

            return productoDAO.crear(producto);
        } catch (Exception e) {
            logger.error("Error al registrar producto: {}", producto, e);
            throw new DAOException("Error al registrar producto", e);
        }
    }

    /**
     * Actualiza la información de un producto existente
     * @param producto Producto con la información actualizada
     * @return true si se actualizó correctamente, false en caso contrario
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public boolean actualizarProducto(Producto producto) throws DAOException {
        try {
            if (!validarProducto(producto)) {
                logger.warn("Intento de actualización de producto con datos inválidos: {}", producto);
                return false;
            }

            // Verificar que el producto existe
            Producto productoExistente = productoDAO.buscarPorId((long) producto.getIdProducto());
            if (productoExistente == null) {
                logger.warn("Intento de actualización de producto inexistente: {}", producto.getIdProducto());
                return false;
            }

            return productoDAO.actualizar(producto);
        } catch (Exception e) {
            logger.error("Error al actualizar producto: {}", producto, e);
            throw new DAOException("Error al actualizar producto", e);
        }
    }

    /**
     * Elimina un producto del inventario
     * @param idProducto ID del producto a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public boolean eliminarProducto(Long idProducto) throws DAOException {
        try {
            if (idProducto == null || idProducto <= 0) {
                logger.warn("Intento de eliminación de producto con ID inválido: {}", idProducto);
                return false;
            }

            // Verificar que el producto existe antes de eliminarlo
            Producto producto = productoDAO.buscarPorId(idProducto);
            if (producto == null) {
                logger.warn("Intento de eliminación de producto inexistente: {}", idProducto);
                return false;
            }

            return productoDAO.eliminar(idProducto);
        } catch (Exception e) {
            logger.error("Error al eliminar producto con ID: {}", idProducto, e);
            throw new DAOException("Error al eliminar producto", e);
        }
    }

    /**
     * Busca un producto por su ID
     * @param idProducto ID del producto
     * @return Optional con el producto encontrado
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public Optional<Producto> buscarProductoPorId(Long idProducto) throws DAOException {
        try {
            if (idProducto == null || idProducto <= 0) {
                logger.warn("Intento de búsqueda de producto con ID inválido: {}", idProducto);
                return Optional.empty();
            }

            return Optional.ofNullable(productoDAO.buscarPorId(idProducto));
        } catch (Exception e) {
            logger.error("Error al buscar producto por ID: {}", idProducto, e);
            throw new DAOException("Error al buscar producto por ID", e);
        }
    }

    /**
     * Busca un producto por su código
     * @param codigo Código del producto
     * @return Optional con el producto encontrado
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public Optional<Producto> buscarProductoPorCodigo(String codigo) throws DAOException {
        try {
            if (codigo == null || codigo.trim().isEmpty()) {
                logger.warn("Intento de búsqueda de producto con código inválido");
                return Optional.empty();
            }

            return Optional.ofNullable(productoDAO.buscarPorCodigo(codigo));
        } catch (Exception e) {
            logger.error("Error al buscar producto por código: {}", codigo, e);
            throw new DAOException("Error al buscar producto por código", e);
        }
    }

    /**
     * Obtiene todos los productos del inventario
     * @return Lista de productos
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public List<Producto> listarProductos() throws DAOException {
        try {
            List<Producto> productos = productoDAO.listarTodos();
            return productos != null ? productos : Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error al listar productos", e);
            throw new DAOException("Error al listar productos", e);
        }
    }

    /**
     * Actualiza el stock de un producto
     * @param idProducto ID del producto
     * @param cantidad Cantidad a agregar (positivo) o restar (negativo)
     * @param deposito Depósito donde se realizará el movimiento
     * @return true si se actualizó correctamente, false en caso contrario
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public boolean actualizarStock(Long idProducto, int cantidad, Deposito deposito) throws DAOException {
        try {
            if (idProducto == null || idProducto <= 0) {
                logger.warn("Intento de actualización de stock con ID de producto inválido: {}", idProducto);
                return false;
            }

            Optional<Producto> productoOpt = buscarProductoPorId(idProducto);
            if (!productoOpt.isPresent()) {
                logger.warn("Intento de actualización de stock de producto inexistente: {}", idProducto);
                return false;
            }

            Producto producto = productoOpt.get();
            int nuevoStock = producto.getStock() + cantidad;

            if (nuevoStock < 0) {
                logger.warn("Intento de actualización de stock que resultaría en cantidad negativa. Producto: {}, Cantidad: {}",
                        idProducto, cantidad);
                return false;
            }

            producto.setStock(nuevoStock);
            return productoDAO.actualizar(producto);
        } catch (Exception e) {
            logger.error("Error al actualizar stock del producto: {}. Cantidad: {}", idProducto, cantidad, e);
            throw new DAOException("Error al actualizar stock", e);
        }
    }

    /**
     * Busca productos por varios criterios
     * @param criterios Mapa de criterios de búsqueda
     * @return Lista de productos que coinciden con los criterios
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public List<Producto> buscarProductos(String criterios) throws DAOException {
        try {
            if (criterios == null || criterios.isEmpty()) {
                logger.warn("Intento de búsqueda con criterios vacíos");
                return Collections.emptyList();
            }

            List<Producto> productos = productoDAO.buscarPorCriterios(criterios);
            return productos != null ? productos : Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error al buscar productos por criterios: {}", criterios, e);
            throw new DAOException("Error al buscar productos por criterios", e);
        }
    }

    /**
     * Obtiene una lista de productos con stock por debajo del mínimo
     * @return Lista de productos con stock bajo
     * @throws DAOException si ocurre un error en la capa de acceso a datos
     */
    public List<Producto> obtenerProductosStockBajo() throws DAOException {
        try {
            List<Producto> productos = productoDAO.listarProductosStockBajo();
            return productos != null ? productos : Collections.emptyList();
        } catch (Exception e) {
            logger.error("Error al obtener productos con stock bajo", e);
            throw new DAOException("Error al obtener productos con stock bajo", e);
        }
    }

    /**
     * Valida los datos básicos de un producto
     * @param producto Producto a validar
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarProducto(Producto producto) {
        if (producto == null) {
            logger.debug("Producto nulo en validación");
            return false;
        }

        if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
            logger.debug("Código de producto inválido en validación");
            return false;
        }

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            logger.debug("Nombre de producto inválido en validación");
            return false;
        }

        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            logger.debug("Precio de producto inválido en validación");
            return false;
        }

        if (producto.getStock() < 0) {
            logger.debug("Stock de producto inválido en validación");
            return false;
        }

        if (producto.getStockMinimo() < 0) {
            logger.debug("Stock mínimo de producto inválido en validación");
            return false;
        }

        return true;
    }

    public List<Deposito> obtenerDepositos() {
        return List.of();
    }

    public List<Producto> obtenerProductosPorDeposito(int id) {
        return List.of();
    }

    public List<Producto> obtenerTodosLosProductos() {
        return null;
    }
}