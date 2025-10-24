<!-- src/main/webapp/views/buscarSalario.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Buscar Salario de Empleado</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Buscar Salario de Empleado</h1>
    <form action="${pageContext.request.contextPath}/empleados" method="post">
        <input type="hidden" name="opcion" value="mostrarSalario">
        <label for="dni">DNI del Empleado:</label>
        <input type="text" id="dni" name="dni" required>
        <br><br>
        <input type="submit" value="Buscar Salario">
        <input type="button" value="Volver" onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'">
    </form>
</body>
</html>