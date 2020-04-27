package v1;

import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
//This is a test
/**
 * Class used to handle the scene for our ViewEntriesScreen
 * @author Matt Williams
 * @version 3.25.2020
 */

public class ViewEntriesScreen extends SceneHandler
{
	
	private BorderPane mainPane; 
	private ListView<String> dateList; 
	private TextArea entryView;
	private Label datesLabel;
	private Label entriesLabel;
	private Button mainMenuButton;
	
	private VBox leftVBox;
	private VBox rightVBox;
	
	
	
	/**
	 * Basic overriden construction
	 * @param manager The GUI manager to make callbacks to
	 */
	public ViewEntriesScreen(GUIManager manager) 
	{
		super(manager);
	}
	
	/**
	 * Overriden method used to clean up the scene before a different scene is switched to.
	 */
	@Override
	protected void cleanUpScene() {
		super.cleanUpScene();
		

	}

	/**
	 * Overriden method used to prepare the scene before the GUI is changed to this scene.s
	 */
	@Override
	protected void prepareScene() 
	{
		mainPane = new BorderPane();
		mainPane.setPadding(new Insets(10,10,10,10));
		mainPane.setStyle("-fx-background-color : GREY");
		Scene scene = new Scene(mainPane);
		setScene(scene);
		
		makeVBoxes();
		setUpLabels();
		makeTextArea(); 
		setUpButtons();
		makeListView();
	}
	
	/**
	 * Method used to Make the Vertical Boxes for the View Entries Screen.
	 */
	private void makeVBoxes()
	{
		leftVBox = new VBox();
		rightVBox = new VBox();
		
		leftVBox.setPadding(new Insets(10,10,10,10));
		rightVBox.setPadding(new Insets(10,10,10,10));
		
		leftVBox.setAlignment(Pos.CENTER);
		rightVBox.setAlignment(Pos.CENTER);
		
		leftVBox.setSpacing(20);
		rightVBox.setSpacing(20);
		
		BorderPane.setAlignment(leftVBox, Pos.CENTER);
		BorderPane.setAlignment(rightVBox, Pos.CENTER);
		
		mainPane.setLeft(leftVBox);
		mainPane.setRight(rightVBox);
	}
	
	/**
	 * Method use to make our non-editable textArea for our entries to be viewed in.
	 */
	private void makeTextArea()
	{
		entryView = new TextArea(); 
		entryView.setEditable(false);
		rightVBox.getChildren().add(entryView);
		
		entryView.setMaxSize(400, 400);
		
	}
	
	/**
	 * Method used to setUp the labels for the view entries screen.
	 */
	private void setUpLabels()
	{
		mainPane.setTop(getTitle());
		
		datesLabel = new Label("Date and Time of Entries");
		datesLabel.setFont( new Font("Arial",15));
		datesLabel.setTextFill(Color.BLACK);
		leftVBox.getChildren().add(datesLabel);
		
		entriesLabel = new Label("Entries");
		entriesLabel.setFont(new Font("Arial",15));
		entriesLabel.setTextFill(Color.BLACK);
		rightVBox.getChildren().add(entriesLabel);
		
	}
	
	/**
	 * Method used to setup the buttons for the view entries screen
	 */
	private void setUpButtons() 
	{
		mainMenuButton = new Button();
		mainMenuButton.setText("Main Menu");
		BorderPane.setAlignment(mainMenuButton, Pos.CENTER);
		mainPane.setBottom(mainMenuButton);
		
		mainMenuButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				cleanUpScene(); 
				getGUIManager().moveToMainMenu();
			}
		});
	}
	
	/**
	 * Method to make our list view of the dates for each entry
	 */
	private void makeListView()
	{
		dateList = new ListView<String>();
		dateList.setOrientation(Orientation.VERTICAL);
		dateList.setMaxSize(200, 200);
		leftVBox.getChildren().add(dateList);

		ArrayList<Entry> entries = getGUIManager().getUserData().getUserEntries();
	
		
		for(Entry en: entries)
		{
			dateList.getItems().add(en.getDate().toString());
		}
		
		//set textfield to the first entry
		changeEntrySelected(entries.get(0).getDate().toString());
		
		//listen for when the selected value is changed
		dateList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
			{
				changeEntrySelected(newValue);
			}
		});
	}
		
	
	/**
	 * Method used to change the entry viewed in the textArea
	 * @param date The date of the entry that was selected.
	 */
	private void changeEntrySelected(String date)
	{
		ArrayList<Entry> entries = getGUIManager().getUserData().getUserEntries();
		
		for(int i = 0; i < entries.size();i++)
		{
			if(entries.get(i).getDate().toString().equals(date))
			{
				entryView.setText(entries.get(i).getText());
				return;
			}
		}
	}
}
