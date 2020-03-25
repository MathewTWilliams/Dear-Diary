package v1;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList; 
import java.time.LocalDate;
import java.time.LocalDateTime; 
import java.time.ZoneId;
import java.util.Collection; 

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
	
	public void addEntry(Entry entry)
	{
		String date = LocalDate.now(ZoneId.of(TIME_ZONE)).toString();
		if(!entries.containsKey(date))
		{
			entries.put(date, new ArrayList<Entry>());
		}
		
		entries.get(date).add(entry);
	}
	
	
	public String getEntry(LocalDateTime dateTime)
	{
		String date = LocalDate.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth()).toString();
		if(entries.containsKey(date))
		{
			ArrayList<Entry> dayEntries = entries.get(date);
			for(Entry entry : dayEntries)
			{
				if(entry.getDate().equals(dateTime))
				{
					return entry.getUserEntry();
				}
			}
		}
		
		return null; 
	}
	
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
