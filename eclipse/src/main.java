import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;
import presenter.LoginPresenter;
import presenter.SetupPresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.DatabaseWriteManager;

public class main {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			LoginPresenter loginPresenter = new LoginPresenter();
			loginPresenter.newScreen();
			System.out.println("Database Connection established.");
			try {
				Date date = sdf.parse("2017-01-01 00:00:00");
				// Constructor(int id, int volume, int stockObjectID, int locationID, int messageID, String batchNumber, Date date)
				ConsumableMaterialValue cv = new ConsumableMaterialValue(0, 100, 3, 1, 1, "BatchNumber-123456", date);
				System.out.println(DatabaseWriteManager.createObject(cv));
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}

		} else {
			// TODO: Show SetupPresenter
			SetupPresenter setupPresenter = new SetupPresenter();
			setupPresenter.newScreen();
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
