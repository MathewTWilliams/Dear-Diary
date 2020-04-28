package v1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class handles the basic Diary Entry Screen
 * @author Matt Williams
 * @version 3.24.2020
 *
 */
public class DiaryEntryScreen extends SceneHandler {


	private BorderPane borderPane;
	private BorderPane centerSubPane;
	private TextArea entryArea;
	private Label descriptionLabel;
	private Button submitButton;
	private Button backButton;
	
	/**Logger for GUI, will help with any logging issues within the application*/
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Basic overloaded constructor
	 * @param manager The UIManager to make callbacks to.
	 */
	public DiaryEntryScreen(GUIManager manager)
	{
		super(manager);
		
	}
	
	/**
	 * Method that handles setting up the text area for the Diary Entry
	 */
	private void setUpTextArea()
	{
		entryArea = new TextArea("Enter Diary Entry here!");
		entryArea.setMaxHeight(300);
		entryArea.setMaxWidth(600);
		centerSubPane.setCenter(entryArea);
		entryArea.setWrapText(true);
		BorderPane.setAlignment(entryArea, Pos.CENTER);
	}
	
	/**
	 * Method that handles setting up the labels for the Diary Entry Screen. 
	 */
	private void setUpLabels()
	{
		descriptionLabel = new Label();
		descriptionLabel.setTextFill(Color.BLACK);
		descriptionLabel.setFont(new Font("Arial", 30));
		BorderPane.setAlignment(descriptionLabel, Pos.CENTER);
		centerSubPane.setTop(descriptionLabel);
		descriptionLabel.setText("Talk to me " + getGUIManager().getUserData().getName() + ". What are you feeling?");
		

		borderPane.setTop(getTitle());
	}
	
	/**
	 * Method that handles setting up the Buttons for the Diary Entry Screen. 
	 */
	private void setUpButtons()
	{
		submitButton = new Button("Log Entry");
		submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
		        LOGGER.log(Level.INFO, "New Text Entry Logged at " + LocalDateTime.now());
				cleanUpScene();
				getGUIManager().moveToMainMenu();
			}
			
			
		});
		
		BorderPane.setAlignment(submitButton, Pos.BOTTOM_CENTER);
		centerSubPane.setRight(submitButton);
	
		
		
		backButton = new Button("Main Menu");
		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				getGUIManager().moveToMainMenu();
			}
			
			
		});
		
		BorderPane.setAlignment(backButton, Pos.BOTTOM_CENTER);
		centerSubPane.setLeft(backButton);
		
	}

	
	/**
	 * Overriden Method used to "clean up" the scene for its next use and store any information before moving
	 * to a different scene. 
	 */
	@Override
	protected void cleanUpScene() 
	{
		getGUIManager().getUserData().logEntry(entryArea.getText());
		//entryArea.setText("Enter Diary Entry here!");
		super.cleanUpScene();
	}

	/**
	 * Overriden Method use to prepare the scene before it is switched to. 
	 */
	@Override
	protected void prepareScene() 
	{
		
		borderPane = new BorderPane();
		centerSubPane = new BorderPane();
		centerSubPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(centerSubPane, Pos.CENTER);
		borderPane.setCenter(centerSubPane);
		borderPane.setStyle("-fx-background-color : GREY");
		Scene scene = new Scene(borderPane); 
		setScene(scene);
	
		setUpTextArea();
		setUpLabels();
		setUpButtons();
		
		
	}
	
}
