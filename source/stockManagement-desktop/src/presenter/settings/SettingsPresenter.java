package presenter.settings;
import model.Session;
import model.UserManager;
import presenter.Presenter;
import presenter.onboarding.SetupPresenter;

import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;


public class SettingsPresenter extends Presenter {
	private UserManager userManager = new UserManager();
	private JButton changeDatabaseLoginButton = new JButton("Datenbankzugangsdaten \u00e4ndern");
	private JButton changePasswordButton = new JButton("Passwort \u00e4ndern");

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

		JLabel settings = new JLabel("Einstellungen");
		settings.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		settings.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(settings);

		this.changeDatabaseLoginButton.setBounds(menuButtonX, firstButtonPlacing, menuButtonWidth, menuButtonHeight);
		this.frame.getContentPane().add(this.changeDatabaseLoginButton);
		this.changeDatabaseLoginButton.addActionListener(this);

		this.changePasswordButton.setBounds(menuButtonX, secondButtonPlacing, menuButtonWidth, menuButtonHeight);
		this.frame.getContentPane().add(changePasswordButton);
		this.changePasswordButton.addActionListener(this);
		this.changePasswordButton.setEnabled(false);

		if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editSelf)) {
			this.changePasswordButton.setEnabled(true);
		}
	}

	@Override
	public void presentData() {
		super.presentData();
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.changeDatabaseLoginButton) {
			SetupPresenter setupPresenter = new SetupPresenter(this);
			setupPresenter.newScreen();
		} else if (e.getSource() == this.changePasswordButton){
			ChangePasswordPresenter changePasswordPresenter = new ChangePasswordPresenter(this);
			changePasswordPresenter.newScreen();
		}
	}
}
