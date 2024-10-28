package com.kioscoapp.dao.interfaces;

import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.model.Cliente;
import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente cliente) throws DAOException;
    void actualizar(Cliente cliente) throws DAOException;
    void eliminar(Integer idCliente) throws DAOException;
    Cliente obtenerPorId(Integer idCliente) throws DAOException;
    List<Cliente> obtenerTodos() throws DAOException;
}