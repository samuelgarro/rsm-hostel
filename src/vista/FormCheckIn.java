package vista;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;

import controlador.CheckIn;
import controlador.ManejoHabitacion;
import datos.MBD;
import modelo.Direccion;
import modelo.Pasajero;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class FormCheckIn extends JInternalFrame {
	private JTextField numeroDocumento;
	private JComboBox<String> tipoDocumento;
	private JTextField nombre;
	private JTextField apellido;
	private JTextField telefono;
	private JTextField domicilio;
	private JComboBox<String> pais;
	private JComboBox<String> provincia;
	private JComboBox<String> ciudad;
	private JComboBox<String> habitacionNumero;
	private JDateChooser egreso;
	private JTextArea observaciones;
	private JButton confirmar;
	private JTextField profesion;
	private JDateChooser nacimiento;
	private JComboBox<String> estadoCivil;
	private CheckIn in;

	/**
	 * Create the frame.
	 */
	public FormCheckIn() {
		setBounds(100, 100, 740, 445);
		getContentPane().setLayout(null);
		MBD m=new MBD();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(10, 11, 703, 393);
		getContentPane().add(panel);
		
		JLabel lblDocumentoNmero = new JLabel("Documento n\u00FAmero");
		lblDocumentoNmero.setBounds(10, 11, 113, 14);
		panel.add(lblDocumentoNmero);
		
		numeroDocumento = new JTextField();
		numeroDocumento.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				int numero=getNumeroDocumento();
				if(numero!=-1) {
					Pasajero pasajero=CheckIn.consultaPasajero(numero);
					if(pasajero!=null) {
						llenarDatos(pasajero);
					}
				}
			}
		});
		numeroDocumento.setColumns(10);
		numeroDocumento.setBounds(137, 8, 189, 20);
		panel.add(numeroDocumento);
		
		JLabel label_1 = new JLabel("Tipo");
		label_1.setBounds(336, 14, 59, 14);
		panel.add(label_1);
		
		tipoDocumento = new JComboBox<String>();
		tipoDocumento.setBounds(392, 11, 113, 20);
		panel.add(tipoDocumento);
		
		JLabel label_2 = new JLabel("Nombre/s");
		label_2.setBounds(10, 36, 100, 14);
		panel.add(label_2);
		
		nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(137, 33, 189, 20);
		panel.add(nombre);
		
		JLabel label_3 = new JLabel("Apellido");
		label_3.setBounds(10, 61, 100, 14);
		panel.add(label_3);
		
		apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(137, 58, 189, 20);
		panel.add(apellido);
		
		JLabel label_4 = new JLabel("Telefono");
		label_4.setBounds(10, 86, 100, 14);
		panel.add(label_4);
		
		telefono = new JTextField();
		telefono.setColumns(10);
		telefono.setBounds(137, 83, 189, 20);
		panel.add(telefono);
		
		JLabel label_5 = new JLabel("Domicilio");
		label_5.setBounds(10, 189, 100, 14);
		panel.add(label_5);
		
		domicilio = new JTextField();
		domicilio.setColumns(10);
		domicilio.setBounds(137, 186, 189, 20);
		panel.add(domicilio);
		
		JLabel label_6 = new JLabel("Pais");
		label_6.setBounds(10, 217, 100, 14);
		panel.add(label_6);
		
		pais = new JComboBox<String>();
		pais.addItem("");
		m.llenarCombo(pais, "pais_nombre", "pais");
		pais.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED) {
					if(provincia.getItemCount()>0)
						provincia.removeAllItems();
					int id=Direccion.getPaisId(pais.getSelectedItem().toString());
					m.llenarCombo(provincia, "provincia_nombre", "provincia","pais_id="+id);
					provincia.setEnabled(true);
					provincia.addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							if(e.getStateChange()==ItemEvent.SELECTED) {
								if(ciudad.getItemCount()>0)
									ciudad.removeAllItems();
								int id=Direccion.getProvinciaId(provincia.getSelectedItem().toString());
								m.llenarCombo(ciudad, "ciudad_nombre", "ciudad","provincia_id="+id);
								ciudad.setEnabled(true);
							}
						}
					});
				}
			}
		});
		pais.setEditable(true);
		pais.setBounds(137, 214, 113, 20);
		panel.add(pais);
		
		JLabel label_7 = new JLabel("Provincia");
		label_7.setBounds(10, 242, 100, 14);
		panel.add(label_7);
		
		provincia = new JComboBox<String>();
		provincia.setEditable(true);
		provincia.setEnabled(false);
		provincia.setBounds(137, 239, 113, 20);
		panel.add(provincia);
		
		JLabel label_8 = new JLabel("Ciudad");
		label_8.setBounds(10, 267, 100, 14);
		panel.add(label_8);
		
		ciudad = new JComboBox<String>();
		ciudad.setEditable(true);
		ciudad.setEnabled(false);
		ciudad.setBounds(137, 264, 113, 20);
		panel.add(ciudad);
		
		JLabel lblHabitacinNmero = new JLabel("Habitaci\u00F3n n\u00FAmero");
		lblHabitacinNmero.setBounds(10, 292, 113, 14);
		panel.add(lblHabitacinNmero);
		
		habitacionNumero = new JComboBox<String>();
		ManejoHabitacion.getInstancia().llenarComboHabLib(habitacionNumero);
		habitacionNumero.setBounds(137, 289, 113, 20);
		panel.add(habitacionNumero);
		
		JLabel label_10 = new JLabel("Fecha egreso");
		label_10.setBounds(10, 317, 113, 14);
		panel.add(label_10);
		
		egreso = new JDateChooser();
		egreso.setBounds(137, 317, 113, 20);
		panel.add(egreso);
		
		JLabel label_11 = new JLabel("Observaciones:");
		label_11.setBounds(336, 206, 131, 14);
		panel.add(label_11);
		
		observaciones = new JTextArea();
		observaciones.setBounds(336, 231, 349, 114);
		panel.add(observaciones);
		
		confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmar.setEnabled(false);
				if(getEgreso()!=null) {					
					int habitacion=getHabitacionNumero();
					int numeroDocumento=getNumeroDocumento();
					
					GregorianCalendar egreso=new GregorianCalendar();
					egreso.setTimeInMillis(getEgreso().getTime());
					GregorianCalendar nacimiento=new GregorianCalendar();
					nacimiento.setTimeInMillis(getNacimiento().getTime());
					
					Direccion direccion=new Direccion(getDomicilio(),getCiudad(),getProvincia(),getPais());
					Pasajero pasajero=new Pasajero(getTipoDocumento(), numeroDocumento, getNombre(), getApellido(), getTelefono(),
							nacimiento, direccion, getEstadoCivil(), getProfesion(), getObsevaciones());
					
					in=new CheckIn(Pasajero.devuelvePasajero(numeroDocumento),egreso,habitacion);
					in.reguistrarEstadia(pasajero);
					
					JOptionPane.showConfirmDialog(getContentPane(),"Se realizo el check in con exito",
							"Check in",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
					vaciarFormulario();
				}
			}
		});
		confirmar.setBounds(269, 359, 116, 23);
		panel.add(confirmar);
		
		JLabel lblFechaDeNacimeinto = new JLabel("Fecha de nacimiento");
		lblFechaDeNacimeinto.setBounds(10, 111, 131, 14);
		panel.add(lblFechaDeNacimeinto);
		
		nacimiento = new JDateChooser();
		nacimiento.setBounds(137, 108, 113, 20);
		panel.add(nacimiento);
		
		JLabel lblEstadoCivil = new JLabel("Estado civil");
		lblEstadoCivil.setBounds(10, 136, 85, 14);
		panel.add(lblEstadoCivil);
		
		JLabel lblProfesin = new JLabel("Profesi\u00F3n");
		lblProfesin.setBounds(10, 161, 113, 14);
		panel.add(lblProfesin);
		
		profesion = new JTextField();
		profesion.setBounds(137, 158, 189, 20);
		panel.add(profesion);
		profesion.setColumns(10);
		
		estadoCivil = new JComboBox<String>();
		estadoCivil.setModel(new DefaultComboBoxModel<String>(new String[] {"Soltero/a", "Comprometido/a", "En relaci\u00F3n", "Casado/a", "Uni\u00F3n de hecho", "Separado/a", "Divorsiado/a", "Viudo/a"}));
		estadoCivil.setBounds(137, 133, 113, 20);
		panel.add(estadoCivil);
		
		m.llenarCombo(tipoDocumento, "tipo_documento_desc", "tipo_documento");
	}

	public int getNumeroDocumento() {
		String numero=this.numeroDocumento.getText();
		if(numero.length()>0)
			return Integer.parseInt(numero);
		else
			return -1;
	}
	public String getNombre() {
		return this.nombre.getText();
	}
	public String getApellido() {
		return this.apellido.getText();
	}
	public Date getEgreso() {
		return this.egreso.getDate();
	}
	public int getHabitacionNumero() {
		return Integer.parseInt(this.habitacionNumero.getSelectedItem().toString());
	}
	public String getDomicilio() {
		return this.domicilio.getText();
	}
	public String getTipoDocumento() {
		return this.tipoDocumento.getSelectedItem().toString();
	}
	public long getTelefono() {
		return Long.parseLong(this.telefono.getText());
	}
	public String getProvincia() {
		return this.provincia.getSelectedItem().toString();
	}
	public String getPais() {
		return this.pais.getSelectedItem().toString();
	}
	public String getCiudad() {
		return this.ciudad.getSelectedItem().toString();
	}
	public String getObsevaciones() {
		return this.observaciones.getText();
	}
	
	public void llenarDatos(Pasajero p) {
		this.tipoDocumento.setSelectedItem(p.getTipoDocumento());
		this.nombre.setText(p.getNombre());
		this.apellido.setText(p.getApellido());
		this.telefono.setText(""+p.getTelefono());
		this.nacimiento.setDate(new Date(p.getNacimiento().getTimeInMillis()));
		this.estadoCivil.setSelectedItem(p.getEstadoCivil());
		this.profesion.setText(p.getProfesion());
		this.domicilio.setText(p.getDireccion().getDomicilio());
		this.pais.setSelectedItem(p.getDireccion().getPais());
		this.provincia.setSelectedItem(p.getDireccion().getProvincia());
		this.ciudad.setSelectedItem(p.getDireccion().getCiudad());
		this.observaciones.setText(p.getObservaciones());
	}
	public Date getNacimiento() {
		return this.nacimiento.getDate();
	}
	public String getProfesion() {
		return this.profesion.getText();
	}
	public String getEstadoCivil() {
		return this.estadoCivil.getSelectedItem().toString();
	}
	
	private void vaciarFormulario() {
		this.tipoDocumento.setSelectedItem("");
		this.numeroDocumento.setText("");
		this.nombre.setText("");
		this.apellido.setText("");
		this.telefono.setText("");
		this.nacimiento.cleanup();
		this.estadoCivil.setSelectedItem("");
		this.profesion.setText("");
		this.domicilio.setText("");
		this.pais.setSelectedItem("");
		this.provincia.setEnabled(false);
		this.provincia.removeAllItems();
		this.ciudad.setEnabled(false);
		this.ciudad.removeAllItems();
		this.observaciones.setText("");
		ManejoHabitacion.getInstancia().llenarComboHabLib(habitacionNumero);
		this.nacimiento.setDate(new Date());
		this.egreso.setDate(new Date());
	}
}
