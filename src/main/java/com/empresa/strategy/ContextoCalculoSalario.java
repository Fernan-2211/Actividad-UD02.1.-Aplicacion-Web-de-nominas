package com.empresa.strategy;

import com.empresa.empleado.Empleado;

/**
 * Contexto que utiliza una estrategia de cálculo de salario.
 * 
 * Permite cambiar la estrategia de cálculo en tiempo de ejecución sin afectar
 * al resto del código. El cliente solo interactúa con este contexto.
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public class ContextoCalculoSalario {

    /** Estrategia de cálculo actual */
    private CalculoSalarioStrategy estrategia;

    /**
     * Constructor que recibe la estrategia concreta a usar.
     * 
     * @param estrategia Estrategia de cálculo de salario
     */
    public ContextoCalculoSalario(CalculoSalarioStrategy estrategia) {
        this.estrategia = estrategia;
    }

    /**
     * Calcula el salario del empleado usando la estrategia configurada.
     * 
     * @param empleado Empleado cuyo salario se desea calcular
     * @return Salario calculado
     */
    public double calcularSalario(Empleado empleado) {
        return estrategia.calcular(empleado);
    }
}
