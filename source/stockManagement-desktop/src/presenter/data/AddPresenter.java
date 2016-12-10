package presenter.data;
import model.databaseObjects.DatabaseObject;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddPresenter extends Presenter {
	public DatabaseObject.ModificationType modificationType;
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;

	private JTextArea textArea;

	private JButton saveButton;

	/**
	 * Create the application.
	 */
	public AddPresenter() {
		initialize();
	}

	/**
	 * Create the application.
	 */
	public AddPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public AddPresenter(Presenter previousPresenter, DatabaseObject.ModificationType modificationType) {
		this.previousPresenter = previousPresenter;
		this.modificationType = modificationType;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JLabel title = new JLabel();
		switch (this.modificationType) {
			case deviceMenuItem:
				title = new JLabel("Gerät hinzufügen:");
				this.setupDeviceMenuItem();
				break;
			case medicalMaterialMenuItem:
				title = new JLabel("Medizinisches Material hinzufügen:");
				this.setupMedicalMaterialMenuItem();
				break;
			case consumableMaterialMenuItem:
				title = new JLabel("Verbrauchsmaterial hinzufügen:");
				this.setupConsumableMaterialMenuItem();
				break;
			case locationMenuItem:
				title = new JLabel("Lagerort hinzufügen:");
				this.setupLocationMenuItem();
				break;
			case userMenuItem:
				title = new JLabel("Benutzer hinzufügen:");
				this.setupUserMenuItem();
				break;
			case groupMenuItem:
				title = new JLabel("Gruppe hinzufügen:");
				this.setupGroupMenuItem();
				break;
		}
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);
	}

	private void setupDeviceMenuItem() {
		/******** Labels ********/
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblName);

		JLabel lblInventarnummer = new JLabel("Inventarnummer:");
		lblInventarnummer.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(lblInventarnummer);

		JLabel lblSeriennummer = new JLabel("Seriennummer:");
		lblSeriennummer.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(lblSeriennummer);

		JLabel lblOptionalerText = new JLabel("optionaler Text:");
		lblOptionalerText.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(lblOptionalerText);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		this.textField2 = new JTextField();
		this.textField2.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*1, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField2);
		this.textField2.setColumns(10);

		this.textField3 = new JTextField();
		this.textField3.setEditable(true);
		this.textField3.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField3);
		this.textField3.setColumns(10);

		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*3);
		// TextArea textArea = new TextArea();
		// textArea.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
		// frame.getContentPane().add(textArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
		this.frame.getContentPane().add(scrollPane);
		this.textArea = new JTextArea();
		// descriptionArea.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		// descriptionArea.setText("Beschreibung");
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setEditable(true);
		scrollPane.setViewportView(this.textArea);

		// Add key listener to change the TAB behavior in
		// JTextArea to transfer focus to other component forward
		// or backward.
		this.textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (e.getModifiers() > 0) {
						textArea.transferFocusBackward();
					} else {
						textArea.transferFocus();
					}
					e.consume();
				}
			}
		});


		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		frame.getContentPane().add(this.saveButton);
	}

	private void setupMedicalMaterialMenuItem() {
		/******** Labels ********/
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(228, 166, 61, 16);
		frame.getContentPane().add(lblName);

		JLabel lblChargennummer = new JLabel("Chargennummer");
		lblChargennummer.setBounds(228, 220, 131, 16);
		frame.getContentPane().add(lblChargennummer);

		JLabel lblMindestbestand = new JLabel("Meldebestand");
		lblMindestbestand.setBounds(121, 306, 131, 16);
		frame.getContentPane().add(lblMindestbestand);

		JLabel lblSollbestand = new JLabel("Sollbestand");
		lblSollbestand.setBounds(442, 306, 98, 16);
		frame.getContentPane().add(lblSollbestand);

		JLabel lblOptionalerText = new JLabel("optionaler Text");
		lblOptionalerText.setBounds(228, 384, 119, 16);
		frame.getContentPane().add(lblOptionalerText);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(367, 160, 182, 28);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		this.textField2 = new JTextField();
		this.textField2.setBounds(367, 214, 182, 28);
		frame.getContentPane().add(this.textField2);
		this.textField2.setColumns(10);

		this.textField3 = new JTextField();
		this.textField3.setBounds(228, 300, 134, 28);
		frame.getContentPane().add(this.textField3);
		this.textField3.setColumns(10);

		this.textField4 = new JTextField();
		this.textField4.setBounds(536, 300, 134, 28);
		frame.getContentPane().add(this.textField4);
		this.textField4.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(367, 386, 182, 103);
		this.frame.getContentPane().add(scrollPane);
		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setEditable(true);
		scrollPane.setViewportView(this.textArea);

		// Add key listener to change the TAB behavior in
		// JTextArea to transfer focus to other component forward
		// or backward.
		this.textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (e.getModifiers() > 0) {
						textArea.transferFocusBackward();
					} else {
						textArea.transferFocus();
					}
					e.consume();
				}
			}
		});

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(357, 518, 117, 29);
		frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	private void setupConsumableMaterialMenuItem() {
		/******** Labels ********/
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(169, 166, 61, 16);
		frame.getContentPane().add(lblName);

		JLabel lblMindestbestand = new JLabel("Meldebestand");
		lblMindestbestand.setBounds(121, 306, 131, 16);
		frame.getContentPane().add(lblMindestbestand);

		JLabel lblSollbestand = new JLabel("Sollbestand");
		lblSollbestand.setBounds(442, 306, 98, 16);
		frame.getContentPane().add(lblSollbestand);

		JLabel lblOptionalerText = new JLabel("optionaler Text");
		lblOptionalerText.setBounds(169, 386, 119, 16);
		frame.getContentPane().add(lblOptionalerText);

		JLabel lblChargennummer = new JLabel("Chargennummer (optional)");
		lblChargennummer.setBounds(169, 220, 193, 16);
		frame.getContentPane().add(lblChargennummer);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(367, 160, 182, 28);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		this.textField2 = new JTextField();
		this.textField2.setBounds(367, 214, 182, 28);
		frame.getContentPane().add(this.textField2);
		this.textField2.setColumns(10);

		this.textField3 = new JTextField();
		this.textField3.setBounds(228, 300, 134, 28);
		frame.getContentPane().add(this.textField3);
		this.textField3.setColumns(10);

		this.textField4 = new JTextField();
		this.textField4.setBounds(536, 300, 134, 28);
		frame.getContentPane().add(this.textField4);
		this.textField4.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(367, 386, 182, 103);
		this.frame.getContentPane().add(scrollPane);
		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.textArea.setEditable(true);
		scrollPane.setViewportView(this.textArea);

		// Add key listener to change the TAB behavior in
		// JTextArea to transfer focus to other component forward
		// or backward.
		this.textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (e.getModifiers() > 0) {
						textArea.transferFocusBackward();
					} else {
						textArea.transferFocus();
					}
					e.consume();
				}
			}
		});

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(357, 518, 117, 29);
		frame.getContentPane().add(this.saveButton);
	}

	private void setupLocationMenuItem() {
		/******** Labels ********/
		JLabel lblLagerortName = new JLabel("Lagerort Name");
		lblLagerortName.setBounds(239, 291, 123, 16);
		frame.getContentPane().add(lblLagerortName);

		/******** Eingabe ********/
		JTextField textField = new JTextField();
		textField.setBounds(354, 285, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(329, 355, 117, 29);
		frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	private void setupUserMenuItem() {
		/******** Labels ********/
		JLabel lblBenutzername = new JLabel("Benutzername");
		lblBenutzername.setBounds(251, 247, 88, 16);
		frame.getContentPane().add(lblBenutzername);

		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(251, 297, 61, 16);
		frame.getContentPane().add(lblPasswort);

		JLabel lblGruppe = new JLabel("Gruppe");
		lblGruppe.setBounds(251, 350, 61, 16);
		frame.getContentPane().add(lblGruppe);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(361, 241, 182, 28);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		this.textField2 = new JTextField();
		this.textField2.setBounds(361, 291, 182, 28);
		frame.getContentPane().add(this.textField2);
		this.textField2.setColumns(10);

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(343, 434, 117, 29);
		frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	private void setupGroupMenuItem() {
		/******** Labels ********/
		JLabel lblGruppenname = new JLabel("Gruppenname");
		lblGruppenname.setBounds(254, 132, 154, 14);
		frame.getContentPane().add(lblGruppenname);

		JLabel lblGruppenstatus = new JLabel("Gruppenstatus");
		lblGruppenstatus.setBounds(254, 178, 84, 14);
		frame.getContentPane().add(lblGruppenstatus);

		JLabel lblGrupenberechtigung = new JLabel("Grupenberechtigung");
		lblGrupenberechtigung.setBounds(254, 239, 154, 14);
		frame.getContentPane().add(lblGrupenberechtigung);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(382, 125, 182, 28);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		this.textField2 = new JTextField();
		this.textField2.setBounds(382, 171, 182, 28);
		frame.getContentPane().add(this.textField2);
		this.textField2.setColumns(10);

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(382, 419, 117, 29);
		frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
