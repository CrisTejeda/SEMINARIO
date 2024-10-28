package com.kioscoapp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final Properties properties = new Properties();
    private static HikariDataSource dataSource;

    static {
        try {
            properties.load(DatabaseConnection.class.getClassLoader()
                    .getResourceAsStream("config/config.properties"));
            initializeDataSource();
        } catch (IOException e) {
            logger.error("Error al cargar archivo de configuración", e);
            throw new RuntimeException("No se pudo inicializar la configuración de la base de datos", e);
        }
    }

    private static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(properties.getProperty("db.url", "jdbc:mysql://localhost:3306/kioscoapp"));
        config.setUsername(properties.getProperty("db.user", "root"));
        config.setPassword(properties.getProperty("db.password", "root"));

        // Configuración de pool de conexiones
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000); // 5 minutos
        config.setConnectionTimeout(20000); // 20 segundos

        // Configuración específica de MySQL
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");

        // Configuración de reconexión
        config.setAutoCommit(true);
        config.setConnectionTestQuery("SELECT 1");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null || dataSource.isClosed()) {
            synchronized (DatabaseConnection.class) {
                if (dataSource == null || dataSource.isClosed()) {
                    initializeDataSource();
                }
            }
        }
        return dataSource.getConnection();
    }

    public static void closeDataSource() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }

    // Método para reiniciar el pool de conexiones si es necesario
    public static void resetConnection() {
        closeDataSource();
        initializeDataSource();
    }
}
