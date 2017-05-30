package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.GuildModel;
import frivilligetimer.gui.model.StaffModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    private VolunteerModel volunteerModel;
    private StaffModel staffModel;
    private GuildModel guildModel;
    private boolean isHourSet;
    private Guild selectedGuild;
    private final ToggleGroup group;
    private final ViewGenerator vg;

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
    @FXML
    private VBox rbContainer;
    @FXML
    private Label lblguildError;
    @FXML
    private Label lblHours;

    public AddVolunteerHoursController()
    {
        this.isHourSet = false;
        this.group = new ToggleGroup();
        this.vg = new ViewGenerator((Stage) lblName.getScene().getWindow());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        volunteerModel = VolunteerModel.getInstance();
        staffModel = StaffModel.getInstance();
        guildModel = GuildModel.getInstance();
        this.volunteer = volunteerModel.getTileVolunteer();
        selectedGuild = guildModel.getSelectedGuild();
        populateFields();

        initializeListeners();

    }

    private void initializeListeners()
    {
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
        // updates hours based on guild selected
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
        {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue)
            {
                selectedGuild = (Guild) newValue.getUserData();
                populateHours();
            }
        });

    }

    /**
     * Populates all the fields when the view is opened
     */
    private void populateFields()
    {
        checkIfManagerIsLoggedIn();
        lblName.setText(volunteer.getFullName());
        lblMail.setText(volunteer.getEmail());
        lblNumber.setText(volunteer.getPhoneNum());

        checkIfVolunteerIsInAGuild();
        setImage();
        if (selectedGuild != null)
        {
            populateHours();
        }
    }

    /**
     * If the volunteer is not in a guild, hours can not be added
     */
    private void checkIfVolunteerIsInAGuild()
    {
        if (selectedGuild == null && createRadioButtons())
        {
            txtHours.setDisable(true);
            btnIncrease.setDisable(true);
            btnDecrease.setDisable(true);
            btnSave.setDisable(true);
            lblguildError.setVisible(true);
            lblHours.setVisible(false);
        }
        else
        {
            lblguildError.setVisible(false);
            lblHours.setVisible(true);
        }
    }

    /**
     * If the manager is logged in there should be more options
     */
    private void checkIfManagerIsLoggedIn()
    {
        if (staffModel.getLevel() == 1)
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
    }

    /**
     * Gets all the guilds the volunteers is a part of
     *
     * @return - Returns a list of all the guilds
     */
    private List<Guild> getAllGuildsForVolunteer()
    {
        List<Guild> guilds = new ArrayList<>();
        for (Guild guild : guildModel.getAllGuildsForTable())
        {
            for (Volunteer vol : guild.getVolunteers())
            {
                if (vol.getId() == volunteer.getId())
                {
                    guilds.add(guild);
                }
            }
        }
        return guilds;
    }

    /**
     * Creates radiobuttons for each guild the volunteer is part of
     *
     * @return - Returns if succesful
     */
    private boolean createRadioButtons()
    {
        List<Guild> guilds = getAllGuildsForVolunteer();
        int heightChange = 0;
        boolean first = true;
        for (Guild guild : guilds)
        {
            RadioButton rb = new RadioButton(guild.getName());

            rb.setUserData(guild);
            rb.setToggleGroup(group);
            rb.setFont(Font.font(32));
            rbContainer.getChildren().add(rb);
            heightChange += 50;
            if (first)
            {
                rb.setSelected(true);
                first = false;
                selectedGuild = (Guild) group.getSelectedToggle().getUserData();
            }
        }

        pane.setPrefHeight(pane.getPrefHeight() + heightChange);
        return first;
    }

    /**
     * Sets the image of the Volunteer
     */
    private void setImage()
    {
        Image image;
        if (volunteer.getImage() != null)
        {
            image = SwingFXUtils.toFXImage(volunteer.getImage(), null);
        }
        else
        {
            image = new Image("frivilligetimer/gui/image/profile-placeholder.jpg");
        }
        imgV.setImage(image);
    }

    @FXML
    private void handleSubtractHour()
    {
        int hours;
        //If the hour field is empty, put a 0
        if (txtHours.getText().equals(""))
        {
            hours = 0;
            txtHours.setText("" + hours);
        }
        //Else get the number from the field
        else
        {
            hours = Integer.parseInt(txtHours.getText());
        }
        //If its not 0 subtract 1
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
        //If the hour field is empty, put a 1
        if (txtHours.getText().equals(""))
        {
            hours = 1;
        }
        //Else add one hour to current number
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
        catch (SQLException ex)
        {
            vg.showAlertBox(Alert.AlertType.ERROR, "Database Fejl", "Der skete en fejl med forbindelsen til databasem", ex.getMessage());
        }
        close();
    }

    /**
     * Saves the input to the database
     */
    private void saveToDB() throws SQLException
    {
        int id = volunteer.getId();

        int guildId = selectedGuild.getId();
        if (staffModel.getLevel() == 1)
        {
            String pref = txtPref.getText();
            String note = txtNote.getText();

            volunteerModel.updateNoteAndPrefForVolunteer(id, pref, note);

            volunteer.setPreference(pref);
            volunteer.setNote(note);
        }

        if (isHourSet)
        {
            volunteerModel.updateHoursForVolunteer(id, new Date(), Integer.parseInt(txtHours.getText()));
        }
        else
        {
            volunteerModel.addHoursForVolunteer(id, new Date(), Integer.parseInt(txtHours.getText()), guildId);
        }
    }

    @FXML
    private void handleClose()
    {
        close();
    }

    /**
     * Closes current window
     */
    private void close()
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    /**
     * Checks the database if hours have been filled today
     * and puts them in the hour field if so
     */
    private void populateHours()
    {
        try
        {
            int hours;
            hours = volunteerModel.getTodaysHours(volunteer.getId(), new Date(), selectedGuild.getId());
            if (hours >= 0)
            {
                isHourSet = true;
            }
            else
            {
                isHourSet = false;
                hours = 0;
            }
            txtHours.setText("" + hours);
        }
        catch (SQLException ex)
        {
            vg.showAlertBox(Alert.AlertType.ERROR, "Database Fejl", "Der skete en fejl med forbindelsen til databasem", ex.getMessage());
        }
    }

}
