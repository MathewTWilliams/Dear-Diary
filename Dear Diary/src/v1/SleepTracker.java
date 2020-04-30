package v1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
/**
 * A class to track sleep stats, currently only contains ability to track stats. 
 * @author Julian Pino
 * @version 4/29/2020
 *
 */
public class SleepTracker implements Trackable, Serializable{
	
	private static final long serialVersionUID = -4489788484949872918L;
	private HashMap<String, Integer> trackedStats = new LinkedHashMap<String, Integer>();
	private DateFormat dateFormat;
	
	public SleepTracker() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	/**
	 * Method to add to tracked stats collection
	 * @param stat hours of sleep for the day
	 * @return boolean true if successfully input, false otherwise
	 */
	public  boolean trackStat(int stat) {
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		if(!trackedStats.containsKey(currentDate) && stat < 24 && stat > 0) {
			trackedStats.put(currentDate,  stat);
			return true;
		}
		return false;
	}
	/**
	 * Basic getter to return hashmap
	 * @return HashMap tracked stats
	 */
	public  HashMap<String, Integer> getStat() {
		return trackedStats;
	}
	
}
