package miniproject_2.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Logger;

import miniproject_2.Server.Client;

/**
 * Diese Klasse verwaltet die Funktionalitäten eines To-Do
 * wie, das Löschen eines To-Do, das Hinzufügen/Erstellen und Speichern  
 * neuer To-Do's, die Ausgabe einer To-Do-Liste (der gespeicherten ID's) 
 * oder die Ausgabe eines bereits gespeicherten To-Do. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class ToDoManager implements Serializable {

	private static Logger logger = Logger.getLogger("");

	//TreeSet, um Redundanzen zu vermeiden:
	private static TreeSet<ToDo> todos = new TreeSet<ToDo>();

	/**
	 * Erstellt ein neues ToDo-Objekt und fügt es einem TreeSet<ToDo> hinzu.
	 * 
	 * @param toDo, nicht null 
	 * @return Eine To-Do-ID
	 */
	public static int createToDo(ToDo toDo) {
		var MAX = 0;
		synchronized (todos) {
			for (ToDo existingTodo : todos) 
			{
				if (existingTodo.getAccount().equals(toDo.getAccount())) 
				{
					if (existingTodo.getId() > MAX) 
					{
						MAX = existingTodo.getId();
					}
				}
			}
		}
		toDo.setId(MAX + 1);
		todos.add(toDo);
		return toDo.getId();
	}

	/**
	 * Löscht ein ToDo-Objekt aus TreeSet<ToDo> mittels To-Do-ID.
	 * @param todoId, nicht null
	 */
	public static void remove(int todoId) {
		synchronized (todos) {
			todos.removeIf(todo -> todo.getId() == todoId);
		}
	}

	/**
	 * Gibt alle To-Do-ID's des TreeSet<ToDo> aus.
	 * 
 	 * @param client, nicht null
	 * @return Liste von To-Do-ID's
	 */
	public static ArrayList<String> listOfIDs(Client client) {
		ArrayList<String> IDs = new ArrayList<>();
		synchronized (todos) {
			for (ToDo t : todos)
				if (t.getAccount().equals(client.getAccount())) 
				{
					IDs.add(Integer.toString(t.getId()));
				}
		}
		return IDs;
	}

	/**
	 * Nimmt eine To-Do-ID entgegen und gibt das entsprechende To-Do-Objekt aus.
	 * 
	 * @param id als int, nicht null
	 * @param client, nicht null
	 * @return entsprechendes To-Do-Objekt
	 */
	public static ToDo getToDo(int id, Client client) {
		synchronized (todos) {
			for (ToDo t : todos) 
			{
				if (t.getId() == id
				&& t.getAccount().equals(client.getAccount())) 
				{
					return t;
				}
			}
			return null;
		}
	}

	/**
	 * Schreibt und speichert eine File-Datei mit To-Do-Objekten
	 */
	public static void saveToDos() {
		File toDosFile = new File("src\\miniproject_2\\saveLoad\\todos.sav");
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(toDosFile))) {
			synchronized (todos) {
				out.writeObject(todos);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			logger.severe("Unable to save toDos: " + e.getMessage());
		}
	}

	/**
	 * Diese Methode liest die gespeicherten ToDo's bei Programmstart ein:
	 */
	public static void readToDos() {
		File toDosFile = new File("src\\miniproject_2\\saveLoad\\todos.sav");
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(toDosFile))) {
			todos = (TreeSet<ToDo>) in.readObject();
		} catch (Exception e) {
			todos = new TreeSet<ToDo>();
			logger.severe("Unable to read toDos: " + e.getMessage());
		}
	}

}
