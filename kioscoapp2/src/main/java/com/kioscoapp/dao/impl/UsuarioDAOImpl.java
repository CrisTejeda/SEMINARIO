package com.kioscoapp.dao.impl;

import com.kioscoapp.config.DatabaseConnection;
import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.dao.interfaces.UsuarioDAO;
import com.kioscoapp.model.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioDAOImpl.class);

    @Override
    public void insertar(Usuario usuario) throws DAOException {
        String sql = "INSERT INTO usuario (nombre, apellido, email, rol, contrasena, idDeposito) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getContrasena());
            if (usuario.getIdDeposito() != null) {
                stmt.setInt(6, usuario.getIdDeposito());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }

            logger.debug("Ejecutando inserción de usuario: {}", usuario.getEmail());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                    logger.debug("Usuario insertado con ID: {}", usuario.getIdUsuario());
                }
            }
        } catch (SQLException e) {
            logger.error("Error al insertar usuario", e);
            throw new DAOException("Error al insertar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws DAOException {
        String sql = "UPDATE usuario SET nombre = ?, apellido = ?, email = ?, " +
                "rol = ?, contrasena = ?, idDeposito = ? WHERE idUsuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getApellido());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getContrasena());
            if (usuario.getIdDeposito() != null) {
                stmt.setInt(6, usuario.getIdDeposito());
            } else {
                stmt.setNull(6, Types.INTEGER);
            }
            stmt.setInt(7, usuario.getIdUsuario());

            logger.debug("Actualizando usuario con ID: {}", usuario.getIdUsuario());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                logger.warn("No se encontró el usuario con ID: {}", usuario.getIdUsuario());
                throw new DAOException("No se encontró el usuario con ID: " + usuario.getIdUsuario());
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar usuario", e);
            throw new DAOException("Error al actualizar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer idUsuario) throws DAOException {
        String sql = "DELETE FROM usuario WHERE idUsuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            logger.debug("Eliminando usuario con ID: {}", idUsuario);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                logger.warn("No se encontró el usuario con ID: {}", idUsuario);
                throw new DAOException("No se encontró el usuario con ID: " + idUsuario);
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar usuario", e);
            throw new DAOException("Error al eliminar usuario: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int idUsuario) throws DAOException {
        eliminar(Integer.valueOf(idUsuario));
    }

    @Override
    public Usuario obtenerPorId(Integer idUsuario) throws DAOException {
        String sql = "SELECT * FROM usuario WHERE idUsuario = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            logger.debug("Buscando usuario con ID: {}", idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                } else {
                    logger.warn("No se encontró el usuario con ID: {}", idUsuario);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por ID", e);
            throw new DAOException("Error al obtener usuario por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario findByEmail(String email) throws DAOException {
        String sql = "SELECT * FROM usuario WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            logger.debug("Buscando usuario con email: {}", email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearUsuario(rs);
                } else {
                    logger.warn("No se encontró el usuario con email: {}", email);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener usuario por email", e);
            throw new DAOException("Error al obtener usuario por email: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return null;
    }

    @Override
    public void crear(Usuario usuario) {

    }

    @Override
    public List<Usuario> listarTodos() {
        return List.of();
    }

    @Override
    public List<Usuario> obtenerTodos() throws DAOException {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            logger.debug("Obteniendo lista de todos los usuarios");

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

            logger.debug("Se encontraron {} usuarios", usuarios.size());
            return usuarios;
        } catch (SQLException e) {
            logger.error("Error al obtener todos los usuarios", e);
            throw new DAOException("Error al obtener todos los usuarios: " + e.getMessage(), e);
        }
    }

    @Override
    public Usuario obtenerPorEmail(String email) throws DAOException {
        return null;
    }

    @Override
    public List<Usuario> obtenerPorRol(String rol) throws DAOException {
        String sql = "SELECT * FROM usuario WHERE rol = ?";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, rol);
            logger.debug("Buscando usuarios con rol: {}", rol);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }

            logger.debug("Se encontraron {} usuarios con rol {}", usuarios.size(), rol);
            return usuarios;
        } catch (SQLException e) {
            logger.error("Error al obtener usuarios por rol", e);
            throw new DAOException("Error al obtener usuarios por rol: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Usuario> obtenerPorDeposito(int idDeposito) throws DAOException {
        String sql = "SELECT * FROM usuario WHERE idDeposito = ?";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDeposito);
            logger.debug("Buscando usuarios del depósito ID: {}", idDeposito);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    usuarios.add(mapearUsuario(rs));
                }
            }

            logger.debug("Se encontraron {} usuarios en el depósito {}", usuarios.size(), idDeposito);
            return usuarios;
        } catch (SQLException e) {
            logger.error("Error al obtener usuarios por depósito", e);
            throw new DAOException("Error al obtener usuarios por depósito: " + e.getMessage(), e);
        }
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellido(rs.getString("apellido"));
        usuario.setEmail(rs.getString("email"));
        usuario.setRol(rs.getString("rol"));
        usuario.setContrasena(rs.getString("contrasena"));

        int idDeposito = rs.getInt("idDeposito");
        if (!rs.wasNull()) {
            usuario.setIdDeposito(idDeposito);
        }

        return usuario;
    }

    @Override
    public boolean validarCredenciales(String email, String contrasena) throws DAOException {
        String sql = "SELECT COUNT(*) FROM usuario WHERE email = ? AND contrasena = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, contrasena);

            logger.debug("Validando credenciales para usuario: {}", email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
                return false;
            }
        } catch (SQLException e) {
            logger.error("Error al validar credenciales", e);
            throw new DAOException("Error al validar credenciales: " + e.getMessage(), e);
        }
    }
}