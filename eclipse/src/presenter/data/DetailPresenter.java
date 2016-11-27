package presenter.data;
import model.DatabaseReadManager;
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.*;
import presenter.Presenter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

public class DetailPresenter extends Presenter implements MouseListener {
	private StockObject stockObject;
	private StockObjectValue[] stockObjectValues;
	private JTable table;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private JButton addButton = new JButton("Hinzufügen");
	private JButton removeButton = new JButton("Entnehmen");
	private JButton editButton = new JButton("Bearbeiten");
	private JButton deleteButton = new JButton("Löschen");

	/**
	 * Create the application.
	 */
	public DetailPresenter(Presenter previousPresenter, StockObject stockObject) {
		this.previousPresenter = previousPresenter;
		this.stockObject = stockObject;
		initialize();
	}

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

		int decentContentHeight = 350;

		JLabel titleLabel = this.stockObject != null ? new JLabel(this.stockObject.title) : new JLabel("Titels");
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		titleLabel.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(titleLabel);
		
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setText("Beschreibung");
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		if (this.stockObject != null) {
			descriptionArea.setText(this.stockObject.description);
		}
		descriptionArea.setBounds(leftPadding, contentY, leftSideMenuWidth, decentContentHeight);
		this.frame.getContentPane().add(descriptionArea);

		JScrollPane scrollPane = new JScrollPane();
		int scrollPaneWidth = displayAreaWidth - (leftPadding+leftSideMenuWidth+smallSpacing);
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+smallSpacing, contentY, scrollPaneWidth, decentContentHeight);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		this.table.addMouseListener(this);
		scrollPane.setViewportView(table);

		JButton[] buttons = new JButton[]{ this.addButton, this.removeButton, this.editButton, this.deleteButton};
		int buttonWidth = (scrollPaneWidth / 4) - smallSpacing;
		int buttonY = decentContentHeight + contentY + smallSpacing;

		this.addButton.setBounds(leftPadding+leftSideMenuWidth+smallSpacing*1 , buttonY, buttonWidth, buttonHeight);
		this.removeButton.setBounds(leftPadding+leftSideMenuWidth+smallSpacing*2+buttonWidth*1, buttonY, buttonWidth, buttonHeight);
		this.editButton.setBounds(leftPadding+leftSideMenuWidth+smallSpacing*3+buttonWidth*2, buttonY, buttonWidth, buttonHeight);
		this.deleteButton.setBounds(leftPadding+leftSideMenuWidth+smallSpacing*4+buttonWidth*3, buttonY, buttonWidth, buttonHeight);

		for (JButton button : buttons) {
			button.addActionListener(this);
			this.frame.getContentPane().add(button);
		}
	}

	@Override
	public void presentData() {
		super.presentData();
		this.loadTableData();
	}

	private void loadTableData() {
		this.stockObjectValues = DatabaseReadManager.getStockObjectValues(this.stockObject);
		if (this.stockObjectValues != null) {
			if (this.stockObject instanceof Device) {
				Object columnNames[] = { "Menge", "Lagerort", "MTK", "STK"};
				DefaultTableModel model = new DefaultTableModel(columnNames, 0);

				for (StockObjectValue stockObjectValue : this.stockObjectValues) {
					if (stockObjectValue instanceof DeviceValue) {
						DeviceValue deviceValue = (DeviceValue) stockObjectValue;
						Object row[] = { deviceValue.volume, DatabaseReadManager.getLocation(stockObjectValue.locationID).title,
								this.sdf.format(deviceValue.mtkDate), this.sdf.format(deviceValue.stkDate)};
						model.addRow(row);
					}
				}
				table.setModel(model);
			} else if (this.stockObject instanceof Material) {
				if (this.stockObject instanceof MedicalMaterial) {
					Object columnNames[] = { "Menge", "Lagerort", "Haltbar bis"};
					DefaultTableModel model = new DefaultTableModel(columnNames, 0);

					for (StockObjectValue stockObjectValue : this.stockObjectValues) {
						if (stockObjectValue instanceof MedicalMaterialValue) {
							MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
							Object row[] = { medicalMaterialValue.volume, DatabaseReadManager.getLocation(stockObjectValue.locationID).title, this.sdf.format(medicalMaterialValue.date)};
							model.addRow(row);
						}
					}
					table.setModel(model);
				} else if (this.stockObject instanceof ConsumableMaterial) {
					Object columnNames[] = { "Menge", "Lagerort", "Haltbar bis"};
					DefaultTableModel model = new DefaultTableModel(columnNames, 0);

					for (StockObjectValue stockObjectValue : this.stockObjectValues) {
						if (stockObjectValue instanceof ConsumableMaterialValue) {
							ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
							Object row[] = { consumableMaterialValue.volume, DatabaseReadManager.getLocation(stockObjectValue.locationID).title, this.sdf.format(consumableMaterialValue.date)};
							model.addRow(row);
						}
					}
					table.setModel(model);
				} else {
					// Do nothing with this object, its not a usable material
				}
			} else {
				// Do nothing, maybe its a vehicle
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		if (e.getSource() == this.addButton) {

		} else if (e.getSource() == this.removeButton) {

		} else if (e.getSource() == this.editButton) {

		} else if (e.getSource() == this.deleteButton) {

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
