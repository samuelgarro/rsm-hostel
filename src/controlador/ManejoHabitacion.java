package controlador;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;

import modelo.Habitacion;

public class ManejoHabitacion {
private ArrayList<Habitacion> habitaciones;
private static ManejoHabitacion instancia=new ManejoHabitacion();

private ManejoHabitacion() {
	habitaciones=new ArrayList<Habitacion>();
	try {
		String sql="SELECT h.habitacion_numero, th.tipo_habitacion_nombre, h.habitacion_precio " + 
				"FROM habitacion h INNER JOIN tipo_habitacion th " + 
				"ON th.tipo_habitacion_Id=h.tipo_habitacion_id";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		PreparedStatement pst=con.prepareStatement(sql);
		ResultSet rs=pst.executeQuery();
		while(rs.next()) {
			habitaciones.add(new Habitacion(rs.getInt(1),rs.getString(2),"libre",rs.getInt(3)));
		}
		rs.close();
		pst.close();
		
		sql="SELECT habitacion_numero FROM check_in";
		pst=con.prepareStatement(sql);
		rs=pst.executeQuery();
		while(rs.next()) {
			cambiarEstadoHab("ocupado",rs.getInt(1));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public static ManejoHabitacion getInstancia() {
	return instancia;
}

public void cambiarEstadoHab(String estado, int numeroHabitacion) {
	getHabitacion(numeroHabitacion).setEstado(estado);
}

public void cambiarPrecio(int precio,String tipo) {
	for(int i=0;i<habitaciones.size();i++) 
		if(habitaciones.get(i).getTipo().equals(tipo))
			habitaciones.get(i).setPrecio(precio);
}

public void cambiarPrecio(int precio, int numero) {
	getHabitacion(numero).setPrecio(precio);
}

private Habitacion getHabitacion(int numero) {
	for(int i=0;i<habitaciones.size();i++) 
		if(habitaciones.get(i).getNumero()==numero) 
			return habitaciones.get(i);
	
	return null;
}

public int getPrecio(int numero) {
	return getHabitacion(numero).getPrecio();
}

public String getEstado(int numero) {
	return getHabitacion(numero).getEstado();
}

public String getTipo(int numero) {
	return getHabitacion(numero).getTipo();
}

public void llenarComboHabLib(JComboBox<String> combo) {
	combo.removeAllItems();
	for(int i=0;i<habitaciones.size();i++) 
		if(habitaciones.get(i).getEstado().equalsIgnoreCase("libre"))
			combo.addItem(""+habitaciones.get(i).getNumero());
}

public void llenarComboHabLib(JComboBox<String> combo, GregorianCalendar ingreso, GregorianCalendar egreso, String tipo) {
	Date in=new Date(ingreso.getTimeInMillis());
	Date out=new Date(egreso.getTimeInMillis());
	try {
		ManejoHabitacion habitaciones=ManejoHabitacion.getInstancia();
		String sql="SELECT habitacion_numero FROM habitacion WHERE habitacion_numero NOT IN (SELECT habitacion_numero "
				+ "FROM reserva WHERE reserva_fecha_entrada<? AND (reserva_fecha_salida>? OR reserva_fecha_salida>? )"
				+ " OR reserva_fecha_entrada>? AND reserva_fecha_salida<? OR reserva_fecha_entrada<? AND reserva_fecha_salida>? "
				+ "OR reserva_fecha_entrada=?);";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root","");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setDate(1, in);
		pst.setDate(2, in);
		pst.setDate(3, out);
		pst.setDate(4, in);
		pst.setDate(5, out);
		pst.setDate(6, out);
		pst.setDate(7, out);
		pst.setDate(8, in);
		ResultSet rs=pst.executeQuery();
		while(rs.next()) {
			if(habitaciones.getTipo(rs.getInt(1)).equals(tipo))
				combo.addItem(""+rs.getInt(1));
		}
		rs.close();
		pst.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

}
