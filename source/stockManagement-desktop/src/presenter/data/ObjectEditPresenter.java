package presenter.data;
import model.DatabaseReadManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.accessControl.Group;
import model.databaseObjects.accessControl.User;
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.ConsumableMaterial;
import model.databaseObjects.stockObjects.Device;
import model.databaseObjects.stockObjects.MedicalMaterial;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ObjectEditPresenter extends Presenter implements MouseListener {
	/** Reusable ObjectAddPresenter modType **/
	public DatabaseObject.ModificationType modificationType;
	/** Data **/
	private DatabaseObject[] databaseObjects = null;
	/** Areas **/
	private JTable table;
	/** Buttons **/
	private JButton nextButton;

	/**
	 * Create the application.
	 */
	public ObjectEditPresenter(Presenter previousPresenter, DatabaseObject.ModificationType modificationType) {
		this.previousPresenter = previousPresenter;
		this.modificationType = modificationType;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		/******** Labels ********/
		JLabel title = new JLabel();
		switch (this.modificationType) {
			case deviceMenuItem:
				title = new JLabel("Gerät bearbeiten:");
				break;
			case medicalMaterialMenuItem:
				title = new JLabel("Medizinisches Material bearbeiten:");
				break;
			case consumableMaterialMenuItem:
				title = new JLabel("Verbrauchsmaterial bearbeiten:");
				break;
			case locationMenuItem:
				title = new JLabel("Lagerort bearbeiten:");
				break;
			case userMenuItem:
				title = new JLabel("Benutzer bearbeiten:");
				break;
			case groupMenuItem:
				title = new JLabel("Gruppe bearbeiten:");
				break;
		}
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);

		JLabel lblName = new JLabel("Auswahl:");
		lblName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblName);

		/******** Initial Data seperated for readability ********/
		switch (this.modificationType) {
			case deviceMenuItem:
				this.databaseObjects = DatabaseReadManager.getStockObjects(DatabaseObject.StockObjectType.device);
				break;
			case medicalMaterialMenuItem:
				this.databaseObjects = DatabaseReadManager.getStockObjects(DatabaseObject.StockObjectType.medicalMaterial);
				break;
			case consumableMaterialMenuItem:
				this.databaseObjects = DatabaseReadManager.getStockObjects(DatabaseObject.StockObjectType.consumableMaterial);
				break;
			case locationMenuItem:
				this.databaseObjects = DatabaseReadManager.getLocations();
				break;
			case userMenuItem:
				this.databaseObjects = DatabaseReadManager.getUsers();
				break;
			case groupMenuItem:
				this.databaseObjects = DatabaseReadManager.getGroups();
				break;
		}

		/******** Table Data ********/
		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*0);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		Object[] columnNames = new Object[0];

		switch (this.modificationType) {
			case deviceMenuItem:
				columnNames = new Object[]{ "ID", "Titel"};
				break;
			case medicalMaterialMenuItem:
				columnNames = new Object[]{ "ID", "Titel"};
				break;
			case consumableMaterialMenuItem:
				columnNames = new Object[]{ "ID", "Titel"};
				break;
			case locationMenuItem:
				columnNames = new Object[]{ "ID", "Titel"};
				break;
			case userMenuItem:
				columnNames = new Object[]{ "ID", "Username", "Vorname", "Name"};
				break;
			case groupMenuItem:
				columnNames = new Object[]{ "ID", "Name", "Aktiviert"};
				break;
		}

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		/******** Database Typesafety because it once crashed here ********/
		if (this.databaseObjects != null) {
			for (DatabaseObject databaseObject : this.databaseObjects) {
				switch (this.modificationType) {
					case deviceMenuItem:
						if (databaseObject instanceof Device) {
							Device device = (Device) databaseObject;
							Object row[] = {databaseObject.id, device.title};
							model.addRow(row);
						}
						break;
					case medicalMaterialMenuItem:
						if (databaseObject instanceof MedicalMaterial) {
							MedicalMaterial medicalMaterial = (MedicalMaterial) databaseObject;
							Object row[] = {databaseObject.id, medicalMaterial.title};
							model.addRow(row);
						}
						break;
					case consumableMaterialMenuItem:
						if (databaseObject instanceof ConsumableMaterial) {
							ConsumableMaterial consumableMaterial = (ConsumableMaterial) databaseObject;
							Object row[] = {databaseObject.id, consumableMaterial.title};
							model.addRow(row);
						}
						break;
					case locationMenuItem:
						if (databaseObject instanceof Location) {
							Location location = (Location) databaseObject;
							Object row[] = {databaseObject.id, location.title};
							model.addRow(row);
						}
						break;
					case userMenuItem:
						if (databaseObject instanceof User) {
							User user = (User) databaseObject;
							Object row[] = {databaseObject.id, user.username, user.firstName, user.name};
							model.addRow(row);
						}
						break;
					case groupMenuItem:
						if (databaseObject instanceof Group) {
							Group group = (Group) databaseObject;
							Object row[] = { databaseObject.id, group.title, group.isActive};
							model.addRow(row);
						}
						break;
				}
			}
		}

		this.table.setModel(model);
		/** Type specific Model setup **/
		switch (this.modificationType) {
			case deviceMenuItem:
				break;
			case medicalMaterialMenuItem:
				break;
			case consumableMaterialMenuItem:
				break;
			case locationMenuItem:
				break;
			case userMenuItem:
				break;
			case groupMenuItem:
				this.table.getColumnModel().getColumn(2).setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
				break;
		}
		this.table.addMouseListener(this);
		scrollPane.setViewportView(this.table);


		/******** Buttons ********/
		this.nextButton = new JButton("weiter");
		this.nextButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.nextButton.addActionListener(this);
		frame.getContentPane().add(this.nextButton);
		this.nextButton.setEnabled(false);
	}

	@Override
	public void showedAsPreviousPresenter() {
		super.showedAsPreviousPresenter();
		int selectedRow = this.table.getSelectedRow();
		DatabaseObject selectedObject = this.databaseObjects[selectedRow];

		switch (this.modificationType) {
			case deviceMenuItem:
				if (selectedObject instanceof Device) {
					this.databaseObjects[selectedRow] = DatabaseReadManager.getStockObject(selectedObject.id);
					this.table.setValueAt(((Device)this.databaseObjects[selectedRow]).id, selectedRow, 0);
					this.table.setValueAt(((Device)this.databaseObjects[selectedRow]).title, selectedRow, 1);
				}
				break;
			case medicalMaterialMenuItem:
				if (selectedObject instanceof MedicalMaterial) {
					this.databaseObjects[selectedRow] = DatabaseReadManager.getStockObject(selectedObject.id);
					this.table.setValueAt(((MedicalMaterial)this.databaseObjects[selectedRow]).id, selectedRow, 0);
					this.table.setValueAt(((MedicalMaterial)this.databaseObjects[selectedRow]).title, selectedRow, 1);
				}
				break;
			case consumableMaterialMenuItem:
				if (selectedObject instanceof ConsumableMaterial) {
					this.databaseObjects[selectedRow] = DatabaseReadManager.getStockObject(selectedObject.id);
					this.table.setValueAt(((ConsumableMaterial)this.databaseObjects[selectedRow]).id, selectedRow, 0);
					this.table.setValueAt(((ConsumableMaterial)this.databaseObjects[selectedRow]).title, selectedRow, 1);
				}
				break;
			case locationMenuItem:
				if (selectedObject instanceof Location) {
					this.databaseObjects[selectedRow] = DatabaseReadManager.getLocation(selectedObject.id);
					this.table.setValueAt(((Location)this.databaseObjects[selectedRow]).id, selectedRow, 0);
					this.table.setValueAt(((Location)this.databaseObjects[selectedRow]).title, selectedRow, 1);
				}
				break;
			case userMenuItem:
				if (selectedObject instanceof User) {
					this.databaseObjects[selectedRow] = DatabaseReadManager.getUser(selectedObject.id);
					this.table.setValueAt(((User)this.databaseObjects[selectedRow]).id, selectedRow, 0);
					this.table.setValueAt(((User)this.databaseObjects[selectedRow]).username, selectedRow, 1);
				}
				break;
			case groupMenuItem:
				if (selectedObject instanceof Group) {
					this.databaseObjects[selectedRow] = DatabaseReadManager.getGroup(selectedObject.id);
					this.table.setValueAt(((Group)this.databaseObjects[selectedRow]).id, selectedRow, 0);
					this.table.setValueAt(((Group)this.databaseObjects[selectedRow]).title, selectedRow, 1);
				}
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.nextButton) {
			int selectedRow = this.table.getSelectedRow();
			DatabaseObject selectedDatabaseObject = this.databaseObjects[selectedRow];
			ObjectAddPresenter objectAddPresenter = new ObjectAddPresenter(this, this.modificationType, selectedDatabaseObject);
			objectAddPresenter.newScreen();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Left mouse click
		if (SwingUtilities.isLeftMouseButton(e)) {
			// get the coordinates of the mouse click
			Point p = e.getPoint();
			// get the row index that contains that coordinate
			int rowNumber = table.rowAtPoint(p);
			// Do sth with the rowPosition
			this.nextButton.setEnabled(true);
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
