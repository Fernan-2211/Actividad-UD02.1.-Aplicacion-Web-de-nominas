<!-- src/main/webapp/index.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Menú de Opciones - Empresa</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <h1>Menú de Opciones - Empresa</h1>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/empleados?opcion=listar">Listar Empleados</a></li>
            <li><a href="${pageContext.request.contextPath}/empleados?opcion=buscarSalario">Buscar Salario</a></li>
            <li><a href="${pageContext.request.contextPath}/empleados?opcion=buscarEditar">Buscar para Editar</a></li>
        </ul>
    </nav>
</body>
</html>