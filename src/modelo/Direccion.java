package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Direccion {
	
private String domicilio,ciudad, provincia,pais;

public Direccion(String domicilio, String ciudad, String provincia, String pais) {
	super();
	this.domicilio = domicilio;
	this.ciudad = ciudad;
	this.provincia = provincia;
	this.pais = pais;
}

public String getDomicilio() {
	return domicilio;
}

public void setDomicilio(String domicilio) {
	this.domicilio = domicilio;
}

public String getCiudad() {
	return ciudad;
}

public void setCiudad(String ciudad) {
	this.ciudad = ciudad;
}

public String getProvincia() {
	return provincia;
}

public void setProvincia(String provincia) {
	this.provincia = provincia;
}

public String getPais() {
	return pais;
}

public void setPais(String pais) {
	this.pais = pais;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Direccion other = (Direccion) obj;
	if (ciudad == null) {
		if (other.ciudad != null)
			return false;
	} else if (!ciudad.equals(other.ciudad))
		return false;
	if (domicilio == null) {
		if (other.domicilio != null)
			return false;
	} else if (!domicilio.equals(other.domicilio))
		return false;
	if (pais == null) {
		if (other.pais != null)
			return false;
	} else if (!pais.equals(other.pais))
		return false;
	if (provincia == null) {
		if (other.provincia != null)
			return false;
	} else if (!provincia.equals(other.provincia))
		return false;
	return true;
}

public static int getPaisId(String pais) {
	String sql="select pais_id from pais where pais_nombre='"+pais+"'";
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root", "");
		ResultSet rs=con.createStatement().executeQuery(sql);
		rs.next();
		return rs.getInt(1);
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return -1;
}

public static int getProvinciaId(String provincia) {
	String sql="select provincia_id from provincia where provincia_nombre='"+provincia+"'";
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root", "");
		ResultSet rs=con.createStatement().executeQuery(sql);
		rs.next();
		return rs.getInt(1);
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return -1;
}

public int getCiudadId() {
	try {
		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
		String sql="SELECT ciudad_id FROM ciudad "
				+ "WHERE ciudad_nombre=?";
		PreparedStatement pst=con.prepareStatement(sql);
		pst.setString(1, ciudad);
		ResultSet rs=pst.executeQuery();
		rs.next();
		int ciudad_id=rs.getInt(1);
		rs.close();
		pst.close();
		con.close();
		return ciudad_id;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return -1;
}

}
