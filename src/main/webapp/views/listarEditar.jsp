<!-- src/main/webapp/views/listarEditar.jsp -->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Resultados de Búsqueda</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
	<h1>Resultados de Búsqueda</h1>
	<c:choose>
		<c:when test="${not empty lista}">
			<table>
				<tr>
					<th>DNI</th>
					<th>Nombre</th>
					<th>Sexo</th>
					<th>Categoría</th>
					<th>Años Trabajados</th>
					<th>Acción</th>
				</tr>
				<c:forEach var="emp" items="${lista}">
					<tr>
						<td>${emp.dni}</td>
						<td>${emp.nombre}</td>
						<td>${emp.sexo}</td>
						<td>${emp.categoria}</td>
						<td>${emp.anyos}</td>
						<td><a
							href="${pageContext.request.contextPath}/empleados?opcion=editar&dni=${emp.dni}">Editar</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<p class="error">No se encontraron empleados con ese criterio.</p>
		</c:otherwise>
	</c:choose>
	<br>
	<div class="volver-container">
		<input type="button" value="Volver"
			 onclick="window.location.href='${pageContext.request.contextPath}/empleados?opcion=buscarEditar'">
	</div>

</body>
</html>