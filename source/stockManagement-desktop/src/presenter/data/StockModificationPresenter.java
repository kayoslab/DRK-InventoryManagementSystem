package presenter.data;
import model.databaseObjects.stockObjects.StockObject;
import model.databaseObjects.stockValues.StockObjectValue;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.text.ParseException;

public class StockModificationPresenter extends Presenter implements DocumentListener {
	/** Data **/
	private StockObject stockObject;
	private StockObjectValue stockObjectValue;
	/** Buttons **/
	private JComboBox operationComboBox;
	private JButton saveButton;
	private JTextField changeTextField;
	private JLabel newVolumeField;
	private int changeVolume = 0;

	/**
	 * Create the application.
	 */
	public StockModificationPresenter(Presenter previousPresenter, StockObject stockObject, StockObjectValue stockObjectValue) {
		this.previousPresenter = previousPresenter;
		this.stockObject = stockObject;
		this.stockObjectValue = stockObjectValue;
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JLabel title = new JLabel("Bestandsänderung:");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);

		/******** Labels ********/
		JLabel titleLabel = new JLabel("Titel:");
		titleLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		this.frame.getContentPane().add(titleLabel);

		JLabel currentVolumeLabel = new JLabel("Derzeitiger Bestand:");
		currentVolumeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		this.frame.getContentPane().add(currentVolumeLabel);

		JLabel operationLabel = new JLabel("Operation:");
		operationLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		this.frame.getContentPane().add(operationLabel);

		JLabel changeLabel = new JLabel("Änderung:");
		changeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
		this.frame.getContentPane().add(changeLabel);

		JLabel newVolumeLabel = new JLabel("Neuer Bestand:");
		newVolumeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*4, leftSideMenuWidth, lineHeight);
		this.frame.getContentPane().add(newVolumeLabel);

		JLabel titleField = new JLabel(this.stockObject.title);
		titleField.setBounds(leftPadding+leftSideMenuWidth+spacing+noSpacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		this.frame.getContentPane().add(titleField);

		JLabel currentVolumeField = new JLabel("" + this.stockObjectValue.volume);
		currentVolumeField.setBounds(leftPadding+leftSideMenuWidth+spacing+noSpacing, contentY+(lineHeight+smallSpacing)*1, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		this.frame.getContentPane().add(currentVolumeField);

		this.operationComboBox = new JComboBox();
		this.operationComboBox.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		this.operationComboBox.addItem("Mehrung");
		this.operationComboBox.addItem("Minderung");
		this.frame.getContentPane().add(operationComboBox);
		// needs to be called after adding the Items
		this.operationComboBox.addActionListener(this);

		this.changeTextField = new JTextField();
		this.changeTextField.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.changeTextField);
		this.changeTextField.setColumns(10);
		this.changeTextField.addActionListener(this);
		this.changeTextField.getDocument().addDocumentListener(this);
		this.changeTextField.setText("" + this.changeVolume);

		int newVolume = this.stockObjectValue.volume + this.changeVolume;
		this.newVolumeField = new JLabel("" + newVolume);
		this.newVolumeField.setBounds(leftPadding+leftSideMenuWidth+spacing+noSpacing, contentY+(lineHeight+smallSpacing)*4, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		this.frame.getContentPane().add(this.newVolumeField);

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.saveButton.addActionListener(this);
		frame.getContentPane().add(this.saveButton);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.operationComboBox) {
			this.update();
		} else if (e.getSource() == this.changeTextField) {

		} else if (e.getSource() == this.saveButton) {
			int newVolume = 0;
			if (this.operationComboBox.getSelectedIndex() == 0) {
				newVolume = this.stockObjectValue.volume + this.changeVolume;
			} else {
				newVolume = this.stockObjectValue.volume - this.changeVolume;
			}

			this.stockObjectValue.volume = newVolume;
			this.stockObjectValue.editObject();
			this.showPreviousPresenter();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == this.changeTextField.getDocument()) {
			this.update();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == this.changeTextField.getDocument()) {
			this.update();
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		if (e.getDocument() == this.changeTextField.getDocument()) {
			this.update();
		}
	}

	private void update() {
		String textFieldValue = this.changeTextField.getText();
		DecimalFormat decimalFormat = new DecimalFormat("#");
		try {
			int number = decimalFormat.parse(textFieldValue).intValue();
			if (number == 0){
				this.changeVolume = 0;
				if (this.newVolumeField != null) {
					this.newVolumeField.setText("" + this.stockObjectValue.volume);
				}
				if (this.saveButton != null) {
					this.saveButton.setEnabled(false);
				}
			} else {
				this.changeVolume = number;
				int newVolume = 0;
				if (this.operationComboBox != null) {
					if (this.operationComboBox.getSelectedIndex() == 0) {
						newVolume = this.stockObjectValue.volume + this.changeVolume;
					} else {
						newVolume = this.stockObjectValue.volume - this.changeVolume;
					}
				}
				if (this.newVolumeField != null) {
					this.newVolumeField.setText("" + newVolume);
				}
				if (this.saveButton != null) {
					if (newVolume >= 0) {
						this.saveButton.setEnabled(true);
					} else {
						this.saveButton.setEnabled(false);
					}
				}
			}
		} catch (ParseException e) {
			this.changeVolume = 0;
			if (this.newVolumeField != null) {
				this.newVolumeField.setText("NaN");
			}
			if (this.saveButton != null) {
				this.saveButton.setEnabled(false);
			}
		}
	}
}