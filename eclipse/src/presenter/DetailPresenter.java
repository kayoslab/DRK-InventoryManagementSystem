package presenter;
import model.DatabaseReadManager;
import model.databaseObjects.stockObjects.*;
import model.databaseObjects.stockValues.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DetailPresenter extends Presenter implements MouseListener {
	private StockObject stockObject;
	private StockObjectValue[] stockObjectValues;
	private JTable table;
	private JButton logo = new JButton("");
	private JButton btnLogout = new JButton("Logout");
	private JButton back = new JButton("");
	private JButton help = new JButton("");
	private JButton btnHinzufgen = new JButton("Hinzufügen");
	private JButton btnEntnehmen = new JButton("Entnehmen");
	private JButton btnBearbeiten = new JButton("Bearbeiten");
	private JButton btnLschen = new JButton("Löschen");

	/**
	 * Create the application.
	 */
	public DetailPresenter(StockObject stockObject) {
		this.stockObject = stockObject;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();

		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		logo.addActionListener(this);
		frame.getContentPane().add(logo);

		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.setBounds(455, 27, 98, 22);
		btnLogout.addActionListener(this);
		frame.getContentPane().add(btnLogout);

		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		back.setIcon (new ImageIcon (imgback));
		back.setBounds(36, 18, 33, 36);
		back.addActionListener(this);
		frame.getContentPane().add(back);

		Image imgbook = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		help.addActionListener(this);
		frame.getContentPane().add(help);

		btnHinzufgen.setBounds(386, 419, 117, 29);
		btnHinzufgen.addActionListener(this);
		frame.getContentPane().add(btnHinzufgen);

		btnEntnehmen.setBounds(560, 419, 117, 29);
		btnEntnehmen.addActionListener(this);
		frame.getContentPane().add(btnEntnehmen);

		btnBearbeiten.setBounds(560, 460, 117, 29);
		btnBearbeiten.addActionListener(this);
		frame.getContentPane().add(btnBearbeiten);

		btnLschen.setBounds(386, 460, 117, 29);
		btnLschen.addActionListener(this);
		frame.getContentPane().add(btnLschen);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 66, 788, 12);
		frame.getContentPane().add(separator);
		
		JLabel titleLabel;
		if (this.stockObject != null) {
			titleLabel = new JLabel(this.stockObject.title);
		} else {
			titleLabel = new JLabel();
		}
		titleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		titleLabel.setBounds(16, 98, 347, 36);
		frame.getContentPane().add(titleLabel);

		
		JTextArea descriptionArea = new JTextArea();
		descriptionArea.setText("Beschreibung");
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		if (this.stockObject != null) {
			descriptionArea.setText(this.stockObject.description);
		}
		descriptionArea.setBounds(36, 199, 258, 224);
		frame.getContentPane().add(descriptionArea);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(350, 199, 358, 208);
		frame.getContentPane().add(scrollPane);
		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};

		table.addMouseListener(this);
		scrollPane.setViewportView(table);

		this.loadTableData();
	}

	private void loadTableData() {
		Object columnNames[] = { "Menge", "Lagerort", "Datum"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		this.stockObjectValues = DatabaseReadManager.getStockObjectValues(this.stockObject);
		if (this.stockObjectValues != null) {
			for (StockObjectValue stockObjectValue : this.stockObjectValues) {
				// Switch between instanceTypes
				if (stockObjectValue instanceof DeviceValue) {
					DeviceValue deviceValue = (DeviceValue) stockObjectValue;
					Object row[] = { deviceValue.volume, "Lagerort" + deviceValue.locationID, ""};
					model.addRow(row);
				} else if (stockObjectValue instanceof MaterialValue) {
					if (stockObjectValue instanceof MedicalMaterialValue) {
						MedicalMaterialValue medicalMaterialValue = (MedicalMaterialValue) stockObjectValue;
						Object row[] = { medicalMaterialValue.volume, "Lagerort" + medicalMaterialValue.locationID, ""};
						model.addRow(row);
					} else if (stockObjectValue instanceof ConsumableMaterialValue) {
						ConsumableMaterialValue consumableMaterialValue = (ConsumableMaterialValue) stockObjectValue;
						Object row[] = { consumableMaterialValue.volume, "Lagerort" + consumableMaterialValue.locationID, ""};
						model.addRow(row);
					} else {
						// Do nothing with this object, its not a usable material
					}
				} else {
					// Do nothing, maybe its a vehicle
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.logo) {
			// Do logo things
		} else if (e.getSource() == this.btnLogout) {
			LoginPresenter loginPresenter = new LoginPresenter();
			this.frame.dispose();
			loginPresenter.newScreen();
		} else if (e.getSource() == this.back) {
			this.showPreviousPresenter();
		} else if (e.getSource() == this.help) {
			// Do help things
		} else if (e.getSource() == this.btnHinzufgen) {

		} else if (e.getSource() == this.btnEntnehmen) {

		} else if (e.getSource() == this.btnBearbeiten) {

		} else if (e.getSource() == this.btnLschen) {

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Left mouse click
		if ( SwingUtilities.isLeftMouseButton(e) ) {
			// get the coordinates of the mouse click
			Point p = e.getPoint();

			// get the row index that contains that coordinate
			int rowNumber = table.rowAtPoint(p);
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
