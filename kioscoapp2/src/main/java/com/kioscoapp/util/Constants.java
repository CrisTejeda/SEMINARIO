package com.kioscoapp.util;

public class Constants {
    // Constantes para la base de datos
    public static final String DB_URL = "jdbc:mysql://localhost:3306/kioscoapp";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "root";

    // Constantes para roles y permisos
    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_VENDEDOR = "VENDEDOR";
    public static final String ROL_INVENTARIO = "INVENTARIO";

    // Permisos específicos
    public static final String PERMISO_CREAR_USUARIO = "CREAR_USUARIO";
    public static final String PERMISO_EDITAR_USUARIO = "EDITAR_USUARIO";
    public static final String PERMISO_ELIMINAR_USUARIO = "ELIMINAR_USUARIO";
    public static final String PERMISO_CREAR_PRODUCTO = "CREAR_PRODUCTO";
    public static final String PERMISO_EDITAR_PRODUCTO = "EDITAR_PRODUCTO";
    public static final String PERMISO_ELIMINAR_PRODUCTO = "ELIMINAR_PRODUCTO";
    public static final String PERMISO_REALIZAR_VENTA = "REALIZAR_VENTA";
    public static final String PERMISO_ANULAR_VENTA = "ANULAR_VENTA";
    public static final String PERMISO_VER_REPORTES = "VER_REPORTES";

    // Constantes para validaciones
    public static final int MIN_LENGTH_USERNAME = 4;
    public static final int MAX_LENGTH_USERNAME = 20;
    public static final int MIN_LENGTH_PASSWORD = 8;
    public static final int MAX_LENGTH_PASSWORD = 16;
    public static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String REGEX_TELEFONO = "^[0-9]{10}$";

    // Constantes para la interfaz de usuario
    public static final String APP_TITLE = "KioscoApp";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATETIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    // Mensajes de error
    public static final String ERROR_CONEXION_DB = "Error al conectar con la base de datos";
    public static final String ERROR_USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String ERROR_CREDENCIALES_INVALIDAS = "Credenciales inválidas";
    public static final String ERROR_PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";
    public static final String ERROR_STOCK_INSUFICIENTE = "Stock insuficiente";
    public static final String ERROR_VENTA_FALLIDA = "Error al procesar la venta";

    // Mensajes de éxito
    public static final String SUCCESS_REGISTRO = "Registro exitoso";
    public static final String SUCCESS_ACTUALIZACION = "Actualización exitosa";
    public static final String SUCCESS_ELIMINACION = "Eliminación exitosa";
    public static final String SUCCESS_VENTA = "Venta realizada con éxito";

    // Constantes para configuración del sistema
    public static final int MAX_INTENTOS_LOGIN = 3;
    public static final int TIEMPO_BLOQUEO_LOGIN = 15; // minutos
    public static final int DIAS_EXPIRACION_PASSWORD = 90;
    public static final int STOCK_MINIMO_DEFAULT = 10;
    public static final double IGV = 0.18; // 18%

    // Rutas de archivos
    public static final String RUTA_LOGS = "logs/";
    public static final String RUTA_REPORTES = "reportes/";
    public static final String RUTA_BACKUPS = "backups/";

    // Configuración de archivos
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_EXCEL = ".xlsx";
    public static final String EXTENSION_CSV = ".csv";

    // Constantes para reportes
    public static final String REPORTE_VENTAS_DIARIAS = "Ventas Diarias";
    public static final String REPORTE_VENTAS_MENSUALES = "Ventas Mensuales";
    public static final String REPORTE_INVENTARIO = "Reporte de Inventario";
    public static final String REPORTE_PRODUCTOS_STOCK_BAJO = "Productos con Stock Bajo";

    // Constantes para paginación
    public static final int ITEMS_POR_PAGINA = 10;
    public static final int MAX_PAGINAS_NAVEGACION = 5;

    private Constants() {
        // Constructor privado para evitar instanciación
    }
}