package presenter;
import model.Session;
import model.databaseObjects.DatabaseObject;
import presenter.data.AddPresenter;
import presenter.data.DataPresenter;
import presenter.data.EditPresenter;
import presenter.settings.SettingsPresenter;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class MenuPresenter extends Presenter implements MouseListener {
	/******** Menu *********/
	private JButton materialAndDevicesButton;
	private JButton inventoryButton;
	private JButton settingsButton = new JButton("Einstellungen");
	/******** Toolbar *********/
	private JButton messagesButton;
	private JButton editButton;
	private JButton addButton;
	/******** PopUps *********/
	private JPopupMenu addPopup = new JPopupMenu();
	private JMenuItem addDeviceMenuItem;
	private JMenuItem addMedicalMaterialMenuItem;
	private JMenuItem addConsumableMaterialMenuItem;
	private JMenuItem addLocationMenuItem;
	private JMenuItem addUserMenuItem;
	private JMenuItem addGroupMenuItem;
	private JPopupMenu editPopup;
	private JMenuItem editDeviceMenuItem;
	private JMenuItem editMedicalMaterialMenuItem;
	private JMenuItem editConsumableMaterialMenuItem;
	private JMenuItem editLocationMenuItem;
	private JMenuItem editUserMenuItem;
	private JMenuItem editGroupMenuItem;

	/**
	 * Create the application.
	 */
	public MenuPresenter() {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * @param previousPresenter Presenter
	 *
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

		/******************* Header Layout ************************/
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

		/******************* Menu Buttons ************************/
		if (this.session.currentUserCanAccessInventory()) {
			this.materialAndDevicesButton = new JButton("Material-/ Geräte Daten");
			this.materialAndDevicesButton.setBounds(menuButtonX, firstButtonPlacing, menuButtonWidth, menuButtonHeight);
			this.materialAndDevicesButton.addActionListener(this);
			this.frame.getContentPane().add(materialAndDevicesButton);
		}

		if (this.session.currentUserCanAccessInventory()) {
			this.inventoryButton = new JButton("Inventarliste");
			this.inventoryButton.setBounds(menuButtonX, secondButtonPlacing, menuButtonWidth, menuButtonHeight);
			this.inventoryButton.addActionListener(this);
			this.frame.getContentPane().add(this.inventoryButton);
		}

		this.settingsButton = new JButton("Einstellungen");
		this.settingsButton.setBounds(menuButtonX, thirdButtonPlacing, menuButtonWidth, menuButtonHeight);
		this.settingsButton.addActionListener(this);
		this.frame.getContentPane().add(this.settingsButton);

		this.help = new JButton("");
		Image bookImage = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		Image bookImageScaled = bookImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.help.setIcon (new ImageIcon (bookImageScaled));
		this.help.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*3, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.help.addActionListener(this);
		this.frame.getContentPane().add(this.help);

		/******************* Toolbar Buttons ************************/
		if (this.session.currentUserCanAccessInventory()) {
			this.messagesButton = new JButton("");
			Image chatImage = new ImageIcon (this.getClass().getResource("/img/chat-button.jpg")).getImage();
			Image chatImageScaled = chatImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
			this.messagesButton.setIcon (new ImageIcon (chatImageScaled));
			this.messagesButton.setBounds(iconButtonBarX, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
			this.messagesButton.addActionListener(this);
			this.frame.getContentPane().add(this.messagesButton);
		}

		if (this.session.currentUserCanAddData()) {
			this.addButton = new JButton("");
			Image addImage = new ImageIcon (this.getClass().getResource("/img/add-circle-1.jpg")).getImage();
			Image addImageScaled = addImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
			this.addButton.setIcon (new ImageIcon (addImageScaled));
			this.addButton.setBounds(iconButtonBarX+(iconButtonWidth+spacing), (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
			this.addButton.addActionListener(this);
			this.frame.getContentPane().add(this.addButton);

			this.addPopup = new JPopupMenu();
			this.frame.getContentPane().add(this.addPopup);

			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createDevice)) {
				this.addDeviceMenuItem = new JMenuItem("Gerät hinzufügen");
				this.addDeviceMenuItem.addActionListener(this);
				this.addPopup.add(addDeviceMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createMedicalMaterial)) {
				this.addMedicalMaterialMenuItem = new JMenuItem("Medizinisches Material hinzufügen");
				this.addMedicalMaterialMenuItem.addActionListener(this);
				this.addPopup.add(addMedicalMaterialMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createConsumableMaterial)) {
				this.addConsumableMaterialMenuItem = new JMenuItem("Verbrauchsmaterial hinzufügen");
				this.addConsumableMaterialMenuItem.addActionListener(this);
				this.addPopup.add(addConsumableMaterialMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createLocation)) {
				this.addLocationMenuItem = new JMenuItem("Lagerort hinzufügen");
				this.addLocationMenuItem.addActionListener(this);
				this.addPopup.add(addLocationMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createUser)) {
				this.addUserMenuItem = new JMenuItem("Benutzer hinzufügen");
				this.addUserMenuItem.addActionListener(this);
				this.addPopup.add(addUserMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.createGroup)) {
				this.addGroupMenuItem = new JMenuItem("Gruppe hinzufügen");
				this.addGroupMenuItem.addActionListener(this);
				this.addPopup.add(addGroupMenuItem);
			}
		}

		if (this.session.currentUserCanEditData() || this.session.currentUserCanDeleteData()) {
			this.editButton = new JButton("");
			Image editImage = new ImageIcon (this.getClass().getResource("/img/edit-button.jpg")).getImage();
			Image editImageScaled = editImage.getScaledInstance(iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
			this.editButton.setIcon (new ImageIcon (editImageScaled));
			this.editButton.setBounds(iconButtonBarX+(iconButtonWidth+spacing)*2, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
			this.editButton.addActionListener(this);
			this.frame.getContentPane().add(this.editButton);

			this.editPopup = new JPopupMenu();
			this.frame.getContentPane().add(this.editPopup);

			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editDevice)
					|| this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteDevice)) {
				this.editDeviceMenuItem = new JMenuItem("Gerät bearbeiten");
				this.editDeviceMenuItem.addActionListener(this);
				this.editPopup.add(editDeviceMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editMedicalMaterial)
					|| this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteMedicalMaterial)) {
				this.editMedicalMaterialMenuItem = new JMenuItem("Medizinisches Material bearbeiten");
				this.editMedicalMaterialMenuItem.addActionListener(this);
				this.editPopup.add(editMedicalMaterialMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editConsumableMaterial)
					|| this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteConsumableMaterial)) {
				this.editConsumableMaterialMenuItem = new JMenuItem("Verbrauchsmaterial bearbeiten");
				this.editConsumableMaterialMenuItem.addActionListener(this);
				this.editPopup.add(editConsumableMaterialMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editLocation)
					|| this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteLocation)) {
				this.editLocationMenuItem = new JMenuItem("Lagerort bearbeiten");
				this.editLocationMenuItem.addActionListener(this);
				this.editPopup.add(editLocationMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editUser)
					|| this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteUser)) {
				this.editUserMenuItem = new JMenuItem("Benutzer bearbeiten");
				this.editUserMenuItem.addActionListener(this);
				this.editPopup.add(editUserMenuItem);
			}
			if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.editGroup)
					|| this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.deleteGroup)) {
				this.editGroupMenuItem = new JMenuItem("Gruppe bearbeiten");
				this.editGroupMenuItem.addActionListener(this);
				this.editPopup.add(editGroupMenuItem);
			}
		}

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
		} else if (e.getSource() == this.messagesButton) {
			MessagePresenter messagePresenter = new MessagePresenter(this);
			messagePresenter.newScreen();
		}  else if (e.getSource() == this.addButton) {
			if (this.addPopup != null) {
				this.addPopup.show(null,this.frame.getX()+this.addButton.getX()+iconButtonHeight,this.frame.getY()+this.addButton.getY()+iconButtonWidth);
			}
		} else if (e.getSource() == this.editButton) {
			if (this.editPopup != null) {
				this.editPopup.show(null,this.frame.getX()+this.editButton.getX()+iconButtonHeight,this.frame.getY()+this.editButton.getY()+iconButtonWidth);
			}
		} else if (e.getSource() == this.addDeviceMenuItem) {
			AddPresenter addPresenter = new AddPresenter(this, DatabaseObject.ModificationType.deviceMenuItem);
			addPresenter.newScreen();
		} else if (e.getSource() == this.addMedicalMaterialMenuItem) {
			AddPresenter addPresenter = new AddPresenter(this, DatabaseObject.ModificationType.medicalMaterialMenuItem);
			addPresenter.newScreen();
		} else if (e.getSource() == this.addConsumableMaterialMenuItem) {
			AddPresenter addPresenter = new AddPresenter(this, DatabaseObject.ModificationType.consumableMaterialMenuItem);
			addPresenter.newScreen();
		} else if (e.getSource() == this.addLocationMenuItem) {
			AddPresenter addPresenter = new AddPresenter(this, DatabaseObject.ModificationType.locationMenuItem);
			addPresenter.newScreen();
		} else if (e.getSource() == this.addUserMenuItem) {
			AddPresenter addPresenter = new AddPresenter(this, DatabaseObject.ModificationType.userMenuItem);
			addPresenter.newScreen();
		} else if (e.getSource() == this.addGroupMenuItem) {
			AddPresenter addPresenter = new AddPresenter(this, DatabaseObject.ModificationType.groupMenuItem);
			addPresenter.newScreen();
		} else if (e.getSource() == this.editDeviceMenuItem) {
			EditPresenter editPresenter = new EditPresenter(this, DatabaseObject.ModificationType.deviceMenuItem);
			editPresenter.newScreen();
		} else if (e.getSource() == this.editMedicalMaterialMenuItem) {
			EditPresenter editPresenter = new EditPresenter(this, DatabaseObject.ModificationType.medicalMaterialMenuItem);
			editPresenter.newScreen();
		} else if (e.getSource() == this.editConsumableMaterialMenuItem) {
			EditPresenter editPresenter = new EditPresenter(this, DatabaseObject.ModificationType.consumableMaterialMenuItem);
			editPresenter.newScreen();
		} else if (e.getSource() == this.editLocationMenuItem) {
			EditPresenter editPresenter = new EditPresenter(this, DatabaseObject.ModificationType.locationMenuItem);
			editPresenter.newScreen();
		} else if (e.getSource() == this.editUserMenuItem) {
			EditPresenter editPresenter = new EditPresenter(this, DatabaseObject.ModificationType.userMenuItem);
			editPresenter.newScreen();
		} else if (e.getSource() == this.editGroupMenuItem) {
			EditPresenter editPresenter = new EditPresenter(this, DatabaseObject.ModificationType.groupMenuItem);
			editPresenter.newScreen();
		}

	}

	private void hideAllPopups() {
		if (this.addPopup != null) {
			if (this.addPopup.isShowing()) {
				this.addPopup.setVisible(false);
			}
		}
		if (this.editPopup != null) {
			if (this.editPopup.isShowing()) {
				this.editPopup.setVisible(false);
			}
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
