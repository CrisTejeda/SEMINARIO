package com.kioscoapp.view;

import com.kioscoapp.controllers.InventarioController;
import com.kioscoapp.model.Deposito;
import com.kioscoapp.model.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventarioView extends JFrame {
    private InventarioController controller;
    private JPanel mainPanel;
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnAgregar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnVolver;
    private JComboBox<Deposito> cmbDeposito;

    public InventarioView(InventarioController controller) {
        this.controller = controller;
        inicializarComponentes();
        configurarVentana();
        configurarEventos();
        cargarDatos();
    }

    private void inicializarComponentes() {
        // Panel principal
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior con búsqueda y filtros
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        cmbDeposito = new JComboBox<>();

        topPanel.add(new JLabel("Buscar: "));
        topPanel.add(txtBuscar);
        topPanel.add(btnBuscar);
        topPanel.add(new JLabel("Depósito: "));
        topPanel.add(cmbDeposito);

        // Tabla de productos
        String[] columnas = {"ID", "Código", "Nombre", "Descripción", "Precio", "Stock", "Depósito"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        // Panel de botones
        JPanel botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAgregar = new JButton("Agregar Producto");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnVolver = new JButton("Volver");

        botonesPanel.add(btnAgregar);
        botonesPanel.add(btnEditar);
        botonesPanel.add(btnEliminar);
        botonesPanel.add(btnVolver);

        // Agregar componentes al panel principal
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(botonesPanel, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setTitle("Gestión de Inventario");
        setContentPane(mainPanel);
        setSize(1000, 600);
        setMinimumSize(new Dimension(800, 500));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarEventos() {
        btnBuscar.addActionListener(e -> buscarProductos());

        btnAgregar.addActionListener(e -> mostrarDialogoAgregarProducto());

        btnEditar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada >= 0) {
                editarProducto(filaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Por favor, seleccione un producto para editar",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            int filaSeleccionada = tablaProductos.getSelectedRow();
            if (filaSeleccionada >= 0) {
                eliminarProducto(filaSeleccionada);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Por favor, seleccione un producto para eliminar",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        btnVolver.addActionListener(e -> {
            MainView mainView = new MainView("");
            mainView.setVisible(true);
            this.dispose();
        });

        cmbDeposito.addActionListener(e -> cargarProductosPorDeposito());
    }

    private void cargarDatos() {
        try {
            // Cargar depósitos en el combo
            List<Deposito> depositos = controller.obtenerDepositos();
            cmbDeposito.removeAllItems();
            cmbDeposito.addItem(new Deposito(0, "Todos los depósitos"));
            for (Deposito deposito : depositos) {
                cmbDeposito.addItem(deposito);
            }

            // Cargar productos
            cargarProductosPorDeposito();
        } catch (Exception e) {
            mostrarError("Error al cargar los datos: " + e.getMessage());
        }
    }

    private void buscarProductos() {
        String termino = txtBuscar.getText().trim();
        try {
            List<Producto> productos = controller.buscarProductos(termino);
            actualizarTabla(productos);
        } catch (Exception e) {
            mostrarError("Error al buscar productos: " + e.getMessage());
        }
    }

    private void cargarProductosPorDeposito() {
        try {
            Deposito depositoSeleccionado = (Deposito) cmbDeposito.getSelectedItem();
            List<Producto> productos;
            if (depositoSeleccionado != null && depositoSeleccionado.getId() > 0) {
                productos = controller.obtenerProductosPorDeposito(depositoSeleccionado.getId());
            } else {
                productos = controller.obtenerTodosLosProductos();
            }
            actualizarTabla(productos);
        } catch (Exception e) {
            mostrarError("Error al cargar productos: " + e.getMessage());
        }
    }

    private void actualizarTabla(List<Producto> productos) {
        modeloTabla.setRowCount(0);
        for (Producto producto : productos) {
            Object[] fila = {
                    producto.getId(),
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getDeposito().getNombre()
            };
            modeloTabla.addRow(fila);
        }
    }

    private void mostrarDialogoAgregarProducto() {
        // TODO: Implementar diálogo para agregar producto
        JOptionPane.showMessageDialog(this,
                "Funcionalidad de agregar producto en desarrollo",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void editarProducto(int fila) {
        // TODO: Implementar edición de producto
        JOptionPane.showMessageDialog(this,
                "Funcionalidad de editar producto en desarrollo",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarProducto(int fila) {
        int idProducto = (int) modeloTabla.getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro que desea eliminar este producto?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                controller.eliminarProducto((long) idProducto);
                modeloTabla.removeRow(fila);
                JOptionPane.showMessageDialog(this,
                        "Producto eliminado exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                mostrarError("Error al eliminar el producto: " + e.getMessage());
            }
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}