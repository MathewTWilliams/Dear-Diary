package v1;

import java.io.Serializable;
import java.time.LocalDateTime; 

//test comment for github
//gay-reate

public class BasicEntry extends Entry implements Serializable {
	
	protected String userEntry;
	
	private static final long serialVersionUID = 1L;
	
	public BasicEntry()
	{
		super();
	}
	
	public BasicEntry(String entry, LocalDateTime date)
	{
		super(entry, date);
		
	}

}
