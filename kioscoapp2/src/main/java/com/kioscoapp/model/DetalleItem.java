package com.kioscoapp.model;

public class DetalleItem {
    private int idDetalleItem;
    private int idDetallePedido;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;
    private double precioTotal;
    private Producto producto; // Referencia al producto

    public DetalleItem() {}

    public DetalleItem(int idDetalleItem, int idDetallePedido, int idProducto,
                       int cantidad, double precioUnitario, double precioTotal) {
        this.idDetalleItem = idDetalleItem;
        this.idDetallePedido = idDetallePedido;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
    }

    // Getters y Setters
    public int getIdDetalleItem() { return idDetalleItem; }
    public void setIdDetalleItem(int idDetalleItem) { this.idDetalleItem = idDetalleItem; }
    public int getIdDetallePedido() { return idDetallePedido; }
    public void setIdDetallePedido(int idDetallePedido) { this.idDetallePedido = idDetallePedido; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    @Override
    public String toString() {
        return "DetalleItem{" +
                "idDetalleItem=" + idDetalleItem +
                ", idDetallePedido=" + idDetallePedido +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", precioTotal=" + precioTotal +
                '}';
    }
}