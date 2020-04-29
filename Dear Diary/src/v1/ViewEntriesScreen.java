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
import javafx.scene.control.SelectionMode;
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
	
	private Label commentLabel; 
	private Label oldCommentsLabel;
	private TextArea newCommentArea;
	private TextArea oldCommentsArea;
	private Button commentButton;
	private Entry currentEntry;
	
	
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
		mainPane.setStyle("-fx-background-color : LIGHTSKYBLUE");
		Scene scene = new Scene(mainPane);
		setScene(scene);
		
		editFeedAndEngine();
		
		makeBoxes();
		setUpButtons();
		setUpLabels();
		makeTextAreas(); 
		makeListView();
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
	private void makeTextAreas()
	{
		entryView = new TextArea(); 
		entryView.setEditable(false);		
		entryView.setMaxSize(400, 400);
		
		newCommentArea = new TextArea("Add Comment Here"); 	
		newCommentArea.setMaxSize(400, 400);
		
		oldCommentsArea = new TextArea(); 
		oldCommentsArea.setEditable(false);		
		oldCommentsArea.setMaxSize(400, 400);
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
		leftVBox.getChildren().add(0,datesLabel);
		
		entriesLabel = new Label("Entries");
		entriesLabel.setFont(new Font("Arial",15));
		entriesLabel.setTextFill(Color.BLACK);
		rightVBox.getChildren().add(entriesLabel);
		
		commentLabel = new Label("Add a Comment to this Entry.");
		commentLabel.setFont(new Font("Arial",15));
		commentLabel.setTextFill(Color.BLACK);
		
		oldCommentsLabel = new Label("Old comments about this Entry.");
		oldCommentsLabel.setFont(new Font("Arial",15));
		oldCommentsLabel.setTextFill(Color.BLACK);
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
		
		commentButton = new Button();
		commentButton.setText("Add Comment");
		
		commentButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				if(currentEntry != null)
				{
					currentEntry.newComment(newCommentArea.getText());
					if(lambdaMap.containsKey(currentEntry.getClass().getSimpleName()))
					{
						lambdaMap.get(currentEntry.getClass().getSimpleName()).accept(currentEntry);
					}
				}
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
		dateList.setPrefSize(300, 600);
		dateList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		leftVBox.getChildren().add(1,dateList);
		

		ArrayList<Entry> entries = getGUIManager().getUserData().getUserEntries();
	
		
		for(Entry en: entries)
		{
			dateList.getItems().add(en.getDate().toString());
		}
		
		if(entries.size() > 0)
		{
			Entry firstEntry = entries.get(0);
			if(lambdaMap.containsKey(firstEntry.getClass().getSimpleName()))
			{
				lambdaMap.get(firstEntry.getClass().getSimpleName()).accept(firstEntry);
			}
		}
		
		
		
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
		entryEngine.loadContent("");
		ArrayList<Entry> entries = getGUIManager().getUserData().getUserEntries();
		
		for(int i = 0; i < entries.size();i++)
		{
			if(entries.get(i).getDate().toString().equals(date))
			{
				Entry entry = entries.get(i);
				if(lambdaMap.containsKey(entry.getClass().getSimpleName()))
				{
					currentEntry = entry;
					lambdaMap.get(entry.getClass().getSimpleName()).accept(entry);
					return; 
				}
			}
		}
	}
	
	/**
	 * Method used to instantiate our HashMap of Lambdas
	 */
	private void makeLambdaMap()
	{
		lambdaMap = new HashMap<String, Consumer<Entry>>();
		
		Consumer<Entry> basic = entry -> makeBasicEntryDisplay(entry);
		lambdaMap.put(BasicEntry.class.getSimpleName(), basic);
		
		Consumer<Entry> image = entry -> makeImageEntryDisplay(entry);
		lambdaMap.put(PhotoEntry.class.getSimpleName(),image);
		
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
		rightVBox.getChildren().remove(1, rightVBox.getChildren().size());
		rightVBox.getChildren().add(entryView);
		entryView.setText(bEntry.getText());
		addRightVBoxBottom(entry);
	}
	
	/**
	 * Method used to prepare the right side to display a image Entry
	 * @param entry The image entry to display
	 */
	private void makeImageEntryDisplay(Entry entry)
	{
		PhotoEntry iEntry = (PhotoEntry)entry;
		rightVBox.getChildren().remove(1, rightVBox.getChildren().size());
		rightVBox.getChildren().addAll(entryFeed,entryView);
		entryView.setText(iEntry.getText());
		entryEngine.loadContent(iEntry.toString());
		addRightVBoxBottom(entry);
		
	}
	
	/**
	 * Method used to prepare the right side to display a video Entry
	 * @param entry The video entry to display
	 */
	private void makeVideoEntryDisplay(Entry entry)
	{
		VideoEntry vEntry = (VideoEntry) entry; 
		rightVBox.getChildren().remove(1, rightVBox.getChildren().size());
		rightVBox.getChildren().addAll(entryFeed,entryView);
		entryView.setText(vEntry.getText());
		entryEngine.loadContent(vEntry.toString());
		addRightVBoxBottom(entry);
	}

	
	/**
	 * Method used to edit our WebView and Engine to prepare for viewing images and videos
	 */
	private void editFeedAndEngine()
	{
		entryFeed.setPrefHeight(300);
		entryFeed.setPrefWidth(300);
		entryEngine.setUserStyleSheetLocation("data:,body { font: 10px Arial; }");
	}
	
	/**
	 * Method used after the rightVBox of the scene has been edited.
	 * This method adds the bottom that is to be added no matter what.
	 * @param entry the entry clicked on
	 */
	private void addRightVBoxBottom(Entry entry)
	{	
		rightVBox.getChildren().addAll(oldCommentsLabel,oldCommentsArea);
		ArrayList<Comment> comments = entry.getComments();
		
		String commentText = "";
		for(Comment c : comments)
		{
			commentText = commentText + 
						  c.getDate().toString() + 
						  "\n" + 
						  c.getText() +
						  "\n"; 
		}
		
		oldCommentsArea.setText(commentText);
		
		newCommentArea.setText("Add a comment here");
		
		rightVBox.getChildren().addAll(commentLabel,newCommentArea, commentButton);
	}
	
}
