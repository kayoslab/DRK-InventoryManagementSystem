package presenter;
import model.DatabaseWriteManager;
import model.PasswordManager;

public class SettingsPresenter extends Presenter {
	private PasswordManager passwordManager = new PasswordManager();
	private DatabaseWriteManager writeManager = new DatabaseWriteManager();
	
}
