/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.ImageManager;
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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView imgVolunteer;

    private VolunteerModel model;
    private ImageManager iManager;
    private ViewHandler viewHandler;
    private Stage stage;
    private final FileChooser fileChooser = new FileChooser();
    private File file;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setStartImgVolunteer();
        validateData();
        viewHandler.ReplaceFirstLetterInField(txtFirstName, txtLastName);
    }


    public AddVolunteerController()
    {
                model = VolunteerModel.getInstance();
                 viewHandler = new ViewHandler(stage);
        try
        {
            iManager = new ImageManager();
        } catch (IOException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en fejl", ex.getMessage());
        } catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
        }
        
    }
    
        private void setStartImgVolunteer()
    {
        Image image = new Image("/frivilligetimer/gui/image/profile-placeholder.png");
        imgVolunteer.setImage(image);
    }
    
    

    @FXML
    private void addVolunteer()
    {
        viewHandler.setErrorRedLines(txtFirstName, txtLastName, txtEmail);
        if(viewHandler.getErrorRedLines() == 0)
        {
            addVolunteerToDB();
        }
    }

    private void addVolunteerToDB()
    {
        try
        {
            Volunteer volunteer = new Volunteer(0, txtFirstName.getText(), txtLastName.getText(),txtEmail.getText(), txtPhoneNumber.getText(), "", "", null, null);
            
            {
                model.addVolunteer(volunteer);
                viewHandler.closeWindow(stage, txtEmail);
            }
            
            if (file != null)
            {
                iManager.updateImage(volunteer, file.getAbsolutePath());
            }
            
        } catch (SQLException | IOException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en fejl", ex.getMessage());
        }
    }

    @FXML
    private void cancel()
    {
        viewHandler.closeWindow(stage, txtEmail);
    }

    @FXML
    private void handleBrowseImage(ActionEvent event)
    {
        stage = (Stage) txtFirstName.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
        Image img = new Image("file:" + file.getAbsolutePath());
        imgVolunteer.setImage(img);

    }

    private static String firstName = "";
    private static String lastName = "";
    private static String eMail = "";
    private static String phoneNumber = "";

    private void validateData()
    {
        txtFirstName.textProperty().addListener((observable, oldVal, newVal) ->
        {
            if (Pattern.matches("[^0-9]+", newVal) || newVal.isEmpty())
            {
                firstName = newVal;
            }
            txtFirstName.setText(firstName);
        });

        txtLastName.textProperty().addListener((observable, oldVal, newVal) ->
        {
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
