package vista;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Usuarios;
import datos.MBD;
import modelo.Usuario;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JDesktopPane;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Interfaz extends JFrame {

	private JPanel contentPane;
	private JButton btnCheckIn;
	private JButton btnCheckOut;
	private JButton btnReserva;
	private JButton btnStockDeProductos;
	private JButton btnCuentasCorrientes;
	private JInternalFrame ventanaActiva;
	private JDesktopPane desktopPane;
	
	/**
	 * Create the frame.
	 */
	public Interfaz(Usuarios u) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 600); // pantalla maximizada
		setExtendedState(JFrame.MAXIMIZED_BOTH);//pantalla completa
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCuenta = new JMenu("Cuenta");
		menuBar.add(mnCuenta);
		
		JMenuItem mntmAdministrarUsuarios = new JMenuItem("Administrar usuarios");
		mnCuenta.add(mntmAdministrarUsuarios);
		
		JMenuItem mntmCambiarDeUsuario = new JMenuItem("Cambiar de usuario");
		mnCuenta.add(mntmCambiarDeUsuario);
		
		JMenu mnHabitacion=new JMenu("Habitación");
		menuBar.add(mnHabitacion);
		
		JMenuItem mntmAjustarPrecios=new JMenuItem("Ajustar precios");
		mnHabitacion.add(mntmAjustarPrecios);
		
		JMenuItem mntmCambiarTipo = new JMenuItem("Cambiar tipo");
		mnHabitacion.add(mntmCambiarTipo);
		
		JMenuItem mntmCambiarEstado = new JMenuItem("Cambiar estado");
		mnHabitacion.add(mntmCambiarEstado);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setLocation(0, 0);
		desktopPane.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		contentPane.add(desktopPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setLocation(0, 0);
		toolBar.setSize((int)desktopPane.getSize().getWidth(),40);
		toolBar.setFloatable(false);
		desktopPane.add(toolBar);
		
		AbrirVentana eventoClick=new AbrirVentana();
		
		btnCheckIn = new JButton("Check in");
		btnCheckIn.addActionListener(eventoClick);
		toolBar.add(btnCheckIn);
		
		btnCheckOut = new JButton("Check out");
		btnCheckOut.addActionListener(eventoClick);
		toolBar.add(btnCheckOut);
		
		btnReserva = new JButton("Reserva");
		btnReserva.addActionListener(eventoClick);
		toolBar.add(btnReserva);
		
		btnStockDeProductos = new JButton("Stock de productos");
		btnStockDeProductos.addActionListener(eventoClick);
		toolBar.add(btnStockDeProductos);
		
		if(u.getRol()==Usuario.GERENETE) {
			btnCuentasCorrientes = new JButton("Cuentas corrientes");
			btnCuentasCorrientes.addActionListener(eventoClick);
			//btnCuentasCorrientes.setIcon(new ImageIcon("direccion"));
			toolBar.add(btnCuentasCorrientes);
		}
				
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				MBD.finalizarXampp();
				System.exit(0);
			}
		});
	}
	
	private class AbrirVentana implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(ventanaActiva!=null) {
				try {
					ventanaActiva.setClosed(true);
				} catch (PropertyVetoException ex) {
					ex.printStackTrace();
				}
			}
			if(e.getSource().equals(btnCheckIn))
				ventanaActiva=new FormCheckIn();
			else if(e.getSource().equals(btnCheckOut))
				ventanaActiva=new FormCheckOut();
			else if (e.getSource().equals(btnReserva))
				ventanaActiva=new FormReserva();
			else if(e.getSource().equals(btnCuentasCorrientes))
				ventanaActiva=new FormCuentaCorriente();
			else if(e.getSource().equals(btnStockDeProductos))
				ventanaActiva=new FormControlStock();
			
			
			ventanaActiva.setClosable(true);
			ventanaActiva.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
			ventanaActiva.setVisible(true);
			desktopPane.add(ventanaActiva);
			desktopPane.setVisible(true);
		}
	}
}
