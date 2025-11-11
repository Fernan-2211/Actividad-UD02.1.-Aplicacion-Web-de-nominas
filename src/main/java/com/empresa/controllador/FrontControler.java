package com.empresa.controllador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.empresa.empleado.Empleado;
import com.empresa.facade.EmpresaFacade;

/**
 * FrontControler que gestiona todas las peticiones relacionadas con empleados.
 * Implementa el patrón Command para reemplazar el uso de un switch tradicional.
 * Cada acción se mapea a un comando que sabe cómo procesar la petición.
 * 
 * @author Fernando
 * @version 1.0
 */
@WebServlet(urlPatterns = { "/empleados" })
public class FrontControler extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Interfaz interna para los comandos. 
     * Cada comando implementa este método para ejecutar la acción con la request y response.
     */
    private interface Command {
        void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
    }

    /**
     * Mapa que asocia cada clave de acción (String) con un comando que la ejecuta.
     * Por ejemplo: "listar" -> ListarCommand
     */
    private Map<String, Command> comandos;

    /** Fachada de la empresa que encapsula la lógica de negocio y el acceso a DAO. */
    private EmpresaFacade empresaFacade;

    /**
     * Inicializa el servlet y carga todos los comandos en el mapa.
     * Esto se ejecuta cuando se arranca la aplicación web.
     */
    @Override
    public void init() throws ServletException {
        empresaFacade = new EmpresaFacade();
        comandos = new HashMap<>();

        // Comando: listar todos los empleados
        comandos.put("listar", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                // Llamamos a la fachada para obtener la lista de empleados
                List<Empleado> lista = empresaFacade.listarEmpleados();
                // Guardamos la lista como atributo para la vista
                request.setAttribute("lista", lista);
                // Redirigimos a la página listar.jsp
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/listar.jsp");
                dispatcher.forward(request, response);
            }
        });

        // Comando: mostrar el formulario de buscar salario (GET)
        comandos.put("buscarSalario", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                // Solo mostramos la página con el formulario
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/buscarSalario.jsp");
                dispatcher.forward(request, response);
            }
        });

        // Comando: mostrar el formulario de búsqueda para editar (GET)
        comandos.put("buscarEditar", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                // Mostramos la página con el formulario de búsqueda
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/buscarEditar.jsp");
                dispatcher.forward(request, response);
            }
        });

        // Comando: mostrar formulario editar (GET con DNI)
        comandos.put("editar", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                // Obtenemos el DNI de la request
                String dni = request.getParameter("dni");
                if (dni == null || dni.trim().isEmpty()) {
                    // Si no viene DNI, mostramos mensaje de error
                    request.setAttribute("error", "DNI no proporcionado para editar.");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }
                // Buscamos el empleado por DNI
                Empleado emp = empresaFacade.buscarEmpleado(dni);
                request.setAttribute("empleados", emp);
                // Redirigimos a editar.jsp
                request.getRequestDispatcher("/views/editar.jsp").forward(request, response);
            }
        });

        // Comando: procesar la edición de un empleado (POST)
        comandos.put("editarPost", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                // Recogemos todos los parámetros del formulario
                String dni = request.getParameter("dni");
                String nombre = request.getParameter("nombre");
                String sexoStr = request.getParameter("sexo");
                String categoriaStr = request.getParameter("categoria");
                String anyosStr = request.getParameter("anyos");

                // Validaciones básicas de los datos
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

                // Creamos un objeto Empleado con los datos recibidos
                Empleado empleado = new Empleado();
                empleado.setDni(dni);
                empleado.setNombre(nombre);
                empleado.setSexo(sexoStr.toUpperCase().charAt(0));
                empleado.setCategoria(categoria);
                empleado.setAnyos(anyos);

                // Llamamos a la fachada para editar al empleado
                boolean exito = empresaFacade.editarEmpleado(empleado);

                // Mostramos la vista correspondiente según el resultado
                if (exito) {
                    request.setAttribute("mensaje", "Empleado editado satisfactoriamente.");
                    request.getRequestDispatcher("/views/exito.jsp").forward(request, response);
                } else {
                    request.setAttribute("error", "No se pudo editar el empleado.");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                }
            }
        });

        // Comando: buscar empleados para editar y listar resultados
        comandos.put("buscarParaEditar", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                String criterio = request.getParameter("criterio");
                String valor = request.getParameter("valor");
                // Usamos la fachada para buscar según criterio
                List<Empleado> lista = empresaFacade.buscarEmpleados(criterio, valor);
                request.setAttribute("lista", lista);
                // Redirigimos a listarEditar.jsp
                request.getRequestDispatcher("/views/listarEditar.jsp").forward(request, response);
            }
        });

        // Comando: mostrar salario de un empleado
        comandos.put("mostrarSalario", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                String dni = request.getParameter("dni");
                if (dni == null || dni.trim().isEmpty()) {
                    request.setAttribute("error", "DNI no proporcionado.");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }
                Empleado emp = empresaFacade.buscarEmpleado(dni);
                if (emp == null || emp.getDni() == null || emp.getDni().isEmpty()) {
                    request.setAttribute("error", "Empleado con DNI '" + dni + "' no encontrado.");
                    request.getRequestDispatcher("/views/error.jsp").forward(request, response);
                    return;
                }
                int sueldo = empresaFacade.obtenerSalario(dni);
                request.setAttribute("empleado", emp);
                request.setAttribute("sueldo", sueldo);
                request.getRequestDispatcher("/views/mostrarSalario.jsp").forward(request, response);
            }
        });

        // Comando: buscar genérico para listar resultados
        comandos.put("buscar", new Command() {
            @Override
            public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
                String criterio = request.getParameter("criterio");
                String valor = request.getParameter("valor");
                List<Empleado> lista = empresaFacade.buscarEmpleados(criterio, valor);
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("/views/listar.jsp").forward(request, response);
            }
        });
    }

    /**
     * Procesa las peticiones GET redirigiéndolas al método procesar.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }

    /**
     * Procesa las peticiones POST redirigiéndolas al método procesar.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        procesar(request, response);
    }

    /**
     * Método central que gestiona todas las acciones según el parámetro "opcion".
     * Decide qué comando ejecutar según la acción solicitada y maneja errores.
     *
     * @param request  Objeto HttpServletRequest
     * @param response Objeto HttpServletResponse
     * @throws ServletException si ocurre un error de servlet
     * @throws IOException      si ocurre un error de I/O
     */
    private void procesar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("opcion");

        // Si es POST y la opción es "editar", usamos el comando "editarPost"
        if ("POST".equalsIgnoreCase(request.getMethod()) && "editar".equals(opcion)) {
            opcion = "editarPost";
        }

        Command comando = comandos.get(opcion);

        if (comando != null) {
            try {
                // Ejecutamos el comando correspondiente
                comando.execute(request, response);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                request.setAttribute("error", "Error en los datos: " + ex.getMessage());
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            } catch (SQLException ex) {
                ex.printStackTrace();
                request.setAttribute("error", "Error en la base de datos: " + ex.getMessage());
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("error", "Error inesperado: " + ex.getMessage());
                request.getRequestDispatcher("/views/error.jsp").forward(request, response);
            }
        } else {
            // Si la opción no existe, mostramos error
            request.setAttribute("error", "Opción no válida: " + opcion);
            request.getRequestDispatcher("/views/error.jsp").forward(request, response);
        }
    }
}
