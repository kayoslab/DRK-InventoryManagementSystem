/*
 * Copyright (c) - All Rights Reserved
 *
 * Unauthorized copying of these files, via any medium is
 * strictly prohibited Proprietary and confidential
 *
 * NOTICE:
 * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
 * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
 * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained by the owner.
 *
 * @author Chris
 *
 */

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
