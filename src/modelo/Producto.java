package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;

public class Producto {
private String marca;
private String descripcion;
private float cantidad;
private byte unidad;
private int id;
/**
 * la cantidad del producto son medidas por unidades
 */
public final static byte UNIDADES=0;
/**
 * cantidad del producto medida en kilogramas
 */
public final static byte KG=1;
/**
 * cantidad del producto medida en litros
 */
public final static byte L=2;

/**
 * devuelve la marca del producto
 * @return marca del producto
 */
public String getMarca() {
	return marca;
}
/**
 * agrega la marca del producto
 * @param marca marca del producto
 */
public void setMarca(String marca) {
	this.marca = marca;
}
/**
 * devuelve la descripcion del producto
 * @return descripcion del producto
 */
public String getDescripcion() {
	return descripcion;
}
/**
 * agrega la descripcion del producto
 * @param descripcion del producto
 */
public void setDescripcion(String descripcion) {
	this.descripcion = descripcion;
}
/**
 * devuelve la cantidad disponible del producto
 * @return cantidad del producto
 */
public float getCantidad() {
	return cantidad;
}
/**
 * cambia la cantidad del producto
 * @param cantidad del producto
 */
public void setCantidad(float cantidad) {
	this.cantidad += cantidad;
}
/**
 * devuelve la unidad de medida del producto
 * @return unidad de medida del producto
 */
public byte getUnidad() {
	return unidad;
}
/**
 * configurta la unidad de medida
 * @param unidad de medida del producto
 */
public void setUnidad(byte unidad) {
	this.unidad = unidad;
}
/**
 * crea un nuevo producto
 * @param marca del producto
 * @param descripcion del producto
 * @param cantidad del producto
 * @param unidad de medida del producto
 */
public Producto(String marca, String descripcion, float cantidad, byte unidad) {
	this.marca = marca;
	this.descripcion = descripcion;
	this.cantidad = cantidad;
	this.unidad = unidad;
	String sql="SELECT producto_id " + 
			"FROM producto " + 
			"WHERE producto_cantidad=? AND producto_unidad=? "
			+ "AND producto_marca=? AND producto_desc=?";
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setFloat(1, this.cantidad);
		pst.setByte(2, this.unidad);
		pst.setString(3, this.marca);
		pst.setString(4, this.descripcion);
		ResultSet rs=pst.executeQuery();
		if(rs.next())
			this.id=rs.getInt(1);
		else
			this.id=0;
		
		pst.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
/**
 * crea un nuevo producto nulo
 */
public Producto() {
	this.marca = "";
	this.descripcion = "";
	this.cantidad = 0;
	this.unidad = 0;
	this.id=0;
}
/**
 * actualiza los datos del producto
 */
public void update() {
	if(this.id!=0) {
		String sql="UPDATE producto SET "
				+ "producto_marca=?,"
				+ "producto_desc=?,"
				+ "producto_cantidad=?, "
				+ "producto_unidad=? "
				+ "WHERE producto_id=? ";
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
			PreparedStatement pst=con.prepareStatement(sql);
			pst.setString(1, this.marca);
			pst.setString(2, this.descripcion);
			pst.setFloat(3, this.cantidad);
			pst.setByte(4, this.unidad);
			pst.setInt(5, this.id);
			pst.executeUpdate();
			pst.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
/**
 * guarda un nuevo producto en la base de datos
 */
public void insert() {
	String sql="INSERT INTO producto VALUES(NULL,?,?,?,?)";
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setString(1, this.marca);
		pst.setString(2, this.descripcion);
		pst.setFloat(3, this.cantidad);
		pst.setByte(4, this.unidad);
		pst.executeUpdate();
		pst.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
public static Producto devuelveProducto(int codigo) {
	String sql="SELECT * FROM  producto WHERE producto_id=?";
	Producto producto=null;
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setInt(1, codigo);
		ResultSet rs=pst.executeQuery();
		if(rs.next()) {
			producto=new Producto();
			producto.id=codigo;
			producto.marca=rs.getString(2);
			producto.descripcion=rs.getString(3);
			producto.cantidad=rs.getFloat(4);
			producto.unidad=rs.getByte(5);
		}
		pst.close();
		con.close();
		return producto;
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return producto;
}
public static void delete(int codigo) {
	String sql="DELETE FROM producto WHERE producto_id=?";
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setInt(1, codigo);
		pst.executeUpdate();
		pst.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
public static void llenarCombo(JComboBox<String> modificarCodigo) {
	String sql="SELECT * FROM  producto";
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement pst=con.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		modificarCodigo.addItem("");
		while(rs.next()) {
			modificarCodigo.addItem(rs.getInt(1) + " - " +rs.getString(2) + "  " +rs.getString(3));
		}
		pst.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}
