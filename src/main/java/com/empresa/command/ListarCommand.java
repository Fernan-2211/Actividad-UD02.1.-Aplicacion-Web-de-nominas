package com.empresa.command;

import com.empresa.facade.EmpresaFacade;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Comando que lista todos los empleados en la base de datos.
 * 
 * El resultado se envía a la vista listar.jsp.
 * Autor: Fernando
 * Versión: 1.0
 */
public class ListarCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EmpresaFacade facade = new EmpresaFacade();
        request.setAttribute("listaEmpleados", facade.listarEmpleados());
        request.getRequestDispatcher("/views/listar.jsp").forward(request, response);
    }
}
