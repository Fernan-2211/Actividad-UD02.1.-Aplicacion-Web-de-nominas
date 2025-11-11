package com.empresa.strategy;

import com.empresa.empleado.Empleado;

/**
 * Interfaz que define la estrategia de cálculo de salario.
 * 
 * Cualquier algoritmo de cálculo de salario debe implementar esta interfaz,
 * permitiendo cambiar la estrategia sin modificar el código cliente.
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public interface CalculoSalarioStrategy {
    
    /**
     * Calcula el salario de un empleado según la estrategia concreta.
     * 
     * @param empleado Empleado cuyo salario se desea calcular
     * @return Salario calculado
     */
    double calcular(Empleado empleado);
}
