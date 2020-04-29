package v1;

import java.time.LocalDateTime;
import java.io.*;

/**
 * Class used to store the data of a video entry
 * @author Liam Donovan
 * @version 4.26.2020
 */
public class VideoEntry extends Entry {
	
	private static final long serialVersionUID = 1L;

	private String videoFileName;
	public File videoFile;
	
	/**
	 * Parameterized Constructor
	 * @param videoFileName The name of the video file.
	 * @param entry The text associated with the entry
	 * @param date The date the entry was made.
	 */
	public VideoEntry(String videoFileName, String entry, LocalDateTime date)
	{
		super(entry, date);
		this.videoFileName = videoFileName;
		setFile(videoFileName);
	}
	
	/**
	 * Setter for the name of the video file.
	 * @param videoFileName
	 */
	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}
	
	/**
	 * Getter for the name of the video file
	 * @return videoFileName
	 */
	public String getVideoFileName() {
		return videoFileName;
	}
	
	/**
	 * Method used to set the video file based on a filename
	 * @param videoFileName
	 */
	public void setFile(String videoFileName) {
		videoFile = new File("Files\\Videos\\" + videoFileName);
	}
	
	/**
	 * Getter to access the actual video file
	 * @return videoFile
	 */
	public File getFile() {
		return videoFile;
	}
	
	/**
	 * Override the toString method so the Video can be displayed in the WebView
	 */
	public String toString()
	{
		return 	   " <video width='300' autoplay>" +
				   "<source src ='" + videoFile.toURI() + 
				   "' type='video/mp4' />" +
				   "Your browser does not support the video element.</video>";
	}
}
