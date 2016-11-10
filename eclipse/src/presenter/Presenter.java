package presenter;

public abstract class Presenter {
	Presenter previousPresenter;
	
	public void showPreviousPresenter() {
		// show previous Presenter if not null
		if (this.previousPresenter != null) {
			// and discard current presenter
		}
	}
}
