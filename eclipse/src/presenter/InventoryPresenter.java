package presenter;

import model.DatabaseReadManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.*;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class InventoryPresenter extends Presenter {
	private JTable table;
	private StockObject[][] tableData = new StockObject[DatabaseObject.StockObjectType.values().length][];

	JRadioButton rdBtnAll;
	JRadioButton rdbtnNurMindestbestnde;
	JRadioButton rdbtnNurAbgelaufeneDaten;
	JCheckBox chckbxGerte;
	JCheckBox chckbxMedmaterialien;
	JCheckBox chckbxBetreuungsmaterialien;
	JComboBox filterComboBox = new JComboBox();

	/**
	 * Create the application.
	 */
	public InventoryPresenter() {
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
		Inventarliste.setBounds(16, 98, 247, 36);
		frame.getContentPane().add(Inventarliste);

		// Radiobuttons
		rdBtnAll = new JRadioButton("Alle");
		rdBtnAll.setSelected(true);
		rdBtnAll.addActionListener(this);
		rdBtnAll.setBounds(36, 166, 190, 23);
		frame.getContentPane().add(rdBtnAll);
		rdbtnNurMindestbestnde = new JRadioButton("Nur Mindestbestände");
		rdbtnNurMindestbestnde.setBounds(36, 199, 190, 23);
		rdbtnNurMindestbestnde.addActionListener(this);
		frame.getContentPane().add(rdbtnNurMindestbestnde);
		rdbtnNurAbgelaufeneDaten = new JRadioButton("Nur abgelaufene Daten");
		rdbtnNurAbgelaufeneDaten.setBounds(36, 232, 190, 23);
		rdbtnNurAbgelaufeneDaten.addActionListener(this);
		frame.getContentPane().add(rdbtnNurAbgelaufeneDaten);

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(rdBtnAll);
		group.add(rdbtnNurMindestbestnde);
		group.add(rdbtnNurAbgelaufeneDaten);

		// Checkboxes
		chckbxGerte = new JCheckBox("Geräte");
		chckbxGerte.setBounds(36, 277, 190, 23);
		chckbxGerte.setSelected(true);
		chckbxGerte.addActionListener(this);
		frame.getContentPane().add(chckbxGerte);
		chckbxMedmaterialien = new JCheckBox("MedMaterialien");
		chckbxMedmaterialien.setBounds(36, 312, 190, 23);
		chckbxMedmaterialien.setSelected(true);
		chckbxMedmaterialien.addActionListener(this);
		frame.getContentPane().add(chckbxMedmaterialien);
		chckbxBetreuungsmaterialien = new JCheckBox("Betreuungsmaterialien");
		chckbxBetreuungsmaterialien.setBounds(36, 347, 190, 23);
		chckbxBetreuungsmaterialien.setSelected(true);
		chckbxBetreuungsmaterialien.addActionListener(this);
		frame.getContentPane().add(chckbxBetreuungsmaterialien);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(36, 259, 190, 12);
		frame.getContentPane().add(separator_1);

		// Combobox
		filterComboBox.setBounds(36, 392, 166, 27);
		filterComboBox.addItem("Alphabetisch");
		filterComboBox.addItem("Typ");
		frame.getContentPane().add(filterComboBox);
		// needs to be called after adding the Items
		filterComboBox.addActionListener(this);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(250, 167, 481, 359);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		scrollPane.setViewportView(table);
		this.loadTableData();
	}

	private void loadTableData() {
		// TODO: Check if a user has the right to see all these objects.
		// Maybe you also want to disable the checkbox
		this.tableData[DatabaseObject.StockObjectType.device.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.device);
		this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.medicalMaterial);
		this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.consumableMaterial);

		this.refreshTableData();
	}

	private void refreshTableData() {
		Object columnNames[] = { "Titel", "Menge", "Typ"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		/**
		 * TODO sort the data by the given sortDescriptor for this.filterComboBox.getSelectedIndex()
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
				sortedData.addAll(Arrays.asList(unsortedDevices));
				sortedData.addAll(Arrays.asList(unsortedmedicalMaterials));
				sortedData.addAll(Arrays.asList(unsortedconsumableMaterials));

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
					if (this.chckbxGerte.isSelected()) {
						Device device = (Device) stockObject;
						if (this.rdBtnAll.isSelected()){
							// show all objects
							Object row[] = { device.title, device.totalVolume, "Gerät"};
							model.addRow(row);
						} else if (this.rdbtnNurMindestbestnde.isSelected()) {
							// do not show filtered objects
						} else if (this.rdbtnNurAbgelaufeneDaten.isSelected()) {
							// TODO: compare current Date with mtk/stk Date

						}
					}
				} else if (stockObject instanceof Material) {
					if (stockObject instanceof MedicalMaterial) {
						if (this.chckbxMedmaterialien.isSelected()) {
							MedicalMaterial medicalMaterial = (MedicalMaterial) stockObject;
							if (this.rdBtnAll.isSelected()){
								// show all objects
								Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume, "Medizinisches Material"};
								model.addRow(row);
							} else if (this.rdbtnNurMindestbestnde.isSelected()) {
								// show minimum Stock
								if (medicalMaterial.totalVolume <= medicalMaterial.minimumStock) {
									Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume, "Medizinisches Material"};
									model.addRow(row);
								} else {
									// do not show filtered objects
								}
							} else if (this.rdbtnNurAbgelaufeneDaten.isSelected()) {
								// TODO: compare current Date with medicalMaterial.date
							}
						}
					} else if (stockObject instanceof ConsumableMaterial) {
						if (this.chckbxBetreuungsmaterialien.isSelected()) {
							ConsumableMaterial consumableMaterial = (ConsumableMaterial) stockObject;
							if (this.rdBtnAll.isSelected()){
								// show all objects
								Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, "Verbrauchsmaterial"};
								model.addRow(row);
							} else if (this.rdbtnNurMindestbestnde.isSelected()) {
								if (consumableMaterial.totalVolume <= consumableMaterial.minimumStock) {
									Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, "Verbrauchsmaterial"};
									model.addRow(row);
								} else {
									// do not show filtered objects
								}
							} else if (this.rdbtnNurAbgelaufeneDaten.isSelected()) {
								// TODO: compare current Date with consumableMaterial.date
							}
						}
					} else {
						// Do nothing with this object, its not a usable material
					}
				} else {
					// Do nothing, maybe its a vehicle
				}
			}
		}

		table.setModel(model);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.rdBtnAll) {
			this.refreshTableData();
		} else if (e.getSource() == this.rdbtnNurMindestbestnde) {
			this.refreshTableData();
		} else if (e.getSource() == this.rdbtnNurAbgelaufeneDaten) {
			this.refreshTableData();
		} else if (e.getSource() == this.chckbxGerte) {
			this.refreshTableData();
		} else if (e.getSource() == this.chckbxMedmaterialien) {
			this.refreshTableData();
		} else if (e.getSource() == this.chckbxBetreuungsmaterialien) {
			this.refreshTableData();
		} else if (e.getSource() == this.filterComboBox) {
			this.refreshTableData();
		}
	}

}
