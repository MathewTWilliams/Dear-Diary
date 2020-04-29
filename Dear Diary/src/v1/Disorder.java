package v1;
import java.util.ArrayList; 

/**
 * Class Disorder defines the characteristics of each disorder
 * @author HarmanRansi
 * @version 3/7/2020
 */
public class Disorder {
	private String name;
	private String description;
	private ArrayList<String> symptoms;
	private ArrayList<String> tips;
	
	/**
	 * Constructor defines name and description of disorder
	 * @param name Name of the disorder
	 * @param description Description of the disorder
	 * @param symptoms Symptoms of disorder
	 */
	public Disorder(String name, String description, ArrayList<String> symptoms, ArrayList<String> tips) {
		this.name = name;
		this.description = description;
		this.symptoms = symptoms;
		this.tips = tips;
	}
	
	//Standard Getters and Setters//
	
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public ArrayList<String> getSymptoms(){
		return symptoms;
	}
	
	public ArrayList<String> getTips(){
		return tips;
	}
	
	////////////////////////////////////////////////////////
}