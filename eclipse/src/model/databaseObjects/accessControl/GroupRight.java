package model.databaseObjects.accessControl;
import model.databaseObjects.DatabaseObject;
/*
 * Struct-like object Class for Group Rights
 */
public class GroupRight extends DatabaseObject {
	public final String title;
	
	public GroupRight(int id, String title) {
		super(id);
		this.title = title;
	}
}
