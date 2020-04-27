package v1;

import java.time.LocalDateTime; 

/**
 * Class used to store the data of a basic text entry
 * @author Liam Donovan
 * @version 4.26.2020
 */
public class BasicEntry extends Entry {
	
	public BasicEntry(String text, LocalDateTime date)
	{
		super(text, date);
	}
}
