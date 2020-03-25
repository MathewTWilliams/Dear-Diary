package v1;

import java.time.LocalDateTime;;

public abstract class Entry 
{
	protected String userEntry; //as entry or a caption for photo, video
	protected LocalDateTime date; 
	
	
	public Entry(String entry, LocalDateTime date)
	{
		this.userEntry = entry; 
		this.date = date; 
	}
	
	/**
	 * @return the userEntry
	 */
	public String getUserEntry() {
		return userEntry;
	}
	/**
	 * @param userEntry the userEntry to set
	 */
	public void setUserEntry(String userEntry) {
		this.userEntry = userEntry;
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

}
