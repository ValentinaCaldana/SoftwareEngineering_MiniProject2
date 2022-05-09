package miniproject_2.model;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Serializable;
import java.security.SecureRandom;
import java.time.Instant;

/**
 * Diese Klasse repräsentiert einen Account.
 * @author Richard Bradley, Rocco Saracino und Valentina Caldana
 */
public class Account implements Serializable {

	private Instant lastLogin;
	private String email;
	private String hashedPassword;
	private byte[] salt = new byte[64]; //64 Zeichen werden dazu genommen, Sicherheits 

	/**
	 * Erstellt einen Account aus einem Username und Passwort.
	 * Das Passwort wird "gesaled" und "gehashed".
	 * 
	 * @param username als String, nicht null
	 * @param password als String, nicht null
	 */
	public Account(String username, String password) {
		var rand = new SecureRandom();

		this.email = username;
		rand.nextBytes(salt);
		this.hashedPassword = hash(password);
		this.lastLogin = Instant.now();
	}

	/**
	 * Diese Methode berechnet den "Hash" für das Passwort. 
	 * 
	 * @param password als String
	 * @return "Hash" als String, nicht null
	 */
	private String hash(String password) {
		try {
			char[] chars = password.toCharArray();
			int iterations = 127;
			PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return bytesToHex(hash);

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return null;
		}
	}

	/**
	 * Byte-Array in ein Hex-String konvertieren.
	 * 
	 * @param bytes als String-Byte
	 * @return das konvertiere Hex-String, nicht null
	 */
	private static String bytesToHex(byte[] bytes) {//TODO: Was ist das? Beschreibung...
		char[] hexArray = "0123456789ABCDEF".toCharArray();

		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	/**
	 * Überprüft ob das mitgegebene Passwort gleich ist wie das gespeicherte Passwort.
	 * 
	 * @param password als String 
	 * @return true, wenn das Passwort übereinstimmt 
	 */
	public boolean checkPassword(String password) {
		String newHash = hash(password);
		boolean success = hashedPassword.equals(newHash);
		if (success)
			this.lastLogin = Instant.now();
		return success;
	}

	/**
	 * Das neue Passwort wird "gesaled", "gehashed" und gespeichert.
	 * @param newPassword als String
	 */
	public void changePassword(String newPassword) {
		var rand = new SecureRandom();
		rand.nextBytes(salt);
		this.hashedPassword = hash(newPassword);
	}

	public String getEmail() {
		return this.email;
	}

	public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass())
            return false;
        Account ol = (Account) o;
        return ol.getEmail().equals(this.email);
    }
	
	public int compareTo(Account account) {
        return email.compareTo(account.getEmail());
    }
}
