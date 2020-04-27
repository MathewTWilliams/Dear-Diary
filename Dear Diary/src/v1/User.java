package v1;

import java.util.ArrayList; 
import java.util.Date;
import java.util.List; 
import java.time.Clock;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import java.time.ZoneId;


public class User 
{
	private String name;
	private String dateOfBirth; 
	private String gender; 
	private List<Disorder> knownDisorders = new ArrayList<Disorder>(); 
	private Diary diary = new Diary(); 
	
	private static final String TIME_ZONE = "America/New_York";
	
	
	public User()
	{
		name = dateOfBirth = "";
	}
	
	
	public User(String name, String gender, String dob)
	{
		this.name = name; 
		this.dateOfBirth = dob; 
		this.gender = gender; 
	}
	
	public void logEntry(String entry)
	{
		LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
		Entry newEntry = new BasicEntry(entry, now);
		diary.addEntry(newEntry);
	}
	
	public ArrayList<Entry> getUserEntries()
	{
		return diary.getEntries();
	}
	
	public String findEntry(LocalDateTime dateTime)
	{
		return diary.getEntryText(dateTime);
	}
	
	public void addDisorder(Disorder disorder)
	{
		if(!knownDisorders.contains(disorder)) {
			knownDisorders.add(disorder);
		}
	}
	
	public void removeDisorder(Disorder disorder)
	{
		if(knownDisorders.contains(disorder))
		{
			knownDisorders.remove(disorder);
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	public int getNumberofDiaryEntrees()
	{
		return diary.numberOfEntries();
	}
	
	
	
}
