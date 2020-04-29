package v1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/**
 * A class to track diet stats, currently only contains ability to track stats. 
 * @author Julian Pino
 * @version 4/29/2020
 *
 */
public class DietTracker implements Trackable, Serializable{
	
	private static final long serialVersionUID = -4597924703830341777L;
	private HashMap<String, Integer> trackedStats = new HashMap<String, Integer>();
	private DateFormat dateFormat;
	
	public DietTracker() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}
	/**
	 * Method to add to tracked stats collection
	 * @param stat number of calories eaten
	 * @return boolean true if successfully input, false otherwise
	 */
	public  boolean trackStat(int stat) {
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		if(!trackedStats.containsKey(currentDate) && stat < 10000 && stat > 0) {
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
