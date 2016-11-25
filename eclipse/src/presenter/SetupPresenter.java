package presenter;
import model.databaseCommunication.DatabaseLoginManager;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;


public class SetupPresenter extends Presenter {
	private DatabaseLoginManager loginManager = new DatabaseLoginManager();
	private JTextField usernameTextField;
	private JTextField urlTextField;
	private JTextField passwordTextField;
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

		urlTextField = new JTextField();
		urlTextField.setBounds(375, 224, 134, 28);
		frame.getContentPane().add(urlTextField);
		urlTextField.setColumns(10);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(375, 267, 134, 28);
		frame.getContentPane().add(usernameTextField);
		usernameTextField.setColumns(10);
		
		passwordTextField = new JTextField();
		passwordTextField.setBounds(375, 310, 134, 28);
		frame.getContentPane().add(passwordTextField);
		passwordTextField.setColumns(10);
		
		JLabel databaseLabel = new JLabel("Datenbank einrichten");
		databaseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		databaseLabel.setBounds(16, 98, 247, 36);
		frame.getContentPane().add(databaseLabel);
	}

	@Override
	public void presentData() {
		super.presentData();
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.logo) {

		} else if (e.getSource() == this.btnSpeichern){
			super.showPreviousPresenter();
		}
	}
}
