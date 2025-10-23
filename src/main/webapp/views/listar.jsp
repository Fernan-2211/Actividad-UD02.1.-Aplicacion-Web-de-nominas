<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Listar Empleados</title>
<style>
  table {
    border-collapse: collapse;
    width: 70%;
    margin: 20px auto;
    font-family: Arial, sans-serif;
  }
  th, td {
    border: 1px solid #999;
    padding: 8px 12px;
    text-align: center;
  }
  th {
    background-color: #eee;
  }
  h1 {
    text-align: center;
    font-family: Arial, sans-serif;
  }
  a {
    text-decoration: none;
    color: blue;
  }
</style>
</head>
<body>
  <h1>Listado de Empleados</h1>

  <table>
    <tr>
      <th>DNI</th>
      <th>Nombre</th>
      <th>Sexo</th>
      <th>Categoría</th>
      <th>Años</th>
      <th>Acción</th>
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
        <td>
          <a href="empleados?opcion=eliminar&dni=${empleado.dni}">
            Eliminar
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>

  <div style="text-align:center; margin-top:20px;">
    <a href="empleados?opcion=nuevo">Agregar nuevo empleado</a>
  </div>
</body>
</html>
