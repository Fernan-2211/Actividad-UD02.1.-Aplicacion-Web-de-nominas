package com.empresa.nomina;

import com.empresa.empleado.Empleado;

/**
 * Clase que calcula el sueldo de un empleado basado en su categoría y años trabajados.
 */
public class Nomina {

    // Array constante con los sueldos base por categoría (1 a 10).
    private static final double[] SUELDO_BASE = {
        50000.0, 70000.0, 90000.0, 110000.0, 130000.0,
        150000.0, 170000.0, 190000.0, 210000.0, 230000.0
    };

    /**
     * Calcula el sueldo total de un empleado.
     * @param emp El empleado cuyo sueldo se desea calcular.
     * @return El sueldo total (sueldo base + plus por años trabajados).
     */
    public double sueldo(Empleado emp) {
        // Obtener sueldo base según categoría (restamos 1 porque el array empieza en 0)
        double sueldoBase = SUELDO_BASE[emp.getCategoria() - 1];
        // Plus de 5000 por cada año trabajado
        double plusPorAnyos = 5000.0 * emp.getAnyos();
        return sueldoBase + plusPorAnyos;
    }
}