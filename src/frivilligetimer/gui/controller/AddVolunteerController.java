/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.ImageManager;
import frivilligetimer.bll.VolunteerManager;
import frivilligetimer.gui.model.VolunteerModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jacob Enemark
 */
public class AddVolunteerController implements Initializable
{

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNumber;
    @FXML
    private Button btnBrowseImage;

    private VolunteerManager manager;
    private VolunteerModel model;
    private Stage stage;
    private final FileChooser fileChooser = new FileChooser();
    private File file;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        model = VolunteerModel.getInstance();
        try
        {
            manager = new VolunteerManager();
        }
        catch (IOException | SQLException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        validateData();
        
    }

    @FXML
    private void addVolunteer()
    {
        try
        {
            Volunteer volunteer = new Volunteer(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), null);

            model.addVolunteer(volunteer);

            ImageManager iManager = new ImageManager();

            iManager.updateImage(volunteer, file.getAbsolutePath());

            cancel();
        }
        catch (SQLException | IOException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void cancel()
    {
        stage = (Stage) txtFirstName.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleBrowseImage(ActionEvent event)
    {
        stage = (Stage) txtFirstName.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);

    }
    
    private static String firstName = "";
    private static String lastName = "";
    private static String eMail = "";
    private static String phoneNumber = "";


    private void validateData()
    {   
        txtFirstName.textProperty().addListener((observable, oldVal, newVal) -> {
            if (Pattern.matches("[^0-9]+", newVal) || newVal.isEmpty())
            {
                firstName = newVal;
            }
            txtFirstName.setText(firstName);
        });
        
        txtLastName.textProperty().addListener((observable, oldVal, newVal) -> {
            if (Pattern.matches("[^0-9]+", newVal) || newVal.isEmpty())
            {
                lastName = newVal;
            }
            txtLastName.setText(lastName);
        });
        
        txtEmail.textProperty().addListener((observable, oldVal, newVal) ->
        {
            if (Pattern.matches("[[\\w]+[@]?[\\w\\.]+]+", newVal) || newVal.isEmpty())
            {
                eMail = newVal;
            }
            txtEmail.setText(eMail);
        });
        
        txtPhoneNumber.textProperty().addListener((observable, oldVal, newVal) -> 
        {
            if (Pattern.matches("[0-9]+", newVal) || newVal.isEmpty())
            {
                phoneNumber = newVal;
            }
            txtPhoneNumber.setText(phoneNumber);
        });
    }

    
    
    
}
