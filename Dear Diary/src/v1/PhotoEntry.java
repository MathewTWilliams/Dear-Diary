package v1;

import java.time.LocalDateTime;
import java.io.*;

/**
 * Class used to store the data of a photo entry
 * @author Liam Donovan
 * @version 4.26.2020
 */
public class PhotoEntry extends Entry {

	private String imageFileName;
	public File imageFile;
	public String imageFilePath;
	
	public PhotoEntry(String imageFileName, String entry, LocalDateTime date)
	{
		super(entry, date);
		this.imageFileName = imageFileName;
		setFile(imageFileName);
	}
	
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
	 * @param imageFilePath
	 */
	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}
	
	/**
	 * @return imageFilePath
	 */
	public String getImageFilePath() {
		return imageFilePath;
	}
	
	/**
	 * @param imageFileName
	 */
	public void setFile(String imageFileName) {
		imageFile = new File("assets\\" + imageFileName);
	}
	
	/**
	 * @return imageFile
	 */
	public File getFile() {
		return imageFile;
	}
}
