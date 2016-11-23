package presenter;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.DISPOSE_ON_CLOSE;

public abstract class Presenter implements ActionListener {
	Presenter previousPresenter;
	JFrame frame;
	
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
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
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


}
