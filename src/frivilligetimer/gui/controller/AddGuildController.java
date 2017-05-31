/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.bll.GuildManager;
import frivilligetimer.gui.model.GuildModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author thomas
 */
public class AddGuildController implements Initializable
{

    @FXML
    private TextField txtLaug;

    GuildModel model;
    GuildManager manager;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    public AddGuildController()
    {
        model = GuildModel.getInstance();
        try
        {
            manager = new GuildManager();
        }
        catch (IOException ex)
        {
            Logger.getLogger(AddGuildController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(AddGuildController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addGuild()
    {
        Guild guild = new Guild(txtLaug.getText());

        if (!txtLaug.getText().isEmpty())
        {

            try
            {
                model.addGuild(guild);
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AddGuildController.class.getName()).log(Level.SEVERE, null, ex);
            }

            cancel();
        }
        else
        {
            ViewGenerator vg = new ViewGenerator((Stage) txtLaug.getScene().getWindow());
            vg.showAlertBox(Alert.AlertType.INFORMATION, "Ingen information", "Udfyld laugets navn", "Når tekstfeltet er udfyldt, vil du kunne tilføje lauget");
        }

    }

    @FXML
    private void cancel()
    {
        Stage stage = (Stage) txtLaug.getScene().getWindow();
        stage.close();
    }

}
