package frivilligetimer.gui.controller;

import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.StaffModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sBirke
 */
public class AddVolunteerHoursController implements Initializable
{

    private Volunteer volunteer;
    private VolunteerModel model;
    private StaffModel staffModel;
    private boolean isHourSet = false;

    @FXML
    private TextField txtHours;
    @FXML
    private Button btnDecrease;
    @FXML
    private Button btnIncrease;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnClose;
    @FXML
    private Label lblName;
    @FXML
    private Label lblMail;
    @FXML
    private Label lblNumber;
    @FXML
    private TextField txtPref;
    @FXML
    private TextArea txtNote;
    @FXML
    private Text lblPref;
    @FXML
    private Text lblNote;
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView imgV;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = VolunteerModel.getInstance();
        staffModel = StaffModel.getInstance();
        this.volunteer = model.getTileVolunteer();
        populateFields();

       

        // force the hour field to be numeric only
        txtHours.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
            {
                if (!newValue.matches("\\d*"))
                {
                    txtHours.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }

    private void populateFields()
    {
         if (staffModel.level == 1)
        {
            txtPref.setText(volunteer.getPreference());
            txtNote.setText(volunteer.getNote());
        }
        else
        {
            pane.getChildren().remove(txtPref);
            pane.getChildren().remove(txtNote);
            pane.getChildren().remove(lblPref);
            pane.getChildren().remove(lblNote);
            pane.setPrefHeight(450);
        }
        lblName.setText(volunteer.getFullName());
        lblMail.setText(volunteer.getEmail());
        lblNumber.setText(volunteer.getPhoneNum());
        setImage();
        populateHours();
    }

    private void setImage()
    {
        Image image;
        if (volunteer.getImage() != null)
        {
            image = SwingFXUtils.toFXImage(volunteer.getImage(), null);
        }
        else
        {
            image = null;
        }
        imgV.setImage(image);
    }

    @FXML
    private void handleSubtractHour()
    {
        int hours;
        if (txtHours.getText().equals(""))
        {
            hours = 0;
            txtHours.setText("" + hours);
        } else{
        hours = Integer.parseInt(txtHours.getText());
        }
        if (hours != 0)
        {
            hours--;
            txtHours.setText("" + hours);
        }
    }

    @FXML
    private void handleAddHour()
    {
        int hours;
        if (txtHours.getText().equals(""))
        {
            hours = 0;
        }
        else
        {
            hours = Integer.parseInt(txtHours.getText());
            hours++;
        }
        txtHours.setText("" + hours);
    }

    @FXML
    private void handleSave()
    {
        try
        {
            saveToDB();
        }
        catch (SQLException | NumberFormatException ex)
        {
            Logger.getLogger(AddVolunteerHoursController.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        close();
    }

    private void saveToDB() throws SQLException, NumberFormatException
    {
        int id = volunteer.getId();
        if (staffModel.level == 1)
        {
            String pref = txtPref.getText();
            String note = txtNote.getText();
            model.updateNoteAndPrefForVolunteer(id, pref, note);
            volunteer.setPreference(pref);
            volunteer.setNote(note);
        }
        
        if (isHourSet)
        {
            model.updateHoursForVolunteer(id, new Date(), Integer.parseInt(txtHours.getText()));
        }
        else
        {
            model.addHoursForVolunteer(id, new Date(), Integer.parseInt(txtHours.getText()));
        }
    }

    @FXML
    private void handleClose()
    {
        close();
    }

    private void close()
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    private void populateHours()
    {
        try
        {
            int hours = model.getTodaysHours(volunteer.getId());
            if (hours >= 0)
            {
                isHourSet = true;
            }
            else
            {
                hours = -1;
            }
            txtHours.setText("" + hours);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AddVolunteerHoursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
