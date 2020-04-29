package v1;

import java.util.ArrayList; 
import java.util.Date;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Clock;
import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter; 
import java.time.ZoneId;

/**
 * This class holds most of the data about the user
 * @author Matt Williams
 * @author Liam Donovan
 * @version 4.27.2020
 */
public class User implements Serializable 
{
	
	private static final String FILE_PATH = "Assets\\UserData.data"; 
	
	private static final long serialVersionUID = 2L; 
	private String name;
	private String dateOfBirth; 
	private String gender; 
	private Diary diary = new Diary(); 
	
	private static final String TIME_ZONE = "America/New_York";
	
	/**
	 * Default Constructor
	 */
	public User()
	{
		name = dateOfBirth = "";
	}
	
	/**
	 * Parameterized Constructor
	 * @param name Name of User
	 * @param gender gender of user
	 * @param dob Date of birth of user
	 */
	public User(String name, String gender, String dob)
	{
		this.name = name; 
		this.dateOfBirth = dob; 
		this.gender = gender; 
	}
	
	/**
	 * Method used to log a basic text entry;
	 * @param entry The text to be put in the entry
	 */
	public void logBasicEntry(String entry)
	{
		LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
		Entry newEntry = new BasicEntry(entry, now);
		diary.addEntry(newEntry);
	}
	
	/**
	 * Method used to Log Video and Photo entries
	 * @param fileName The name of the file
	 * @param isPhoto Is this file a photo?
	 */
	public void logFileEntry(String fileName, String text, boolean isPhoto)
	{
		LocalDateTime now = LocalDateTime.now(ZoneId.of(TIME_ZONE));
		Entry newEntry;
		
		if(isPhoto)
		{
			newEntry = new PhotoEntry(fileName,text,now);
		}
		else
		{
			newEntry = new VideoEntry(fileName,text,now);
		}
		
		diary.addEntry(newEntry);
	}
	
	/**
	 * Method used to access all the user entries
	 * @return
	 */
	public ArrayList<Entry> getUserEntries()
	{
		return diary.getEntries();
	}
	
	/**
	 * Method used to find an entry based on a dateTime
	 * @param dateTime
	 * @return The text associated with the entry with the given date. 
	 */
	public String findEntry(LocalDateTime dateTime)
	{
		return diary.getEntryText(dateTime);
	}

	/**
	 * Getter for the user's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the user's name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the date of birth of the user.
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * Setter for the date of birth of the user
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * Getter for the gender of the user.
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * Setter for the gender of the user.
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * Method used to access the number of entries the user has. 
	 * @return
	 */
	public int getNumberofDiaryEntrees()
	{
		return diary.numberOfEntries();
	}
	
	/**
	 * Static method used to serialize the user data passed to the method
	 * @param data The data to be serialized
	 */
	public static void serializeData(User data)
	{
		FileOutputStream fs = null;
		ObjectOutputStream os = null; 
		
		try
		{
			fs = new FileOutputStream(FILE_PATH);
			os = new ObjectOutputStream(fs);
			os.writeObject(data);
		}
		catch(IOException e)
		{
			e.printStackTrace(); 
			return; 
		}
		
		finally
		{
			try
			{
				os.close(); 
				fs.close(); 
			}
			
			catch(IOException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Static method used to deserializesave data and return that data
	 * @return The deserialized user data
	 */
	public static User deserializeData()
	{
		FileInputStream fs = null; 
		ObjectInputStream os = null; 
		
		try
		{
			fs = new FileInputStream(FILE_PATH);
			os = new ObjectInputStream(fs);
			
			return (User)os.readObject();
		}
		
		catch(ClassNotFoundException e)
		{
			System.out.println("Can't read old serialized object. Returning Null");
			return null; 
		}
		
		catch(FileNotFoundException e)
		{
			System.out.println("User data file not found. Returning Null");
			
			return null; 
		}
		
		catch(IOException e)
		{
			System.out.println("IOException occurred during deserialization. Returning null");
			e.printStackTrace();
			return null; 
		}
		
		finally
		{
			try
			{
				if(os != null)
				{
					os.close(); 
				}
				
				if(fs != null)
				{
					fs.close(); 
				}
			}
			
			catch(IOException e)
			{
				System.out.println("Something else is happening here.");
				System.out.println(e.getMessage());
			}
		}
	}
	
	
	
}
