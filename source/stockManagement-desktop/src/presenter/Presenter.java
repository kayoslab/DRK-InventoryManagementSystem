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
 * @author Sabine
 *
 */

package presenter;
import model.Session;
import model.databaseCommunication.DatabaseLoginManager;
import presenter.onboarding.LoginPresenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Presenter implements ActionListener {
	public Session session = Session.getSharedInstance();
	public Presenter previousPresenter;
	public JFrame frame;
	public JButton logo;
	public JButton btnLogout;
	public JButton help;
	public JSeparator separator;
	private JButton back;

	public void Presenter() {

	}

	public void Presenter(Presenter previousPresenter) {
		this.Presenter();
		this.previousPresenter = previousPresenter;
	}

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
		Dimension objDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int coordX = (objDimension.width - frame.getWidth()) / 4;
		int coordY = (objDimension.height - frame.getHeight()) / 4;
		frame.setBounds(coordX, coordY, width, height);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		/** it just works **/
		frame.setMinimumSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setPreferredSize(new Dimension(width,height));

	}

	/**
	 * Call this function to setup the generic TopLayout
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
			this.previousPresenter.showedAsPreviousPresenter();
		} else {
			System.out.println("previousPresenter == null");
		}
	}

	public void showedAsPreviousPresenter() {
		// Delegate
	}

	public void presentData() {
		// hmm ...
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btnLogout) {
			LoginPresenter loginPresenter = new LoginPresenter(this);
			this.frame.dispose();
			loginPresenter.newScreen();
			Session.getSharedInstance().invalidateSession();
		} else if (e.getSource() == this.back){
			this.showPreviousPresenter();
		} else if (e.getSource() == this.help){
			/***** Refferences the help document as a document on the server *****/
			DatabaseLoginManager databaseLoginManager = new DatabaseLoginManager();
			try {
				URL url = new URL(databaseLoginManager.getURL());
				String hostAddress = url.getHost();
				System.out.println(hostAddress);
				String urlString = "http://" + hostAddress + "/help/";

				java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlString));
			} catch (MalformedURLException e1) {
				// not really important
				System.out.println("Malformed URL");
			} catch (IOException openException) {
				// not really important
				System.out.println("Cant open");
			}

		} else if (e.getSource() == this.logo) {
			String urlString = "http://drk-sennestadt.de/";
			try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(urlString));
			} catch (IOException openException) {
				System.out.println("Cant open");
			}
		}
	}

	/**
	 *
	 * Magic Numbers for View Layout
	 */
	/* Size */
	public int width = 800;
	public int height = 600;
	public int leftPadding = 16;
	public int rightPadding = 16;
	public int topPadding = 6;
	public int bottomPadding = 36;
	public int displayAreaWidth = width - leftPadding - rightPadding;
	public int displayAreaHeight = height - topPadding - bottomPadding;
	/* Spacings */
	public int noSpacing = 4;
	public int smallSpacing = 10;
	public int spacing = 16;
	public int buttonSpacing = 27;
	public int hugeSpacing = 100;
	/* IconButtons */
	public int characterButtonWidth = 24;
	public int characterButtonHeight = 24;
	public int buttonHeight = 29;
	public int leftSideMenuWidth = 190;
	public int headlineY = 100;
	public int lineHeight = 36;
	public int labelHeight = 14;
	public int generalButtonWidth = 140;
	public int contentY = headlineY+lineHeight+spacing;
	public int contentHeight = height-contentY-bottomPadding;
	public int iconButtonWidth = 33;
	public int iconButtonHeight = 33;
	public int iconButtonBarX = leftPadding+hugeSpacing;
	/* TopLayout */
	public int logoWidth = 200;
	public int logoHeight = 65;
	public int logoX = (width-rightPadding-logoWidth);
	public int logoutWidth = 90;
	public int logoutHeight = 22;
	public int logoutX = (width-rightPadding-logoWidth-spacing-logoutWidth);
	/* reference */
	public int topLayoutCenter = (topPadding + (logoHeight) / 2);
	/* Menu Presenter */
	public int menuButtonWidth = 240;
	public int menuButtonX = (displayAreaWidth/2) - (menuButtonWidth/2);
	public int menuButtonHeight = 58;
	public int firstButtonPlacing = 200;
	public int secondButtonPlacing = (firstButtonPlacing + menuButtonHeight + buttonSpacing );
	public int thirdButtonPlacing = (secondButtonPlacing + menuButtonHeight + buttonSpacing );
	/* Login and Setup */
	public int centeredContentAreaWidth = displayAreaWidth * 2 / 3;
	public int centeredContentAreaX = displayAreaWidth / 6;
	public int setupLabelHeight = 16;
	public int setupLabelWidth = centeredContentAreaWidth / 3;
	public int setupTextFieldHeight = 28;
	public int setupLabelTopMargin = (setupTextFieldHeight-setupLabelHeight)/2;
	public int setupButtonWidth = centeredContentAreaWidth / 2;
	public int firstRowPlacing = firstButtonPlacing;
	public int secondRowPlacing = firstRowPlacing + setupLabelHeight + spacing;
	public int thirdRowPlacing = secondRowPlacing + setupLabelHeight + spacing;

	/*************************************************************************/


}

