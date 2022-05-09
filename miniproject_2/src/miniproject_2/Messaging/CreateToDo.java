package miniproject_2.Messaging;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import miniproject_2.Server.*;
import miniproject_2.model.ToDo;
import miniproject_2.model.ToDoManager;

/**
 * Diese Klasse repräsentiert das gleichnamige Kommando. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class CreateToDo extends Message {

	private String token;
	private String title;
	private Priority priority;
	private String description;
	private LocalDate duedate;
	
	private String[] data;

	/**
	 * @param data als String-Array:
	 * 
	 * Erster Index: token, nicht null
	 * Zweiter Index: title, nicht null
	 * Dritter Index: priority(low, medium, high), nicht null
	 * Vierter Index: description
	 * Optional - Fünfter Index: data(duedate)
	 */
	public CreateToDo(String[] data) {
		super(data);
		this.data = data;
		this.token = data[1];
		this.title = data[2];
		this.priority = Priority.valueOf(data[3].toLowerCase());
		if(data.length==4) {
			this.description = "";
		} else {
			this.description = data[4];
		}
	}

	/**
	 *  @process wertet die Eingabe des Users aus. Das To-Do wird nur unter 
	 *  den folgenden Bedingungen kreiert und gespeichert:
	 *  
	 * -Wenn das richtige bzw. gültige Token mitgegeben wird
	 * -Wenn der Titel 3 - 20 Zeichen enthält
	 * -Wenn eine gültige Priorität (gem. Enum) eingegeben wird
	 * -Wenn die Beschreibung nicht mehr als 255 Zeichen enthält
	 * -Bei Eingabe des Fälligkeitsdatums: Wenn das Datum nicht in der Vergangenheit liegt
	 * -Bei Eingabe des Fälligkeitsdatums: Wenn das Datumsformat 'JJJJ-MM-TT' eingehalten wird
	 */
	public void process(Client client) {
		
		if ((data.length == 5 || data.length == 4)
				&& client.getToken().equals(token) 
				&& title != null 
				&& title.length() >= 3 
				&& title.length() <= 20
				&& priority != null 
				&& description.length() <= 255)
		{
			var newToDo = new ToDo(title, priority, description, client.getAccount());
			ToDoManager.createToDo(newToDo);
			
			client.send(new Result(this.getClass(), true, String.valueOf(newToDo.getId())));
			ToDoManager.saveToDos();
		} else {
			try {
				this.duedate = LocalDate.parse(data[5]);
				if(!(LocalDate.now().isBefore(duedate) || LocalDate.now().equals(duedate))) {
					client.send(new Result(this.getClass(), false));
					return;
				}
			}catch (DateTimeParseException e) {
				client.send(new Result(this.getClass(), false));
			return;
					}
		
			if (client.getToken().equals(token) && title != null 
					&& title.length() >= 3 
					&& title.length() <= 20
					&& priority != null
					&& description.length() <= 255
				)
			{
				var newToDo = new ToDo(title, priority, description, duedate, client.getAccount());
				ToDoManager.createToDo(newToDo);
				
				client.send(new Result(this.getClass(), true, String.valueOf(newToDo.getId())));
				ToDoManager.saveToDos();
			} else {
				client.send(new Result(this.getClass(), false));
			}
		}
	}
}
