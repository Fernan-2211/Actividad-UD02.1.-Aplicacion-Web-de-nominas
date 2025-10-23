<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Editar Empleado</title>
</head>
<body>
<h1>Editar Empleado</h1>

<form action="empleados" method="post">
    <input type="hidden" name="opcion" value="editar">
    <input type="hidden" name="dni" value="${empleado.dni}">
    <table>
        <tr>
            <td>Nombre:</td>
            <td><input type="text" name="nombre" value="${empleado.nombre}" required></td>
        </tr>
        <tr>
            <td>Sexo:</td>
            <td>
                <select name="sexo" required>
                    <option value="M" <c:if test="${empleado.sexo == 'M'}">selected</c:if>>Masculino</option>
                    <option value="F" <c:if test="${empleado.sexo == 'F'}">selected</c:if>>Femenino</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>Categoría:</td>
            <td><input type="number" name="categoria" value="${empleado.categoria}" required></td>
        </tr>
        <tr>
            <td>Años:</td>
            <td><input type="number" name="anyos" value="${empleado.anyos}" required></td>
        </tr>
    </table>
    <input type="submit" value="Guardar">
</form>

</body>
</html>
