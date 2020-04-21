package v1;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ExerciseTracker implements Trackable, Serializable {


	private static final long serialVersionUID = 2928208117201869150L;
	private HashMap<String, Integer> trackedStats = new HashMap<String, Integer>();
	private DateFormat dateFormat;
	
	public ExerciseTracker() {
		dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	}

	public  boolean trackStat(int stat) {
		Date date = new Date();
		String currentDate = dateFormat.format(date);
		if(!trackedStats.containsKey(currentDate) && stat >= 0 && stat <= 24) {
			trackedStats.put(currentDate, stat);
			return true;
		}
		return false;
	}
	
	public  HashMap<String, Integer> getStat() {
		return trackedStats;
	}
}
