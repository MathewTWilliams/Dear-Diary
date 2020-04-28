package v1;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


/**
 *  This class is used to handle the Login Screen of the Application.
 * @author Matt Williams
 * @version 3.24.2020
 *
 */
public class LoginGUI extends SceneHandler {

	private TextField nameField; 
	private TextField dobField; 
	private Label descriptionLabel; 
	private BorderPane borderPane; 
	private BorderPane centerSubPane;
	private Button submitButton;
	
	/**Logger for Login GUI, will help with any logging issues within the application*/
	private final static Logger LOGGER =  
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Basic Overriden Constructor
	 * @param manager The GUI manager to make callbacks to. 
	 */
	public LoginGUI(GUIManager manager)
	{
		super(manager);
	}
	
	/**
	 * Method used to setup all of the labels for the Login Screen. 
	 */
	private void setUpLabels()
	{	
		descriptionLabel = new Label("Please Enter your name and Date of Birth(mm/dd/yyyy).");
		descriptionLabel.setTextFill(Color.BLACK);
		descriptionLabel.setFont(new Font("Arial",20));
		centerSubPane.setTop(descriptionLabel);
		BorderPane.setAlignment(descriptionLabel, Pos.TOP_CENTER);

		borderPane.setTop(getTitle());
		
		
	}
	
	/**
	 * Method used to setup the text fields of the Login Screen. 
	 */
	private void setUpTextFields()
	{
		nameField = new TextField(); 
		centerSubPane.setLeft(nameField);
		nameField.setPromptText("Name");
		nameField.getParent().requestFocus();
		nameField.setMaxWidth(200);
		BorderPane.setAlignment(nameField, Pos.CENTER_RIGHT);
		
		dobField = new TextField();
		centerSubPane.setCenter(dobField);
		dobField.setPromptText("DOB: mm/dd/yyyy");
		dobField.setMaxWidth(200);
		BorderPane.setAlignment(dobField, Pos.CENTER);
		
	}
	
	/**
	 * Method used to setup the Buttons of the Login Screen. 
	 */
	private void setUpButtons()
	{
		//submit button
		submitButton = new Button();
		submitButton.setText("Submit");
		centerSubPane.setRight(submitButton);
		BorderPane.setAlignment(submitButton, Pos.CENTER_RIGHT);
		
		
		submitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				try {
					if (isValidName(nameField.getText().toString()) && isValidDate(dobField.getText().toString())) {
						LOGGER.log(Level.INFO, nameField.getText() + " logged in at " + LocalDateTime.now());
						cleanUpScene();
						getGUIManager().moveToMainMenu();
					} else {
						throw new InvalidCredentialsException();
					}
				} catch (InvalidCredentialsException e) {
					JOptionPane.showMessageDialog(null, "Name or date not valid", "Input Error", JOptionPane.WARNING_MESSAGE);
					LOGGER.log(Level.INFO, "Invalid login credentials given at " + LocalDateTime.now());
				}
			}
		});
		
		
	}
	
	/**
	 * Method returns whether the name inputted valid or not.
	 * @param name The name being checked
	 * @return Whether name is okay or not (as boolean)
	 */
	private boolean isValidName(String name) {
		String regx = "^[\\p{L} .'-]+$";
		if (!name.matches(regx)) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Method used check if the DOB a person entered is valid
	 * @param date The birthday of the user. 
	 * @return True if a valid birthday, false otherwise. 
	 */
	private boolean isValidDate(String date)
	{
			String validDateRegex = "[0-9][0-9]\\/[0-9][0-9]\\/[0-9][0-9][0-9][0-9]";
			String splitRegex = "\\/";

			String timeZone = "America/New_York";

			if(!date.matches(validDateRegex))
			{
				return false; 
			}

			int month = Integer.valueOf(date.split(splitRegex)[0]);
			int day = Integer.valueOf(date.split(splitRegex)[1]);
			int year = Integer.valueOf(date.split(splitRegex)[2]);

			if(month == 0 || month > 12)
			{
				return false; 
			}


			if(day == 0 || day > Month.of(month).length(Year.isLeap(year)))
			{
				return false; 
			}

			LocalDate now = LocalDate.now(ZoneId.of(timeZone));
			LocalDate entered = LocalDate.of(year, month, day);
			if(entered.isAfter(now))
			{
				return false; 
			}


			return true; 
	}


	/**
	 * Overriden method used to clean up the scene before the scene is switched to something else. 
	 */
	@Override
	protected void cleanUpScene() {
		
		getGUIManager().getUserData().setDateOfBirth(dobField.getText());
		getGUIManager().getUserData().setName(nameField.getText());
		super.cleanUpScene();
		
	}


	/**
	 * Overriden method used to prepare the scene before it is switched to. 
	 */
	@Override
	protected void prepareScene() {
		
		borderPane = new BorderPane(); 
		borderPane.setStyle("-fx-background-color: GREY");
		centerSubPane = new BorderPane(); 
		borderPane.setCenter(centerSubPane);
		BorderPane.setAlignment(centerSubPane, Pos.CENTER);
		
		Scene scene = new Scene(borderPane);
		setScene(scene);
		
		setUpLabels(); 
		setUpTextFields();
		setUpButtons();
		
	}

}
