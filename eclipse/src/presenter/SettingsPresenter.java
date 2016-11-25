package presenter;
import model.PasswordManager;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;


public class SettingsPresenter extends Presenter {
	private PasswordManager passwordManager = new PasswordManager();
	private JButton btnDatenbankzugangsdatenndern = new JButton("Datenbankzugangsdaten ändern");
	private JButton btnPasswortndern = new JButton("Passwort ändern");

	/**
	 * Create the application.
	 */
	public SettingsPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		btnDatenbankzugangsdatenndern.setBounds(247, 258, 280, 57);
		frame.getContentPane().add(btnDatenbankzugangsdatenndern);

		btnPasswortndern.setBounds(247, 331, 280, 57);
		frame.getContentPane().add(btnPasswortndern);
		btnPasswortndern.addActionListener(this);

		JLabel Einstellungen = new JLabel("Einstellungen");
		Einstellungen.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Einstellungen.setBounds(16, 98, 247, 36);
		frame.getContentPane().add(Einstellungen);
	}

	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
