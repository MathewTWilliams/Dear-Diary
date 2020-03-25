package v1;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.ChoiceBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;
import java.time.LocalDate; 
import java.time.ZoneId;

/**
 * This class handles the Track Stat Menu for the Application
 * @author Matt Williams
 * @version 3.24.2020
 *
 */

public class TrackStatGUI extends SceneHandler 
{
	//for testing purposes for now. 
	private final String SLEEP_TEXT = "Sleep Tracker";
	private final String DIET_TEXT = "Diet Tracker";
	private final String EXERCISE_TEXT = "Exercise Tracker";
	private final String MOOD_TEXT = "Mood Tracker";
	

	private BorderPane mainPane; 
	private BorderPane centerSubPane; 
	private HBox bottomHBox;
	private Label chooseTrackerLabel; 
	private ChoiceBox<String> statChoiceBox; 
	private Button submitButton; 
	private Button backButton; 
	private TextField statTextField; 
	private Label descriptionLabel; 
	
	private LineChart<String, Number> trackerLineChart; 
	private Series<String,Number> mainSeries; 
	NumberAxis yAxis; 
	CategoryAxis xAxis; 
	
	
	private HashMap<String, TestTracker> trackerTable; 
	
	/**
	 * Basic Overriden Constructor
	 * @param manager The GUI manager to make callbacks to. 
	 */
	public TrackStatGUI(GUIManager manager) 
	{
		super(manager);
	}

	/**
	 * Overriden method used to clean up the scene before a new scene is switched to. 
	 * Currently Empty. 
	 */
	@Override
	protected void cleanUpScene() 
	{
		super.cleanUpScene();
	}

	/**
	 * Overriden method used to prepare the scene before it is switched to. 
	 */
	@Override
	protected void prepareScene() 
	{
		
		setUpPanes();
		Scene scene = new Scene(mainPane);
		setScene(scene);
		
		makeTrackerTable();
		makeLineChart(); 
		setUpLabels(); 
		setUpChoiceBox(); 
		setUpButtons(); 
		makeTextField();
		
		changeTracker(MOOD_TEXT);
		
		
	}
	
	/**
	 * Method used to setup all the labels in the Track Stats screen. 
	 */
	private void setUpLabels()
	{
		mainPane.setTop(getTitle());
		
		chooseTrackerLabel = new Label("Which Statistic would you like to track?");
		chooseTrackerLabel.setFont(new Font("Arial",35));
		chooseTrackerLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(chooseTrackerLabel, Pos.TOP_CENTER);
		centerSubPane.setTop(chooseTrackerLabel);
		
		descriptionLabel = new Label(); 
		descriptionLabel.setFont(new Font("Arial",18));
		descriptionLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(descriptionLabel, Pos.CENTER);
		centerSubPane.setBottom(descriptionLabel);
	
	}
	
	/**
	 * Method used to setup the choice box for the Track Stats screen. 
	 */
	private void setUpChoiceBox()
	{
		statChoiceBox = new ChoiceBox<String>(); 
		statChoiceBox.getItems().addAll(MOOD_TEXT, EXERCISE_TEXT, DIET_TEXT, SLEEP_TEXT);
		BorderPane.setAlignment(statChoiceBox, Pos.TOP_CENTER);
		centerSubPane.setCenter(statChoiceBox);
		
		
		//set up listener
		statChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
			{
				changeTracker(newValue);
			}
		});
	}
	
	/**
	 * Method used to set up the bottons for the Track Stats screen.
	 */
	private void setUpButtons()
	{
		submitButton = new Button("Track Stat"); 
		backButton = new Button("Main Menu");
		
		bottomHBox.getChildren().addAll(submitButton, backButton);
		
		submitButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				String timeZone = "America/New_York";
				String now = LocalDate.now(ZoneId.of(timeZone)).toString();
				double number = Double.valueOf(statTextField.getText());
				
				
				Data<String,Number> newPoint = new Data<String,Number>(now,number);
				mainSeries.getData().add(newPoint);
				statTextField.clear();
				submitButton.setDisable(true);
				
				trackerTable.get(statChoiceBox.getValue()).testTracks.put(now, number);
			}
		});
		
		
		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				cleanUpScene(); 
				getGUIManager().moveToMainMenu();
				
			}
		});
		
	}
	
	/**
	 * Method used by our ChangeListener to change the Screen based on the tracker selected
	 * @param trackerToChange The new tracker that has been selected. 
	 */
	private void changeTracker(String trackerToChange)
	{
		
		
		statChoiceBox.setValue(trackerToChange);
		TestTracker currentTestTracker = trackerTable.get(trackerToChange);
		
		
		descriptionLabel.setText(currentTestTracker.description);
		yAxis.setLabel(currentTestTracker.yAxisString);
		trackerLineChart.setTitle(trackerToChange);
		mainSeries = new Series<String,Number>();
		//grabs the fist word found in trackerToChange string.
		mainSeries.setName(trackerToChange.split("\\s")[0]);
		
		Set<String> keys = currentTestTracker.testTracks.keySet();
		for(String key : keys)
		{
			Data<String,Number> point = new Data<String,Number>(key,currentTestTracker.testTracks.get(key));
			mainSeries.getData().add(point);
		}
		
		trackerLineChart.getData().clear();
		trackerLineChart.getData().add(mainSeries);
		statTextField.clear();
		submitButton.setDisable(true);
		
	}
	
	/**
	 * Method used to set up the panes of Track Stats Screen. 
	 */
	private void setUpPanes()
	{
		mainPane = new BorderPane(); 
		mainPane.setStyle("-fx-background-color: GREY");
		centerSubPane = new BorderPane(); 
		centerSubPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(centerSubPane, Pos.CENTER);
		mainPane.setCenter(centerSubPane);
		
		bottomHBox = new HBox();
		bottomHBox.setPadding(new Insets(10,10,10,10));
		bottomHBox.setSpacing(40);
		bottomHBox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(bottomHBox,Pos.CENTER);
		mainPane.setBottom(bottomHBox);
		
		
	}
	
	/**
	 * Test method used to make a hash table for a tracker name and its associated Test Tracker
	 */
	private void makeTrackerTable()
	{
		trackerTable = new HashMap<String, TestTracker>();
		trackerTable.put(MOOD_TEXT, new TestTracker("Mood Description", "out of 10"));
		trackerTable.put(DIET_TEXT, new TestTracker("Diet Description", "Calories"));
		trackerTable.put(SLEEP_TEXT, new TestTracker("Sleep Description", "Hours"));
		trackerTable.put(EXERCISE_TEXT, new TestTracker("Exercise Description", "Minutes"));

	}
	
	/**
	 * Method used to make the line chart for the Track stats screen.  
	 */
	private void makeLineChart()
	{
		yAxis = new NumberAxis(); 
		xAxis = new CategoryAxis(); 
		xAxis.setLabel("Date");
		trackerLineChart = new LineChart<String, Number>(xAxis,yAxis);
		BorderPane.setAlignment(trackerLineChart, Pos.CENTER);
		centerSubPane.setRight(trackerLineChart);
	}
	
	/**
	 * Method used to make the stats text field
	 */
	private void makeTextField()
	{
		statTextField = new TextField(); 
		statTextField.setAlignment(Pos.CENTER_LEFT);
		statTextField.setPromptText("Enter stat here");
		statTextField.setMaxWidth(200);
		bottomHBox.getChildren().add(0, statTextField);
		
	
		statTextField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			
			public void handle(KeyEvent event)
			{
				String regex = "\\d+(\\.\\d+)?";
				
				if(statTextField.getText().matches(regex))
				{
					submitButton.setDisable(false);
				}
				
				else
				{
					submitButton.setDisable(true);
				}
			}
		});
	}
	
	
	/**
	 * 
	 * Just used as a test class until actual trackers are implemented. 
	 *
	 */
	private class TestTracker
	{
		public HashMap<String,Double> testTracks; 
		public String description; 
		public String yAxisString;
		
		public TestTracker(String description, String yAxisString)
		{
			this.description = description; 
			this.yAxisString = yAxisString; 
			testTracks = new LinkedHashMap<String, Double>();
			makeRandomTableValues();
		}
		
		/**
		 * Function that makes test dates and values
		 */
		private void makeRandomTableValues()
		{
			String timeZone = "America/New_York";
			LocalDate now = LocalDate.now(ZoneId.of(timeZone));
			Random rand = new Random(); 
			int testBound = 11;
			int numRandomValues = 4;
			
			for(int i = 0 ; i < numRandomValues; i++)
			{
				String testDay = now.minusDays(numRandomValues - i).toString();
				double testValue = rand.nextDouble() * rand.nextInt(testBound);
				testTracks.put(testDay, testValue);
			}
		}
		
	}
	

}
