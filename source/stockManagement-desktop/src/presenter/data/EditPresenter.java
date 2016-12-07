package presenter.data;
import model.databaseObjects.DatabaseObject;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditPresenter extends Presenter {
	DatabaseObject.ModificationType modificationType;

	/**
	 * Create the application.
	 */
	public EditPresenter() {
		initialize();
	}

	/**
	 * Create the application.
	 */
	public EditPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public EditPresenter(Presenter previousPresenter, DatabaseObject.ModificationType modificationType) {
		this.previousPresenter = previousPresenter;
		this.modificationType = modificationType;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JLabel title = new JLabel();
		switch (this.modificationType) {
			case deviceMenuItem:
				title = new JLabel("Gerät bearbeiten:");
				break;
			case medicalMaterialMenuItem:
				title = new JLabel("Medizinisches Material bearbeiten:");
				break;
			case consumableMaterialMenuItem:
				title = new JLabel("Verbrauchsmaterial bearbeiten:");
				break;
			case locationMenuItem:
				title = new JLabel("Lagerort bearbeiten:");
				break;
			case userMenuItem:
				title = new JLabel("Benutzer bearbeiten:");
				break;
			case groupMenuItem:
				title = new JLabel("Gruppe bearbeiten:");
				break;
		}
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
