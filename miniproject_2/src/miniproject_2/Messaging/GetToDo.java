package miniproject_2.Messaging;

import miniproject_2.Server.Client;
import miniproject_2.model.ToDoManager;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class GetToDo extends Message {

	private String token;
	private int todoID;

	/**
	 * @param data als String-Array
	 * 
	 * Erster Index: token, nicht null
	 * Zweiter Index: todoID, nicht null
	 */
	public GetToDo(String[] data) {
		super(data);
		this.token = data[1];
		this.todoID = Integer.parseInt(data[2]); 
	}

	/**
	 * @process wertet die Eingabe des Users aus. Das gesuchte
	 * To-Do wird nur unter den folgenden Bedingungen ausgegeben:
	 * 
	 * -Wenn das richtige bzw. gültige Token mitgegeben wird
	 * -Wenn eine gültige bzw. existierede To-Do-ID eingegeben wurde
	 * 
	 * Merke: Es kann nur jeweils ein To-Do angezeigt werden.
	 */
	public void process(Client client) {
		
		if (client.getToken().equals(token)) 
		{
			var todo = ToDoManager.getToDo(todoID, client);
			if (todo == null) 
			{
				client.send(new Result(this.getClass(), false));
			} else {
				client.send(new Result(this.getClass(), true, String.valueOf(todo)));
			}
		}
	}
}
