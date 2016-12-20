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
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class StockValuePresenter extends Presenter {
	/** Data **/
	private StockObject stockObject;
	private StockObjectValue stockObjectValue;
	Location[] locations = DatabaseReadManager.getLocations();
	/** Buttons **/
	private JButton saveButton;
	private JButton deleteButton;
	/** Data **/
	private JTextField volumeTextField;
	private JComboBox locationComboBox;
	private JDatePickerImpl dateField1;
	private JDatePickerImpl dateField2;
	private JTextField inventoryField;
	private JTextField serialField;
	private JTextField umdnsField;
	private JTextField batchField;
	private JTextField minimumStockField;
	private JTextField quotaStockField;

	/**
	 * Create the application.
	 */
	public StockValuePresenter(Presenter previousPresenter, StockObject stockObject) {
		this.previousPresenter = previousPresenter;
		this.stockObject = stockObject;
		this.initialize();

		JLabel title = new JLabel("Hinzuf\u00fcgen:");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);
	}

	/**
	 * Create the application.
	 */
	public StockValuePresenter(Presenter previousPresenter, StockObject stockObject, StockObjectValue stockObjectValue) {
		this.previousPresenter = previousPresenter;
		this.stockObject = stockObject;
		this.stockObjectValue = stockObjectValue;
		this.initialize();

		JLabel title = new JLabel("Bearbeiten:");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		this.presenterHelpId = 5;
		/******** Labels ********/
		JLabel titleLabel = new JLabel("Titel:");
		titleLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		this.frame.getContentPane().add(titleLabel);

		JLabel currentVolumeLabel = new JLabel("Bestand:");
		currentVolumeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		this.frame.getContentPane().add(currentVolumeLabel);

		JLabel locationLabel = new JLabel("Lagerort:");
		locationLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		this.frame.getContentPane().add(locationLabel);


		/******** Data ********/
		JLabel titleField = new JLabel(this.stockObject.title);
		titleField.setBounds(leftPadding+leftSideMenuWidth+spacing+noSpacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		this.frame.getContentPane().add(titleField);

		if (this.stockObjectValue != null) {
			JLabel volumeLabel = new JLabel("" + this.stockObjectValue.volume);
			volumeLabel.setBounds(leftPadding+leftSideMenuWidth+spacing+noSpacing, contentY+(lineHeight+smallSpacing)*1, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
			this.frame.getContentPane().add(volumeLabel);
		} else {
			this.volumeTextField = new JTextField();
			this.volumeTextField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*1, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
			frame.getContentPane().add(this.volumeTextField);
			this.volumeTextField.setColumns(10);
			this.volumeTextField.addActionListener(this);
			this.volumeTextField.setText("0");
		}

		this.locationComboBox = new JComboBox();
		this.locationComboBox.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		int selectedLocation = 0;
		int currentLocation = 0;
		for (Location location : this.locations) {
			this.locationComboBox.addItem(location.title);
			if (this.stockObjectValue != null) {
				if (location.id == this.stockObjectValue.locationID) {
					selectedLocation = currentLocation;
				}
			}
			currentLocation++;
		}
		this.locationComboBox.setSelectedIndex(selectedLocation);
		this.frame.getContentPane().add(locationComboBox);
		// needs to be called after adding the Items
		this.locationComboBox.addActionListener(this);

		if (this.stockObject != null) {
			if (this.stockObject instanceof Device) {
				/** Device Specific Setup **/
				JLabel dateFieldLabel1 = new JLabel("Letzte MTK:");
				dateFieldLabel1.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(dateFieldLabel1);

				JLabel dateFieldLabel2 = new JLabel("Letzte STK:");
				dateFieldLabel2.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*4, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(dateFieldLabel2);

				JLabel serialLabel = new JLabel("Seriennummer:");
				serialLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*5, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(serialLabel);

				JLabel inventoryLabel = new JLabel("Inventarnummer:");
				inventoryLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*6, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(inventoryLabel);

				JLabel umdnsLabel = new JLabel("UMDNS-Nummer:");
				umdnsLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*7, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(umdnsLabel);

				UtilDateModel model1 = new UtilDateModel();
				Properties p1 = new Properties();
				p1.put("text.today", "Heute");
				p1.put("text.month", "Monat");
				p1.put("text.year", "Jahr");

				UtilDateModel model2 = new UtilDateModel();
				Properties p2 = new Properties();
				p2.put("text.today", "Heute");
				p2.put("text.month", "Monat");
				p2.put("text.year", "Jahr");

				if (this.stockObjectValue != null) {
					if (this.stockObjectValue instanceof DeviceValue) {
						DeviceValue deviceValue = (DeviceValue) this.stockObjectValue;
						model1.setValue(deviceValue.mtkDate);
						model2.setValue(deviceValue.stkDate);
					}
				}

				JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
				datePanel1.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				this.dateField1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
				this.dateField1.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				this.frame.add(this.dateField1);

				JDatePanelImpl datePanel2 = new JDatePanelImpl(model2, p2);
				datePanel2.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*4, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				this.dateField2 = new JDatePickerImpl(datePanel2, new DateLabelFormatter());
				this.dateField2.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*4, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				this.frame.add(this.dateField2);

				this.serialField = new JTextField();
				this.serialField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*5, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				frame.getContentPane().add(this.serialField);
				this.serialField.setColumns(10);
				this.serialField.addActionListener(this);

				if (this.stockObjectValue != null) {
					if (this.stockObjectValue instanceof DeviceValue) {
						DeviceValue deviceValue = (DeviceValue) this.stockObjectValue;
						this.serialField.setText(deviceValue.serialNumber);
					}
				}

				this.inventoryField = new JTextField();
				this.inventoryField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*6, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				frame.getContentPane().add(this.inventoryField);
				this.inventoryField.setColumns(10);
				this.inventoryField.addActionListener(this);

				if (this.stockObjectValue != null) {
					if (this.stockObjectValue instanceof DeviceValue) {
						DeviceValue deviceValue = (DeviceValue) this.stockObjectValue;
						this.inventoryField.setText(deviceValue.inventoryNumber);
					}
				}

				this.umdnsField = new JTextField();
				this.umdnsField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*7, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				frame.getContentPane().add(this.umdnsField);
				this.umdnsField.setColumns(10);
				this.umdnsField.addActionListener(this);

				if (this.stockObjectValue != null) {
					if (this.stockObjectValue instanceof DeviceValue) {
						DeviceValue deviceValue = (DeviceValue) this.stockObjectValue;
						this.umdnsField.setText(deviceValue.umdns);
					}
				}

			} else if (this.stockObject instanceof Material) {
				/** Material Specific Setup **/

				/** Labels **/
				JLabel dateFieldLabel1 = new JLabel("Mindestens haltbar bis:");
				dateFieldLabel1.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(dateFieldLabel1);


				JLabel dateFieldLabel2 = new JLabel("Chargennummer:");
				dateFieldLabel2.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*4, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(dateFieldLabel2);

				JLabel minimumValueLabel = new JLabel("Mindestbestand:");
				minimumValueLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*5, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(minimumValueLabel);

				JLabel quotaStockLabel = new JLabel("Sollbestand:");
				quotaStockLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*6, leftSideMenuWidth, lineHeight);
				this.frame.getContentPane().add(quotaStockLabel);

				/** Data **/
				UtilDateModel model = new UtilDateModel();
				Properties p = new Properties();
				p.put("text.today", "Heute");
				p.put("text.month", "Monat");
				p.put("text.year", "Jahr");
				if (this.stockObjectValue != null) {
					if (this.stockObjectValue instanceof MaterialValue) {
						MaterialValue materialValue = (MaterialValue) this.stockObjectValue;
						model.setValue(materialValue.date);
					}
				}
				JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
				datePanel.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				this.dateField1 = new JDatePickerImpl(datePanel, new DateLabelFormatter());
				this.dateField1.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				this.frame.add(this.dateField1);

				this.batchField = new JTextField();
				this.batchField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*4, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				frame.getContentPane().add(this.batchField);
				this.batchField.setColumns(10);
				this.batchField.addActionListener(this);

				this.minimumStockField = new JTextField();
				this.minimumStockField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*5, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				frame.getContentPane().add(this.minimumStockField);
				this.minimumStockField.setColumns(10);
				this.minimumStockField.addActionListener(this);

				this.quotaStockField = new JTextField();
				this.quotaStockField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*6, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
				frame.getContentPane().add(this.quotaStockField);
				this.quotaStockField.setColumns(10);
				this.quotaStockField.addActionListener(this);

				if (this.stockObjectValue != null) {
					if (this.stockObjectValue instanceof MaterialValue) {
						MaterialValue materialValue = (MaterialValue) this.stockObjectValue;
						this.batchField.setText(materialValue.batchNumber);
						this.minimumStockField.setText("" + materialValue.minimumStock);
						this.quotaStockField.setText("" + materialValue.quotaStock);
					}
				}
			} else {
				// Should not be reached - only Vehicles

			}
		}

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.saveButton.addActionListener(this);
		frame.getContentPane().add(this.saveButton);

		if (this.stockObjectValue != null) {
			this.deleteButton = new JButton("l\u00f6schen");
			this.deleteButton.setBounds(displayAreaWidth-saveButton.getWidth()+leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
			this.deleteButton.addActionListener(this);
			frame.getContentPane().add(this.deleteButton);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.saveButton) {
			if (this.stockObjectValue != null) {
				System.out.println("Edit");
				this.stockObjectValue.locationID = this.locations[this.locationComboBox.getSelectedIndex()].id;
				if (this.stockObjectValue instanceof DeviceValue) {
					DeviceValue deviceValue = (DeviceValue) this.stockObjectValue;
					deviceValue.mtkDate = (Date)this.dateField1.getModel().getValue();
					deviceValue.stkDate = (Date)this.dateField2.getModel().getValue();
					deviceValue.serialNumber = this.serialField.getText();
					deviceValue.inventoryNumber = this.inventoryField.getText();
					deviceValue.umdns = this.umdnsField.getText();
					if (deviceValue.editObject()) {
						this.showPreviousPresenter();
					}
				} else if (this.stockObjectValue instanceof MaterialValue) {
					System.out.println("Material");
					MaterialValue materialValue = (MaterialValue) this.stockObjectValue;
					materialValue.date = (Date)this.dateField1.getModel().getValue();
					materialValue.batchNumber = this.batchField.getText();

					DecimalFormat decimalFormat = new DecimalFormat("#");
					int minimumStock = 0;
					int quotaStock = 0;
					try {
						minimumStock = decimalFormat.parse(this.minimumStockField.getText()).intValue();
					} catch (ParseException parseException) {
						// Cant parse Textfields to int
					}
					try {
						quotaStock = decimalFormat.parse(this.quotaStockField.getText()).intValue();
					} catch (ParseException parseException) {
						// Cant parse Textfields to int
					}

					materialValue.minimumStock = minimumStock;
					materialValue.quotaStock = quotaStock;

					if (materialValue.editObject()) {
						System.out.println("Saved");
						this.showPreviousPresenter();
					} else {
						System.out.println("Not");
					}
				}
			} else {
				int locationID = this.locations[this.locationComboBox.getSelectedIndex()].id;
				String textFieldValue = this.volumeTextField.getText();
				DecimalFormat decimalFormat = new DecimalFormat("#");
				try {
					int volume = decimalFormat.parse(textFieldValue).intValue();
					if (volume > 0){
						/** Reasonable Data **/
						if (this.stockObject instanceof Device) {
							Date mtkDate = (Date)this.dateField1.getModel().getValue();
							Date stkDate = (Date)this.dateField2.getModel().getValue();
							String serialNumber = this.serialField.getText();
							String inventoryNumber = this.inventoryField.getText();
							String umdns = this.umdnsField.getText();

							// (int id, int volume, Boolean silencedWarnings, Date mtkDate, Date stkDate,
							// int stockObjectID, int locationID, int messageID, String serialNumber,
							// String inventoryNumber, String umdns)
							DeviceValue deviceValue = new DeviceValue(0, volume, false, mtkDate,
									stkDate, this.stockObject.id, locationID, 1, serialNumber,
									inventoryNumber, umdns);
							if (deviceValue.createObject()) {
								this.showPreviousPresenter();
							}  else {
								this.stockObjectValue = null;
							}
						} else if (this.stockObject instanceof Material) {
							Date date = (Date)this.dateField1.getModel().getValue();
							String batchNumber = this.batchField.getText();
							int minimumStock = 0;
							int quotaStock = 0;

							if (this.stockObject instanceof MedicalMaterial) {
								// (int id, int volume, Boolean silencedWarnings, int stockObjectID, int locationID, int messageID, String batchNumber, Date date, int minimumStock, int quotaStock)
								MedicalMaterialValue medicalMaterialValue = new MedicalMaterialValue(0, volume,
										false, this.stockObject.id, locationID, 1,
										batchNumber, date, minimumStock, quotaStock);
								if (medicalMaterialValue.createObject()) {
									this.showPreviousPresenter();
								} else {
									this.stockObjectValue = null;
								}
							} else if (this.stockObject instanceof ConsumableMaterial) {
								// (int id, int volume, Boolean silencedWarnings, int stockObjectID, int locationID, int messageID, String batchNumber, Date date, int minimumStock, int quotaStock)
								ConsumableMaterialValue consumableMaterialValue = new ConsumableMaterialValue(0,
										volume, false, this.stockObject.id, locationID, 1,
										batchNumber, date, minimumStock, quotaStock);
								if (consumableMaterialValue.createObject()) {
									this.showPreviousPresenter();
								} else {
									this.stockObjectValue = null;
								}
							}

						}
					} else {

					}
				} catch (ParseException exception) {

				}
			}

		} else if (e.getSource() == this.deleteButton) {
			if (this.stockObjectValue != null) {
				this.stockObjectValue.deleteObject();
				this.showPreviousPresenter();
			}
		}
	}

	public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

		private String datePattern = "yyyy-MM-dd HH:mm:ss";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}
}
