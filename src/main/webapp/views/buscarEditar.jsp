<!-- src/main/webapp/views/buscarEditar.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Buscar Empleado para Editar</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Buscar Empleado para Editar</h1>
    <form action="${pageContext.request.contextPath}/empleados" method="post">
        <input type="hidden" name="opcion" value="buscarParaEditar">
        <label for="criterio">Criterio de búsqueda:</label>
        <select name="criterio" id="criterio">
            <option value="dni">DNI</option>
            <option value="nombre">Nombre</option>
            <option value="sexo">Sexo (M/F)</option> <!-- CAMBIO: Agregado -->
            <option value="categoria">Categoría</option> <!-- CAMBIO: Agregado -->
            <option value="anyos">Años Trabajados</option> <!-- CAMBIO: Agregado -->
        </select>
        <br><br>
        <label for="valor">Valor:</label>
        <input type="text" id="valor" name="valor" required>
        <br><br>
        <input type="submit" value="Buscar">
        <input type="button" value="Volver" onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'">
    </form>
</body>
</html>