/*
 * Copyright (c) - All Rights Reserved
 *
 * Unauthorized copying of these files, via any medium is
 * strictly prohibited Proprietary and confidential
 *
 * NOTICE:
 * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
 * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
 * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained by the owner.
 *
 * @author Amina
 * @author Sabine
 *
 */

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
import java.util.Arrays;

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
		this.presenterHelpId = 4;

		/******** Labels ********/
		JLabel title = new JLabel();
		switch (this.modificationType) {
			case deviceMenuItem:
				title = new JLabel("Ger\u00e4t bearbeiten:");
				break;
			case medicalMaterialMenuItem:
				title = new JLabel("Medizinisches Material bearbeiten:");
				break;
			case consumableMaterialMenuItem:
				title = new JLabel("Betreuungsmaterial bearbeiten:");
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

		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*0);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
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
	public void presentData() {
		super.presentData();
		this.loadTableData();
	}

	private void loadTableData() {
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
				if (this.databaseObjects != null) {
					if (this.databaseObjects instanceof Location[]) {
						Location[] locations = (Location[]) this.databaseObjects;
						Arrays.sort(locations, (a, b) -> a.title.compareToIgnoreCase(b.title));
						this.databaseObjects = locations;
					}
				}
				break;
			case userMenuItem:
				this.databaseObjects = DatabaseReadManager.getUsers();
				break;
			case groupMenuItem:
				this.databaseObjects = DatabaseReadManager.getGroups();
				break;
		}

		/******** Table Data ********/
		Object[] columnNames = new Object[0];

		switch (this.modificationType) {
			case deviceMenuItem:
				columnNames = new Object[]{ "Titel", "MTK Intervall", "STK Intervall"};
				break;
			case medicalMaterialMenuItem:
				columnNames = new Object[]{ "Titel", "" };
				break;
			case consumableMaterialMenuItem:
				columnNames = new Object[]{ "Titel"};
				break;
			case locationMenuItem:
				columnNames = new Object[]{ "Titel"};
				break;
			case userMenuItem:
				columnNames = new Object[]{ "Username", "Vorname", "Name"};
				break;
			case groupMenuItem:
				columnNames = new Object[]{ "Name", "Aktiviert"};
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
							Object row[] = {device.title, device.mtkIntervall, device.stkIntervall};
							model.addRow(row);
						}
						break;
					case medicalMaterialMenuItem:
						if (databaseObject instanceof MedicalMaterial) {
							MedicalMaterial medicalMaterial = (MedicalMaterial) databaseObject;
							Object row[] = {medicalMaterial.title };
							model.addRow(row);
						}
						break;
					case consumableMaterialMenuItem:
						if (databaseObject instanceof ConsumableMaterial) {
							ConsumableMaterial consumableMaterial = (ConsumableMaterial) databaseObject;
							Object row[] = {consumableMaterial.title };
							model.addRow(row);
						}
						break;
					case locationMenuItem:
						if (databaseObject instanceof Location) {
							Location location = (Location) databaseObject;
							Object row[] = {location.title};
							model.addRow(row);
						}
						break;
					case userMenuItem:
						if (databaseObject instanceof User) {
							User user = (User) databaseObject;
							Object row[] = { user.username, user.firstName, user.name};
							model.addRow(row);
						}
						break;
					case groupMenuItem:
						if (databaseObject instanceof Group) {
							Group group = (Group) databaseObject;
							Object row[] = { group.title, group.isActive};
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
				this.table.getColumnModel().getColumn(1).setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
				break;
		}
	}

	@Override
	public void showedAsPreviousPresenter() {
		super.showedAsPreviousPresenter();
		this.nextButton.setEnabled(false);
		this.loadTableData();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.nextButton) {
			int selectedRow = this.table.getSelectedRow();
			if (selectedRow >= 0) {
				DatabaseObject selectedDatabaseObject = this.databaseObjects[selectedRow];
				ObjectAddPresenter objectAddPresenter = new ObjectAddPresenter(this, this.modificationType, selectedDatabaseObject);
				objectAddPresenter.newScreen();
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Left mouse click
		if (SwingUtilities.isLeftMouseButton(e)) {
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
