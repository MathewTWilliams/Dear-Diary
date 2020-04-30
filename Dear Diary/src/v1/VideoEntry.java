package v1;

import java.time.LocalDateTime;
import java.io.*;

/**
 * Class used to store the data of a video entry
 * @author Liam Donovan
 * @version 4.29.2020
 */
public class VideoEntry extends FileEntry {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Parameterized Constructor
	 * @param videoFileName The name of the video file.
	 * @param entry The text associated with the entry
	 * @param date The date the entry was made.
	 */
	public VideoEntry(String videoFileName, String entry, LocalDateTime date)
	{
		super(videoFileName,entry, date);
	}

	/**
	 * Method used to set the video file based on a filename
	 * @param videoFileName
	 */
	public void setFile(String videoFileName) {
		file = new File("Files\\Videos\\" + videoFileName);
	}
	
	/**
	 * Override the toString method so the Video can be displayed in the WebView
	 */
	public String toString()
	{
		return 	   " <video width='300' autoplay>" +
				   "<source src ='" + file.toURI() + 
				   "' type='video/mp4' />" +
				   "Your browser does not support the video element.</video>";
	}
}
