/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.VolunteerManager;
import frivilligetimer.gui.model.VolunteerModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Jacob Enemark
 */
public class EditVolunteerController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNummer;

     private VolunteerManager manager;
     private VolunteerModel model;
     private Volunteer volunteer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = VolunteerModel.getInstance();
        volunteer = model.getSelectedVolunteer();
        
        model.getSelectedVolunteer();
        
        
       getCurrentInfo();
    }    

    private void getCurrentInfo()
    {
        txtFirstName.setText(volunteer.getFirstName());
        txtLastName.setText(volunteer.getLastName());
        txtEmail.setText(volunteer.getEmail());
        txtPhoneNummer.setText(volunteer.getPhoneNum());
    }
    
    @FXML
    private void handleUpdate() {
        volunteer.setFirstName(txtFirstName.getText());
        volunteer.setLastName(txtLastName.getText());
        volunteer.setEmail(txtEmail.getText());
        volunteer.setPhoneNum(txtPhoneNummer.getText());
        try
        {
            model.editVolunteer(volunteer);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EditVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
    }

    
    
}
