package com.kioscoapp.controllers;

import com.kioscoapp.dao.interfaces.UsuarioDAO;
import com.kioscoapp.dao.impl.UsuarioDAOImpl;
import com.kioscoapp.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> tablaUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> colId;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colUsuario;

    @FXML
    private TableColumn<Usuario, String> colRol;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private ComboBox<String> comboRol;

    private UsuarioDAO usuarioDAO;
    private ObservableList<Usuario> listaUsuarios;

    public UsuariosController() {
        usuarioDAO = new UsuarioDAOImpl();
        listaUsuarios = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar las columnas de la tabla
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));

        // Inicializar el ComboBox de roles
        comboRol.setItems(FXCollections.observableArrayList("ADMIN", "VENDEDOR", "INVENTARIO"));

        // Cargar los usuarios
        cargarUsuarios();

        // Configurar el listener para la selección de usuarios
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                mostrarDetallesUsuario(newSelection);
            }
        });
    }

    @FXML
    private void guardarUsuario() {
        if (validarCampos()) {
            Usuario usuario = new Usuario();
            usuario.setNombre(txtNombre.getText());
            usuario.setUsuario(txtUsuario.getText());
            usuario.setPassword(txtPassword.getText());
            usuario.setRol(comboRol.getValue());

            try {
                if (tablaUsuarios.getSelectionModel().getSelectedItem() != null) {
                    // Actualizar usuario existente
                    usuario.setIdUsuario(tablaUsuarios.getSelectionModel().getSelectedItem().getId());
                    usuarioDAO.actualizar(usuario);
                } else {
                    // Crear nuevo usuario
                    usuarioDAO.crear(usuario);
                }

                limpiarCampos();
                cargarUsuarios();
                mostrarMensaje("Éxito", "Usuario guardado correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                mostrarMensaje("Error", "Error al guardar el usuario: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void eliminarUsuario(Integer idUsuario) {
        Usuario usuarioSeleccionado = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSeleccionado != null) {
            Optional<ButtonType> result = mostrarConfirmacion("¿Está seguro de eliminar este usuario?");

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    usuarioDAO.eliminar(usuarioSeleccionado.getId());
                    cargarUsuarios();
                    limpiarCampos();
                    mostrarMensaje("Éxito", "Usuario eliminado correctamente", Alert.AlertType.INFORMATION);
                } catch (Exception e) {
                    mostrarMensaje("Error", "Error al eliminar el usuario: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarMensaje("Advertencia", "Por favor, seleccione un usuario", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void nuevoUsuario() {
        limpiarCampos();
        tablaUsuarios.getSelectionModel().clearSelection();
    }

    private void cargarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioDAO.listarTodos();
            listaUsuarios.clear();
            listaUsuarios.addAll(usuarios);
            tablaUsuarios.setItems(listaUsuarios);
        } catch (Exception e) {
            mostrarMensaje("Error", "Error al cargar los usuarios: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void mostrarDetallesUsuario(Usuario usuario) {
        txtNombre.setText(usuario.getNombre());
        txtUsuario.setText(usuario.getUsuario());
        txtPassword.clear(); // Por seguridad, no mostramos la contraseña
        comboRol.setValue(usuario.getRol());
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtUsuario.clear();
        txtPassword.clear();
        comboRol.setValue(null);
    }

    private boolean validarCampos() {
        String mensaje = "";

        if (txtNombre.getText().isEmpty()) {
            mensaje += "El nombre es requerido\n";
        }
        if (txtUsuario.getText().isEmpty()) {
            mensaje += "El usuario es requerido\n";
        }
        if (txtPassword.getText().isEmpty() &&
                tablaUsuarios.getSelectionModel().getSelectedItem() == null) {
            mensaje += "La contraseña es requerida para nuevos usuarios\n";
        }
        if (comboRol.getValue() == null) {
            mensaje += "El rol es requerido\n";
        }

        if (!mensaje.isEmpty()) {
            mostrarMensaje("Error de validación", mensaje, Alert.AlertType.ERROR);
            return false;
        }

        return true;
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private Optional<ButtonType> mostrarConfirmacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return List.of();
    }

    public Usuario obtenerUsuarioPorId(Integer idUsuario) {
        return null;
    }

    public void crearUsuario(Usuario usuarioNuevo) {
    }

    public void actualizarUsuario(Usuario usuarioNuevo) {
    }

    public List<Usuario> buscarUsuarios(String criterio) {
        return List.of();
    }

    public String getUsuarioActual() {
        return null;
    }
}