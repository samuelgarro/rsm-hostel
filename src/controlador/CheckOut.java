package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JTable;

import modelo.Pasajero;

public class CheckOut {
	
private CheckIn[] in;

public CheckOut(int documento) {
	Pasajero pasajero=Pasajero.devuelvePasajero(documento);
	if(pasajero!=null) {
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
			PreparedStatement pst=con.prepareStatement("SELECT habitacion_numero, check_in_ingreso FROM check_in WHERE pasajero_documento=?");
			pst.setInt(1,pasajero.getNumeroDocumento());
			ResultSet rs=pst.executeQuery();
			rs.last();
			in=new CheckIn[rs.getRow()];
			rs.first();
			GregorianCalendar ingreso=new GregorianCalendar();
			ingreso.setTimeInMillis(rs.getDate(2).getTime());
			in[0]=new CheckIn(pasajero,ingreso,new GregorianCalendar(),rs.getInt(1));
			for(int i=0;rs.next();i++) {
				ingreso=new GregorianCalendar();
				ingreso.setTimeInMillis(rs.getTimestamp(2).getTime());
				in[i]=new CheckIn(pasajero,ingreso,new GregorianCalendar(),rs.getInt(1));
			}
			rs.close();
			pst.close();
			con.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

/**
 * cuando se realiza el check out se actualiza la fecha de salida de la ocupación
 */
public void registrarSalida() {
	 try{
		    String sql="INSERT INTO ocupacion VALUES(?,?,?,?,?);";
		    ManejoHabitacion hab = ManejoHabitacion.getInstancia();
		    int cantidadDias;
		    Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		    PreparedStatement pst;
		    for(int i=0;i<in.length;i++) {
		    	double redondear=(double)(in[i].getEgreso().getTimeInMillis()-in[i].getIngreso().getTimeInMillis())/(double)86400000;
		    	cantidadDias=(int)Math.ceil(redondear);
			    pst=con.prepareStatement(sql);
			    pst.setInt(1,in[i].getPasajero().getNumeroDocumento());
			    pst.setInt(2,in[i].getNumeroHab());
			    pst.setTimestamp(3,new Timestamp(in[i].getIngreso().getTimeInMillis()));
			    pst.setTimestamp(4,new Timestamp(in[i].getEgreso().getTimeInMillis()));
			    pst.setInt(5,hab.getPrecio(in[i].getNumeroHab())*cantidadDias);
			    pst.executeUpdate();
			    pst.close();
		    }
		    sql="DELETE FROM check_in WHERE pasajero_documento=?";
		    pst=con.prepareStatement(sql);
		    pst.setInt(1, in[0].getPasajero().getNumeroDocumento());
		    pst.executeUpdate();
		    pst.close();
		    con.close();
		    cambiarEstadoHab();
		 }catch(SQLException e){
		    e.printStackTrace();
		 } 
}




public int calcularCosto() {
	int costo=0;
	ManejoHabitacion habitaciones=ManejoHabitacion.getInstancia();
	for(int i=0;i<in.length;i++) {
		costo+=habitaciones.getPrecio(in[i].getNumeroHab());
	}
	return costo;
}

public void cambiarEstadoHab() {
	ManejoHabitacion habitaciones=ManejoHabitacion.getInstancia();
	for(int i=0;i<in.length;i++) {
		habitaciones.cambiarEstadoHab("libre",in[i].getNumeroHab());
	}
}

public JTable informacionEstadia() {
	String[] etiquetas= {"Fecha de ingresos", "habitacion", "tipo de habitacion", "dias hospedado","precio/dia", "total"};
	String[][] datos=new String[in.length+1][etiquetas.length];
	datos[0]=etiquetas;
	for(int i=1;i-1<in.length;i++) {
		GregorianCalendar ingreso=in[i-1].getIngreso();
		double redondear=(double)(in[i-1].getEgreso().getTimeInMillis()-ingreso.getTimeInMillis())/(double)86400000;
		int cantidadDias=(int)Math.ceil(redondear);
		int precio=ManejoHabitacion.getInstancia().getPrecio(in[i-1].getNumeroHab());
		datos[i][0]=ingreso.get(Calendar.DAY_OF_MONTH)+"/"+ingreso.get(Calendar.MONTH)+"/"+ingreso.get(Calendar.YEAR);
		datos[i][1]=""+in[i-1].getNumeroHab();
		datos[i][2]=ManejoHabitacion.getInstancia().getTipo(in[i-1].getNumeroHab());
		datos[i][3]=""+cantidadDias;
		datos[i][4]=""+precio;
		datos[i][5]=""+(precio*cantidadDias);		
	}
	JTable tabla=new JTable(datos,etiquetas);	
	return tabla;
}

public Pasajero getPasajero() {
	return in[0].getPasajero();
}

public int[] getHabitacionNumero() {
	int[] retorno=new int[in.length];
	for(int i=0;i<in.length;i++) {
		retorno[i]=in[i].getNumeroHab();
	}
	return retorno;
}
}
