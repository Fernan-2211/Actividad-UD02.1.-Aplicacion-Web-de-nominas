<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Error</h1>
    <p class="error">${error}</p> <!-- CAMBIO: Muestra el error -->
    <input type="button" value="Volver" onclick="history.back();"> <!-- CAMBIO: Botón volver -->
</body>
</html>