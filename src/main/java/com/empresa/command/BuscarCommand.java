package com.empresa.command;

import com.empresa.facade.EmpresaFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Comando que realiza la búsqueda de empleados según un criterio y valor.
 * 
 * El resultado se envía a la vista listar.jsp.
 * Autor: Fernando
 * Versión: 1.0
 */
public class BuscarCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EmpresaFacade facade = new EmpresaFacade();

        String criterio = request.getParameter("criterio");
        String valor = request.getParameter("valor");

        request.setAttribute("listaEmpleados", facade.buscarEmpleados(criterio, valor));
        request.getRequestDispatcher("/views/listar.jsp").forward(request, response);
    }
}
