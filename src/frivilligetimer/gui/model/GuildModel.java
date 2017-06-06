/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.GuildManager;
import frivilligetimer.bll.StaffManager;
import frivilligetimer.bll.VolunteerManager;
import frivilligetimer.gui.controller.ViewHandler;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author thomas
 */
public final class GuildModel
{

    public Guild selectedGuild;
    private static GuildModel instance;
    private GuildManager manager;
    private StaffManager staffManager;
    private VolunteerManager volunteerManager;
    private final ObservableList<Guild> allActiveGuilds;
    private final ObservableList<Volunteer> allVolunteers;
    private final ObservableList<Employee> allEmployees;
    private final ObservableList<String> guildNames;
    private final ObservableList<Volunteer> volunteersInCurrentGuild;
    private final ObservableList<Employee> employeesInCurrentGuild;
    private Stage stage;
    private ViewHandler viewHandler;

    public static GuildModel getInstance()
    {
        if (instance == null)
        {
            instance = new GuildModel();
        }
        return instance;
    }

    private GuildModel()
    {
        viewHandler = new ViewHandler(stage);
        try
        {
            manager = new GuildManager();
            volunteerManager = new VolunteerManager();
            staffManager = new StaffManager();
        } catch (IOException | SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }
        allActiveGuilds = FXCollections.observableArrayList();
        allVolunteers = FXCollections.observableArrayList();
        allEmployees = FXCollections.observableArrayList();
        guildNames = FXCollections.observableArrayList();
        volunteersInCurrentGuild = FXCollections.observableArrayList();
        employeesInCurrentGuild = FXCollections.observableArrayList();

        setAllGuilds();
        setAllVolunteers();
        setAllEmployees();
        populateGuildsWithVolunteers();
        populateGuildsWithEmployees();
        setAllGuildNames(true);

    }

    /**
     * Gets all guilds in tableview "laug"
     *
     * @param sorted - If the returned list should be sorted
     * @return a list of all guilds
     */
    public ObservableList<Guild> getAllGuildsForTable(boolean sorted)
    {
        if (sorted)
        {
            allActiveGuilds.sort((Guild t, Guild t1) -> t.getName().compareTo(t1.getName()));
        }
        return allActiveGuilds;
    }

    public List<Guild> getAllGuilds()
    {
        return manager.getAllGuilds();
    }

    public void setAllGuilds()
    {
        allActiveGuilds.clear();
        allActiveGuilds.addAll(manager.getAllActiveGuilds());
    }

    private ObservableList<Volunteer> getAllVolunteers()
    {
        return allVolunteers;
    }

    private void setAllVolunteers()
    {
        allVolunteers.clear();
        allVolunteers.addAll(volunteerManager.getAllActiveVolunteers());
    }

    private ObservableList<Employee> getAllEmployees()
    {
        return allEmployees;
    }

    private void setAllEmployees()
    {
        allEmployees.clear();
        allEmployees.addAll(staffManager.getAllEmployees());
    }

    public void addGuild(Guild guild) throws SQLException
    {
        allActiveGuilds.add(guild);
        manager.addGuild(guild);
    }

    public void deleteGuild(Guild guild)
    {
        allActiveGuilds.remove(guild);
        try
        {
            manager.deleteGuild(guild);
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Database fejl", "Databasen kunne ikke kontaktes", ex.getMessage());
        }
    }

    public void addVolunteerToGuild(Guild selectedGuild, Volunteer selectedVolunteer) throws SQLException
    {
        selectedGuild.addVolunteer(selectedVolunteer);
        manager.addVolunteerToGuild(selectedGuild.getId(), selectedVolunteer.getId());

    }

    public void setVolunteersInGuild(Guild guild)
    {

        volunteersInCurrentGuild.clear();
        volunteersInCurrentGuild.addAll(guild.getVolunteers());
    }

    public List<String> getEmployeesInguilds()
    {
        return manager.getEmployeesInGuild();
    }

    public List<String> getAllVolunteersInGuilds()
    {
        return manager.getVolunteersInGuild();
    }

    public ObservableList<Volunteer> getVolunteersInGuild()
    {
        return volunteersInCurrentGuild;
    }

    public void editGuild(Guild guild) throws SQLException
    {
        manager.updateGuild(selectedGuild);
    }

    public Guild getSelectedGuild()
    {
        return selectedGuild;
    }

    public void setSelectedGuild(Guild selectedGuild)
    {
        this.selectedGuild = selectedGuild;
    }

    /**
     * Gets the volunteers in each guild
     */
    public void populateGuildsWithVolunteers()
    {

        List<Volunteer> allVol;
        allVol = getAllVolunteers();
        for (String string : getAllVolunteersInGuilds())
        {
            String[] data = string.split(",");
            int uid = Integer.parseInt(data[0].trim());
            int laugid = Integer.parseInt(data[1].trim());

            for (Guild guild : getAllGuildsForTable(false))
            {
                if (laugid == guild.getId())
                {
                    for (Volunteer volunteer : allVol)
                    {
                        if (uid == volunteer.getId())
                        {
                            guild.addVolunteer(volunteer);
                            volunteersInCurrentGuild.add(volunteer);
                        }
                    }
                }
            }
        }
    }

    public void populateGuildsWithEmployees()
    {
        List<Employee> allEmp;
        allEmp = getAllEmployees();
        for (String string : getEmployeesInguilds())
        {
            String[] data = string.split(",");
            int uid = Integer.parseInt(data[0].trim());
            int laugid = Integer.parseInt(data[1].trim());

            for (Guild guild : getAllGuildsForTable(false))
            {
                if (laugid == guild.getId())
                {
                    for (Employee employee : allEmp)
                    {
                        if (uid == employee.getId())
                        {
                            guild.addEmployee(employee);
                            employeesInCurrentGuild.add(employee);
                        }
                    }
                }
            }
        }
    }

    /**
     * Gets all names of the guilds
     *
     * @return Guild Names
     */
    public ObservableList<String> getAllGuildNames()
    {
        return guildNames;
    }

    private void setAllGuildNames(boolean includeAllField)
    {
        guildNames.clear();

        if (includeAllField)
        {
            guildNames.add("Alle Personer");
        }
        for (Guild guild : manager.getAllActiveGuilds())
        {
            guildNames.add(guild.getName());
        }
    }

    public ObservableList<Volunteer> getVolunteersInCurrentGuild()
    {
        return volunteersInCurrentGuild;
    }

    public ObservableList<Employee> getEmployeesInCurrentGuild()
    {
        return employeesInCurrentGuild;
    }

    public void addEmployeeToGuild(Guild selectedGuild, Employee selectedEmployee) throws SQLException
    {
        selectedGuild.addEmployee(selectedEmployee);
        manager.addEmployeeToGuild(selectedGuild.getId(), selectedEmployee.getId());
    }

    public int getWorkedHoursInPeriodForGuild(Date from, Date to, int id) throws SQLException, IOException
    {
        return manager.getWorkedHoursInPeriodForGuild(from, to, id);
    }

    public void deleteInactiveGuilds() throws SQLException
    {
        manager.deleteInactiveGuilds();
    }

    public ObservableList<Guild> getAllActiveGuilds()
    {
        return allActiveGuilds;
    }

}
