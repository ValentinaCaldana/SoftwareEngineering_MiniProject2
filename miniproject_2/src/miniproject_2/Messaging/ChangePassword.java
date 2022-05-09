package miniproject_2.Messaging;

import miniproject_2.Server.Client;
import miniproject_2.model.AccountManager;
import miniproject_2.model.Account;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class ChangePassword extends Message {

	private String token;
	private String password;

	/**
	 * @param data als String-Array
	 * 
	 * Erste index: token, nicht null
	 * Zweite index: password, nicht null
	 */
	public ChangePassword(String[] data) {
		super(data);
		this.token = data[1];
		this.password = data[2];
	}

	/**
	 * @process wertet die Eingabe des Users aus. Das Passwort wird 
	 * nur unter den folgenden Bedingungen geändert und gespeichert:
	 * 
	 * -Wenn das richtige bzw. gültige Token mitgegeben 
	 * -Wenn das neue Passwort 3 - 20 Zeichen enthält
	 */
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token) 
			&& password.length() <= 20 
			&& password.length() >= 3) 
		{
			Account account = client.getAccount();
			account.changePassword(password);
			result = true;
		}
		client.send(new Result(this.getClass(), result));
		AccountManager.saveAccounts();
	}
}
