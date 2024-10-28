package com.kioscoapp.view;

import com.kioscoapp.controllers.UsuariosController;
import com.kioscoapp.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuariosView extends JFrame {
    private JPanel mainPanel;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private JButton btnNuevo;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private UsuariosController usuariosController;

    public UsuariosView(UsuariosController usuariosController) {
        this.usuariosController = usuariosController;
        inicializarComponentes();
        configurarVentana();
        configurarEventos();
        cargarUsuarios();
    }

    private void inicializarComponentes() {
        // Panel principal
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con búsqueda
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelSuperior.add(new JLabel("Buscar: "));
        panelSuperior.add(txtBuscar);
        panelSuperior.add(btnBuscar);

        // Tabla de usuarios
        String[] columnas = {"ID", "Nombre", "Usuario", "Rol", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaUsuarios = new JTable(modeloTabla);
        tablaUsuarios.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnNuevo = new JButton("Nuevo Usuario");
        btnEditar = new JButton("Editar Usuario");
        btnEliminar = new JButton("Eliminar Usuario");
        btnVolver = new JButton("Volver al Menú");

        // Estilizar botones
        btnNuevo.setBackground(new Color(92, 184, 92));
        btnNuevo.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(91, 192, 222));
        btnEditar.setForeground(Color.WHITE);
        btnEliminar.setBackground(new Color(217, 83, 79));
        btnEliminar.setForeground(Color.WHITE);
        btnVolver.setBackground(new Color(240, 173, 78));
        btnVolver.setForeground(Color.WHITE);

        panelBotones.add(btnNuevo);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnVolver);

        // Agregar componentes al panel principal
        mainPanel.add(panelSuperior, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setTitle("Gestión de Usuarios");
        setContentPane(mainPanel);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void configurarEventos() {
        btnNuevo.addActionListener(e -> mostrarDialogoNuevoUsuario());
        btnEditar.addActionListener(e -> editarUsuarioSeleccionado());
        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
        btnVolver.addActionListener(e -> volverAlMenu());
        btnBuscar.addActionListener(e -> buscarUsuarios());
    }

    private void cargarUsuarios() {
        try {
            limpiarTabla();
            List<Usuario> usuarios = usuariosController.obtenerTodosUsuarios();
            for (Usuario usuario : usuarios) {
                Object[] fila = {
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getUsuario(),
                        usuario.getRol(),
                        usuario.getEstado()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al cargar usuarios", e.getMessage());
        }
    }

    private void limpiarTabla() {
        while (modeloTabla.getRowCount() > 0) {
            modeloTabla.removeRow(0);
        }
    }

    private void mostrarDialogoNuevoUsuario() {
        mostrarDialogoUsuario(null);
    }

    private void editarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            Integer idUsuario = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
            try {
                Usuario usuario = usuariosController.obtenerUsuarioPorId(idUsuario);
                mostrarDialogoUsuario(usuario);
            } catch (Exception e) {
                mostrarError("Error al cargar usuario", e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un usuario para editar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarDialogoUsuario(Usuario usuario) {
        // Crear diálogo
        JDialog dialogo = new JDialog(this, usuario == null ? "Nuevo Usuario" : "Editar Usuario", true);
        dialogo.setSize(400, 450);
        dialogo.setLocationRelativeTo(this);

        // Panel principal del formulario
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Campos del formulario
        JTextField txtNombre = new JTextField(20);
        JTextField txtUsuario = new JTextField(20);
        JPasswordField txtPassword = new JPasswordField(20);
        JPasswordField txtConfirmarPassword = new JPasswordField(20);
        JComboBox<String> cmbRol = new JComboBox<>(new String[]{"Administrador", "Vendedor", "Inventario"});
        JComboBox<String> cmbEstado = new JComboBox<>(new String[]{"Activo", "Inactivo"});

        // Si estamos editando, llenar los campos
        if (usuario != null) {
            txtNombre.setText(usuario.getNombre());
            txtUsuario.setText(usuario.getUsuario());
            cmbRol.setSelectedItem(usuario.getRol());
            cmbEstado.setSelectedItem(usuario.getEstado());
        }

        // Agregar componentes al panel
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panel.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        panel.add(txtUsuario, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Confirmar Contraseña:"), gbc);
        gbc.gridx = 1;
        panel.add(txtConfirmarPassword, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        panel.add(cmbRol, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        panel.add(cmbEstado, gbc);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.setBackground(new Color(92, 184, 92));
        btnGuardar.setForeground(Color.WHITE);
        btnCancelar.setBackground(new Color(217, 83, 79));
        btnCancelar.setForeground(Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        // Configurar eventos de botones
        btnGuardar.addActionListener(e -> {
            if (validarFormulario(txtNombre, txtUsuario, txtPassword, txtConfirmarPassword)) {
                try {
                    Usuario usuarioNuevo = new Usuario();
                    if (usuario != null) {
                        usuarioNuevo.setId(usuario.getId());
                    }
                    usuarioNuevo.setNombre(txtNombre.getText().trim());
                    usuarioNuevo.setUsuario(txtUsuario.getText().trim());
                    usuarioNuevo.setPassword(new String(txtPassword.getPassword()));
                    usuarioNuevo.setRol(cmbRol.getSelectedItem().toString());
                    usuarioNuevo.setEstado(cmbEstado.getSelectedItem().toString());

                    if (usuario == null) {
                        usuariosController.crearUsuario(usuarioNuevo);
                    } else {
                        usuariosController.actualizarUsuario(usuarioNuevo);
                    }

                    cargarUsuarios();
                    dialogo.dispose();
                } catch (Exception ex) {
                    mostrarError("Error al guardar usuario", ex.getMessage());
                }
            }
        });

        btnCancelar.addActionListener(e -> dialogo.dispose());

        // Configurar el diálogo
        dialogo.setLayout(new BorderLayout());
        dialogo.add(panel, BorderLayout.CENTER);
        dialogo.add(panelBotones, BorderLayout.SOUTH);
        dialogo.setResizable(false);
        dialogo.setVisible(true);
    }

    private boolean validarFormulario(JTextField txtNombre, JTextField txtUsuario,
                                      JPasswordField txtPassword, JPasswordField txtConfirmarPassword) {
        if (txtNombre.getText().trim().isEmpty()) {
            mostrarError("Error de validación", "El nombre es requerido");
            return false;
        }
        if (txtUsuario.getText().trim().isEmpty()) {
            mostrarError("Error de validación", "El usuario es requerido");
            return false;
        }
        if (txtPassword.getPassword().length == 0) {
            mostrarError("Error de validación", "La contraseña es requerida");
            return false;
        }
        if (!new String(txtPassword.getPassword()).equals(new String(txtConfirmarPassword.getPassword()))) {
            mostrarError("Error de validación", "Las contraseñas no coinciden");
            return false;
        }
        return true;
    }

    private void eliminarUsuarioSeleccionado() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea eliminar este usuario?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                try {
                    Integer idUsuario = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
                    usuariosController.eliminarUsuario(idUsuario);
                    cargarUsuarios();
                } catch (Exception e) {
                    mostrarError("Error al eliminar usuario", e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un usuario para eliminar",
                    "Selección requerida",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarUsuarios() {
        String criterio = txtBuscar.getText().trim();
        try {
            limpiarTabla();
            List<Usuario> usuarios = usuariosController.buscarUsuarios(criterio);
            for (Usuario usuario : usuarios) {
                Object[] fila = {
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getUsuario(),
                        usuario.getRol(),
                        usuario.getEstado()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            mostrarError("Error al buscar usuarios", e.getMessage());
        }
    }

    private void volverAlMenu() {
        MainView mainView = new MainView(usuariosController.getUsuarioActual());
        mainView.setVisible(true);
        this.dispose();
    }

    private void mostrarError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this,
                mensaje,
                titulo,
                JOptionPane.ERROR_MESSAGE);
    }
}