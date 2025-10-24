<!-- src/main/webapp/views/exito.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Operación Exitosa</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Operación Exitosa</h1>
    <p class="success">${mensaje}</p>
    <input type="button" value="Volver" onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'">
</body>
</html>