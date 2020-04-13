package controlador;

import modelo.Producto;

public class ControlStock {
	private Producto producto;
	/**
	 * devuelve el producto con el que se esta trabajando
	 * @return devuelve un producto
	 */
	public Producto getProducto() {
		return producto;
	}
	/**
	 * cambia de producto
	 * @param producto producto
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	/**
	 * inicia el control del stock de un producto
	 * @param marca marca del producto
	 * @param descripcion descripción del producto
	 * @param cantidad cantidad del producto
	 * @param unidad unidad de medida del producto puede ser Producto.KG, Producto.L, Producto.UNIDAD
	 */
	public ControlStock(String marca, String descripcion, float cantidad, byte unidad) {
		producto=new Producto(marca,descripcion,cantidad,unidad);
	}
	public ControlStock(int codigo) {
		producto=Producto.devuelveProducto(codigo);
	}
	/**
	 * guarda el producto en la base de datos
	 */
	public void crearProducto() {
		producto.insert();
	}
	/**
	 * modifica los datos del producto
	* @param marca marca del producto
	 * @param descripcion descripción del producto
	 * @param cantidad cantidad del producto
	 * @param unidad unidad de medida del producto puede ser Producto.KG, Producto.L, Producto.UNIDAD
	 */
	public void modificarProducto(String marca, String descripcion, float cantidad) {
		producto.setMarca(marca);
		producto.setDescripcion(descripcion);
		producto.setCantidad(cantidad);
		producto.update();
	}
	/**
	 * 
	 * @param codigo
	 */
	public static void eliminar(int codigo) {
		Producto.delete(codigo);
	}
	
}
