package com.pildorasinformaticas.productos;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class ControladorProductos
 */
@WebServlet("/ControladorProductos")
public class ControladorProductos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ModeloProductos modeloProductos;
	@Resource(name="jdbc/Productos")
	private DataSource miPool;
	
	
	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
		try {
		modeloProductos=new ModeloProductos(miPool);
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}



	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//Leer el parámetro del formulario
		
		String elComando=request.getParameter("instruccion");
		
		//Si no se envía el parámetro, listar productos
		
		if(elComando==null) elComando="listar";
		
		//Redirigir el flujo de ejecución al método adecuado
		
		switch(elComando) {
		
		case "listar":
			
			obtenerProductos(request, response);	
			
			break;
			
		case "insertarBBDD":
			
			agregarProductos(request,  response);
			
			break;
			
		default:
			
			obtenerProductos(request, response);
			
		}
		
	
	}



	private void agregarProductos(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		//Leer la información del producto que viene del formulario
		
		String CodArticulo=request.getParameter("CArt");
		String Seccion=request.getParameter("seccion");
		String NombreArticulo=request.getParameter("NArt");		
		SimpleDateFormat formatoFecha=new SimpleDateFormat("yyyy-MM-dd");	
		Date Fecha=null;		
		try {
			Fecha=formatoFecha.parse(request.getParameter("fecha"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double Precio=Double.parseDouble(request.getParameter("precio"));
		String Importado=request.getParameter("importado");
		String PaisOrigen=request.getParameter("POrig");
				
		//Crear un objeto de tipo Producto
		
		Productos NuevoProducto=new Productos(CodArticulo,CodArticulo,NombreArticulo,Precio,Fecha,Importado,PaisOrigen);
		
		//Enviar el objeto al modelo y despues insertar el objeto producto en la BBDD
		
		modeloProductos.agregarElNuevoProducto(NuevoProducto);
		
		//Volver a listar la tabla de Productos
		obtenerProductos(request, response);
		
	}



	private void obtenerProductos(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//----------Obtener la lista de productos desde el modelo--------
		
		List<Productos> productos;
		try {
			productos=modeloProductos.getProductos();
			
		//----------Agregar lista de productos al request----------
			request.setAttribute("LISTAPRODUCTOS", productos);
		//----------Enviar ese request a la pagina JSP
			RequestDispatcher miDispatcher=request.getRequestDispatcher("/ListaProductos.jsp");
			miDispatcher.forward(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
