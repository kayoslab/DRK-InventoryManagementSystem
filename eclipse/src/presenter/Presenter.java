package presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.DISPOSE_ON_CLOSE;

public abstract class Presenter implements ActionListener {
	Presenter previousPresenter;
	JFrame frame;
	private JButton back;
	JButton logo;
	JButton btnLogout;
	JButton help;
	JSeparator separator;

	public void newScreen() {
		// General newScreen() implementation
		EventQueue.invokeLater(() -> {
			try {
				// DataPresenter window = new DataPresenter();
				this.frame.setVisible(true);
				if (this.previousPresenter != null) {
					this.previousPresenter.frame.setVisible(false);
				}
				this.presentData();
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
		this.frame.getContentPane().add(this.logo);
		this.logo.addActionListener(this);

		this.btnLogout = new JButton("Logout");
		this.btnLogout.setBounds(logoutX, (topLayoutCenter - logoutHeight / 2), logoutWidth, logoutHeight);
		this.frame.getContentPane().add(this.btnLogout);
		this.btnLogout.addActionListener(this);

		this.back = new JButton("");
		Image imgback = new ImageIcon (this.getClass().getResource("/img/back-button.jpg")).getImage();
		Image imgbackScaled = imgback.getScaledInstance( iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.back.setIcon (new ImageIcon (imgbackScaled));
		this.back.addActionListener(this);
		this.back.setBounds(leftPadding+spacing, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.frame.getContentPane().add(this.back);

		this.help = new JButton("");
		Image helpImage = new ImageIcon (this.getClass().getResource("/img/book-button.jpg")).getImage();
		Image helpImageScaled = helpImage.getScaledInstance( iconButtonWidth, iconButtonHeight,  java.awt.Image.SCALE_SMOOTH );
		this.help.setIcon (new ImageIcon (helpImageScaled));
		this.help.setBounds(iconButtonBarX, (topLayoutCenter - iconButtonHeight / 2), iconButtonWidth, iconButtonHeight);
		this.frame.getContentPane().add(this.help);
		this.help.addActionListener(this);

		this.separator = new JSeparator();
		this.separator.setBounds(leftPadding, topPadding+logoHeight+1, (width - leftPadding - rightPadding), smallSpacing);
		this.frame.getContentPane().add(this.separator);
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

	public void presentData() {

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

	/**
	 *
	 * Magic Numbers for View Layout
	 */
	/* Size */
	int width = 800;
	int height = 600;
	int leftPadding = 16;
	int rightPadding = 16;
	int topPadding = 6;
	int bottomPadding = 26;
	/* Spacings */
	int tinySpacing = 4;
	int smallSpacing = 10;
	int spacing = 16;
	int hugeSpacing = 100;
	/* IconButtons */
	int iconButtonWidth = 33;
	int iconButtonHeight = 33;
	int iconButtonBarX = leftPadding+hugeSpacing;
	/* TopLayout */
	int logoWidth = 200;
	int logoHeight = 65;
	int logoX = (width-rightPadding-logoWidth);
	int logoutWidth = 90;
	int logoutHeight = 22;
	int logoutX = (width-rightPadding-logoWidth-spacing-logoutWidth);
	/* reference */
	int topLayoutCenter = (topPadding + (logoHeight) / 2);
}

