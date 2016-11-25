package presenter;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.*;

public class MessagePresenter extends Presenter {
	private JTable table;

	/**
	 * Create the application.
	 */
	public MessagePresenter() {
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
		messageLabel.setBounds(16, 98, 210, 36);
		this.frame.getContentPane().add(messageLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(157, 219, 503, 192);
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
