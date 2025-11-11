package com.empresa.empleado;

/**
 * Clase que representa un empleado de la empresa.
 * Contiene los datos básicos necesarios para la gestión de nóminas y operaciones de empleados.
 * 
 * Autor: Fernando
 * Versión: 1.0
 */
public class Empleado {

    /** DNI del empleado, identificador único */
    private String dni;

    /** Nombre completo del empleado */
    private String nombre;

    /** Sexo del empleado: 'M' masculino, 'F' femenino */
    private char sexo;

    /** Categoría laboral del empleado (entero del 1 al 10) */
    private int categoria;

    /** Años de antigüedad del empleado */
    private int anyos;

    /**
     * Constructor completo con todos los atributos.
     * @param dni DNI del empleado
     * @param nombre Nombre completo
     * @param sexo Sexo ('M' o 'F')
     * @param categoria Categoría laboral
     * @param anyos Años trabajados
     */
    public Empleado(String dni, String nombre, char sexo, int categoria, int anyos) {
        super();
        this.dni = dni;
        this.nombre = nombre;
        this.sexo = sexo;
        this.categoria = categoria;
        this.anyos = anyos;
    }

    /**
     * Constructor vacío, útil para inicializar objetos sin datos.
     */
    public Empleado() {
        // Constructor vacío
    }

    // --- GETTERS Y SETTERS ---

    /** @return el DNI del empleado */
    public String getDni() {
        return dni;
    }

    /** @param dni el DNI a asignar */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /** @return el nombre del empleado */
    public String getNombre() {
        return nombre;
    }

    /** @param nombre el nombre a asignar */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** @return el sexo del empleado */
    public char getSexo() {
        return sexo;
    }

    /** @param sexo el sexo a asignar ('M' o 'F') */
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    /** @return la categoría laboral del empleado */
    public int getCategoria() {
        return categoria;
    }

    /** @param categoria la categoría laboral a asignar */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /** @return los años de antigüedad del empleado */
    public int getAnyos() {
        return anyos;
    }

    /** @param anyos los años de antigüedad a asignar */
    public void setAnyos(int anyos) {
        this.anyos = anyos;
    }

    /**
     * Representación en cadena del empleado, útil para depuración y logs.
     * @return Cadena con los datos del empleado
     */
    @Override
    public String toString() {
        return "Empleado [dni=" + dni + ", nombre=" + nombre + ", sexo=" + sexo + ", categoria=" + categoria
                + ", anyos=" + anyos + "]";
    }
}
