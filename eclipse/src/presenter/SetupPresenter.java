package presenter;
import model.databaseCommunication.DatabaseLoginManager;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;


public class SetupPresenter extends Presenter {
	private DatabaseLoginManager loginManager = new DatabaseLoginManager();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton logo =  new JButton("");
	private JButton btnSpeichern = new JButton("speichern");

	/**
	 * Create the application.
	 */
	public SetupPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();

		logo.addActionListener(this);
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		frame.getContentPane().add(logo);

		btnSpeichern.setBounds(343, 401, 117, 29);
		frame.getContentPane().add(btnSpeichern);
		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		Image imgbook = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		
		JSeparator separator = new JSeparator();
		separator.setBounds(leftPadding, 66, (width - leftPadding - rightPadding), 12);
		frame.getContentPane().add(separator);
		
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setBounds(281, 230, 61, 16);
		frame.getContentPane().add(lblUrl);
		
		JLabel lblNutzername = new JLabel("Nutzername");
		lblNutzername.setBounds(281, 273, 82, 16);
		frame.getContentPane().add(lblNutzername);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(281, 316, 61, 16);
		frame.getContentPane().add(lblPasswort);
		
		textField = new JTextField();
		textField.setBounds(375, 267, 134, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(375, 224, 134, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(375, 310, 134, 28);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		JLabel Datenbank = new JLabel("Datenbank einrichten");
		Datenbank.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Datenbank.setBounds(16, 98, 247, 36);
		frame.getContentPane().add(Datenbank);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.logo) {

		} else if (e.getSource() == this.btnSpeichern){
			super.showPreviousPresenter();
		}
	}
}
