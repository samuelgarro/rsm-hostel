package vista;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.CardLayout;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import com.toedter.calendar.JDateChooser;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class Informes extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Informes frame = new Informes();
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
	public Informes() {
		setBounds(100, 100, 847, 543);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(0, 0, 821, 502);
		getContentPane().add(panel);
		panel.setLayout(new CardLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(tabbedPane, "name_730046635824300");
		
		JPanel panelOcupacion = new JPanel();
		panelOcupacion.setLayout(null);
		tabbedPane.addTab("Ocupación", null, panelOcupacion, null);
		
		JRadioButton radioButton = new JRadioButton("Ocupadas");
		radioButton.setSelected(true);
		radioButton.setBounds(19, 39, 92, 23);
		panelOcupacion.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("Libres");
		radioButton_1.setBounds(113, 39, 92, 23);
		panelOcupacion.add(radioButton_1);
		
		JRadioButton radioButton_2 = new JRadioButton("Reservadas");
		radioButton_2.setBounds(207, 39, 117, 23);
		panelOcupacion.add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("Todos");
		radioButton_3.setBounds(326, 39, 92, 23);
		panelOcupacion.add(radioButton_3);
		
		JLabel label = new JLabel("Estado de habitaci\u00F3n:");
		label.setBounds(10, 11, 174, 14);
		panelOcupacion.add(label);
		
		JLabel label_1 = new JLabel("Desde");
		label_1.setBounds(10, 79, 39, 14);
		panelOcupacion.add(label_1);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(49, 78, 87, 20);
		panelOcupacion.add(dateChooser);
		
		JLabel label_2 = new JLabel("Hasta");
		label_2.setBounds(169, 79, 46, 14);
		panelOcupacion.add(label_2);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(207, 78, 87, 20);
		panelOcupacion.add(dateChooser_1);
		
		JButton button = new JButton("Buscar");
		button.setBounds(245, 140, 89, 23);
		panelOcupacion.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 201, 649, 279);
		panelOcupacion.add(scrollPane);
		
		JPanel panelPasajero = new JPanel();
		panelPasajero.setLayout(null);
		tabbedPane.addTab("Informes Pasajeros", null, panelPasajero, null);
		
		JLabel label_3 = new JLabel("N\u00FAmero de documento");
		label_3.setBounds(10, 11, 132, 14);
		panelPasajero.add(label_3);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(152, 5, 185, 20);
		panelPasajero.add(textField);
		
		JLabel label_4 = new JLabel("Nombre");
		label_4.setBounds(10, 36, 132, 14);
		panelPasajero.add(label_4);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(152, 30, 185, 20);
		panelPasajero.add(textField_1);
		
		JLabel label_5 = new JLabel("Apellido");
		label_5.setBounds(10, 61, 132, 14);
		panelPasajero.add(label_5);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(152, 55, 185, 20);
		panelPasajero.add(textField_2);
		
		JLabel label_6 = new JLabel("Pais");
		label_6.setBounds(10, 100, 46, 14);
		panelPasajero.add(label_6);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(43, 97, 85, 20);
		panelPasajero.add(comboBox);
		
		JLabel label_7 = new JLabel("Provincia");
		label_7.setBounds(150, 100, 57, 14);
		panelPasajero.add(label_7);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setEnabled(false);
		comboBox_1.setBounds(206, 97, 85, 20);
		panelPasajero.add(comboBox_1);
		
		JLabel label_8 = new JLabel("Ciudad");
		label_8.setBounds(314, 100, 57, 14);
		panelPasajero.add(label_8);
		
		JComboBox<String> comboBox_2 = new JComboBox<String>();
		comboBox_2.setEnabled(false);
		comboBox_2.setBounds(370, 97, 85, 20);
		panelPasajero.add(comboBox_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(43, 205, 625, 275);
		panelPasajero.add(scrollPane_1);
		
		JButton button_1 = new JButton("Buscar");
		button_1.setBounds(271, 157, 89, 23);
		panelPasajero.add(button_1);

	}

}
