package modelo;

import java.util.GregorianCalendar;

public class Usuario extends Persona{
	
private int rol;
/**
 * Rol de gerente
 */
public static final int GERENETE=1;
/**
 * Rol de conserje
 */
public static final int CONSERJE=0;
/**
 * genera un nuevo usuario
 * @param tipoDocumento tipo de documento DNI, pasaporte, ect
 * @param numeroDocumento numero de documento
 * @param nombre nombre del usuario
 * @param apellido apellido del usuario
 * @param telefono numero de telefono del usuario
 * @param nacimiento fecha de nacimiento
 * @param estadoCivil estado civil  en el que se encuentra
 * @param direccion direccion donde vive, incluse domicilio, ciudad, provincia y pais
 * @param rol rol que ocupa en la organización
 */
public Usuario(String tipoDocumento, int numeroDocumento, String nombre, String apellido, long telefono,
		GregorianCalendar nacimiento, String estadoCivil, Direccion direccion, int rol) {
	super(tipoDocumento, numeroDocumento, nombre, apellido, telefono, nacimiento, estadoCivil, direccion);
	this.rol=rol;
}

/**
 * crea un usuario vacio
 */
public Usuario() {
}
/**
 * obtener el rol del usuario
 * @return devuelve el rol del usuario
 */
public int getRol() {
	return rol;
}
/**
 * modifica el rol del usuario
 * @param rol rol a establecer para el usuario
 */
public void setRol(int rol) {
	this.rol=rol;
}

}
