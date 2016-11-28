package presenter;
import presenter.data.DataPresenter;
import presenter.settings.SettingsPresenter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;


public class MenuPresenter extends Presenter {
	private JButton materialAndDevicesButton = new JButton("Material-/ Geräte Daten");
	private JButton inventoryButton = new JButton("Inventarliste");
	private JButton settingsButton = new JButton("Einstellungen");

	private JButton messages = new JButton("");
	private JButton edit = new JButton("");
	private JButton plus = new JButton("");

	/**
	 * Create the application.
	 */
	public MenuPresenter() {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public MenuPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		/* Use SuperClass Attributes */
		this.logo = new JButton("");
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		this.logo.setIcon (new ImageIcon (img));
		this.logo.setBounds(logoX, topPadding, logoWidth, logoHeight);
		this.frame.getContentPane().add(this.logo);
		this.logo.addActionListener(this);

		this.btnLogout = new JButton("Logout");
		this.btnLogout.setBounds(logoutX, (topLayoutCenter - logoutHeight / 2), logoutWidth, logoutHeight);
		this.frame.getContentPane().add(this.btnLogout);
		this.btnLogout.addActionListener(this);

		this.separator = new JSeparator();
		this.separator.setBounds(leftPadding, topPadding+logoHeight+1, (width - leftPadding - rightPadding), smallSpacing);
		this.frame.getContentPane().add(this.separator);

		/* Use Local Class Attributes */
		this.materialAndDevicesButton.setBounds(menuButtonX, firstButtonPlacing, menuButtonWidth, menuButtonHeight);
		this.materialAndDevicesButton.addActionListener(this);
		this.frame.getContentPane().add(materialAndDevicesButton);
		
		this.inventoryButton.setBounds(menuButtonX, secondButtonPlacing, menuButtonWidth, menuButtonHeight);
		this.inventoryButton.addActionListener(this);
		this.frame.getContentPane().add(this.inventoryButton);

		this.settingsButton.setBounds(menuButtonX, thirdButtonPlacing, menuButtonWidth, menuButtonHeight);
		this.settingsButton.addActionListener(this);
		this.frame.getContentPane().add(this.settingsButton);

		Image chatImage = new ImageIcon (this.getClass().getResource("/img/chat-button.jpg")).getImage();
		Image chatImageScaled = chatImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.messages.setIcon (new ImageIcon (chatImageScaled));
		this.messages.setBounds(iconButtonBarX, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.messages.addActionListener(this);
		this.frame.getContentPane().add(this.messages);

		Image editImage = new ImageIcon (this.getClass().getResource("/img/edit-button.jpg")).getImage();
		Image editImageScaled = editImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.edit.setIcon (new ImageIcon (editImageScaled));
		this.edit.setBounds(iconButtonBarX+(iconButtonWidth+spacing), (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.edit.addActionListener(this);
		this.frame.getContentPane().add(this.edit);

		Image addImage = new ImageIcon (this.getClass().getResource("/img/add-circle-1.jpg")).getImage();
		Image addImageScaled = addImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.plus.setIcon (new ImageIcon (addImageScaled));
		this.plus.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*2, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.plus.addActionListener(this);
		this.frame.getContentPane().add(this.plus);

		/* Use SuperClass Attribute help */
		this.help = new JButton("");
		Image bookImage = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		Image bookImageScaled = bookImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.help.setIcon (new ImageIcon (bookImageScaled));
		this.help.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*3, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.help.addActionListener(this);
		this.frame.getContentPane().add(this.help);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.materialAndDevicesButton) {
			DataPresenter dataPresenter = new DataPresenter(this);
			dataPresenter.newScreen();
		} else if (e.getSource() == this.inventoryButton) {
			InventoryPresenter inventoryPresenter = new InventoryPresenter(this);
			inventoryPresenter.newScreen();
		} else if (e.getSource() == this.settingsButton) {
			SettingsPresenter settingsPresenter = new SettingsPresenter(this);
			settingsPresenter.newScreen();
		} else if (e.getSource() == this.messages) {
			MessagePresenter messagePresenter = new MessagePresenter(this);
			messagePresenter.newScreen();
		} else if (e.getSource() == this.edit) {

		} else if (e.getSource() == this.plus) {

		}
	}
}
