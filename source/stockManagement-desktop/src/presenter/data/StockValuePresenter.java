package presenter.data;
import model.databaseObjects.stockObjects.StockObject;
import model.databaseObjects.stockValues.StockObjectValue;
import presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StockValuePresenter extends Presenter {
	/** Data **/
	private StockObject stockObject;
	private StockObjectValue stockObjectValue;
	/** Buttons **/
	private JButton saveButton;

	/**
	 * Create the application.
	 */
	public StockValuePresenter(Presenter previousPresenter, StockObject stockObject) {
		this.previousPresenter = previousPresenter;
		this.stockObject = stockObject;
		this.initialize();

		JLabel title = new JLabel("Hinzufügen:");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);
	}

	/**
	 * Create the application.
	 */
	public StockValuePresenter(Presenter previousPresenter, StockObject stockObject, StockObjectValue stockObjectValue) {
		this.previousPresenter = previousPresenter;
		this.stockObject = stockObject;
		this.stockObjectValue = stockObjectValue;
		this.initialize();

		JLabel title = new JLabel("Bearbeiten:");
		title.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		title.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(title);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() == this.saveButton) {

		}
	}
}
