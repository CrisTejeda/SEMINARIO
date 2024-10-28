package com.kioscoapp.model;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    private String contrasena;
    private Integer idDeposito;
    private String usuario;
    private boolean requiereCambioPassword;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String apellido,
                   String email, String rol, String contrasena, Integer idDeposito) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.contrasena = contrasena;
        this.idDeposito = idDeposito;
    }

    // Getters y Setters
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    public Integer getIdDeposito() { return idDeposito; }
    public void setIdDeposito(Integer idDeposito) { this.idDeposito = idDeposito; }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", rol='" + rol + '\'' +
                ", idDeposito=" + idDeposito +
                '}';
    }

    public void setPassword(String password) {
        this.contrasena = password;
    }

    public void setRequiereCambioPassword(boolean requiereCambioPassword) {
        this.requiereCambioPassword = requiereCambioPassword;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return this.idUsuario;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public Object getEstado() {
        return null;
    }

    public void setId(int id) {
    }

    public void setEstado(String string) {

    }
}