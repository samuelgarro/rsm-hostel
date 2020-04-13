package vista;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import controlador.CuentaCorriente;
import modelo.Pasajero;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class FormCuentaCorriente extends JInternalFrame {
	private JPanel crear;
	private JPanel estados;
	private JPanel eliminar;
	private final ButtonGroup crearGrupo = new ButtonGroup();
	private JTextField crearSaldo;
	private JTextField crearDocumento;
	private JTextField eliminarDocumento;
	private final ButtonGroup eliminarGrupo = new ButtonGroup();
	private JButton btnEliminar;
	private JButton btnAbrirCuenta;
	private JTextField estadoDocumento;
	private JTextField estadoSaldo;
	private final ButtonGroup estadoGrupo = new ButtonGroup();
	private JSpinner estadoPago;
	private JComboBox<String> estadoEstado;
	private JButton btnActualizarEstado;

	public FormCuentaCorriente() {
		setBounds(100, 100, 547, 428);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setBounds(10, 11, 511, 376);
		getContentPane().add(tabbedPane);
		
		crear = new JPanel();
		tabbedPane.addTab("Nueva cuenta", null, crear, null);
		crear.setLayout(null);
		
		JLabel lblNumeroDocumento = new JLabel("Numero documento");
		lblNumeroDocumento.setBounds(10, 11, 115, 14);
		crear.add(lblNumeroDocumento);
		
		JLabel lblSaldo = new JLabel("Saldo");
		lblSaldo.setBounds(10, 61, 46, 14);
		crear.add(lblSaldo);
		
		JLabel lblTipoDePasajero = new JLabel("Tipo de pasajero");
		lblTipoDePasajero.setBounds(10, 36, 100, 14);
		crear.add(lblTipoDePasajero);
		
		JRadioButton rdbtnEmpresa = new JRadioButton("Empresa");
		crearGrupo.add(rdbtnEmpresa);
		rdbtnEmpresa.setBounds(130, 32, 81, 23);
		rdbtnEmpresa.setActionCommand("empresa");
		crear.add(rdbtnEmpresa);
		
		JRadioButton rdbtnPersona = new JRadioButton("Persona");
		rdbtnPersona.setSelected(true);
		crearGrupo.add(rdbtnPersona);
		rdbtnPersona.setBounds(209, 32, 100, 23);
		rdbtnPersona.setActionCommand("persona");
		crear.add(rdbtnPersona);
		
		crearSaldo = new JTextField();
		crearSaldo.setBounds(130, 58, 155, 20);
		crear.add(crearSaldo);
		crearSaldo.setColumns(10);
		
		crearDocumento = new JTextField();
		crearDocumento.setBounds(130, 8, 155, 20);
		crear.add(crearDocumento);
		crearDocumento.setColumns(10);
		
		btnAbrirCuenta = new JButton("Abrir cuenta");
		btnAbrirCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numeroDocumento = crearDocumento();
				int saldo = crearSaldo();
				if(numeroDocumento!=-1 && saldo!=-1) {
					Pasajero pasajero=Pasajero.devuelvePasajero(numeroDocumento);
					CuentaCorriente cuenta=new CuentaCorriente(pasajero);
					cuenta.asignarCuentaCorriente();
					cuenta.modificarSaldo(crearSaldo(),CuentaCorriente.PAGAR);
					JOptionPane.showConfirmDialog(getContentPane(), "Se ha autorizado al pasajero a pagar en cuenta corriente, su saldo actual es: "
							+ saldo, "Nueva cuenta creada",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnAbrirCuenta.setBounds(122, 117, 131, 23);
		crear.add(btnAbrirCuenta);
		
		estados = new JPanel();
		tabbedPane.addTab("Estado de cuentas", null, estados, null);
		estados.setLayout(null);
		
		JLabel label_2 = new JLabel("Numero documento");
		label_2.setBounds(10, 14, 115, 14);
		estados.add(label_2);
		
		estadoDocumento = new JTextField();
		estadoDocumento.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				int numero=estadoDocumento();
				if(numero!=-1) {
					Pasajero pasajero=Pasajero.devuelvePasajero(numero);
					CuentaCorriente cuenta=new CuentaCorriente(pasajero);
					
					if(cuenta.consultaAutorizacion())
						estadoEstado.setSelectedItem("Activa");
					else
						estadoEstado.setSelectedItem("Inactiva");
	
					estadoSaldo.setText(""+cuenta.consultaSaldo());
				}
			}
		});
		estadoDocumento.setColumns(10);
		estadoDocumento.setBounds(130, 11, 155, 20);
		estados.add(estadoDocumento);
		
		JLabel label_3 = new JLabel("Tipo de pasajero");
		label_3.setBounds(10, 39, 100, 14);
		estados.add(label_3);
		
		JRadioButton estadoEmpresa = new JRadioButton("Empresa");
		estadoGrupo.add(estadoEmpresa);
		estadoEmpresa.setBounds(130, 35, 81, 23);
		estadoEmpresa.setActionCommand("empresa");
		estados.add(estadoEmpresa);
		
		JRadioButton estadoPersona = new JRadioButton("Persona");
		estadoGrupo.add(estadoPersona);
		estadoPersona.setSelected(true);
		estadoPersona.setBounds(209, 35, 100, 23);
		estadoPersona.setActionCommand("persona");
		estados.add(estadoPersona);
		
		JLabel lblPago = new JLabel("Pago");
		lblPago.setBounds(10, 64, 46, 14);
		estados.add(lblPago);
		
		btnActualizarEstado = new JButton("Actualizar estado");
		btnActualizarEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pasajero pasajero=Pasajero.devuelvePasajero(estadoDocumento());
				CuentaCorriente cuenta=new CuentaCorriente(pasajero);
				if(estadoPago()>0)
					cuenta.modificarSaldo(estadoPago(),CuentaCorriente.SALDAR);
				if(cuenta.consultaAutorizacion()) {
					if(estadoEstado().equalsIgnoreCase("inactiva")) {
						cuenta.eliminarAutorizacion();
					}
				}else {
					if(estadoEstado().equalsIgnoreCase("activa")) {
						cuenta.habilitar();
					}
				}
				JOptionPane.showConfirmDialog(getContentPane(), "Se ha actualizado el estado de cuenta, se encuentra "
						+ estadoEstado() + " con un saldo de $" +cuenta.consultaSaldo(),"Actualizacion de estado de cuenta",
						JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnActualizarEstado.setBounds(121, 144, 164, 23);
		estados.add(btnActualizarEstado);
		
		estadoPago = new JSpinner();
		estadoPago.setBounds(130, 61, 100, 20);
		estados.add(estadoPago);
		
		JLabel lblEstado = new JLabel("Estado");
		lblEstado.setBounds(10, 89, 46, 14);
		estados.add(lblEstado);
		
		JLabel lblSaldo_1 = new JLabel("Saldo actual");
		lblSaldo_1.setBounds(10, 114, 89, 14);
		estados.add(lblSaldo_1);
		
		estadoEstado = new JComboBox<String>();
		estadoEstado.setBounds(130, 86, 100, 20);
		estadoEstado.addItem("Activa");
		estadoEstado.addItem("Inactiva");
		estadoEstado.addItem("");
		estados.add(estadoEstado);
		
		estadoSaldo = new JTextField();
		estadoSaldo.setEnabled(false);
		estadoSaldo.setEditable(false);
		estadoSaldo.setBounds(130, 114, 100, 20);
		estados.add(estadoSaldo);
		estadoSaldo.setColumns(10);
		
		eliminar = new JPanel();
		tabbedPane.addTab("Eliminar cuenta", null, eliminar, null);
		eliminar.setLayout(null);
		
		JLabel label = new JLabel("Numero documento");
		label.setBounds(10, 14, 115, 14);
		eliminar.add(label);
		
		eliminarDocumento = new JTextField();
		eliminarDocumento.setColumns(10);
		eliminarDocumento.setBounds(130, 11, 155, 20);
		eliminar.add(eliminarDocumento);
		
		JLabel label_1 = new JLabel("Tipo de pasajero");
		label_1.setBounds(10, 39, 100, 14);
		eliminar.add(label_1);
		
		JRadioButton eliminarEmpresa = new JRadioButton("Empresa");
		eliminarGrupo.add(eliminarEmpresa);
		eliminarEmpresa.setBounds(130, 35, 81, 23);
		eliminarEmpresa.setActionCommand("empresa");
		eliminar.add(eliminarEmpresa);
		
		JRadioButton eliminarPersona = new JRadioButton("Persona");
		eliminarGrupo.add(eliminarPersona);
		eliminarPersona.setSelected(true);
		eliminarPersona.setBounds(209, 35, 100, 23);
		eliminarPersona.setActionCommand("persona");
		eliminar.add(eliminarPersona);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pasajero pasajero=Pasajero.devuelvePasajero(eliminarDocumento());
				CuentaCorriente cuenta=new CuentaCorriente(pasajero);
				cuenta.eliminarAutorizacion();
				JOptionPane.showConfirmDialog(getContentPane(), "Se ha eliminado la autorizacion de pagar en cuenta corriente",
						"Autorizacion eliminada", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnEliminar.setBounds(130, 92, 131, 23);
		eliminar.add(btnEliminar);

	}
	
	private int crearDocumento() {
		return Integer.parseInt(crearDocumento.getText());
	}
	
	private int crearSaldo() {
		String saldo=crearSaldo.getText();
		if(saldo.length()>0)
			return Integer.parseInt(saldo);
		else
			return -1;
	}
	
	private String crearTipoPasajero() {
		return crearGrupo.getSelection().getActionCommand();
	}
	
	private int estadoDocumento() {
		String numero = estadoDocumento.getText();
		if(numero.length()>0)
			return Integer.parseInt(numero);
		else
			return -1;
	}
	
	private String estadoTipoPasajero() {
		return estadoGrupo.getSelection().getActionCommand();
	}
	
	private int estadoPago() {
		return (int)estadoPago.getValue();
	}
	
	private String estadoEstado() {
		return estadoEstado.getSelectedItem().toString();
	}
	
	private int estadoSaldo() {
		return Integer.parseInt(estadoSaldo.getText());
	}
	
	private int eliminarDocumento() {
		return Integer.parseInt(eliminarDocumento.getText());
	}
	
	private String eliminarTipoPasajero() {
		return eliminarGrupo.getSelection().getActionCommand();
	}
}
