package miniproject_2.Server;

import miniproject_2.Messaging.Message;

/**
 * Interface definiert 2 abstrakte Methoden:
 * @send(): Versand von Nachrichten
 * @getName(): Für die Identifizierung des Nachrichtenzielortes 
 * 
 * @author Rocco Saracino und Valentina Caldana
 */
public interface messageInterface {

	public abstract String getName(); 
	public abstract void send(Message msg); 

}
