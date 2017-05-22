/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.VolunteerManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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
    public Volunteer tileVolunteer;

    VolunteerManager manager;

    private static VolunteerModel instance;

    private final ObservableList<Volunteer> allActiveVolunteers;

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
        }
        catch (IOException ex)
        {
            Logger.getLogger(VolunteerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(VolunteerModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        allActiveVolunteers = FXCollections.observableArrayList();
    }

    /**
     * Gets all volunteers in the tableview "Frivillige"
     *
     * @return a list of all volunteers
     */
    public ObservableList<Volunteer> getAllVolunteersForTable()
    {
        allActiveVolunteers.clear();
        for (Volunteer volunteer : manager.getAllActiveVolunteers())
        {
            allActiveVolunteers.add(volunteer);
        }
        return allActiveVolunteers;
    }

    public void addVolunteer(Volunteer volunteer) throws SQLException
    {
        allActiveVolunteers.add(volunteer);
        manager.addVolunteer(volunteer);

    }

    public void deleteVolunteer(Volunteer volunteer)
    {
        allActiveVolunteers.remove(volunteer);
        manager.deleteVolunteer(volunteer);
    }

    public void removeVolunteerFromAssignedGuild(Volunteer volunteer, Guild guild)
    {
        manager.removeVolunteerFromAssignedGuild(volunteer, guild);
    }

    public Volunteer getSelectedVolunteer()
    {
        return selectedVolunteer;
    }

    public void setSelectedVolunteer(Volunteer selectedVolunteer)
    {
        this.selectedVolunteer = selectedVolunteer;
    }

    public void editVolunteer(Volunteer volunteer) throws SQLException
    {
        manager.updateVolunteer(selectedVolunteer);
    }

    public Volunteer getTileVolunteer()
    {
        return tileVolunteer;
    }

    public void setTileVolunteer(Volunteer tileVolunteer)
    {
        this.tileVolunteer = tileVolunteer;
    }

    public void addHoursForVolunteer(int uid, Date date, int hours, int guildId) throws SQLException
    {
        manager.addHoursForVolunteer(uid, date, hours, guildId);
    }

    public int getTodaysHours(int id, Date date, int guildid) throws SQLException
    {
        return manager.getTodaysHours(id, date, guildid);
    }

    public void updateHoursForVolunteer(int id, Date date, int hours) throws SQLException
    {
        manager.updateHoursForVolunteers(id, date, hours);
    }

    public void updateNoteAndPrefForVolunteer(int id, String pref, String note) throws SQLException
    {
        manager.updateNoteAndPrefForVolunteer(id, pref, note);
    }

    public int getWorkedHoursInPeriodForVolunteer(Date from, Date to, int id) throws SQLException, IOException
    {
        return manager.getWorkedHoursInPeriodForVolunteer(from, to, id);
    }
}
