package miniproject_2.Messaging;

import miniproject_2.Server.Client;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class Logout extends Message {

	/**
	 * Der Konstruktor erwartet keinen Variablen
	 */
	public Logout(String[] data) {
		super(data);
	}

	/**
	 * @process wertet die Eingabe des Users aus (das Kommando). 
	 * Das Token und der Account werden automatisch auf null gesetzt.
	 */
	public void process(Client client) {
		client.setToken(null); 
		client.setAccount(null); 
		client.send(new Result(this.getClass(), true));
	}
}
