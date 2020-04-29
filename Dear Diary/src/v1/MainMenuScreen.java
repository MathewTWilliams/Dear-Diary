package v1;

import javafx.scene.Scene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * This class handles the Main Menu of the Application. 
 * 
 * @author Matt Williams 
 * @version 3.24.2020
 */
public class MainMenuScreen extends SceneHandler {


	private Button diaryEntryButton;
	private Button viewDiaryButton;
	private Button trackerButton;
	private Button mentalHealthButton;
	private BorderPane borderPane;
	private HBox horizontalPane;
	private Label welcomeLabel;

	/**
	 * Overriden basic constructor
	 * @param manager THE GUI manager to make callbacks to. 
	 */
	public MainMenuScreen(GUIManager manager)
	{
		super(manager);

	}

	/**
	 * Method used to setup all the buttons for the Main Menu Screen.
	 */
	private void setUpButtons() 
	{
		diaryEntryButton = new Button();
		viewDiaryButton = new Button();
		trackerButton = new Button();
		mentalHealthButton = new Button(); 


		diaryEntryButton.setText("New Entry");
		viewDiaryButton.setText("View Diary Entries");
		trackerButton.setText("Track a Stat");
		mentalHealthButton.setText("Mental Health Info");
		
		diaryEntryButton.setPrefSize(150, 75);
		viewDiaryButton.setPrefSize(150, 75);
		trackerButton.setPrefSize(150, 75);
		mentalHealthButton.setPrefSize(150, 75);
		horizontalPane.getChildren().addAll(diaryEntryButton, viewDiaryButton, trackerButton, mentalHealthButton);

		diaryEntryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				cleanUpScene();
				getGUIManager().moveToDiaryEntryScreen();
			}

		});

		trackerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				cleanUpScene();
				getGUIManager().moveToTrackStatScreen();
			}
		});



		viewDiaryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				cleanUpScene();
				getGUIManager().moveToViewEntriesScreen();
			}

		});


		mentalHealthButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				cleanUpScene();
				getGUIManager().moveToMentalHealthScreen();
			}

		});

	}

	/**
	 * Method used to setup the Horizontal Box container for our buttons. 
	 */
	private void setUpHBox()
	{
		horizontalPane = new HBox();
		horizontalPane.setMaxHeight(150);
		horizontalPane.setPadding(new Insets(15,12,15,12));
		horizontalPane.setSpacing(10);
		horizontalPane.setAlignment(Pos.CENTER);

		BorderPane.setAlignment(horizontalPane, Pos.CENTER);


		borderPane.setCenter(horizontalPane);
	}

	/**
	 * Overriden Method used to clean up the scene before the scene is switched to something else. 
	 * Currently empty. 
	 */
	@Override
	protected void cleanUpScene() {
		super.cleanUpScene();
	}

	/**
	 * Overriden Method used to prepare the scene before it is switched to. 
	 */
	@Override
	protected void prepareScene() {

		borderPane = new BorderPane(); 
		borderPane.setStyle("-fx-background-color : LIGHTSKYBLUE");
		Scene scene = new Scene(borderPane);
		setScene(scene);
		setUpHBox(); 
		setUpButtons(); 
		setUpLabels();
	}

	/**
	 * Method is used to setup all the labels for the Main Menu. 
	 */
	private void setUpLabels()
	{

		welcomeLabel = new Label();
		welcomeLabel.setFont(new Font("Arial",35));
		welcomeLabel.setTextFill(Color.BLACK);
		welcomeLabel.setText("Hello " + getGUIManager().getUserData().getName() + ". How are you doing today?");
		BorderPane.setAlignment(welcomeLabel, Pos.TOP_CENTER);
		borderPane.setBottom(welcomeLabel);

		borderPane.setTop(getTitle());

	}

}
