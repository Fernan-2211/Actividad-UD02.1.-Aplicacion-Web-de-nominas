package com.empresa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.empresa.conexion.Conexion;
import com.empresa.empleado.Empleado;

public class empresaDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacion;

	// guardar producto
	public boolean guardar(Empleado empleado) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			connection.setAutoCommit(false);
			sql = "INSERT INTO empleados (dni, nombre, sexo, categoria, anyos) VALUES(?,?,?,?,?)";
			statement = connection.prepareStatement(sql);

			statement.setString(1, empleado.getDni());
			statement.setString(2, empleado.getNombre());
			statement.setString(3, String.valueOf(empleado.getSexo()));
			statement.setInt(4, empleado.getCategoria());
			statement.setInt(5, empleado.getAnyos());

			estadoOperacion = statement.executeUpdate() > 0;

			connection.commit();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	// editar producto
	public boolean editar(Empleado empleado) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "UPDATE empleados SET nombre=?, sexo=?, categoria=?, anyos =? WHERE dni=?";
			statement = connection.prepareStatement(sql);

			statement.setString(1, empleado.getDni());
			statement.setString(2, empleado.getNombre());
			statement.setString(3, String.valueOf(empleado.getSexo()));
			statement.setInt(4, empleado.getCategoria());
			statement.setInt(5, empleado.getAnyos());

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	// eliminar producto
	public boolean eliminar(String dni) throws SQLException {
		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();
		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM empleados WHERE dni =?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, dni);

			estadoOperacion = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();
			connection.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		}

		return estadoOperacion;
	}

	// obtener lista de productos
	public List<Empleado> obtenerEmpleados() throws SQLException {
		ResultSet resultSet = null;
		List<Empleado> listaProductos = new ArrayList<>();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM empleados";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Empleado p = new Empleado();
				p.setDni(resultSet.getString(1));
				p.setNombre(resultSet.getString(2));
				p.setSexo(resultSet.getString(3).charAt(0));
				p.setCategoria(resultSet.getInt(4));
				p.setAnyos(resultSet.getInt(5));
				listaProductos.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaProductos;
	}

	// obtener producto
	public Empleado obtenerEmpleado(String dniEmpleado) throws SQLException {
		ResultSet resultSet = null;
		Empleado p = new Empleado();

		String sql = null;
		estadoOperacion = false;
		connection = obtenerConexion();

		try {
			sql = "SELECT * FROM empleados WHERE dni =?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, dniEmpleado);

			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				p.setDni(resultSet.getString(1));
				p.setNombre(resultSet.getString(2));
				p.setSexo(resultSet.getString(3).charAt(0));
				p.setCategoria(resultSet.getInt(4));
				p.setAnyos(resultSet.getInt(5));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	// obtener conexion pool
	private Connection obtenerConexion() throws SQLException {
		return Conexion.getConnection();
	}

}