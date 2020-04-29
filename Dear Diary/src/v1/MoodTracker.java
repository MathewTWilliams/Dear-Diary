package v1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 * A class to track mood stats, currently only contains ability to track stats. 
 * @author Julian Pino
 * @version 4/29/2020
 *
 */
public class MoodTracker implements Trackable, Serializable{


	private static final long serialVersionUID = 8087736999957727761L;
	private HashMap<String, Integer> trackedStats = new HashMap<String, Integer>();
	private DateFormat dateFormat;
	
	public MoodTracker() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	/**
	 * Method to add to tracked stats collection
	 * @param stat rating on mood for the day
	 * @return boolean true if successfully input, false otherwise
	 */
	public  boolean trackStat(int stat) {
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		if(!trackedStats.containsKey(currentDate) && stat >= 0 && stat <= 10) {
			trackedStats.put(currentDate, stat);
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
