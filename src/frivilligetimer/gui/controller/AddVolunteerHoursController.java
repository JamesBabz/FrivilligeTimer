package frivilligetimer.gui.controller;

import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.VolunteerModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author sBirke
 */
public class AddVolunteerHoursController implements Initializable
{

    private Volunteer volunteer;
    private VolunteerModel model;

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
    private Text lblName;
    @FXML
    private Text lblMail;
    @FXML
    private Text lblNumber;
    @FXML
    private TextField txtPref;
    @FXML
    private TextArea txtNote;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        model = VolunteerModel.getInstance();
        this.volunteer = model.getTileVolunteer();
        lblName.setText(volunteer.getFullName());
        lblMail.setText(volunteer.getEmail());
        lblNumber.setText(volunteer.getPhoneNum());
        txtPref.setText(volunteer.getPreference());
        txtNote.setText(volunteer.getNote());
    }

    @FXML
    private void handleSubtractHour(ActionEvent event)
    {

    }

    @FXML
    private void handleAddHour(ActionEvent event)
    {
    }

    @FXML
    private void handleSave(ActionEvent event)
    {
    }

    @FXML
    private void handleClose(ActionEvent event)
    {
    }

}
