package com.kioscoapp.model;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private LocalDateTime fecha;
    private String estado;
    private int idDeposito;
    private double precioTotalPedido;
    private Cliente cliente; // Referencia al cliente
    private DetallePedido detallePedido; // Referencia al detalle del pedido

    public Pedido() {}

    public Pedido(int idPedido, int idCliente, LocalDateTime fecha,
                  String estado, int idDeposito, double precioTotalPedido) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
        this.idDeposito = idDeposito;
        this.precioTotalPedido = precioTotalPedido;
    }

    // Getters y Setters
    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getIdDeposito() { return idDeposito; }
    public void setIdDeposito(int idDeposito) { this.idDeposito = idDeposito; }
    public double getPrecioTotalPedido() { return precioTotalPedido; }
    public void setPrecioTotalPedido(double precioTotalPedido) { this.precioTotalPedido = precioTotalPedido; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public DetallePedido getDetallePedido() { return detallePedido; }
    public void setDetallePedido(DetallePedido detallePedido) { this.detallePedido = detallePedido; }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", idCliente=" + idCliente +
                ", fecha=" + fecha +
                ", estado='" + estado + '\'' +
                ", idDeposito=" + idDeposito +
                ", precioTotalPedido=" + precioTotalPedido +
                '}';
    }

    public void setDetalles(List<DetallePedido> detallesPedido) {
    }
}