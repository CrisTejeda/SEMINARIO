package com.kioscoapp.view;

import com.kioscoapp.controllers.InventarioController;
import com.kioscoapp.controllers.UsuariosController;
import com.kioscoapp.controllers.VentasController;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JButton btnVentas;
    private JButton btnInventario;
    private JButton btnUsuarios;
    private JButton btnReportes;
    private JButton btnCerrarSesion;
    private JLabel lblUsuarioActual;
    private String nombreUsuario;
    private VentasController ventasController;
    private InventarioController inventarioController;
    private UsuariosController usuariosController;

    public MainView(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.ventasController = new VentasController();
        this.inventarioController = new InventarioController();
        this.usuariosController = new UsuariosController();
        inicializarComponentes();
        configurarVentana();
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Panel principal con GridBagLayout para mejor control del espacio
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(245, 245, 245));

        // Configuración de constraints para GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Banner superior con nombre de usuario
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(51, 122, 183));
        lblUsuarioActual = new JLabel("Bienvenido: " + nombreUsuario);
        lblUsuarioActual.setForeground(Color.WHITE);
        lblUsuarioActual.setFont(new Font("Arial", Font.BOLD, 14));
        lblUsuarioActual.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        topPanel.add(lblUsuarioActual, BorderLayout.WEST);

        // Panel de botones principales
        JPanel botonesPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        botonesPanel.setOpaque(false);

        // Crear botones con iconos
        btnVentas = crearBotonMenuPrincipal("Ventas", "/icons/ventas.png");
        btnInventario = crearBotonMenuPrincipal("Inventario", "/icons/inventario.png");
        btnUsuarios = crearBotonMenuPrincipal("Usuarios", "/icons/usuarios.png");
        btnReportes = crearBotonMenuPrincipal("Reportes", "/icons/reportes.png");

        // Agregar botones al panel
        botonesPanel.add(btnVentas);
        botonesPanel.add(btnInventario);
        botonesPanel.add(btnUsuarios);
        botonesPanel.add(btnReportes);

        // Botón de cerrar sesión
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBackground(new Color(217, 83, 79));
        btnCerrarSesion.setForeground(Color.WHITE);
        btnCerrarSesion.setFocusPainted(false);

        // Agregar componentes al panel principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(topPanel, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(botonesPanel, gbc);

        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.weightx = 0;
        gbc.weighty = 0;
        mainPanel.add(btnCerrarSesion, gbc);
    }

    private JButton crearBotonMenuPrincipal(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            Image img = icono.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono para: " + texto);
        }

        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        return boton;
    }

    private void configurarVentana() {
        setTitle("KioscoApp - Panel Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(800, 600);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
    }

    private void configurarEventos() {
        btnVentas.addActionListener(e -> {
            try {
                VentasView ventasView = new VentasView(ventasController);
                ventasView.setVisible(true);
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir la vista de ventas: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnInventario.addActionListener(e -> {
            try {
                InventarioView inventarioView = new InventarioView(inventarioController);
                inventarioView.setVisible(true);
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir la vista de inventario: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUsuarios.addActionListener(e -> {
            try {
                UsuariosView usuariosView = new UsuariosView(usuariosController);
                usuariosView.setVisible(true);
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir la vista de usuarios: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReportes.addActionListener(e -> {
            try {
                ReportesView reportesView = new ReportesView();
                reportesView.setVisible(true);
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al abrir la vista de reportes: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCerrarSesion.addActionListener(e -> {
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Está seguro que desea cerrar sesión?",
                    "Confirmar Cierre de Sesión",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                LoginView loginView = new LoginView();
                loginView.setVisible(true);
                this.dispose();
            }
        });
    }

    // Método para actualizar el nombre de usuario mostrado
    public void setUsuarioActual(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
        lblUsuarioActual.setText("Bienvenido: " + nombreUsuario);
    }
}