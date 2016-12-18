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
import model.databaseObjects.stockValues.StockObjectValue;
import presenter.Presenter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ObjectAddPresenter extends Presenter implements MouseListener {
	/** Reusable ObjectAddPresenter modType **/
	public DatabaseObject.ModificationType modificationType;
	/** Prepared Data **/
	private DatabaseObject databaseObject;
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
	private JButton deleteButton;

	/**
	 * Create the application.
	 */
	public ObjectAddPresenter(Presenter previousPresenter, DatabaseObject.ModificationType modificationType) {
		this.previousPresenter = previousPresenter;
		this.modificationType = modificationType;
		this.initialize();
	}

	/**
	 * Create the application.
	 */
	public ObjectAddPresenter(Presenter previousPresenter, DatabaseObject.ModificationType modificationType, DatabaseObject preparedData) {
		this.previousPresenter = previousPresenter;
		this.modificationType = modificationType;
		this.databaseObject = preparedData;
		this.initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		/******** Buttons ********/
		if (this.databaseObject != null) {
			this.saveButton = new JButton("aktualisieren");
			this.deleteButton = new JButton("löschen");
			this.deleteButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*2) - smallSpacing, leftSideMenuWidth, buttonHeight);
			this.deleteButton.addActionListener(this);
			this.deleteButton.setEnabled(false);
			frame.getContentPane().add(this.deleteButton);
		} else {
			this.saveButton = new JButton("speichern");
		}

		this.saveButton.setBounds(leftPadding, displayAreaHeight-(buttonHeight*1), leftSideMenuWidth, buttonHeight);
		this.saveButton.addActionListener(this);
		this.saveButton.setEnabled(false);
		frame.getContentPane().add(this.saveButton);

		JLabel title = new JLabel();
		switch (this.modificationType) {
			case deviceMenuItem:
				if (this.databaseObject == null) {
					title = new JLabel("Ger\u00e4t hinzuf\u00fcgen:");
				} else {
					title = new JLabel("Ger\u00e4t bearbeiten:");
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editDevice)) {
					this.saveButton.setEnabled(true);
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteDevice)) {
					if (this.deleteButton != null) {
						this.deleteButton.setEnabled(true);
					}
				}
				this.setupDeviceMenuItem();
				break;
			case medicalMaterialMenuItem:
				if (this.databaseObject == null) {
					title = new JLabel("Medizinisches Material hinzuf\u00fcgen:");
				} else {
					title = new JLabel("Medizinisches Material bearbeiten:");
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editMedicalMaterial)) {
					this.saveButton.setEnabled(true);
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteMedicalMaterial)) {
					if (this.deleteButton != null) {
						this.deleteButton.setEnabled(true);
					}
				}
				this.setupMedicalMaterialMenuItem();
				break;
			case consumableMaterialMenuItem:
				if (this.databaseObject == null) {
					title = new JLabel("Betreuungsmaterial hinzuf\u00fcgen:");
				} else {
					title = new JLabel("Betreuungsmaterial bearbeiten:");
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editConsumableMaterial)) {
					this.saveButton.setEnabled(true);
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteConsumableMaterial)) {
					if (this.deleteButton != null) {
						this.deleteButton.setEnabled(true);
					}
				}
				this.setupConsumableMaterialMenuItem();
				break;
			case locationMenuItem:
				if (this.databaseObject == null) {
					title = new JLabel("Lagerort hinzuf\u00fcgen:");
				} else {
					title = new JLabel("Lagerort bearbeiten:");
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editLocation)) {
					this.saveButton.setEnabled(true);
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteLocation)) {
					if (this.deleteButton != null) {
						this.deleteButton.setEnabled(true);
					}
				}
				this.setupLocationMenuItem();
				break;
			case userMenuItem:
				if (this.databaseObject == null) {
					title = new JLabel("Benutzer hinzuf\u00fcgen:");
				} else {
					title = new JLabel("Benutzer bearbeiten:");
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editUser)) {
					this.saveButton.setEnabled(true);
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteUser)) {
					if (this.deleteButton != null) {
						this.deleteButton.setEnabled(true);
					}
				}
				this.setupUserMenuItem();
				break;
			case groupMenuItem:
				if (this.databaseObject == null) {
					title = new JLabel("Gruppe hinzuf\u00fcgen:");
				} else {
					title = new JLabel("Gruppe bearbeiten:");
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.editGroup)) {
					this.saveButton.setEnabled(true);
				}
				if (this.session.currentUserCanHandleGroupRight(Session.PossibleGroupRight.deleteGroup)) {
					if (this.deleteButton != null) {
						this.deleteButton.setEnabled(true);
					}
				}
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


		if (this.databaseObject != null) {
			if (this.databaseObject instanceof Device) {
				Device device = (Device) this.databaseObject;
				this.textField1.setText(device.title);
				this.textField2.setText("" + device.mtkIntervall);
				this.textField3.setText("" + device.stkIntervall);
				this.textArea.setText(device.description);
			}
		}
	}

	private void setupMedicalMaterialMenuItem() {
		/******** Labels ********/
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblName);

		JLabel batchSizeLabel = new JLabel("Losgr\u00f6\u00dfe:");
		batchSizeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(batchSizeLabel);

		JLabel lblOptionalerText = new JLabel("optionaler Text:");
		lblOptionalerText.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
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


		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*2);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
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

		if (this.databaseObject != null) {
			if (this.databaseObject instanceof MedicalMaterial) {
				MedicalMaterial medicalMaterial = (MedicalMaterial) this.databaseObject;
				this.textField1.setText(medicalMaterial.title);
				this.textField2.setText("" + medicalMaterial.batchSize);
				this.textArea.setText(medicalMaterial.description);
			}
		}
	}

	private void setupConsumableMaterialMenuItem() {
		/******** Labels ********/
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblName);

		JLabel batchSizeLabel = new JLabel("Losgr\u00f6\u00dfe:");
		batchSizeLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(batchSizeLabel);

		JLabel lblOptionalerText = new JLabel("optionaler Text:");
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

		int calculatedTextAreaHeight = displayAreaHeight - (contentY+(lineHeight+smallSpacing)*2);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*2, displayAreaWidth-(leftSideMenuWidth+spacing),calculatedTextAreaHeight);
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

		if (this.databaseObject != null) {
			if (this.databaseObject instanceof ConsumableMaterial) {
				ConsumableMaterial consumableMaterial = (ConsumableMaterial) this.databaseObject;
				this.textField1.setText(consumableMaterial.title);
				this.textField2.setText("" + consumableMaterial.batchSize);
				this.textArea.setText(consumableMaterial.description);
			}
		}
	}

	private void setupLocationMenuItem() {
		/******** Labels ********/
		JLabel lblLagerortName = new JLabel("Lagerort Name:");
		lblLagerortName.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblLagerortName);

		/******** Eingabe ********/
		this.textField1 = new JTextField();
		this.textField1.setBounds(leftPadding+leftSideMenuWidth+spacing, contentY+(lineHeight+smallSpacing)*0, displayAreaWidth-(leftSideMenuWidth+spacing),lineHeight);
		frame.getContentPane().add(this.textField1);
		this.textField1.setColumns(10);

		if (this.databaseObject != null) {
			if (this.databaseObject instanceof Location) {
				Location location = (Location) this.databaseObject;
				this.textField1.setText(location.title);
			}
		}
	}

	private void setupUserMenuItem() {
		/******** Labels ********/
		JLabel usernameLabel = new JLabel("Benutzername:");
		usernameLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(usernameLabel);

		JLabel firstNameLabel = new JLabel("Vorname:");
		firstNameLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(firstNameLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*2, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(nameLabel);

		JLabel mailLabel = new JLabel("E-Mail:");
		mailLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*3, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(mailLabel);

		JLabel passwordLabel = new JLabel("Passwort:");
		passwordLabel.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*4, leftSideMenuWidth, lineHeight);
		frame.getContentPane().add(passwordLabel);

		JLabel groupLabel = new JLabel("Gruppen:");
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
		Object columnNames[] = { "Gruppe", "Hinzuf\u00fcgen"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		Group[] groups = DatabaseReadManager.getGroups();
		Arrays.sort(groups, Comparator.comparingInt(a -> a.id));
		if (groups != null) {
			if (this.databaseObject == null) {
				for (Group group : groups) {
					if (group.id == 1) {
						Object row[] = { group.title,  true };
						model.addRow(row);
					} else {
						Object row[] = { group.title,  false };
						model.addRow(row);
					}
				}
			} else {
				Group[] usersGroups = DatabaseReadManager.getGroups((User)this.databaseObject);
				for (Group group : groups) {
					if (usersGroups != null) {
						if (group.id == 1) {
							Object row[] = { group.title,  true };
							model.addRow(row);
						} else {
							Boolean selectedGroup = false;
							for (Group usersGroup : usersGroups) {
								if (usersGroup.id == group.id) {
									selectedGroup = true;
									break;
								}
							}
							Object row[] = { group.title,  selectedGroup };
							model.addRow(row);
						}
					}
				}
			}
		}
		this.table.setModel(model);
		this.table.getColumnModel().getColumn(1).setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
		this.table.addMouseListener(this);
		scrollPane.setViewportView(this.table);

		if (this.databaseObject != null) {
			if (this.databaseObject instanceof User) {
				User user = (User) this.databaseObject;
				this.textField1.setText(user.username);
				this.textField2.setText("" + user.firstName);
				this.textField3.setText("" + user.name);
				this.textField4.setText("" + user.mail);
				this.textField5.setText("");
				passwordLabel.setText("Passwort (ausgeblendet):");

				if (user.id <= 1) {
					this.deleteButton.setEnabled(false);
				}
			}
		}
	}

	private void setupGroupMenuItem() {
		/******** Labels ********/
		JLabel lblGruppenname = new JLabel("Gruppenname:");
		lblGruppenname.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*0, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblGruppenname);

		JLabel lblGruppenstatus = new JLabel("Gruppenstatus:");
		lblGruppenstatus.setBounds(leftPadding, contentY+(lineHeight+smallSpacing)*1, leftSideMenuWidth,lineHeight);
		frame.getContentPane().add(lblGruppenstatus);

		JLabel lblGrupenberechtigung = new JLabel("Grupenberechtigungen:");
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
		Object columnNames[] = { "Recht", "Hinzuf\u00fcgen"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		GroupRight[] groupRights = DatabaseReadManager.getGroupRights();
		Arrays.sort(groupRights, Comparator.comparingInt(a -> a.id));
		if (groupRights != null) {
			if (this.databaseObject == null) {
				for (GroupRight groupRight : groupRights) {
					Object row[] = { groupRight.title,  false };
					model.addRow(row);
				}
			} else {
				GroupRight[] groupsRights = DatabaseReadManager.getGroupRights((Group)this.databaseObject);
				for (GroupRight groupRight : groupRights) {
					Boolean selectedGroupRight = false;

					for (GroupRight groupsRight : groupsRights) {
						if (groupsRight.id == groupRight.id) {
							selectedGroupRight = true;
							break;
						}
					}

					Object row[] = { groupRight.title,  selectedGroupRight };
					model.addRow(row);
				}
			}
		}
		this.table.setModel(model);
		this.table.getColumnModel().getColumn(1).setCellRenderer(this.table.getDefaultRenderer(Boolean.class));
		this.table.addMouseListener(this);
		scrollPane.setViewportView(this.table);

		if (this.databaseObject != null) {
			if (this.databaseObject instanceof Group) {
				Group group = (Group) this.databaseObject;
				this.textField1.setText(group.title);
				if (group.isActive) {
					this.booleanCombobox.setSelectedIndex(0);
				} else {
					this.booleanCombobox.setSelectedIndex(1);
				}
				if (this.databaseObject.id <= 5) {
					this.deleteButton.setEnabled(false);
				}
			}
		}
	}

	private Boolean saveButtonValidation() {
		switch (this.modificationType) {
			case deviceMenuItem:
				if (this.textField1.getText().length() > 0) {
					DecimalFormat decimalFormat = new DecimalFormat("#");
					int mtkIntervall = 0;
					int stkIntervall = 0;
					try {
						mtkIntervall = decimalFormat.parse(this.textField2.getText()).intValue();
					} catch (ParseException e) {
						// Cant parse Textfields to int
					}
					try {
						stkIntervall = decimalFormat.parse(this.textField3.getText()).intValue();
					} catch (ParseException e) {
						// Cant parse Textfields to int
					}
					if (this.databaseObject == null) {
						/** Create new Device **/
						this.databaseObject = new Device(0,this.textField1.getText(), this.textArea.getText(), StockObject.StockObjectType.device,0, mtkIntervall ,stkIntervall);
						if (DatabaseWriteManager.createObject(this.databaseObject)) {
							return true;
						}  else {
							this.databaseObject = null;
							return false;
						}
					} else {
						/** Update existing Device **/
						((Device)this.databaseObject).title = this.textField1.getText();
						((Device)this.databaseObject).description = this.textArea.getText();
						((Device)this.databaseObject).mtkIntervall = mtkIntervall;
						((Device)this.databaseObject).stkIntervall = stkIntervall;
						return DatabaseWriteManager.editObject(this.databaseObject);
					}
				} else {
					// required Textfields are empty
					Border border = BorderFactory.createLineBorder(Color.red);
					this.textField1.setBorder(border);
				}

				return false;
			case medicalMaterialMenuItem:
				if (this.textField1.getText().length() > 0) {
					DecimalFormat decimalFormat = new DecimalFormat("#");
					int batchSize = 0;
					// int minimumSize = 0;
					// int quotaSize = 0;
					try {
						batchSize = decimalFormat.parse(this.textField2.getText()).intValue();
					} catch (ParseException e) {
						// Cant parse Textfields to int
					}
					if (this.databaseObject == null) {
						// new StockObject
						// (int id, String title, String description, DatabaseObject.StockObjectType type, int totalVolume, int batchSize)
						this.databaseObject = new MedicalMaterial(0,this.textField1.getText(), this.textArea.getText(), StockObject.StockObjectType.medicalMaterial, 0, batchSize);
						if (DatabaseWriteManager.createObject(this.databaseObject)) {
							return true;
						}  else {
							this.databaseObject = null;
							return false;
						}
					} else {
						/** update existing MedicalMaterial **/
						((MedicalMaterial)this.databaseObject).title = this.textField1.getText();
						((MedicalMaterial)this.databaseObject).description = this.textArea.getText();
						((MedicalMaterial)this.databaseObject).batchSize = batchSize;
						return DatabaseWriteManager.editObject(this.databaseObject);
					}
				} else {
					// required Textfields are empty
					Border border = BorderFactory.createLineBorder(Color.red);
					this.textField1.setBorder(border);
				}
				return false;
			case consumableMaterialMenuItem:
				if (this.textField1.getText().length() > 0) {
					DecimalFormat decimalFormat = new DecimalFormat("#");
					int batchSize = 0;
					// int minimumSize = 0;
					// int quotaSize = 0;
					try {
						batchSize = decimalFormat.parse(this.textField2.getText()).intValue();
					} catch (ParseException e) {
						// Cant parse Textfields to int
					}
					if (this.databaseObject == null) {
						// new StockObject
						// (int id, String title, String description, DatabaseObject.StockObjectType type, int totalVolume, int batchSize)
						this.databaseObject = new ConsumableMaterial(0, this.textField1.getText(), this.textArea.getText(), StockObject.StockObjectType.medicalMaterial, 0, batchSize );
						if (DatabaseWriteManager.createObject(this.databaseObject)) {
							return true;
						}  else {
							this.databaseObject = null;
							return false;
						}
					} else {
						/** update existing ConsumableMaterial **/
						((ConsumableMaterial)this.databaseObject).title = this.textField1.getText();
						((ConsumableMaterial)this.databaseObject).description = this.textArea.getText();
						((ConsumableMaterial)this.databaseObject).batchSize = batchSize;
						return DatabaseWriteManager.editObject(this.databaseObject);
					}
				} else {
					// required Textfields are empty
					Border border = BorderFactory.createLineBorder(Color.red);
					this.textField1.setBorder(border);
				}
				return false;
			case locationMenuItem:
				if (this.textField1.getText().length() > 0) {
					if (this.databaseObject == null) {
						this.databaseObject = new Location(0, this.textField1.getText());
						if (DatabaseWriteManager.createObject(this.databaseObject)) {
							return true;
						}  else {
							this.databaseObject = null;
							return false;
						}
					} else {
						((Location)this.databaseObject).title = this.textField1.getText();
						return DatabaseWriteManager.editObject(this.databaseObject);
					}

				} else {
					// required Textfields are empty
					Border border = BorderFactory.createLineBorder(Color.red);
					this.textField1.setBorder(border);
				}
				return false;
			case userMenuItem:
				if (this.textField1.getText().length() > 0
						&& this.textField2.getText().length() > 0
						&& this.textField3.getText().length() > 0
						&& this.textField4.getText().length() > 0) {
					// password textfield only required if user is null
					if (this.databaseObject == null && this.textField5.getText().length() > 0) {
						UserManager userManager = new UserManager();
						try {
							String passwordHash = userManager.generatePasswordHash(textField5.getText());
							this.databaseObject = new User(0, textField1.getText(), textField2.getText(), textField3.getText(), this.textField4.getText(), passwordHash, false);
							if (DatabaseWriteManager.createObject(this.databaseObject)) {
								this.databaseObject = DatabaseReadManager.getUser(this.textField1.getText());
								if (this.databaseObject != null) {
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
									return DatabaseWriteManager.setGroupsForUser((User)this.databaseObject, groupsArray);
								}
							} else {
								this.databaseObject = null;
								return false;
							}
						} catch (NoSuchAlgorithmException exception) {
							return false;
						}
					} else {
						/** Update existing User */
						UserManager userManager = new UserManager();
						try {
							String passwordHash;
							if (this.textField5.getText().length() > 0) {
								passwordHash = userManager.generatePasswordHash(textField5.getText());
								((User)this.databaseObject).passwordHash = passwordHash;
							}
							((User)this.databaseObject).username = this.textField1.getText();
							((User)this.databaseObject).firstName = this.textField2.getText();
							((User)this.databaseObject).name = this.textField3.getText();
							((User)this.databaseObject).mail = this.textField4.getText();
							/** Save Update and add Groups **/
							if (DatabaseWriteManager.editObject(this.databaseObject)) {
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
								return DatabaseWriteManager.setGroupsForUser((User)this.databaseObject, groupsArray);
							}
						} catch (NoSuchAlgorithmException exception) {
							return false;
						}
					}

				} else {
					// required Textfields are empty
					Border border = BorderFactory.createLineBorder(Color.red);
					this.textField1.setBorder(border);
					this.textField2.setBorder(border);
					this.textField3.setBorder(border);
					this.textField4.setBorder(border);
					if (this.databaseObject == null) {
						this.textField5.setBorder(border);
					}
				}
				return false;
			case groupMenuItem:
				if (this.textField1.getText().length() > 0) {
					if (this.databaseObject == null) {
						Boolean comboboxState = true;
						if (this.booleanCombobox.getSelectedIndex() == 1) {
							comboboxState = false;
						}
						this.databaseObject = new Group(0, this.textField1.getText(), comboboxState);
						if (DatabaseWriteManager.createObject(this.databaseObject)) {
							this.databaseObject = DatabaseReadManager.getGroup(((Group) this.databaseObject).title);
							if (this.databaseObject != null) {
								ArrayList<GroupRight> groupRights = new ArrayList<>();
								DefaultTableModel dtm = (DefaultTableModel) this.table.getModel();
								int nRow = dtm.getRowCount();
								for (int i = 1; i <= nRow; i++) {
									Boolean selected = (Boolean) dtm.getValueAt(i - 1, 1);
									if (selected) {
										GroupRight groupRight = DatabaseReadManager.getGroupRight(i);
										if (groupRight != null) {
											groupRights.add(groupRight);
										}
									}
								}
								GroupRight[] groupRightsArray = groupRights.toArray(new GroupRight[groupRights.size()]);
								return DatabaseWriteManager.setGroupRights((Group) this.databaseObject, groupRightsArray);
							}
						} else {
							this.databaseObject = null;
							return false;
						}
					} else {
						/** Update existing Group **/
						Boolean comboboxState = true;
						if (this.booleanCombobox.getSelectedIndex() == 1) {
							comboboxState = false;
						}
						((Group)this.databaseObject).isActive = comboboxState;
						((Group)this.databaseObject).title = this.textField1.getText();

						if (DatabaseWriteManager.editObject(this.databaseObject)) {
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
							return DatabaseWriteManager.setGroupRights((Group)this.databaseObject, groupRightsArray);
						}
					}
				} else {
					// required Textfields are empty
					Border border = BorderFactory.createLineBorder(Color.red);
					this.textField1.setBorder(border);
				}
				return false;
		}
		return false;
	}

	public static int okcancel(String theMessage) {
		int result = JOptionPane.showConfirmDialog((Component) null, theMessage, "Löschen", JOptionPane.OK_CANCEL_OPTION);
		return result;
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
		} else  if (e.getSource() == this.deleteButton) {
			if (this.databaseObject != null) {
				int selection = -1;
				switch (this.modificationType) {
					case deviceMenuItem:
						if (this.databaseObject instanceof Device) {
							Device device = (Device) this.databaseObject;
							StockObjectValue[] stockObjectValues = DatabaseReadManager.getStockObjectValues(device);
							int length = 0;
							if (stockObjectValues != null) {
								length = stockObjectValues.length;
							}
							selection = this.okcancel("Es befinden sich noch " + length +
									" Objekte in Ihrem Bestand. Diese werden ebenfalls gelöscht." +
									" Möchten Sie diesen Artikel wirklich löschen?");
						}
						break;
					case medicalMaterialMenuItem:
						if (this.databaseObject instanceof MedicalMaterial) {
							MedicalMaterial medicalMaterial = (MedicalMaterial) this.databaseObject;
							StockObjectValue[] stockObjectValues = DatabaseReadManager.getStockObjectValues(medicalMaterial);
							int length = 0;
							if (stockObjectValues != null) {
								length = stockObjectValues.length;
							}
							selection = this.okcancel("Es befinden sich noch " + length +
									" Objekte in Ihrem Bestand. Diese werden ebenfalls gelöscht." +
									" Möchten Sie diesen Artikel wirklich löschen?");
						}
						break;
					case consumableMaterialMenuItem:
						if (this.databaseObject instanceof ConsumableMaterial) {
							ConsumableMaterial consumableMaterial = (ConsumableMaterial) this.databaseObject;
							StockObjectValue[] stockObjectValues = DatabaseReadManager.getStockObjectValues(consumableMaterial);
							int length = 0;
							if (stockObjectValues != null) {
								length = stockObjectValues.length;
							}
							selection = this.okcancel("Es befinden sich noch " + length +
									" Objekte in Ihrem Bestand. Diese werden ebenfalls gelöscht." +
									" Möchten Sie diesen Artikel wirklich löschen?");
						}
						break;
					case locationMenuItem:
						if (this.databaseObject instanceof Location) {
							Location location = (Location) this.databaseObject;
							StockObjectValue[] stockObjectValues = DatabaseReadManager.getStockObjectValues(location);
							int length = 0;
							if (stockObjectValues != null) {
								length = stockObjectValues.length;
							}
							selection = this.okcancel("Es befinden sich noch " + length +
									" Objekte in diesem Lager. Diese werden ebenfalls gelöscht." +
									" Möchten Sie dieses Lager wirklich löschen?");
						}

						break;
					case userMenuItem:
						selection = this.okcancel("Möchten Sie diesen Benutzer wirklich löschen?");
						break;
					case groupMenuItem:
						if (this.databaseObject instanceof Group) {
							Group group = (Group) this.databaseObject;
							User[] users = DatabaseReadManager.getUsers(group);
							int length = 0;
							if (users != null) {
								length = users.length;
							}
							selection = this.okcancel("Es befinden sich " + length +
									" Nutzer in dieser Gruppe. Möchten Sie diese Gruppe wirklich löschen?");
						}
						break;
				}


				if (selection == 0) {
					if (this.databaseObject.deleteObject()) {
						this.showPreviousPresenter();
					}
				}
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