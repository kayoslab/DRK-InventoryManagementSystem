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
import model.Session;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class InventoryPresenter extends Presenter {
	private JTable table;
	private StockObjectValue[][] tableData = new StockObjectValue[DatabaseObject.StockObjectType.values().length][];
	private JCheckBox checkBoxDevices;
	private JCheckBox checkBoxMedicalMaterials;
	private JCheckBox checkBoxConsumableMaterial;
	// private JComboBox filterComboBox = new JComboBox();
	private JComboBox locationComboBox = new JComboBox();
	private JButton generateInventoryButton;
	private StockObjectValue[] stockObjectValues = new StockObjectValue[0];
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

		this.presenterHelpId = 6;
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
		this.checkBoxMedicalMaterials = new JCheckBox("Medizinische Materialien");
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

//		// Combobox
//		this.filterComboBox.setBounds(leftPadding, contentY+lineHeight*3, leftSideMenuWidth, 24);
//		this.filterComboBox.addItem("Alphabetisch");
//		this.filterComboBox.addItem("Typ");
//		this.frame.getContentPane().add(filterComboBox);
//		// needs to be called after adding the Items
//		this.filterComboBox.addActionListener(this);

		this.locationComboBox.setBounds(leftPadding, contentY+lineHeight*3, leftSideMenuWidth, 24);
		this.locationComboBox.addItem("Alle");
		Location[] locations = DatabaseReadManager.getLocations();
		if (locations != null) {
			for (Location location : locations) {
				this.locationComboBox.addItem(location.title);
			}
		} else {
		}
		this.frame.getContentPane().add(locationComboBox);
		// needs to locationComboBox called after adding the Items
		this.locationComboBox.addActionListener(this);

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
		if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.viewDevices)) {
			this.tableData[DatabaseObject.StockObjectType.device.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.device);
			this.checkBoxDevices.setEnabled(true);
		}
		if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.viewMedicalMaterials)) {
			this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.medicalMaterial);
			this.checkBoxMedicalMaterials.setEnabled(true);
		}
		if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.viewConsumableMaterials)) {
			this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.consumableMaterial);
			this.checkBoxConsumableMaterial.setEnabled(true);
		}
	}

	private void refreshTableData() {
		this.frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		DefaultTableModel model;
		Object columnNames[] = { "Titel", "Menge", "Lagerort", "Typ"};
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

		StockObjectValue[] unfilteredDeviceValues = this.tableData[DatabaseObject.StockObjectType.device.ordinal()];
		StockObjectValue[] unfilteredmedicalMaterialValues = this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()];
		StockObjectValue[] unfilteredconsumableMaterialValues = this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()];

		ArrayList<StockObjectValue> sortedData = new ArrayList<StockObjectValue>();

		StockObjectValue[] filteredDeviceValues = null;
		StockObjectValue[] filteredmedicalMaterialValues = null;
		StockObjectValue[] filteredconsumableMaterialValues = null;

		if (this.locationComboBox.getSelectedIndex() != 0) {
			Object object = this.locationComboBox.getSelectedItem();
			if (object != null) {
				if (object instanceof String) {
					Location location = DatabaseReadManager.getLocation((String)object);
					if (location != null) {
						filteredDeviceValues =  Arrays.stream(unfilteredDeviceValues).filter(x -> x.locationID == location.id).toArray(StockObjectValue[]::new);
						filteredmedicalMaterialValues =  Arrays.stream(unfilteredmedicalMaterialValues).filter(x -> x.locationID == location.id).toArray(StockObjectValue[]::new);
						filteredconsumableMaterialValues =  Arrays.stream(unfilteredconsumableMaterialValues).filter(x -> x.locationID == location.id).toArray(StockObjectValue[]::new);
					}
				}
			}

		} else {
			filteredDeviceValues = unfilteredDeviceValues;
			filteredmedicalMaterialValues = unfilteredmedicalMaterialValues;
			filteredconsumableMaterialValues = unfilteredconsumableMaterialValues;
		}

		if (this.checkBoxDevices.isSelected() == true) {
			if (filteredDeviceValues != null) {
				sortedData.addAll(Arrays.asList(filteredDeviceValues));
			}
		}
		if (this.checkBoxMedicalMaterials.isSelected() == true) {
			if (filteredmedicalMaterialValues != null) {
				sortedData.addAll(Arrays.asList(filteredmedicalMaterialValues));
			}
		}
		if (this.checkBoxConsumableMaterial.isSelected() == true) {
			if (filteredconsumableMaterialValues != null) {
				sortedData.addAll(Arrays.asList(filteredconsumableMaterialValues));
			}
		}
		this.stockObjectValues = sortedData.toArray(new StockObjectValue[sortedData.size()]);
		Arrays.sort(this.stockObjectValues, (a,b) -> a.locationID-b.locationID);

		if (this.stockObjectValues != null) {
			for (StockObjectValue stockObjectValue : this.stockObjectValues) {
				// Switch between instanceTypes
				if (stockObjectValue instanceof DeviceValue) {
					if (this.checkBoxDevices.isSelected()) {
						DeviceValue deviceValue = (DeviceValue) stockObjectValue;
						StockObject stockObject = DatabaseReadManager.getStockObject(deviceValue.stockObjectID);
						// show all objects
						if (stockObject != null) {
							Location location = DatabaseReadManager.getLocation(stockObjectValue.locationID);
							if (location != null) {
								Object row[] = { stockObject.title, deviceValue.volume, location.title, "Ger\u00e4t"};
								model.addRow(row);
							}
						}
					}
				} else if (stockObjectValue instanceof MaterialValue) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						if (this.checkBoxMedicalMaterials.isSelected()) {
							MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
							StockObject stockObject = DatabaseReadManager.getStockObject(medicalMaterialValue.stockObjectID);
							// show all objects
							if (stockObject != null) {
								Location location = DatabaseReadManager.getLocation(stockObjectValue.locationID);
								if (location != null) {
									Object row[] = { stockObject.title, medicalMaterialValue.volume, location.title, "Medizinisches Material"};
									model.addRow(row);
								}
							}
						}
					} else if (stockObjectValue instanceof ConsumableMaterialValue) {
						if (this.checkBoxConsumableMaterial.isSelected()) {
							ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
							StockObject stockObject = DatabaseReadManager.getStockObject(consumableMaterialValue.stockObjectID);
							// show all objects
							if (stockObject != null) {
								Location location = DatabaseReadManager.getLocation(stockObjectValue.locationID);
								if (location != null) {
									Object row[] = { stockObject.title, consumableMaterialValue.volume, location.title, "Betreuungsmaterial"};
									model.addRow(row);
								}
							}
						}
					} else {
						// Do nothing with this object, its not a usable material
					}
				} else {
					// Do nothing, maybe its a vehicle
				}
			}
			this.table.setModel(model);
		} else {
			Object row[] = { "", "",  ""};
			model.addRow(row);
			this.table.setModel(model);
		}
		this.frame.setCursor(Cursor.getDefaultCursor());


//		StockObject[] unsortedDevices = this.tableData[DatabaseObject.StockObjectType.device.ordinal()];
//		StockObject[] unsortedmedicalMaterials = this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()];
//		StockObject[] unsortedconsumableMaterials = this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()];
//
//		ArrayList<StockObject> sortedData = new ArrayList<StockObject>();
//
//		StockObject[] sortedDevices = unsortedDevices;
//		StockObject[] sortedmedicalMaterials = unsortedmedicalMaterials;
//		StockObject[] sortedconsumableMaterials = unsortedconsumableMaterials;
//
//		switch (this.filterComboBox.getSelectedIndex()) {
//			case 0:
//				// Lambda sort alphabetically after adding to stockObjects
//				// Outcome: Sorted Alphabetically
//				if (unsortedDevices != null) {
//					sortedData.addAll(Arrays.asList(unsortedDevices));
//				}
//				if (unsortedmedicalMaterials != null) {
//					sortedData.addAll(Arrays.asList(unsortedmedicalMaterials));
//				}
//				if (unsortedconsumableMaterials != null) {
//					sortedData.addAll(Arrays.asList(unsortedconsumableMaterials));
//				}
//
//				this.stockObjects = sortedData.toArray(new StockObject[sortedData.size()]);
//				Arrays.sort(this.stockObjects, (a, b) -> a.title.compareToIgnoreCase(b.title));
//
//				break;
//			case 1:
//				// Lambda sort alphabetically before adding to stockObjects
//				// Outcome: Sorted by Type
//				Arrays.sort(sortedDevices, (a, b) -> a.title.compareToIgnoreCase(b.title));
//				Arrays.sort(sortedmedicalMaterials, (a, b) -> a.title.compareToIgnoreCase(b.title));
//				Arrays.sort(sortedconsumableMaterials, (a, b) -> a.title.compareToIgnoreCase(b.title));
//
//				sortedData.addAll(Arrays.asList(sortedDevices));
//				sortedData.addAll(Arrays.asList(sortedmedicalMaterials));
//				sortedData.addAll(Arrays.asList(sortedconsumableMaterials));
//
//				this.stockObjects = sortedData.toArray(new StockObject[sortedData.size()]);
//				break;
//		}
//
//		if (this.stockObjects != null) {
//			// iterate over existing objects in sortedData
//			for (StockObject stockObject : stockObjects) {
//				// Switch between instanceTypes
//				if (stockObject instanceof Device) {
//					if (this.checkBoxDevices.isSelected()) {
//						Device device = (Device) stockObject;
//						// show all objects
//						Object row[] = { device.title, device.totalVolume , "Ger\u00e4t"};
//						model.addRow(row);
//					}
//				} else if (stockObject instanceof Material) {
//					if (stockObject instanceof MedicalMaterial) {
//						if (this.checkBoxMedicalMaterials.isSelected()) {
//							MedicalMaterial medicalMaterial = (MedicalMaterial) stockObject;
//							// show all objects
//							Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume,  "Medizinisches Material"};
//							model.addRow(row);
//						}
//					} else if (stockObject instanceof ConsumableMaterial) {
//						if (this.checkBoxConsumableMaterial.isSelected()) {
//							ConsumableMaterial consumableMaterial = (ConsumableMaterial) stockObject;
//							// show all objects
//							Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, "Betreuungsmaterial"};
//							model.addRow(row);
//						}
//					} else {
//						// Do nothing with this object, its not a usable material
//					}
//				} else {
//					// Do nothing, maybe its a vehicle
//				}
//			}
//		}
//
//		this.table.setModel(model);
	}

	private void setInventorButtonEnabled() {
		if (this.checkBoxDevices.isSelected() || this.checkBoxMedicalMaterials.isSelected() || this.checkBoxConsumableMaterial.isSelected()) {
			this.generateInventoryButton.setEnabled(true);
		} else {
			this.generateInventoryButton.setEnabled(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.checkBoxDevices) {
			this.refreshTableData();
			this.setInventorButtonEnabled();
		} else if (e.getSource() == this.checkBoxMedicalMaterials) {
			this.refreshTableData();
			this.setInventorButtonEnabled();
		} else if (e.getSource() == this.checkBoxConsumableMaterial) {
			this.refreshTableData();
			this.setInventorButtonEnabled();
		} else if (e.getSource() == this.locationComboBox) {
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
					pdfGenerator.generatePDF(this.stockObjectValues, fileToSave.getAbsolutePath());
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
				}
			}


		}
	}

}
