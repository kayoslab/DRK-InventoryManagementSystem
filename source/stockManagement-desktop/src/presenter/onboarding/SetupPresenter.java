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
import model.Session;
import model.databaseCommunication.DatabaseLoginManager;
import presenter.Presenter;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;


public class SetupPresenter extends Presenter {
	private DatabaseLoginManager loginManager = new DatabaseLoginManager();
	private JTextField usernameTextField;
	private JTextField urlTextField;
	private JPasswordField passwordTextField;
	private JButton logo =  new JButton("");
	private JButton btnSpeichern;

	/**
	 * Create the application.
	 */
	public SetupPresenter() {
		this.initialize();
	}

	/**
	 * Create the application.
	 */
	public SetupPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@Override
	public void initialize() {
		super.initialize();
		if (this.previousPresenter != null) {
			// Show total TopLayout with backButton if this Presenter is defined by SettingsPresenter.
			super.setupTopLayout();
			this.presenterHelpId = 7;
			JLabel databaseLabel = new JLabel("Datenbankzugangsdaten \u00e4ndern");
			databaseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			databaseLabel.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
			this.frame.getContentPane().add(databaseLabel);
		} else {
			this.presenterHelpId = 1;
			// If this is the inital Presenter don't display any Buttons.
			this.logo = new JButton("");
			Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
			this.logo.setIcon (new ImageIcon (img));
			this.logo.setBounds(logoX, topPadding, logoWidth, logoHeight);
			this.frame.getContentPane().add(this.logo);
			this.logo.addActionListener(this);

			this.separator = new JSeparator();
			this.separator.setBounds(leftPadding, topPadding+logoHeight+1, (width - leftPadding - rightPadding), smallSpacing);
			this.frame.getContentPane().add(this.separator);

			JLabel databaseLabel = new JLabel("Datenbank einrichten");
			databaseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
			databaseLabel.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
			this.frame.getContentPane().add(databaseLabel);
		}

		JLabel lblUrl = new JLabel("URL");
		lblUrl.setBounds(centeredContentAreaX, firstRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(lblUrl);

		JLabel lblNutzername = new JLabel("Nutzername");
		lblNutzername.setBounds(centeredContentAreaX, secondRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(lblNutzername);

		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(centeredContentAreaX, thirdRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(lblPasswort);

		this.urlTextField = new JTextField();
		this.urlTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, firstRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.frame.getContentPane().add(this.urlTextField);
		this.urlTextField.setColumns(10);

		this.usernameTextField = new JTextField();
		this.usernameTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, secondRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.frame.getContentPane().add(this.usernameTextField);
		this.usernameTextField.setColumns(10);

		this.passwordTextField = new JPasswordField();
		this.passwordTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, thirdRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.frame.getContentPane().add(this.passwordTextField);
		this.passwordTextField.setColumns(10);

		this.btnSpeichern = new JButton("speichern");
		this.btnSpeichern.setBounds(menuButtonX, thirdRowPlacing+setupTextFieldHeight+buttonSpacing, setupButtonWidth, buttonHeight);
		this.btnSpeichern.addActionListener(this);
		this.frame.getContentPane().add(this.btnSpeichern);
	}

	@Override
	public void presentData() {
		super.presentData();
		this.urlTextField.setText(this.loginManager.getURL());
		this.usernameTextField.setText(this.loginManager.getUsername());
		this.passwordTextField.setText(this.loginManager.getPassword());
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.btnSpeichern){
			this.loginManager = new DatabaseLoginManager(this.usernameTextField.getText(),  String.valueOf(this.passwordTextField.getPassword()), this.urlTextField.getText());
			if (this.previousPresenter == null) {
				if (this.loginManager.testDatabaseConnection()) {
					System.out.println("Database Connection established.");
					LoginPresenter loginPresenter = new LoginPresenter(this);
					loginPresenter.newScreen();
				} else {
					this.shakeButton();
				}
			} else {
				Session.getSharedInstance().invalidateSession();
				DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
				if (dbloginManager.testDatabaseConnection()) {
					System.out.println("Database Connection established.");
					LoginPresenter loginPresenter = new LoginPresenter();
					loginPresenter.newScreen();
				} else {
					System.out.println("Can't connect to database using the given credentials.");
					SetupPresenter setupPresenter = new SetupPresenter();
					setupPresenter.newScreen();
				}
				this.frame.dispose();
			}
		}
		if (this.previousPresenter == null) {
			if (e.getSource() == this.logo) {
				String urlString = "http://drk-sennestadt.de/";
				try {
					java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlString));
				} catch (IOException openException) {
					System.out.println("Cant open");
				}
			}
		}
	}


	/**
	 * Make the Button Shake
	 */
	private void shakeButton() {
		final Point point = this.btnSpeichern.getLocation();
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
				btnSpeichern.setLocation(p);
			}
		});
	}
}
