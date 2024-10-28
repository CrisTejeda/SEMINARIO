package com.kioscoapp.model;

public class Producto {
    private int idProducto;
    private String codigo;        // Añadido
    private String nombre;        // Añadido
    private String descripcion;
    private double precioUnitario;
    private Double precio;        // Añadido
    private int stock;
    private int stockMinimo;      // Añadido

    public Producto() {}

    public Producto(int idProducto, String codigo, String nombre, String descripcion,
                    double precioUnitario, int stock, int stockMinimo) {
        this.idProducto = idProducto;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.precio = (double) precioUnitario;
        this.stock = stock;
        this.stockMinimo = stockMinimo;
    }

    // Getters y Setters originales
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        this.precio = (double) precioUnitario;
    }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    // Nuevos getters y setters necesarios
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Double getPrecio() { return precio; }
    public void setPrecio(double precio) {
        this.precio = precio;
        this.precioUnitario = precio;
    }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + idProducto +
                ", codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioUnitario=" + precioUnitario +
                ", precio=" + precio +
                ", stock=" + stock +
                ", stockMinimo=" + stockMinimo +
                '}';
    }

    public Object getId() {
        return null;
    }

    public Producto getDeposito() {
        return null;
    }
}