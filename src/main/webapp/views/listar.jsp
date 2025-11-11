<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Empleados</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
  <h1>Listado de Empleados</h1>

  <c:if test="${empty lista}"> <!-- CAMBIO: Mensaje si vacío -->
    <p class="error">No hay empleados en la base de datos.</p>
  </c:if>

  <table>
    <tr>
      <th>DNI</th>
      <th>Nombre</th>
      <th>Sexo</th>
      <th>Categoría</th>
      <th>Años</th>
    </tr>

    <c:forEach var="empleado" items="${lista}">
      <tr>
        <td>
          <a href="empleados?opcion=editar&dni=${empleado.dni}">
            <c:out value="${empleado.dni}" />
          </a>
        </td>
        <td><c:out value="${empleado.nombre}" /></td>
        <td><c:out value="${empleado.sexo}" /></td>
        <td><c:out value="${empleado.categoria}" /></td>
        <td><c:out value="${empleado.anyos}" /></td>
      </tr>
    </c:forEach>
  </table>
	<div class="volver-container">
		<input type="button" value="Volver"
			onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'">
	</div>
</body>
</html>