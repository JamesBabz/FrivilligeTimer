/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.bll;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Guild;
import frivilligetimer.be.Manager;
import frivilligetimer.be.Volunteer;
import frivilligetimer.dal.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * A class that holds data about the people.
 *
 * @author Simon Birkedal, Stephan Fuhlendorff, Thomas Hansen & Jacob Enemark
 */
public class VolunteerManager
{
    DBManager dbManager;

    /**
     * The constuctor for the class.
     * @throws IOException 
     */
    public VolunteerManager() throws IOException, SQLException
    {
        dbManager = new DBManager();
    }
    /**
     * Gets all volunteers from DAO
     * @return a list of all volunteers
     */
    public List<Volunteer> getAllVolunteers()
    {
        return dbManager.getAllVolunteers();
    }
    /**
     * Gets all employees from DAO
     * @return a list of employees
     */
    public List<Employee> getAllEmployees()
    {
        return dbManager.getAllEmployees();
    }
    /**
     * Gets all managers from DAO
     * @return a list of managers
     */
    public List<Manager> getAllManagers()
    {
        return dbManager.getAllManagers();
    }
    
    /**
     * Gets all guilds from DAO
     * @return a list of guilds
     */
    public List<Guild> getAllGuilds()
    {
        return dbManager.getAllGuilds();
    }
    
    
}
