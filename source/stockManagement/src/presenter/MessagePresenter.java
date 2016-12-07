package presenter;
import model.DatabaseReadManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockValues.StockObjectValue;

import java.awt.event.ActionEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MessagePresenter extends Presenter {
	private JTable table;

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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding, contentY, displayAreaWidth, contentHeight);
		this.frame.getContentPane().add(scrollPane);

		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		scrollPane.setViewportView(this.table);


		Object columnNames[] = { "Menge", "Lagerort", "Bestand", "Meldegrund"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		// ArrayList<StockObjectValue> sortedYellowData = new ArrayList<StockObjectValue>();
		// StockObjectValue[] yellowDeviceObjectValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.yellow);
		// StockObjectValue[] yellowMedicalObjectValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.yellow);
		// StockObjectValue[] yellowConsumableObjectValues = DatabaseReadManager.getStockObjectValues(DatabaseObject.StockValueMessage.yellow);

		// Lambda sort alphabetically after adding to stockObjects
		// Outcome: Sorted Alphabetically
		// sortedYellowData.addAll(Arrays.asList(yellowDeviceObjectValues));
		// sortedYellowData.addAll(Arrays.asList(yellowMedicalObjectValues));
		// sortedYellowData.addAll(Arrays.asList(yellowConsumableObjectValues));

	}

	@Override
	public void presentData() {
		super.presentData();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
