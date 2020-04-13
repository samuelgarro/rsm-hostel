package modelo;

import java.util.GregorianCalendar;

public abstract class Persona{
	private String tipoDocumento;
	private int numeroDocumento;
	private String nombre;
	private String apellido;
	private long telefono;
	private GregorianCalendar nacimiento;
	private String estadoCivil;
	private Direccion direccion;

	public Persona(String tipoDocumento, int numeroDocumento, String nombre, String apellido, long telefono,
			GregorianCalendar nacimiento,String estadoCivil, Direccion direccion) {
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.nacimiento = nacimiento;
		this.estadoCivil=estadoCivil;
		this.direccion=direccion;
	}

	
	public Persona() {
		this.tipoDocumento ="";
		this.numeroDocumento =0;
		this.nombre ="";
		this.apellido = "";
		this.telefono = 0L;
		this.nacimiento = null;
		this.estadoCivil="";
		this.direccion=null;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public int getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(int numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public long getTelefono() {
		return telefono;
	}
	public void setTelefono(long telefono) {
		this.telefono = telefono;
	}
	public GregorianCalendar getNacimiento() {
		return nacimiento;
	}
	public void setNacimiento(GregorianCalendar nacimiento) {
		this.nacimiento = nacimiento;
	}
	public Direccion getDireccion() {
		return this.direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion=direccion;
	}
	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (direccion == null) {
			if (other.direccion != null)
				return false;
		} else if (!direccion.equals(other.direccion))
			return false;
		if (estadoCivil == null) {
			if (other.estadoCivil != null)
				return false;
		} else if (!estadoCivil.equals(other.estadoCivil))
			return false;
		if (nacimiento == null) {
			if (other.nacimiento != null)
				return false;
		} else if (!nacimiento.equals(other.nacimiento))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numeroDocumento != other.numeroDocumento)
			return false;
		if (telefono != other.telefono)
			return false;
		if (tipoDocumento == null) {
			if (other.tipoDocumento != null)
				return false;
		} else if (!tipoDocumento.equals(other.tipoDocumento))
			return false;
		return true;
	}

	
}
