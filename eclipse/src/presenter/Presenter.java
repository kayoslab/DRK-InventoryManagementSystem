package presenter;
import model.DatabaseReadManager;

public abstract class Presenter {
	Presenter previousPresenter;
	DatabaseReadManager readManager;
	
	public Presenter() {
		this.readManager = new DatabaseReadManager();
	}
	
	public void showPreviousPresenter() {
		// show previous Presenter if not null
		if (this.previousPresenter != null) {
			// and discard current presenter
		}
	}
}
