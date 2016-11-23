package presenter;

import model.DatabaseReadManager;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.stockObjects.Device;
import model.databaseObjects.stockObjects.StockObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class InventoryPresenter extends Presenter {
	private JTable table;
	private JButton btnLogout = new JButton("Logout");
	private JButton back = new JButton("");
	private JButton help = new JButton("");

	/**
	 * Create the application.
	 */
	public InventoryPresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		
		JButton logo = new JButton("");
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		frame.getContentPane().add(logo);

		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.addActionListener(this);
		btnLogout.setBounds(455, 27, 98, 22);
		frame.getContentPane().add(btnLogout);

		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		back.setIcon (new ImageIcon (imgback));
		back.setBounds(36, 18, 33, 36);
		back.addActionListener(this);
		frame.getContentPane().add(back);
		back.addActionListener(this);

		Image imgbook = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		help.addActionListener(this);
		frame.getContentPane().add(help);
		help.addActionListener(this);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 66, 788, 12);
		frame.getContentPane().add(separator);
		
		JLabel Inventarliste = new JLabel("Inventarliste");
		Inventarliste.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Inventarliste.setBounds(16, 98, 247, 36);
		frame.getContentPane().add(Inventarliste);
		
		JRadioButton rdbtnNurMindestbestnde = new JRadioButton("Nur Mindestbestände");
		rdbtnNurMindestbestnde.setBounds(36, 164, 166, 23);
		frame.getContentPane().add(rdbtnNurMindestbestnde);
		
		JRadioButton rdbtnNurAbgelaufeneDaten = new JRadioButton("Nur abgelaufene Daten");
		rdbtnNurAbgelaufeneDaten.setBounds(36, 199, 190, 23);
		frame.getContentPane().add(rdbtnNurAbgelaufeneDaten);
		
		JCheckBox chckbxMedmaterialien = new JCheckBox("MedMaterialien");
		chckbxMedmaterialien.setBounds(36, 244, 128, 23);
		frame.getContentPane().add(chckbxMedmaterialien);
		
		JCheckBox chckbxBetreuungsmaterialien = new JCheckBox("Betreuungsmaterialien");
		chckbxBetreuungsmaterialien.setBounds(36, 279, 190, 23);
		frame.getContentPane().add(chckbxBetreuungsmaterialien);
		
		JCheckBox chckbxGerte = new JCheckBox("Geräte");
		chckbxGerte.setBounds(36, 314, 128, 23);
		frame.getContentPane().add(chckbxGerte);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(36, 226, 171, 12);
		frame.getContentPane().add(separator_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(36, 359, 166, 27);
		frame.getContentPane().add(comboBox);
		comboBox.addItem("Sortieren nach...");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(250, 167, 481, 359);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);

		StockObject[] stockObjects = DatabaseReadManager.generateInventory(DatabaseObject.StockObjectType.device);
		Object columnNames[] = { "Titel", "Menge", "Datum", "Lagerort"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (StockObject stockObject : stockObjects) {
			Object row[] = { stockObject.title, stockObject.totalVolume, "Row1-Column3","Row1-Column4"};
			model.addRow(row);
		}


		table.setModel(model);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnLogout) {
			LoginPresenter loginPresenter = new LoginPresenter();
			this.frame.dispose();
			loginPresenter.newScreen();
		}
		if (e.getSource() == this.back) {
			this.showPreviousPresenter();
		}
		if (e.getSource() == this.help) {

		}
	}

}
