package presenter;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.ActionListener;

public abstract class Presenter implements ActionListener {
	Presenter previousPresenter;
	JFrame frame;
	
	public void newScreen() {
		// General newScreen() implementation
		if (previousPresenter != null) {
			previousPresenter.frame.dispose();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	public void showPreviousPresenter() {
		// show previous Presenter if not null
		if (this.previousPresenter != null) {
			// and discard current presenter
			this.previousPresenter.newScreen();
			this.frame.dispose();
		} else {
			System.out.println("previousPresenter == null");
		}
	}


}
