package com.kioscoapp.dao.interfaces;

import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.model.Pedido;
import java.util.List;

public interface PedidoDAO {
    void insertar(Pedido pedido) throws DAOException;
    void actualizar(Pedido pedido) throws DAOException;
    void eliminar(Integer idPedido) throws DAOException;

    void eliminar(int idPedido) throws DAOException;

    Pedido obtenerPorId(Integer idPedido) throws DAOException;
    List<Pedido> obtenerTodos() throws DAOException;
    List<Pedido> obtenerPedidosPorCliente(Integer idCliente) throws DAOException;
    void actualizarEstado(Integer idPedido, String estado) throws DAOException;
}