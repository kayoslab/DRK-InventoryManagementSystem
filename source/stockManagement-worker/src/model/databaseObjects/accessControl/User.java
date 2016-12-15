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
 * @author
 *
 */

package model.databaseObjects.accessControl;
import model.databaseObjects.DatabaseObject;

/*
 * Struct-like object Class for Users
 */
public class User extends DatabaseObject {
	public String username;
	public String firstName;
	public String name;
	public String mail;
	public String passwordHash;
	public Boolean passwordChanged;
	
	public User(int id, String username, String firstname, String name, String mail, String passwordHash, Boolean passwordChanged) {
		super(id);
		this.username = username;
		this.firstName = firstname;
		this.name = name;
		this.mail = mail;
		this.passwordHash = passwordHash;
		this.passwordChanged = passwordChanged;
	}
}
