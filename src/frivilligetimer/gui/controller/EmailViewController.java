/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.GuildModel;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class EmailViewController implements Initializable
{

    private GuildModel gModel;
    private Guild selGuild;
    private HashMap<String, String> volHash;
    @FXML
    private TilePane tPane;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private CheckBox theAllCheckBox;
    @FXML
    private TextArea allEmails;

    public EmailViewController()
    {
        gModel = GuildModel.getInstance();
        volHash = new HashMap<>();
//        ------- Til test -------
//        selGuild = gModel.getAllGuilds().get(0);

        selGuild = gModel.getSelectedGuild();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tPane.prefWidthProperty().bind(anchorPane.widthProperty());
        createCheckBoxes();
    }

    private void createCheckBoxes()
    {
        ArrayList<CheckBox> cBoxList = new ArrayList<>();
        for (Volunteer volunteer : selGuild.getVolunteers())
        {
            volHash.put(volunteer.getFullName(), volunteer.getEmail());
            CheckBox cBox = new CheckBox(volunteer.getFullName());
            addEventHandlerForCheckBox(cBox);
            cBox.setSelected(true);
            updateText(true, volunteer.getFullName());
            cBoxList.add(cBox);
        }
        cBoxList.sort((CheckBox t, CheckBox t1) -> t.getText().compareTo(t1.getText()));
        tPane.getChildren().addAll(cBoxList);
    }

    private void addEventHandlerForCheckBox(CheckBox cBox)
    {
        cBox.setOnAction((ActionEvent event)
                -> 
                {
                    CheckBox cBox1 = (CheckBox) event.getSource();
                    boolean checked = cBox1.isSelected();
                    String name = cBox1.getText();
                    updateText(checked, name);
        });
    }

    @FXML
    private void handleTheAllCheckBox()
    {
        allEmails.setText("");
        if (theAllCheckBox.isSelected())
        {
            for (Volunteer volunteer : selGuild.getVolunteers())
            {
                updateText(true, volunteer.getFullName());
            }

        }

        for (Node node : tPane.getChildren())
        {
            CheckBox cBox = (CheckBox) node;
            cBox.setSelected(theAllCheckBox.isSelected());
        }

    }

    private void updateText(boolean checked, String name)
    {
        String email = volHash.get(name);

        if (checked)
        {
            if (allEmails.getText().isEmpty())
            {
                allEmails.setText(email);
            }
            else
            {
                allEmails.setText(allEmails.getText() + ", " + email);
            }
        }
        else if (allEmails.getText().contains(","))
        {
            allEmails.setText(allEmails.getText().replace(", " + email, ""));
            allEmails.setText(allEmails.getText().replace(email + ", ", ""));
        }
        else
        {
            allEmails.setText("");
            theAllCheckBox.setSelected(false);
        }
    }

    @FXML
    private void handleSendMail()
    {
        if (!allEmails.getText().isEmpty())
        {
            sendMail();
        }
        else
        {
            ViewGenerator vg = new ViewGenerator((Stage) tPane.getScene().getWindow());
            vg.showAlertBox(Alert.AlertType.WARNING, "Ingen Modtagere", "Der er ikke valgt nogen modtagere",
                    "Vælg venligst én eller flere modtagere fra listen over for at sende en mail");
        }
    }

    private void sendMail()
    {
        URI uri = null;
        String mails = allEmails.getText();
        mails = mails.replace(" ", "");
        if (Desktop.isDesktopSupported())
        {
            try
            {
                uri = new URI("mailto:" + mails);
                Desktop.getDesktop().browse(uri);
            }
            catch (URISyntaxException | IOException ex)
            {
                ViewGenerator vg = new ViewGenerator((Stage) tPane.getScene().getWindow());
                vg.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en fejl under åbning af mailprogram",
                        "Der gik noget galt under oprettelse af forbindelsen til dit standard mailprogram. "
                        + "Prøv igen eller marker dine emails og copier dem over i dit mailprogram");
            }

        }
    }

}
