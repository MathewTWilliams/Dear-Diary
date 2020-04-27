package v1;

import java.io.Serializable;
import java.time.LocalDateTime; 

/**
 * Class used to store the data of a basic text entry
 * @author Liam Donovan
 * @author Matt Williams
 * @version 4.26.2020
 */

public class BasicEntry extends Entry implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public BasicEntry()
	{
		super();
	}
	
	public BasicEntry(String entry, LocalDateTime date)

	{
		super(text, date);
	}
}
