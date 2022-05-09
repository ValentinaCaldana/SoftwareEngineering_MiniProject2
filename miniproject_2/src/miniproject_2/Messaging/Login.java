package miniproject_2.Messaging;

import miniproject_2.Server.Client;
import miniproject_2.model.AccountManager;
import miniproject_2.model.Account;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class Login extends Message {

	private String username;
	private String password;

	/**
	 * @param data als String-Array
	 * 
	 * Erster Index: username, nicht null
	 * Zweiter Index: password, nicht null
	 */
	public Login(String[] data) {
		super(data);
		this.username = data[1];
		this.password = data[2];
	}

	/**
	 * @process wertet die Eingabe des Users aus. Das
	 * Passwort und die Emailadresse werden nur unter den folgenden 
	 * Bedingungen akzeptiert und gesetzt:
	 * 
	 * -Wenn der Username/die Emailadresse existiert
	 * -Wenn das zugehörige Passwort eingegeben wird 
	 */
	public void process(Client client) {
		Account account = AccountManager.exists(username);
		String token = AccountManager.generateToken();
		
		if (account != null 
			&& account.checkPassword(password)) 
		{
			client.setAccount(account);
			client.setToken(token);
			
			client.send(new Result(this.getClass(), true, token));
		} else {
			client.send(new Result(this.getClass(), false));		}
	}

}
