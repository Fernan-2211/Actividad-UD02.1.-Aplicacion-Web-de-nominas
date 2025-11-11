package com.empresa.conexion;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Clase que gestiona la conexión a la base de datos usando un pool de conexiones.
 * Se aplica el patrón Singleton para que exista un único DataSource compartido.
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public class Conexion {

    /** Objeto DataSource que gestiona el pool de conexiones. */
    private static BasicDataSource dataSource = null;

    /**
     * Método privado que crea y configura el DataSource si no existe todavía.
     * Aquí se definen las credenciales, URL de la base de datos y parámetros del pool.
     * 
     * @return DataSource configurado
     */
    private static DataSource getDataSource() {
        // Si el dataSource aún no está inicializado, lo creamos
        if (dataSource == null) {
            dataSource = new BasicDataSource();
            // Driver JDBC de MySQL
            dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            // Usuario y contraseña de la base de datos
            dataSource.setUsername("root");
            dataSource.setPassword("123456");
            // URL de conexión con opciones para SSL y zona horaria
            dataSource.setUrl(
                "jdbc:mysql://localhost:3306/empleados?useSSL=false&allowPublicKeyRetrieval=true&useTimezone=true&serverTimezone=UTC"
            );
            // Configuración del pool de conexiones
            dataSource.setInitialSize(5);   // conexiones iniciales
            dataSource.setMaxIdle(10);      // máximo de conexiones inactivas
            dataSource.setMaxTotal(10);     // máximo total de conexiones
            dataSource.setMaxWaitMillis(5000); // máximo tiempo de espera para obtener conexión
        }
        return dataSource;
    }

    /**
     * Método público para obtener una conexión de la base de datos desde el pool.
     * Siempre devuelve una conexión nueva o reciclada del pool.
     * 
     * @return Connection lista para usar
     * @throws SQLException si ocurre un error al obtener la conexión
     */
    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
}
