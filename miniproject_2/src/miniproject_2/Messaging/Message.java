package miniproject_2.Messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import miniproject_2.Server.Client;

/**
 * Diese abstrakte Klasse repräsentiert eine Nachrichten- bzw. 
 * eine Server-Rückmeldung.
 * @author Richard Bradley, Rocco Saracino und Valentina Caldana
 */
public abstract class Message {
	
	private static Logger logger = Logger.getLogger("");
	private String[] data;
	
	/**
	 * Die Konstruktoren repräsentieren die Nachrichtenzeilen,
	 * bzw. "Kommandozeilen" die vom User eingegeben werden.
	 * 
	 * @param data als String-Array
	 */
	public Message(String[] data) { // Konstruktor Nr. 1: Data
		this.data = data;
	}
	
	public Message(String[] data, ArrayList<String> elements) { // Konstruktor Nr. 2: Data und Liste
		this.data = new String[data.length + elements.size()];
		for (int i = 0; i < data.length; i++)
			this.data[i] = data[i];
		for (int i = 0; i < elements.size(); i++)
			this.data[i + data.length] = elements.get(i);
	}

	/**
	 * @process: Vorgegebene (abstrakte) Methode für die Prozesse aller "Kommando-Klassen"
	 * @param client, nicht null
	 */
	public abstract void process(Client client);

	// Diese Methode wickelt den Versand von Nachrichten ab:
	public void send(Socket socket) throws IOException {
		OutputStreamWriter out;
		out = new OutputStreamWriter(socket.getOutputStream());
		logger.info("Sending message: " + this.toString());
		out.write(this.toString() + "\n");
		out.flush();
	}

	/**
	 * Diese Methode wickelt den Empfang von Nachrichten ab:
	 *  1. Zeilen einlesen
	 *  2. Trennung der Zeileninhalte bei '|'
	 *  3. Suche nach der betreffenden "Kommando-Klasse" gemäss Pfad und
	 *     Verwendung von deren Konstruktor 
	 * 
	 * @param socket, nicht null 
	 * @return Message-Objekt, nicht null
	 * 
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 * @throws NoClassDefFoundError
	 */
	public static Message receive(Socket socket)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException, NoClassDefFoundError {
		
		BufferedReader in;
		Message msg = null;

		try {
			//1
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String msgText = in.readLine(); 
			logger.info("Receiving message: " + msgText);

			//2
			String[] parts = msgText.split("\\|",-1);
			for (int i = 0; i < parts.length; i++) {
				parts[i] = parts[i].trim();
			}

			//3
			String messageClassName = Message.class.getPackage().getName() + "." + parts[0];
			Class<?> messageClass = Class.forName(messageClassName);
			Constructor<?> constructor = messageClass.getConstructor(String[].class);

			msg = (Message) constructor.newInstance(new Object[] { parts }); // Konstruktor aufrufen, gemäss betreffender "Kommando-Klasse"
			logger.info("Received message of type " + parts[0]);

		} catch (IOException e) {
			logger.warning(e.toString());
			socket.close();
			throw new IOException();
		}
		return msg;
	}

	public String toString() {
		return String.join("|", data);
	}
}
