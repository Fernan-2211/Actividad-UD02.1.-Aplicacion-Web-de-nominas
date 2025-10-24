package com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.empresa.conexion.Conexion;
import com.empresa.empleado.Empleado;
import com.empresa.nomina.Nomina;

/**
 * Clase que gestiona las operaciones con la base de datos para empleados y nóminas.
 */
public class empresaDAO {
    private Connection connection;
    private PreparedStatement statement;
    private boolean estadoOperacion;

    /**
     * Obtiene la lista de todos los empleados en la base de datos.
     * @return Lista de empleados.
     * @throws SQLException Si ocurre un error en la consulta.
     */
    public List<Empleado> obtenerEmpleados() throws SQLException {
        ResultSet resultSet = null;
        List<Empleado> listaEmpleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados ORDER BY dni";
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
     * @param dni DNI del empleado.
     * @return Objeto Empleado o un objeto vacío si no se encuentra.
     * @throws SQLException Si ocurre un error en la consulta.
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
     * Edita los datos de un empleado y actualiza su sueldo en la tabla nominas.
     * @param empleado Empleado con los datos actualizados.
     * @return true si la edición fue exitosa, false en caso contrario.
     * @throws SQLException Si ocurre un error en la operación.
     */
    public boolean editar(Empleado empleado) throws SQLException {
        estadoOperacion = false;
        connection = obtenerConexion();
        try {
            connection.setAutoCommit(false);

            // Actualizar datos en la tabla empleados
            String sql = "UPDATE empleados SET nombre = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, empleado.getNombre());
            statement.setString(2, String.valueOf(empleado.getSexo()));
            statement.setInt(3, empleado.getCategoria());
            statement.setInt(4, empleado.getAnyos());
            statement.setString(5, empleado.getDni());

            int filasActualizadas = statement.executeUpdate();
            estadoOperacion = filasActualizadas > 0;
            System.out.println("Filas actualizadas en empleados: " + filasActualizadas + " para DNI: " + empleado.getDni());

            // Recalcular sueldo usando la clase Nomina
            Nomina nomina = new Nomina();
            double sueldo = nomina.sueldo(empleado);

            // Actualizar o insertar sueldo en la tabla nominas
            sql = "INSERT INTO nominas (dni, sueldo) VALUES (?, ?) ON DUPLICATE KEY UPDATE sueldo = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, empleado.getDni());
            statement.setDouble(2, sueldo);
            statement.setDouble(3, sueldo);
            filasActualizadas = statement.executeUpdate();
            estadoOperacion = estadoOperacion && filasActualizadas > 0;
            System.out.println("Filas actualizadas en nominas: " + filasActualizadas + " para DNI: " + empleado.getDni());

            connection.commit();
            System.out.println("Commit ejecutado para DNI: " + empleado.getDni());
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
     * Busca empleados según un criterio y valor especificados.
     * @param criterio Campo por el que buscar (dni, nombre, sexo, categoria, anyos).
     * @param valor Valor a buscar.
     * @return Lista de empleados que coinciden con el criterio.
     * @throws SQLException Si ocurre un error en la consulta.
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
     * Obtiene la nómina completa de un empleado (datos del empleado y su sueldo).
     * Si el sueldo no existe en nominas, lo calcula usando la clase Nomina.
     * @param dni DNI del empleado.
     * @return Objeto NominaCompleta con los datos del empleado y su sueldo, o null si no se encuentra.
     * @throws SQLException Si ocurre un error en la consulta.
     */
    public NominaCompleta obtenerNomina(String dni) throws SQLException {
        ResultSet resultSet = null;
        Empleado empleado = null;
        double sueldo = -1.0;
        connection = obtenerConexion();

        try {
            // Obtener datos del empleado
            empleado = obtenerEmpleado(dni);
            if (empleado.getDni() == null) {
                return null; // Empleado no encontrado
            }

            // Obtener sueldo de nominas
            String sql = "SELECT sueldo FROM nominas WHERE dni = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, dni);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                sueldo = resultSet.getDouble("sueldo");
            } else {
                // Calcular sueldo si no existe
                Nomina nomina = new Nomina();
                sueldo = nomina.sueldo(empleado);
                sql = "INSERT INTO nominas (dni, sueldo) VALUES (?, ?) ON DUPLICATE KEY UPDATE sueldo = ?";
                statement = connection.prepareStatement(sql);
                statement.setString(1, dni);
                statement.setDouble(2, sueldo);
                statement.setDouble(3, sueldo);
                statement.executeUpdate();
            }
            return new NominaCompleta(empleado, sueldo);
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    /**
     * Obtiene la conexión a la base de datos desde el pool de conexiones.
     * @return Conexión a la base de datos.
     * @throws SQLException Si no se puede establecer la conexión.
     */
    private Connection obtenerConexion() throws SQLException {
        return Conexion.getConnection();
    }
}