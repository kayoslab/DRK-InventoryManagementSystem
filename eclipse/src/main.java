import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.ConsumableMaterial;
import model.databaseObjects.stockObjects.Device;
import model.databaseObjects.stockObjects.MedicalMaterial;
import model.databaseObjects.stockValues.ConsumableMaterialValue;
import model.databaseObjects.stockValues.DeviceValue;
import model.databaseObjects.stockValues.MedicalMaterialValue;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.accessControl.*;

import java.util.Date;

import model.DatabaseReadManager;
import model.DatabaseWriteManager;
import java.util.Date;
public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			// TODO: Show LoginPresenter
			DeviceValue dv = new DeviceValue(0, 1, new Date(), new Date(), 10, 2, 1, "ABCD", "EFG", "FOO");
			MedicalMaterialValue mv = new MedicalMaterialValue(0, 10, 11, 2, 1, "ABC", new Date());
			ConsumableMaterialValue cv = new ConsumableMaterialValue(0, 12, 11, 2, 1, "ABC", new Date());
			System.out.println(DatabaseWriteManager.createObject(dv));			
			
		} else {
			// TODO: Show SetupPresenter
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
