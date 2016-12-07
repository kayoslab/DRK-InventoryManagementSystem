package model.databaseObjects.accessControl;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for Groups
 */
public class Group extends DatabaseObject {
	public String title;
	public Boolean isActive;
	
	public Group(int id, String title, Boolean isActive) {
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
