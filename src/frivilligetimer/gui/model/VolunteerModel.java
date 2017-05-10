/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.ImageManager;
import frivilligetimer.bll.VolunteerManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thomas
 */
public class VolunteerModel
{

    public Volunteer selectedVolunteer;

    VolunteerManager manager;

    private static VolunteerModel instance;

    private final ObservableList<Volunteer> allVolunteers;

    public static VolunteerModel getInstance()
    {
        if (instance == null)
        {
            instance = new VolunteerModel();
        }
        return instance;
    }

    /**
     * The default contructor
     */
    private VolunteerModel()
    {
        try
        {
            manager = new VolunteerManager();
        } catch (IOException ex)
        {
            Logger.getLogger(VolunteerModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(VolunteerModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        allVolunteers = FXCollections.observableArrayList();
    }

    /**
     * Gets all volunteers in the tableview "Frivillige"
     *
     * @return a list of all volunteers
     */
    public ObservableList<Volunteer> getAllVolunteersForTable()
    {
        allVolunteers.clear();
        for (Volunteer volunteer : manager.getAllVolunteers())
        {
            allVolunteers.add(volunteer);
        }
        return allVolunteers;
    }

    public void addVolunteer(Volunteer volunteer) throws SQLException
    {
        allVolunteers.add(volunteer);
        manager.addVolunteer(volunteer);

    }
    
    public void deleteVolunteer(Volunteer volunteer)
    {
        allVolunteers.remove(volunteer);
        manager.deleteVolunteer(volunteer);
    }
    
    public Volunteer getSelectedVolunteer() {
        return selectedVolunteer;
    }

    public void setSelectedVolunteer(Volunteer selectedVolunteer) {
        this.selectedVolunteer = selectedVolunteer;
    }
    
    public void editVolunteer(Volunteer volunteer) throws SQLException
    {
       manager.updateVolunteer(selectedVolunteer);
    }

   


}
