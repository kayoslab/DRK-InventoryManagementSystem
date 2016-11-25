package presenter;

import model.DatabaseReadManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DataPresenter extends Presenter implements MouseListener {
	private JTextField searchText;
	private JTable table;
	private StockObject[][] tableData = new StockObject[DatabaseObject.StockObjectType.values().length][];
	private StockObject[] stockObjects;

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
	private JButton[] characterButtons = new JButton[]{btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH,
			btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW,
			btnX, btnY, btnZ};

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
		super.setupTopLayout();

		JLabel MaterialUndGeraeteDaten = new JLabel("Material- und Geräte Daten");
		MaterialUndGeraeteDaten.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		MaterialUndGeraeteDaten.setBounds(leftPadding, 98, (width-leftPadding-rightPadding), 36);
		frame.getContentPane().add(MaterialUndGeraeteDaten);

		this.btnA.setBounds(5, 164, 24, 21);
		this.btnB.setBounds(31, 164, 24, 21);
		this.btnC.setBounds(57, 164, 24, 21);
		this.btnD.setBounds(83, 164, 24, 21);
		this.btnE.setBounds(109, 164, 24, 21);
		this.btnF.setBounds(135, 164, 24, 21);
		this.btnG.setBounds(161, 164, 24, 21);
		this.btnH.setBounds(187, 164, 24, 21);
		this.btnI.setBounds(213, 164, 24, 21);
		this.btnJ.setBounds(239, 164, 24, 21);
		this.btnK.setBounds(265, 164, 24, 21);
		this.btnL.setBounds(291, 164, 24, 21);
		this.btnM.setBounds(317, 164, 24, 21);
		this.btnN.setBounds(343, 164, 24, 21);
		this.btnO.setBounds(369, 164, 24, 21);
		this.btnP.setBounds(395, 164, 24, 21);
		this.btnQ.setBounds(421, 164, 24, 21);
		this.btnR.setBounds(447, 164, 24, 21);
		this.btnS.setBounds(473, 164, 24, 21);
		this.btnT.setBounds(499, 164, 24, 21);
		this.btnU.setBounds(525, 164, 24, 21);
		this.btnV.setBounds(551, 164, 24, 21);
		this.btnW.setBounds(577, 164, 24, 21);
		this.btnX.setBounds(603, 164, 24, 21);
		this.btnY.setBounds(629, 164, 24, 21);
		this.btnZ.setBounds(655, 164, 24, 21);

		for (JButton button:this.characterButtons) {
			this.frame.getContentPane().add(button);
			button.addActionListener(this);
		}

		this.searchText = new JTextField();
		this.searchText.setBackground(Color.WHITE);
		this.searchText.setHorizontalAlignment(SwingConstants.CENTER);
		this.searchText.setText("Suchen");
		this.searchText.setToolTipText("Suchen");
		this.searchText.setBounds(707, 160, 87, 28);
		this.frame.getContentPane().add(this.searchText);
		this.searchText.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(109, 253, 586, 257);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		this.table.addMouseListener(this);
		scrollPane.setViewportView(this.table);

		this.loadTableData();
	}

	private void loadTableData() {
		// TODO: Check if a user has the right to see all these objects.

		this.tableData[DatabaseObject.StockObjectType.device.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.device);
		this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.medicalMaterial);
		this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()] = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.consumableMaterial);

		StockObject[] unsortedDevices = this.tableData[DatabaseObject.StockObjectType.device.ordinal()];
		StockObject[] unsortedmedicalMaterials = this.tableData[DatabaseObject.StockObjectType.medicalMaterial.ordinal()];
		StockObject[] unsortedconsumableMaterials = this.tableData[DatabaseObject.StockObjectType.consumableMaterial.ordinal()];

		ArrayList<StockObject> sortedData = new ArrayList<StockObject>();

		sortedData.addAll(Arrays.asList(unsortedDevices));
		sortedData.addAll(Arrays.asList(unsortedmedicalMaterials));
		sortedData.addAll(Arrays.asList(unsortedconsumableMaterials));

		stockObjects = sortedData.toArray(new StockObject[sortedData.size()]);
		Arrays.sort(stockObjects, (a, b) -> a.title.compareToIgnoreCase(b.title));

		this.fillTableWithData();
	}

	private void fillTableWithData() {
		Object columnNames[] = { "Titel", "Menge", "Typ"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		if (stockObjects != null) {
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
		}

		table.setModel(model);
	}

	// TODO: Try this function with data in the Table
	private void scrollToVisible(int rowIndex, int vColIndex) {
		if (!(this.table.getParent() instanceof JViewport)) {
			return;
		}
		JViewport viewport = (JViewport)this.table.getParent();

		// This rectangle is relative to the table where the
		// northwest corner of cell (0,0) is always (0,0).
		Rectangle rect = this.table.getCellRect(rowIndex, vColIndex, true);

		// The location of the viewport relative to the table
		Point pt = viewport.getViewPosition();

		// Translate the cell location so that it is relative
		// to the view, assuming the northwest corner of the
		// view is (0,0)
		rect.setLocation(rect.x-pt.x, rect.y-pt.y);

		this.table.scrollRectToVisible(rect);

		// Scroll the area into view
		//viewport.scrollRectToVisible(rect);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		// it's a Trap / Character
		if (e.getSource() == this.btnA) {
			this.scrollToVisible(0,0);
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

	@Override
	public void mouseClicked(MouseEvent e) {
		// Left mouse click
		if ( SwingUtilities.isLeftMouseButton(e) ) {
			// get the coordinates of the mouse click
			Point p = e.getPoint();
			// get the row index that contains that coordinate
			int rowNumber = table.rowAtPoint(p);
			// Show Details
			DetailPresenter detailPresenter = new DetailPresenter(this.stockObjects[rowNumber]);
			detailPresenter.previousPresenter = this;
			detailPresenter.newScreen();
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