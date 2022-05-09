package miniproject_2.Server;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import miniproject_2.model.AccountManager;
import miniproject_2.model.ToDoManager;

/**
 * Diese Klasse repräsentiert einen simplen Server. Er muss zunächst erfolgreich
 * gestartet werden, um anschliessend vom TestClient genutzen werden zu können.
 * 
 * @author Richard Bradley, Rocco Saracino und Valentina Caldana
 */
public class Server {

	private static final Logger logger = Logger.getLogger("");
	private static int port = -1;

	public static void main(String[] args) {

		// Zunächst soll der Logger eingerichtet werden:
		setupLogging();

		/*
		 * Gespeicherte Daten (kreierte Accounts, To-Do's) aus vorigen 
		 * Sitzungen werden hier geladen:
		 */
		logger.info("Read any existing data");
		ToDoManager.readToDos();
		AccountManager.readAccounts();

		readOptions();

		try {
			ClientThread lt = new ClientThread(port);
			lt.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Diese Methode richtet den Logger ein:
	 */
	private static void setupLogging() {
		logger.setLevel(Level.FINE);
		logger.getHandlers()[0].setLevel(Level.WARNING);
		try {
			FileHandler fh = new FileHandler("%h/ToDoServer_%u_%g.log", 10000000, 2);
			fh.setFormatter(new SimpleFormatter());
			fh.setLevel(Level.FINE);
			logger.addHandler(fh);

		} catch (Exception e) {
			logger.severe("Unable to create file handler for logging: " + e.toString());
			throw new RuntimeException("Unable to initialize log files: " + e.toString());
		}
	}

	/**
	 * In dieser Methode wird definiert, welche Eingaben der Server zum Start
	 * erwartet (gültige Portnummer):
	 */
	private static void readOptions() {
		logger.info("Read start-up info from the console");
		try (Scanner in = new Scanner(System.in)) {
			while (port < 1024 || port > 65535) {
				System.out.println("Enter the port number (1024-65535): ");
				String s = in.nextLine();
				try {
					port = Integer.parseInt(s);
				} catch (NumberFormatException e) {
				}
			}
			System.out.println("Server started successfully!");
		}
	}

}
