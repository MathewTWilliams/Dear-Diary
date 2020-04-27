package v1;

import java.time.LocalDateTime;


/**
 * Class used to store the data of a comment on one of the entries
 * @author Liam Donovan
 * @version 4.26.2020
 */
public class Comment {
	private String text;
	private LocalDateTime date;
	
	
	public Comment(String text, LocalDateTime date) {
		this.text = text;
		this.date = date;
	}
	
	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}
	
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
