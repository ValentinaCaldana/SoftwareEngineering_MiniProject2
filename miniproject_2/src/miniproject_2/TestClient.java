package miniproject_2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Diese Klasse ermöglicht die Eingabe verschiedener Kommandos in der Konsole.
 * Damit erfolgt eine Kommunikation mit dem Server.
 * 
 * @author Richard Bradley, Rocco Saracino und Valentina Caldana
 */
public class TestClient {
	
	public static void main(String[] args) {

		String ipAddress = null;
		int portNumber = 0;

		try (Scanner in = new Scanner(System.in)) {
			boolean valid = false;

			 // In diesem Absatz wird die IP-Adresse eingelesen:
			while (!valid) {
				System.out.println("Enter the IP-Address of the server: ");
				ipAddress = in.nextLine();
				valid = validateIpAddress(ipAddress);
			}

			// In diesem Absatz wird die Portnummer eingelesen:
			valid = false;
			while (!valid) {
				System.out.println("Enter the port number on the server: "); 
				String strPort = in.nextLine();
				valid = validatePortNumber(strPort);
				if (valid)
					portNumber = Integer.parseInt(strPort);
			}

			Socket socket = null;

			/**
			 * Hier werden IP-Adresse und Portnummer an das Socket-Objekt übergeben,
			 * sofern die Eingabe korrekt ist. Andernfalls wird ausgegeben, dass die Eingabe
			 * fehlerhaft war. 
			 */
			try {
				socket = new Socket(ipAddress, portNumber);

				System.out.println("Connected!");
				try (var socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						var out = new PrintWriter(socket.getOutputStream(), true)) {

					// Thread-Objekt wird eingebaut:
					Runnable r = () -> {
						while (true) {
							String msg;
							try {
								msg = socketIn.readLine();
								System.out.println("Received: " + msg);
							} catch (IOException e) {
								break;
							}
							if (msg == null)
								break;
						}
					};
					Thread t = new Thread(r);
					t.start();

					// Nun folgt eine kurze Beschreibung (Ausgabe) der möglichen Kommandos in Listenform:
					System.out.println("\nEnter commands to server and confirm by pressing the ENTER-key\n\n");
					System.out.println("***LIST OF COMMANDS***");
					System.out.println("- Create an account (password >= 3 characters):\n  CreateLogin|Enter A Email|Enter A Password\n");
					System.out.println("- Log into your account:\n  Login|Enter Your Email|Enter Your Password\n");
					System.out.println("- Change your password (new password >= 3 characters):\n  ChangePassword|Token|Enter New Password\n");
					System.out.println("- To log out of your account:\n  Logout\n");
					System.out.println("- Create a To-Do (title >= 3 characters | for priority, enter \"low\", \"medium\" or \"high\" "
							+ "| for description <= 255 characters | optional duedate = format JJJJ-MM-TT):\n  CreateToDo|Token|Title|Priority|Description|Duedate\n");
					System.out.println("- Read a specific To-Do:\n  GetToDo|Token|ID\n");
					System.out.println("- Delete a specific To-Do:\n  DeleteToDo|Token|ID\n");
					System.out.println("- To see your To-Do-List (to view all ID's):\n  ListToDo|Token\n");
					System.out.println("- Ping (note that entering the token is optional):\n  Ping|Token\n");
					System.out.println("***********************");
					
					// Eingegebene Kommandos werden eingelesen:
					while (in.hasNext()) {
						String line = in.nextLine();
						out.println(line);
						System.out.println("Sent: " + line);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				System.out.println("\nPort number or IP-Address is incorrect!");
			} finally {
				if (socket != null)
					try {
						socket.close();
					} catch (IOException e) {
						
					}
			}
		}
	}

	// Diese Methode überprüft die Richtigkeit der eingegebenen IP-Adresse:
	private static boolean validateIpAddress(String ipAddress) {
		boolean valid = false;
		String[] parts = ipAddress.split("\\.", -1);

		/**
		 *  Hier wird die Syntax der IP-Adresse geprüft (4-teilig,
		 *  Integer-Werte, Zahlen zwischen 0 und 255):
		 */
		if (parts.length == 4) {
			valid = true;
			for (String part : parts) {
				try {
					int value = Integer.parseInt(part);
					if (value < 0 || value > 255)
						valid = false;
				} catch (NumberFormatException e) {
					valid = false;
				}
			}
		}

		/**
		 * Wenn die IP-Adresse nicht syntaktisch mit den obigen Vorgaben übereinstimmt, 
		 * soll auch die Eingabe einer symbolischen Adresse  ermöglicht werden 
		 *(mind. 2-teilig, mind. 2 char, char jeglicher Art):
		 */
		if (!valid) {
			if (parts.length >= 2) {
				valid = true;
				for (String part : parts) {
					if (parts.length < 2)
						valid = false;
				}
			}
		}
		return valid;
	}
	
	
	/**
	 * Diese Methode überprüft die Richtigkeit der eingegebenen Portnummer (1024-65535):
	 * 
	 * @param portText, nicht null
	 * @return true wenn das Format richtig ist
	 */
	private static boolean validatePortNumber(String portText) {
		boolean formatOK = false;

		try {
			int portNumber = Integer.parseInt(portText);
			if (portNumber >= 1024 & portNumber <= 65535) {
				formatOK = true;
			}
		} catch (NumberFormatException e) {

		}
		return formatOK;
	}
}
