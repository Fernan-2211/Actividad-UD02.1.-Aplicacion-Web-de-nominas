package com.empresa.strategy;

import com.empresa.empleado.Empleado;

/**
 * Estrategia concreta que implementa el cálculo base del salario.
 * 
 * Esta clase define la forma estándar de calcular el salario de un empleado
 * usando una fórmula simple basada en la categoría y los años trabajados.
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public class CalculoSalarioBase implements CalculoSalarioStrategy {

    /**
     * Calcula el salario de un empleado.
     * Fórmula base: 1000 + (categoría * 150) + (años trabajados * 50)
     * 
     * @param e Empleado cuyo salario se desea calcular
     * @return Salario calculado
     */
    @Override
    public double calcular(Empleado e) {
        return 1000 + (e.getCategoria() * 150) + (e.getAnyos() * 50);
    }
}
