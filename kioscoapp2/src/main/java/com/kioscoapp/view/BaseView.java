package com.kioscoapp.view;

public interface BaseView {
    void initComponents();
    void setupLayout();
    void setupListeners();
    void display();
    void close();
    void showError(String message);
    void showSuccess(String message);
}