package miniproject_2.Messaging;

import java.util.ArrayList;

import miniproject_2.Server.Client;
import miniproject_2.model.ToDoManager;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class ListToDos extends Message {

	private String token;
	
	/**
	 * @param data als String-Array
	 * 
	 * Erster Index: token, nicht null
	 */
	public ListToDos(String[] data) {
		super(data);
		this.token = data[1];
	}

	/**
	 * @process wertet die Eingabe des Users aus. Die
	 * To-Do-Liste wird nur unter der folgenden Bedingungen ausgegeben:
	 * 
	 * -Wenn das richtige bzw. gültige Token mitgegeben wird
	 * 
	 * Merke: Es werden nur die ID's der To-Do's in der Liste ausgegeben. 
	 */
	public void process(Client client) {
		if (client.getToken().equals(token)) 
		{
			ArrayList<String> ids = ToDoManager.listOfIDs(client);
			client.send(new Result(this.getClass(), true, ids));
		} else {
			client.send(new Result(this.getClass(), false));
		}
	}
}
