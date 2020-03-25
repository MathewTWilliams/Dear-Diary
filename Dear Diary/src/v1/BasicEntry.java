package v1;

import java.time.LocalDateTime; 

public class BasicEntry extends Entry {
	
	protected String userEntry;
	
	
	public BasicEntry(String entry, LocalDateTime date)
	{
		super(entry, date);
		
	}

}
