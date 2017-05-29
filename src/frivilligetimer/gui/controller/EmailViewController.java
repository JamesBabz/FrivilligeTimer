/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.GuildModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author James
 */
public class EmailViewController implements Initializable
{
    
    private GuildModel gModel;
    private Guild selGuild;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    public EmailViewController()
    {
        gModel = gModel.getInstance();
        selGuild = gModel.getSelectedGuild();
        for (Volunteer volunteer : selGuild.getVolunteers())
        {
            System.out.println(volunteer.getFullName());
        }
    }
    
    
    
}
