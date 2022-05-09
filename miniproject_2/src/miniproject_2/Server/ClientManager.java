package miniproject_2.Server;

import java.util.ArrayList;

/**
 * Diese Klasse verwaltet die Funktionalitäten eines Clients, 
 * wie das Hinzufügen eines Clients oder das Überprüfen der Existenz  
 * des betreffenden Clients. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class ClientManager {

	public static ArrayList<Client> clients = new ArrayList<>();

	/**
	 * Erstellt ein neues Client-Objekt und fügt es der ArryList<Client> hinzu.
	 * 
	 * @param client, nicht null
	 */
	public static void add(Client client) {
		synchronized (client) {
			clients.add(client);
		}
	}

	/**
	 * Überprüft, ob ein Client existiert mittels Email.
	 * 
	 * @param email, nicht null
	 * @return ein Client, wenn die übergebene Email existiert
	 */
	public static Client exists(String email) {
		synchronized (clients) {
			for (Client c : clients) {
				if (c.getAccount() != null && c.getName().equals(email))
					return c;
			}
		}
		return null;
	}
}
