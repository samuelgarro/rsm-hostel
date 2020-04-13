package modelo;

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

public class Pasajero extends Persona {
	
private String profesion;
private String observaciones;

public Pasajero() {
	super();
	this.profesion="";
	this.observaciones="";
}

public Pasajero(String tipoDocumento, int numeroDocumento, String nombre, String apellido, long telefono,
		GregorianCalendar nacimiento, Direccion direccion, String estadoCivil, String profesion, String observaciones) {
	super(tipoDocumento, numeroDocumento, nombre, apellido, telefono, nacimiento, estadoCivil, direccion);
	this.profesion=profesion;
	this.observaciones=observaciones;
}

public String getProfesion() {
	return profesion;
}

public void setProfesion(String profesion) {
	this.profesion = profesion;
}

public String getObservaciones() {
	return observaciones;
}

public void setObservaciones(String observaciones) {
	this.observaciones = observaciones;
}

/**
 * busca en la base de datos el pasajero y lo devueve
 * @param dni campo por el cual buscar el pasajero
 * @return devuelve el pasajero con el dni especificado
 */
public static Pasajero devuelvePasajero(int dni) {
	Pasajero persona=null;
	String domicilio,ciudad,provincia,pais;//datos de la direccion
	String tipoDocumento,nombre,apellido,observaciones,profesion,estadoCivil;
	Date nacimiento;
	GregorianCalendar naci=new GregorianCalendar();
	long telefono;
	String sql="SELECT pj.pasajero_domicilio, c.ciudad_nombre, p.provincia_nombre, pa.pais_nombre " 
			+ "FROM ciudad c, provincia p, pais pa, pasajero pj "  
			+ "WHERE pj.ciudad_id=c.ciudad_id AND c.provincia_id=p.provincia_id AND "
			+ "p.pais_id=pa.pais_id AND pj.pasajero_documento=?";
	try {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		PreparedStatement st=con.prepareStatement(sql);
		st.setInt(1,dni);
		ResultSet rs=st.executeQuery();
		Direccion direccion = null;
		if(rs.next()) {
			domicilio=rs.getString(1);
			ciudad=rs.getString(2);
			provincia=rs.getString(3);
			pais=rs.getString(4);
			direccion=new Direccion(domicilio,ciudad,provincia,pais);
			rs.close();
			st.close();
		}
		sql="SELECT td.tipo_documento_desc, p.pasajero_nombre, p.pasajero_apellido, p.pasajero_observaciones, " +
				"p.pasajero_telefono, p.pasajero_nacimiento, p.pasajero_profesion, p.pasajero_estado_civil " + 
				"FROM pasajero p " + 
				"INNER JOIN tipo_documento td " + 
				"ON p.tipo_documento_id=td.tipo_documento_id " + 
				"WHERE p.pasajero_documento=?";
		st=con.prepareStatement(sql);
		st.setInt(1,dni);
		rs=st.executeQuery();
		if(rs.next()) {
			tipoDocumento=rs.getString(1);
			nombre=rs.getString(2);
			apellido=rs.getString(3);
			observaciones=rs.getString(4);
			telefono=rs.getLong(5);
			nacimiento=rs.getDate(6);
			naci.setTimeInMillis(nacimiento.getTime());
			profesion=rs.getString(7);
			estadoCivil=rs.getString(8);
			persona=new Pasajero(tipoDocumento, dni, nombre, apellido, telefono,naci,direccion,estadoCivil,profesion,observaciones);
		}
		rs.close();
		st.close();
		con.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return persona;
}

/**
 * guarda el pasajero en la base de dato
 * @param p pasajero a guardar
 */
public void nuevoPasajero() {
	int ciudad_id=0;
	int documentoTipo=0;
	try{
		ciudad_id=this.getDireccion().getCiudadId();
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		String sql="select tipo_documento_id from tipo_documento where tipo_documento_desc=?;";
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setString(1,this.getTipoDocumento());
		ResultSet rs=pst.executeQuery();
		rs.next();
		documentoTipo=rs.getInt(1);
		rs.close();
		pst.close();
		
		sql="insert into pasajero values (?,?,?,?,?,?,?,?,?,?,?);";
		pst=con.prepareStatement(sql);
		pst.setInt(1,this.getNumeroDocumento());
		pst.setString(2,this.getNombre());
		pst.setString(3,this.getApellido());
		pst.setLong(4,this.getTelefono());
		pst.setDate(5,new Date(this.getNacimiento().getTimeInMillis()));
		pst.setString(6,this.getEstadoCivil());
		pst.setString(7,this.getProfesion());
		pst.setString(8,this.getDireccion().getDomicilio());
		pst.setInt(9, ciudad_id);
		pst.setString(10, this.getObservaciones());
		pst.setInt(11, documentoTipo);
		pst.executeUpdate();
		pst.close();
		con.close();
	}catch(SQLException e) {
		e.printStackTrace();
	}
}


/**
 * genera un reporte de pasajero
 * si dni es distinto de 0 se genera un informe sobre ese pasajero
 * sino se filtrara por cada uno de los parametros para generar el informe,
 * todos pueden ser null entonces se mostrara un informe de todos los pasajero existentes (ademas debe ser dni=0)
 * @param dni numero de documento del pasajero puede ser 0
 * @param nombre nombre del pasajero
 * @param apellido apellido del pasajero
 * @param provincia provincia del pasajero
 * @return devuelve un informe de pasajeros en forma de tabla
 */
 public JTable informePasajero(int dni, String nombre, String apellido, String provincia) {
	DefaultTableModel modelo=new DefaultTableModel();
	JTable tabla=null;
	int caso=0;
	String sql="SELECT p.pasajero_documento, p.pasajero_nombre, p.pasajero_apellido, pr.provincia_nombre "
			+ "FROM pasajero p INNER JOIN ciudad c ON p.ciudad_id=c.ciudad_id"
			+ " INNER JOIN provincia pr ON pr.provincia_id=c.provincia_id";
	if(dni!=0) {
		try {
			sql+=" WHERE pasajero_documento=?;";
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
			PreparedStatement pst=con.prepareStatement(sql);
			pst.setInt(1, dni);
			ResultSet rs=pst.executeQuery();
			ConversorResultSetADefaultTableModel.rellena(rs, modelo);
			tabla=new JTable(modelo);
			rs.close();
			pst.close();
			con.close();
			return tabla;
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}else {
		if(nombre!=null) {
			if(apellido!=null) {
				if(provincia!=null) {
					sql=sql+" WHERE p.pasajero_nombre=? AND p.pasajero_apellido=? AND pr.provincia_nombre=?";
				}else {
					sql=sql+" WHERE p.pasajero_nombre=? AND p.pasajero_apellido=?";
					caso=1;
				}
			}else {
				if(provincia!=null) {
					sql=sql+" WHERE p.pasajero_nombre=? AND pr.provincia_nombre=?";
					caso=2;
				}else {
					sql=sql+" WHERE p.pasajero_nombre=?";
					caso=3;
				}
			}
		}else {
			if(apellido!=null) {
				if(provincia!=null) {
					sql=sql+" WHERE p.pasajero_apellido=? AND pr.provincia_nombre=?";
					caso=4;
				}else {
					sql=sql+" WHERE p.pasajero_apellido=?";
					caso=5;
				}
			}else {
				if(provincia!=null) {
					sql=sql+" WHERE pr.provincia_nombre=?";
					caso=6;
				}else {
					caso=7;
				}
			}
		}
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
			PreparedStatement pst=con.prepareStatement(sql);
			switch(caso) {
			case 0:
				pst.setString(1,nombre);
				pst.setString(2,apellido);
				pst.setString(3,provincia);
				break;
			case 1:
				pst.setString(1,nombre);
				pst.setString(2,apellido);
				break;
			case 2:
				pst.setString(1,nombre);
				pst.setString(2,provincia);
				break;
			case 3:
				pst.setString(1,nombre);
				break;
			case 4:
				pst.setString(1,apellido);
				pst.setString(2,provincia);
				break;
			case 5:
				pst.setString(1,apellido);
				break;
			case 6:
				pst.setString(1,provincia);
                break;
            case 7:
			}
			ResultSet rs=pst.executeQuery();
			ConversorResultSetADefaultTableModel.rellena(rs, modelo);
			tabla=new JTable(modelo);
			rs.close();
			pst.close();
			con.close();
			return tabla;
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	return tabla;
}

/**
 * actualiza los datos del pasajero
 * @param nuevo datos nuevos del pasajero
 * @param viejo datos anteriores del pasajero
 */
public void actualizaPasajero(Pasajero p) {
	String sql="UPDATE pasajero SET ";
	int i=0;
	
	if (!this.getNombre().equals(p.getNombre())) {
		sql+="pasajero_nombre=?, ";
		i++;}
	
	if (!this.getApellido().equals(p.getApellido())) {
		sql+="pasajero_apellido=?, ";
		i++;}
	
	if (p.getDireccion()!=null && !this.getDireccion().equals(p.getDireccion())) {
		sql+="ciudad_id=?, pasajero_domicilio=?, ";
		i+=2;}
	
	if (p.getEstadoCivil()!=null && !this.getEstadoCivil().equals(p.getEstadoCivil())) {
		sql+="pasajero_estado_civil=?, ";
		i++;}
	
	if (this.getTelefono() != p.getTelefono()) {
		sql+="pasajero_telefono=?, ";
		i++;}
	
	if (!observaciones.equals(p.observaciones)) {
		sql+="pasajero_observaciones=?, ";
		i++;}
	
	if (p.profesion!=null && !profesion.equals(p.profesion)) {
		sql+="pasajero_profesion=? ";
		i++;}
	
	if(sql.charAt(sql.length()-2)==',')
		sql=sql.substring(0, sql.length()-2);
		
	try {
		sql+=" WHERE pasajero_documento=?";
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root","");
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setInt(i+1, p.getNumeroDocumento());
		i=1;
		
		if (!this.getNombre().equals(p.getNombre())) {
			pst.setString(i, p.getNombre());
			i++;
		}
			
		if (!this.getApellido().equals(p.getApellido())) {
			pst.setString(i, p.getApellido());
			i++;
		}
					
		if (!this.getDireccion().equals(p.getDireccion())) {
			pst.setInt(i, p.getDireccion().getCiudadId());
			i++;
			pst.setString(i, p.getDireccion().getDomicilio());
			i++;
		}
				
		if (!this.getEstadoCivil().equals(p.getEstadoCivil())) {
			pst.setString(i, p.getEstadoCivil());
			i++;
		}
					
		if (this.getTelefono() != p.getTelefono()) {
			pst.setLong(i, p.getTelefono());
			i++;
		}
				
		if (!observaciones.equals(p.observaciones)) {
			pst.setString(i, p.observaciones);
			i++;
		}
					
		if (!profesion.equals(p.profesion)) 
			pst.setString(i, p.profesion);
		
		pst.executeUpdate();
		pst.close();
		con.close();
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
}


public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (!super.equals(obj))
		return false;
	if (getClass() != obj.getClass())
		return false;
	Pasajero other = (Pasajero) obj;
	if (observaciones == null) {
		if (other.observaciones != null)
			return false;
	} else if (!observaciones.equals(other.observaciones))
		return false;
	if (profesion == null) {
		if (other.profesion != null)
			return false;
	} else if (!profesion.equals(other.profesion))
		return false;
	return true;
}

}
