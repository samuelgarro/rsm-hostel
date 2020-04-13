package vista;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.Usuarios;
import datos.MBD;
import modelo.Usuario;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField fUsuario;
	private JLabel lblhasOlvidadoLa;
	private Evento click;
	private JButton btnIngresar;
	private JPasswordField fContrasenia;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MBD.iniciarXampp();
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 363, 217);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fUsuario = new JTextField();
		fUsuario.setBounds(86, 11, 204, 20);
		contentPane.add(fUsuario);
		fUsuario.setColumns(10);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(10, 11, 66, 20);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(10, 56, 66, 20);
		contentPane.add(lblContrasea);
		
		click=new Evento();
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.addMouseListener(click);
		btnIngresar.setBounds(147, 102, 89, 23);
		contentPane.add(btnIngresar);
		
		lblhasOlvidadoLa = new JLabel("Recuperar contrase\u00F1a");
		lblhasOlvidadoLa.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblhasOlvidadoLa.addMouseListener(click);
		lblhasOlvidadoLa.setBounds(136, 136, 139, 14);
		contentPane.add(lblhasOlvidadoLa);
		
		fContrasenia = new JPasswordField();
		fContrasenia.setBounds(86, 56, 204, 20);
		contentPane.add(fContrasenia);
	}

	private class Evento implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(btnIngresar)) {
				String usuario = fUsuario.getText().trim();
				String contrasenia=new String(fContrasenia.getPassword());
				if(usuario.length()<6) {
					JOptionPane.showMessageDialog(null, "Nombre de usuario mal intorducido", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					Usuarios u=new Usuarios();							
					if(u.login(usuario, contrasenia)){
						Interfaz ventana=new Interfaz(u);
						ventana.setVisible(true);
						dispose();
					} else {
						JOptionPane.showConfirmDialog(getParent(), "Error al introducir el usuario o la contraseña",
								"Error al iniciar seción", JOptionPane.DEFAULT_OPTION,JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
