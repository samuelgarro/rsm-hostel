package vista;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import controlador.CheckOut;
import controlador.CuentaCorriente;
import datos.MBD;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JScrollBar;

public class FormCheckOut extends JInternalFrame {
	private JTextField numeroDocumento;
	private JTextField nombre;
	private JTextField apellido;
	private JComboBox<String> habitacionNumero;
	private JComboBox<String> formaPago;
	private JTextArea observaciones;
	private JPanel informacion;

	/**
	 * Create the frame.
	 */
	public FormCheckOut() {
		setBounds(100, 100, 597, 506);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(10, 11, 561, 454);
		getContentPane().add(panel);
		
		JLabel label = new JLabel("Documento n\u00FAmero");
		label.setBounds(10, 14, 114, 14);
		panel.add(label);
		
		numeroDocumento = new JTextField();
		numeroDocumento.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				int numero=getNumeroDocumento();
				if(numero!=-1) {
					CheckOut out = new CheckOut(numero);
					int[] habitaciones=out.getHabitacionNumero();
					String numHab="";
					nombre.setText(out.getPasajero().getNombre());
					apellido.setText(out.getPasajero().getApellido());
					observaciones.setText(out.getPasajero().getObservaciones());
					for(int i=0;i<habitaciones.length;i++) {
						if(i+1==habitaciones.length)
							numHab+=""+habitaciones[i];
						else
							numHab+=habitaciones[i]+",";
					}
					habitacionNumero.removeAllItems();
					habitacionNumero.addItem(numHab);
					JTable datos=out.informacionEstadia();
					informacion.removeAll();
					informacion.add(datos);
					informacion.setVisible(true);
				}
			}
		});
		numeroDocumento.setColumns(10);
		numeroDocumento.setBounds(134, 11, 196, 20);
		panel.add(numeroDocumento);
		
		JLabel label_1 = new JLabel("Observaciones:");
		label_1.setBounds(10, 141, 114, 14);
		panel.add(label_1);
		
		observaciones = new JTextArea();
		observaciones.setBounds(134, 141, 346, 111);
		panel.add(observaciones);
		
		nombre = new JTextField();
		nombre.setEnabled(false);
		nombre.setColumns(10);
		nombre.setBounds(134, 63, 196, 20);
		panel.add(nombre);
		
		apellido = new JTextField();
		apellido.setEnabled(false);
		apellido.setColumns(10);
		apellido.setBounds(134, 88, 196, 20);
		panel.add(apellido);
		
		JLabel label_2 = new JLabel("Apellido");
		label_2.setBounds(10, 91, 114, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Nombre/s");
		label_3.setBounds(10, 66, 114, 14);
		panel.add(label_3);
		
		JButton confirmar = new JButton("Confirmar");
		confirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CheckOut out=new CheckOut(getNumeroDocumento());				
				String pago=getFormaPago();
				if(pago.equalsIgnoreCase("cuenta corriente")) {
					CuentaCorriente cuenta=new CuentaCorriente(out.getPasajero());
					if(cuenta.consultaAutorizacion()) {
						out.registrarSalida();
						cuenta.modificarSaldo(out.calcularCosto(),CuentaCorriente.PAGAR);
						JOptionPane.showConfirmDialog(getParent(),
								"Se ha registrado la salida del pasajero con exito, el saldo de la cuenta es de: "+cuenta.consultaSaldo()
								,"Check out realizado", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						vaciarFormulario();
					} else {
						JOptionPane.showConfirmDialog(getParent(),
								"El pasajero no posee una cuenta corriente autorizada, primero se deberá autorizar la cuenta",
								"Error",JOptionPane.ERROR_MESSAGE);
					}
				} else {
					out.registrarSalida();
					JOptionPane.showConfirmDialog(getParent(), "Se ha registrado la salida del pasajero con exito",
							"Check out realizado", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
					vaciarFormulario();
				}
			}
		});
		confirmar.setBounds(216, 392, 132, 23);
		panel.add(confirmar);
		
		JLabel label_4 = new JLabel("Forma de pago");
		label_4.setBounds(10, 116, 114, 14);
		panel.add(label_4);
		
		MBD m = new MBD();
		
		formaPago = new JComboBox<String>();
		formaPago.setBounds(134, 113, 120, 20);
		m.llenarCombo(formaPago, "forma_pago_desc", "forma_pago");
		panel.add(formaPago);
		
		JLabel lblHabitacionNumero = new JLabel("habitacion numero");
		lblHabitacionNumero.setBounds(10, 41, 114, 14);
		panel.add(lblHabitacionNumero);
		
		habitacionNumero = new JComboBox<String>();
		habitacionNumero.setBounds(134, 37, 120, 20);
		panel.add(habitacionNumero);
		
		informacion = new JPanel();
		informacion.setBounds(10, 276, 541, 93);
		panel.add(informacion);
	}
	
	private int getNumeroDocumento() {
		String numero=numeroDocumento.getText();
		if(numero.length()>0)
			return Integer.parseInt(numero);
		else
			return -1;
	}
	private String getFormaPago() {
		return formaPago.getSelectedItem().toString();
	}
	
	private void vaciarFormulario() {
		this.numeroDocumento.setText("");
		this.apellido.setText("");
		this.nombre.setText("");
		this.habitacionNumero.removeAllItems();
		this.formaPago.setSelectedItem("");
		this.observaciones.setText("");
		this.informacion.removeAll();
	}
}
