# 🏪 Sistema de Ventas - ElectroCentro

## ⭐ Descripción
Sistema de Ventas es una aplicación de escritorio desarrollada en **Java** para la gestión de stock y ventas de **ElectroCentro**, un comercio dedicado a la venta de artículos eléctricos (cables, llaves térmicas, iluminación, herramientas y accesorios de instalación). Utiliza el patrón **MVC (Modelo-Vista-Controlador)**, interfaz gráfica con **Java Swing** y una base de datos relacional **MySQL**. Funciona dentro de la red local del comercio, sin depender de internet.

## 🚀 Características

### 👥 Gestión de Usuarios
- Registro y administración de usuarios del sistema
- Control de acceso mediante usuario y contraseña (pantalla de login)
- Estado de usuario (1: activo, 0: inactivo)

### 🧑‍💼 Gestión de Clientes
- Registro de nombre, apellido, DNI, teléfono y dirección
- Estado de cliente activo/inactivo

### 🗂️ Gestión de Categorías
- Alta y administración de categorías (Cables, Llaves térmicas, Iluminación, Herramientas, Accesorios de instalación)
- Estado de la categoría

### 📦 Gestión de Productos
- Registro de productos con nombre, descripción, precio y cantidad en stock
- Porcentaje de IVA por producto
- Asociación de cada producto a una categoría
- Actualización de stock

### 🛍️ Gestión de Ventas
- Facturación con cabecera y detalle de venta
- Descuento automático del stock al registrar la venta
- Cálculo automático de subtotales y total a pagar
- Generación de facturas en **PDF**
- Registro de la fecha de venta
- Historial de ventas realizadas

### 📊 Reportes
- Reportes de ventas
- Gráficas estadísticas de ventas

## 🛠️ Tecnologías Utilizadas
| Componente | Tecnología |
|---|---|
| Lenguaje | Java |
| Interfaz gráfica | Java Swing |
| IDE | NetBeans |
| Base de datos | MySQL |
| Diseño de la base de datos | MySQL Workbench |
| Conexión a la base de datos | MySQL Connector/J 8.1.0 (JDBC) |
| Generación de PDF | iText 5.5.12 |
| Selector de fechas | JCalendar 1.4 |

## 🏗️ Arquitectura
El proyecto está organizado en los siguientes paquetes:

```text
├── modelo        (CabeceraVenta, DetalleVenta, Categoria, Cliente, Producto, Usuario)
├── vista         (FrmLogin, FrmMenu e interfaces de gestión, facturación y reportes)
├── controlador   (Ctrl_Categoria, Ctrl_Cliente, Ctrl_Producto, Ctrl_Usuario, Ctrl_RegistrarVenta, VentaPDF, Reportes)
├── conexion      (Conexion.java)
└── conector      (mysql-connector-j-8.1.0.jar, iText 5.5.12, jcalendar-1.4.jar)
```

## 📋 Estructura de la Base de Datos
Base de datos: `bd_sistema_ventas_electrocentro`, con 6 tablas relacionales, claves primarias y foráneas:

```text
├── tb_usuario         (Usuarios del sistema)
├── tb_cliente         (Información de clientes)
├── tb_categoria       (Categorías de productos)
├── tb_producto        (Inventario de productos)
├── tb_cabecera_venta  (Registro principal de ventas)
└── tb_detalle_venta   (Detalle de cada venta)
```

## 🎥 Video Demostrativo


## 💻 Requisitos del Sistema
- MySQL 5.7 o superior
- Java JDK 8 o superior
- Mínimo 4 GB de RAM
- 500 MB de espacio en disco

## ⚙️ Instalación y Configuración

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com
   ```

2. **Configurar la Base de Datos:**
   * Abrir MySQL Workbench.
   * Ejecutar el script `.sql` que se encuentra dentro de la carpeta `BD Y DER` para crear la base de datos y sus tablas.

3. **Cargar el proyecto en el IDE:**
   * Abrir NetBeans.
   * Abrir el proyecto ubicado dentro de la carpeta `SistemaDeVentas`.
   * Verificar que las librerías del conector de MySQL estén agregadas en la sección de *Libraries*.

4. **Configurar la Conexión:**
   * Configurar las credenciales de tu MySQL local (host, puerto, usuario y contraseña) en la clase `Conexion.java`.

5. **Ejecutar:**
   * Compilar y ejecutar la aplicación desde NetBeans.


## 👥 Usuarios Registrados
| Nombres | Apellidos | Usuario | Contraseña | Teléfono |
|---|---|---|---|---|
| Pedro | García | pgarcia | 12345 | 1123456789 |
| Mariano | Lopez | mlopez | abc123 | 1134567890 |
| Carlos | Martínez | cmartinez | pass456 | 1145678901 |
