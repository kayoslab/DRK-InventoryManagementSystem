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

		Image chatImage = new ImageIcon (this.getClass().getResource("/img/chat-button.jpg")).getImage();
		Image chatImageScaled = chatImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		chat.setIcon (new ImageIcon (chatImageScaled));
		chat.setBounds(iconButtonBarX, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		frame.getContentPane().add(chat);

		Image editImage = new ImageIcon (this.getClass().getResource("/img/edit-button.jpg")).getImage();
		Image editImageScaled = editImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		edit.setIcon (new ImageIcon (editImageScaled));
		edit.setBounds(iconButtonBarX+(iconButtonWidth+spacing), (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		frame.getContentPane().add(edit);

		Image addImage = new ImageIcon (this.getClass().getResource("/img/add-circle-1.jpg")).getImage();
		Image addImageScaled = addImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		plus.setIcon (new ImageIcon (addImageScaled));
		plus.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*2, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		frame.getContentPane().add(plus);


		Image bookImage = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		Image bookImageScaled = bookImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		help.setIcon (new ImageIcon (bookImageScaled));
		help.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*3, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		frame.getContentPane().add(help);

		JSeparator separator = new JSeparator();
		separator.setBounds(leftPadding, 66, (width - leftPadding - rightPadding), 12);
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
