package v1;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class Disorder defines the characteristics of each disorder
 * @author HarmanRansi
 * @version 3/7/2020
 */
public class Disorder {
	private String name;
	private String description;
	private ArrayList<String> symptoms;
	
	/**
	 * Constructor defines name and description of disorder
	 * @param name Name of the disorder
	 * @param description Description of the disorder
	 */
	public Disorder(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	//////////////////////////////////////////////////////////////
	//Standard Getters and Setters
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setSymptoms(ArrayList<String> symptoms) {
		this.symptoms = symptoms;
	}

	////////////////////////////////////////////////////////
	
	/**
	 * !!!!!!!!!!WARNING: SWITCHED FROM ArrayList<Tips> to ArrayList<String> return!!!!!!!!!
	 * Method returns an ArrayList of Tips dependent on disorder user inputs
	 * @param disorder The disorder searched for
	 * @return Tips of a particular disorder
	 */
	protected ArrayList<String> getTips(String disorder){
		return Tips.getTipsOfDisord(disorder);
	}
	
	/**
	 * Method returns the symptoms of the particular disorder
	 * @return List of all the symptoms of the disorder
	 */
	protected ArrayList<String> returnSymps() {
		return symptoms;
	}
	
	/**
	 * Method returns a random tip depending on the disorder
	 * @param disorder Disorder of the tips being searched
	 * @return One random tip for the particular disorder
	 */
	protected String newRandomTip(String disorder) {
		Random rand = new Random();
		int index = rand.nextInt(Tips.getTipsOfDisord(disorder).size());
		return Tips.getTipsOfDisord(disorder).get(index);
	}
}
