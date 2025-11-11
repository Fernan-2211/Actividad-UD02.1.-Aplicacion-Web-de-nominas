package com.empresa.facade;

import java.sql.SQLException;
import java.util.List;
import com.empresa.dao.EmpresaDAO;
import com.empresa.empleado.Empleado;

/**
 * Fachada que centraliza las operaciones sobre empleados y nóminas.
 * 
 * Esta clase actúa como intermediario entre la capa de presentación (Servlets/JSP) 
 * y la capa de acceso a datos (DAO), ocultando la complejidad de la lógica de negocio y
 * acceso a la base de datos.
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public class EmpresaFacade {

    /** Objeto DAO que gestiona las operaciones en la base de datos */
    private EmpresaDAO dao;

    /**
     * Constructor de la fachada.
     * Inicializa el DAO de empleados y nóminas.
     */
    public EmpresaFacade() {
        dao = new EmpresaDAO();
    }

    /**
     * Lista todos los empleados registrados en la base de datos.
     * @return Lista de objetos Empleado
     * @throws SQLException Si ocurre un error al consultar la base de datos
     */
    public List<Empleado> listarEmpleados() throws SQLException {
        return dao.obtenerEmpleados();
    }

    /**
     * Busca un empleado por su DNI.
     * @param dni DNI del empleado
     * @return Objeto Empleado correspondiente al DNI, o vacío si no existe
     * @throws SQLException Si ocurre un error al consultar la base de datos
     */
    public Empleado buscarEmpleado(String dni) throws SQLException {
        return dao.obtenerEmpleado(dni);
    }

    /**
     * Obtiene o calcula el salario de un empleado.
     * 
     * Primero busca el salario en la tabla de nóminas. Si no existe, lo calcula
     * usando la estrategia definida (por defecto CalculoSalarioBase) y lo inserta en la base de datos.
     * 
     * @param dni DNI del empleado
     * @return Salario del empleado
     * @throws SQLException Si ocurre un error al consultar o actualizar la base de datos
     */
    public int obtenerSalario(String dni) throws SQLException {
        return dao.obtenerSalario(dni);
    }

    /**
     * Edita los datos de un empleado y recalcula su salario.
     * 
     * Actualiza tanto la tabla de empleados como la de nóminas, usando la estrategia
     * de cálculo de salario.
     * 
     * @param emp Objeto Empleado con los datos actualizados
     * @return true si la edición fue exitosa, false en caso contrario
     * @throws SQLException Si ocurre un error al actualizar la base de datos
     */
    public boolean editarEmpleado(Empleado emp) throws SQLException {
        return dao.editar(emp);
    }

    /**
     * Busca empleados según un criterio y un valor dado.
     * 
     * Permite buscar por DNI, nombre, sexo, categoría o años de antigüedad.
     * 
     * @param criterio Campo por el que buscar ("dni", "nombre", "sexo", "categoria", "anyos")
     * @param valor Valor a buscar
     * @return Lista de empleados que cumplen el criterio de búsqueda
     * @throws SQLException Si ocurre un error al consultar la base de datos
     */
    public List<Empleado> buscarEmpleados(String criterio, String valor) throws SQLException {
        return dao.buscarEmpleados(criterio, valor);
    }
}
