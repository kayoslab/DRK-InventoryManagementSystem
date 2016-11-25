package presenter;

import model.DatabaseReadManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.*;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataPresenter extends Presenter {
	private JTextField txtSuchen;
	private JTable table;
	private StockObject[][] tableData = new StockObject[DatabaseObject.StockObjectType.values().length][];
	private JButton logo = new JButton("");
	private JButton btnLogout = new JButton("Logout");
	private JButton back = new JButton("");
	private JButton help = new JButton("");
	private JButton btnA = new JButton("A");
	private JButton btnB = new JButton("B");
	private JButton btnC = new JButton("C");
	private JButton btnD = new JButton("D");
	private JButton btnE = new JButton("E");
	private JButton btnF = new JButton("F");
	private JButton btnG = new JButton("G");
	private JButton btnH = new JButton("H");
	private JButton btnI = new JButton("I");
	private JButton btnJ = new JButton("J");
	private JButton btnK = new JButton("K");
	private JButton btnL = new JButton("L");
	private JButton btnM = new JButton("M");
	private JButton btnN = new JButton("N");
	private JButton btnO = new JButton("O");
	private JButton btnP = new JButton("P");
	private JButton btnQ = new JButton("Q");
	private JButton btnR = new JButton("R");
	private JButton btnS = new JButton("S");
	private JButton btnT = new JButton("T");
	private JButton btnU = new JButton("U");
	private JButton btnV = new JButton("V");
	private JButton btnW = new JButton("W");
	private JButton btnX = new JButton("X");
	private JButton btnY = new JButton("Y");
	private JButton btnZ = new JButton("Z");

	/**
	 * Create the application.
	 */
	public DataPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();

		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		frame.getContentPane().add(logo);
		logo.addActionListener(this);

		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.setBounds(455, 27, 98, 22);
		frame.getContentPane().add(btnLogout);
		btnLogout.addActionListener(this);

		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		back.setIcon (new ImageIcon (imgback));
		back.setBounds(36, 18, 33, 36);
		frame.getContentPane().add(back);
		back.addActionListener(this);

		Image imgbook = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		frame.getContentPane().add(help);
		help.addActionListener(this);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 66, 788, 12);
		frame.getContentPane().add(separator);

		
		JLabel MaterialUndGeraeteDaten = new JLabel("Material- und Geräte Daten");
		MaterialUndGeraeteDaten.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		MaterialUndGeraeteDaten.setBounds(16, 98, 347, 36);
		frame.getContentPane().add(MaterialUndGeraeteDaten);

		btnA.setBounds(5, 164, 24, 21);
		btnB.setBounds(31, 164, 24, 21);
		btnC.setBounds(57, 164, 24, 21);
		btnD.setBounds(83, 164, 24, 21);
		btnE.setBounds(109, 164, 24, 21);
		btnF.setBounds(135, 164, 24, 21);
		btnG.setBounds(161, 164, 24, 21);
		btnH.setBounds(187, 164, 24, 21);
		btnI.setBounds(213, 164, 24, 21);
		btnJ.setBounds(239, 164, 24, 21);
		btnK.setBounds(265, 164, 24, 21);
		btnL.setBounds(291, 164, 24, 21);
		btnM.setBounds(317, 164, 24, 21);
		btnN.setBounds(343, 164, 24, 21);
		btnO.setBounds(369, 164, 24, 21);
		btnP.setBounds(395, 164, 24, 21);
		btnQ.setBounds(421, 164, 24, 21);
		btnR.setBounds(447, 164, 24, 21);
		btnS.setBounds(473, 164, 24, 21);
		btnT.setBounds(499, 164, 24, 21);
		btnU.setBounds(525, 164, 24, 21);
		btnV.setBounds(551, 164, 24, 21);
		btnW.setBounds(577, 164, 24, 21);
		btnX.setBounds(603, 164, 24, 21);
		btnY.setBounds(629, 164, 24, 21);
		btnZ.setBounds(655, 164, 24, 21);

		JButton[] buttons = new JButton[]{btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH,
				btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS,
				btnT, btnU, btnV, btnW, btnX, btnY, btnZ};

		for (JButton button:buttons) {
			frame.getContentPane().add(button);
			button.addActionListener(this);
		}
		
		txtSuchen = new JTextField();
		txtSuchen.setBackground(Color.WHITE);
		txtSuchen.setHorizontalAlignment(SwingConstants.CENTER);
		txtSuchen.setText("Suchen");
		txtSuchen.setToolTipText("Suchen");
		txtSuchen.setBounds(707, 160, 87, 28);
		frame.getContentPane().add(txtSuchen);
		txtSuchen.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(109, 253, 586, 257);
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

		sortedData.addAll(Arrays.asList(unsortedDevices));
		sortedData.addAll(Arrays.asList(unsortedmedicalMaterials));
		sortedData.addAll(Arrays.asList(unsortedconsumableMaterials));

		StockObject[] stockObjects = sortedData.toArray(new StockObject[sortedData.size()]);
		Arrays.sort(stockObjects, (a, b) -> a.title.compareToIgnoreCase(b.title));


		// iterate over existing objects in sortedData
		for (StockObject stockObject : stockObjects) {
			// Switch between instanceTypes
			if (stockObject instanceof Device) {
				Device device = (Device) stockObject;
				Object row[] = { device.title, device.totalVolume, "Gerät"};
				model.addRow(row);
			} else if (stockObject instanceof Material) {
				if (stockObject instanceof MedicalMaterial) {
					MedicalMaterial medicalMaterial = (MedicalMaterial) stockObject;
					Object row[] = { medicalMaterial.title, medicalMaterial.totalVolume, "Medizinisches Material"};
					model.addRow(row);
				} else if (stockObject instanceof ConsumableMaterial) {
					ConsumableMaterial consumableMaterial = (ConsumableMaterial) stockObject;
					Object row[] = { consumableMaterial.title, consumableMaterial.totalVolume, "Verbrauchsmaterial"};
					model.addRow(row);
				} else {
					// Do nothing with this object, its not a usable material
				}
			} else {
				// Do nothing, maybe its a vehicle
			}
		}

		table.setModel(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.logo) {
			// Do logo things
		} else if (e.getSource() == this.btnLogout) {
			LoginPresenter loginPresenter = new LoginPresenter();
			this.frame.dispose();
			loginPresenter.newScreen();
		} else if (e.getSource() == this.back) {
			this.showPreviousPresenter();
		} else if (e.getSource() == this.help) {
			// Do help things
		} else if (e.getSource() == this.btnA) {

		} else if (e.getSource() == this.btnB) {

		} else if (e.getSource() == this.btnC) {

		} else if (e.getSource() == this.btnD) {

		} else if (e.getSource() == this.btnE) {

		} else if (e.getSource() == this.btnF) {

		} else if (e.getSource() == this.btnG) {

		} else if (e.getSource() == this.btnH) {

		} else if (e.getSource() == this.btnI) {

		} else if (e.getSource() == this.btnJ) {

		} else if (e.getSource() == this.btnK) {

		} else if (e.getSource() == this.btnL) {

		} else if (e.getSource() == this.btnM) {

		} else if (e.getSource() == this.btnN) {

		} else if (e.getSource() == this.btnO) {

		} else if (e.getSource() == this.btnP) {

		} else if (e.getSource() == this.btnQ) {

		} else if (e.getSource() == this.btnR) {

		} else if (e.getSource() == this.btnS) {

		} else if (e.getSource() == this.btnT) {

		} else if (e.getSource() == this.btnU) {

		} else if (e.getSource() == this.btnV) {

		} else if (e.getSource() == this.btnW) {

		} else if (e.getSource() == this.btnX) {

		} else if (e.getSource() == this.btnY) {

		} else if (e.getSource() == this.btnZ) {

		}
	}
}