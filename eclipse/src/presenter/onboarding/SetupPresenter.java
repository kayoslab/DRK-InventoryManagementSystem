package presenter.onboarding;
import model.databaseCommunication.DatabaseLoginManager;
import presenter.Presenter;

import java.awt.event.ActionEvent;
import java.awt.*;
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
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public SetupPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();

		this.logo = new JButton("");
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		this.logo.setIcon (new ImageIcon (img));
		this.logo.setBounds(logoX, topPadding, logoWidth, logoHeight);
		this.frame.getContentPane().add(this.logo);
		this.logo.addActionListener(this);

		this.separator = new JSeparator();
		this.separator.setBounds(leftPadding, topPadding+logoHeight+1, (width - leftPadding - rightPadding), smallSpacing);
		this.frame.getContentPane().add(this.separator);

		this.btnSpeichern = new JButton("speichern");
		this.btnSpeichern.setBounds(343, 401, 117, 29);
		this.btnSpeichern.addActionListener(this);
		this.frame.getContentPane().add(this.btnSpeichern);
		
		JLabel lblUrl = new JLabel("URL");
		lblUrl.setBounds(281, 230, 61, 16);
		this.frame.getContentPane().add(lblUrl);
		
		JLabel lblNutzername = new JLabel("Nutzername");
		lblNutzername.setBounds(281, 273, 82, 16);
		this.frame.getContentPane().add(lblNutzername);
		
		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(281, 316, 61, 16);
		this.frame.getContentPane().add(lblPasswort);

		this.urlTextField = new JTextField();
		this.urlTextField.setBounds(375, 224, 134, 28);
		this.frame.getContentPane().add(this.urlTextField);
		this.urlTextField.setColumns(10);

		this.usernameTextField = new JTextField();
		this.usernameTextField.setBounds(375, 267, 134, 28);
		this.frame.getContentPane().add(this.usernameTextField);
		this.usernameTextField.setColumns(10);

		this.passwordTextField = new JPasswordField();
		this.passwordTextField.setBounds(375, 310, 134, 28);
		this.frame.getContentPane().add(this.passwordTextField);
		this.passwordTextField.setColumns(10);
		
		JLabel databaseLabel = new JLabel("Datenbank einrichten");
		databaseLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		databaseLabel.setBounds(16, 98, 247, 36);
		this.frame.getContentPane().add(databaseLabel);
	}

	@Override
	public void presentData() {
		super.presentData();
		this.urlTextField.setText(this.loginManager.getURL());
		this.usernameTextField.setText(this.loginManager.getUsername());
		this.passwordTextField.setText(this.loginManager.getPassword());
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.logo) {

		} else if (e.getSource() == this.btnSpeichern){
			this.loginManager = new DatabaseLoginManager(this.usernameTextField.getText(),  String.valueOf(this.passwordTextField.getPassword()), this.urlTextField.getText());
			if (loginManager.testDatabaseConnection()) {
				System.out.println("Database Connection established.");
				LoginPresenter loginPresenter = new LoginPresenter(this);
				loginPresenter.newScreen();
			} else {
				this.shakeButton();
			}
		}
	}


	/**
	 * Make the Button Shake
	 */
	private void shakeButton() {
		final Point point = this.btnSpeichern.getLocation();
		final int delay = 75;
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
