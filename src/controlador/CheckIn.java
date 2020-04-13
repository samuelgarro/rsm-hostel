package controlador;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import datos.ConversorResultSetADefaultTableModel;
import modelo.Pasajero;

public class CheckIn {
private Pasajero pasajero;
private GregorianCalendar ingreso, egreso;
private int numeroHab;

/**
 * crea un nuevo check in
 * @param pasajero es el pasajero que se hospedará
 * @param egreso es la fecha de egreso del pasajero del hotel
 * @param numeroHab es la habitación en la que se hospedará
 */
public CheckIn(Pasajero pasajero, GregorianCalendar egreso, int numeroHab) {
	super();
	this.pasajero = pasajero;
	this.ingreso = new GregorianCalendar();
	this.egreso = egreso;
	this.numeroHab = numeroHab;
}

public CheckIn(Pasajero pasajero, GregorianCalendar ingreso, GregorianCalendar egreso, int numeroHab) {
	this.pasajero = pasajero;
	this.ingreso = ingreso;
	this.egreso = egreso;
	this.numeroHab = numeroHab;
}

public Pasajero getPasajero() {
	return pasajero;
}
public void setPasajero(Pasajero pasajero) {
	this.pasajero = pasajero;
}
public GregorianCalendar getIngreso() {
	return ingreso;
}
public void setIngreso(GregorianCalendar ingreso) {
	this.ingreso = ingreso;
}
public GregorianCalendar getEgreso() {
	return egreso;
}
public void setEgreso(GregorianCalendar egreso) {
	this.egreso = egreso;
}
public int getNumeroHab() {
	return numeroHab;
}
public void setNumeroHab(int numeroHab) {
	this.numeroHab = numeroHab;
}

/**
 * guarda una nueva ocupacion en la base de dato
 * @param p 
 */
public void reguistrarEstadia(Pasajero p) {
	if(pasajero==null)
		p.nuevoPasajero();
	else if(!this.pasajero.equals(p))
		pasajero.actualizaPasajero(p);
	
	try {
		Timestamp ingreso=new Timestamp(this.getIngreso().getTimeInMillis());
		Date egreso= new Date(this.getEgreso().getTimeInMillis());
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
		
		PreparedStatement pst=con.prepareStatement("INSERT INTO check_in VALUES(?,?,?,?);");
		pst.setInt(1,this.getPasajero().getNumeroDocumento());
		pst.setInt(2,this.getNumeroHab());
		pst.setTimestamp(3,ingreso);
		pst.setDate(4,egreso);
		pst.executeUpdate();
		pst.close();
		con.close();
		
		ManejoHabitacion hab=ManejoHabitacion.getInstancia();
		hab.cambiarEstadoHab("ocupado", numeroHab);
	}catch(SQLException e) {
		e.printStackTrace();
	}
}
/**
 * genera un informe sobre la ocupacion
 * si inicio es null el informe sera de todas las ocupaciones donde el pasajero abandono la habitacion en el dia especificado
 * si fin es null el informe sera de todas las ocupaciones donde el pasajero ingreso a la habitacion en el dia especificado
 * si inicio y fin son null el informe sera el historial de todas las ocupaciones que hubo 
 * @param inicio fecha de inicio
 * @param fin fecha de finalizacion
 * @param estado estado en el que se encontraba la habitacion 1 libre o 2 ocupada
 * @return informe de ocupacion en forma de tabla. Formato (n habitacion, nombre_pasajero, fecha inicio, fecha final)
 */
public JTable informeOcupacion(GregorianCalendar inicio, GregorianCalendar fin, int estado) {
	DefaultTableModel modelo=new DefaultTableModel();
	JTable tabla=null;
	String sql = null;
	int caso=0;
	switch(estado) {
	case 1:
		sql="SELECT h.habitacion_numero,o.ocupacion_fecha_entrada,o.ocupacion_fecha_salida "
				+ "FROM ocupacion o, habitacion h "
				+ "WHERE h.habitacion_numero NOT IN( "
				+ "SELECT habitacion_numero from ocupacion ";
		if(inicio!=null) {
			if(fin!=null) {
				sql=sql+"WHERE ocupacion_fecha_entrada=? AND ocupacion_fecha_salida=?);";
			}else {
				sql=sql+"WHERE ocupacion_fecha_entrada=?);";
				caso=1;
			}
		}else {
			if(fin!=null) {
				sql=sql+" WHERE ocupacion_fecha_salida=?);";
				caso=2;
			}else {
				sql=sql+");";
				caso=3;
			}
		}
		break;
	case 2:
		sql="SELECT o.habitacion_numero,p.pasajero_nombre,o.ocupacion_fecha_entrada,o.ocupacion_fecha_salida "
				+ "from ocupacion o INNER JOIN pasajero p on o.pasajero_dni=p.pasajero_dni";
		if(inicio!=null) {
			if(fin!=null) {
				sql=sql+" where ocupacion_fecha_entrada=? and ocupacion_fecha_salida=?";
			}else {
				sql=sql+" where ocupacion_fecha_entrada=?";
				caso=1;
			}
		}else {
			if(fin!=null) {
				sql=sql+" where ocupacion_fecha_salida=?";
				caso=2;
			}else {
				caso=3;
			}
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
                            break;
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

public static Pasajero consultaPasajero(int numeroDocumento) {
	return Pasajero.devuelvePasajero(numeroDocumento);
}
}
