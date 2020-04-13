package datos;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ControlDatos{
	//--------------------Control del nombre--------------------
	public static boolean nombre(String nombre) {
		if(nombre.length()!=0) {
		for(int i=0;i<nombre.length();i++) {
			int ascii=(int)nombre.charAt(i);
			if(!(ascii>=65 && ascii<=90 || ascii>=97 && ascii<=122)) {
				if(!(ascii==32 || ascii==225 || ascii==233 || ascii==237 || ascii==241 || ascii==243 || ascii==250)) {
					if(!(ascii==193 || ascii==201 || ascii==205 || ascii==209 || ascii==211 || ascii==218)) {
						return false;
						}
					}
				}
		}
		return true;}
		return false;
	}
	public static boolean apellido(String apellido) {
		return ControlDatos.nombre(apellido);
	}
	//--------------------Control del documento--------------------
	public static boolean documento(String documento) {
		if(documento.length()!=0) {
		for(int i=0;i<documento.length();i++) {
			int ascii=(int)documento.charAt(i);
			if(ascii<48 || ascii>57) {
				return false;
			}
		}
		if(documento.length()<7 || documento.length()>8) {
			return false;}
		return true;}
		return false;
		}
	//--------------------Control del numero de telefono--------------------
	public static boolean telefono(String telefono) {
		if(telefono.length()!=0) {
		for(int i=0;i<telefono.length();i++) {
			int ascii=(int)telefono.charAt(i);
			if(ascii<48 || ascii>57) {
				return false;
			}
		}
		if(telefono.length()!=10 && telefono.length()!=6) {
			return false;
		}
		return true;}
		return false;
	}
	//--------------------control de la direccion--------------------
	public static boolean direccion(String direccion) {
		if(direccion.length()!=0) {
		for(int i=0;i<direccion.length();i++) {
			int ascii=(int)direccion.charAt(i);
			if(!(ascii>=48 && ascii<=57 || ascii>=65 && ascii<=90 || ascii>=97 && ascii<=122)) {
				if(!(ascii==32 || ascii==225 || ascii==233 || ascii==237 || ascii==241 || ascii==243 || ascii==250)) {
					if(!(ascii==193 || ascii==201 || ascii==205 || ascii==209 || ascii==211 || ascii==218)) {
						return false;
						}
				}
			}
		}
		return true;}
		return false;
	}

	//--------------------control de fecha entrada-salida--------------------
	public static boolean fecha(GregorianCalendar fechaE,GregorianCalendar fechaS) {
		if(fechaE!=null) {
			if(fechaS==null) {return true;}
			if(fechaS.get(Calendar.YEAR)==fechaE.get(Calendar.YEAR)) {//ao salida ingreso
				if(fechaS.get(Calendar.MONTH)==fechaE.get(Calendar.MONTH)){//mes salida ingreso
					if(fechaS.get(Calendar.DATE)<fechaE.get(Calendar.DATE)) {//dia salida ingreso
						return false;//no puede ser un dia anterior
					}
				}else if(fechaS.get(Calendar.MONTH)<fechaE.get(Calendar.MONTH)) {//mes salida ingreso
					return false;//no puede ser un mes anterior
				}
			}else {
				if(fechaS.get(Calendar.YEAR)<fechaE.get(Calendar.YEAR)) {//a�o salida ingreso
				return false;//no puede ser un a�o anterior
				}
			}
			return true;}
		return false;
	}
	//-------------------------control edad-------------------------
	public static boolean edad(String edad) {
	if(edad.length()>0) {
	if(edad.length()<=3) {
		for(int i=0;i<edad.length();i++) {
			if(!((int)edad.charAt(i)>=48 && (int)edad.charAt(i)<=57)) {
				return false;
			}
		}
	}
	return true;}
	return false;
	}
}
	
