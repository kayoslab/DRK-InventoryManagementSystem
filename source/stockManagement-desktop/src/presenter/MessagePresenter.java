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
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.StockObject;
import model.databaseObjects.stockValues.StockObjectValue;

import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MessagePresenter extends Presenter {
	private JTable table;
	private JRadioButton radioButtonMessages;
	private JRadioButton radioButtonWarnings;

	/**
	 * Create the application.
	 */
	public MessagePresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JLabel messageLabel = new JLabel("Meldungen");
		messageLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		messageLabel.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(messageLabel);

		int radioButtonWidth = displayAreaWidth/2 - smallSpacing - (leftPadding+rightPadding);

		this.radioButtonMessages = new JRadioButton("Meldungen");
		this.radioButtonMessages.setBounds(leftPadding, contentY, radioButtonWidth, 24);
		this.radioButtonMessages.addActionListener(this);
		this.frame.getContentPane().add(this.radioButtonMessages);
		this.radioButtonMessages.setSelected(true);

		this.radioButtonWarnings = new JRadioButton("Warnungen");
		this.radioButtonWarnings.setBounds(leftPadding + (displayAreaWidth/2) + smallSpacing, contentY, radioButtonWidth, 24);
		this.radioButtonWarnings.addActionListener(this);
		this.frame.getContentPane().add(this.radioButtonWarnings);

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(this.radioButtonMessages);
		group.add(this.radioButtonWarnings);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding, contentY+lineHeight*1, displayAreaWidth, contentHeight - lineHeight);
		this.frame.getContentPane().add(scrollPane);

		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		scrollPane.setViewportView(this.table);

		this.showMessagesStockValuesData();
	}

	private void showMessagesStockValuesData() {
		Object columnNames[] = { "Titel" , "Typ", "Meldung" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		StockObjectValue[] yellowObjectValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.yellow);

		if (yellowObjectValues != null) {
			for (StockObjectValue stockObjectValue : yellowObjectValues) {
				StockObject stockObject = DatabaseReadManager.getStockObject(stockObjectValue.stockObjectID);
				if (stockObject.type.ordinal() == 1 && this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewDevices)) {
					Object row[] = { stockObject.title, DatabaseObject.StockObjectTypeStrings[stockObject.type.ordinal()]};
					model.addRow(row);
				} else if (stockObject.type.ordinal() == 2 && this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewMedicalMaterials)) {
					Object row[] = { stockObject.title, DatabaseObject.StockObjectTypeStrings[stockObject.type.ordinal()]};
					model.addRow(row);
				} else if (stockObject.type.ordinal() == 3 && this.session.currentUserCanHandleGroupRight(DatabaseObject.GroupRight.viewConsumableMaterials)) {
					Object row[] = { stockObject.title, DatabaseObject.StockObjectTypeStrings[stockObject.type.ordinal()]};
					model.addRow(row);
				}
			}
		}

		this.table.setModel(model);
	}

	private void showWarningsStockValuesData() {
		Object columnNames[] = { "Titel", "Typ", "Meldung" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		StockObjectValue[] redObjectValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.red);

		if (redObjectValues != null) {
			for (StockObjectValue stockObjectValue : redObjectValues) {
				StockObject stockObject = DatabaseReadManager.getStockObject(stockObjectValue.stockObjectID);
				Object row[] = { stockObject.title, DatabaseObject.StockObjectTypeStrings[stockObject.type.ordinal()]};
				model.addRow(row);
			}
		}

		this.table.setModel(model);
	}

	@Override
	public void presentData() {
		super.presentData();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.radioButtonMessages) {
			this.showMessagesStockValuesData();
		} else if (e.getSource() == this.radioButtonWarnings) {
			this.showWarningsStockValuesData();
		}
	}
}
