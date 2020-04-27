package v1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SleepTracker implements Trackable, Serializable{
	
	private static final long serialVersionUID = -4489788484949872918L;
	private HashMap<String, Integer> trackedStats = new HashMap<String, Integer>();
	private DateFormat dateFormat;
	
	public SleepTracker() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public  boolean trackStat(int stat) {
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		if(!trackedStats.containsKey(currentDate) && stat < 24 && stat > 0) {
			trackedStats.put(currentDate,  stat);
			return true;
		}
		return false;
	}
	
	public  HashMap<String, Integer> getStat() {
		return trackedStats;
	}
	
}
