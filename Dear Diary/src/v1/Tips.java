package v1;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Tips class stores all the tips associated with each disorder. Also provides
 * functionality to add tips, return a random tip, and return all tips
 * @author HarmanRansi
 * @version 3/7/2020
 */
public class Tips {
	//HashMap which keys are all disorders (String) and values are all tips for the particular disorder (ArrayList)
	private static HashMap<String, ArrayList<String>> allTips;
	
	/**
	 * Constructor defines the HashMap allTips
	 */
	public Tips() {
		allTips = new HashMap<>();
	}
	
	/**
	 * Method returns all disorders with all their tips
	 * @return map of all the disorders and tips
	 */
	public HashMap<String, ArrayList<String>> getTips(){
		return allTips;
	}
	
	/**
	 * Method allows for a Tip to be added to particular disorder. However, the disorder must exist beforehand. 
	 * @param disorder The disorder the particular tip pertains to
	 * @param tips The tip(s) the user is adding to the ArrayList
	 */
	public void addTip(String disorder, ArrayList<String> tips) {
		if (check(disorder) == 1) { 
			for (String newTip: tips) {
				allTips.get(disorder).add(newTip);
			}
		}
	}
	
	/**
	 * Extra Method added to make code look cleaner for addTip(String disorder, ArrayList<String> tips)
	 */
	public int check(String disorder) {
		int count = 0;
		for (String ord: allTips.keySet()) {
			if (ord.equals(disorder)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Method returns tips of particular disorder
	 * @param disorder The disorder selected
	 * @return tips for the particular disorder
	 */
	public static ArrayList<String> getTipsOfDisord(String disorder) {
		return allTips.get(disorder);
	}
}
