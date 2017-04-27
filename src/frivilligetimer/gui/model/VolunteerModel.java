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

/**
 *
 * @author thomas
 */
public class VolunteerModel
{
    VolunteerManager volunteerManager;
    
     private static VolunteerModel instance;

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
    }
    
    /**
     * Gets all volunteers from the VolunteerManager.
     * @return all the volunteers
     */
    public List<Volunteer> getAllVolunteers()
    {
       return volunteerManager.getAllVolunteers();
    }
    
    /**
     * Gets all employees from the manager
     * @return a list of all the employees
     */
    public List<Employee> getAllEmployees()
    {
        return volunteerManager.getAllEmployees();
    }
    
    /**
     * Gets all guilds from the manager
     * @return a list of all guilds
     */
    public List<Guild> getAllGuilds()
    {
        return volunteerManager.getAllGuilds();
    }
    
    
    
}
