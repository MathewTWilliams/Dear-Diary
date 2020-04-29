package v1;

import java.time.LocalDateTime;
import java.io.*;

/**
 * Class used to store the data of a photo entry
 * @author Liam Donovan
 * @version 4.26.2020
 */
public class PhotoEntry extends Entry {

	private static final long serialVersionUID = 1L;
	
	private String imageFileName;
	public File imageFile;
	
	/**
	 * Parameterized Constructor
	 * @param imageFileName The name of the file
	 * @param entry The text associated with the entry
	 * @param date The date the entry was made.
	 */
	public PhotoEntry(String imageFileName, String entry, LocalDateTime date)
	{
		super(entry, date);
		this.imageFileName = imageFileName;
		setFile(imageFileName);
	}
	
	//Standard Getters and Setters
	
	/**
	 * @param imageFileName
	 */
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	
	/**
	 * @return imageFileName
	 */
	public String getImageFileName() {
		return imageFileName;
	}

	/**
	 * @param imageFileName
	 */
	public void setFile(String imageFileName) {
		imageFile = new File("Files\\Images\\" + imageFileName);
	}
	
	/**
	 * @return imageFile
	 */
	public File getFile() {
		return imageFile;
	}
	
	/**
	 * Override toString method so The File can be view in WebEngine
	 */
	public String toString()
	{
		return  "<figure><img src='" + imageFile.toURI() + "' />" + "</figure>";
	}
}
