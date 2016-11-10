package model;
import java.security.*;

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
	public Boolean tryLogin(int userid, String password) {
		String databasePasswordHash = this.getPasswordHashFromDatabase(userid);
		try {
			if (this.generatePasswordHash(password) == databasePasswordHash) {
				return true;
			}
		} catch (java.security.NoSuchAlgorithmException exception) {
			// maybe do anything with the exception for missing md5 hash function
			// on the current Operating System.
		}
		return false;
	}
	
	/**
	 * @param int userid, String currentPassword, String newPassword
	 * @return Boolean
	 */
	public Boolean setNewPassword(int userid, String currentPassword, String newPassword) {
		if (this.validatePassword(newPassword)) {
			if (this.tryLogin(userid, currentPassword)) {
				try {
					String newPasswordHash = this.generatePasswordHash(newPassword);
					// Store this newPasswordHash into the database
					// if completed proceed.
					// return true;
				} catch (java.security.NoSuchAlgorithmException exception) {
					// maybe do anything with the exception for missing md5 hash function
					// on the current Operating System.
				}
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
	private String generatePasswordHash(String password) throws java.security.NoSuchAlgorithmException {
		// Create Instance of MessageDigest with MD5 Hash Initializer
		java.security.MessageDigest messageDigest = java.security.MessageDigest.getInstance("MD5");
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
	
	/**
	 * @param int userid
	 * @return String
	 */
	private String getPasswordHashFromDatabase(int userid) {
		// get current password hash from Database and return for userid
		return "";
	}
	
}
