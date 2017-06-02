/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.VolunteerManager;
import frivilligetimer.gui.controller.ViewHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

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
    private final ObservableList<Volunteer> allInactiveVoluenteers;
    private ObservableList<Volunteer> searchedVolunteer;
    private ObservableList<Volunteer> allVolunteerInCurrentView;
    private final ObservableList<String> volunteerNames;
    private Stage stage;
    private ViewHandler viewHandler;

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
        viewHandler = new ViewHandler(stage);
        try
        {
            manager = new VolunteerManager();
        }
        catch (IOException | SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }

        allActiveVolunteers = FXCollections.observableArrayList();
        searchedVolunteer = FXCollections.observableArrayList();
        allVolunteerInCurrentView = FXCollections.observableArrayList();
        allInactiveVoluenteers = FXCollections.observableArrayList();
        volunteerNames = FXCollections.observableArrayList();
    }

    /**
     * Gets all volunteers in the tableview "Frivillige"
     *
     * @return a list of all volunteers
     */
    public ObservableList<Volunteer> getAllVolunteersForTable(boolean sorted)
    {
        allActiveVolunteers.clear();
        List<Volunteer> loopList = manager.getAllActiveVolunteers();
        if (sorted)
        {
            loopList.sort((Volunteer t, Volunteer t1) -> t.getFirstName().compareTo(t1.getFirstName()));
        }
        for (Volunteer volunteer : loopList)
        {
            allActiveVolunteers.add(volunteer);
        }

        return allActiveVolunteers;
    }

    public ObservableList<Volunteer> getAllInactiveVolunteers()
    {
        allInactiveVoluenteers.clear();
        for (Volunteer volunteer : manager.getAllInactiveVolunteers())
        {
            allInactiveVoluenteers.add(volunteer);
        }
        return allInactiveVoluenteers;
    }

    public void addVolunteer(Volunteer volunteer) throws SQLException
    {
        allActiveVolunteers.add(volunteer);
        manager.addVolunteer(volunteer);

    }

    public void deleteInactiveVolunteer(Volunteer volunteer)
    {
        try
        {
            manager.deleteInactiveVolunteer(volunteer);
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }
    }

    public void deleteVolunteer(Volunteer volunteer)
    {
        allActiveVolunteers.remove(volunteer);
        try
        {
            manager.deleteVolunteer(volunteer);
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }
    }

    public void activeteVolunteer(Volunteer volunteer)
    {
        allInactiveVoluenteers.remove(volunteer);
        try
        {
            manager.activeteVolunteer(volunteer);
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }
    }

    public void deleteInactiveVolunteers()
    {

        try
        {
            manager.deleteInactiveVolunteers();
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }
    }

    public void removeVolunteerFromAssignedGuild(Volunteer volunteer, Guild guild)
    {
        try
        {
            manager.removeVolunteerFromAssignedGuild(volunteer, guild);
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }
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

    public void updateHoursForVolunteer(int id, Date date, int hours, int guildId) throws SQLException
    {
        manager.updateHoursForVolunteers(id, date, hours, guildId);
    }

    public void updateNoteAndPrefForVolunteer(int id, String pref, String note) throws SQLException
    {
        manager.updateNoteAndPrefForVolunteer(id, pref, note);
    }

    public TreeMap<java.sql.Date, Integer> getWorkedHoursInPeriodForVolunteer(Date from, Date to, int id, int guildid) throws SQLException, IOException
    {
        return manager.getWorkedHoursInPeriodForVolunteer(from, to, id, guildid);
    }

    public ObservableList<Volunteer> getSearchedVolunteers()
    {
        return searchedVolunteer;
    }

    public ObservableList<Volunteer> getAllVolunteerInCurrentView()
    {
        return allVolunteerInCurrentView;
    }

    public void setSearchedVolunteer(ObservableList<Volunteer> searchedVolunteer)
    {
        this.searchedVolunteer = searchedVolunteer;
    }

    public void setAllVolunteerInCurrentView(ObservableList<Volunteer> allVolunteerInCurrentView)
    {
        this.allVolunteerInCurrentView = allVolunteerInCurrentView;
    }

    public ObservableList<String> getSearchedVolunteerNames()
    {
        volunteerNames.clear();

        for (Volunteer volunteer : getSearchedVolunteers())
        {
            volunteerNames.add(volunteer.getFullName());
        }
        return volunteerNames;
    }
}
