/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.bll;

import frivilligetimer.be.Volunteer;
import frivilligetimer.dal.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     *
     * @throws IOException
     */
    public VolunteerManager() throws IOException, SQLException
    {
        dbManager = new DBManager();
    }

    /**
     * Gets all volunteers from DAO
     *
     * @return a list of all volunteers
     */
    public List<Volunteer> getAllVolunteers()
    {
        return dbManager.getAllVolunteers();
    }

     public void addVolunteer(Volunteer volunteer) throws SQLException
    {
        dbManager.addVolunteer(volunteer);
    }
    
     public void deleteVolunteer(Volunteer volunteer)
     {
        try {
            dbManager.deleteVolunteer(volunteer);
        } catch (SQLException ex) {
            Logger.getLogger(VolunteerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    public void updateVolunteer(Volunteer volunteer) {
        dbManager.updateVolunteer(volunteer);
    }
}
