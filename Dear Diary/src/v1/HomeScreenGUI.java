package v1;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Button;
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
		loginButton.setText("Contine/Login");
		borderPane.setCenter(loginButton);
		BorderPane.setAlignment(loginButton, Pos.TOP_CENTER);
		
		loginButton.setOnMousePressed(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				getGUIManager().moveToLoginScene();
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
		borderPane.setStyle("-fx-background-color: GREY");
		
		Scene scene = new Scene(borderPane,400,600);
		setScene(scene);
		
		borderPane.setTop(getTitle());
		setUpLoginButton(); 
		
	}
	
	
	
}
