package v1;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tracker {
	private HashMap<String, Integer> trackedStats = new HashMap<String, Integer>();
	private DateFormat dateFormat;
	public Tracker() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	
	public boolean inputStat(int Stat) {
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		if(!trackedStats.containsKey(currentDate)) {
			trackedStats.put(currentDate, Stat);
			return true;
		}
		
		return false;
	}
	
	public HashMap<String, Integer> getStats() {
		return trackedStats;
	}
	
}