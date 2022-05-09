package miniproject_2.Messaging;

import miniproject_2.Server.Client;
import miniproject_2.model.ToDoManager;

/**
 * Diese Klasse repräsetiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class DeleteToDo extends Message {

	private String token;
	private int todoId;
	
	/**
	 * @param data als String-Array
	 * 
	 * Erster Index: token, nicht null
	 * Zweiter Index: todoId, nicht null
	 */
	public DeleteToDo(String[] data) {
		super(data);
		this.token = data[1];
		this.todoId = Integer.parseInt(data[2]);
	}

	/**
	 * @process wertet die Eingabe des Users aus. Das
	 * To-Do wird nur unter den folgenden Bedingungen gelöscht:
	 * 
	 * -Wenn das richtige bzw. gültige Token mitgegeben 
	 * -Wenn eine gültige bzw. existierende To-Do-ID eingegeben wurde
	 */
	public void process(Client client) {
		boolean result = false;
		if (client.getToken().equals(token)) 
		{
			ToDoManager.remove(todoId);
			result = true;
		}
		client.send(new Result(this.getClass(), result));
		ToDoManager.saveToDos();
	}
}
