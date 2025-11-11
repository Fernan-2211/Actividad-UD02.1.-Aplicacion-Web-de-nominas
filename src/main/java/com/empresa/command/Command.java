package com.empresa.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interfaz de comando que define la acción a ejecutar.
 * 
 * Todas las acciones (listar, editar, buscar, mostrar salario, etc.)
 * implementarán esta interfaz para desacoplar la lógica del FrontController.
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public interface Command {
    
    /**
     * Ejecuta la acción correspondiente.
     * 
     * @param request Objeto HttpServletRequest con los parámetros del cliente
     * @param response Objeto HttpServletResponse para enviar la respuesta
     * @throws Exception Si ocurre algún error durante la ejecución
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
