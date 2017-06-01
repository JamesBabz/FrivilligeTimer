/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.gui.model.GuildModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class EditGuildController implements Initializable
{

    @FXML
    private TextField txtLaug;

    private GuildModel model;
    private Guild guild;
    private ViewHandler viewHandler;
    
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = GuildModel.getInstance();
        guild = model.getSelectedGuild();
        viewHandler = new ViewHandler(stage);

        model.getSelectedGuild();

        getCurrentInfo();
    }

    private void getCurrentInfo()
    {
        txtLaug.setText(guild.getName());

    }

    @FXML
    private void handleUpdate()
    {
        if(!txtLaug.getText().isEmpty())
        {
        guild.setName(txtLaug.getText());
        try
        {
            model.editGuild(guild);
        } catch (SQLException ex)
        {
              viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
        }
        
        viewHandler.closeWindow(stage, txtLaug);
        } else
        {
            txtLaug.setStyle("-fx-border-color: red");
        }
        
    }

    @FXML
    private void cancel()
    {
            viewHandler.closeWindow(stage, txtLaug);
    }
}
