package com.empresa.empleado;
 
 
public class Empleado {
 
 private String dni;
 private String nombre;
 private char sexo;
 private int categoria;
 private int anyos;
 
 public Empleado(String dni, String nombre, char sexo, int categoria, int anyos) {
  super();
  this.dni = dni;
  this.nombre = nombre;
  this.sexo = sexo;
  this.categoria= categoria;
  this.anyos = anyos;

 }
 
 public Empleado() {
  // TODO Auto-generated constructor stub
 }
 
 
 public String getDni() {
	return dni;
}

 public void setDni(String dni) {
	this.dni = dni;
 }

 public String getNombre() {
	return nombre;
 }

 public void setNombre(String nombre) {
	this.nombre = nombre;
 }

 public char getSexo() {
	return sexo;
 }

 public void setSexo(char sexo) {
	this.sexo = sexo;
 }

 public int getCategoria() {
	return categoria;
 }

 public void setCategoria(int categoria) {
	this.categoria = categoria;
 }

 public int getAnyos() {
	return anyos;
 }

 public void setAnyos(int anyos) {
	this.anyos = anyos;
 }

 @Override
 public String toString() {
	 return "Empleado [dni=" + dni + ", nombre=" + nombre + ", sexo=" + sexo + ", categoria=" + categoria
			    + ", anyos=" + anyos + "]";
 }
 
}