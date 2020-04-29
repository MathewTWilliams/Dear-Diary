package v1;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Class used to store the data of a comment on one of the entries
 * @author Liam Donovan
 * @version 4.26.2020
 */
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String text;
	private LocalDateTime date;
	
	/**
	 * Parameterized Constructor
	 * @param text The text associated with the entry
	 * @param date The date the entry was made.
	 */
	public Comment(String text, LocalDateTime date) {
		this.text = text;
		this.date = date;
	}
	
	/**
	 * Accessor for the date of the entry.
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}
	
	/**
	 * Mutator to set the date
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	/**
	 * Mutator to set the text associated with the entry.
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Accessor to access the text associated with the entry.
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
