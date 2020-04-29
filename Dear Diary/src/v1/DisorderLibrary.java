package v1;
import java.io.BufferedReader; 
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Disorder Library defines all the disorders of the library
 * @author HarmanRansi
 * @version 4/19/2020
 */
public class DisorderLibrary {

	protected ArrayList<Disorder> disorders;
	
	/**
	 * Constructor instantiates the ArrayList
	 */
	public DisorderLibrary() {
		disorders = new ArrayList<>();
		try {
			collectDisorders();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets disorder
	 * @param disorder Selected disorder
	 * @return The disorder and its details
	 */
	public Disorder getDisorder(Disorder disorder) {
		for (Disorder x: disorders) {
			if (x.toString().equals(disorder.toString())) {
				return x;
			} else {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Method returns all the disorders (in a list)
	 * @return List of disorders
	 */
	public ArrayList<Disorder> getDisorders(){
		return disorders;
	}
	
	/**
	 * Method adds Disorder to Disorder Library
	 * @param x The disorder being added
	 */
	private void addDisorder(Disorder x) {
		disorders.add(x);
	}
	
	/**
	 * Method looks through DisorderList.txt file to determine and add disorders to the program
	 * @throws IOException If file cannot be found.
	 */
	private void collectDisorders() throws IOException {

		//Text File being used to store disorders in
		String filename = "Assets\\DisorderList.txt";

		//Initialization of file w/ appropriate reader and buffer
		File f = new File(filename);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);

		//Some Extra Shit
		String line =  null;
		String disorder = null;
		String disorderName = null;
		String description = null;
		ArrayList<String> symptoms = null;
		ArrayList<String> tips = null;

		
		while ((line = br.readLine()) != null) {

			if (line.contains("Disorder: ")) {
				int startIndex = line.indexOf(":");
				int endIndex = line.length();
				disorder = format(line.substring(startIndex+1, endIndex));
				disorderName = disorder;
				System.out.println(disorder);
			} else if (line.contains("Description: ")) {
				int startIndex = line.indexOf(":");
				int endIndex = line.length();
				description = line.substring(startIndex+1, endIndex).trim(); 
				System.out.println(description);
			} else if (line.contains("Symptoms: ")){
				int startIndex = line.indexOf(":");
				int endIndex = line.length();
				String symptomString = line.substring(startIndex+1, endIndex);
				String[] elements = symptomString.split(" -- ");
				List<String> fixedLengthList = Arrays.asList(elements);
				ArrayList<String> listOfString = new ArrayList<String>(fixedLengthList);
				ArrayList<String> trimmedStrings = (ArrayList<String>) listOfString.stream().map(String::trim).collect(Collectors.toList());
				symptoms = trimmedStrings;
			} else if (line.contains("Tips: ")) {
				int startIndex = line.indexOf(":");
				int endIndex = line.length();
				String tipString = line.substring(startIndex+1, endIndex);
				String[] elements2 = tipString.split(" -- ");
				List<String> fixedLengthList2 = Arrays.asList(elements2);
				ArrayList<String> listOfString2 = new ArrayList<String>(fixedLengthList2);
				ArrayList<String> trimmedStrings2 = (ArrayList<String>) listOfString2.stream().map(String::trim).collect(Collectors.toList());
				tips = trimmedStrings2;
				Disorder x = new Disorder(disorderName, description, symptoms, tips);
				disorders.add(x);
			}
		}
	}
	
	/**
	 * Helper method for collectDisorders() to remove spaces from a string.
	 * @param inputString Unformatted String
	 * @return Formatted string without extra spaces
	 */
	private static String format(String inputString) {
		return inputString.trim();
	}
	
	/** CONTINUE OFF HERE
	 * Method returns a random tip depending on the disorder
	 * @param disorder Disorder of the tips being searched
	 * @return One random tip for the particular disorder
	 */
	/**
	
	protected String newRandomTip() {
		Random rand = new Random();
		int index = rand.nextInt(Tips.getTipsOfDisord(disorder).size());
		return Tips.getTipsOfDisord(disorder).get(index);
	}
	*/
}
