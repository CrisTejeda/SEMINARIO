package com.kioscoapp.model;

public class Deposito {
    private int idDeposito;
    private String nombre;
    private String direccion;

    public Deposito(int i, String todosLosDepósitos) {}

    public Deposito(int idDeposito, String nombre, String direccion) {
        this.idDeposito = idDeposito;
        this.nombre = nombre;
        this.direccion = direccion;
    }

    // Getters y Setters
    public int getIdDeposito() { return idDeposito; }
    public void setIdDeposito(int idDeposito) { this.idDeposito = idDeposito; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    @Override
    public String toString() {
        return "Deposito{" +
                "idDeposito=" + idDeposito +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public int getId() {
        return 0;
    }
}