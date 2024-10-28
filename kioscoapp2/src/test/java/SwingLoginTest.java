package com.kioscoapp.test;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Versión de prueba de la interfaz de login usando Swing.
 * Mantener este archivo para pruebas rápidas y debugging.
 * No usar en producción.
 */
public class SwingLoginTest {
    // Tiempo de espera para mensajes de estado (ms)
    private static final int MESSAGE_TIMEOUT = 3000;
    private static final int MAX_INTENTOS = 3;

    private JFrame frame;
    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JLabel mensajeLabel;
    private JButton loginButton;
    private Timer messageTimer;
    private int intentosFallidos = 0;

    public SwingLoginTest() {
        initializeFrame();
        initializeComponents();
        setupLayout();
        setupListeners();
    }

    private void initializeFrame() {
        frame = new JFrame("KioscoApp - Login (Test)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void initializeComponents() {
        // Crear componentes
        JLabel titleLabel = new JLabel("KioscoApp Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        usuarioField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Iniciar Sesión");
        mensajeLabel = new JLabel(" ", SwingConstants.CENTER);

        // Configurar placeholders
        usuarioField.putClientProperty("JTextField.placeholderText", "Ingrese su usuario");
        passwordField.putClientProperty("JTextField.placeholderText", "Ingrese su contraseña");

        // Estilo para el mensaje
        mensajeLabel.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel para el título
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(mensajeLabel);

        // Panel para los campos
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Agregar componentes con GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        fieldsPanel.add(new JLabel("Usuario:"), gbc);

        gbc.gridy = 1;
        fieldsPanel.add(usuarioField, gbc);

        gbc.gridy = 2;
        fieldsPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridy = 3;
        fieldsPanel.add(passwordField, gbc);

        gbc.gridy = 4; gbc.insets = new Insets(15, 5, 5, 5);
        fieldsPanel.add(loginButton, gbc);

        gbc.gridy = 5;
        fieldsPanel.add(mensajeLabel, gbc);

        // Agregar paneles al panel principal
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(fieldsPanel);

        frame.add(mainPanel);
    }

    private void setupListeners() {
        // Listener para el botón de login
        loginButton.addActionListener(e -> handleLogin());

        // Listener para Enter en los campos
        KeyAdapter enterListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        };

        usuarioField.addKeyListener(enterListener);
        passwordField.addKeyListener(enterListener);

        // Listener para limpiar recursos al cerrar
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cleanup();
            }
        });
    }

    private void handleLogin() {
        String usuario = usuarioField.getText();
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Validación de campos vacíos
        if (usuario.trim().isEmpty() || password.trim().isEmpty()) {
            showMessage("Por favor complete todos los campos", true);
            return;
        }

        // Validación de credenciales
        if (validarCredenciales(usuario, password)) {
            loginExitoso();
        } else {
            loginFallido();
        }

        // Limpiar el password del array por seguridad
        java.util.Arrays.fill(passwordChars, '0');
    }

    private boolean validarCredenciales(String usuario, String password) {
        // Simulación de validación - Reemplazar con lógica real
        return usuario.equals("admin") && password.equals("admin");
    }

    private void loginExitoso() {
        intentosFallidos = 0;
        showMessage("Login exitoso!", false);

        // Simular apertura de ventana principal
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(frame,
                    "Login exitoso! Aquí se abriría la ventana principal.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void loginFallido() {
        intentosFallidos++;
        passwordField.setText("");

        if (intentosFallidos >= MAX_INTENTOS) {
            bloquearLogin();
        } else {
            showMessage("Usuario o contraseña incorrectos. Intentos restantes: " +
                    (MAX_INTENTOS - intentosFallidos), true);
        }
    }

    private void bloquearLogin() {
        loginButton.setEnabled(false);
        showMessage("Demasiados intentos fallidos. Sistema bloqueado.", true);

        // Desbloquear después de 30 segundos
        Timer unlockTimer = new Timer(30000, e -> {
            loginButton.setEnabled(true);
            intentosFallidos = 0;
            showMessage("Sistema desbloqueado. Puede intentar nuevamente.", false);
        });
        unlockTimer.setRepeats(false);
        unlockTimer.start();
    }

    private void showMessage(String message, boolean isError) {
        mensajeLabel.setText(message);
        mensajeLabel.setForeground(isError ? Color.RED : new Color(0, 100, 0));

        // Cancelar timer anterior si existe
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
        }

        // Crear nuevo timer para limpiar el mensaje
        messageTimer = new Timer(MESSAGE_TIMEOUT, e -> mensajeLabel.setText(" "));
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    private void cleanup() {
        if (messageTimer != null && messageTimer.isRunning()) {
            messageTimer.stop();
        }
    }

    public void display() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            // Intentar establecer el Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                SwingLoginTest loginTest = new SwingLoginTest();
                loginTest.display();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Error al iniciar la aplicación: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}