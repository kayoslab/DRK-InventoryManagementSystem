package model.databaseObjects.accessControl;
import model.databaseObjects.DatabaseObject;
/*
 * Struct-like object Class for Groups
 */
public class Group extends DatabaseObject {
	public final String title;
	public final Boolean isActive;
	
	Group(int id, String title, Boolean isActive) {
		super(id);
		this.title = title;
		
		// Optional isActive
		if (isActive == null) {
			this.isActive = true;
		} else {
			this.isActive = isActive;
		}
	}
}
