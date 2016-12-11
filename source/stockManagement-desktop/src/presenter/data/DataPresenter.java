package presenter.data;

import model.DatabaseReadManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.*;
import presenter.Presenter;

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
		// this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public DataPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
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
		MaterialUndGeraeteDaten.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		frame.getContentPane().add(MaterialUndGeraeteDaten);

		int i = 0;
		for (JButton button:this.characterButtons) {
			button.setBounds(leftPadding+noSpacing+(characterButtonWidth)*i,contentY,characterButtonWidth,characterButtonHeight);
			this.frame.getContentPane().add(button);
			button.addActionListener(this);
			i++;
		}

		this.searchText = new JTextField();
		this.searchText.setBackground(Color.WHITE);
		this.searchText.setHorizontalAlignment(SwingConstants.CENTER);
		this.searchText.setText("Suchen");
		this.searchText.setToolTipText("Suchen");
		int searchTextX = leftPadding+noSpacing+(characterButtonWidth)*(this.characterButtons.length) + smallSpacing;
		int searchTextWidth = width-searchTextX-rightPadding;
		this.searchText.setBounds(searchTextX, contentY, searchTextWidth, characterButtonHeight);
		this.frame.getContentPane().add(this.searchText);
		this.searchText.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		int scrollPaneHeight = height - (contentY + characterButtonHeight + smallSpacing + bottomPadding);
		scrollPane.setBounds(leftPadding, contentY + characterButtonHeight + smallSpacing, displayAreaWidth, scrollPaneHeight);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		this.table.addMouseListener(this);
		scrollPane.setViewportView(this.table);
	}

	@Override
	public void presentData() {
		super.presentData();
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
	public void showedAsPreviousPresenter() {
		super.showedAsPreviousPresenter();
		this.loadTableData();
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
		if (SwingUtilities.isLeftMouseButton(e)) {
			// get the coordinates of the mouse click
			Point p = e.getPoint();
			// get the row index that contains that coordinate
			int rowNumber = table.rowAtPoint(p);
			// Show Details
			DetailPresenter detailPresenter = new DetailPresenter(this, this.stockObjects[rowNumber]);
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