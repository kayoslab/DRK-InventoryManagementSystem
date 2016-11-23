package presenter;
import model.PasswordManager;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;


public class SettingsPresenter extends Presenter {
	private PasswordManager passwordManager = new PasswordManager();
	private JButton logo = new JButton("");
	private JButton btnLogout = new JButton("Logout");
	private JButton back = new JButton("");
	private JButton help = new JButton("");
	private JButton btnDatenbankzugangsdatenndern = new JButton("Datenbankzugangsdaten ändern");
	private JButton btnPasswortndern = new JButton("Passwort ändern");

	/**
	 * Launch the application.
	 */
	public void newScreen() {
		super.newScreen();
		EventQueue.invokeLater(() -> {
			try {
				SettingsPresenter window = new SettingsPresenter();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

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

		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		frame.getContentPane().add(logo);
		
		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.addActionListener(this);
		btnLogout.setBounds(455, 27, 98, 22);
		frame.getContentPane().add(btnLogout);
		
		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		back.setIcon (new ImageIcon (imgback));
		back.addActionListener(this);
		back.setBounds(36, 18, 33, 36);
		frame.getContentPane().add(back);

		btnDatenbankzugangsdatenndern.setBounds(247, 258, 280, 57);
		frame.getContentPane().add(btnDatenbankzugangsdatenndern);

		btnPasswortndern.setBounds(247, 331, 280, 57);
		frame.getContentPane().add(btnPasswortndern);
		btnPasswortndern.addActionListener(this);
		
		Image imgbook = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		help.addActionListener(this);
		frame.getContentPane().add(help);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 66, 788, 12);
		frame.getContentPane().add(separator);

		JLabel Einstellungen = new JLabel("Einstellungen");
		Einstellungen.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Einstellungen.setBounds(16, 98, 247, 36);
		frame.getContentPane().add(Einstellungen);
	}


		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == this.btnLogout) {
				LoginPresenter loginPresenter = new LoginPresenter();
				this.frame.dispose();
				loginPresenter.newScreen();
			} else if (e.getSource() == this.back){
				super.showPreviousPresenter();
			} else if (e.getSource() == this.help){

			}
		}

}
