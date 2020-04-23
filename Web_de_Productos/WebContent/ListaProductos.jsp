<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%@ page import="java.util.*, com.pildorasinformaticas.productos.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<style>

table{

	float:left;

}

#contenedorBoton{

	margin-left:1000px;
}
</style>
</head>

<%

//Obtiene los productos del controlador(Servlet)

List<Productos> losProductos=(List<Productos>) request.getAttribute("LISTAPRODUCTOS");




%>
<body>

<table>
<tr>
<td class="cabecera">Código Artículo</td>
<td class="cabecera">Sección</td>
<td class="cabecera">Nombre Artículo</td>
<td class="cabecera">Fecha</td>
<td class="cabecera">Precio</td>
<td class="cabecera">Importado</td>
<td class="cabecera">País de Origen</td>
<td class="cabecera">Acción</td>
</tr>

<% for(Productos tempProd : losProductos){ %>

<!--  Link para cada prodcuto con su campo clave -->>

<c:url var="linkTemp" value="ControladorProductos">

	<c:param name="instruccion" value="cargar"></c:param>
	<c:param name="CArticulo" value="${tempProd.cArt}"></c:param>
	
	<C/:url>
	
	<!--  Link para eliminar cada registro con su campo clave -->
	
	<c:url var="linkTemp" value="ControladorProductos">

	<c:param name="instruccion" value="eliminar"></c:param>
	<c:param name="CArticulo" value="${tempProd.cArt}"></c:param>
	
	<C/:url>
	
<tr>

<td class="filas"><%=tempProd.getcArt() %></td>
<td class="filas"><%=tempProd.getSeccion() %></td>
<td class="filas"><%=tempProd.getnArt() %></td>
<td class="filas"><%=tempProd.getFecha() %></td>
<td class="filas"><%=tempProd.getPrecio() %></td>
<td class="filas"><%=tempProd.getImportado() %></td>
<td class="filas"><%=tempProd.getpOrig() %></td>
<td class="filas "><a href="${linkTemp} }">Actualizar</a>&nbsp;&nbsp;<a href="${linkTempEliminar}">Eliminar</a></td>

</tr>

<%} %>
</table>

<div id="contenedorBoton">

	<input type="button" value="Insertar Registro" onclick="window.location.href='inserta_producto.jsp' "/>
	

</div>

</body>
</html>