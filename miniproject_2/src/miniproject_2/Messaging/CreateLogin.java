package miniproject_2.Messaging;

import miniproject_2.Server.Client;
import miniproject_2.model.AccountManager;
import miniproject_2.model.Account;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando.
 * Damit kann ein neuer Account erstellt werden.  
 * Nach jeder erfolgreichen Account-Erstellung, muss sich der User 
 * vor dessen Verwendung einloggen.
 * 
 * @author Rocco Saracino und Valentina Caldana
 */
public class CreateLogin extends Message {

	private String email;
	private String password;

	/**
	 * @param data als String-Array
	 * 
	 * Erster Index: email, nicht null
	 * Zweiter Index: password, nicht null
	 */
	public CreateLogin(String[] data) {
		super(data);
		this.email = data[1];
		this.password = data[2];
	}

	/**
	 * @process wertet die Eingabe des Users aus. Das
	 * Passwort und die Emailadresse werden nur unter den folgenden 
	 * Bedingungen akzeptiert und gesetzt:
	 * 
	 * -Wenn der Username eine syntaktisch gültige Emailadresse ist
	 * -Wenn die Emailadresse nicht bereits verwendet wurde
	 * -Wenn das  Passwort 3 - 20 Zeichen enthält
	 */

	public void process(Client client) {
		boolean result = false;

		if (email != null 
			&& email.contains("@") 
			&& validateUserName(email)
			&& password != null 
			&& password.length() <= 20 
			&& password.length() >= 3
			&& AccountManager.exists(email) == null)
		{
			Account newAccount = new Account(email, password);
			AccountManager.add(newAccount);
			result = true;
		}
		
		client.send(new Result(this.getClass(), result));
		AccountManager.saveAccounts();	
	}

	/**
	 * Die Validierung der Syntax (Emailadresse) erfolgt hier.
	 * @param email, nicht null
	 * @return true, wenn Anforderungen erfüllt
	 */
	private boolean validateUserName(String email) {
		boolean valid = false;

		String[] addressParts = email.split("@"); 
		if (addressParts.length == 2 
			&& !addressParts[0].isEmpty() 
			&& !addressParts[1].isEmpty()) 
		{		
			if (addressParts[1].charAt(addressParts[1].length() - 1) != '.') 
			{
				String[] domainParts = addressParts[1].split("\\.");
				if (domainParts.length >= 2) 
				{
					valid = true;
					for (String s : domainParts) 
					{
						if (s.length() < 2)
							valid = false;
					}
				}
			}
		}
		return valid;
	}
}
