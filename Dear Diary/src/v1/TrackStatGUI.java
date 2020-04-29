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
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.logging.Level;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
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
	private Text historyText;
	private ButtonBase showChart;
	private Button backButton;
	private ButtonBase submitButton;
	private HBox bottomHBox;
	private VBox header;
	private Label chooseTrackerLabel; 
	private BorderPane mainPane;
	private BorderPane centerSubPane;
	private HBox descriptionBox;
	
	/**Logger for Login GUI, will help with any logging issues within the application*/
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TrackStatGUI(GUIManager manager) throws Exception 
	{
		super(manager);

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
				System.out.printf("Serialized HashMap data is saved in hashmap.ser");
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
				System.out.println("Class not found");
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setUpButtons()
	{
		submitButton = new Button("Track Stat"); 
		backButton = new Button("Main Menu");
		showChart = new Button("View History");


		bottomHBox.getChildren().addAll(submitButton, showChart, backButton);

		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event)
			{
				cleanUpScene(); 
				getGUIManager().moveToMainMenu();

			}
		});

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
							oos.close();
							fos.close();
							System.out.printf("Serialized HashMap data is saved in hashmap.ser");
						}catch(IOException ioe)
						{
							ioe.printStackTrace();
						}
						System.out.println("success");
					}
					else {
						LOGGER.log(Level.INFO, "User may have submitted two stats for the same field in one day");
						statTextField.setText("Invalid input");
					}
				} catch(NumberFormatException e) {
					LOGGER.log(Level.WARNING, "User inputted invalid input for tracker stat at " + LocalDateTime.now());
					statTextField.setText("Invalid input");
				}
			}
		});
		
		showChart.setOnMouseClicked(new EventHandler<MouseEvent>(){
			
			public void handle(MouseEvent event) {
				try{
					FileInputStream fis = new FileInputStream("trackers.ser");
					ObjectInputStream ois = new ObjectInputStream(fis);
					trackerTable = (HashMap) ois.readObject();
					String textForHistory = "";
					for(String key : trackerTable.keySet()) {
						HashMap<String, Integer> stats = trackerTable.get(key).getStat();
						
						for(String statKey : stats.keySet()) {
							textForHistory += (stats.get(statKey)+ " " + key + "\n");
						}
					}
					historyText.setText(textForHistory);
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
	private void setUpPanes()
	{
		mainPane = new BorderPane(); 
		String mainPaneColor = "-fx-background-color: rgba(255, 255, 153, 0.5);";
		mainPane.setStyle(mainPaneColor);
		historyText = new Text();
		historyText.setFont(new Font(15));
		centerSubPane = new BorderPane(); 
		centerSubPane.setPadding(new Insets(10,10,10,10));
		BorderPane.setAlignment(centerSubPane, Pos.CENTER);
		mainPane.setCenter(centerSubPane);
		
		BorderPane.setAlignment(historyText, Pos.CENTER);
		centerSubPane.setCenter(historyText);

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
