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
import model.DatabaseWriteManager;
import model.Session;
import model.databaseObjects.DatabaseObject;
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
	private JButton addButton = new JButton("Hinzuf\u00fcgen");
	private JButton editButton = new JButton("Bearbeiten");
	private JButton shiftButton = new JButton("Bestands\u00e4nderung");

	/**
	 * Create the application.
	 */
	public DetailPresenter(Presenter previousPresenter, StockObject stockObject) {
		this.previousPresenter = previousPresenter;
		this.stockObject = stockObject;
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 */
	@Override
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
		descriptionArea.setEditable(false);
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

		JButton[] buttons = new JButton[]{ this.addButton, this.editButton, this.shiftButton};
		int buttonWidth = (scrollPaneWidth / buttons.length) - smallSpacing;
		int buttonY = decentContentHeight + contentY + smallSpacing;

		this.addButton.setBounds(leftPadding+leftSideMenuWidth+smallSpacing*1 , buttonY, buttonWidth, buttonHeight);
		this.editButton.setBounds(leftPadding+leftSideMenuWidth+smallSpacing*2+buttonWidth*1, buttonY, buttonWidth, buttonHeight);
		this.shiftButton.setBounds(leftPadding+leftSideMenuWidth+smallSpacing*3+buttonWidth*2, buttonY, buttonWidth, buttonHeight);

		this.editButton.setEnabled(false);
		this.shiftButton.setEnabled(false);

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
				Object columnNames[] = { "Lagerort", "Lagerbestand", "MTK", "STK" };
				DefaultTableModel model = new DefaultTableModel(columnNames, 0);

				for (StockObjectValue stockObjectValue : this.stockObjectValues) {
					if (stockObjectValue instanceof DeviceValue) {
						DeviceValue deviceValue = (DeviceValue) stockObjectValue;
						String mtkDate = "kein Datum";
						String stkDate = "kein Datum";
						if (deviceValue.mtkDate != null) {
							mtkDate = this.sdf.format(deviceValue.mtkDate);
						}
						if (deviceValue.stkDate != null) {
							stkDate = this.sdf.format(deviceValue.stkDate);
						}
						Object row[] = { DatabaseReadManager.getLocation(stockObjectValue.locationID).title,
								deviceValue.volume, mtkDate, stkDate,
						};
						model.addRow(row);
					}
				}
				table.setModel(model);
			} else if (this.stockObject instanceof Material) {
				if (this.stockObject instanceof MedicalMaterial) {
					Object columnNames[] = { "Lagerort", "Lagerbestand", "Mindestbestand", "Sollbestand", "Haltbar bis" };
					DefaultTableModel model = new DefaultTableModel(columnNames, 0);

					for (StockObjectValue stockObjectValue : this.stockObjectValues) {
						if (stockObjectValue instanceof MedicalMaterialValue) {
							MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
							String date = "kein Datum";
							if (medicalMaterialValue.date != null) {
								date = this.sdf.format(medicalMaterialValue.date);
							}
							Object row[] = { DatabaseReadManager.getLocation(stockObjectValue.locationID).title,
									medicalMaterialValue.volume, medicalMaterialValue.minimumStock,
									medicalMaterialValue.quotaStock, date
									 };
							model.addRow(row);
						}
					}
					table.setModel(model);
				} else if (this.stockObject instanceof ConsumableMaterial) {
					Object columnNames[] = { "Lagerort", "Lagerbestand", "Mindestbestand", "Sollbestand", "Haltbar bis"};
					DefaultTableModel model = new DefaultTableModel(columnNames, 0);

					for (StockObjectValue stockObjectValue : this.stockObjectValues) {
						if (stockObjectValue instanceof ConsumableMaterialValue) {
							ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
							String date = "kein Datum";
							if (consumableMaterialValue.date != null) {
								date = this.sdf.format(consumableMaterialValue.date);
							}
							Object row[] = { DatabaseReadManager.getLocation(stockObjectValue.locationID).title ,
									consumableMaterialValue.volume, consumableMaterialValue.minimumStock,
									consumableMaterialValue.quotaStock, date
									};
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

	private void activateButtons() {
		if (this.stockObject instanceof Device) {
			if (this.session.currentUserCanChangeDeviceStock()) {
				this.editButton.setEnabled(true);
				this.shiftButton.setEnabled(true);
			}
		} else if (this.stockObject instanceof Material) {
			if (this.stockObject instanceof MedicalMaterial) {
				if (this.session.currentUserCanChangeMedicalMaterialStock()) {
					this.editButton.setEnabled(true);
					this.shiftButton.setEnabled(true);
				}
			} else if (this.stockObject instanceof ConsumableMaterial) {
				if (this.session.currentUserCanChangeConsumableMaterialStock()) {
					this.editButton.setEnabled(true);
					this.shiftButton.setEnabled(true);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);

		if (e.getSource() == this.addButton) {
			StockValuePresenter stockValuePresenter = new StockValuePresenter(this, this.stockObject);
			stockValuePresenter.newScreen();
		} else if (e.getSource() == this.editButton) {
			int selectedIndex = this.table.getSelectedRow();
			StockObjectValue selectedValue = this.stockObjectValues[selectedIndex];
			StockValuePresenter stockValuePresenter = new StockValuePresenter(this, this.stockObject, selectedValue);
			stockValuePresenter.newScreen();
		} else if (e.getSource() == this.shiftButton) {
			int selectedIndex = this.table.getSelectedRow();
			StockObjectValue selectedValue = this.stockObjectValues[selectedIndex];
			StockModificationPresenter stockModificationPresenter = new StockModificationPresenter(this, this.stockObject, selectedValue);
			stockModificationPresenter.newScreen();
		}
	}

	@Override
	public void showedAsPreviousPresenter() {
		super.showedAsPreviousPresenter();
		loadTableData();
		this.editButton.setEnabled(false);
		this.shiftButton.setEnabled(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Left mouse click
		if ( SwingUtilities.isLeftMouseButton(e) ) {
			// get the coordinates of the mouse click
			Point p = e.getPoint();

			// get the row index that contains that coordinate
			int rowNumber = table.rowAtPoint(p);

			this.activateButtons();
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
