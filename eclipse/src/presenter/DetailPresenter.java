package presenter;
import model.DatabaseReadManager;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DetailPresenter extends Presenter implements MouseListener {
	private StockObject stockObject;
	private StockObjectValue[] stockObjectValues;
	private JTable table;

	private JButton btnHinzufgen = new JButton("Hinzufügen");
	private JButton btnEntnehmen = new JButton("Entnehmen");
	private JButton btnBearbeiten = new JButton("Bearbeiten");
	private JButton btnLschen = new JButton("Löschen");

	/**
	 * Create the application.
	 */
	public DetailPresenter(StockObject stockObject) {
		this.stockObject = stockObject;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JButton[] buttons = new JButton[]{ btnHinzufgen, btnEntnehmen, btnBearbeiten, btnLschen};
		btnHinzufgen.setBounds(386, 450, 117, 29);
		btnEntnehmen.setBounds(560, 450, 117, 29);
		btnBearbeiten.setBounds(560, 490, 117, 29);
		btnLschen.setBounds(386, 490, 117, 29);

		for (JButton button : buttons) {
			button.addActionListener(this);
			frame.getContentPane().add(button);
		}


		JLabel titleLabel = this.stockObject != null ? new JLabel(this.stockObject.title) : new JLabel("Titels");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		titleLabel.setBounds(16, 98, 347, 36);
		frame.getContentPane().add(titleLabel);

		
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setText("Beschreibung");
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		if (this.stockObject != null) {
			descriptionArea.setText(this.stockObject.description);
		}
		descriptionArea.setBounds(16, 199, 300, 240);
		frame.getContentPane().add(descriptionArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(350, 199, 358, 240);
		frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		table.addMouseListener(this);
		scrollPane.setViewportView(table);

		this.loadTableData();
	}

	private void loadTableData() {
		Object columnNames[] = { "Menge", "Lagerort", "Datum"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		this.stockObjectValues = DatabaseReadManager.getStockObjectValues(this.stockObject);
		if (this.stockObjectValues != null) {
			for (StockObjectValue stockObjectValue : this.stockObjectValues) {
				// Switch between instanceTypes
				if (stockObjectValue instanceof DeviceValue) {
					DeviceValue deviceValue = (DeviceValue) stockObjectValue;
					Object row[] = { deviceValue.volume, "Lagerort" + deviceValue.locationID, ""};
					model.addRow(row);
				} else if (stockObjectValue instanceof MaterialValue) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
						Object row[] = { medicalMaterialValue.volume, "Lagerort" + medicalMaterialValue.locationID, ""};
						model.addRow(row);
					} else if (stockObjectValue instanceof ConsumableMaterialValue) {
						ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
						Object row[] = { consumableMaterialValue.volume, "Lagerort" + consumableMaterialValue.locationID, ""};
						model.addRow(row);
					} else {
						// Do nothing with this object, its not a usable material
					}
				} else {
					// Do nothing, maybe its a vehicle
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		if (e.getSource() == this.btnHinzufgen) {

		} else if (e.getSource() == this.btnEntnehmen) {

		} else if (e.getSource() == this.btnBearbeiten) {

		} else if (e.getSource() == this.btnLschen) {

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
