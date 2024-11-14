package vista;

import conexion.Conexion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Cris
 */
public class InterGraficaVentas extends javax.swing.JInternalFrame {

    ArrayList<Integer> listaCantidad = new ArrayList<>();
    ArrayList<String> listaFechas = new ArrayList<>();
    int cantidadResultados = 0;
    String[] vector_fechaVenta;
    int[] vector_estatus_cantidad;

    //Constructor
    public InterGraficaVentas() {
        initComponents();
        this.setSize(new Dimension(550, 650));
        this.setTitle("Historial de Ventas");
        this.MetodoContador();
        vector_fechaVenta = new String[cantidadResultados];
        vector_estatus_cantidad = new int[cantidadResultados];
        this.MetodoAlmacenaDatos();

    }

    //Metodo para determinar la cantidad de resultados a graficar
    private int MetodoContador() {
        try {

            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select fechaVenta, count(fechaVenta) as Ventas from tb_cabecera_venta "
                    + "where fechaVenta BETWEEN '" + InterGraficas.fecha_inicio + "' and '" + InterGraficas.fecha_fin + "' group by fechaVenta;");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                cantidadResultados++;
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error en: " + e);
        }
        return cantidadResultados;
    }

    //Metodo para almacenar en las listas los datos a graficar
    private void MetodoAlmacenaDatos() {
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select fechaVenta, count(fechaVenta) as Ventas from tb_cabecera_venta "
                    + "where fechaVenta BETWEEN '" + InterGraficas.fecha_inicio + "' and '" + InterGraficas.fecha_fin + "' group by fechaVenta;");
            ResultSet rs = pst.executeQuery();
            int contador = 0;
            while (rs.next()) {
                vector_fechaVenta[contador] = rs.getString("fechaVenta");
                listaFechas.add(vector_fechaVenta[contador]);
                vector_estatus_cantidad[contador] = rs.getInt("Ventas");
                listaCantidad.add(vector_estatus_cantidad[contador]);

                contador++;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error en: " + e);
        }
    }

    //Metodo para dterminar cual es el dia de mahyor cantidad de ventas
    public int MetodoMayorVenta(ArrayList<Integer> listaCantidad) {
        int mayor = 0;
        for (int i = 0; i < listaCantidad.size(); i++) {
            if (listaCantidad.get(i) > mayor) {
                mayor = listaCantidad.get(i);
            }
        }
        return mayor;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setResizable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione fechas para Graficar");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 0, 330, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    //metodo que dibuja graficas
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int mayorVenta = MetodoMayorVenta(listaCantidad);
        int largo_NuevoIngreso = 0;
        int parametro1 = 133;
        int parametroFecha = 118;
        int parametro3 = 100;

        for (int i = 0; i < listaCantidad.size(); i++) {
            largo_NuevoIngreso = listaCantidad.get(i) * 400 / mayorVenta;

            if (i == 0) {
                g.setColor(new Color(140, 0, 75));//morado
            } else if (i == 1) {
                g.setColor(new Color(255, 0, 0));//rojo
            } else if (i == 2) {
                g.setColor(new Color(0, 0, 0));//negro
            } else if (i == 3) {
                g.setColor(new Color(255, 255, 255));//blanco
            } else if (i == 4) {
                g.setColor(new Color(0, 85, 0));//verde
            } else if (i == 5) {
                g.setColor(new Color(0, 0, 255));//azul
            } else if (i == 6) {
                g.setColor(new Color(255, 127, 0));//naranja
            } else {
                g.setColor(new Color(17, 251, 216));//celeste
            }
            
            g.fillRect(100, parametro3, largo_NuevoIngreso, 40);
            g.drawString(listaFechas.get(i), 10 , parametroFecha);
            g.drawString("Cantidad: " + listaCantidad.get(i), 10, parametro1);
            parametro1 += 50;
            parametroFecha += 50;
            parametro3 += 50;
        }
    }
}
