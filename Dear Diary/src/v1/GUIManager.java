package v1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * 
 * This class handles the GUI of the entire application. This class holds a reference to each
 * Screen and/or Menu that can be visited in the application. Each screen or menu holds a reference
 * to the same instance of this class so the screens can switch between each other without knowing about each other. 
 * 
 * @author Matt Williams
 * @version 3.24.2020
 *
 */
public class GUIManager extends Application {

	private Stage mainStage; 
	private Scene currentScene;
	private Label title; 
	
	
	private HomeScreenGUI homeScreen; 
	private LoginGUI loginScreen;
	private MainMenuScreen mainMenu;
	private DiaryEntryScreen entryScreen;
	private TrackStatGUI trackStatScreen; 
	private ViewEntriesScreen viewEntriesScreen;
	
	private User userData;
	
	/**
	 * Method used to actually launch the GUI, called by the main method in Driver.
	 * @param args any arguments passed to the programmed.n
	 */
	public void launchGUI(String[] args)
	{
		Application.launch(this.getClass(),args);
	}
	
	/**
	 * Overriden method used to help setup the stage and to initialize the GUI. 
	 * @throws Exception 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		userData = new User(); 
		mainStage = primaryStage;
		makeTitle();
		makeDefaultScenes(); 
		setStage();
	}
	

	/**
	 * Method used to make the size of the stage and to set and show the first screen to the user. 
	 */
	private void setStage()
	{
		mainStage.setWidth(800);
		mainStage.setHeight(800);
		mainStage.setTitle("Dear Diary");
		
		mainStage.setScene(currentScene);
		mainStage.show();
		
	}

	/** 
	 * Method used to make the difference screens in the application.
	 * Also sets the default currentScene before the app starts. 
	 * @throws Exception 
	 */
	private void makeDefaultScenes() throws Exception
	{
		
		homeScreen = new HomeScreenGUI(this);
		loginScreen = new LoginGUI(this);
		mainMenu = new MainMenuScreen(this);
		entryScreen = new DiaryEntryScreen(this); 
		trackStatScreen = new TrackStatGUI(this);
		viewEntriesScreen = new ViewEntriesScreen(this);
		currentScene = homeScreen.getScene();

	}
	
	/**
	 * Method used by other screens to move to the Login Screen
	 */
	public void moveToLoginScene()
	{
		currentScene = loginScreen.getScene();
		mainStage.setScene(currentScene);
	}
	
	/**
	 * Method used by other screens to move to the Main Menu
	 */
	public void moveToMainMenu()
	{
		currentScene = mainMenu.getScene();
		mainStage.setScene(currentScene);
	}
	
	/**
	 * Method used by other screens to move to the Diary Entry page
	 */
	public void moveToDiaryEntryScreen()
	{
		currentScene = entryScreen.getScene();
		mainStage.setScene(currentScene);
	}
	
	/**
	 * Method used by other screens to move to the TrackStats Screen. 
	 */
	public TrackStatGUI moveToTrackStatScreen()
	{
		currentScene = trackStatScreen.getScene(); 
		mainStage.setScene(currentScene);
		return trackStatScreen;
	}
	
	
	public void moveToViewEntriesScreen()
	{
		currentScene = viewEntriesScreen.getScene();
		mainStage.setScene(currentScene);
	}
	
	/**
	 * Method used to make the default title for the Application
	 */
	private void makeTitle()
	{
		title = new Label("DEAR DIARY");
		title.setTextFill(Color.BLACK);
		title.setFont(new Font("Arial",60));
		BorderPane.setAlignment(title, Pos.CENTER);
		
	}
	
	/**
	 * Accessor for other screens to access the default title of the application. 
	 * @return
	 */
	public Label getTitle()
	{
		return title;
	}
	
	/**
	 * Accessor for other screens to access the user's data
	 * @return
	 */
	public User getUserData()
	{
		return userData; 
	}
}
