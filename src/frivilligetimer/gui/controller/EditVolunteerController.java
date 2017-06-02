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
import javafx.embed.swing.SwingFXUtils;
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
public class EditVolunteerController implements Initializable
{

    private VolunteerModel model;
    private Volunteer volunteer;
    private ViewHandler viewHandler;
    private Stage stage;
    private final FileChooser fileChooser = new FileChooser();
    private File file;

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNummer;
    @FXML
    private ImageView imgVolunteer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = VolunteerModel.getInstance();
        volunteer = model.getSelectedVolunteer();
        viewHandler = new ViewHandler(stage);

        model.getSelectedVolunteer();

        getCurrentInfo();
        setImageOfVolunteer(volunteer);
        viewHandler = new ViewHandler(stage);
        viewHandler.ReplaceFirstLetterInField(txtFirstName, txtLastName);

    }

    private void setImageOfVolunteer(Volunteer volunteer)
    {
        if (volunteer.getImage() == null)
        {
            Image image = new Image("/frivilligetimer/gui/image/profile-placeholder.png");
            imgVolunteer.setImage(image);

        }
        else
        {
            Image image = SwingFXUtils.toFXImage(volunteer.getImage(), null);
            imgVolunteer.setImage(image);
        }
    }

    private void getCurrentInfo()
    {
        txtFirstName.setText(volunteer.getFirstName());
        txtLastName.setText(volunteer.getLastName());
        txtEmail.setText(volunteer.getEmail());
        txtPhoneNummer.setText(volunteer.getPhoneNum());
    }

    @FXML
    private void handleUpdate()
    {
        viewHandler.setErrorRedLines(txtFirstName, txtLastName, txtEmail);
        if (viewHandler.getErrorRedLines() == 0)
        {
            volunteer.setFirstName(txtFirstName.getText());
            volunteer.setLastName(txtLastName.getText());
            volunteer.setEmail(txtEmail.getText());
            volunteer.setPhoneNum(txtPhoneNummer.getText());
            updateVolunteerInDB();
            viewHandler.closeWindow(stage, txtEmail);
        }
    }

    private void updateVolunteerInDB()
    {
        try
        {
            model.editVolunteer(volunteer);

            if (file != null)
            {
                ImageManager iManager = new ImageManager();

                iManager.updateImage(volunteer, file.getAbsolutePath());
            }
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
        }
        catch (IOException ex)
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

}
