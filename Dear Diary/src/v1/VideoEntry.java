package v1;

import java.time.LocalDateTime;
import java.io.*;

/**
 * Class used to store the data of a video entry
 * @author Liam Donovan
 * @version 4.26.2020
 */
public class VideoEntry extends Entry {

	private String videoFileName;
	public File videoFile;
	public String videoFilePath;
	
	public VideoEntry(String videoFileName, String entry, LocalDateTime date)
	{
		super(entry, date);
		this.videoFileName = videoFileName;
		setFile(videoFileName);
	}
	
	/**
	 * @param videoFileName
	 */
	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}
	
	/**
	 * @return videoFileName
	 */
	public String getVideoFileName() {
		return videoFileName;
	}
	
	/**
	 * @param videoFilePath
	 */
	public void setVideoFilePath(String videoFilePath) {
		this.videoFilePath = videoFilePath;
	}
	
	/**
	 * @return videoFilePath
	 */
	public String getVideoFilePath() {
		return videoFilePath;
	}
	
	/**
	 * @param videoFileName
	 */
	public void setFile(String videoFileName) {
		videoFile = new File("assets\\" + videoFileName);
	}
	
	/**
	 * @return videoFile
	 */
	public File getFile() {
		return videoFile;
	}
}
