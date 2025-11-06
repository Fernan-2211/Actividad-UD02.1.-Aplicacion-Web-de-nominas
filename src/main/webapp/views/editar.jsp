<!-- src/main/webapp/views/editar.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Editar Empleado</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Editar Empleado</h1>
    <c:if test="${not empty empleados}">
        <form action="${pageContext.request.contextPath}/empleados" method="post">
            <input type="hidden" name="opcion" value="editar">
            <label for="dni">DNI:</label>
            <input type="text" id="dni" name="dni" value="${empleados.dni}" readonly>
            <br>
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="${empleados.nombre}" required>
            <br>
            <label for="sexo">Sexo (M/F):</label>
            <select id="sexo" name="sexo" required>
    <c:choose>
    <c:when test="${empleados.sexo == 1}">
        <option value="M" selected>Masculino</option>
        <option value="F">Femenino</option>
    </c:when>
    <c:otherwise>
        <option value="M">Masculino</option>
        <option value="F" selected>Femenino</option>
    </c:otherwise>
</c:choose>
    
</select>
            
            
            <br>
            <label for="categoria">Categoría (1-10):</label>
            <input type="number" id="categoria" name="categoria" value="${empleados.categoria}" min="1" max="10" required>
            <br>
            <label for="anyos">Años Trabajados:</label>
            <input type="number" id="anyos" name="anyos" value="${empleados.anyos}" min="0" required>
            <br><br>
            <input type="submit" value="Guardar Cambios">
            <input type="button" value="Volver" onclick="window.location.href='${pageContext.request.contextPath}/empleados?opcion=buscarEditar'">
        </form>
    </c:if>
    <c:if test="${empty empleados}">
        <p class="error">Empleado no encontrado.</p>
        <input type="button" value="Volver" onclick="window.location.href='${pageContext.request.contextPath}/empleados?opcion=buscarEditar'">
    </c:if>
</body>
</html>