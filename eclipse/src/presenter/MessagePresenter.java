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

		JLabel Meldungen = new JLabel("Meldungen");
		Meldungen.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		Meldungen.setBounds(16, 98, 210, 36);
		frame.getContentPane().add(Meldungen);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(157, 219, 503, 192);
		frame.getContentPane().add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		scrollPane.setViewportView(table);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
