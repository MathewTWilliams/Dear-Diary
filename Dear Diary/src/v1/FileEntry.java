package v1;

import java.io.File;
import java.time.LocalDateTime;

/**
 * An abstract class to represent file based entries into the Diary
 * @author Matt Williams
 * @version 04.29.2020
 *
 */
public abstract class FileEntry extends Entry 
{

	private static final long serialVersionUID = 1L;
	protected String fileName;
	protected File file; 
	
	/**
	 * Parameterized Constructor
	 * @param fileName The name of the file
	 * @param entry The text associated with the file entry
	 * @param date The date the entry was made. 
	 */
	public FileEntry(String fileName, String entry, LocalDateTime date)
	{
		super(entry,date);
		this.fileName = fileName; 
		setFile(fileName);
		
	}
	
	/**
	 * Setter for the fileName
	 * @param fileName The name of the file.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Accessor for the fileName
	 * @return The name of the file
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Method used to set the and make the File Object
	 * @param fileName, the name of the file
	 */
	public abstract void setFile(String fileName);

	public File getFile() {
		return file;
	}


}
