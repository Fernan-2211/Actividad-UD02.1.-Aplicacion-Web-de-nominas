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
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
        <button onclick="history.back()">Volver</button>
    </c:if>

    <c:if test="${empty error && not empty empleado}">
        <table>
            <tr><th>DNI</th><td>${empleado.dni}</td></tr>
            <tr><th>Nombre</th><td>${empleado.nombre}</td></tr>
            <tr><th>Sexo</th><td>${empleado.sexo}</td></tr>
            <tr><th>Categoría</th><td>${empleado.categoria}</td></tr>
            <tr><th>Años</th><td>${empleado.anyos}</td></tr>
            <tr><th><strong>Sueldo</strong></th><td><strong>${sueldo}</strong></td></tr>
        </table>
        <br>
        <div class="volver-container">
		<input type="button" value="Volver"
			onclick="window.location.href='${pageContext.request.contextPath}/empleados?opcion=buscarSalario'">
		</div>

    </c:if>
</body>
</html>