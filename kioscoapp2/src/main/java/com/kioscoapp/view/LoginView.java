package com.kioscoapp.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends AbstractView {

    private JTextField usuarioField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginView() {
        super("KioscoApp - Login");
    }

    @Override
    public void initComponents() {
        usuarioField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Ingresar");
        cancelButton = new JButton("Cancelar");

        // Configurar el panel principal con un borde
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    @Override
    public void setupLayout() {
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Logo o título
        JLabel titleLabel = new JLabel("KioscoApp");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // Usuario
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(new JLabel("Usuario:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(usuarioField, gbc);

        // Contraseña
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(passwordField, gbc);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);
    }

    @Override
    public void setupListeners() {
        cancelButton.addActionListener(e -> this.close());
    }

    // Métodos para establecer los listeners desde el controlador
    public void setLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    // Métodos para obtener los valores de los campos
    public String getUsuario() {
        return usuarioField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // Método para limpiar los campos
    public void clearFields() {
        usuarioField.setText("");
        passwordField.setText("");
        statusLabel.setText(" ");
    }
}