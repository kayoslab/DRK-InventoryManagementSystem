/*
 * Copyright (c) - All Rights Reserved
 *
 * Unauthorized copying of these files, via any medium is
 * strictly prohibited Proprietary and confidential
 *
 * NOTICE:
 * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
 * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
 * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained by the owner.
 *
 * @author Amina
 * @author Sabine
 *
 */

package presenter.onboarding;
import model.LogManager;
import model.Session;
import model.UserManager;
import presenter.MenuPresenter;
import presenter.Presenter;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class LoginPresenter extends Presenter {
	private UserManager userManager = new UserManager();
	private JPasswordField passwordField;
	private JTextField userNameTextField;
	private JButton loginButton = new JButton("Einloggen");


	/**
	 * Create the application.
	 */
	public LoginPresenter() {
		this.initialize();
	}

	/**
	 * Create the application.
	 */
	public LoginPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	public void initialize() {
		super.initialize();
		this.presenterHelpId = 2;
		this.loginButton.addActionListener(this);
		this.loginButton.setBounds(356, 477, 117, 29);
		this.frame.getContentPane().add(this.loginButton);
		this.frame.getRootPane().setDefaultButton(this.loginButton);

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
			if (this.userManager.tryLogin(this.userNameTextField.getText(), String.valueOf(this.passwordField.getPassword()))) {
				this.userManager.loginProceeded(this.userNameTextField.getText());
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.login)) {
					LogManager.writeLogMessage(LogManager.Operation.login);
					MenuPresenter menuPresenter = new MenuPresenter(this);
					menuPresenter.newScreen();
				} else {
					shakeButton();
					this.session.invalidateSession();
				}
			} else {
				shakeButton();
			}
		}
	}

	/**
	 * Make the Button Shake
	 */
	private void shakeButton() {
		final Point point = this.loginButton.getLocation();
		final int delay = 50;
		Runnable r = () -> {
			for (int i = 0; i < 2; i++) {
				try {
					moveButton(new Point(point.x + 5, point.y));
					Thread.sleep(delay);
					moveButton(point);
					Thread.sleep(delay);
					moveButton(new Point(point.x - 5, point.y));
					Thread.sleep(delay);
					moveButton(point);
					Thread.sleep(delay);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

	private void moveButton(final Point p) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				loginButton.setLocation(p);
			}
		});
	}

}
