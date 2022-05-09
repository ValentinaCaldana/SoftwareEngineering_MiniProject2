package miniproject_2.Server;

import miniproject_2.Messaging.Message;
import miniproject_2.Messaging.Result;
import miniproject_2.model.Account;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Diese Klasse repräsentiert einen Client.
 * @author Rocco Saracino und Valentina Caldana
 */
public class Client implements messageInterface {
	
	private static Logger logger = Logger.getLogger("");
	private String token = null;
	private Account account = null;
	private Socket socket;
	private boolean clientReachable = true;

	/**
	 * Im Konstruktor wird ein Thread erstellt, in welchem
	 * der Client eingerichtet wird. Das heisst, dass der
	 * Client über die verschiedenen Messaging-Optionen bzw. 
	 * Kommando-Optionen "in Kenntnis gesetzt" wird und via @process
	 * die Fähigkeit erhält diese auszuführen (@run).
	 * 
	 * @param socket, nicht null
	 */
	public Client(Socket socket) {
		this.socket = socket;

		var t = new Thread(() -> {

			while (clientReachable) {
				try {
					Message msg = Message.receive(socket);
					msg.process(this); //this = Referenz auf Runnable

				} catch (IOException e) { //Wenn die Verbindung unterbrochen wird
					this.send(new Result(this.getClass(), false, "Connection error"));
					clientReachable = false;
					token = null; 
					account = null;

				} catch (Exception e) { // Fehlerhafte Formate
					this.send(new Result(this.getClass(), false));

				} catch (NoClassDefFoundError e) { // Fehlerhafte Kommandoeingabe
					this.send(new Result(this.getClass(), false));
				}
			}
		});
		t.start();
		logger.info("New client created");
	}

	/**
	 * Sendet eine Anfrage an den Server.
	 */
	public void send(Message msg) {
		try {
			msg.send(socket);
		} catch (IOException e) {
			logger.warning("Client " + this.getName() + " unreachable; logged out");
			this.token = null;
			clientReachable = false;
		}
	}

	public String getName() {
		String name = null;
		if (account != null)
			name = account.getEmail();
		return name;
	}
	
	public String getToken() {
		return token;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Account getAccount() {
		return account;
	}
}
