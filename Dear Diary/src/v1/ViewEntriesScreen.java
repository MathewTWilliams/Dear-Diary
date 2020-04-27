package v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
//This is a test
/**
 * Class used to handle the scene for our ViewEntriesScreen
 * @author Matt Williams
 * @version 3.25.2020
 *
 */

public class ViewEntriesScreen extends SceneHandler
{
	
	private VBox mainPane; // root pane
	private HBox subPane;  // child of root
	private ListView<String> dateList; 
	private TextArea entryView;
	private Label datesLabel;
	private Label entriesLabel;
	private Button mainMenuButton;
	
	private VBox leftVBox; //child of subPane
	private VBox rightVBox; //child of subPane
	
	
	private HashMap<String, Consumer<Entry>> lambdaMap;
	
	private WebView entryFeed = new WebView();
	private WebEngine entryEngine = entryFeed.getEngine(); 
	
	
	/**
	 * Basic overriden construction
	 * @param manager The GUI manager to make callbacks to
	 */
	public ViewEntriesScreen(GUIManager manager) 
	{
		super(manager);
		makeLambdaMap(); 
	
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
		mainPane = new VBox();
		mainPane.setAlignment(Pos.TOP_CENTER);
		mainPane.setPadding(new Insets(10,10,10,10));
		mainPane.setStyle("-fx-background-color : GREY");
		Scene scene = new Scene(mainPane);
		setScene(scene);
		
		makeBoxes();
		setUpLabels();
		makeListView();
		makeTextArea(); 
		setUpButtons();
	}
	
	/**
	 * Method used to Make the Vertical Boxes for the View Entries Screen.
	 */
	private void makeBoxes()
	{
		subPane = new HBox(); 
		leftVBox = new VBox();
		rightVBox = new VBox();
		
		
		subPane.setPadding(new Insets(10,10,10,10));
		leftVBox.setPadding(new Insets(10,10,10,10));
		rightVBox.setPadding(new Insets(10,10,10,10));
		
		subPane.setAlignment(Pos.TOP_CENTER);
		leftVBox.setAlignment(Pos.TOP_CENTER);
		rightVBox.setAlignment(Pos.TOP_CENTER);
		
		subPane.setSpacing(20);
		leftVBox.setSpacing(20);
		rightVBox.setSpacing(20);
		

		mainPane.getChildren().add(subPane);
		subPane.getChildren().addAll(leftVBox,rightVBox);
	}
	
	/**
	 * Method use to make our non-editable textArea for our entries to be viewed in.
	 */
	private void makeTextArea()
	{
		entryView = new TextArea(); 
		entryView.setEditable(false);
		//rightVBox.getChildren().add(entryView);
		
		entryView.setMaxSize(400, 400);
		
	}
	
	/**
	 * Method used to setUp the labels for the view entries screen.
	 */
	private void setUpLabels()
	{
		mainPane.getChildren().add(0, getTitle());
		
		datesLabel = new Label("Date and Time of Entries");
		datesLabel.setFont( new Font("Arial",15));
		datesLabel.setTextFill(Color.BLACK);
		leftVBox.getChildren().add(datesLabel);
		
		entriesLabel = new Label("Entries");
		entriesLabel.setFont(new Font("Arial",15));
		entriesLabel.setTextFill(Color.BLACK);
		leftVBox.getChildren().add(entriesLabel);
		
	}
	
	/**
	 * Method used to setup the buttons for the view entries screen
	 */
	private void setUpButtons() 
	{
		mainMenuButton = new Button();
		mainMenuButton.setText("Main Menu");
		leftVBox.getChildren().add(mainMenuButton);
		
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
				Entry entry = entries.get(i);
				if(lambdaMap.containsKey(entry.getClass().getSimpleName()))
				{
					lambdaMap.get(entry.getClass().getSimpleName()).accept(entry);
				}
				
			}
		}
	}
	
	/**
	 * Method used to instantiate out HashMap of Lambdas
	 */
	private void makeLambdaMap()
	{
		lambdaMap = new HashMap<String, Consumer<Entry>>();
		
		Consumer<Entry> basic = entry -> makeBasicEntryDisplay(entry);
		lambdaMap.put(BasicEntry.class.getSimpleName(), basic);
		
		Consumer<Entry> image = entry -> makeImageEntryDisplay(entry);
		lambdaMap.put(ImageEntry.class.getSimpleName(),image);
		
		Consumer<Entry> video = entry ->makeVideoEntryDisplay(entry);
		lambdaMap.put(VideoEntry.class.getSimpleName(), video);
		
		
	}
	
	/**
	 * Method used to prepare the right side to display a basic Entry
	 * @param entry The basic entry to display
	 */
	private void makeBasicEntryDisplay(Entry entry)
	{
		BasicEntry bEntry = (BasicEntry)entry; 
	}
	
	/**
	 * Method used to prepare the right side to display a image Entry
	 * @param entry The image entry to display
	 */
	private void makeImageEntryDisplay(Entry entry)
	{
		ImageEntry iEntry = (ImageEntry)entry;
	}
	
	/**
	 * Method used to prepare the right side to display a video Entry
	 * @param entry The video entry to display
	 */
	private void makeVideoEntryDisplay(Entry entry)
	{
		VideoEntry vEntry = (VideoEntry) entry; 
	}

	
}
