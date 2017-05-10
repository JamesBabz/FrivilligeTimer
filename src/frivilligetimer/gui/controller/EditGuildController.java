/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.bll.GuildManager;
import frivilligetimer.gui.model.GuildModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class EditGuildController implements Initializable {

    @FXML
    private TextField txtLaug;
    
    
    private GuildManager manager;
    private GuildModel model;
    private Guild guild;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = GuildModel.getInstance();
        guild = model.getSelectedGuild();
        
        model.getSelectedGuild();
        
        
       getCurrentInfo();
    }    
    
    private void getCurrentInfo()
    {
        txtLaug.setText(guild.getName());
 
    }
    
        @FXML
        private void handleUpdate() {
        guild.setName(txtLaug.getText());
        try
        {
            model.editGuild(guild);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EditVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    @FXML
    private void cancel(ActionEvent event) {
    }
}
