package com.pildorasinformaticas.productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;

public class ModeloProductos {

	private DataSource origenDatos;
	
	public ModeloProductos(DataSource origenDatos) {
		this.origenDatos=origenDatos;
		
		
	}
	public List<Productos> getProductos() throws Exception{
		List<Productos> productos=new ArrayList<>();
		Connection miConexion=null;
		Statement miStatement=null;
		ResultSet miResultset=null;
		
		//-------Establecer la conexion--------------
		miConexion=origenDatos.getConnection();
		
		//-------Crear sentencia sql-----------------
		String instruccionSql="SELECT * FROM PRODUCTOS";
		
		miStatement=miConexion.createStatement();
		
		//----------ejecutar sql---------------
		
		miResultset=miStatement.executeQuery(instruccionSql);
		//------------recorrer el resultset obtenido-----------------
		while(miResultset.next()) {
			String c_art=miResultset.getString("CÓDIGOARTÍCULO");
			String seccion=miResultset.getString("SECCIÓN");
			String n_art=miResultset.getString("NOMBREARTÍCULO");
			double precio=miResultset.getDouble("PRECIO");
			Date fecha=miResultset.getDate("FECHA");
			String importado=miResultset.getString("IMPORTADO");
			String p_orig=miResultset.getString("PAÍSORIGEN");
			
			Productos temProd=new Productos(c_art,seccion,n_art,precio,fecha,importado,p_orig);
			productos.add(temProd);
		}
		return productos;
		
		
	}
	public void agregarElNuevoProducto(Productos nuevoProducto) {
		// TODO Auto-generated method stub
		
		Connection miConexion=null;
		
		PreparedStatement miStatement=null;
		
		//Obtener la conexión 
		
		try {
			
			miConexion=origenDatos.getConnection();
			
		
		
		//Crear instrucción sql que inserte el producto
		
		String sql="INSERT INTO PRODUCTOS (CÓDIGOARTÍCULO, SECCIÓN, NOMBREARTÍCULO, PRECIO, FECHA, IMPORTADO, PAÍSDEORIGEN)"+
		"VALUES(?,?,?,?,?,?,?)";
		
		miStatement=miConexion.prepareStatement(sql);
		
		//Establecer parametros para el producto
		
		miStatement.setString(1, nuevoProducto.getcArt());
		miStatement.setString(2, nuevoProducto.getSeccion());
		miStatement.setString(3, nuevoProducto.getnArt());
		miStatement.setDouble(4, nuevoProducto.getPrecio());
		java.util.Date utilDate=nuevoProducto.getFecha();
		java.sql.Date fechaConvertida= new java.sql.Date(utilDate.getTime());		
		miStatement.setDate(5, nuevoProducto.getFecha());
		miStatement.setString(6, nuevoProducto.getImportado());
		miStatement.setString(7, nuevoProducto.getpOrig());
				
		//Ejecutar la instrucción SQL
		miStatement.execute();
		}catch(Exception e) {
			
			
		}
		
	}
}
