package miniproject_2.model;

import miniproject_2.Messaging.Priority;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Diese Klasse repräsentiert ein To-Do. 
 * @author Rocco Saracino und Valentina Caldana
 */
public class ToDo implements Comparable<ToDo>, Serializable {

	private String title;
	private Priority priority;
	private String description;
	private LocalDate duedate;
	private Account account;
	private int id;

	/**
	 * Konstruktor Nr. 1 repräsentiert To-Do's mit Fälligkeitsdatum.
	 * 
	 * @param title als String
	 * @param priority (low, medium, high)
	 * @param description als String 
	 * @param duedate als JJJJ-MM-TT
	 * @param account, nicht null
	 */
	public ToDo(String title, Priority priority, String description, LocalDate duedate, Account account) {
		this.title = title;
		this.priority = priority;
		this.description = description;
		this.duedate = duedate;
		this.account = account;
	}

	//Konstruktor Nr. 2 repräsentiert To-Do's ohne Fälligkeitsdatum
	public ToDo(String title, Priority priority, String description, Account account) {
		this.title = title;
		this.priority = priority;
		this.description = description;
		this.account = account;
	}
	
	public int compareTo(ToDo o) {
        if (id > o.id) {
            return 1;
        } else if (id < o.id) {
            return -1;
        } else {
            return account.compareTo(o.account);
        }
    }

	public int getId() {
		return this.id;
	}

	public Account getAccount() {
		return account;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		if (duedate == null) {
			if(description == null) {
				return title + "|" + priority + "|" + "|";
			} else {
				return title + "|" + priority + "|" + description;
			}
		} else {
			if (description == null) {
				return title + "|" + priority + "|" + "|" + duedate;
			} else {
				return title + "|" + priority + "|" +description +"|" + duedate;
			}
		}
	}
	
	public LocalDate getDuedate() {
		return duedate;	
	}
}
