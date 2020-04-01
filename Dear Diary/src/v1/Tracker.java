package v1;

import java.util.Date; 

public interface Trackable 
{	
	public abstract void TrackStat(int stat);
	public abstract void EditStat(Date date, int newStat);
	public abstract void DeleteStat(Date date);
	
	
}
