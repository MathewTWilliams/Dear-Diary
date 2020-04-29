package v1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.io.File;



import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class handles the basic Diary Entry Screen
 * @author Matt Williams
 * @version 4.27.2020
 *
 */
public class DiaryEntryScreen extends SceneHandler {

	
	private static final String TEXT_ENTRY = "Text Entry";
	private static final String PHOTO_ENTRY = "Photo Entry";
	private static final String VIDEO_ENTRY = "Video Entry";


	private TextArea entryArea;
	private Label descriptionLabel;
	private Label fileLabel; 
	private Label entryLabel;
	private Button submitButton;
	private Button backButton;
	
	/**Logger for GUI, will help with any logging issues within the application*/
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private ChoiceBox<String> entryChoiceBox;

	private VBox root; 
	private HBox subPane; 
	
	private VBox leftVBox;
	private VBox rightVBox; 
	private HBox buttonBox; 
	
	private ListView<String> fileView;

	
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
		entryArea.setMaxHeight(200);
		entryArea.setMaxWidth(450);
		entryArea.setWrapText(true);
		rightVBox.getChildren().add(entryArea);
	}
	
	/**
	 * Method that handles setting up the labels for the Diary Entry Screen. 
	 */
	private void setUpLabels()
	{
		descriptionLabel = new Label();
		descriptionLabel.setTextFill(Color.BLACK);
		descriptionLabel.setFont(new Font("Arial", 30));
		descriptionLabel.setText("Talk to me " + getGUIManager().getUserData().getName() + ". What are you feeling?");
		
		
		root.getChildren().addAll(getTitle(), descriptionLabel);
		
		fileLabel = new Label();
		fileLabel.setTextFill(Color.BLACK);
		fileLabel.setFont(new Font("Arial", 15));
		fileLabel.setText("Files Listed Below.");
		leftVBox.getChildren().add(0, fileLabel);
		
		entryLabel = new Label();
		entryLabel.setTextFill(Color.BLACK);
		entryLabel.setFont(new Font("Arial", 15));
		entryLabel.setText("Enter Entry Below");
		rightVBox.getChildren().add(0,entryLabel);


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

				    LogEntry();

				    cleanUpScene();
				    getGUIManager().moveToMainMenu();
			}
			
			
		});
		
		
		backButton = new Button("Main Menu");
		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				cleanUpScene();
				getGUIManager().moveToMainMenu();
			}
			
			
		});
		
		buttonBox.getChildren().addAll(backButton,submitButton);
		
	}

	/**
	 * Overriden Method use to prepare the scene before it is switched to. 
	 */
	@Override
	protected void prepareScene() 
	{
		
		root = new VBox();
		buttonBox = new HBox(); 
		subPane = new HBox(); 
		leftVBox = new VBox();
		rightVBox = new VBox(); 

		root.setPadding(new Insets(10,10,10,10));
		buttonBox.setPadding(new Insets(10,10,10,10));
		subPane.setPadding(new Insets(10,10,10,10));
		leftVBox.setPadding(new Insets(10,10,10,10));
		rightVBox.setPadding(new Insets(10,10,10,10));

		root.setAlignment(Pos.TOP_CENTER);
		leftVBox.setAlignment(Pos.TOP_CENTER);
		rightVBox.setAlignment(Pos.TOP_CENTER);
		
		buttonBox.setAlignment(Pos.CENTER);
		subPane.setAlignment(Pos.CENTER);

		root.setStyle("-fx-background-color : LIGHTSKYBLUE");
		buttonBox.setStyle("-fx-background-color :  LIGHTSKYBLUE");
		subPane.setStyle("-fx-background-color :  LIGHTSKYBLUE");
		leftVBox.setStyle("-fx-background-color :  LIGHTSKYBLUE");
		rightVBox.setStyle("-fx-background-color : LIGHTSKYBLUE");

		root.setSpacing(20d);
		buttonBox.setSpacing(100d);
		subPane.setSpacing(20d);
		leftVBox.setSpacing(20d);
		rightVBox.setSpacing(20d);
		
		subPane.getChildren().addAll(leftVBox,rightVBox);

		Scene scene = new Scene(root); 
		setScene(scene);
	
		setUpLabels();
		makeFileView(); 
		makeChoiceBox();
		
		root.getChildren().add(subPane);
		
		setUpButtons();
		setUpTextArea();
		
		root.getChildren().add(buttonBox);
	}
	
	/**
	 * Method used to make the entry choice box screen and the map associated with it
	 */
	private void makeChoiceBox()
	{
		entryChoiceBox = new ChoiceBox<String>();
		entryChoiceBox.getItems().addAll(TEXT_ENTRY,PHOTO_ENTRY,VIDEO_ENTRY);
		root.getChildren().add(entryChoiceBox);
		changeEntryType(TEXT_ENTRY); 
		
		entryChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
			{
				changeEntryType(newValue);
			}
		});
		
	}
	
	/**
	 * Method called to actually change the scene based on which entry the user wants
	 * @param s The enry the user wants
	 */
	private void changeEntryType(String s)
	{
		entryChoiceBox.setValue(s);
		switch(s) {
			
			case TEXT_ENTRY:
				fileView.setDisable(true);
				fileView.getItems().clear();
				break; 
				
			case PHOTO_ENTRY:
				fileView.setDisable(false);
				getListOfFiles("Files\\Images");
				break;
			
			case VIDEO_ENTRY:
				fileView.setDisable(false);
				getListOfFiles("Files\\Videos");
				break; 
		}
	}
	
	/**
	 * Method used to make the File View for the scene
	 */
	private void makeFileView()
	{
		fileView = new ListView<String>();
		fileView.setOrientation(Orientation.VERTICAL);
		fileView.setMaxSize(200,200);
		fileView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		leftVBox.getChildren().add(fileView);
	}
	
	/**
	 * When Ever Photo or Video entry is selected. This method is called to load
	 * the list of files with in their respective Folder. 
	 * @param s The name of the folder to access files
	 */
	private void getListOfFiles(String s)
	{
		File folder = new File(s);
		File[] files = folder.listFiles();
		fileView.getItems().clear();
		for(File file : files)
		{
			fileView.getItems().add(file.getName());
		}
		
		if(files.length > 0)
		{
			fileView.getSelectionModel().clearAndSelect(0);
			fileView.getFocusModel().focus(0);
		}
	}
	
	/**
	 * Method used to actually add the entry to the diary
	 */
	private void LogEntry()
	{
		switch(entryChoiceBox.getSelectionModel().getSelectedItem())
		{
			case TEXT_ENTRY:
				getGUIManager().getUserData().logBasicEntry(entryArea.getText());
				break;
			case PHOTO_ENTRY:
				getGUIManager().getUserData().logFileEntry(fileView.getSelectionModel().getSelectedItem(), entryArea.getText(), true);
				break;
			case VIDEO_ENTRY:
				getGUIManager().getUserData().logFileEntry(fileView.getSelectionModel().getSelectedItem(), entryArea.getText(), false);
				break; 
		}
	}
}
