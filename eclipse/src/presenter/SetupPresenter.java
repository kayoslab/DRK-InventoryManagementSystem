package presenter;
import model.databaseCommunication.DatabaseLoginManager;
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

		logo.addActionListener(this);
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		frame.getContentPane().add(logo);

		this.btnSpeichern = new JButton("speichern");
		this.btnSpeichern.setBounds(343, 401, 117, 29);
		this.btnSpeichern.addActionListener(this);
		this.frame.getContentPane().add(this.btnSpeichern);
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
		
		passwordTextField = new JPasswordField();
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
