package presenter;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;


public class MenuPresenter extends Presenter {
	private JButton btnMaterialGerteDaten = new JButton("Material-/ Geräte Daten");
	private JButton btnInventarliste = new JButton("Inventarliste");
	private JButton btnMeldungen = new JButton("Meldungen");	
	private JButton settingsButton = new JButton("Einstellungen");
	private JButton btnLogout = new JButton("Logout");
	private JButton chat = new JButton("");
	private JButton edit = new JButton("");
	private JButton plus = new JButton("");
	private JButton help = new JButton("");

	/**
	 * Create the application.
	 */
	public MenuPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();

		JButton logo = new JButton("");
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		frame.getContentPane().add(logo);

		btnMaterialGerteDaten.setBounds(278, 361, 234, 57);
		frame.getContentPane().add(btnMaterialGerteDaten);
		btnMaterialGerteDaten.addActionListener(this);
		
		btnInventarliste.setBackground(Color.WHITE);
		btnInventarliste.setBounds(278, 200, 234, 57);
		frame.getContentPane().add(btnInventarliste);
		btnInventarliste.addActionListener(this);

		btnMeldungen.setBounds(278, 284, 234, 51);
		frame.getContentPane().add(btnMeldungen);
		btnMeldungen.addActionListener(this);

		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.addActionListener(this);
		btnLogout.setBounds(442, 6, 98, 22);
		frame.getContentPane().add(btnLogout);

		settingsButton.addActionListener(this);
		settingsButton.setBounds(442, 40, 98, 22);
		frame.getContentPane().add(settingsButton);

		Image imgchat = new ImageIcon (this.getClass().getResource("/img/chat-button.jpg")).getImage();
		chat.setIcon (new ImageIcon (imgchat));
		chat.setBounds(155, 18, 33, 36);
		frame.getContentPane().add(chat);

		Image imgedit = new ImageIcon (this.getClass().getResource("/img/edit-button.jpg")).getImage();
		edit.setIcon (new ImageIcon (imgedit));
		edit.setBounds(215, 18, 33, 36);
		frame.getContentPane().add(edit);

		Image imgplus = new ImageIcon (this.getClass().getResource("/img/add-circle-1.jpg")).getImage();
		plus.setIcon (new ImageIcon (imgplus));
		plus.setBounds(278, 18, 33, 36);
		frame.getContentPane().add(plus);
		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();

		Image imgbook = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		frame.getContentPane().add(help);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 66, 788, 12);
		frame.getContentPane().add(separator);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnMaterialGerteDaten) {
			DataPresenter dataPresenter = new DataPresenter();
			dataPresenter.previousPresenter = this;
			dataPresenter.newScreen();
		} else if (e.getSource() == this.btnInventarliste) {
			InventoryPresenter inventoryPresenter = new InventoryPresenter();
			inventoryPresenter.previousPresenter = this;
			inventoryPresenter.newScreen();
		} else if (e.getSource() == this.btnMeldungen) {
			MessagePresenter messagePresenter = new MessagePresenter();
			messagePresenter.previousPresenter = this;
			messagePresenter.newScreen();
		} else if (e.getSource() == this.settingsButton) {
			SettingsPresenter settingsPresenter = new SettingsPresenter();
			settingsPresenter.previousPresenter = this;
			settingsPresenter.newScreen();
		} else if (e.getSource() == this.btnLogout) {
			LoginPresenter loginPresenter = new LoginPresenter();
			this.frame.dispose();
			loginPresenter.newScreen();
		}
	}
}
