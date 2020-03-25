package v1;

import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 * Abstract class for handling a scene in the GUI
 * 
 * @author Matt Williams
 * @version 3.23.2020
 */
public abstract class SceneHandler 
{
	private Scene scene = null; 
	private GUIManager manager; 
	private Label title; 
	
	/**
	 * Basic Constructor
	 * @param manager An instance of the GUIManager that handles the scenes
	 */
	 public SceneHandler(GUIManager manager)
	{
		this.manager = manager; 
		title = manager.getTitle();
	}
	
	
	/**
	 * Mutator for the Scene
	 * @param scene The new scene for this handler. 
	 */
	protected void setScene(Scene scene)
	{
		this.scene = scene; 
	}
	
	/**
	 * Accessor for the GUIManager
	 * @return
	 */
	protected GUIManager getGUIManager()
	{
		return manager; 
	}
	
	/**
	 * Accessor for the scene
	 * @return The scene the handler currently has. 
	 */
	public Scene getScene()
	{
		
		if(scene == null)
		{
			prepareScene();
		}
		return scene;
	}
	
	/**
	 * Accessor for handlers to access their title
	 * @return The title of the app
	 */
	protected Label getTitle()
	{
		return title; 
	}
	
	/**
	 * method to clean up this Handler's Scene before switching to a new scene.
	 * 
	 */
	protected  void cleanUpScene()
	{
		scene = null;
	}
	
	/**
	 * Abstract method to prepare this Handler's scene to be switched to. 
	 */
	protected abstract void prepareScene();
	
}
