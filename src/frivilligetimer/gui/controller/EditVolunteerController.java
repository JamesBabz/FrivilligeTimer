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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class EditVolunteerController implements Initializable
{

    private VolunteerManager manager;
    private VolunteerModel model;
    private Volunteer volunteer;
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
    private Button btnBrowseImage;
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

        model.getSelectedVolunteer();

        getCurrentInfo();
        setImageOfVolunteer(volunteer);

    }

    private void setImageOfVolunteer(Volunteer volunteer)
    {
        if (volunteer.getImage() == null)
        {
            Image image = new Image("/frivilligetimer/gui/image/profile-placeholder.png");
            imgVolunteer.setImage(image);

        } else
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
        volunteer.setFirstName(txtFirstName.getText());
        volunteer.setLastName(txtLastName.getText());
        volunteer.setEmail(txtEmail.getText());
        volunteer.setPhoneNum(txtPhoneNummer.getText());
        try
        {
            model.editVolunteer(volunteer);

            if (file != null)
            {
                ImageManager iManager = new ImageManager();

                iManager.updateImage(volunteer, file.getAbsolutePath());
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(EditVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(EditVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        cancel();
    }

    @FXML
    private void cancel()
    {
        Stage stage = (Stage) txtFirstName.getScene().getWindow();
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

}
