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


public class User implements Serializable 
{
	
	private static final String FILE_PATH = "Assets\\UserData.data"; 
	
	private static final long serialVersionUID = 1L; 
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
		return diary.getEntry(dateTime);
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
