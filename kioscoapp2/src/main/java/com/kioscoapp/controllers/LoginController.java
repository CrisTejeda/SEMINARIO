package com.kioscoapp.controllers;

import com.kioscoapp.dao.exceptions.DAOException;
import com.kioscoapp.dao.interfaces.UsuarioDAO;
import com.kioscoapp.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.util.List;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label messageLabel;

    private UsuarioDAO usuarioDAO;

    @FXML
    public void initialize() {
        usuarioDAO = new UsuarioDAO() {
            @Override
            public void insertar(Usuario usuario) throws DAOException {
                
            }

            @Override
            public void actualizar(Usuario usuario) throws DAOException {

            }

            @Override
            public void eliminar(Integer idUsuario) throws DAOException {

            }

            @Override
            public void eliminar(int idUsuario) throws DAOException {

            }

            @Override
            public Usuario obtenerPorId(Integer idUsuario) throws DAOException {
                return null;
            }

            @Override
            public List<Usuario> obtenerTodos() throws DAOException {
                return List.of();
            }

            @Override
            public Usuario obtenerPorEmail(String email) throws DAOException {
                return null;
            }

            @Override
            public List<Usuario> obtenerPorRol(String rol) throws DAOException {
                return List.of();
            }

            @Override
            public List<Usuario> obtenerPorDeposito(int idDeposito) throws DAOException {
                return List.of();
            }

            @Override
            public boolean validarCredenciales(String email, String contrasena) throws DAOException {
                return false;
            }

            @Override
            public Usuario findByEmail(String email) throws DAOException {
                return null;
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
        };
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Por favor complete todos los campos");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Usuario usuario = usuarioDAO.findByEmail(email);

            if (usuario != null && usuario.getContrasena().equals(password)) {
                // Login exitoso
                messageLabel.setText("Login exitoso");
                messageLabel.setStyle("-fx-text-fill: green;");

                // Cargar la vista principal según el rol
                loadMainView(event, usuario);
            } else {
                messageLabel.setText("Credenciales inválidas");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            messageLabel.setText("Error al iniciar sesión: " + e.getMessage());
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void loadMainView(ActionEvent event, Usuario usuario) throws Exception {
        String fxmlFile;

        // Seleccionar la vista según el rol del usuario
        switch (usuario.getRol().toLowerCase()) {
            case "admin":
                fxmlFile = "/fxml/inventario.fxml";
                break;
            case "vendedor":
                fxmlFile = "/fxml/ventas.fxml";
                break;
            default:
                throw new IllegalStateException("Rol no válido: " + usuario.getRol());
        }

        // Cargar la nueva vista
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();

        // Obtener el controlador de la nueva vista y pasarle el usuario si es necesario
        // BaseController controller = loader.getController();
        // controller.setUsuario(usuario);

        // Mostrar la nueva vista
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("KioscoApp - " + usuario.getRol());
        stage.setMaximized(true);
        stage.show();
    }
}