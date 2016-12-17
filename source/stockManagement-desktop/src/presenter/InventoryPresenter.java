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

package presenter;
import model.DatabaseReadManager;
import model.PdfGenerator;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class InventoryPresenter extends Presenter {
	private JTable table;
	private StockObject[][] tableData = new StockObject[DatabaseObject.StockObjectType.values().length][];
	private JCheckBox checkBoxDevices;
	private JCheckBox checkBoxMedicalMaterials;
	private JCheckBox checkBoxConsumableMaterial;
	private JComboBox filterComboBox = new JComboBox();
	private JButton generateInventoryButton;
	/**
	 * Create the application.
	 */
	public InventoryPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JLabel Inventarliste = new JLabel("Inventarliste");
		Inventarliste.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Inventarliste.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		frame.getContentPane().add(Inventarliste);

		// Checkboxes
		this.checkBoxDevices = new JCheckBox("Ger\u00e4te");
		this.checkBoxDevices.setBounds(leftPadding, contentY+lineHeight*0, leftSideMenuWidth, 24);
		this.checkBoxDevices.setSelected(true);
		this.checkBoxDevices.setEnabled(false);
		this.checkBoxDevices.addActionListener(this);
		this.frame.getContentPane().add(this.checkBoxDevices);
		this.checkBoxMedicalMaterials = new JCheckBox("MedMaterialien");
		this.checkBoxMedicalMaterials.setBounds(leftPadding, contentY+lineHeight*1, leftSideMenuWidth, 24);
		this.checkBoxMedicalMaterials.setSelected(true);
		this.checkBoxMedicalMaterials.setEnabled(false);
		this.checkBoxMedicalMaterials.addActionListener(this);
		this.frame.getContentPane().add(this.checkBoxMedicalMaterials);
		this.checkBoxConsumableMaterial = new JCheckBox("Betreuungsmaterialien");
		this.checkBoxConsumableMaterial.setBounds(leftPadding, contentY+lineHeight*2, leftSideMenuWidth, 24);
		this.checkBoxConsumableMaterial.setSelected(true);
		this.checkBoxConsumableMaterial.setEnabled(false);
		this.checkBoxConsumableMaterial.addActionListener(this);
		this.frame.getContentPane().add(this.checkBoxConsumableMaterial);

		// Combobox
		this.filterComboBox.setBounds(leftPadding, contentY+lineHeight*3, leftSideMenuWidth, 24);
		this.filterComboBox.addItem("Alphabetisch");
		this.filterComboBox.addItem("Typ");
		this.frame.getContentPane().add(filterComboBox);
		// needs to be called after adding the Items
		this.filterComboBox.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY, displayAreaWidth-(leftSideMenuWidth+spacing), displayAreaHeight-contentY);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		scrollPane.setViewportView(this.table);

		/******** Buttons ********/
		this.generateInventoryButton = new JButton("PDF erzeugen");
		this.generateInventoryButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.generateInventoryButton.addActionListener(this);
		frame.getContentPane().add(this.generateInventoryButton);
	}

	@Override
	public void presentData() {
		super.presentData();
		this.loadTableData();
		this.refreshTableData();
	}

	private void loadTableData() {
		if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewDevices)) {
			this.tableData[DatabaseObject.StockObjectType.device.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.device);
			this.checkBoxDevices.setEnabled(true);
		}
		if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewMedicalMaterials)) {
			this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.medicalMaterial);
			this.checkBoxMedicalMaterials.setEnabled(true);
		}
		if (this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewConsumableMaterials)) {
			this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.consumableMaterial);
			this.checkBoxConsumableMaterial.setEnabled(true);
		}
	}

	private void refreshTableData() {
		DefaultTableModel model;
		Object columnNames[] = { "Titel", "Menge", "Typ"};
		model = new DefaultTableModel(columnNames, 0);

		/**
		 *
		 * Maybe this is an overcomplicated approach, because some SortDescriptors expect the StockObjects
		 * to be stored into just one Array (e.g. sort alphabetically by stockObject.title).
		 *
		 * For this reason I've added a Switch-Case-Block below. Just add additional options
		 * to the filterComboBox and switch between them via different cases.
		 * You can sort the unsorted Arrays and add them to the sortedData ArrayList like
		 * I've done this exemplary for case 0.
		 *
		 */

		StockObject[] unsortedDevices = this.tableData[DatabaseObject.StockObjectType.device.ordinal()];
		StockObject[] unsortedmedicalMaterials = this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()];
		StockObject[] unsortedconsumableMaterials = this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()];

		ArrayList<StockObject> sortedData = new ArrayList<StockObject>();

		StockObject[] sortedDevices = unsortedDevices;
		StockObject[] sortedmedicalMaterials = unsortedmedicalMaterials;
		StockObject[] sortedconsumableMaterials = unsortedconsumableMaterials;

		StockObject[] stockObjects = new StockObject[0];

		switch (this.filterComboBox.getSelectedIndex()) {
			case 0:
				// Lambda sort alphabetically after adding to stockObjects
				// Outcome: Sorted Alphabetically
				if (unsortedDevices != null) {
					sortedData.addAll(Arrays.asList(unsortedDevices));
				}
				if (unsortedmedicalMaterials != null) {
					sortedData.addAll(Arrays.asList(unsortedmedicalMaterials));
				}
				if (unsortedconsumableMaterials != null) {
					sortedData.addAll(Arrays.asList(unsortedconsumableMaterials));
				}

				stockObjects = sortedData.toArray(new StockObject[sortedData.size()]);
				Arrays.sort(stockObjects, (a, b) -> a.title.compareToIgnoreCase(b.title));

				break;
			case 1:
				// Lambda sort alphabetically before adding to stockObjects
				// Outcome: Sorted by Type
				Arrays.sort(sortedDevices, (a, b) -> a.title.compareToIgnoreCase(b.title));
				Arrays.sort(sortedmedicalMaterials, (a, b) -> a.title.compareToIgnoreCase(b.title));
				Arrays.sort(sortedconsumableMaterials, (a, b) -> a.title.compareToIgnoreCase(b.title));

				sortedData.addAll(Arrays.asList(sortedDevices));
				sortedData.addAll(Arrays.asList(sortedmedicalMaterials));
				sortedData.addAll(Arrays.asList(sortedconsumableMaterials));

				stockObjects = sortedData.toArray(new StockObject[sortedData.size()]);
				break;
		}

		if (stockObjects != null) {
			// iterate over existing objects in sortedData
			for (StockObject stockObject : stockObjects) {
				// Switch between instanceTypes
				if (stockObject instanceof Device) {
					if (this.checkBoxDevices.isSelected()) {
						Device device = (Device) stockObject;
						// show all objects
						Object row[] = { device.title, device.totalVolume , "Ger\u00e4t"};
						model.addRow(row);
					}
				} else if (stockObject instanceof Material) {
					if (stockObject instanceof MedicalMaterial) {
						if (this.checkBoxMedicalMaterials.isSelected()) {
							MedicalMaterial medicalMaterial = (MedicalMaterial) stockObject;
							// show all objects
							Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume,  "Medizinisches Material"};
							model.addRow(row);
						}
					} else if (stockObject instanceof ConsumableMaterial) {
						if (this.checkBoxConsumableMaterial.isSelected()) {
							ConsumableMaterial consumableMaterial = (ConsumableMaterial) stockObject;
							// show all objects
							Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, "Betreuungsmaterial"};
							model.addRow(row);
						}
					} else {
						// Do nothing with this object, its not a usable material
					}
				} else {
					// Do nothing, maybe its a vehicle
				}
			}
		}

		this.table.setModel(model);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.checkBoxDevices) {
			this.refreshTableData();
		} else if (e.getSource() == this.checkBoxMedicalMaterials) {
			this.refreshTableData();
		} else if (e.getSource() == this.checkBoxConsumableMaterial) {
			this.refreshTableData();
		} else if (e.getSource() == this.filterComboBox) {
			this.refreshTableData();
		} else if (e.getSource() == this.generateInventoryButton) {
			// parent component of the dialog
			JFrame parentFrame = new JFrame();

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Speichern unter");
			fileChooser.setSelectedFile(new File(System.getProperty("user.home") + File.separator +  "Inventory.pdf"));
			int userSelection = fileChooser.showSaveDialog(parentFrame);

			if (userSelection == JFileChooser.APPROVE_OPTION) {
				File fileToSave = fileChooser.getSelectedFile();
				// System.out.println("Save as file: " + fileToSave.getAbsolutePath());

				PdfGenerator pdfGenerator = new PdfGenerator();
				try {
					pdfGenerator.generatePDF(fileToSave.getAbsolutePath());
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}


		}
	}

}
