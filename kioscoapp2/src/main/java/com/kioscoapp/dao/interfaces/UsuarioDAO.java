package com.kioscoapp.dao.interfaces;

import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    void insertar(Usuario usuario) throws DAOException;
    void actualizar(Usuario usuario) throws DAOException;
    void eliminar(Integer idUsuario) throws DAOException;

    void eliminar(int idUsuario) throws DAOException;

    Usuario obtenerPorId(Integer idUsuario) throws DAOException;
    List<Usuario> obtenerTodos() throws DAOException;
    Usuario obtenerPorEmail(String email) throws DAOException;

    List<Usuario> obtenerPorRol(String rol) throws DAOException;

    List<Usuario> obtenerPorDeposito(int idDeposito) throws DAOException;

    boolean validarCredenciales(String email, String contrasena) throws DAOException;

    Usuario findByEmail(String email) throws DAOException;

    Usuario buscarPorUsername(String username);

    void crear(Usuario usuario);

    List<Usuario> listarTodos();
}