package v1;



import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent; 

/**
 * This class handles the super basic Home screen of the application
 * @author Matt Williams
 * @version 3.24.2020
 *
 */
public class HomeScreenGUI extends SceneHandler
{
	private BorderPane borderPane; 
	private Button loginButton; 
	
	private Label disclaimerLabel; 
	
	/**
	 * Basic overriden constructor
	 * @param manager The GUI manager to make callbacks to.
	 */
	public HomeScreenGUI(GUIManager manager)
	{
		super(manager);
	}

	/**
	 * Simple method used to make the login button of the Home screen 
	 */
	private void setUpLoginButton()
	{
		loginButton = new Button();
		loginButton.setText("Continue/Login");
		borderPane.setCenter(loginButton);
		BorderPane.setAlignment(loginButton, Pos.CENTER);
		
		loginButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				if(getGUIManager().getIsNewUser())
				{
					getGUIManager().moveToLoginScene();
				}
				
				else
				{
					getGUIManager().moveToMainMenu();
				}
			}
		});
	}

	/**
	 * Overriden method to clean up the scene before switching to a new scene
	 * Blank as of right now. 
	 */
	@Override
	protected void cleanUpScene() 
	{
		super.cleanUpScene();
	}

	/**
	 * Overriden method used to prepare the scene before it is switchted to. 
	 */
	@Override
	protected void prepareScene() 
	{
		borderPane = new BorderPane(); 
		borderPane.setStyle("-fx-background-color: LIGHTSKYBLUE");
		
		
		Scene scene = new Scene(borderPane,400,600);
		setScene(scene);
		
		setUpLabels();
		setUpLoginButton(); 
		
		
	}
	
	/**
	 * Method used to set up the labels for this screen
	 */
	private void setUpLabels()
	{
		borderPane.setTop(getTitle());
		disclaimerLabel = new Label("");
		disclaimerLabel.setFont(new Font("Arial",20));
		disclaimerLabel.setTextFill(Color.BLACK);
		disclaimerLabel.setPadding(new Insets(15,15,15,15));
		
		String text = "DISCLAIMER: \n";
		text += "This app is not designed to Diagnose any individual.\n";
		text += "Our purpose is to be able to provide knowledge, organization, \n";
		text += "and reflectivity to those already diagnosed by a trained \n";
		text += "medical professional. If you believe you may be suffering from \n";
		text += "one of these disorders, please seek consultation with a \n";
		text += "medical professional. \n";
		
		disclaimerLabel.setText(text);
		BorderPane.setAlignment(disclaimerLabel, Pos.CENTER);
		borderPane.setLeft(disclaimerLabel);
		
	}
	
}
