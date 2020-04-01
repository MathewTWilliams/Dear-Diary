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
import javafx.scene.chart.XYChart;
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
	private Label successLabel;

	private LineChart<String, Number> trackerLineChart; 
	private Series<String,Number> mainSeries; 
	private NumberAxis yAxis; 
	private CategoryAxis xAxis; 


	private HashMap<String, Tracker> trackerTable; 

	public TrackStatGUI(GUIManager manager) 
	{
		super(manager);
		trackerTable = new HashMap<String, Tracker>();
		trackerTable.put("Sleep Tracker", new Tracker());
		trackerTable.put("Mood Tracker", new Tracker());
		trackerTable.put("Exercise Tracker", new Tracker());
		trackerTable.put("Diet Tracker", new Tracker());
	}

	@Override
	protected void cleanUpScene() 
	{
		super.cleanUpScene();
	}

	@Override
	protected void prepareScene() 
	{

		setUpPanes();
		Scene scene = new Scene(mainPane);
		setScene(scene);
		setUpLabels(); 
		setUpChoiceBox(); 
		setUpButtons(); 
		makeTextField();
		makeLineChart();

		changeTracker(MOOD_TEXT);


	}
	private void setUpLabels()
	{
		mainPane.setTop(getTitle());
		chooseTrackerLabel = new Label("Which Statistic would you like to track?");
		chooseTrackerLabel.setFont(new Font("Arial",35));
		chooseTrackerLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(chooseTrackerLabel, Pos.TOP_CENTER);
		centerSubPane.setTop(chooseTrackerLabel);
		successLabel = new Label("Success!");
		successLabel.setFont(new Font("Ariel", 35));
		successLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(successLabel, Pos.CENTER);
		centerSubPane.setRight(successLabel);
		descriptionLabel = new Label(); 
		descriptionLabel.setFont(new Font("Arial",18));
		descriptionLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(descriptionLabel, Pos.CENTER);
		centerSubPane.setBottom(descriptionLabel);

	}

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

	private void setUpButtons()
	{
		submitButton = new Button("Track Stat"); 
		backButton = new Button("Main Menu");

		bottomHBox.getChildren().addAll(submitButton, backButton);
		
		submitButton.setOnAction((e) -> {
			if(validInput(statChoiceBox.getValue(), Integer.parseInt(statTextField.getText()))) {
				if(trackerTable.get(statChoiceBox.getValue()).inputStat(Integer.parseInt(statTextField.getText()))) {
					successLabel.setText("YUUUUP");
				}
				else {
					successLabel.setText("Stat tracked today already");
				}
			}
			else {
				successLabel.setText("Invalid Entry");
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
	
	public boolean validInput(String currentTracker, int stat) {
		if(currentTracker.equals("Mood Tracker")) {
			if(stat > 10 || stat < 1) {
				return false;
			}
			return true;
		}
		else if(currentTracker.equals("Sleep Tracker")) {
			if(stat > 24 || stat < 1) {
				return false;
			}
			return true;
		}
		else if(currentTracker.equals("Exercise Tracker")) {
			if(stat > 24 || stat < 1) {
				return false;
			}
			return true;
		}
		else if(currentTracker.equals("Diet Tracker")) {
			if(stat > 10000 || stat < 1) {
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * Method used by our ChangeListener to change the Screen based on the tracker selected
	 * @param trackerToChange The new tracker that has been selected. 
	 */
	private void changeTracker(String trackerToChange)
	{


		statChoiceBox.setValue(trackerToChange);

		if(trackerToChange.equals("Mood Tracker")) {
			descriptionLabel.setText("What is your mood 1-10");
		}
		else if(trackerToChange.equals("Sleep Tracker")) {
			descriptionLabel.setText("How many hours did you sleep?");
		}
		else if(trackerToChange.equals("Exercise Tracker")) {
			descriptionLabel.setText("How long did you work out today?");
		}
		else if(trackerToChange.equals("Diet Tracker")) {
			descriptionLabel.setText("About how many calories did you eat today?");
		}
		HashMap<String, Integer> copyStats = trackerTable.get(trackerToChange).getStats();
		for(String key : copyStats.keySet())
		{
			Data<String, Number> point = new Data<String, Number>(key, copyStats.get(key));
			mainSeries.getData().add(point);
		}
		
		trackerLineChart.getData().clear();
		trackerLineChart.getData().add(mainSeries);
		statTextField.clear();

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
	 * Method used to make the stats text field
	 */
	private void makeTextField()
	{
		statTextField = new TextField(); 
		statTextField.setAlignment(Pos.CENTER_LEFT);
		statTextField.setPromptText("Enter stat here");
		statTextField.setMaxWidth(200);
		bottomHBox.getChildren().add(0, statTextField);
		
	}
	private void makeLineChart()
	{
		yAxis = new NumberAxis(); 
		xAxis = new CategoryAxis(); 
		xAxis.setLabel("Date");
		trackerLineChart = new LineChart<String, Number>(xAxis,yAxis);
		BorderPane.setAlignment(trackerLineChart, Pos.CENTER_LEFT);
		centerSubPane.setLeft(trackerLineChart);
	}


}
