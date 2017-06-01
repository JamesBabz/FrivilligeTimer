/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.ImageManager;
import frivilligetimer.bll.VolunteerManager;
import frivilligetimer.gui.model.VolunteerCellModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    private Button btnBrowseImage;
    @FXML
    private ImageView imgVolunteer;

    private VolunteerModel model;
    private ImageManager iManager;
    private ViewGenerator viewGenerator;
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
            iManager = new ImageManager();
        } catch (IOException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(AddVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Image image = new Image("/frivilligetimer/gui/image/profile-placeholder.png");
        imgVolunteer.setImage(image);
        validateData();
        viewGenerator = new ViewGenerator(stage);

    }

    @FXML
    private void addVolunteer()
    {
        
        HashMap<TextField, Integer> list = new HashMap<>();
        txtFirstName.setStyle("-fx-border-color: #0000");
        txtLastName.setStyle("-fx-border-color: #0000");
        txtEmail.setStyle("-fx-border-color: #0000");
        

        if (txtFirstName.getText().isEmpty())
        {
            list.put(txtFirstName, 0);
        }
        if (txtLastName.getText().isEmpty())
        {
            list.put(txtLastName, 0);
        }
        if (txtEmail.getText().isEmpty())
        {
            list.put(txtEmail, 0);
        }

        if (list.isEmpty())
        {

            addVolunteerToDB();
        } else
        {
            for (Map.Entry<TextField, Integer> entry : list.entrySet())
            {
                if(entry.getValue() == 0)
                {
                    entry.getKey().setStyle("-fx-border-color : red");
                } 
            }
        }
        
    }

    private void addVolunteerToDB()
    {
        try
        {
            Volunteer volunteer = new Volunteer(0, txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNumber.getText(), "", "", null);
            
            {
                model.addVolunteer(volunteer);
                cancel();
            }
            
            if (file != null)
            {
                iManager.updateImage(volunteer, file.getAbsolutePath());
            }
            
        } catch (SQLException | IOException ex)
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
