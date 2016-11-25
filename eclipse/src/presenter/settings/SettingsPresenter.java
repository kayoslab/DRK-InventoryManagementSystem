package presenter.settings;
import model.PasswordManager;
import presenter.Presenter;

import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;


public class SettingsPresenter extends Presenter {
	private PasswordManager passwordManager = new PasswordManager();
	private JButton changeDatabaseLoginButton = new JButton("Datenbankzugangsdaten ändern");
	private JButton changePasswordButton = new JButton("Passwort ändern");

	/**
	 * Create the application.
	 */
	public SettingsPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		this.changeDatabaseLoginButton.setBounds(247, 258, 280, 57);
		this.frame.getContentPane().add(this.changeDatabaseLoginButton);

		this.changePasswordButton.setBounds(247, 331, 280, 57);
		this.frame.getContentPane().add(changePasswordButton);
		this.changePasswordButton.addActionListener(this);

		JLabel settings = new JLabel("Einstellungen");
		settings.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		settings.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(settings);
	}

	@Override
	public void presentData() {
		super.presentData();
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
