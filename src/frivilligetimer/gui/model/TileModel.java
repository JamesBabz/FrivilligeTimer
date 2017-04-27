/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Volunteer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James
 */
public class TileModel
{

    private static TileModel INSTANCE;
    private ObservableList<Volunteer> allVolunteers;

    public static synchronized TileModel getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new TileModel();
        }
        return INSTANCE;
    }

    private TileModel()
    {
        allVolunteers = FXCollections.observableArrayList();
    }

    private void addVolunteerTile(){
        
    }
    
    public ObservableList<Volunteer> getAllVolunteers()
    {
        return allVolunteers;
    }
}
