package v1;

import java.time.LocalDateTime;
import java.io.*;

/**
 * Class used to store the data of a photo entry
 * @author Liam Donovan
 * @version 4.29.2020
 */
public class PhotoEntry extends FileEntry {

	private static final long serialVersionUID = 1L;

	
	/**
	 * Parameterized Constructor
	 * @param imageFileName The name of the file
	 * @param entry The text associated with the entry
	 * @param date The date the entry was made.
	 */
	public PhotoEntry(String imageFileName, String entry, LocalDateTime date)
	{
		super(imageFileName,entry, date);

	}
	
	/**
	 * Setter to set and make the file
	 * @param imageFileName
	 */
	public void setFile(String imageFileName) {
		file = new File("Files\\Images\\" + imageFileName);
	}
	

	/**
	 * Override toString method so The File can be view in WebEngine
	 */
	public String toString()
	{
		return  "<figure><img src='" + file.toURI() + "' />" + "</figure>";
	}
}
