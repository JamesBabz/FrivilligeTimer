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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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

    private GuildModel model;
    private ViewHandler viewHandler;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = GuildModel.getInstance();
        viewHandler = new ViewHandler(stage);
        viewHandler.ReplaceFirstLetterInField(txtLaug);
    }

    @FXML
    private void addGuild()
    {
        Guild guild = new Guild(txtLaug.getText());

        if (!txtLaug.getText().isEmpty())
        {

            addGuildToDB(guild);
            viewHandler.closeWindow(stage, txtLaug);
        } else
        {
            txtLaug.setStyle("-fx-border-color: red");
        }
    }

    private void addGuildToDB(Guild guild)
    {
        try
        {
            model.addGuild(guild);
        } catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
        }
    }

    @FXML
    private void cancel()
    {
        viewHandler.closeWindow(stage, txtLaug);
    }

}
