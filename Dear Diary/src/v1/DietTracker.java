package v1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DietTracker implements Trackable, Serializable{
	
	private static final long serialVersionUID = -4597924703830341777L;
	private HashMap<String, Integer> trackedStats = new HashMap<String, Integer>();
	private DateFormat dateFormat;
	
	public DietTracker() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public  boolean trackStat(int stat) {
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		if(!trackedStats.containsKey(currentDate) && stat < 10000 && stat > 0) {
			trackedStats.put(currentDate, stat);
			return true;
		}
		return false;
	}
	
	public  HashMap<String, Integer> getStat() {
		return trackedStats;
	}
	
}
