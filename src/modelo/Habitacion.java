package modelo;

public class Habitacion {
private int numero;
private String tipo;
private String estado;
private int precio;

public Habitacion(int numero, String tipo, String estado, int precio) {
	this.numero = numero;
	this.tipo = tipo;
	this.estado = estado;
	this.precio = precio;
}

public int getNumero() {
	return numero;
}
public void setNumero(int numero) {
	this.numero = numero;
}
public String getTipo() {
	return tipo;
}
public void setTipo(String tipo) {
	this.tipo = tipo;
}
public String getEstado() {
	return estado;
}
public void setEstado(String estado) {
	this.estado = estado;
}
public int getPrecio() {
	return precio;
}
public void setPrecio(int precio) {
	this.precio = precio;
}


}
