package presenter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;

public class MessagePresenter extends Presenter implements ActionListener {
	private JFrame frame;
	private JTable table;
	private JButton btnLogout = new JButton("Logout");
	private JButton back = new JButton("");
	private JButton help = new JButton("");
	
	/**
	 * Launch the application.
	 */
	public void newScreen() {
		super.newScreen();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MessagePresenter window = new MessagePresenter();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MessagePresenter() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton logo = new JButton("");
		Image img = new ImageIcon (this.getClass().getResource("/DRK-LogoMini.jpg")).getImage();
		logo.setIcon (new ImageIcon (img));
		logo.setBounds(595, 6, 199, 65);
		frame.getContentPane().add(logo);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 66, 788, 12);
		frame.getContentPane().add(separator);
		

		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogout.setBounds(455, 27, 98, 22);
		frame.getContentPane().add(btnLogout);
		
		
		Image imgback = new ImageIcon (this.getClass().getResource("/back-button.jpg")).getImage();
		back.setIcon (new ImageIcon (imgback));
		back.setBounds(36, 18, 33, 36);
		frame.getContentPane().add(back);
		
		
		Image imgbook = new ImageIcon (this.getClass().getResource("/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		frame.getContentPane().add(help);
		
		JLabel Meldungen = new JLabel("Meldungen");
		Meldungen.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Meldungen.setBounds(16, 98, 210, 36);
		frame.getContentPane().add(Meldungen);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(157, 219, 503, 192);
		frame.getContentPane().add(table);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnLogout) {
			SettingsPresenter settingsPresenter = new SettingsPresenter();
			//settingsPresenter.previousPresenter = this;
			settingsPresenter.newScreen();	
		} else if (e.getSource() == this.back){
			SettingsPresenter settingsPresenter = new SettingsPresenter();
			settingsPresenter.previousPresenter = this;
			settingsPresenter.newScreen();
		} else if (e.getSource() == this.help){
			SettingsPresenter settingsPresenter = new SettingsPresenter();
			settingsPresenter.previousPresenter = this;
			settingsPresenter.newScreen();
		}
		
	}
}
