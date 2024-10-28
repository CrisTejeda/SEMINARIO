package com.kioscoapp.dao.impl;

import com.kioscoapp.config.DatabaseConnection;
import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.dao.interfaces.PedidoDAO;
import com.kioscoapp.model.Pedido;
import com.kioscoapp.model.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    private static final Logger logger = LoggerFactory.getLogger(PedidoDAOImpl.class);

    @Override
    public void insertar(Pedido pedido) throws DAOException {
        String sql = "INSERT INTO pedido (idCliente, fecha, estado, idDeposito, precioTotalPedido) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setTimestamp(2, Timestamp.valueOf(pedido.getFecha()));
            stmt.setString(3, pedido.getEstado());
            stmt.setInt(4, pedido.getIdDeposito());
            stmt.setDouble(5, pedido.getPrecioTotalPedido());

            logger.debug("Ejecutando inserción de pedido para cliente ID: {}", pedido.getIdCliente());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setIdPedido(rs.getInt(1));
                    logger.debug("Pedido insertado con ID: {}", pedido.getIdPedido());
                }
            }
        } catch (SQLException e) {
            logger.error("Error al insertar pedido", e);
            throw new DAOException("Error al insertar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Pedido pedido) throws DAOException {
        String sql = "UPDATE pedido SET idCliente = ?, fecha = ?, estado = ?, " +
                "idDeposito = ?, precioTotalPedido = ? WHERE idPedido = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setTimestamp(2, Timestamp.valueOf(pedido.getFecha()));
            stmt.setString(3, pedido.getEstado());
            stmt.setInt(4, pedido.getIdDeposito());
            stmt.setDouble(5, pedido.getPrecioTotalPedido());
            stmt.setInt(6, pedido.getIdPedido());

            logger.debug("Actualizando pedido con ID: {}", pedido.getIdPedido());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                logger.warn("No se encontró el pedido con ID: {}", pedido.getIdPedido());
                throw new DAOException("No se encontró el pedido con ID: " + pedido.getIdPedido());
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar pedido", e);
            throw new DAOException("Error al actualizar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer idPedido) throws DAOException {
        String sql = "DELETE FROM pedido WHERE idPedido = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);

            logger.debug("Eliminando pedido con ID: {}", idPedido);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                logger.warn("No se encontró el pedido con ID: {}", idPedido);
                throw new DAOException("No se encontró el pedido con ID: " + idPedido);
            }
        } catch (SQLException e) {
            logger.error("Error al eliminar pedido", e);
            throw new DAOException("Error al eliminar pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int idPedido) throws DAOException {

    }

    @Override
    public Pedido obtenerPorId(Integer idPedido) throws DAOException {
        String sql = "SELECT p.*, c.nombre as nombreCliente, c.apellido as apellidoCliente, " +
                "c.direccion, c.telefono FROM pedido p " +
                "LEFT JOIN cliente c ON p.idCliente = c.idCliente " +
                "WHERE p.idPedido = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            logger.debug("Buscando pedido con ID: {}", idPedido);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(rs.getInt("idPedido"));
                    pedido.setIdCliente(rs.getInt("idCliente"));
                    pedido.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setIdDeposito(rs.getInt("idDeposito"));
                    pedido.setPrecioTotalPedido(rs.getDouble("precioTotalPedido"));

                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombreCliente"));
                    cliente.setApellido(rs.getString("apellidoCliente"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setTelefono(rs.getString("telefono"));
                    pedido.setCliente(cliente);

                    return pedido;
                } else {
                    logger.warn("No se encontró el pedido con ID: {}", idPedido);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error al obtener pedido por ID", e);
            throw new DAOException("Error al obtener pedido por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> obtenerTodos() throws DAOException {
        String sql = "SELECT p.*, c.nombre as nombreCliente, c.apellido as apellidoCliente, " +
                "c.direccion, c.telefono FROM pedido p " +
                "LEFT JOIN cliente c ON p.idCliente = c.idCliente";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            logger.debug("Obteniendo lista de todos los pedidos");

            while (rs.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(rs.getInt("idPedido"));
                pedido.setIdCliente(rs.getInt("idCliente"));
                pedido.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                pedido.setEstado(rs.getString("estado"));
                pedido.setIdDeposito(rs.getInt("idDeposito"));
                pedido.setPrecioTotalPedido(rs.getDouble("precioTotalPedido"));

                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombreCliente"));
                cliente.setApellido(rs.getString("apellidoCliente"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setTelefono(rs.getString("telefono"));
                pedido.setCliente(cliente);

                pedidos.add(pedido);
            }

            logger.debug("Se encontraron {} pedidos", pedidos.size());
            return pedidos;
        } catch (SQLException e) {
            logger.error("Error al obtener todos los pedidos", e);
            throw new DAOException("Error al obtener todos los pedidos: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Pedido> obtenerPedidosPorCliente(Integer idCliente) throws DAOException {
        String sql = "SELECT p.*, c.nombre as nombreCliente, c.apellido as apellidoCliente, " +
                "c.direccion, c.telefono FROM pedido p " +
                "LEFT JOIN cliente c ON p.idCliente = c.idCliente " +
                "WHERE p.idCliente = ?";
        List<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);
            logger.debug("Buscando pedidos para el cliente ID: {}", idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pedido pedido = new Pedido();
                    pedido.setIdPedido(rs.getInt("idPedido"));
                    pedido.setIdCliente(rs.getInt("idCliente"));
                    pedido.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                    pedido.setEstado(rs.getString("estado"));
                    pedido.setIdDeposito(rs.getInt("idDeposito"));
                    pedido.setPrecioTotalPedido(rs.getDouble("precioTotalPedido"));

                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNombre(rs.getString("nombreCliente"));
                    cliente.setApellido(rs.getString("apellidoCliente"));
                    cliente.setDireccion(rs.getString("direccion"));
                    cliente.setTelefono(rs.getString("telefono"));
                    pedido.setCliente(cliente);

                    pedidos.add(pedido);
                }
            }

            logger.debug("Se encontraron {} pedidos para el cliente ID: {}", pedidos.size(), idCliente);
            return pedidos;
        } catch (SQLException e) {
            logger.error("Error al obtener pedidos por cliente", e);
            throw new DAOException("Error al obtener pedidos por cliente: " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizarEstado(Integer idPedido, String estado) throws DAOException {
        String sql = "UPDATE pedido SET estado = ? WHERE idPedido = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado);
            stmt.setInt(2, idPedido);

            logger.debug("Actualizando estado del pedido ID: {} a: {}", idPedido, estado);
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                logger.warn("No se encontró el pedido con ID: {}", idPedido);
                throw new DAOException("No se encontró el pedido con ID: " + idPedido);
            }
        } catch (SQLException e) {
            logger.error("Error al actualizar estado del pedido", e);
            throw new DAOException("Error al actualizar estado del pedido: " + e.getMessage(), e);
        }
    }
}