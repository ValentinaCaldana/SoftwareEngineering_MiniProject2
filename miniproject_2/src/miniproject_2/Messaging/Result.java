package miniproject_2.Messaging;

import java.util.ArrayList;

import miniproject_2.Server.Client;

/**
 * Diese Klasse repräsentiert die Server-Rückmeldungen.
 * @author Richard Bradley, Rocco Saracino und Valentina Caldana
 */
public class Result extends Message {

	/**
	 * Konstruktor Nr. 1 repräsentiert die Validierung der Resultate
	 * Result | true oder Result | false
	 * Er wird von den Klassen ChangePassword, CreateLogin, DeleteToDo,
	 * Logout und Ping verwendet
	 */
	public Result(Class<?> msgClass, boolean result) {
		super(new String[] { "Result", Boolean.toString(result) });
	}

	/**
	 * Konstruktor Nr. 2 repräsentiert die Validierung der Resultate
	 * inkl. der Rückgabe eines via Kommando geforderten Wertes. 
	 * Result | true | data
	 * Er wird von den Klassen Login, GetToDo und CreateToDo verwendet
	 */
	public Result(Class<?> msgClass, boolean result, String data) {
		super(new String[] { "Result", Boolean.toString(result), data });
	}

	/**
	 * Konstruktor Nr. 3 repräsentiert die Validierung der Resultate
	 * inkl. der Rückgabe einer via Kommando geforderten Liste mit Werten. 
	 * Result | true | list
	 * Er wird von der Klasse ListToDos verwendet
	 */
	public Result(Class<?> msgClass, boolean result, ArrayList<String> list) {
		super(new String[] { "Result", Boolean.toString(result) }, list);
	}

	/**
	 * Die Klasse Message fordert die nachfolgende Implementation. Sie wird aber nicht weiter
	 * verwendet...
	 */
	public void process(Client clientManager) {
	}
}
