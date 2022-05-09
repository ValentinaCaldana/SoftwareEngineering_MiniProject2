package miniproject_2.model;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Diese Klasse verwaltet die Funktionalitäten eines Accounts.
 * @author Rocco Saracino und Valentina Caldana
 */
public class AccountManager implements Serializable {

	private static final Logger logger = Logger.getLogger("");

	private static List<Account> accounts;
	private static final SecureRandom rand = new SecureRandom();

	/**
	 * Fügt ein neues Account-Objekt der ArrayList hinzu.
	 * 
	 * @param account als Account-Objekt, nicht null
	 */
	public static void add(Account account) { 
		synchronized (accounts) {
			accounts.add(account);
		}
	}

	/**
	 * Überprüft ob ein Account existiert mittels Email.
	 * 
	 * @param email als String
	 * @return ein Account, wenn die übergebene Email existiert
	 */
	public static Account exists(String email) {
		synchronized (accounts) {
			for (Account account : accounts) {
				if (account.getEmail().equals(email))
					return account;
			}
		}
		return null;
	}

	/**
	 * Schreibt und speichert eine File-Datei die Account-Objekte enthält.
	 */
	public static void saveAccounts() {
		File accountFile = new File("src\\miniproject_2\\saveLoad\\accounts.sav");
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(accountFile))) {
			synchronized (accounts) {
				out.writeObject(accounts);
				out.close();
			}
		} catch (IOException e) {
			logger.severe("Unable to save accounts: " + e.getMessage());
		}
	}

	/**
	 * Diese Methode liest die gespeicherten Accounts bei Programmstart ein:
	 */
	public static void readAccounts() {
		File accountFile = new File("src\\miniproject_2\\saveLoad\\accounts.sav");
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(accountFile))) {
			accounts = (List<Account>) in.readObject();
			logger.fine("Loaded accounts successfully");
		} catch (Exception e) {
			logger.severe("Unable to read accounts: " + e.getMessage());
			accounts = new ArrayList<Account>();
		}
	}

	/**
	 * Es wird ein zufälliger Token generiert.
	 * 
	 * @return ein Token, nicht null
	 */
	public static String generateToken() {
		byte[] token = new byte[16];
		rand.nextBytes(token);
		return bytesToHex(token);
	}

	/**
	 * Konvertierung eines Byte-Arrays, in ein Hex-String.
	 * From: https://stackoverflow.com/questions/9655181/how-to-convert-a-
	 * byte-array-to-a-hex-string-in-java
	 */
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

	private static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
