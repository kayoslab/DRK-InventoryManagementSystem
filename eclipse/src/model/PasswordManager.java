package model;
// Import for MD5 Hash
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import model.databaseObjects.*;

public class PasswordManager {

	/**
	 * Constructor
	 */
	public PasswordManager() {
		
	}
	
	/**
	 * @param int userid, String password
	 * @return Boolean
	 */
	public Boolean tryLogin(String username, String password) {
		String databasePasswordHash = DatabaseReadManager.getUser(username).passwordHash;
		try {
			if (this.generatePasswordHash(password) == databasePasswordHash) {
				return true;
			}
		} catch (NoSuchAlgorithmException exception) {
			// maybe do anything with the exception for missing md5 hash function
			// on the current Operating System.
		}
		return false;
	}
	
	/**
	 * @param String username, String currentPassword, String newPassword
	 * @return Boolean
	 */
	public Boolean setNewPassword(String username, String currentPassword, String newPassword) {
		if (this.validatePassword(newPassword)) {
			if (this.tryLogin(username, currentPassword)) {
				try {
					String newPasswordHash = this.generatePasswordHash(newPassword);
					User user = DatabaseReadManager.getUser(username);
					user.passwordHash = newPasswordHash;
					// Store this newPasswordHash into the database
					// if completed proceed.
					Boolean passwordSuccesfullyChanged = DatabaseWriteManager.setPassword(user);
					return passwordSuccesfullyChanged;
				} catch (NoSuchAlgorithmException exception) {
					// maybe do anything with the exception for missing md5 hash function
					// on the current Operating System.
				}
			}
		}
		return false;
	}
	
	/**
	 * @param String username, String newPassword
	 * @return Boolean
	 */
	public Boolean overrideCurrentPassword(String username, String newPassword) {
		if (this.validatePassword(newPassword)) {
			try {
				String newPasswordHash = this.generatePasswordHash(newPassword);
				User user = DatabaseReadManager.getUser(username);
				user.passwordHash = newPasswordHash;
				user.passwordChanged = false;
				// Store this newPasswordHash into the database
				// if completed proceed.
				Boolean passwordSuccesfullyChanged = DatabaseWriteManager.setPassword(user);
				return passwordSuccesfullyChanged;
			} catch (NoSuchAlgorithmException exception) {
				// maybe do anything with the exception for missing md5 hash function
				// on the current Operating System.
			}
		}
		return false;
	}
	
	/**
	 * @param String password
	 * @return Boolean
	 */
	public Boolean validatePassword(String password) {
		return true;
	}
	
	/**
	 * @param String password
	 * @return String
	 */
	private String generatePasswordHash(String password) throws NoSuchAlgorithmException {
		// Create Instance of MessageDigest with MD5 Hash Initializer
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // Cast String to md5 Byte Array
		byte[] byteArray = messageDigest.digest(password.getBytes());
        return this.toHexString(byteArray);
	}
	
	/**
	 * @param Byte[]
	 * @return String
	 */
	private String toHexString(byte[] bytes) {
		// String builder instance
	    StringBuilder hexString = new StringBuilder();
	    // iterate over Bytes in Array
	    for (int i = 0; i < bytes.length; i++) {
	    	// convert every byte to an HexString
	    	// use 0xFF & bytes[i] as a quick way for calculating an unsigned value
	        String hex = Integer.toHexString(0xFF & bytes[i]);
	        if (hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
	
}
