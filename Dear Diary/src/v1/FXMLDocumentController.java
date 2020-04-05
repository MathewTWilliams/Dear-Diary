/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package v1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author julia
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    Label label;
    @FXML
    Button submitBtn;
    @FXML
    TextField statSubmission;
    @FXML
    ChoiceBox trackerBox;
    @FXML
    Button mainMenuBtn;
    
    @FXML
    private void submitButtonHit(){
        System.out.println("Clicked");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
