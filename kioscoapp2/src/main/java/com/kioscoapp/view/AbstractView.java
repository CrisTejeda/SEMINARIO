package com.kioscoapp.view;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JFrame implements BaseView {

    protected JPanel mainPanel;
    protected JLabel statusLabel;

    public AbstractView(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.mainPanel = new JPanel();
        this.statusLabel = new JLabel(" ");
        this.statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        initializeFrame();
    }

    private void initializeFrame() {
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(statusLabel, BorderLayout.SOUTH);

        initComponents();
        setupLayout();
        setupListeners();
    }

    @Override
    public void display() {
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void close() {
        this.dispose();
    }

    @Override
    public void showError(String message) {
        statusLabel.setForeground(Color.RED);
        statusLabel.setText(message);
    }

    @Override
    public void showSuccess(String message) {
        statusLabel.setForeground(new Color(0, 100, 0));
        statusLabel.setText(message);
    }
}