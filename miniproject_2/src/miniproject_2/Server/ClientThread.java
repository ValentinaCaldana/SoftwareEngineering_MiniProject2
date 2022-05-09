package miniproject_2.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Diese Klasse repräsentiert ein Thread.  
 * @author Rocco Saracino und Valentina Caldana
 */
public class ClientThread extends Thread {

	private static Logger logger = Logger.getLogger("");
	private final ServerSocket serverSocket;
	private final int port;

	/**
	 * Der Konstruktor nimmt eine Portnummer entgegen
	 * und erstellt ein ServerSocket für die Netzwerkkommunikation.
	 * 
	 * @param port, nicht null
	 * @throws IOException
	 */
	public ClientThread(int port) throws IOException {
		super();
		this.port = port;
		this.setName("ClientThread");
		serverSocket = new ServerSocket(port, 10, null);
	}

	/**
	 * @run erstellt die Verbindung zum Server:
	 */
	public void run() {
		logger.info("Starting listener on port " + port);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ClientManager.add(new Client(socket));
			} catch (Exception e) {
				logger.info(e.toString());
			}
		}
	}
}
