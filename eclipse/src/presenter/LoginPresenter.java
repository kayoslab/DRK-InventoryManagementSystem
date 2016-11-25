package presenter;

import model.PasswordManager;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class LoginPresenter extends Presenter {
	private PasswordManager passwordManager = new PasswordManager();
	private JPasswordField passwordField;
	private JTextField userNameTextField;
	private JButton loginButton = new JButton("Einloggen");


	/**
	 * Create the application.
	 */
	public LoginPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();

		this.loginButton.addActionListener(this);
		this.loginButton.setBounds(356, 477, 117, 29);
		this.frame.getContentPane().add(this.loginButton);

		JLabel usernameLabel = new JLabel("Benutzername");
		usernameLabel.setBounds(261, 352, 88, 16);
		this.frame.getContentPane().add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Passwort");
		passwordLabel.setBounds(261, 403, 61, 16);
		this.frame.getContentPane().add(passwordLabel);

		this.userNameTextField = new JTextField();
		this.userNameTextField.setBounds(383, 346, 182, 28);
		this.frame.getContentPane().add(this.userNameTextField);
		this.userNameTextField.setColumns(10);

		this.passwordField = new JPasswordField();
		this.passwordField.setBounds(383, 397, 182, 28);
		this.frame.getContentPane().add(this.passwordField);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(261, 449, 304, 16);
		this.frame.getContentPane().add(separator);
		
		JLabel logoLabel = new JLabel("");
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoKlein.jpg")).getImage();
		logoLabel.setIcon (new ImageIcon (img));
		logoLabel.setBounds(135, 57, 526, 227);
		this.frame.getContentPane().add(logoLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(262, 306, 303, 16);
		this.frame.getContentPane().add(separator_1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.loginButton) {
			if (this.passwordManager.tryLogin(this.userNameTextField.getText(), String.valueOf(this.passwordField.getPassword()))) {
				MenuPresenter menuPresenter = new MenuPresenter();
				menuPresenter.previousPresenter = this;
				menuPresenter.newScreen();
			}
		}
	}

}
