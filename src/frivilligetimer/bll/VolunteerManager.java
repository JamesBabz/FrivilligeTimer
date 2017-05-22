/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.bll;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.dal.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
    public List<Volunteer> getAllActiveVolunteers()
    {
        return dbManager.getAllActiveVolunteers();
    }

    public void addVolunteer(Volunteer volunteer) throws SQLException
    {
        dbManager.addVolunteer(volunteer);
    }

    public void deleteVolunteer(Volunteer volunteer)
    {
        try
        {
            dbManager.removeVolunteerFromGuilds(volunteer);
            dbManager.deleteVolunteer(volunteer);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(VolunteerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void removeVolunteerFromAssignedGuild(Volunteer volunteer, Guild guild)
     {
        try
        {
            dbManager.removeVolunteerFromAssignedGuild(volunteer, guild);
        } catch (SQLException ex)
        {
            Logger.getLogger(VolunteerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    public void updateVolunteer(Volunteer volunteer) throws SQLException
    {
        dbManager.updateVolunteer(volunteer);
    }

    public void addHoursForVolunteer(int uid, Date date, int hours, int guildId) throws SQLException
    {
        dbManager.addHoursForVolunteer(uid, date, hours, guildId);
    }

    public int getTodaysHours(int id, Date date, int guildid) throws SQLException
    {
        return dbManager.getTodaysHours(id, date, guildid);
    }

    public void updateHoursForVolunteers(int id, Date date, int hours) throws SQLException
    {
        dbManager.updateHoursForVolunteer(id, date, hours);
    }

    public void updateNoteAndPrefForVolunteer(int id, String pref, String note) throws SQLException
    {
        dbManager.updateNoteAndPrefForVolunteer(id, pref, note);
    }
    
    public int getWorkedHoursInPeriodForVolunteer(Date from, Date to, int id) throws SQLException, IOException{
        return dbManager.getWorkedHoursInPeriod(from, to, id, true);
    }
}
