package presenter.data;
import model.databaseObjects.DatabaseObject;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AddPresenter extends Presenter {
	DatabaseObject.ModificationType modificationType;

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
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(276, 160, 46, 14);
		frame.getContentPane().add(lblName);

		JLabel lblInventarnummer = new JLabel("Inventarnummer");
		lblInventarnummer.setBounds(276, 198, 103, 14);
		frame.getContentPane().add(lblInventarnummer);

		JLabel lblSeriennummer = new JLabel("Seriennummer");
		lblSeriennummer.setBounds(276, 234, 103, 16);
		frame.getContentPane().add(lblSeriennummer);

		JLabel lblOptionalerText = new JLabel("optionaler Text");
		lblOptionalerText.setBounds(276, 276, 85, 14);
		frame.getContentPane().add(lblOptionalerText);

		JTextField textField = new JTextField();
		textField.setBounds(389, 157, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JTextField textField_1 = new JTextField();
		textField_1.setBounds(389, 192, 182, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JTextField textField_2 = new JTextField();
		textField_2.setBounds(389, 231, 182, 28);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);

		TextArea textArea = new TextArea();
		textArea.setBounds(389, 276, 182, 103);
		frame.getContentPane().add(textArea);

		JButton btnSpeichern = new JButton("speichern");
		btnSpeichern.setBounds(393, 415, 117, 29);
		frame.getContentPane().add(btnSpeichern);
	}

	private void setupMedicalMaterialMenuItem() {
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(228, 166, 61, 16);
		frame.getContentPane().add(lblName);

		JTextField textField = new JTextField();
		textField.setBounds(367, 160, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblChargennummer = new JLabel("Chargennummer");
		lblChargennummer.setBounds(228, 220, 131, 16);
		frame.getContentPane().add(lblChargennummer);

		JTextField textField_1 = new JTextField();
		textField_1.setBounds(367, 214, 182, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblMindestbestand = new JLabel("Meldebestand");
		lblMindestbestand.setBounds(121, 306, 131, 16);
		frame.getContentPane().add(lblMindestbestand);

		JLabel lblSollbestand = new JLabel("Sollbestand");
		lblSollbestand.setBounds(442, 306, 98, 16);
		frame.getContentPane().add(lblSollbestand);

		JLabel lblOptionalerText = new JLabel("optionaler Text");
		lblOptionalerText.setBounds(228, 384, 119, 16);
		frame.getContentPane().add(lblOptionalerText);

		JTextField textField_4 = new JTextField();
		textField_4.setBounds(228, 300, 134, 28);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		JTextField textField_5 = new JTextField();
		textField_5.setBounds(536, 300, 134, 28);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);

		TextArea textArea = new TextArea();
		textArea.setBounds(367, 386, 182, 103);
		frame.getContentPane().add(textArea);

		JButton btnSpeichern = new JButton("speichern");
		btnSpeichern.setBounds(357, 518, 117, 29);
		frame.getContentPane().add(btnSpeichern);
		btnSpeichern.addActionListener(this);
	}

	private void setupConsumableMaterialMenuItem() {
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(169, 166, 61, 16);
		frame.getContentPane().add(lblName);

		JTextField textField = new JTextField();
		textField.setBounds(367, 160, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblChargennummer = new JLabel("Chargennummer (optional)");
		lblChargennummer.setBounds(169, 220, 193, 16);
		frame.getContentPane().add(lblChargennummer);

		JTextField textField_1 = new JTextField();
		textField_1.setBounds(367, 214, 182, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblMindestbestand = new JLabel("Meldebestand");
		lblMindestbestand.setBounds(121, 306, 131, 16);
		frame.getContentPane().add(lblMindestbestand);

		JLabel lblSollbestand = new JLabel("Sollbestand");
		lblSollbestand.setBounds(442, 306, 98, 16);
		frame.getContentPane().add(lblSollbestand);

		JLabel lblOptionalerText = new JLabel("optionaler Text");
		lblOptionalerText.setBounds(169, 386, 119, 16);
		frame.getContentPane().add(lblOptionalerText);

		JTextField textField_4 = new JTextField();
		textField_4.setBounds(228, 300, 134, 28);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);

		JTextField textField_5 = new JTextField();
		textField_5.setBounds(536, 300, 134, 28);
		frame.getContentPane().add(textField_5);
		textField_5.setColumns(10);

		TextArea textArea = new TextArea();
		textArea.setBounds(367, 386, 182, 103);
		frame.getContentPane().add(textArea);

		JButton btnSpeichern = new JButton("speichern");
		btnSpeichern.setBounds(357, 518, 117, 29);
		frame.getContentPane().add(btnSpeichern);
	}

	private void setupLocationMenuItem() {
		JLabel lblLagerortName = new JLabel("Lagerort Name");
		lblLagerortName.setBounds(239, 291, 123, 16);
		frame.getContentPane().add(lblLagerortName);

		JTextField textField = new JTextField();
		textField.setBounds(354, 285, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnSpeichern = new JButton("speichern");
		btnSpeichern.setBounds(329, 355, 117, 29);
		frame.getContentPane().add(btnSpeichern);
		btnSpeichern.addActionListener(this);
	}

	private void setupUserMenuItem() {
		JLabel lblBenutzername = new JLabel("Benutzername");
		lblBenutzername.setBounds(251, 247, 88, 16);
		frame.getContentPane().add(lblBenutzername);

		JLabel lblPasswort = new JLabel("Passwort");
		lblPasswort.setBounds(251, 297, 61, 16);
		frame.getContentPane().add(lblPasswort);

		JTextField textField = new JTextField();
		textField.setBounds(361, 241, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JTextField textField_1 = new JTextField();
		textField_1.setBounds(361, 291, 182, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblGruppe = new JLabel("Gruppe");
		lblGruppe.setBounds(251, 350, 61, 16);
		frame.getContentPane().add(lblGruppe);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(361, 346, 182, 27);
		frame.getContentPane().add(comboBox);
		comboBox.addItem("Bitte auswählen...");

		JButton btnSpeichern = new JButton("speichern");
		btnSpeichern.setBounds(343, 434, 117, 29);
		frame.getContentPane().add(btnSpeichern);
		btnSpeichern.addActionListener(this);
	}

	private void setupGroupMenuItem() {
		JLabel lblGruppenname = new JLabel("Gruppenname");
		lblGruppenname.setBounds(254, 132, 154, 14);
		frame.getContentPane().add(lblGruppenname);

		JTextField textField = new JTextField();
		textField.setBounds(382, 125, 182, 28);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JLabel lblGruppenstatus = new JLabel("Gruppenstatus");
		lblGruppenstatus.setBounds(254, 178, 84, 14);
		frame.getContentPane().add(lblGruppenstatus);

		JTextField textField_1 = new JTextField();
		textField_1.setBounds(382, 171, 182, 28);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);

		JLabel lblGrupenberechtigung = new JLabel("Grupenberechtigung");
		lblGrupenberechtigung.setBounds(254, 239, 154, 14);
		frame.getContentPane().add(lblGrupenberechtigung);

		JCheckBox chckbxMedizinischeMaterialienHinzufgen = new JCheckBox("Medizinische Materialien hinzuf\u00FCgen");
		chckbxMedizinischeMaterialienHinzufgen.setBounds(382, 235, 195, 23);
		chckbxMedizinischeMaterialienHinzufgen.setBackground(Color.WHITE);
		frame.getContentPane().add(chckbxMedizinischeMaterialienHinzufgen);

		JCheckBox chckbxMedizinischeMaterialienBearbeiten = new JCheckBox("Medizinische Materialien bearbeiten");
		chckbxMedizinischeMaterialienBearbeiten.setBounds(382, 260, 195, 23);
		chckbxMedizinischeMaterialienBearbeiten.setBackground(Color.WHITE);
		frame.getContentPane().add(chckbxMedizinischeMaterialienBearbeiten);

		JCheckBox chckbxBetreuungsmaterialienHinzufgen = new JCheckBox("Betreuungsmaterialien hinzuf\u00FCgen");
		chckbxBetreuungsmaterialienHinzufgen.setBounds(382, 286, 189, 23);
		chckbxBetreuungsmaterialienHinzufgen.setBackground(Color.WHITE);
		frame.getContentPane().add(chckbxBetreuungsmaterialienHinzufgen);

		JCheckBox chckbxBetreuungsmaterialienBearbeiten = new JCheckBox("Betreuungsmaterialien bearbeiten");
		chckbxBetreuungsmaterialienBearbeiten.setBounds(382, 312, 187, 23);
		chckbxBetreuungsmaterialienBearbeiten.setBackground(Color.WHITE);
		frame.getContentPane().add(chckbxBetreuungsmaterialienBearbeiten);

		JCheckBox chckbxGerteHinzufgen = new JCheckBox("Ger\u00E4te hinzuf\u00FCgen");
		chckbxGerteHinzufgen.setBounds(382, 338, 115, 23);
		chckbxGerteHinzufgen.setBackground(Color.WHITE);
		frame.getContentPane().add(chckbxGerteHinzufgen);

		JCheckBox chckbxGerteBearbeiten = new JCheckBox("Ger\u00E4te bearbeiten");
		chckbxGerteBearbeiten.setBounds(382, 364, 113, 23);
		chckbxGerteBearbeiten.setBackground(Color.WHITE);
		frame.getContentPane().add(chckbxGerteBearbeiten);

		JButton btnSpeichern = new JButton("speichern");
		btnSpeichern.setBounds(382, 419, 117, 29);
		frame.getContentPane().add(btnSpeichern);
		//Image imgback1 = new ImageIcon (this.getClass().getResource("/back-button.jpg")).getImage();
		//Image imgbook1 = new ImageIcon (this.getClass().getResource("/book-button.jpg")).getImage();
		btnSpeichern.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
