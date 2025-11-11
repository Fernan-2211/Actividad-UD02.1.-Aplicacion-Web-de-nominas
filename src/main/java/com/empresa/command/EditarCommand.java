package com.empresa.command;

import com.empresa.empleado.Empleado;
import com.empresa.facade.EmpresaFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Comando que edita los datos de un empleado y actualiza la base de datos.
 * 
 * Si la edición es correcta, muestra un mensaje de éxito; si falla, muestra un error.
 * También actualiza la lista de empleados para refrescar la vista listar.jsp.
 * Autor: Fernando
 * Versión: 1.0
 */
public class EditarCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EmpresaFacade facade = new EmpresaFacade();

        String dni = request.getParameter("dni");
        String nombre = request.getParameter("nombre");
        char sexo = request.getParameter("sexo").charAt(0);
        int categoria = Integer.parseInt(request.getParameter("categoria"));
        int anyos = Integer.parseInt(request.getParameter("anyos"));

        Empleado emp = new Empleado(dni, nombre, sexo, categoria, anyos);
        boolean actualizado = facade.editarEmpleado(emp);

        if (actualizado) {
            request.setAttribute("mensaje", "Empleado actualizado correctamente");
        } else {
            request.setAttribute("error", "Error al actualizar empleado");
        }

        request.setAttribute("listaEmpleados", facade.listarEmpleados());
        request.getRequestDispatcher("/views/listar.jsp").forward(request, response);
    }
}
