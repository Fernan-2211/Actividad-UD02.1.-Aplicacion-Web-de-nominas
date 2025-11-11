package com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.empresa.conexion.Conexion;
import com.empresa.empleado.Empleado;
import com.empresa.strategy.CalculoSalarioBase;
import com.empresa.strategy.ContextoCalculoSalario;

/**
 * Clase DAO que gestiona todas las operaciones de base de datos relacionadas con empleados y nóminas.
 * 
 * Se encarga de:
 * - Listar empleados
 * - Obtener un empleado por DNI
 * - Calcular o recuperar salarios
 * - Editar empleados y actualizar nóminas
 * - Buscar empleados por distintos criterios
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public class EmpresaDAO {

    /** Conexión activa con la base de datos */
    private Connection connection;

    /** Statement para ejecutar consultas */
    private PreparedStatement statement;

    /** Estado de la operación (true si tuvo éxito) */
    private boolean estadoOperacion;

    /**
     * Obtiene todos los empleados de la base de datos ordenados por nombre.
     * @return Lista de objetos Empleado
     * @throws SQLException Si ocurre algún error en la consulta
     */
    public List<Empleado> obtenerEmpleados() throws SQLException {
        ResultSet resultSet = null;
        List<Empleado> listaEmpleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados ORDER BY nombre";
        connection = obtenerConexion();

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Empleado emp = new Empleado();
                emp.setDni(resultSet.getString("dni"));
                emp.setNombre(resultSet.getString("nombre"));
                emp.setSexo(resultSet.getString("sexo").charAt(0));
                emp.setCategoria(resultSet.getInt("categoria"));
                emp.setAnyos(resultSet.getInt("anyos"));
                listaEmpleados.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return listaEmpleados;
    }

    /**
     * Obtiene un empleado por su DNI.
     * @param dni DNI del empleado
     * @return Empleado encontrado o vacío si no existe
     * @throws SQLException Si ocurre algún error de consulta
     */
    public Empleado obtenerEmpleado(String dni) throws SQLException {
        ResultSet resultSet = null;
        Empleado emp = new Empleado();
        String sql = "SELECT * FROM empleados WHERE dni = ?";
        connection = obtenerConexion();

        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, dni);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                emp.setDni(resultSet.getString("dni"));
                emp.setNombre(resultSet.getString("nombre"));
                emp.setSexo(resultSet.getString("sexo").charAt(0));
                emp.setCategoria(resultSet.getInt("categoria"));
                emp.setAnyos(resultSet.getInt("anyos"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return emp;
    }

    /**
     * Obtiene el salario de un empleado.
     * Si ya existe en la tabla nominas, lo recupera.
     * Si no existe, lo calcula usando la estrategia CalculoSalarioBase y lo inserta en la base de datos.
     * @param dni DNI del empleado
     * @return Sueldo calculado o recuperado
     * @throws SQLException Si ocurre un error en la conexión o consulta
     */
    public int obtenerSalario(String dni) throws SQLException {
        int sueldo = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = obtenerConexion();
            System.out.println("Conexión obtenida en obtenerSalario para DNI: " + dni);

            // 1. Buscar en nominas
            String sql = "SELECT sueldo FROM nominas WHERE dni = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, dni);
            rs = stmt.executeQuery();

            if (rs.next()) {
                // Ya existe el sueldo en la base de datos
                sueldo = rs.getInt("sueldo");
                System.out.println("Sueldo encontrado en BD: " + sueldo);
            } else {
                // 2. Si no existe, obtener empleado y calcular usando Strategy
                Empleado emp = obtenerEmpleado(dni);
                if (emp.getDni() != null && !emp.getDni().isEmpty()) {
                    ContextoCalculoSalario contexto = new ContextoCalculoSalario(new CalculoSalarioBase());
                    sueldo = (int) contexto.calcularSalario(emp);
                    System.out.println("Sueldo calculado: " + sueldo);

                    // 3. Insertar en la tabla nominas
                    sql = "INSERT INTO nominas (dni, sueldo) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(sql)) {
                        insertStmt.setString(1, dni);
                        insertStmt.setInt(2, sueldo);
                        insertStmt.executeUpdate();
                        System.out.println("Sueldo insertado en BD.");
                    }
                } else {
                    throw new SQLException("Empleado no encontrado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerSalario: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException ignored) {}
            if (stmt != null) try { stmt.close(); } catch (SQLException ignored) {}
            if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
        }
        return sueldo;
    }

    /**
     * Edita los datos de un empleado y actualiza su sueldo usando Strategy.
     * @param empleado Empleado con los datos modificados
     * @return true si se editó correctamente, false si hubo algún fallo
     * @throws SQLException Si ocurre un error en la operación
     */
    public boolean editar(Empleado empleado) throws SQLException {
        estadoOperacion = false;
        connection = obtenerConexion();

        try {
            connection.setAutoCommit(false);

            // Actualizar tabla empleados
            String sql = "UPDATE empleados SET nombre = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, empleado.getNombre());
            statement.setString(2, String.valueOf(empleado.getSexo()));
            statement.setInt(3, empleado.getCategoria());
            statement.setInt(4, empleado.getAnyos());
            statement.setString(5, empleado.getDni());
            int filasActualizadas = statement.executeUpdate();
            estadoOperacion = filasActualizadas > 0;

            // Recalcular sueldo con Strategy
            ContextoCalculoSalario contexto = new ContextoCalculoSalario(new CalculoSalarioBase());
            double sueldo = contexto.calcularSalario(empleado);

            // Insertar o actualizar en nominas
            sql = "INSERT INTO nominas (dni, sueldo) VALUES (?, ?) ON DUPLICATE KEY UPDATE sueldo = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, empleado.getDni());
            statement.setDouble(2, sueldo);
            statement.setDouble(3, sueldo);
            filasActualizadas = statement.executeUpdate();
            estadoOperacion = estadoOperacion && filasActualizadas > 0;

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
                System.out.println("Rollback ejecutado para DNI: " + empleado.getDni() + " - Error: " + e.getMessage());
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return estadoOperacion;
    }

    /**
     * Busca empleados según un criterio y valor.
     * @param criterio Campo a filtrar (dni, nombre, sexo, categoria, anyos)
     * @param valor Valor a buscar
     * @return Lista de empleados que cumplen el criterio
     * @throws SQLException Si ocurre un error en la consulta
     */
    public List<Empleado> buscarEmpleados(String criterio, String valor) throws SQLException {
        ResultSet resultSet = null;
        List<Empleado> listaEmpleados = new ArrayList<>();
        String sql = null;
        connection = obtenerConexion();

        try {
            switch (criterio.toLowerCase()) {
                case "dni":
                    sql = "SELECT * FROM empleados WHERE dni LIKE ?";
                    valor = "%" + valor + "%";
                    break;
                case "nombre":
                    sql = "SELECT * FROM empleados WHERE nombre LIKE ?";
                    valor = "%" + valor + "%";
                    break;
                case "sexo":
                    sql = "SELECT * FROM empleados WHERE sexo = ?";
                    break;
                case "categoria":
                    sql = "SELECT * FROM empleados WHERE categoria = ?";
                    break;
                case "anyos":
                    sql = "SELECT * FROM empleados WHERE anyos = ?";
                    break;
                default:
                    throw new SQLException("Criterio de búsqueda no válido");
            }

            statement = connection.prepareStatement(sql);
            statement.setString(1, valor);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Empleado emp = new Empleado();
                emp.setDni(resultSet.getString("dni"));
                emp.setNombre(resultSet.getString("nombre"));
                emp.setSexo(resultSet.getString("sexo").charAt(0));
                emp.setCategoria(resultSet.getInt("categoria"));
                emp.setAnyos(resultSet.getInt("anyos"));
                listaEmpleados.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return listaEmpleados;
    }

    /**
     * Obtiene la conexión a la base de datos desde la clase Conexion.
     * @return Conexión lista para usar
     * @throws SQLException Si no se puede establecer la conexión
     */
    private Connection obtenerConexion() throws SQLException {
        return Conexion.getConnection();
    }
}
