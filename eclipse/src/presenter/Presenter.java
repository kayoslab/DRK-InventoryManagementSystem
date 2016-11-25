package presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.DISPOSE_ON_CLOSE;

public abstract class Presenter implements ActionListener {
	public Presenter previousPresenter;
	public JFrame frame;
	public JButton logo;
	public JButton btnLogout;
	public JButton back;
	public JButton help;

	/* Size */
	public int width = 800;
	public int height = 600;
	public int leftPadding = 16;
	public int rightPadding = 16;
	public int topPadding = 6;
	public int bottomPadding = 26;
	/* Spacings */
	public int tinySpacing = 4;
	public int smallSpacing = 10;
	public int spacing = 16;
	public int hugeSpacing = 100;
	/* IconButtons */
	public int iconButtonWidth = 33;
	public int iconButtonHeight = 33;
	public int iconButtonBarX = leftPadding+hugeSpacing;
	/* TopLayout */
	private int logoWidth = 200;
	private int logoHeight = 65;
	private int logoX = (width-rightPadding-logoWidth);
	private int logoutWidth = 90;
	private int logoutHeight = 22;
	private int logoutX = (width-rightPadding-logoWidth-spacing-logoutWidth);
	/* reference */
	public int topLayoutCenter = (topPadding + (logoHeight) / 2);

	public void newScreen() {
		// General newScreen() implementation
		EventQueue.invokeLater(() -> {
			try {
				// DataPresenter window = new DataPresenter();
				this.frame.setVisible(true);
				if (previousPresenter != null) {
					previousPresenter.frame.setVisible(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	/**
	 * Setup the Top Layout
	 */
	public void setupTopLayout() {
		this.logo = new JButton("");
		Image img = new ImageIcon (this.getClass().getResource("/img/DRK-LogoMini.jpg")).getImage();
		this.logo.setIcon (new ImageIcon (img));
		this.logo.setBounds(logoX, topPadding, logoWidth, logoHeight);
		frame.getContentPane().add(this.logo);
		this.logo.addActionListener(this);

		this.btnLogout = new JButton("Logout");
		this.btnLogout.setBounds(logoutX, (topLayoutCenter - logoutHeight / 2), logoutWidth, logoutHeight);
		frame.getContentPane().add(this.btnLogout);
		this.btnLogout.addActionListener(this);

		this.back = new JButton("");
		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		Image imgbackScaled = imgback.getScaledInstance( iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.back.setIcon (new ImageIcon (imgbackScaled));
		this.back.addActionListener(this);
		this.back.setBounds(leftPadding+spacing, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		frame.getContentPane().add(this.back);

		this.help = new JButton("");
		Image helpImage = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		Image helpImageScaled = helpImage.getScaledInstance( iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.help.setIcon (new ImageIcon (helpImageScaled));
		this.help.setBounds(iconButtonBarX, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		frame.getContentPane().add(this.help);
		this.help.addActionListener(this);

		JSeparator separator = new JSeparator();
		separator.setBounds(leftPadding, topPadding+logoHeight+1, (width - leftPadding - rightPadding), smallSpacing);
		frame.getContentPane().add(separator);
	}

	public void showPreviousPresenter() {
		// show previous Presenter if not null
		if (this.previousPresenter != null) {
			// and discard current presenter
			this.previousPresenter.frame.setVisible(true);
			this.frame.dispose();
		} else {
			System.out.println("previousPresenter == null");
		}
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.btnLogout) {
			LoginPresenter loginPresenter = new LoginPresenter();
			this.frame.dispose();
			loginPresenter.newScreen();
		} else if (e.getSource() == this.back){
			this.showPreviousPresenter();
		} else if (e.getSource() == this.help){
			// TODO: HELP Message
		}
	}
}

