import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.DatabaseWriteManager;

public class main {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			// TODO: Show LoginPresenter
			System.out.println("Database Connection established.");
			try {
				Date date = sdf.parse("2016-03-03 03:00:00");
				DeviceValue dv = new DeviceValue(0, 1, date, date, 10, 2, 1, "ABCD", "EFG", "FOO");
				MedicalMaterialValue mv = new MedicalMaterialValue(0, 10, 11, 2, 1, "ABC", date);
				ConsumableMaterialValue cv = new ConsumableMaterialValue(0, 10, 12, 2, 1, "ABC", date);
				System.out.println(DatabaseWriteManager.createObject(cv));
			} catch (ParseException e) {
				System.out.println(e.getMessage());
			}

		} else {
			// TODO: Show SetupPresenter
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
