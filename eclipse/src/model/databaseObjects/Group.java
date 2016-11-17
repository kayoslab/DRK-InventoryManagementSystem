package model.databaseObjects;
/*
 * Struct-like object Class for Groups
 */
public class Group {
	public final int id;
	public final String title;
	public final Boolean isActive;
	
	Group(int id, String title, Boolean isActive) {
		this.id = id;
		this.title = title;
		
		// Optional isActive
		if (isActive == null) {
			this.isActive = true;
		} else {
			this.isActive = isActive;
		}
	}
}
