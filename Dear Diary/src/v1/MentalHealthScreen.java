package v1;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class used to handle the Mental Health Screen.
 * @author Matt Williams
 * @version 4.28.2020
 *
 */
public class MentalHealthScreen extends SceneHandler {
	
	
	
	private VBox root; 
	private HBox subPane; 
	private VBox leftVBox; 
	private VBox rightVBox; 
	private Button backButton;
	private TextArea infoArea;
	
	private Label disordersLabel;
	private Label infoLabel; 
	private Label descriptionLabel;
	
	
	private ListView<String> disorderList;
	private DisorderLibrary disorderLibrary;
	
	
	/**
	 * Standard Constructor
	 * @param manager
	 */
	public MentalHealthScreen(GUIManager manager) {
		super(manager);
		disorderLibrary = new DisorderLibrary(); 
		
	}

	/**
	 * Overriden method used to prepare the scene before being switched to
	 */
	@Override
	protected void prepareScene() 
	{
		root = new VBox(); 
		root.setAlignment(Pos.TOP_CENTER);
		root.setPadding(new Insets(10,10,10,10));
		root.setStyle("-fx-background-color : GREY");
		Scene scene = new Scene(root);
		setScene(scene);
		
		makeBoxes(); 
		makeLabels();
		makeDisorderList();
		makeButtons();
		makeTextAreas();

	}
	
	/**
	 * Method used to set up the sub panes for the root node
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
		

		root.getChildren().add(subPane);
		subPane.getChildren().addAll(leftVBox,rightVBox);
		
	}

	/**
	 * Method used to make the labels for this page.
	 */
	private void makeLabels()
	{
		root.getChildren().add(0,getTitle());
		
		descriptionLabel = new Label("Access a list of Common mental disorders and Learn Information about them.");
		descriptionLabel.setFont(new Font("Arial",20));
		descriptionLabel.setTextFill(Color.BLACK);
		root.getChildren().add(1,descriptionLabel);
		
		disordersLabel = new Label("List of Common Mental Disorders.");
		disordersLabel.setFont(new Font("Arial",15));
		disordersLabel.setTextFill(Color.BLACK);
		leftVBox.getChildren().add(disordersLabel);

		infoLabel = new Label("Information about Chosen Mental Disorder");
		infoLabel.setFont(new Font("Arial",15));
		infoLabel.setTextFill(Color.BLACK);
		rightVBox.getChildren().add(infoLabel);		
	}
	
	/**
	 * Method used to make the buttons for this screen.
	 */
	private void makeButtons()
	{
		backButton = new Button();
		backButton.setText("Main Menu");
		
		backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			public void handle(MouseEvent event)
			{
				cleanUpScene(); 
				getGUIManager().moveToMainMenu();
			}
		});
		
		leftVBox.getChildren().add(backButton);
	}
	
	/**
	 * Method used to make the List of Disorders
	 */
	private void makeDisorderList()
	{
		disorderList = new ListView<String>();
		disorderList.setOrientation(Orientation.VERTICAL);
		disorderList.setMaxSize(400, 350);
		disorderList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		leftVBox.getChildren().add(disorderList);
		
		for(Disorder d: disorderLibrary.getDisorders())
		{
			disorderList.getItems().add(d.getName());
		}
		
		
		disorderList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
			{
				changeDisorderSelected(newValue);
			}
		});
		
		
	}
	
	/**
	 * Method used to make the text areas used to display disorder information
	 */
	private void makeTextAreas()
	{
		infoArea = new TextArea();
		infoArea.setEditable(false);
		infoArea.setMaxSize(300,750);
		infoArea.setMaxHeight(750);
		infoArea.setWrapText(true);
		
		rightVBox.getChildren().add(infoArea);
		
		changeDisorderSelected(disorderLibrary.getDisorders().get(0).getName());
	}
	
	/**
	 * Method used to change the information displayed to the 
	 * information of the newly selected disorder. 
	 * @param name The name of the disorder selected.
	 */
	private void changeDisorderSelected(String name)
	{
		for(Disorder d: disorderLibrary.getDisorders())
		{
			if(d.getName() == name) 
			{
				String newText = "Name: " + name + "\n\n";
				newText += "Description: " + d.getDescription()+ "\n\n";
				newText += "Symptoms: \n";
				for(String s: d.getSymptoms())
				{
					newText += s + "\n";
				}
				newText += "\n";
				newText += "Tips: \n";
				for(String s: d.getTips())
				{
					newText += s + "\n";
				}
				
				infoArea.setText(newText);
				return;
				
			}
		}
	}
}
