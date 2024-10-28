package com.kioscoapp.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

public class ReportesView extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTable tablaReporteVentas;
    private JTable tablaReporteInventario;
    private JSpinner fechaInicio;
    private JSpinner fechaFin;
    private JComboBox<String> tipoReporte;
    private JButton btnGenerar;
    private JButton btnExportar;
    private JButton btnVolver;
    private JLabel lblTotalVentas;

    public ReportesView() {
        inicializarComponentes();
        configurarVentana();
        configurarEventos();
    }

    private void inicializarComponentes() {
        // Panel principal
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel superior para filtros
        JPanel panelFiltros = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Componentes del panel de filtros
        tipoReporte = new JComboBox<>(new String[]{"Ventas por Período", "Inventario", "Productos más Vendidos", "Clientes Frecuentes"});

        // Configuración de los spinners de fecha
        SpinnerDateModel modeloInicio = new SpinnerDateModel();
        SpinnerDateModel modeloFin = new SpinnerDateModel();
        fechaInicio = new JSpinner(modeloInicio);
        fechaFin = new JSpinner(modeloFin);

        // Personalizar el editor de los spinners para mostrar solo la fecha
        JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(fechaInicio, "dd/MM/yyyy");
        JSpinner.DateEditor editorFin = new JSpinner.DateEditor(fechaFin, "dd/MM/yyyy");
        fechaInicio.setEditor(editorInicio);
        fechaFin.setEditor(editorFin);

        // Configurar tamaño preferido para los spinners
        Dimension spinnerSize = new Dimension(120, 25);
        fechaInicio.setPreferredSize(spinnerSize);
        fechaFin.setPreferredSize(spinnerSize);

        btnGenerar = new JButton("Generar Reporte");
        btnExportar = new JButton("Exportar a Excel");

        // Configuración de los componentes de filtro
        gbc.gridx = 0; gbc.gridy = 0;
        panelFiltros.add(new JLabel("Tipo de Reporte:"), gbc);
        gbc.gridx = 1;
        panelFiltros.add(tipoReporte, gbc);

        gbc.gridx = 2;
        panelFiltros.add(new JLabel("Fecha Inicio:"), gbc);
        gbc.gridx = 3;
        panelFiltros.add(fechaInicio, gbc);

        gbc.gridx = 4;
        panelFiltros.add(new JLabel("Fecha Fin:"), gbc);
        gbc.gridx = 5;
        panelFiltros.add(fechaFin, gbc);

        gbc.gridx = 6;
        panelFiltros.add(btnGenerar, gbc);
        gbc.gridx = 7;
        panelFiltros.add(btnExportar, gbc);

        // TabbedPane para diferentes tipos de reportes
        tabbedPane = new JTabbedPane();

        // Tabla de ventas
        String[] columnasVentas = {"Fecha", "N° Pedido", "Cliente", "Total", "Método Pago", "Estado"};
        tablaReporteVentas = new JTable(new DefaultTableModel(columnasVentas, 0));
        JScrollPane scrollVentas = new JScrollPane(tablaReporteVentas);

        // Tabla de inventario
        String[] columnasInventario = {"Código", "Producto", "Stock Actual", "Stock Mínimo", "Último Ingreso", "Precio"};
        tablaReporteInventario = new JTable(new DefaultTableModel(columnasInventario, 0));
        JScrollPane scrollInventario = new JScrollPane(tablaReporteInventario);

        // Panel inferior con totales
        JPanel panelTotales = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTotalVentas = new JLabel("Total Ventas: $0.00");
        lblTotalVentas.setFont(new Font("Arial", Font.BOLD, 14));
        panelTotales.add(lblTotalVentas);

        // Botón volver
        btnVolver = new JButton("Volver al Menú Principal");
        btnVolver.setBackground(new Color(51, 122, 183));
        btnVolver.setForeground(Color.WHITE);

        // Agregar componentes al panel principal
        mainPanel.add(panelFiltros, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(panelTotales, BorderLayout.SOUTH);

        // Agregar pestañas
        tabbedPane.addTab("Reporte de Ventas", scrollVentas);
        tabbedPane.addTab("Reporte de Inventario", scrollInventario);

        // Panel para el botón volver
        JPanel panelVolver = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelVolver.add(btnVolver);
        mainPanel.add(panelVolver, BorderLayout.SOUTH);
    }

    private void configurarVentana() {
        setTitle("KioscoApp - Reportes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(1024, 768);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
    }

    private void configurarEventos() {
        btnGenerar.addActionListener(e -> generarReporte());
        btnExportar.addActionListener(e -> exportarReporte());
        btnVolver.addActionListener(e -> {
            setVisible(false);
            dispose();
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof MainView) {
                    window.setVisible(true);
                    break;
                }
            }
        });

        tipoReporte.addActionListener(e -> {
            boolean mostrarFechas = tipoReporte.getSelectedItem().toString().contains("Ventas");
            fechaInicio.setEnabled(mostrarFechas);
            fechaFin.setEnabled(mostrarFechas);
        });
    }

    private void generarReporte() {
        try {
            Date fechaIni = (Date) fechaInicio.getValue();
            Date fechaF = (Date) fechaFin.getValue();

            if (fechaIni != null && fechaF != null && fechaF.before(fechaIni)) {
                JOptionPane.showMessageDialog(this,
                        "La fecha final debe ser posterior a la fecha inicial",
                        "Error en fechas",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Generando reporte de " + tipoReporte.getSelectedItem().toString(),
                    "Generando Reporte",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al generar el reporte: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportarReporte() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                String rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
                if (!rutaArchivo.toLowerCase().endsWith(".xlsx")) {
                    rutaArchivo += ".xlsx";
                }

                JOptionPane.showMessageDialog(this,
                        "Reporte exportado exitosamente a: " + rutaArchivo,
                        "Exportación Exitosa",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al exportar el reporte: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}