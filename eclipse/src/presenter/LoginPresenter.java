package presenter;
import model.PasswordManager;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPresenter extends Presenter {
	private PasswordManager passwordManager = new PasswordManager();
	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPresenter window = new LoginPresenter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblBenutzername = new JLabel("Benutzername");
		lblBenutzername.setBounds(261, 352, 88, 16);
		frame.getContentPane().add(lblBenutzername);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(261, 403, 61, 16);
		frame.getContentPane().add(lblPasswort);
		
		textField = new JTextField();
		textField.setBounds(383, 346, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(383, 397, 182, 28);
		frame.getContentPane().add(passwordField);
		
		JButton btnEinloggen = new JButton("Einloggen");
		btnEinloggen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == e){
					// TODO: Login Abfragen
				}
			}
		});
		btnEinloggen.setBounds(356, 477, 117, 29);
		frame.getContentPane().add(btnEinloggen);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.WHITE);
		separator.setBackground(Color.LIGHT_GRAY);
		separator.setBounds(261, 449, 304, 16);
		frame.getContentPane().add(separator);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon (this.getClass().getResource("/DRK-LogoKlein.jpg")).getImage();
		lblNewLabel.setIcon (new ImageIcon (img));
		lblNewLabel.setBounds(135, 57, 526, 227);
		frame.getContentPane().add(lblNewLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.WHITE);
		separator_1.setBackground(Color.LIGHT_GRAY);
		separator_1.setBounds(262, 306, 303, 16);
		frame.getContentPane().add(separator_1);
	}

}
