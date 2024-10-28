package com.kioscoapp.dao.interfaces;

import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.model.Producto;
import java.util.List;
import java.util.Map;

public interface ProductoDAO {
    List<Producto> buscarPorCriterios(Map<String, String> criterios);

    void insertar(Producto producto) throws DAOException;
    boolean actualizar(Producto producto) throws DAOException;
    boolean eliminar(Long idProducto) throws DAOException;
    Producto obtenerPorId(Integer idProducto) throws DAOException;
    List<Producto> obtenerTodos() throws DAOException;
    void actualizarStock(Integer idProducto, Integer nuevoStock) throws DAOException;

    List<Producto> listarProductosStockBajo();

    boolean crear(Producto producto);

    Producto buscarPorId(Long idProducto);

    Producto buscarPorCodigo(String codigo);

    List<Producto> listarTodos();

    List<Producto> buscarPorCriterios(String criterios);
}