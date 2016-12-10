package presenter.data;
import model.DatabaseReadManager;
import model.DatabaseWriteManager;
import model.UserManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.accessControl.Group;
import model.databaseObjects.accessControl.GroupRight;
import model.databaseObjects.accessControl.User;
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.ConsumableMaterial;
import model.databaseObjects.stockObjects.Device;
import model.databaseObjects.stockObjects.MedicalMaterial;
import model.databaseObjects.stockObjects.StockObject;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class AddPresenter extends Presenter implements MouseListener {
	/** Reusable AddPresenter modType **/
	public DatabaseObject.ModificationType modificationType;
	/** TextFields **/
	private JTextField textField1;
	private JTextField textField2;
	private JTextField textField3;
	private JTextField textField4;
	private JTextField textField5;
	/** Areas **/
	private JTextArea textArea;
	private JTable table;
	private JComboBox booleanCombobox;
	/** Buttons **/
	private JButton saveButton;

	/**
	 * Create the application.
	 */
	public AddPresenter(Presenter previousPresenter, DatabaseObject.ModificationType modificationType) {
		this.previousPresenter = previousPresenter;
		this.modificationType = modificationType;
		this.initialize();
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

		JLabel lblMtkIntervall = new JLabel("MTK Intervall (in Monaten):");
		lblMtkIntervall.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(lblMtkIntervall);

		JLabel lblStkIntervall = new JLabel("STK Intervall (in Monaten):");
		lblStkIntervall.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(lblStkIntervall);

		JLabel lblOptionalerText = new JLabel("Beschreibung:");
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
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
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
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.saveButton.addActionListener(this);
		frame.getContentPane().add(this.saveButton);
	}

	private void setupMedicalMaterialMenuItem() {
		/******** Labels ********/
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblName);

		JLabel batchSizeLabel = new JLabel("Losgröße");
		batchSizeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(batchSizeLabel);

		JLabel minimumStockLabel = new JLabel("Mindestbestand");
		minimumStockLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(minimumStockLabel);

		JLabel quotaStockLabel = new JLabel("Sollbestand");
		quotaStockLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(quotaStockLabel);

		JLabel lblOptionalerText = new JLabel("optionaler Text");
		lblOptionalerText.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*4, leftSideMenuWidth, lineHeight);
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
		this.textField3.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField3);
		this.textField3.setColumns(10);

		this.textField4 = new JTextField();
		this.textField4.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField4);
		this.textField4.setColumns(10);

		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*4);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*4, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
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
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.saveButton.addActionListener(this);
		frame.getContentPane().add(this.saveButton);
	}

	private void setupConsumableMaterialMenuItem() {
		/******** Labels ********/
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblName);

		JLabel batchSizeLabel = new JLabel("Losgröße");
		batchSizeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(batchSizeLabel);

		JLabel minimumStockLabel = new JLabel("Mindestbestand");
		minimumStockLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(minimumStockLabel);

		JLabel quotaStockLabel = new JLabel("Sollbestand");
		quotaStockLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(quotaStockLabel);

		JLabel lblOptionalerText = new JLabel("optionaler Text");
		lblOptionalerText.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*4, leftSideMenuWidth, lineHeight);
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
		this.textField3.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField3);
		this.textField3.setColumns(10);

		this.textField4 = new JTextField();
		this.textField4.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField4);
		this.textField4.setColumns(10);

		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*4);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*4, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
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
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.saveButton.addActionListener(this);
		frame.getContentPane().add(this.saveButton);
	}

	private void setupLocationMenuItem() {
		/******** Labels ********/
		JLabel lblLagerortName = new JLabel("Lagerort Name");
		lblLagerortName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblLagerortName);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	private void setupUserMenuItem() {
		/******** Labels ********/
		JLabel usernameLabel = new JLabel("Benutzername");
		usernameLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(usernameLabel);

		JLabel firstNameLabel = new JLabel("Vorname");
		firstNameLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(firstNameLabel);

		JLabel nameLabel = new JLabel("Name");
		nameLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(nameLabel);

		JLabel mailLabel = new JLabel("E-Mail");
		mailLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(mailLabel);

		JLabel passwordLabel = new JLabel("Passwort");
		passwordLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*4, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(passwordLabel);

		JLabel groupLabel = new JLabel("Gruppe");
		groupLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*5, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(groupLabel);

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
		this.textField3.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField3);
		this.textField3.setColumns(10);

		this.textField4 = new JTextField();
		this.textField4.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*3, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField4);
		this.textField4.setColumns(10);

		this.textField5 = new JTextField();
		this.textField5.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*4, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField5);
		this.textField5.setColumns(10);

		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*5);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*5, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		Object columnNames[] = { "Gruppe", "Hinzufügen"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		Group[] groups = DatabaseReadManager.getGroups();
		Arrays.sort(groups, Comparator.comparingInt(a -> a.id));
		if (groups != null) {
			for (Group group : groups) {
				if (group.id == 1) {
					Object row[] = { group.title,  true };
					model.addRow(row);
				} else {
					Object row[] = { group.title,  false };
					model.addRow(row);
				}
			}
		}
		this.table.setModel(model);
		this.table.getColumnModel().getColumn(1).setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
		this.table.addMouseListener(this);
		scrollPane.setViewportView(this.table);

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	private void setupGroupMenuItem() {
		/******** Labels ********/
		JLabel lblGruppenname = new JLabel("Gruppenname");
		lblGruppenname.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblGruppenname);

		JLabel lblGruppenstatus = new JLabel("Gruppenstatus");
		lblGruppenstatus.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblGruppenstatus);

		JLabel lblGrupenberechtigung = new JLabel("Grupenberechtigung");
		lblGrupenberechtigung.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblGrupenberechtigung);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		this.booleanCombobox = new JComboBox();
		this.booleanCombobox.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*1, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		this.booleanCombobox.addItem("Aktiviert");
		this.booleanCombobox.addItem("Deaktiviert");
		this.frame.getContentPane().add(booleanCombobox);
		// needs to be called after adding the Items
		this.booleanCombobox.addActionListener(this);

		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*2);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
		this.frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		Object columnNames[] = { "Recht", "Hinzufügen"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		GroupRight[] groupRights = DatabaseReadManager.getGroupRights();
		Arrays.sort(groupRights, Comparator.comparingInt(a -> a.id));
		if (groupRights != null) {
			for (GroupRight groupRight : groupRights) {
				Object row[] = { groupRight.title,  false };
				model.addRow(row);
			}
		}
		this.table.setModel(model);
		this.table.getColumnModel().getColumn(1).setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
		this.table.addMouseListener(this);
		scrollPane.setViewportView(this.table);

		/******** Buttons ********/
		this.saveButton = new JButton("speichern");
		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.saveButton.addActionListener(this);
		frame.getContentPane().add(this.saveButton);
	}

	private Boolean saveButtonValidation() {
		switch (this.modificationType) {
			case deviceMenuItem:
				if (this.textField1.getText().length() > 0 && this.textField2.getText().length() > 0 && this.textField3.getText().length() > 0) {
					DecimalFormat decimalFormat = new DecimalFormat("#");
					try {
						int mtkIntervall = decimalFormat.parse(this.textField2.getText()).intValue();
						int stkIntervall = decimalFormat.parse(this.textField3.getText()).intValue();
						Device device = new Device(0,this.textField1.getText(), this.textArea.getText(),false, StockObject.StockObjectType.device,0, mtkIntervall ,stkIntervall);
						return DatabaseWriteManager.createObject(device);
					} catch (ParseException e) {
						// Cant parse Textfields to int
						return false;
					}
				} else {
					// required Textfields are empty
				}

				return false;
			case medicalMaterialMenuItem:
				if (this.textField1.getText().length() > 0 && this.textField2.getText().length() > 0 && this.textField3.getText().length() > 0 && this.textField4.getText().length() > 0) {
					DecimalFormat decimalFormat = new DecimalFormat("#");
					try {
						int batchSize = decimalFormat.parse(this.textField2.getText()).intValue();
						int minimumStock = decimalFormat.parse(this.textField3.getText()).intValue();
						int quotaStock = decimalFormat.parse(this.textField3.getText()).intValue();
						MedicalMaterial medicalMaterial = new MedicalMaterial(0,this.textField1.getText(), this.textArea.getText(), false, StockObject.StockObjectType.medicalMaterial, 0, batchSize, minimumStock ,quotaStock );
						return DatabaseWriteManager.createObject(medicalMaterial);
					} catch (ParseException e) {
						// Cant parse Textfields to int
						return false;
					}
				} else {
					// required Textfields are empty
				}
				return false;
			case consumableMaterialMenuItem:
				if (this.textField1.getText().length() > 0 && this.textField2.getText().length() > 0 && this.textField3.getText().length() > 0 && this.textField4.getText().length() > 0) {
					DecimalFormat decimalFormat = new DecimalFormat("#");
					try {
						int batchSize = decimalFormat.parse(this.textField2.getText()).intValue();
						int minimumStock = decimalFormat.parse(this.textField3.getText()).intValue();
						int quotaStock = decimalFormat.parse(this.textField3.getText()).intValue();
						ConsumableMaterial consumableMaterial = new ConsumableMaterial(0,this.textField1.getText(), this.textArea.getText(), false, StockObject.StockObjectType.medicalMaterial, 0, batchSize, minimumStock ,quotaStock );
						return DatabaseWriteManager.createObject(consumableMaterial);
					} catch (ParseException e) {
						// Cant parse Textfields to int
						return false;
					}
				} else {
					// required Textfields are empty
				}
				return false;
			case locationMenuItem:
				if (this.textField1.getText().length() > 0) {
					Location location = new Location(0, this.textField1.getText());
					return DatabaseWriteManager.createObject(location);
				} else {
					// required Textfields are empty
				}
				return false;
			case userMenuItem:
				if (this.textField1.getText().length() > 0
						&& this.textField2.getText().length() > 0
						&& this.textField3.getText().length() > 0
						&& this.textField4.getText().length() > 0
						&& this.textField5.getText().length() > 0) {
					UserManager userManager = new UserManager();
					try {
						String passwordHash = userManager.generatePasswordHash(textField5.getText());
						User user = new User(0, textField1.getText(), textField2.getText(), textField3.getText(), this.textField4.getText(), passwordHash, false);
						if (DatabaseWriteManager.createObject(user)) {
							User dbUser = DatabaseReadManager.getUser(this.textField1.getText());
							if (dbUser != null) {
								ArrayList<Group> groups = new ArrayList<>();
								DefaultTableModel dtm = (DefaultTableModel) this.table.getModel();
								int nRow = dtm.getRowCount();
								for (int i = 1 ; i <= nRow ; i++) {
									Boolean selected = (Boolean) dtm.getValueAt(i-1,1);
									if (selected) {
										Group group = DatabaseReadManager.getGroup(i);
										if (group != null) {
											groups.add(group);
										}
									}
								}
								Group[] groupsArray = groups.toArray(new Group[groups.size()]);
								return DatabaseWriteManager.setGroupsForUser(dbUser, groupsArray);
							}
						}
					} catch (NoSuchAlgorithmException exception) {

					}
				} else {
					// required Textfields are empty
				}
				return false;
			case groupMenuItem:
				if (this.textField1.getText().length() > 0) {
					Boolean comboboxState = true;
					if (this.booleanCombobox.getSelectedIndex() == 1) {
						comboboxState = false;
					}
					Group group = new Group(0, this.textField1.getText(), comboboxState);
					if (DatabaseWriteManager.createObject(group)) {
						Group dbGroup = DatabaseReadManager.getGroup(group.title);
						if (dbGroup != null) {
							ArrayList<GroupRight> groupRights = new ArrayList<>();
							DefaultTableModel dtm = (DefaultTableModel) this.table.getModel();
							int nRow = dtm.getRowCount();
							for (int i = 1 ; i <= nRow ; i++) {
								Boolean selected = (Boolean) dtm.getValueAt(i-1,1);
								if (selected) {
									GroupRight groupRight = DatabaseReadManager.getGroupRight(i);
									if (groupRight != null) {
										groupRights.add(groupRight);
									}
								}
							}
							GroupRight[] groupRightsArray = groupRights.toArray(new GroupRight[groupRights.size()]);
							return DatabaseWriteManager.setGroupRights(dbGroup, groupRightsArray);
						}
					}
				} else {
					// required Textfields are empty
				}
				return false;
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.saveButton) {
			if (this.saveButtonValidation()) {
				this.showPreviousPresenter();
			} else {
				// Can't be saved, unique field with same value or db connection error.
			}
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
			// Do sth with the rowPosition
			Boolean value = (Boolean) this.table.getValueAt(rowNumber,1);
			this.table.setValueAt(!value, rowNumber,1);
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