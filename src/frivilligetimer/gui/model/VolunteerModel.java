/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.VolunteerManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
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

    VolunteerManager volunteerManager;

    private static VolunteerModel instance;

    private final ObservableList<Volunteer> allVolunteers;
    private final ObservableList<Employee> allEmployees;
    private final ObservableList<Guild> allGuilds;

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
            volunteerManager = new VolunteerManager();
        } catch (IOException ex)
        {
            Logger.getLogger(VolunteerModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(VolunteerModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        allVolunteers = FXCollections.observableArrayList();
        allEmployees = FXCollections.observableArrayList();
        allGuilds = FXCollections.observableArrayList();
        
    }

    /**
     * Sets all volunteers in the tableview "Frivillige"
     * @return a list of all volunteers
     */
    public ObservableList<Volunteer> getAllVolunteersForTable()
    {
        for (Volunteer volunteer : volunteerManager.getAllVolunteers())
        {
            allVolunteers.add(volunteer);
        }
            return allVolunteers;
    }

    /**
     * Sets all employees in the tableview "Tovholdere"
     * @return a list of all employees
     */
    public ObservableList<Employee> getAllGuildManagersForTable()
    {
        for (Employee employee : volunteerManager.getAllEmployees())
        {
            allEmployees.add(employee);
        }
        return allEmployees;
    }

    /**
     * Sets all guilds in tableview "laug"
     * @return a list of all guilds
     */
    public ObservableList<Guild> getAllGuildForTable()
    {
        for (Guild guild : volunteerManager.getAllGuilds())
        {
            allGuilds.add(guild);
        }
        return allGuilds;
    }

}
