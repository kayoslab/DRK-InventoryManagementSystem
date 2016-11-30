package presenter;
import model.UserManager;
import presenter.data.DataPresenter;
import presenter.settings.SettingsPresenter;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MenuPresenter extends Presenter implements MouseListener {
	private JButton materialAndDevicesButton = new JButton("Material-/ Geräte Daten");
	private JButton inventoryButton = new JButton("Inventarliste");
	private JButton settingsButton = new JButton("Einstellungen");

	private JButton messages = new JButton("");
	private JButton edit = new JButton("");
	private JButton plus = new JButton("");
	/******** PopUps *********/
	private JPopupMenu plusPopup = new JPopupMenu();
	private JMenuItem addDeviceMenuItem = new JMenuItem("Gerät hinzufügen");
	private JMenuItem addMedicalMaterialMenuItem = new JMenuItem("Medizinisches Material hinzufügen");
	private JMenuItem addConsumableMaterialMenuItem = new JMenuItem("Verbrauchsmaterial hinzufügen");
	private JMenuItem addLocationMenuItem = new JMenuItem("Lagerort hinzufügen");
	private JMenuItem addUserMenuItem = new JMenuItem("Benutzer hinzufügen");
	private JMenuItem addGroupMenuItem = new JMenuItem("Gruppe hinzufügen");
	private JPopupMenu editPopup = new JPopupMenu();
	private JMenuItem editDeviceMenuItem = new JMenuItem("Gerät bearbeiten");
	private JMenuItem editMedicalMaterialMenuItem = new JMenuItem("Medizinisches Material bearbeiten");
	private JMenuItem editConsumableMaterialMenuItem = new JMenuItem("Verbrauchsmaterial bearbeiten");
	private JMenuItem editLocationMenuItem = new JMenuItem("Lagerort bearbeiten");
	private JMenuItem editUserMenuItem = new JMenuItem("Benutzer bearbeiten");
	private JMenuItem editGroupMenuItem = new JMenuItem("Gruppe bearbeiten");
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
		this.frame.addMouseListener(this);
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

		Image addImage = new ImageIcon (this.getClass().getResource("/img/add-circle-1.jpg")).getImage();
		Image addImageScaled = addImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.plus.setIcon (new ImageIcon (addImageScaled));
		this.plus.setBounds(iconButtonBarX+(iconButtonWidth+spacing), (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.plus.addActionListener(this);
		this.frame.getContentPane().add(this.plus);

		// Add Popup
		this.addDeviceMenuItem.addActionListener(this);
		this.addMedicalMaterialMenuItem.addActionListener(this);
		this.addConsumableMaterialMenuItem.addActionListener(this);
		this.addLocationMenuItem.addActionListener(this);
		this.addUserMenuItem.addActionListener(this);
		this.addGroupMenuItem.addActionListener(this);
		this.plusPopup.add(addDeviceMenuItem);
		this.plusPopup.add(addMedicalMaterialMenuItem);
		this.plusPopup.add(addConsumableMaterialMenuItem);
		this.plusPopup.add(addLocationMenuItem);
		this.plusPopup.add(addUserMenuItem);
		this.plusPopup.add(addGroupMenuItem);
		this.frame.getContentPane().add(this.plusPopup);

		// Edit Popup
		this.editDeviceMenuItem.addActionListener(this);
		this.editMedicalMaterialMenuItem.addActionListener(this);
		this.editConsumableMaterialMenuItem.addActionListener(this);
		this.editLocationMenuItem.addActionListener(this);
		this.editUserMenuItem.addActionListener(this);
		this.editGroupMenuItem.addActionListener(this);
		this.editPopup.add(editDeviceMenuItem);
		this.editPopup.add(editMedicalMaterialMenuItem);
		this.editPopup.add(editConsumableMaterialMenuItem);
		this.editPopup.add(editLocationMenuItem);
		this.editPopup.add(editUserMenuItem);
		this.editPopup.add(editGroupMenuItem);
		this.frame.getContentPane().add(this.editPopup);

		Image editImage = new ImageIcon (this.getClass().getResource("/img/edit-button.jpg")).getImage();
		Image editImageScaled = editImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.edit.setIcon (new ImageIcon (editImageScaled));
		this.edit.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*2, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.edit.addActionListener(this);
		this.frame.getContentPane().add(this.edit);

		/* Use SuperClass Attribute help */
		this.help = new JButton("");
		Image bookImage = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		Image bookImageScaled = bookImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.help.setIcon (new ImageIcon (bookImageScaled));
		this.help.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*3, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.help.addActionListener(this);
		this.frame.getContentPane().add(this.help);

		// System.out.println(PasswordManager.getSharedInstance().user.firstName);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		this.hideAllPopups();
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
			this.editPopup.show(null,this.frame.getX()+this.edit.getX()+iconButtonHeight,this.frame.getY()+this.edit.getY()+iconButtonWidth);
		} else if (e.getSource() == this.plus) {
			this.plusPopup.show(null,this.frame.getX()+this.plus.getX()+iconButtonHeight,this.frame.getY()+this.plus.getY()+iconButtonWidth);
		}

	}

	private void hideAllPopups() {
		if (this.plusPopup.isShowing()) {
			this.plusPopup.setVisible(false);
		}
		if (this.editPopup.isShowing()) {
			this.editPopup.setVisible(false);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Left mouse click
		if (SwingUtilities.isLeftMouseButton(e)) {
			this.hideAllPopups();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}
