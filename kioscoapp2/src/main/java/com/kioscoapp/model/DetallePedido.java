package com.kioscoapp.model;

import java.util.ArrayList;
import java.util.List;

public class DetallePedido {
    private int idDetallePedido;
    private int idPedido;
    private List<DetalleItem> items;

    public DetallePedido() {
        this.items = new ArrayList<>();
    }

    public DetallePedido(int idDetallePedido, int idPedido) {
        this.idDetallePedido = idDetallePedido;
        this.idPedido = idPedido;
        this.items = new ArrayList<>();
    }

    // Getters y Setters
    public int getIdDetallePedido() { return idDetallePedido; }
    public void setIdDetallePedido(int idDetallePedido) { this.idDetallePedido = idDetallePedido; }
    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public List<DetalleItem> getItems() { return items; }
    public void setItems(List<DetalleItem> items) { this.items = items; }

    @Override
    public String toString() {
        return "DetallePedido{" +
                "idDetallePedido=" + idDetallePedido +
                ", idPedido=" + idPedido +
                ", items=" + items +
                '}';
    }

    public Producto getProducto() {
        return null;
    }

    public double getCantidad() {
        return 0;
    }

    public double getPrecioUnitario(Object precio) {
        return 0;
    }

    public void setProducto(Producto producto) {
    }

    public void setCantidad(int cantidad) {
    }
}