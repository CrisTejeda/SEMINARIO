## 🏪 Sistema de Ventas - Gestión Comercial

### ⭐ Descripción
Sistema de Ventas es una aplicación desarrollada en Java que permite la gestión integral de operaciones comerciales, incluyendo el manejo de ventas, productos, clientes y usuarios. Utiliza una arquitectura basada en el patrón MVC (Modelo-Vista-Controlador) con una robusta gestión de datos en MySQL para garantizar la integridad y eficiencia de las operaciones comerciales.

### 🚀 Características

### 👥 Gestión de Usuarios:

- Registro y administración de usuarios del sistema
- Control de acceso con usuario y contraseña
- Gestión de estados de usuarios
- Almacenamiento seguro de información personal


### 🧑‍💼 Gestión de Clientes:

- Registro completo de información de clientes
- Seguimiento de DNI, teléfono y dirección
- Sistema de estado para clientes activos/inactivos


### 📦 Gestión de Productos:

- Control de inventario
- Gestión de precios y cantidades
- Categorización de productos
- Cálculo automático de IVA
- Descripción detallada de productos


### 🛍️ Gestión de Ventas:

- Registro detallado de transacciones
- Cálculo automático de subtotales y totales
- Sistema de descuentos
- Cálculo de IVA por producto
- Registro de fecha de venta
- Histórico de ventas por cliente


### 📊 Categorización:

- Sistema de categorías para productos
- Organización eficiente del inventario



### 🛠️ Tecnologías Utilizadas
- IDE: NetBeans
- Base de Datos: MySQL
- Herramienta de Diseño de Base de Datos: MySQL Workbench
- Lenguaje de Programación: Java
- Conexión a la Base de Datos: Conector Java para MySQL (JDBC)
- Estructura de Base de Datos: 6 tablas relacionales
- Características de la Base de Datos: Claves primarias y foráneas, campos optimizados por tipo de dato



### 📋 Estructura de la Base de Datos

```html
sql
├── tb_usuario (Gestión de Usuarios)
├── tb_cliente (Información de Clientes)
├── tb_categoria (Categorías de Productos)
├── tb_producto (Inventario de Productos)
├── tb_cabecera_venta (Registro Principal de Ventas)
└── tb_detalle_venta (Detalles de Cada Venta)
```
### 🎥 Video Demostrativo
[Links]()

### 💻 Requisitos del Sistema

- MySQL 5.7 o superior
- Java JDK 8 o superior
- Mínimo 4GB de RAM
- Espacio en disco: 500MB

### ⚙️ Instalación

- Clonar el repositorio
- Ejecutar el script SQL BD Sistema de Ventas.sql
- Configurar las credenciales de la base de datos
- Compilar y ejecutar la aplicación Java

### 👥 Usuarios Registrados

 | Nombres  | Apellidos  | Usuarios | Contraseña | Telefono | 
 | :------------ |:---------------:| -----:|
 | Pedro | Garcia | pgarcia | 12345 | 1123456789 |
 | Mariana | Lopez | mlopez | abc123 | 1134567890 |
 | Carlos | Martínez | cmartinez | pass456 | 1145678901 |
 | Laura | Fernández | lfernandez | laura789 | 1156789012 |
