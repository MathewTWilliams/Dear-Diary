package v1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.ChoiceBox;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.Random;
import java.util.Set;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate; 
import java.time.ZoneId;

/**
 * This class handles the Track Stat Menu for the Application
 * @author Matt Williams
 * @version 3.24.2020
 *
 */

public class TrackStatGUI extends SceneHandler implements Serializable
{

	private static final long serialVersionUID = -5712356763668291198L;
	private HashMap<String, Trackable> trackerTable;
	private ChoiceBox<String> statChoiceBox;
	private Label descriptionLabel;
	private TextField statTextField;
	private ButtonBase showChart;
	private Button backButton;
	private ButtonBase submitButton;
	private HBox bottomHBox;
	private VBox header;
	private Label chooseTrackerLabel; 
	private BorderPane mainPane;
	private BorderPane centerSubPane;
	private HBox descriptionBox;
	private LineChart<String, Number> lineChart;
	@SuppressWarnings("rawtypes")
	private XYChart.Series series;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TrackStatGUI(GUIManager manager) throws Exception 
	{
		super(manager);
		/*
		 * Check if a file exists containing trackers, if it doesn't create a new file containing the
		 * tracker table
		 * 
		 * If file exists load the HashMap contents into trackerTable
		 */
		if(!new File("Trackers.ser").exists())
			try{		
				trackerTable = new HashMap<String, Trackable>();
				trackerTable.put("Sleep Tracker", new SleepTracker());
				trackerTable.put("Mood Tracker", new MoodTracker());
				trackerTable.put("Exercise Tracker", new ExerciseTracker());
				trackerTable.put("Diet Tracker", new DietTracker());
				FileOutputStream fos =
						new FileOutputStream("Trackers.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(trackerTable);
				oos.close();
				fos.close();
			}catch(IOException ioe)
		{
				ioe.printStackTrace();
		}
		else {
			try{
				FileInputStream fis = new FileInputStream("trackers.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				trackerTable = (HashMap) ois.readObject();
				ois.close();
				fis.close();
			}catch(IOException ioe)
			{
				ioe.printStackTrace();
				return;
			}catch(ClassNotFoundException c)
			{
				c.printStackTrace();
				return;
			}
		}
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

		changeTracker("Mood Tracker");


	}
	/**
	 * Method to set up all labels in the GUI
	 */
	private void setUpLabels()
	{
		chooseTrackerLabel = new Label("Which Statistic would you like to track?");
		chooseTrackerLabel.setFont(new Font("Arial",35));
		chooseTrackerLabel.setTextFill(Color.BLACK);
		descriptionLabel = new Label(); 
		descriptionLabel.setFont(new Font("Arial",18));
		descriptionLabel.setTextFill(Color.BLACK);
		descriptionBox.getChildren().add(descriptionLabel);

	}
	
	/**
	 * Method to set up the choice box, also adds a listener that will switch various items
	 * to the new tracker selected
	 */
	private void setUpChoiceBox()
	{
		statChoiceBox = new ChoiceBox<String>(); 
		statChoiceBox.getItems().addAll("Mood Tracker", "Exercise Tracker", "Diet Tracker", "Sleep Tracker");
		header.getChildren().addAll(chooseTrackerLabel, statChoiceBox);
		//set up listener
		statChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
			{
				changeTracker(newValue);
			}
		});
	}
	
	/*
	 * Method to set up the buttons for the GUI
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setUpButtons()
	{
		submitButton = new Button("Track Stat"); 
		backButton = new Button("Main Menu");
		showChart = new Button("View History");


		bottomHBox.getChildren().addAll(submitButton, showChart, backButton);
		
		/**
		 * If main menu button is pressed return to the main menu
		 */
		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				cleanUpScene(); 
				getGUIManager().moveToMainMenu();

			}
		});
		/**
		 * If submit button is pressed get the text from the statTextField and parse it into a integer
		 * then proceed to input stat into the current tracker selected from the statChoiceBox, if the trackStat
		 * method returns true write to the Trackers file the updated tracker.
		 * 
		 * If false is returned display an invalid input message in the statTextField
		 */
		submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				try {
					int stat = Integer.parseInt(statTextField.getText());
					if(trackerTable.get(statChoiceBox.getValue()).trackStat(stat)) {
						try{		
							FileOutputStream fos =
									new FileOutputStream("Trackers.ser");
							ObjectOutputStream oos = new ObjectOutputStream(fos);
							oos.writeObject(trackerTable);
							statTextField.setText("Stat Recorded");
							oos.close();
							fos.close();
						}catch(IOException ioe)
						{
							ioe.printStackTrace();
						}
					}
					else {
						statTextField.setText("Invalid input");
					}
				}catch(NumberFormatException e){
					statTextField.setText("Invalid input");
				}
			}
		});
		
		/**
		 * If the view history button is pressed retrieve the HashMap from the tracker file and fill an xy series
		 * from the information in the collection. Then populate the lineChart with the new series
		 * 
		 * If the tracker does not contain any information a blank line chart will appear
		 */
		showChart.setOnMouseClicked(new EventHandler<MouseEvent>(){

			public void handle(MouseEvent event) {
				try{
					FileInputStream fis = new FileInputStream("trackers.ser");
					ObjectInputStream ois = new ObjectInputStream(fis);
					trackerTable = (HashMap) ois.readObject();
					series = new XYChart.Series();
					series.setName("My " + statChoiceBox.getValue());
					if(trackerTable.get(statChoiceBox.getValue()).getStat() != null){
						HashMap<String, Integer> cpStats = trackerTable.get(statChoiceBox.getValue()).getStat();
						for(String key : cpStats.keySet()) {
							int stat = cpStats.get(key);
							series.getData().add(new XYChart.Data(key, stat));
						}
						lineChart.getData().add(series);
					}
					ois.close();
					fis.close();
				}catch(IOException ioe)
				{
					ioe.printStackTrace();
					return;
				}catch(ClassNotFoundException c)
				{
					c.printStackTrace();
					return;
				}
				showChart.setDisable(true);
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

		if(trackerToChange.equals("Mood Tracker")) {
			descriptionLabel.setText("What is your mood 1-10");
			lineChart.getData().clear();
			lineChart.setTitle("Mood Tracker");
			showChart.setDisable(false);
		}
		else if(trackerToChange.equals("Sleep Tracker")) {
			descriptionLabel.setText("How many hours did you sleep?");
			lineChart.getData().clear();
			lineChart.setTitle("Exercise Tracker");
			showChart.setDisable(false);
		}
		else if(trackerToChange.equals("Exercise Tracker")) {
			descriptionLabel.setText("How long did you work out today?");
			lineChart.getData().clear();
			lineChart.setTitle("Exercise Tracker");
			showChart.setDisable(false);
		}
		else if(trackerToChange.equals("Diet Tracker")) {
			descriptionLabel.setText("About how many calories did you eat today?");
			lineChart.getData().clear();
			lineChart.setTitle("Diet Tracker");
			showChart.setDisable(false);
		}

		statTextField.clear();

	}

	/**
	 * Method used to set up the panes of Track Stats Screen. 
	 */
	private void setUpPanes()
	{
		mainPane = new BorderPane(); 
		String mainPaneColor = "-fx-background-color: rgba(255, 255, 153, 0.5);";
		mainPane.setStyle(mainPaneColor);
		centerSubPane = new BorderPane(); 
		centerSubPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(centerSubPane, Pos.CENTER);
		mainPane.setCenter(centerSubPane);


		header = new VBox();
		header.setPadding(new Insets(10,10,10,10));
		header.setSpacing(20);
		String lightBlueColor = "-fx-background-color: rgba(51, 204, 255, 0.5);";
		header.setStyle(lightBlueColor);
		header.setAlignment(Pos.TOP_CENTER);
		BorderPane.setAlignment(header, Pos.CENTER);
		mainPane.setTop(header);

		descriptionBox = new HBox();
		descriptionBox.setPadding(new Insets(10,20,20,20));
		descriptionBox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(descriptionBox, Pos.CENTER);
		centerSubPane.setBottom(descriptionBox);


		bottomHBox = new HBox();
		bottomHBox.setPadding(new Insets(30,10,10,10));
		bottomHBox.setSpacing(40);
		bottomHBox.setStyle(lightBlueColor);
		bottomHBox.setAlignment(Pos.CENTER);
		BorderPane.setAlignment(bottomHBox,Pos.CENTER);
		mainPane.setBottom(bottomHBox);

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Date");
		lineChart = new LineChart<String,Number>(xAxis,yAxis);
		lineChart.setPrefSize(500, 500);
		lineChart.setMinSize(450, 450);
		lineChart.setMaxSize(700, 700);
		BorderPane.setAlignment(lineChart, Pos.CENTER);
		centerSubPane.setCenter(lineChart);

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
}
