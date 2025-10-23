package com.empresa.controllador;
 
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.empresa.dao.empresaDAO;
import com.empresa.empleado.Empleado;
 
/**
 * Servlet implementation class ProductoController
 */
@WebServlet(description = "administra peticiones para la tabla productos", urlPatterns = { "/empleados", "/nominas" })
public class Controlador extends HttpServlet {
 private static final long serialVersionUID = 1L;
 
 /**
  * @see HttpServlet#HttpServlet()
  */
 public Controlador() {
  super();
  // TODO Auto-generated constructor stub
 }
 
 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
  *      response)
  */
 protected void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
  // TODO Auto-generated method stub
 
  String opcion = request.getParameter("opcion");
 
  if (opcion.equals("crear")) {
   System.out.println("Usted a presionado la opcion crear");
   RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/crear.jsp");
   requestDispatcher.forward(request, response);
  } else if (opcion.equals("listar")) {
 
   empresaDAO empleadoDAO = new empresaDAO();
   List<Empleado> lista = new ArrayList<>();
   try {
    lista = empleadoDAO.obtenerEmpleados();
    for (Empleado empleado : lista) {
     System.out.println(empleado);
    }
 
    request.setAttribute("lista", lista);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/listar.jsp");
    requestDispatcher.forward(request, response);
 
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
 
   System.out.println("Usted a presionado la opcion listar");
  } else if (opcion.equals("editar")) {
   String dni = request.getParameter("dni");
   System.out.println("Editar dni: " + dni);
   empresaDAO empleadoDAO = new empresaDAO();
   Empleado emp = new Empleado();
   try {
    emp = empleadoDAO.obtenerEmpleado(dni);
    System.out.println(emp);
    request.setAttribute("empleados", emp);
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/views/editar.jsp");
    requestDispatcher.forward(request, response);
 
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
 
  } else if (opcion.equals("eliminar")) {
   empresaDAO empresaDAO = new empresaDAO();
   String dni = request.getParameter("dni");
   try {
    empresaDAO.eliminar(dni);
    System.out.println("Registro eliminado satisfactoriamente...");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
    requestDispatcher.forward(request, response);
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
 
  }
  // response.getWriter().append("Served at: ").append(request.getContextPath());
 }
 
 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
  *      response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
  // TODO Auto-generated method stub
  String opcion = request.getParameter("opcion");
 
  if (opcion.equals("guardar")) {
   empresaDAO empresaDAO = new empresaDAO();
   Empleado empleado = new Empleado();
   
   empleado.setDni(request.getParameter("dni"));
   empleado.setNombre(request.getParameter("nombre"));
   empleado.setSexo(request.getParameter("sexo").charAt(0));
   empleado.setCategoria(Integer.parseInt(request.getParameter("categoria")));
   empleado.setAnyos(Integer.parseInt(request.getParameter("anyos")));
 
   
   try {
    empresaDAO.guardar(empleado);
    System.out.println("Registro guardado satisfactoriamente...");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
    requestDispatcher.forward(request, response);
 
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
  } else if (opcion.equals("editar")) {
   Empleado empleado = new Empleado();
   empresaDAO empresaDAO = new empresaDAO();
   
   empleado.setDni(request.getParameter("dni"));
   empleado.setNombre(request.getParameter("nombre"));
   empleado.setSexo(request.getParameter("sexo").charAt(0));
   empleado.setCategoria(Integer.parseInt(request.getParameter("categoria")));
   empleado.setAnyos(Integer.parseInt(request.getParameter("anyos")));
   try {
    empresaDAO.editar(empleado);
    System.out.println("Registro editado satisfactoriamente...");
    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
    requestDispatcher.forward(request, response);
   } catch (SQLException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }
  }
 
  // doGet(request, response);
 }
 
}