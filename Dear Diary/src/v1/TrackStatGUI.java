package v1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import java.io.IOException;
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
	/*private final String SLEEP_TEXT = "Sleep Tracker";
	private final String DIET_TEXT = "Diet Tracker";
	private final String EXERCISE_TEXT = "Exercise Tracker";
	private final String MOOD_TEXT = "Mood Tracker";


	private BorderPane mainPane; 
	private BorderPane centerSubPane;
	private BorderPane leftSubPane;
	private BorderPane rightSubPane;
	private HBox bottomHBox;
	private Label chooseTrackerLabel; 
	private ChoiceBox<String> statChoiceBox; 
	private Button submitButton; 
	private Button backButton; 
	private TextField statTextField; 
	private Label descriptionLabel; 
	private Label successLabel;
	private Button showChart;
	private VBox header;

	private LineChart<?, ?> statHistory;
	private CategoryAxis xAxis;
	private NumberAxis yAxis;*/

	private HashMap<String, Tracker> trackerTable; 

	public TrackStatGUI(GUIManager manager) throws Exception 
	{
		super(manager);
		trackerTable = new HashMap<String, Tracker>();
		trackerTable.put("Sleep Tracker", new Tracker());
		trackerTable.put("Mood Tracker", new Tracker());
		trackerTable.put("Exercise Tracker", new Tracker());
		trackerTable.put("Diet Tracker", new Tracker());
		Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
		Scene scene = new Scene(root);
		
		setScene(scene);
	}

	@Override
	protected void prepareScene() {
		// TODO Auto-generated method stub
		
	}

	/**@Override
	/*protected void cleanUpScene() 
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

		changeTracker(MOOD_TEXT);


	}
	private void setUpLabels()
	{
		mainPane.setTop(getTitle());
		chooseTrackerLabel = new Label("Which Statistic would you like to track?");
		chooseTrackerLabel.setFont(new Font("Arial",35));
		chooseTrackerLabel.setTextFill(Color.BLACK);

		successLabel = new Label("Success!");
		successLabel.setFont(new Font("Ariel", 35));
		successLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(successLabel, Pos.CENTER);
		mainPane.setRight(successLabel);

		descriptionLabel = new Label(); 
		descriptionLabel.setFont(new Font("Arial",18));
		descriptionLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(descriptionLabel, Pos.CENTER);
		centerSubPane.setBottom(descriptionLabel);
		
		header.getChildren().add(chooseTrackerLabel);

	}

	private void setUpChoiceBox()
	{
		statChoiceBox = new ChoiceBox<String>(); 
		statChoiceBox.getItems().addAll(MOOD_TEXT, EXERCISE_TEXT, DIET_TEXT, SLEEP_TEXT);
		header.getChildren().add(statChoiceBox);


		//set up listener
		statChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
			{
				changeTracker(newValue);
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setUpButtons()
	{
		submitButton = new Button("Track Stat"); 
		backButton = new Button("Main Menu");
		showChart = new Button("View History");


		bottomHBox.getChildren().addAll(submitButton, showChart, backButton);

		submitButton.setOnAction((e) -> {
			try {
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
			}catch(NumberFormatException ex){
				successLabel.setText("Please Enter a valid number");
			}
		});

		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				cleanUpScene(); 
				getGUIManager().moveToMainMenu();

			}
		});



		showChart.setOnAction((e) -> {
			if(trackerTable.get(statChoiceBox.getValue()).getStats().isEmpty()) {
				successLabel.setText("No data to display");
			}
			else {
				HashMap<String, Integer> copyStats = trackerTable.get(statChoiceBox.getValue()).getStats();
				xAxis = new CategoryAxis();
				xAxis.setLabel("Date");
				yAxis = new NumberAxis();
				yAxis.setLabel("Label");

				statHistory = new LineChart(xAxis, yAxis);

				XYChart.Series series = new XYChart.Series();

				series.setName("Stats");

				for(String keyDate : copyStats.keySet()){
					series.getData().add(new XYChart.Data(keyDate,(Number)copyStats.get(keyDate)));
				}
				statHistory.getData().add(series);
				leftSubPane.getChildren().add(statHistory);
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
	/**private void changeTracker(String trackerToChange)
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

		statTextField.clear();

	}

	/**
	 * Method used to set up the panes of Track Stats Screen. 
	 */
	/*private void setUpPanes()
	{
		mainPane = new BorderPane(); 
		mainPane.setStyle("-fx-background-color: GREY");
		centerSubPane = new BorderPane(); 
		centerSubPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(centerSubPane, Pos.CENTER);
		mainPane.setCenter(centerSubPane);
		
		header = new VBox();
		header.setPadding(new Insets(10,10,10,10));
		header.setSpacing(40);
		BorderPane.setAlignment(header, Pos.TOP_CENTER);
		mainPane.setTop(header);
		
		
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
	/*private void makeTextField()
	{
		statTextField = new TextField(); 
		statTextField.setAlignment(Pos.CENTER_LEFT);
		statTextField.setPromptText("Enter stat here");
		statTextField.setMaxWidth(200);
		bottomHBox.getChildren().add(0, statTextField);

	}*/
}
