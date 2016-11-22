package presenter;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class DataPresenter extends Presenter {

	private JFrame frame;
	private JTextField txtSuchen;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public void newScreen () {
		super.newScreen();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataPresenter window = new DataPresenter();
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
	public DataPresenter() {
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
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setBackground(Color.LIGHT_GRAY);
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnLogout.setBounds(455, 27, 98, 22);
		frame.getContentPane().add(btnLogout);
		
		JButton back = new JButton("");
		Image imgback = new ImageIcon (this.getClass().getResource("/back-button.jpg")).getImage();
		back.setIcon (new ImageIcon (imgback));
		back.setBounds(36, 18, 33, 36);
		frame.getContentPane().add(back);
		
		JButton help = new JButton("");
		Image imgbook = new ImageIcon (this.getClass().getResource("/book-button.jpg")).getImage();
		help.setIcon (new ImageIcon (imgbook));
		help.setBounds(381, 18, 33, 36);
		frame.getContentPane().add(help);
		
		JLabel MaterialUndGeraeteDaten = new JLabel("Material- und Geräte Daten");
		MaterialUndGeraeteDaten.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		MaterialUndGeraeteDaten.setBounds(16, 98, 347, 36);
		frame.getContentPane().add(MaterialUndGeraeteDaten);
		
		JButton btnA = new JButton("A");
		btnA.setBackground(Color.WHITE);
		btnA.setBounds(5, 164, 24, 21);
		frame.getContentPane().add(btnA);
		
		JButton btnB = new JButton("B");
		btnB.setBounds(31, 164, 24, 21);
		frame.getContentPane().add(btnB);
		
		JButton btnC = new JButton("C");
		btnC.setBounds(57, 164, 24, 21);
		frame.getContentPane().add(btnC);
		
		JButton btnD = new JButton("D");
		btnD.setBounds(83, 164, 24, 21);
		frame.getContentPane().add(btnD);
		
		JButton btnE = new JButton("E");
		btnE.setBounds(109, 164, 24, 21);
		frame.getContentPane().add(btnE);
		
		JButton btnF = new JButton("F");
		btnF.setBounds(135, 164, 24, 21);
		frame.getContentPane().add(btnF);
		
		JButton btnG = new JButton("G");
		btnG.setBounds(161, 164, 24, 21);
		frame.getContentPane().add(btnG);
		
		JButton btnH = new JButton("H");
		btnH.setBounds(187, 164, 24, 21);
		frame.getContentPane().add(btnH);
		
		JButton btnI = new JButton("I");
		btnI.setBounds(213, 164, 24, 21);
		frame.getContentPane().add(btnI);
		
		JButton btnJ = new JButton("J");
		btnJ.setBounds(239, 164, 24, 21);
		frame.getContentPane().add(btnJ);
		
		JButton btnK = new JButton("K");
		btnK.setBounds(265, 164, 24, 21);
		frame.getContentPane().add(btnK);
		
		JButton btnL = new JButton("L");
		btnL.setBounds(291, 164, 24, 21);
		frame.getContentPane().add(btnL);
		
		JButton btnM = new JButton("M");
		btnM.setBounds(317, 164, 24, 21);
		frame.getContentPane().add(btnM);
		
		JButton btnN = new JButton("N");
		btnN.setBounds(343, 164, 24, 21);
		frame.getContentPane().add(btnN);
		
		JButton btnO = new JButton("O");
		btnO.setBounds(369, 164, 24, 21);
		frame.getContentPane().add(btnO);
		
		JButton btnP = new JButton("P");
		btnP.setBounds(395, 164, 24, 21);
		frame.getContentPane().add(btnP);
		
		JButton btnQ = new JButton("Q");
		btnQ.setBounds(421, 164, 24, 21);
		frame.getContentPane().add(btnQ);
		
		JButton btnR = new JButton("R");
		btnR.setBounds(447, 164, 24, 21);
		frame.getContentPane().add(btnR);
		
		JButton btnS = new JButton("S");
		btnS.setBounds(473, 164, 24, 21);
		frame.getContentPane().add(btnS);
		
		JButton btnT = new JButton("T");
		btnT.setBounds(499, 164, 24, 21);
		frame.getContentPane().add(btnT);
		
		JButton btnU = new JButton("U");
		btnU.setBounds(525, 164, 24, 21);
		frame.getContentPane().add(btnU);
		
		JButton btnV = new JButton("V");
		btnV.setBounds(551, 164, 24, 21);
		frame.getContentPane().add(btnV);
		
		JButton btnW = new JButton("W");
		btnW.setBounds(577, 164, 24, 21);
		frame.getContentPane().add(btnW);
		
		JButton btnX = new JButton("X");
		btnX.setBounds(603, 164, 24, 21);
		frame.getContentPane().add(btnX);
		
		JButton btnY = new JButton("Y");
		btnY.setBounds(629, 164, 24, 21);
		frame.getContentPane().add(btnY);
		
		JButton btnZ = new JButton("Z");
		btnZ.setBounds(655, 164, 24, 21);
		frame.getContentPane().add(btnZ);
		
		txtSuchen = new JTextField();
		txtSuchen.setBackground(Color.WHITE);
		txtSuchen.setHorizontalAlignment(SwingConstants.CENTER);
		txtSuchen.setText("suchen");
		txtSuchen.setBounds(707, 160, 87, 28);
		frame.getContentPane().add(txtSuchen);
		txtSuchen.setColumns(10);
		
		table = new JTable();
		table.setBackground(Color.LIGHT_GRAY);
		table.setBounds(109, 253, 586, 257);
		frame.getContentPane().add(table);

	}

}
