package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import modelo.Pasajero;

public class CuentaCorriente {
private Pasajero pasajero;
public static final byte PAGAR=1;
public static final byte SALDAR=-1;

public CuentaCorriente(Pasajero pasajero) {
	this.pasajero=pasajero;
}

public Pasajero getPasajero() {
	return pasajero;
}

public void setPasajero(Pasajero pasajero) {
	this.pasajero = pasajero;
}

public void asignarCuentaCorriente() {
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement ps=con.prepareStatement("INSERT INTO cuenta_corriente VALUES(NULL,0,1,?);"); 
		ps.setInt(1, pasajero.getNumeroDocumento());
		ps.executeUpdate();
		ps.close();
		con.close();
	}catch (SQLException e) {
		e.printStackTrace();
	}
}

public void eliminarAutorizacion() {
	try {
		String sql="UPDATE cuenta_corriente SET cuenta_corriente_estado=0 WHERE pasajero_documento=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, pasajero.getNumeroDocumento());
		ps.executeUpdate();
		ps.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
}

public void modificarSaldo(int monto, byte tipoOperacion) {
	try {
		int saldo;
		String sql="SELECT cuenta_corriente_saldo FROM cuenta_corriente WHERE pasajero_documento=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, pasajero.getNumeroDocumento());
		ResultSet rs=ps.executeQuery();
		rs.next();
		saldo=rs.getInt(1);
		rs.close();
		ps.close();
		saldo+=(monto*tipoOperacion);
		sql="UPDATE cuenta_corriente SET cuenta_corriente_saldo=? WHERE pasajero_documento=?";
		ps=con.prepareStatement(sql);
		ps.setInt(1, saldo);
		ps.setInt(2, pasajero.getNumeroDocumento());
		ps.executeUpdate();
		ps.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public boolean consultaAutorizacion() {
	try{
		String sql="SELECT cuenta_corriente_estado FROM cuenta_corriente WHERE pasajero_documento=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, pasajero.getNumeroDocumento());
		ResultSet rs=ps.executeQuery();
		if(!rs.next())
			return false;
		else if(rs.getInt(1)==1)
			return true;
		else
			return false;
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
}

public void habilitar() {
	try {
		String sql="UPDATE cuenta_corriente SET cuenta_corriente_estado=1 WHERE pasajero_documento=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, pasajero.getNumeroDocumento());
		ps.executeUpdate();
		ps.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}

public int consultaSaldo() {
	try{
		String sql="SELECT cuenta_corriente_saldo FROM cuenta_corriente WHERE pasajero_documento=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement ps=con.prepareStatement(sql);
		ps.setInt(1, pasajero.getNumeroDocumento());
		ResultSet rs=ps.executeQuery();
		rs.next();
		return rs.getInt(1);
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return 0;
}
}
