package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;

import modelo.Usuario;

public class Usuarios {
	private Usuario usuario;

	public Usuarios(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Usuarios() {
		
	}

	/**
	 * verifica si existe el usuario en la base de dato
	 * @param usuario nombre de usuario
	 * @param contrasenia contrasenia del usuario
	 * @return devuelve true si la clave y usuario son correctas 
	 */
	public boolean login(String usuario,String contrasenia) {
		Usuario user;
		String u="";//usuario
		String c="";//contrasenia
		try {
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino","root","");
			ResultSet rs=con.createStatement().executeQuery("SELECT * FROM usuario");
			while(rs.next()) {
	    		u=rs.getString(10);
	    		c=rs.getString(9);
	    		if(u.equals(usuario) && c.equals(contrasenia)) {
	    			user=new Usuario();
	    			user.setNumeroDocumento(rs.getInt(1));
	    			user.setNombre(rs.getString(2));
	    			user.setApellido(rs.getString(3));
	    			user.setTelefono(rs.getLong(4));
	    			GregorianCalendar nacimiento=new GregorianCalendar();
	    			nacimiento.setTimeInMillis(rs.getDate(5).getTime());
	    			user.setNacimiento(nacimiento);	    		
	    			user.setRol(rs.getInt(8));	   
	    			con.close();
	        		rs.close();
	        		this.usuario=user;
	    			return true;
	    		}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public int getRol() {
		return usuario.getRol();
	}
	
}
