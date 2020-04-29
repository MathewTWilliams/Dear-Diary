package v1;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	
	private static final String TIME_ZONE = "America/New_York";
	
	/**
	 * Default Constructor
	 */
	public Entry()
	{
		text = "";
		date = LocalDateTime.now();
	}
	
	/**
	 * Parameterized Constructor
	 * @param text The text associated with the entry.
	 * @param date The date the entry was made. 
	 */
	public Entry(String text, LocalDateTime date)
	{
		this.text = text; 
		this.date = date; 
		comments = new ArrayList<Comment>();
	}
	
	/**
	 * Accessor for the entry's text
	 * @return the userEntry
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Mutator ffor the Entry's text
	 * @param userEntry the userEntry to set
	 */
	public void setUserEntry(String text) {
		this.text = text;
	}
	
	/**
	 * Accessor for the date the entry was made
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}
	
	/**
	 * Mutator to set the date associated with the entry
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
	/**
	 * Returns the list of comments on this entry
	 * @return comments
	 */
	public ArrayList<Comment> getComments(){
		return comments;
	}
	
	/**
	 * Mutator to set the list of comments on this entry.
	 * @param comments
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Method used to add a new comment to the entry.
	 * @param text The text for the comment
	 */
	public void newComment(String text) {
		comments.add(new Comment(text, LocalDateTime.now(ZoneId.of(TIME_ZONE))));
	}
	
	/**
	 * Method to delete a comment via an index
	 * @param index The index of the comment to delete.
	 * @return the removed Comment
	 */
	public Comment deleteComment(int index) {
		return comments.remove(index);
	}
	
	/**
	 * Method used to get a single comment based on the index passed it.
	 * @param index Index of comment
	 * @return the comment at index
	 */
	public Comment getComment(int index) {
		return comments.get(index);
	}
}