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
	private JRadioButton radioButtonAll;
	private JRadioButton radioButtonMinimumStockOnly;
	private JRadioButton radioButtonquotaStockOnly;
	private JCheckBox checkBoxDevices;
	private JCheckBox checkBoxMedicalMaterials;
	private JCheckBox checkBoxConsumableMaterial;
	private JComboBox filterComboBox = new JComboBox();

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

		// Radiobuttons
		this.radioButtonAll = new JRadioButton("Alle");
		this.radioButtonAll.setSelected(true);
		this.radioButtonAll.addActionListener(this);
		this.radioButtonAll.setBounds(leftPadding, contentY+lineHeight*0, leftSideMenuWidth, 24);
		this.frame.getContentPane().add(this.radioButtonAll);

		this.radioButtonquotaStockOnly = new JRadioButton("Nur Sollbest\u00e4nde");
		this.radioButtonquotaStockOnly.setBounds(leftPadding, contentY+lineHeight*1, leftSideMenuWidth, 24);
		this.radioButtonquotaStockOnly.addActionListener(this);
		this.frame.getContentPane().add(this.radioButtonquotaStockOnly);

		this.radioButtonMinimumStockOnly = new JRadioButton("Nur Mindestbest\u00e4nde");
		this.radioButtonMinimumStockOnly.setBounds(leftPadding, contentY+lineHeight*2, leftSideMenuWidth, 24);
		this.radioButtonMinimumStockOnly.addActionListener(this);
		this.frame.getContentPane().add(this.radioButtonMinimumStockOnly);

		JSeparator comboBoxSeperator = new JSeparator();
		comboBoxSeperator.setBounds(leftPadding, (contentY+lineHeight*3)+lineHeight/4, leftSideMenuWidth, 12);
		this.frame.getContentPane().add(comboBoxSeperator);

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioButtonAll);
		group.add(this.radioButtonMinimumStockOnly);
		group.add(this.radioButtonquotaStockOnly);

		// Checkboxes
		this.checkBoxDevices = new JCheckBox("Ger\u00e4te");
		this.checkBoxDevices.setBounds(leftPadding, contentY+lineHeight*4, leftSideMenuWidth, 24);
		this.checkBoxDevices.setSelected(true);
		this.checkBoxDevices.addActionListener(this);
		this.frame.getContentPane().add(this.checkBoxDevices);
		this.checkBoxMedicalMaterials = new JCheckBox("MedMaterialien");
		this.checkBoxMedicalMaterials.setBounds(leftPadding, contentY+lineHeight*5, leftSideMenuWidth, 24);
		this.checkBoxMedicalMaterials.setSelected(true);
		this.checkBoxMedicalMaterials.addActionListener(this);
		this.frame.getContentPane().add(this.checkBoxMedicalMaterials);
		this.checkBoxConsumableMaterial = new JCheckBox("Betreuungsmaterialien");
		this.checkBoxConsumableMaterial.setBounds(leftPadding, contentY+lineHeight*6, leftSideMenuWidth, 24);
		this.checkBoxConsumableMaterial.setSelected(true);
		this.checkBoxConsumableMaterial.addActionListener(this);
		this.frame.getContentPane().add(this.checkBoxConsumableMaterial);

		// Combobox
		this.filterComboBox.setBounds(leftPadding, contentY+lineHeight*7, leftSideMenuWidth, 24);
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

	}

	@Override
	public void presentData() {
		super.presentData();
		this.loadTableData();
		this.refreshTableData();
	}

	private void loadTableData() {
		// TODO: Check if a user has the right to see all these objects.
		// Maybe you also want to disable the checkbox
		this.tableData[DatabaseObject.StockObjectType.device.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.device);
		this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.medicalMaterial);
		this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.consumableMaterial);
	}

	private void refreshTableData() {
		DefaultTableModel model;
		if (this.radioButtonMinimumStockOnly.isSelected()) {
			Object columnNames[] = { "Titel", "Menge", "Mindesbestand", "Typ"};
			model = new DefaultTableModel(columnNames, 0);
		} else if (this.radioButtonquotaStockOnly.isSelected()) {
			Object columnNames[] = { "Titel", "Menge", "Sollbestand", "Typ"};
			model = new DefaultTableModel(columnNames, 0);
		} else {
			Object columnNames[] = { "Titel", "Menge", "Mindesbestand", "Typ"};
			model = new DefaultTableModel(columnNames, 0);
		}

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
					if (this.checkBoxDevices.isSelected()) {
						Device device = (Device) stockObject;
						if (this.radioButtonAll.isSelected()){
							// show all objects
							Object row[] = { device.title, device.totalVolume,  0 , "Ger\u00e4t"};
							model.addRow(row);
						} else if (this.radioButtonMinimumStockOnly.isSelected()) {
							// do not show filtered objects
						} else if (this.radioButtonquotaStockOnly.isSelected()) {
							Object row[] = { device.title, device.totalVolume, 0, "Ger\u00e4t"};
							model.addRow(row);
						}
					}
				} else if (stockObject instanceof Material) {
					if (stockObject instanceof MedicalMaterial) {
						if (this.checkBoxMedicalMaterials.isSelected()) {
							MedicalMaterial medicalMaterial = (MedicalMaterial) stockObject;
							if (this.radioButtonAll.isSelected()){
								// show all objects
								Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume, medicalMaterial.minimumStock, "Medizinisches Material"};
								model.addRow(row);
							} else if (this.radioButtonMinimumStockOnly.isSelected()) {
								// show minimum Stock
								if (medicalMaterial.totalVolume <= medicalMaterial.minimumStock) {
									Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume, medicalMaterial.minimumStock, "Medizinisches Material"};
									model.addRow(row);
								} else {
									// do not show filtered objects
								}
							} else if (this.radioButtonquotaStockOnly.isSelected()) {
								Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume, medicalMaterial.quotaStock, "Medizinisches Material"};
								model.addRow(row);
							}
						}
					} else if (stockObject instanceof ConsumableMaterial) {
						if (this.checkBoxConsumableMaterial.isSelected()) {
							ConsumableMaterial consumableMaterial = (ConsumableMaterial) stockObject;
							if (this.radioButtonAll.isSelected()){
								// show all objects
								Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, consumableMaterial.minimumStock, "Verbrauchsmaterial"};
								model.addRow(row);
							} else if (this.radioButtonMinimumStockOnly.isSelected()) {
								if (consumableMaterial.totalVolume <= consumableMaterial.minimumStock) {
									Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, consumableMaterial.minimumStock, "Verbrauchsmaterial"};
									model.addRow(row);
								} else {
									// do not show filtered objects
								}
							} else if (this.radioButtonquotaStockOnly.isSelected()) {
								Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, consumableMaterial.quotaStock, "Verbrauchsmaterial"};
								model.addRow(row);
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

		this.table.setModel(model);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.radioButtonAll) {
			this.refreshTableData();
		} else if (e.getSource() == this.radioButtonMinimumStockOnly) {
			this.refreshTableData();
		} else if (e.getSource() == this.radioButtonquotaStockOnly) {
			this.refreshTableData();
		} else if (e.getSource() == this.checkBoxDevices) {
			this.refreshTableData();
		} else if (e.getSource() == this.checkBoxMedicalMaterials) {
			this.refreshTableData();
		} else if (e.getSource() == this.checkBoxConsumableMaterial) {
			this.refreshTableData();
		} else if (e.getSource() == this.filterComboBox) {
			this.refreshTableData();
		}
	}

}
