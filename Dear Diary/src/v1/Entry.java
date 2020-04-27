package v1;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Class used to store the data of all the entries in our Diary
 * @author Liam Donovan
 * @author Matt Williams
 * @version 4.26.2020
 */

public abstract class Entry implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	
	protected String text; //as entry or a caption for photo, video
	protected LocalDateTime date; 
  protected ArrayList<Comment> comments; 
	public Entry()
	{
		userEntry = ""; 
		date = LocalDateTime.now();
	}
	
	public Entry(String text, LocalDateTime date)
	{
		this.text = text; 
		this.date = date; 
		comments = new ArrayList<Comment>();
	}
	
	/**
	 * @return the userEntry
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * @param userEntry the userEntry to set
	 */
	public void setUserEntry(String text) {
		this.text = text;
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
	 * @return comments
	 */
	public ArrayList<Comment> getComments(){
		return comments;
	}
	
	/**
	 * @param comments
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * @param text
	 */
	public void newComment(String text) {
		comments.add(new Comment(text, LocalDateTime.now()));
	}
	
	/**
	 * @param index
	 * @return the removed Comment
	 */
	public Comment deleteComment(int index) {
		return comments.remove(index);
	}
	
	/**
	 * @param index
	 * @return the comment at index
	 */
	public Comment getComment(int index) {
		return comments.get(index);
	}
}