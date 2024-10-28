package com.kioscoapp.dao.impl;

import com.kioscoapp.config.DatabaseConnection;
import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.dao.interfaces.ClienteDAO;
import com.kioscoapp.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    private static final Logger logger = LoggerFactory.getLogger(ClienteDAOImpl.class);

    @Override
    public void insertar(Cliente cliente) throws DAOException {
        String sql = "INSERT INTO cliente (nombre, apellido, direccion, telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getDireccion());
            stmt.setString(4, cliente.getTelefono());

            logger.debug("Ejecutando inserción de cliente: {}", cliente.getNombre());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setIdCliente(rs.getInt(1));
                    logger.debug("Cliente insertado con ID: {}", cliente.getIdCliente());
                }
            }
        } catch (SQLException e) {
            logger.error("Error al insertar cliente", e);
            throw new DAOException("Error al insertar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Cliente cliente) throws DAOException {
        String sql = "UPDATE cliente SET nombre = ?, apellido = ?, direccion = ?, telefono = ? WHERE idCliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getDireccion());
            stmt.setString(4, cliente.getTelefono());
            stmt.setInt(5, cliente.getIdCliente());

            logger.debug("Actualizando cliente con ID: {}", cliente.getIdCliente());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                logger.warn("No se encontró el cliente con ID: {}", cliente.getIdCliente());
                throw new DAOException("No se encontró el cliente con ID: " + cliente.getIdCliente());
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar cliente", e);
            throw new DAOException("Error al actualizar cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer idCliente) throws DAOException {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            logger.debug("Eliminando cliente con ID: {}", idCliente);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                logger.warn("No se encontró el cliente con ID: {}", idCliente);
                throw new DAOException("No se encontró el cliente con ID: " + idCliente);
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar cliente", e);
            throw new DAOException("Error al eliminar cliente: " + e.getMessage(), e);
        }
    }

    public Cliente obtenerPorId() throws DAOException {
        return obtenerPorId(null);
    }

    @Override
    public Cliente obtenerPorId(Integer idCliente) throws DAOException {
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            logger.debug("Buscando cliente con ID: {}", idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombre"));
                    cliente.setApellido(rs.getString("apellido"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setTelefono(rs.getString("telefono"));
                    return cliente;
                } else {
                    logger.warn("No se encontró el cliente con ID: {}", idCliente);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener cliente por ID", e);
            throw new DAOException("Error al obtener cliente por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Cliente> obtenerTodos() throws DAOException {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            logger.debug("Obteniendo lista de todos los clientes");

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                clientes.add(cliente);
            }

            logger.debug("Se encontraron {} clientes", clientes.size());
            return clientes;
        } catch (SQLException e) {
            logger.error("Error al obtener todos los clientes", e);
            throw new DAOException("Error al obtener todos los clientes: " + e.getMessage(), e);
        }
    }
}