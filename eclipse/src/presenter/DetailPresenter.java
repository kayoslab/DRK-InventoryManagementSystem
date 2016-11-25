package presenter;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.*;

public class DetailPresenter extends Presenter {
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
	public DetailPresenter() {
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
		frame.getContentPane().add(logo);

		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.addActionListener(this);
		btnLogout.setBounds(455, 27, 98, 22);
		frame.getContentPane().add(btnLogout);

		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		back.setIcon (new ImageIcon (imgback));
		back.setBounds(36, 18, 33, 36);
		frame.getContentPane().add(back);

		Image imgbook = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		frame.getContentPane().add(help);

		btnHinzufgen.setBounds(386, 419, 117, 29);
		frame.getContentPane().add(btnHinzufgen);

		btnEntnehmen.setBounds(560, 419, 117, 29);
		frame.getContentPane().add(btnEntnehmen);

		btnBearbeiten.setBounds(560, 460, 117, 29);
		frame.getContentPane().add(btnBearbeiten);

		btnLschen.setBounds(386, 460, 117, 29);
		frame.getContentPane().add(btnLschen);

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 66, 788, 12);
		frame.getContentPane().add(separator);
		
		JLabel DatenFenster = new JLabel("Hier der Name");
		DatenFenster.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		DatenFenster.setBounds(16, 98, 347, 36);
		frame.getContentPane().add(DatenFenster);
		
		JTextArea txtrOptionalerText = new JTextArea();
		txtrOptionalerText.setText("optionaler Text");
		txtrOptionalerText.setBounds(36, 199, 258, 224);
		frame.getContentPane().add(txtrOptionalerText);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(350, 199, 358, 208);
		frame.getContentPane().add(table);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.logo) {

		} else if (e.getSource() == this.btnLogout) {

		} else if (e.getSource() == this.back) {
			this.showPreviousPresenter();
		} else if (e.getSource() == this.help) {

		} else if (e.getSource() == this.btnHinzufgen) {

		} else if (e.getSource() == this.btnEntnehmen) {

		} else if (e.getSource() == this.btnBearbeiten) {

		} else if (e.getSource() == this.btnLschen) {

		}
	}
}
