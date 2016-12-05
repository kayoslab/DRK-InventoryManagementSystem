package presenter.settings;
import presenter.Presenter;
import model.UserManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ChangePasswordPresenter extends Presenter {
	private JPasswordField currentPasswordTextField;
	private JPasswordField passwordTextField;
	private JPasswordField newPasswordTextField;
	private JButton saveButton = new JButton("speichern");
	private UserManager userManager = new UserManager();

	/**
	 * Create the application.
	 */
	public ChangePasswordPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JLabel changePasswordLabel = new JLabel("Passwort Ändern");
		changePasswordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		changePasswordLabel.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(changePasswordLabel);

		JLabel oldPasswordLabel = new JLabel("altes Passwort");
		oldPasswordLabel.setBounds(centeredContentAreaX, firstRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(oldPasswordLabel);

		JLabel newPasswordLabel = new JLabel("neues Passwort");
		newPasswordLabel.setBounds(centeredContentAreaX, secondRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(newPasswordLabel);

		JLabel newPasswordAgainLabel = new JLabel("Passwort wiederholen");
		newPasswordAgainLabel.setBounds(centeredContentAreaX, thirdRowPlacing+setupLabelTopMargin, setupLabelWidth, setupLabelHeight);
		this.frame.getContentPane().add(newPasswordAgainLabel);

		this.currentPasswordTextField = new JPasswordField();
		this.currentPasswordTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, firstRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.currentPasswordTextField.setColumns(10);
		this.frame.getContentPane().add(this.currentPasswordTextField);

		this.passwordTextField = new JPasswordField();
		this.passwordTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, secondRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.frame.getContentPane().add(this.passwordTextField);

		this.newPasswordTextField = new JPasswordField();
		this.newPasswordTextField.setBounds(centeredContentAreaX+setupLabelWidth+spacing, thirdRowPlacing, centeredContentAreaWidth-(rightPadding+leftPadding+setupLabelWidth+spacing), setupTextFieldHeight);
		this.frame.getContentPane().add(this.newPasswordTextField);

		this.saveButton.setBounds(menuButtonX, thirdRowPlacing+setupTextFieldHeight+buttonSpacing, setupButtonWidth, buttonHeight);
		this.frame.getContentPane().add(this.saveButton);
		this.saveButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.saveButton){
			if (passwordTextField.getPassword().equals(newPasswordTextField.getPassword())) {
				if (this.userManager.setNewPassword("", String.valueOf(currentPasswordTextField.getPassword()), String.valueOf(passwordTextField.getPassword()))) {
					// Password changed.
				} else {
					// Can't change Password.
				}
			}
		}
	}
}
