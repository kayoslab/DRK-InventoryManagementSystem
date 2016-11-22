package presenter;

import javax.swing.JFrame;
import java.awt.*;

public abstract class Presenter {
	Presenter previousPresenter;
	JFrame frame;
	
	public void newScreen() {
		// General newScreen() implementation
		if (previousPresenter != null) {
			previousPresenter.frame.dispose();
		}
	}
	
	public void showPreviousPresenter() {
		// show previous Presenter if not null
		if (this.previousPresenter != null) {
			// and discard current presenter
			this.frame.dispose();
			this.previousPresenter.newScreen();
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
}
