package vista;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import controlador.ManejoHabitacion;
import controlador.Reserva;
import datos.MBD;
import modelo.Pasajero;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;

public class FormReserva extends JInternalFrame {
	private JTextField documento;
	private JTextField nombre;
	private JTextField apellido;
	private JTextField telefono;
	private JComboBox<String> habitacionTipo;
	private JDateChooser egreso;
	private JTextArea observaciones;
	private JDateChooser ingreso;
	private JComboBox<String> habitacionNumero;
	private Reserva reserva;
	private JTextField modificarDocumento;
	private JTextField cancelarDocumento;
	private JDateChooser modificarAntiguoIngreso;
	private JDateChooser modificarNuevoIngreso;
	private JDateChooser modificarNuevoEgreso;
	private JComboBox<String> modificarTipoHabitacion;
	private JComboBox<String> modificarNumeroHabitacion;
	private JDateChooser cancelarIngreso;

	/**
	 * Create the frame.
	 */
	public FormReserva() {
		setBounds(100, 100, 484, 429);
		getContentPane().setLayout(new CardLayout(0, 0));
		
		MBD m=new MBD();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		getContentPane().add(tabbedPane, "name_622420007383700");
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Nueva reserva", null, panel, null);
		panel.setLayout(null);
		
		JLabel label = new JLabel("Documento n\u00FAmero");
		label.setBounds(10, 14, 113, 14);
		panel.add(label);
		
		documento = new JTextField();
		documento.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				int numero=getDocumento();
				if(numero!=-1) {
					Pasajero p=Pasajero.devuelvePasajero(numero);
					if(p!=null) {
						nombre.setText(p.getNombre());
						apellido.setText(p.getApellido());
						telefono.setText(""+p.getTelefono());
						observaciones.setText(p.getObservaciones());
					}
				}
			}
		});
		documento.setColumns(10);
		documento.setBounds(133, 11, 196, 20);
		panel.add(documento);
		
		JLabel label_1 = new JLabel("Nombre/s");
		label_1.setBounds(10, 39, 113, 14);
		panel.add(label_1);
		
		nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(133, 36, 196, 20);
		panel.add(nombre);
		
		JLabel label_2 = new JLabel("Apellido");
		label_2.setBounds(10, 64, 113, 14);
		panel.add(label_2);
		
		apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(133, 61, 196, 20);
		panel.add(apellido);
		
		JLabel label_3 = new JLabel("Telefono");
		label_3.setBounds(10, 89, 113, 14);
		panel.add(label_3);
		
		telefono = new JTextField();
		telefono.setColumns(10);
		telefono.setBounds(133, 86, 196, 20);
		panel.add(telefono);
		
		JLabel lblHabitacinTipo = new JLabel("Habitaci\u00F3n tipo");
		lblHabitacinTipo.setBounds(10, 164, 113, 14);
		panel.add(lblHabitacinTipo);
		
		habitacionTipo = new JComboBox<String>();
		habitacionTipo.addItem("");
		m.llenarCombo(habitacionTipo, "tipo_habitacion_nombre", "tipo_habitacion");
		habitacionTipo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					if(getIngreso()!=null && getEgreso()!=null) {
						habitacionNumero.setEnabled(true);
						habitacionNumero.removeAllItems();
						ManejoHabitacion.getInstancia().llenarComboHabLib(habitacionNumero,getIngreso(),getEgreso(),getHabitacionTipo());					
					} else {
						habitacionTipo.removeItemListener(this);
						habitacionTipo.setSelectedItem("");
						JOptionPane.showConfirmDialog(getParent(), "Primero seleccione la fecha de ingreso y egreso",
								"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						habitacionTipo.addItemListener(this);
					}
				}					
			}
		});
		habitacionTipo.setBounds(133, 161, 113, 20);
		panel.add(habitacionTipo);
		
		JLabel label_5 = new JLabel("Fecha egreso");
		label_5.setBounds(10, 139, 113, 14);
		panel.add(label_5);
		
		egreso = new JDateChooser();
		egreso.setBounds(133, 136, 113, 20);
		panel.add(egreso);
		
		JLabel label_6 = new JLabel("Observaciones:");
		label_6.setBounds(10, 214, 145, 14);
		panel.add(label_6);
		
		observaciones = new JTextArea();
		observaciones.setBounds(10, 234, 344, 114);
		panel.add(observaciones);
		
		JButton confirmar = new JButton("Reservar");
		confirmar.addActionListener( (e)-> {
				int numeroDocumento=getDocumento();
				if(numeroDocumento!=-1) {
					Pasajero pasajero=new Pasajero();
					pasajero.setNumeroDocumento(numeroDocumento);
					pasajero.setNombre(getNombre());
					pasajero.setApellido(getApellido());
					pasajero.setTelefono(getTelefono());
					pasajero.setObservaciones(getObservaciones());
					
					int numero=getHabitacionNumero();
					if(numero!=-1) {
						reserva=new Reserva(Pasajero.devuelvePasajero(numeroDocumento),getIngreso(),getEgreso(),numero);
						int[] numeros=reserva.getNumeroReserva();
						String numeroReserva;
						if(numeros.length==1) {
							numeroReserva=", el numero de reserva es: " + numeros[0];
						} else {
							numeroReserva=", los números de reserva son: ";
							for(int i=0;i<numeros.length;i++) {
								numeroReserva+=numeros[i] + ", ";
							}
							numeroReserva=numeroReserva.substring(0, numeroReserva.length()-2);
						}
						reserva.nuevaReserva(pasajero);
						JOptionPane.showConfirmDialog(getParent(), "Se ha reliazado la reserva con exito" + numeroReserva,
								"Operación exitosa", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						vaciarFormulario();
					} else {
						JOptionPane.showConfirmDialog(getParent(), "No hay habitación disponible",
								"Sin habitación", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showConfirmDialog(getParent(), "Primero se debe llenar los datos del pasajero",
							"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
		});
		confirmar.setBounds(150, 359, 89, 23);
		panel.add(confirmar);
		
		JLabel label_7 = new JLabel("Fecha ingreso");
		label_7.setBounds(10, 114, 113, 14);
		panel.add(label_7);
		
		ingreso = new JDateChooser();
		ingreso.setBounds(133, 111, 113, 20);
		panel.add(ingreso);
		
		JLabel lblHabitacinNmero = new JLabel("Habitaci\u00F3n n\u00FAmero");
		lblHabitacinNmero.setBounds(10, 189, 113, 14);
		panel.add(lblHabitacinNmero);
		
		habitacionNumero = new JComboBox<String>();
		habitacionNumero.setEnabled(false);
		habitacionNumero.setBounds(133, 186, 113, 20);
		panel.add(habitacionNumero);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Modificar reserva", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel label_4 = new JLabel("Documento n\u00FAmero");
		label_4.setBounds(10, 14, 113, 14);
		panel_1.add(label_4);
		
		modificarDocumento = new JTextField();
		modificarDocumento.setBounds(133, 11, 196, 20);
		modificarDocumento.setColumns(10);
		panel_1.add(modificarDocumento);
		
		JLabel label_8 = new JLabel("Fecha ingreso");
		label_8.setBounds(10, 39, 113, 14);
		panel_1.add(label_8);
		
		modificarAntiguoIngreso = new JDateChooser();
		modificarAntiguoIngreso.setBounds(133, 36, 113, 20);
		panel_1.add(modificarAntiguoIngreso);
		
		JLabel label_9 = new JLabel("Fecha egreso");
		label_9.setBounds(10, 89, 113, 14);
		panel_1.add(label_9);
		
		modificarNuevoEgreso = new JDateChooser();
		modificarNuevoEgreso.setBounds(133, 86, 113, 20);
		panel_1.add(modificarNuevoEgreso);
		
		JLabel label_10 = new JLabel("Habitaci\u00F3n n\u00FAmero");
		label_10.setBounds(10, 139, 113, 14);
		panel_1.add(label_10);
		
		modificarNumeroHabitacion = new JComboBox<String>();
		modificarNumeroHabitacion.setBounds(133, 137, 113, 20);
		modificarNumeroHabitacion.setEnabled(false);
		panel_1.add(modificarNumeroHabitacion);
		
		JLabel lblNuevaIngreso = new JLabel("nueva ingreso");
		lblNuevaIngreso.setBounds(10, 64, 113, 14);
		panel_1.add(lblNuevaIngreso);
		
		modificarNuevoIngreso = new JDateChooser();
		modificarNuevoIngreso.setBounds(133, 61, 113, 20);
		panel_1.add(modificarNuevoIngreso);
		
		modificarTipoHabitacion = new JComboBox<String>();
		modificarTipoHabitacion.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					if(getModificarNuevoIngreso()!=null && getModificarNuevoEgreso()!=null) {
						modificarNumeroHabitacion.setEnabled(true);
						modificarNumeroHabitacion.removeAllItems();
						ManejoHabitacion.getInstancia().llenarComboHabLib(modificarNumeroHabitacion,getModificarNuevoIngreso(),
								getModificarNuevoEgreso(),getModificarTipoHabitacion());
					} else {
						modificarTipoHabitacion.removeItemListener(this);
						modificarTipoHabitacion.setSelectedItem("");
						JOptionPane.showConfirmDialog(getParent(), "Primero seleccione la nueva fecha de ingreso y egreso",
								"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						modificarTipoHabitacion.addItemListener(this);
					}
				}					
			}
		});
		modificarTipoHabitacion.setBounds(133, 112, 113, 20);
		panel_1.add(modificarTipoHabitacion);
		
		JLabel label_11 = new JLabel("Habitaci\u00F3n tipo");
		label_11.setBounds(10, 114, 113, 14);
		panel_1.add(label_11);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener((e) -> {
			int documento=getModificarDocumento();
			if(documento!=-1) {
				GregorianCalendar ingreso=getModificarNuevoIngreso();
				GregorianCalendar egreso=getModificarNuevoEgreso();
				if(ingreso!=null && egreso!=null) {
					int numeroHab=getModificarNumeroHabitacion();
					if(numeroHab!=-1) {
						GregorianCalendar antiguoIngreso=getModificarAntiguoIngreso();
						reserva=new Reserva(Pasajero.devuelvePasajero(documento),ingreso,egreso,numeroHab);
						reserva.actualizarReserva(antiguoIngreso);
					}else {
						JOptionPane.showConfirmDialog(getParent(), "No sé ha seleccionado ninguna habitacion",
								"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showConfirmDialog(getParent(), "Primero seleccione la nueva fecha de ingreso y egreso",
							"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showConfirmDialog(getParent(), "Debe ingresar el número de documento",
						"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		});
		btnConfirmar.setBounds(114, 206, 89, 23);
		panel_1.add(btnConfirmar);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Cancelar reserva", null, panel_2, null);
		panel_2.setLayout(null);
		
		cancelarDocumento = new JTextField();
		cancelarDocumento.setBounds(133, 11, 196, 20);
		cancelarDocumento.setColumns(10);
		panel_2.add(cancelarDocumento);
		
		JLabel label_12 = new JLabel("Documento n\u00FAmero");
		label_12.setBounds(10, 14, 113, 14);
		panel_2.add(label_12);
		
		JLabel label_13 = new JLabel("Fecha ingreso");
		label_13.setBounds(10, 39, 113, 14);
		panel_2.add(label_13);
		
		cancelarIngreso = new JDateChooser();
		cancelarIngreso.setBounds(133, 36, 113, 20);
		panel_2.add(cancelarIngreso);
		
		JButton btnCancelar = new JButton("Confirmar");
		btnCancelar.setBounds(125, 90, 89, 23);
		btnCancelar.addActionListener((e)->{
			int numero=getCancelarDocumento();
			GregorianCalendar ingreso=getCancelarIngreso();
			if(numero!=-1) {
				if(ingreso!=null) {
					Reserva.cancelarReserva(numero,ingreso);
				} else {
					JOptionPane.showConfirmDialog(getParent(), "Debe ingresar la fecha de ingreso primero",
							"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showConfirmDialog(getParent(), "Debe ingresar el número de documento",
						"Faltan datos", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
			}
		});
		panel_2.add(btnCancelar);
	}
	
	private int getModificarDocumento() {
		String numero=this.modificarDocumento.getText();
		return (numero.length()>0) ? Integer.parseInt(numero) : -1;
	}

	private int getCancelarDocumento() {
		String numero=this.cancelarDocumento.getText();
		return (numero.length()>0) ? Integer.parseInt(numero) : -1;
	}
	
	private int getDocumento() {
		String numero=this.documento.getText();
		return (numero.length()>0) ? Integer.parseInt(numero) : -1;
	}
	
	private String getNombre() {
		return nombre.getText();
	}
	
	private String getApellido() {
		return apellido.getText();
	}
	
	private long getTelefono() {
		String numero=telefono.getText();
		return (numero.length()>0) ? Long.parseLong(numero) : -1L;
	}
	
	private GregorianCalendar getIngreso() {
		GregorianCalendar fecha=new GregorianCalendar();
		Date in=ingreso.getDate();
		if(in!=null)
			fecha.setTimeInMillis(in.getTime());
		else 
			return null;
		return fecha;
	}
	
	private GregorianCalendar getModificarNuevoIngreso() {
		GregorianCalendar fecha=new GregorianCalendar();
		Date in=modificarNuevoIngreso.getDate();
		if(in!=null)
			fecha.setTimeInMillis(in.getTime());
		else 
			return null;
		return fecha;
	}
	
	private GregorianCalendar getCancelarIngreso() {
		GregorianCalendar fecha=new GregorianCalendar();
		Date in=cancelarIngreso.getDate();
		if(in!=null)
			fecha.setTimeInMillis(in.getTime());
		else 
			return null;
		return fecha;
	}
	
	private GregorianCalendar getModificarAntiguoIngreso() {
		GregorianCalendar fecha=new GregorianCalendar();
		Date in=modificarAntiguoIngreso.getDate();
		if(in!=null)
			fecha.setTimeInMillis(in.getTime());
		else 
			return null;
		return fecha;
	}
	
	private GregorianCalendar getEgreso() {
		GregorianCalendar fecha=new GregorianCalendar();
		Date out=egreso.getDate();
		if(out!=null)
			fecha.setTimeInMillis(out.getTime());
		else 
			return null;
		return fecha;
	}
	
	private GregorianCalendar getModificarNuevoEgreso() {
		GregorianCalendar fecha=new GregorianCalendar();
		Date out=modificarNuevoEgreso.getDate();
		if(out!=null)
			fecha.setTimeInMillis(out.getTime());
		else 
			return null;
		return fecha;
	}
	
	private String getHabitacionTipo() {
		return habitacionTipo.getSelectedItem().toString();
	}
	
	private String getModificarTipoHabitacion(){
		return modificarTipoHabitacion.getSelectedItem().toString();
	}
	
	private int getHabitacionNumero() {
		String numero=habitacionNumero.getSelectedItem().toString();
		return (numero.length()>0) ? Integer.parseInt(numero) : -1;
	}
	
	private String getObservaciones() {
		return observaciones.getText();
	}
	
	private int getModificarNumeroHabitacion() {
		String numero=modificarNumeroHabitacion.getSelectedItem().toString();
		return (numero.length()>0) ? Integer.parseInt(numero) : -1;
	}
	
	private void vaciarFormulario() {
		documento.setText("");
		nombre.setText("");
		apellido.setText("");
		telefono.setText("");
		habitacionTipo.setSelectedItem("");
		habitacionNumero.setSelectedItem("");
		observaciones.setText("");
		ingreso.cleanup();
		egreso.cleanup();
	}
}
