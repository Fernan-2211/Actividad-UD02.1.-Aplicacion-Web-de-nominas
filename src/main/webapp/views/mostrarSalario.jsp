<!-- src/main/webapp/views/mostrarSalario.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Mostrar Nómina</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Nómina del Empleado</h1>
    <c:if test="${not empty empleado}">
        <table>
            <tr>
                <th>DNI</th>
                <th>Nombre</th>
                <th>Sexo</th>
                <th>Categoría</th>
                <th>Años Trabajados</th>
                <th>Sueldo</th>
            </tr>
            <tr>
                <td>${empleado.dni}</td>
                <td>${empleado.nombre}</td>
                <td>${empleado.sexo}</td>
                <td>${empleado.categoria}</td>
                <td>${empleado.anyos}</td>
                <td>${sueldo}</td>
            </tr>
        </table>
    </c:if>
    <c:if test="${empty empleado}">
        <p class="error">Empleado no encontrado.</p>
    </c:if>
    <br>
    <input type="button" value="Volver" onclick="window.location.href='${pageContext.request.contextPath}/empleados?opcion=buscarSalario'">
</body>
</html>