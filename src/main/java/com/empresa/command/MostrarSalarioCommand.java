package com.empresa.command;

import com.empresa.empleado.Empleado;
import com.empresa.facade.EmpresaFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Comando que obtiene el salario de un empleado y lo muestra en la vista mostrarSalario.jsp.
 * 
 * Recupera el empleado y su salario desde la fachada y los pasa como atributos a la vista.
 * Autor: Fernando
 * Versión: 1.0
 */
public class MostrarSalarioCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EmpresaFacade facade = new EmpresaFacade();
        String dni = request.getParameter("dni");

        Empleado emp = facade.buscarEmpleado(dni);
        int sueldo = facade.obtenerSalario(dni);

        request.setAttribute("empleado", emp);
        request.setAttribute("sueldo", sueldo);
        request.getRequestDispatcher("/views/mostrarSalario.jsp").forward(request, response);
    }
}
