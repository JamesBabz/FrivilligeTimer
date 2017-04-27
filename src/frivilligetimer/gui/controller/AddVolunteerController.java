/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.gui.model.VolunteerModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jacob Enemark
 */
public class AddVolunteerController implements Initializable {

    
     private VolunteerModel model;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNummer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        model = VolunteerModel.getInstance();    }    
    
    @FXML
    private void addVolunteer()
    {
        String fName = txtFirstName.getText();
        String email = txtEmail.getText();
        String lName = txtLastName.getText();        
        String pNumber = txtPhoneNummer.getText();
        
        model.addVolunteer(fName, email, lName, pNumber);
        
    }
    
    
    
    
    
}
