package presenter.data;
import model.databaseObjects.DatabaseObject;
import presenter.Presenter;
import java.awt.event.ActionEvent;

public class AddPresenter extends Presenter {
	DatabaseObject.ModificationType modificationType;

	/**
	 * Create the application.
	 */
	public AddPresenter() {
		initialize();
	}

	/**
	 * Create the application.
	 */
	public AddPresenter(Presenter previousPresenter) {
		this.previousPresenter = previousPresenter;
		initialize();
	}

	/**
	 * Create the application.
	 */
	public AddPresenter(Presenter previousPresenter, DatabaseObject.ModificationType modificationType) {
		this.previousPresenter = previousPresenter;
		this.modificationType = modificationType;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		super.initialize();
		super.setupTopLayout();

		switch (this.modificationType) {
			case deviceMenuItem:
				break;
			case medicalMaterialMenuItem:
				break;
			case consumableMaterialMenuItem:
				break;
			case locationMenuItem:
				break;
			case userMenuItem:
				break;
			case groupMenuItem:
				break;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
	}
}
