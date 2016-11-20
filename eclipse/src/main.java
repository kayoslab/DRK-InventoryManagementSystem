import model.databaseCommunication.DatabaseLoginManager;
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.ConsumableMaterial;
import model.databaseObjects.stockObjects.Device;
import model.databaseObjects.stockObjects.MedicalMaterial;
import model.databaseObjects.DatabaseObject;
import model.databaseObjects.accessControl.*;
import model.DatabaseReadManager;
import model.DatabaseWriteManager;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatabaseLoginManager dbloginManager = new DatabaseLoginManager();
		if (dbloginManager.testDatabaseConnection()) {
			// TODO: Show LoginPresenter
			System.out.println("Database-Connection established.");
			Device device = new Device(10,"MeduMat","Test",false, DatabaseObject.StockObjectType.device,10,"12345","67890","Fisch",24,24);
			System.out.println(DatabaseWriteManager.editObject(device));
			MedicalMaterial medmat = new MedicalMaterial(11,"Mullbinde","Test",false,DatabaseObject.StockObjectType.medicalMaterial,0,10,5,30);
			System.out.println(DatabaseWriteManager.editObject(medmat));
			ConsumableMaterial consmat = new ConsumableMaterial(12,"Snickers","Test",false,DatabaseObject.StockObjectType.consumableMaterial,0,10,5,30);
			System.out.println(DatabaseWriteManager.editObject(consmat));			
			
		} else {
			// TODO: Show SetupPresenter
			System.out.println("Can't connect to database using the given credentials.");
		}
	}
}
