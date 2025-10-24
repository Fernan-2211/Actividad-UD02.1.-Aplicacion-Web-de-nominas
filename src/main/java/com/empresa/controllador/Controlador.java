package com.empresa.controllador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.empresa.dao.empresaDAO; // Cambiado a empresaDAO
import com.empresa.empleado.Empleado;

/**
 * Servlet que maneja las peticiones para listar, buscar, editar y mostrar salarios de empleados.
 */
@WebServlet(urlPatterns = { "/empleados" })
public class Controlador extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor por defecto.
     */
    public Controlador() {
        super();
    }

    /**
     * Maneja las peticiones GET para listar, buscar o editar empleados.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("opcion");
        RequestDispatcher dispatcher;

        try {
            empresaDAO empresaDAO = new empresaDAO();
            switch (opcion) {
                case "listar":
                    List<Empleado> lista = empresaDAO.obtenerEmpleados();
                    request.setAttribute("lista", lista);
                    dispatcher = request.getRequestDispatcher("/views/listar.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "buscarSalario":
                    dispatcher = request.getRequestDispatcher("/views/buscarSalario.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "buscarEditar":
                    dispatcher = request.getRequestDispatcher("/views/buscarEditar.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "editar":
                    String dni = request.getParameter("dni");
                    Empleado emp = empresaDAO.obtenerEmpleado(dni);
                    request.setAttribute("empleados", emp);
                    dispatcher = request.getRequestDispatcher("/views/editar.jsp");
                    dispatcher.forward(request, response);
                    break;

                default:
                    request.setAttribute("error", "Opción no válida.");
                    dispatcher = request.getRequestDispatcher("/views/error.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos: " + e.getMessage());
            dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * Maneja las peticiones POST para buscar salario, buscar empleados o editar.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("opcion");
        RequestDispatcher dispatcher;

        try {
            empresaDAO empresaDAO = new empresaDAO(); // Cambiado a empresaDAO
            switch (opcion) {
            case "mostrarSalario":
                String dniSalario = request.getParameter("dni");
                Empleado empSalario = empresaDAO.obtenerEmpleado(dniSalario);
                double sueldo = empresaDAO.obtenerSalario(dniSalario);
                request.setAttribute("empleado", empSalario);
                request.setAttribute("sueldo", sueldo);
                dispatcher = request.getRequestDispatcher("/views/mostrarSalario.jsp");
                dispatcher.forward(request, response);
                break;
                case "buscarParaEditar":
                    String criterio = request.getParameter("criterio");
                    String valor = request.getParameter("valor");
                    List<Empleado> lista = empresaDAO.buscarEmpleados(criterio, valor);
                    request.setAttribute("lista", lista);
                    dispatcher = request.getRequestDispatcher("/views/listarEditar.jsp");
                    dispatcher.forward(request, response);
                    break;

                case "editar":
                    // Validar datos del formulario
                    String dni = request.getParameter("dni");
                    String nombre = request.getParameter("nombre");
                    String sexoStr = request.getParameter("sexo");
                    String categoriaStr = request.getParameter("categoria");
                    String anyosStr = request.getParameter("anyos");

                    // Validaciones básicas
                    if (nombre == null || nombre.trim().isEmpty()) {
                        throw new IllegalArgumentException("El nombre no puede estar vacío.");
                    }
                    if (sexoStr == null || sexoStr.trim().isEmpty() || (!sexoStr.equalsIgnoreCase("M") && !sexoStr.equalsIgnoreCase("F"))) {
                        throw new IllegalArgumentException("El sexo debe ser M o F.");
                    }
                    int categoria;
                    int anyos;
                    try {
                        categoria = Integer.parseInt(categoriaStr);
                        if (categoria < 1 || categoria > 10) {
                            throw new IllegalArgumentException("La categoría debe estar entre 1 y 10.");
                        }
                        anyos = Integer.parseInt(anyosStr);
                        if (anyos < 0) {
                            throw new IllegalArgumentException("Los años trabajados no pueden ser negativos.");
                        }
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Categoría y años deben ser números válidos.");
                    }

                    // Crear objeto Empleado con los datos validados
                    Empleado empleado = new Empleado();
                    empleado.setDni(dni);
                    empleado.setNombre(nombre);
                    empleado.setSexo(sexoStr.toUpperCase().charAt(0));
                    empleado.setCategoria(categoria);
                    empleado.setAnyos(anyos);

                    // Guardar cambios en la base de datos
                    boolean exito = empresaDAO.editar(empleado);
                    if (exito) {
                        request.setAttribute("mensaje", "Empleado editado satisfactoriamente.");
                        dispatcher = request.getRequestDispatcher("/views/exito.jsp");
                    } else {
                        request.setAttribute("error", "No se pudo editar el empleado.");
                        dispatcher = request.getRequestDispatcher("/views/error.jsp");
                    }
                    dispatcher.forward(request, response);
                    break;

                default:
                    request.setAttribute("error", "Opción no válida.");
                    dispatcher = request.getRequestDispatcher("/views/error.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en la base de datos: " + e.getMessage());
            dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error en los datos: " + e.getMessage());
            dispatcher = request.getRequestDispatcher("/views/error.jsp");
            dispatcher.forward(request, response);
        }
    }
}