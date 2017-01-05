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

package presenter.settings;
import model.Session;
import presenter.Presenter;
import model.UserManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ChangePasswordPresenter extends Presenter {
	private JPasswordField currentPasswordTextField;
	private JPasswordField passwordTextField;
	private JPasswordField newPasswordTextField;
	private JButton saveButton = new JButton("speichern");
	private UserManager userManager = new UserManager();

	/**
	 * Create the application.
	 */
	public ChangePasswordPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		// initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	public void initialize() {
		super.initialize();
		super.setupTopLayout();
		this.presenterHelpId = 7;
		JLabel changePasswordLabel = new JLabel("Passwort \u00c4ndern");
		changePasswordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		changePasswordLabel.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(changePasswordLabel);

		JLabel oldPasswordLabel = new JLabel("altes Passwort");
		oldPasswordLabel.setBounds(centeredContentAreaX, firstRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(oldPasswordLabel);

		JLabel newPasswordLabel = new JLabel("neues Passwort");
		newPasswordLabel.setBounds(centeredContentAreaX, secondRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(newPasswordLabel);

		JLabel newPasswordAgainLabel = new JLabel("Passwort wiederholen");
		newPasswordAgainLabel.setBounds(centeredContentAreaX, thirdRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(newPasswordAgainLabel);

		this.currentPasswordTextField = new JPasswordField();
		this.currentPasswordTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, firstRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.currentPasswordTextField.setColumns(10);
		this.frame.getContentPane().add(this.currentPasswordTextField);

		this.passwordTextField = new JPasswordField();
		this.passwordTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, secondRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.frame.getContentPane().add(this.passwordTextField);

		this.newPasswordTextField = new JPasswordField();
		this.newPasswordTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, thirdRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.frame.getContentPane().add(this.newPasswordTextField);

		this.saveButton.setBounds(menuButtonX, thirdRowPlacing+setupTextFieldHeight+buttonSpacing, setupButtonWidth, buttonHeight);
		this.frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.saveButton){
			if (String.valueOf(this.passwordTextField.getPassword()).equals(String.valueOf(this.newPasswordTextField.getPassword()))) {
				String currentPasswordToTest = String.valueOf(this.currentPasswordTextField.getPassword());
				if (this.userManager.tryLogin(Session.getSharedInstance().currentUser.username, currentPasswordToTest)) {
					if (this.userManager.setNewPassword(this.session.currentUser.username,
							String.valueOf(currentPasswordTextField.getPassword()), String.valueOf(passwordTextField.getPassword()))) {
						// Password changed.
						this.showPreviousPresenter();
					} else {
						shakeButton();
					}
				} else {
					shakeButton();
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
		final Point point = this.saveButton.getLocation();
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
				saveButton.setLocation(p);
			}
		});
	}
}
