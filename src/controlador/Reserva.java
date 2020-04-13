package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import datos.ConversorResultSetADefaultTableModel;
import modelo.Pasajero;

public class Reserva {
	
private Pasajero pasajero;
private GregorianCalendar ingreso,salida;
private int numeroHabitacion;


public Reserva(Pasajero persona, GregorianCalendar ingreso, GregorianCalendar salida, int habitacion) {
	this.pasajero = persona;
	this.ingreso = ingreso;
	this.salida = salida;
	this.numeroHabitacion = habitacion;
}

public Pasajero getPasajero() {
	return pasajero;
}

public void setPasajero(Pasajero persona) {
	this.pasajero = persona;
}

public GregorianCalendar getIngreso() {
	return ingreso;
}

public void setIngreso(GregorianCalendar ingreso) {
	this.ingreso = ingreso;
}

public GregorianCalendar getSalida() {
	return salida;
}

public void setSalida(GregorianCalendar salida) {
	this.salida = salida;
}

public int getNumeroHabitacion() {
	return numeroHabitacion;
}

public void setNumeroHabitacion(int numeroHabitacion) {
	this.numeroHabitacion = numeroHabitacion;
}

/**
 * guarda una nueva reserva en la base de dato
 */
public void nuevaReserva(Pasajero p) {
	if(pasajero==null)
		p.nuevoPasajero();
	else if(!pasajero.equals(p))
		pasajero.actualizaPasajero(p);
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		PreparedStatement pst=con.prepareStatement("insert into reserva values(null,?,?,?,?);");
		pst.setInt(1,this.pasajero.getNumeroDocumento());
		pst.setInt(2,this.numeroHabitacion);
		pst.setDate(3,new Date(this.ingreso.getTimeInMillis()));
		pst.setDate(4,new Date(this.salida.getTimeInMillis()));
		pst.executeUpdate();
		pst.close();
		con.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}
}

/**
 * genera un informe sobre las reservas 
 * si inicio es null el informe sera de todas las reservas que el pasajero abandona la habitacion en la fecha de salida especificada
 * si fin es null el informe sera de todas las reservas en las que el pasajero ingresa en la fecha especificada
 * si ambos campos son null el informe sera de todas las reservas existentes
 * @param inicio fecha de inicio de estadia
 * @param fin fecha de fin de estadia
 * @return devuelve un informe sobre las reservas en forma de tabla Formato(nombre, tipo habitacion, fecha ingreso, fecha salida)
 */
public JTable informeReserva(GregorianCalendar inicio, GregorianCalendar fin) {
	DefaultTableModel modelo=new DefaultTableModel();
	JTable tabla=null;
	int caso=0;
	String sql ="SELECT p.pasajero_nombre,ht.tipo_habitacion_nombre,r.reserva_fecha_entrada,r.reserva_fecha_salida "
			+ "FROM reserva r INNER JOIN pasajero p ON p.pasajero_dni=r.pasajero_dni "
			+ "INNER JOIN tipo_habitacion ht ON r.tipo_habitacion_id=ht.tipo_habitacion_Id";
	if(inicio!=null) {
		if(fin!=null) {
			sql=sql+" WHERE r.reserva_fecha_entrada=? AND r.reserva_fecha_salida=?;";
		}else {
			sql=sql+" WHERE r.reserva_fecha_entrada=?;";
			caso=1;
		}
	}else {
		if(fin!=null) {
			sql=sql+" WHERE r.reserva_fecha_salida=?;";
			caso=2;
		}else {
			caso=3;
		}
	}
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		PreparedStatement pst=con.prepareStatement(sql);
		switch(caso) {
		case 0:
			pst.setDate(1, new Date(inicio.getTimeInMillis()));
			pst.setDate(2, new Date(fin.getTimeInMillis()));
			break;
		case 1: 
			pst.setDate(1, new Date(inicio.getTimeInMillis()));
			break;
		case 2:
			pst.setDate(1, new Date(fin.getTimeInMillis()));
		}
		ResultSet rs=pst.executeQuery();
		ConversorResultSetADefaultTableModel.rellena(rs, modelo);
		tabla=new JTable(modelo);
		rs.close();
		pst.close();
		con.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return tabla;
}

public int[] getNumeroReserva() {
	int[] resultado;
	try {
		String sql="SELECT reserva_numero FROM reseva WHERE pasajero_dni=? AND reserva_fecha_entrada=? AND reserva_fecha_salida=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setInt(1, pasajero.getNumeroDocumento());
		pst.setDate(2, new Date(ingreso.getTimeInMillis()));
		pst.setDate(3, new Date(salida.getTimeInMillis()));
		ResultSet rs=pst.executeQuery();
		if(rs.last()) {
			resultado=new int[rs.getRow()]; 
			rs.first();
			resultado[0]=rs.getInt(1);
			for(int i=1;rs.next();i++) {
				resultado[i]=rs.getInt(1);
			}
			return resultado;
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	resultado=new int[1];
	resultado[0]=0;
	return resultado;
}

public void actualizarReserva(GregorianCalendar antiguoIngreso) {
	try {
		String sql="UPDATE `reserva` SET habitacion_numero=?,reserva_fecha_entrada=?,reserva_fecha_salida=? WHERE pasajero_dni=? AND reserva_fecha_entrada=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setInt(1, numeroHabitacion);
		pst.setDate(2, new Date(ingreso.getTimeInMillis()));
		pst.setDate(3, new Date(salida.getTimeInMillis()));
		pst.setInt(4, pasajero.getNumeroDocumento());
		pst.setDate(5, new Date(antiguoIngreso.getTimeInMillis()));
		pst.executeUpdate();
		pst.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public static void cancelarReserva(int documento, GregorianCalendar ingreso) {
	String sql="DELETE FROM reserva WHERE pasajero_dni=? and reserva_fecha_entrada=?";
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setInt(1,documento);
		pst.setDate(2,new Date(ingreso.getTimeInMillis()));
		pst.executeUpdate();
		pst.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

}
