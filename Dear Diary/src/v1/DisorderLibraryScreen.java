package v1;


import java.util.ArrayList;
import java.util.HashMap;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
public class DisorderLibraryScreen extends SceneHandler {

	private VBox mainPane;
	private HBox subPane; 
	private VBox leftVBox; 
	private VBox rightVBox; 
	private TextArea displayTextArea; 
	private ListView<String> disordersView; 
	private DisorderLibrary disorderLibrary = new DisorderLibrary(); 
	
	public DisorderLibraryScreen(GUIManager manager) {
		super(manager);
	
	}

	@Override
	protected void prepareScene() 
	{
		mainPane = new VBox();
		mainPane.setAlignment(Pos.TOP_CENTER);
		mainPane.setPadding(new Insets(10,10,10,10));
		mainPane.setStyle("-fx-background-color : GREY");
		Scene scene = new Scene(mainPane);
		setScene(scene);
		
		
		makeBoxes(); 
		makeDisorderView(); 
	}
	
	private void makeBoxes()
	{
		subPane = new HBox(); 
		leftVBox = new VBox();
		rightVBox = new VBox(); 
		
		subPane.setAlignment(Pos.TOP_CENTER);
		leftVBox.setAlignment(Pos.TOP_CENTER);
		rightVBox.setAlignment(Pos.TOP_CENTER);
		
		subPane.setPadding(new Insets(10,10,10,10));
		leftVBox.setPadding(new Insets(10,10,10,10));
		rightVBox.setPadding(new Insets(10,10,10,10));
		
		subPane.setSpacing(20);
		leftVBox.setSpacing(20);
		rightVBox.setSpacing(20);
		
		mainPane.getChildren().add(subPane);
		subPane.getChildren().addAll(leftVBox,rightVBox);
	}
	
	
	private void makeDisorderView()
	{
		disordersView = new ListView<String>();
		disordersView.setOrientation(Orientation.HORIZONTAL);
		disordersView.setMaxSize(200,200);
		leftVBox.getChildren().add(disordersView);
		
		
		ArrayList<Disorder> disorders = disorderLibrary.getDisorders();
		
		for(Disorder d : disorders)
		{
			disordersView.getItems().add(d.getName());
		}
		
		disordersView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue)
			{
				
			}
			
		});
	}

}
