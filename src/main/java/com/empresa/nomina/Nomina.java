package com.empresa.nomina;

import com.empresa.empleado.Empleado;

/**
 * Clase que calcula el sueldo de un empleado basado en su categoría y años trabajados.
 */
public class Nomina {

    // Array constante con los sueldos base por categoría (1 a 10).
    private static final int[] SUELDO_BASE = {
        50000, 70000, 90000, 110000, 130000,
        150000, 170000, 190000, 210000, 230000
    };

    /**
     * Calcula el sueldo total de un empleado.
     * @param emp El empleado cuyo sueldo se desea calcular.
     * @return El sueldo total (sueldo base + plus por años trabajados).
     */
    public int sueldo(Empleado emp) {
        // Obtener sueldo base según categoría (restamos 1 porque el array empieza en 0)
        int sueldoBase = SUELDO_BASE[emp.getCategoria() - 1];
        // Plus de 5000 por cada año trabajado
        int plusPorAnyos = 5000 * emp.getAnyos();
        return sueldoBase + plusPorAnyos;
    }
}