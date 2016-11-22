package presenter;

import javax.swing.JFrame;

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
}
