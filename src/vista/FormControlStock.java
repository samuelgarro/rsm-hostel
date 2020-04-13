package vista;


import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controlador.ControlStock;
import modelo.Producto;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class FormControlStock extends JInternalFrame {
	private JTextField nuevoMarca;
	private JTextField nuevoDescripcion;
	private JSpinner nuevoCantidad;
	private JComboBox<String> nuevoUnidad;
	private JComboBox<String> modificarCodigo;
	private JSpinner modificarCantidad;
	private JComboBox<String> eliminarCodigo;
	private JTextField modificarMarca;
	private JTextField modificarDescripcion;
	private JComboBox<String> modificarAccion;
	
	/**
	 * Create the frame.
	 */
	public FormControlStock() {
		setBounds(100, 100, 472, 285);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(10, 11, 436, 233);
		getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Nuevo producto", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblMarca = new JLabel("Marca");
		lblMarca.setBounds(10, 11, 80, 14);
		panel.add(lblMarca);
		
		nuevoMarca = new JTextField();
		nuevoMarca.setBounds(100, 8, 190, 20);
		panel.add(nuevoMarca);
		nuevoMarca.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setBounds(10, 36, 88, 14);
		panel.add(lblDescripcion);
		
		nuevoDescripcion = new JTextField();
		nuevoDescripcion.setBounds(100, 33, 190, 20);
		panel.add(nuevoDescripcion);
		nuevoDescripcion.setColumns(10);
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(10, 61, 88, 14);
		panel.add(lblCantidad);
		
		nuevoCantidad = new JSpinner();
		nuevoCantidad.setBounds(100, 58, 70, 20);
		panel.add(nuevoCantidad);
		
		nuevoUnidad = new JComboBox<String>();
		nuevoUnidad.setModel(new DefaultComboBoxModel<String>(new String[] {"", "Unidades", "Kilogramos", "Litros"}));
		nuevoUnidad.setBounds(180, 58, 110, 20);
		panel.add(nuevoUnidad);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener( (e)->{
			String marca=getNuevoMarca();
			String descripcion=getNuevoDescripcion();
			float cantidad=getNuevoCantidad();
			byte unidad=getNuevoUnidad();
			if(cantidad!= -1 && unidad!=-1 && marca.length()>0 && descripcion.length()>0) {
				ControlStock control=new ControlStock(marca, descripcion, cantidad, unidad);
				control.crearProducto();
				JOptionPane.showConfirmDialog(getParent(), "Se a guardado el producto con exito", "Operacion exitosa",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showConfirmDialog(getParent(), "Primero llene todos los campos", "Faltan datos",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		});
		btnConfirmar.setBounds(140, 118, 101, 23);
		panel.add(btnConfirmar);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Compra/consumo", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Marca");
		label.setBounds(10, 39, 80, 14);
		panel_1.add(label);
		
		modificarMarca = new JTextField();
		modificarMarca.setColumns(10);
		modificarMarca.setBounds(100, 36, 190, 20);
		panel_1.add(modificarMarca);
		
		JLabel label_1 = new JLabel("Descripci\u00F3n");
		label_1.setBounds(10, 64, 88, 14);
		panel_1.add(label_1);
		
		modificarDescripcion = new JTextField();
		modificarDescripcion.setColumns(10);
		modificarDescripcion.setBounds(100, 61, 190, 20);
		panel_1.add(modificarDescripcion);
		
		JLabel label_2 = new JLabel("Cantidad");
		label_2.setBounds(10, 89, 88, 14);
		panel_1.add(label_2);
		
		modificarCantidad = new JSpinner();
		modificarCantidad.setBounds(100, 86, 70, 20);
		panel_1.add(modificarCantidad);
		
		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(10, 11, 80, 14);
		panel_1.add(lblCodigo);
		
		modificarCodigo = new JComboBox<String>();
		Producto.llenarCombo(modificarCodigo);
		modificarCodigo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					int codigo=getModificarCodigo();
					Producto producto=Producto.devuelveProducto(codigo);
					modificarMarca.setText(producto.getMarca());
					modificarDescripcion.setText(producto.getDescripcion());
				}
			}
		});
		modificarCodigo.setBounds(100, 8, 190, 20);
		panel_1.add(modificarCodigo);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.setBounds(134, 139, 110, 23);
		btnActualizar.addActionListener( (e)->{
			int codigo=getModificarCodigo();
			String marca=getModificarMarca();
			String descripcion=getModificarDescripcion();
			float cantidad=getModificarCantidad();
			if(codigo!=-1) {
				ControlStock control=new ControlStock(codigo);
				control.modificarProducto(marca, descripcion, cantidad*getModificarAccion());
				JOptionPane.showConfirmDialog(getParent(), "Se actualizo el producto con exito", "Exito en la operacion",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showConfirmDialog(getParent(), "Primero seleccione el producto", "Faltan datos",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		});
		panel_1.add(btnActualizar);
		
		modificarAccion = new JComboBox<String>();
		modificarAccion.setModel(new DefaultComboBoxModel<String>(new String[] {"Comprados", "Consumidos"}));
		modificarAccion.setBounds(180, 86, 110, 20);
		panel_1.add(modificarAccion);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Eliminar producto", null, panel_2, null);
		panel_2.setLayout(null);
		
		
		JLabel label_3 = new JLabel("Codigo");
		label_3.setBounds(10, 14, 80, 14);
		panel_2.add(label_3);
		
		JTextField eliminarMarca = new JTextField();
		eliminarMarca.setEnabled(false);
		eliminarMarca.setColumns(10);
		eliminarMarca.setBounds(100, 36, 190, 20);
		panel_2.add(eliminarMarca);
		
		JTextField eliminarDescripcion = new JTextField();
		eliminarDescripcion.setEnabled(false);
		eliminarDescripcion.setColumns(10);
		eliminarDescripcion.setBounds(100, 61, 190, 20);
		panel_2.add(eliminarDescripcion);
		
		JSpinner eliminarCantidad = new JSpinner();
		eliminarCantidad.setEnabled(false);
		eliminarCantidad.setBounds(100, 86, 70, 20);
		panel_2.add(eliminarCantidad);
		
		JLabel label_4 = new JLabel("Cantidad");
		label_4.setBounds(10, 89, 80, 14);
		panel_2.add(label_4);
		
		JLabel label_5 = new JLabel("Descripci\u00F3n");
		label_5.setBounds(10, 64, 88, 14);
		panel_2.add(label_5);
		
		JLabel label_6 = new JLabel("Marca");
		label_6.setBounds(10, 39, 70, 14);
		panel_2.add(label_6);
		
		JComboBox<String> eliminarUnidad = new JComboBox<String>();
		eliminarUnidad.setEnabled(false);
		eliminarUnidad.setBounds(180, 86, 110, 20);
		panel_2.add(eliminarUnidad);
		
		eliminarCodigo = new JComboBox<String>();
		Producto.llenarCombo(eliminarCodigo);
		eliminarCodigo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					int codigo=getEliminarCodigo();
					Producto producto=Producto.devuelveProducto(codigo);
					System.out.println(codigo);
					System.out.println(producto==null);
					eliminarMarca.setText(producto.getMarca());
					eliminarDescripcion.setText(producto.getDescripcion());
					eliminarCantidad.setValue(producto.getCantidad());
					String unidad="";
					switch(producto.getUnidad()) {
					case Producto.KG:
						unidad="Kilogramos";
						break;
					case Producto.L: 
						unidad="Litros";
						break;
					case Producto.UNIDADES:
						unidad="Unidades";
					}
					eliminarUnidad.addItem(unidad);
				}
			}
		});
		eliminarCodigo.setBounds(100, 11, 190, 20);
		panel_2.add(eliminarCodigo);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(144, 142, 89, 23);
		btnEliminar.addActionListener( (e)->{
			int codigo=getEliminarCodigo();
			if(codigo!=-1) {
				ControlStock.eliminar(codigo);
				JOptionPane.showConfirmDialog(getParent(), "Se eliminado el producto con exito", "Exito en la operacion",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showConfirmDialog(getParent(), "Primero seleccione el producto", "Faltan datos",
						JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		});
		panel_2.add(btnEliminar);
	
	}
	
	private String getNuevoMarca() {
		return nuevoMarca.getText();
	}
	
	private String getNuevoDescripcion() {
		return nuevoDescripcion.getText();
	}
	
	private float getNuevoCantidad() {
		String numero = nuevoCantidad.getValue().toString();
		return (numero.length()>0) ? Long.parseLong(numero) : -1;
	}
	
	private byte getNuevoUnidad() {
		String unidad=nuevoUnidad.getSelectedItem().toString();
		switch(unidad) {
		case "Kilogramos":
			return Producto.KG;
		case "Litros": 
			return Producto.L;
		case "Unidades":
			return Producto.UNIDADES;
		default : 
			return -1;
		}
	}
	
	private int getModificarCodigo() {
		String codigo=modificarCodigo.getSelectedItem().toString().split("-")[0];
		return (codigo.length()>0) ? Integer.parseInt(codigo.trim()) : -1;
	}
	
	private String getModificarMarca() {
		return modificarMarca.getText();
	}
	
	private String getModificarDescripcion() {
		return modificarDescripcion.getText();
	}
	
	private float getModificarCantidad() {
		String numero = modificarCantidad.getValue().toString();
		return (numero.length()>0) ? Long.parseLong(numero) : -1;
	}
	
	private int getModificarAccion() {
		String accion=modificarAccion.getSelectedItem().toString();
		switch(accion) {
		case "Comprados":
			return 1;
		case "Consumidos": 
			return -1;
		default:
			return 0;
		}
	}
	
	private int getEliminarCodigo() {
		String codigo=eliminarCodigo.getSelectedItem().toString().split("-")[0];
		return (codigo.length()>0) ? Integer.parseInt(codigo.trim()) : -1;
	}
	
}
