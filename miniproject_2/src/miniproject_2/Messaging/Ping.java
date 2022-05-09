package miniproject_2.Messaging;

import miniproject_2.Server.Client;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class Ping extends Message {

	private String token;

	/**
	 * @param data als String-Array
	 * 
	 * Optional - Erster Index: token, nicht null
	 */
	public Ping(String[] data) {
		super(data);
		if (data.length > 1)
			token = data[1];
	}

	/**
	 * @process wertet die Eingabe des Users aus. Wird kein Token mitgegeben,
	 * so gilt die Kommando-Eingabe als gülitg. Wird ein Token mitgegeben,
	 * so gilt die Eingabe als gültig, wenn Token und Client einander zugehörig sind.
	 */
	public void process(Client client) {
		boolean result = (token == null || token.equals(client.getToken()));
		client.send(new Result(this.getClass(), result));
	}
}
