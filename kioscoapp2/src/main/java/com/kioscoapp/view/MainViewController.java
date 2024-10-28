package com.kioscoapp.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MainViewController {
    @FXML
    private Label lblUsuarioActual;
    @FXML
    private Button btnVentas;
    @FXML
    private Button btnInventario;
    @FXML
    private Button btnUsuarios;
    @FXML
    private Button btnReportes;
    @FXML
    private Button btnCerrarSesion;

    private String nombreUsuario;

    @FXML
    public void initialize() {
        // Aquí puedes inicializar cualquier configuración adicional
    }

    public void setUsuario(String usuario) {
        this.nombreUsuario = usuario;
        lblUsuarioActual.setText("Bienvenido: " + usuario);
    }

    @FXML
    private void handleVentas(ActionEvent event) {
        try {
            cargarVista("/fxml/ventas_view.fxml", "Ventas");
        } catch (IOException e) {
            mostrarError("Error al abrir la vista de ventas", e);
        }
    }

    @FXML
    private void handleInventario(ActionEvent event) {
        try {
            cargarVista("/fxml/inventario_view.fxml", "Inventario");
        } catch (IOException e) {
            mostrarError("Error al abrir la vista de inventario", e);
        }
    }

    @FXML
    private void handleUsuarios(ActionEvent event) {
        try {
            cargarVista("/fxml/usuarios_view.fxml", "Usuarios");
        } catch (IOException e) {
            mostrarError("Error al abrir la vista de usuarios", e);
        }
    }

    @FXML
    private void handleReportes(ActionEvent event) {
        try {
            cargarVista("/fxml/reportes_view.fxml", "Reportes");
        } catch (IOException e) {
            mostrarError("Error al abrir la vista de reportes", e);
        }
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        try {
            // Cargar la vista de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent loginView = loader.load();

            // Obtener el stage actual
            Stage stage = (Stage) btnCerrarSesion.getScene().getWindow();

            // Configurar la nueva escena
            Scene scene = new Scene(loginView);
            stage.setScene(scene);
            stage.setTitle("KioscoApp - Login");
            stage.show();
        } catch (IOException e) {
            mostrarError("Error al cerrar sesión", e);
        }
    }

    private void cargarVista(String fxml, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent vista = loader.load();
        Stage stage = (Stage) btnVentas.getScene().getWindow();
        stage.setScene(new Scene(vista));
        stage.setTitle("KioscoApp - " + titulo);
        stage.show();
    }

    private void mostrarError(String mensaje, Exception e) {
        e.printStackTrace();
        // Aquí puedes implementar tu propia lógica de mostrar errores
        // Por ejemplo, usando Alert de JavaFX
    }
}