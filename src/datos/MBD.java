package datos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;

public class MBD {
	private static Process xampp;
	private static Process mysql;
	/**
	 * llena el combo box con los datos del campo especificado
	 * @param combo JComoboBox a llenar
	 * @param campo campo de la tabla
	 * @param tabla tabla de la base de dato
	 * @param condicion es una condicion para filtrar
	 */
	public void llenarCombo(JComboBox<String> combo, String campo, String tabla, String condicion){
		String sql="";
		if(condicion==null)
			sql="SELECT "+campo+" from "+tabla+" order by "+campo;
		else 
			sql="SELECT "+campo+" from "+tabla+" where "+condicion+" order by "+campo;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotelbonino", "root", "");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while(rs.next()) {
				combo.addItem(rs.getString(campo));
			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * llena el combo box con los datos del campo especificado
	 * @param combo JComoboBox a llenar
	 * @param campo campo de la tabla
	 * @param tabla tabla de la base de dato
	 */
	public void llenarCombo(JComboBox<String> combo, String campo, String tabla) {
		llenarCombo(combo,campo,tabla,null);
	}
	
	public static void iniciarXampp() {
		try {
			xampp=Runtime.getRuntime().exec("C:\\xampp\\xampp_start.exe");
			mysql=Runtime.getRuntime().exec("C:\\xampp\\mysql\\bin\\mysqld.exe");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static void finalizarXampp() {
		xampp.destroyForcibly();
		mysql.destroyForcibly();
	}
}
