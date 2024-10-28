package com.kioscoapp;

import com.kioscoapp.view.MainView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    private TextField usuarioField;
    private PasswordField passwordField;
    private Label mensajeLabel;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Crear una interfaz básica si el FXML falla
            VBox root = new VBox(10);
            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(20));
            root.setPrefSize(300, 400);

            // Título
            Label titleLabel = new Label("KioscoApp Login");
            titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            // Campos de login
            usuarioField = new TextField();
            usuarioField.setPromptText("Usuario");
            usuarioField.setMaxWidth(200);

            passwordField = new PasswordField();
            passwordField.setPromptText("Contraseña");
            passwordField.setMaxWidth(200);

            // Botón de login
            Button loginButton = new Button("Iniciar Sesión");
            loginButton.setDefaultButton(true);
            loginButton.setMaxWidth(200);

            // Label para mensajes
            mensajeLabel = new Label();
            mensajeLabel.setWrapText(true);
            mensajeLabel.setMaxWidth(200);

            // Agregar elementos al VBox
            root.getChildren().addAll(
                    titleLabel,
                    new Label("Usuario:"),
                    usuarioField,
                    new Label("Contraseña:"),
                    passwordField,
                    loginButton,
                    mensajeLabel
            );

            // Manejar el evento de login
            loginButton.setOnAction(e -> handleLogin(primaryStage));

            // Crear y configurar la escena
            Scene scene = new Scene(root);

            // Configurar el stage principal
            primaryStage.setTitle("KioscoApp - Login");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);

            // Manejar el cierre de la aplicación
            primaryStage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);
            });

            // Mostrar la ventana
            primaryStage.show();

        } catch (Exception e) {
            mostrarError("Error al iniciar la aplicación", e);
            Platform.exit();
            System.exit(1);
        }
    }

    private void handleLogin(Stage primaryStage) {
        String usuario = usuarioField.getText();
        String password = passwordField.getText();

        // Validación básica de campos vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Por favor complete todos los campos", true);
            return;
        }

        try {
            // Validar credenciales
            if (validarCredenciales(usuario, password)) {
                mostrarMensaje("Login exitoso!", false);

                // Cerrar la ventana de JavaFX
                primaryStage.close();

                // Iniciar la aplicación Swing en un nuevo hilo
                SwingUtilities.invokeLater(() -> {
                    try {
                        // Crear y mostrar la ventana principal
                        MainView mainView = new MainView(usuario);
                        mainView.setVisible(true);
                    } catch (Exception ex) {
                        mostrarError("Error al abrir la ventana principal", ex);
                    }
                });

            } else {
                mostrarMensaje("Usuario o contraseña incorrectos", true);
                passwordField.clear();
            }
        } catch (Exception e) {
            mostrarError("Error durante el login", e);
        }
    }

    private boolean validarCredenciales(String usuario, String password) {
        // Simulación de validación - Reemplazar con lógica real de autenticación
        return usuario.equals("admin") && password.equals("admin");
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setStyle(esError ?
                "-fx-text-fill: red; -fx-font-weight: bold;" :
                "-fx-text-fill: green; -fx-font-weight: bold;");
    }

    private void mostrarError(String mensaje, Exception e) {
        e.printStackTrace(); // Para logging

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(mensaje);
        alert.setContentText(e.getMessage());

        // Crear un área expandible con el stack trace
        TextArea textArea = new TextArea(e.toString());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        alert.getDialogPane().setExpandableContent(textArea);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}