package presenter;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class MessagePresenter extends Presenter {
	private JTable table;

	/**
	 * Create the application.
	 */
	public MessagePresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		JLabel messageLabel = new JLabel("Meldungen");
		messageLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		messageLabel.setBounds(leftPadding, headlineY, displayAreaWidth, lineHeight);
		this.frame.getContentPane().add(messageLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(leftPadding, contentY, displayAreaWidth, contentHeight);
		this.frame.getContentPane().add(scrollPane);

		this.table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		scrollPane.setViewportView(this.table);
	}

	@Override
	public void presentData() {
		super.presentData();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
