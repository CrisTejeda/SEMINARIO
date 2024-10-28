package com.kioscoapp.view;

import com.kioscoapp.controllers.VentasController;
import com.kioscoapp.model.Cliente;
import com.kioscoapp.model.Producto;
import com.kioscoapp.model.DetallePedido;
import com.kioscoapp.model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentasView extends JFrame {
    private VentasController controller;

    // Componentes de la interfaz
    private JTable tablaPedido;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscarProducto;
    private JTextField txtCantidad;
    private JTextField txtCliente;
    private JLabel lblTotal;
    private JButton btnAgregar;
    private JButton btnQuitar;
    private JButton btnBuscarProducto;
    private JButton btnBuscarCliente;
    private JButton btnFinalizarVenta;
    private List<DetallePedido> detallesPedido;

    public VentasView(VentasController ventasController) {
        controller = new VentasController();
        detallesPedido = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        // Configuración básica de la ventana
        setTitle("Gestión de Ventas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelSuperior = new JPanel(new GridLayout(2, 4, 10, 10));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Componentes del panel superior
        panelSuperior.add(new JLabel("Buscar Producto:"));
        txtBuscarProducto = new JTextField();
        panelSuperior.add(txtBuscarProducto);
        btnBuscarProducto = new JButton("Buscar");
        panelSuperior.add(btnBuscarProducto);

        panelSuperior.add(new JLabel("Cantidad:"));
        txtCantidad = new JTextField();
        panelSuperior.add(txtCantidad);
        btnAgregar = new JButton("Agregar");
        panelSuperior.add(btnAgregar);

        panelSuperior.add(new JLabel("Cliente:"));
        txtCliente = new JTextField();
        panelSuperior.add(txtCliente);
        btnBuscarCliente = new JButton("Buscar Cliente");
        panelSuperior.add(btnBuscarCliente);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla de pedidos
        String[] columnas = {"Código", "Producto", "Cantidad", "Precio Unit.", "Subtotal"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPedido = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaPedido);
        add(scrollPane, BorderLayout.CENTER);

        // Panel inferior
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnQuitar = new JButton("Quitar");
        btnFinalizarVenta = new JButton("Finalizar Venta");
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));

        panelInferior.add(btnQuitar);
        panelInferior.add(lblTotal);
        panelInferior.add(btnFinalizarVenta);
        add(panelInferior, BorderLayout.SOUTH);

        // Agregar listeners
        btnBuscarProducto.addActionListener(e -> buscarProducto());
        btnAgregar.addActionListener(e -> agregarProducto());
        btnQuitar.addActionListener(e -> quitarProducto());
        btnBuscarCliente.addActionListener(e -> buscarCliente());
        btnFinalizarVenta.addActionListener(e -> finalizarVenta());
    }

    private void buscarProducto() {
        String codigo = txtBuscarProducto.getText().trim();
        if (!codigo.isEmpty()) {
            Producto producto = controller.buscarProducto(codigo);
            if (producto != null) {
                txtBuscarProducto.setText(producto.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void agregarProducto() {
        try {
            String productoNombre = txtBuscarProducto.getText().trim();
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());

            if (productoNombre.isEmpty() || cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "Ingrese producto y cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto producto = controller.buscarProducto(productoNombre);
            if (producto != null) {
                DetallePedido detalle = new DetallePedido();
                detalle.setProducto(producto);
                detalle.setCantidad(cantidad);
                detalle.getPrecioUnitario(producto.getPrecio());

                detallesPedido.add(detalle);
                actualizarTabla();
                actualizarTotal();

                // Limpiar campos
                txtBuscarProducto.setText("");
                txtCantidad.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una cantidad válida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void quitarProducto() {
        int filaSeleccionada = tablaPedido.getSelectedRow();
        if (filaSeleccionada >= 0) {
            detallesPedido.remove(filaSeleccionada);
            actualizarTabla();
            actualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para quitar", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCliente() {
        String documento = txtCliente.getText().trim();
        if (!documento.isEmpty()) {
            Cliente cliente = controller.buscarCliente(documento);
            if (cliente != null) {
                txtCliente.setText(cliente.getNombre());
            } else {
                JOptionPane.showMessageDialog(this, "Cliente no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void finalizarVenta() {
        if (detallesPedido.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue productos al pedido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String clienteNombre = txtCliente.getText().trim();
        if (clienteNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Cliente cliente = controller.buscarCliente(clienteNombre);
        if (cliente != null) {
            Pedido pedido = new Pedido();
            pedido.setCliente(cliente);
            pedido.setDetalles(detallesPedido);

            boolean resultado = controller.guardarVenta(pedido);
            if (resultado) {
                JOptionPane.showMessageDialog(this, "Venta realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarVenta();
            } else {
                JOptionPane.showMessageDialog(this, "Error al procesar la venta", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (DetallePedido detalle : detallesPedido) {
            Producto producto = detalle.getProducto();
            double subtotal = detalle.getCantidad() * detalle.getPrecioUnitario(producto.getPrecio());
            modeloTabla.addRow(new Object[]{
                    producto.getCodigo(),
                    producto.getNombre(),
                    detalle.getCantidad(),
                    detalle.getPrecioUnitario(producto.getPrecio()),
                    subtotal
            });
        }
    }

    private void actualizarTotal() {
        Producto producto = new Producto();
        double total = detallesPedido.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitario(producto.getPrecio()))
                .sum();
        lblTotal.setText(String.format("Total: $%.2f", total));
    }

    private void limpiarVenta() {
        txtBuscarProducto.setText("");
        txtCantidad.setText("");
        txtCliente.setText("");
        detallesPedido.clear();
        actualizarTabla();
        actualizarTotal();
    }

    public void mostrar() {
        setLocationRelativeTo(null);
        setVisible(true);
    }
}