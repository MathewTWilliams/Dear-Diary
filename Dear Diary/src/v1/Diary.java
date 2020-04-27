package v1;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList; 
import java.time.LocalDate;
import java.time.LocalDateTime; 
import java.time.ZoneId;
import java.util.Collection; 

/**
 * Class used to store the data of a basic text entry
 * @author Matt Williams, Liam Donovan
 * @version 4.26.2020
 */
public class Diary 
{
	//for the key in the hashmap could also be local date
	private HashMap<String, ArrayList<Entry>> entries; 
	
	private static final String TIME_ZONE = "America/New_York";

	public Diary()
	{
		entries = new LinkedHashMap<String, ArrayList<Entry>>(); 
		makeFakeEntries(); //for testing purposes
	}
	
	/**
	 * Adds an entry to the HashMap entries
	 * @param entry
	 */
	public void addEntry(Entry entry)
	{
		String date = LocalDate.now(ZoneId.of(TIME_ZONE)).toString();
		if(!entries.containsKey(date))
		{
			entries.put(date, new ArrayList<Entry>());
		}
		
		entries.get(date).add(entry);
	}
	
	/**
	 * Returns the text an entry holds
	 * @param dateTime
	 * @return the entries text
	 */
	public String getEntryText(LocalDateTime dateTime)
	{
		String date = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()).toString();
		if(entries.containsKey(date))
		{
			ArrayList<Entry> dayEntries = entries.get(date);
			for(Entry entry : dayEntries)
			{
				if(entry.getDate().equals(dateTime))
				{
					return entry.getText();
				}
			}
		}
		
		return null; 
	}
	
	/**
	 * Returns the number of entries
	 * @return the number of entries
	 */
	public int numberOfEntries()
	{
		int num = 0; 
		Collection<ArrayList<Entry>> values = entries.values();
		for(ArrayList<Entry> dayEntries : values)
		{
			num += dayEntries.size();
		}
		
		return num; 
	}
	
	/**
	 * Retrurns an ArrayList of all the entries
	 * @return currentEntries
	 */
	public ArrayList<Entry> getEntries()
	{
		ArrayList<Entry> currentEntries = new ArrayList<Entry>();
		Collection<ArrayList<Entry>> values = entries.values();
		
		for(ArrayList<Entry> value: values)
		{
			currentEntries.addAll(value);
		}
		return currentEntries;
	}
	
	/**
	 * Returns an ArrayList of all the comments corresponding to the given entry
	 * @param entry
	 * @return comments
	 */
	public ArrayList<Comment> getEntryComments(Entry entry){
		return entry.getComments();
	}
	
	/**
	 * Returns an ArrayList of all the comments corresponding the entry at the given time
	 * @param dateTime
	 * @return comments
	 */
	public ArrayList<Comment> getEntryComments(LocalDateTime dateTime){		
		String date = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()).toString();
		if(entries.containsKey(date))
		{
			ArrayList<Entry> dayEntries = entries.get(date);
			for(Entry entry : dayEntries)
			{
				if(entry.getDate().equals(dateTime))
				{
					return entry.getComments();
				}
			}
		}
		
		return null; 
	}
	
	/**
	 * Returns the comment at made on the given entry, at the given time
	 * @param entry
	 * @param dateTime
	 * @return comment
	 */
	public Comment getComment(Entry entry, LocalDateTime dateTime) {
		String date = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()).toString();
		ArrayList<Comment> comments = entry.getComments();
		for(Comment comment : comments) {
			if(comment.getDate().equals(dateTime)) {
				return comment;
			}
		}
		
		return null; 
	}
	
	
	/**
	 * test function to test GUI
	 */
	private void makeFakeEntries()
	{
		LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
		
		Entry entry1 = new BasicEntry("I'm feeling okay", now.minusHours(3));
		Entry entry2 = new BasicEntry("I'm feeling terrible", now.minusHours(2));
		Entry entry3 = new BasicEntry("I'm feeling great", now.minusHours(1));
		
		addEntry(entry1);
		addEntry(entry2);
		addEntry(entry3);
		
		
	}
	
}
