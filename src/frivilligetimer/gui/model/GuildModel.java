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
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
    private ObservableList<Volunteer> volunteersInGuild;
    private ObservableList<Volunteer> volunteersInCurrentGuild;
    private ObservableList<Employee> employeesInGuild;
    private ObservableList<Employee> employeesInCurrentGuild;

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
        try
        {
            manager = new GuildManager();
            volunteerManager = new VolunteerManager();
            staffManager = new StaffManager();
        } catch (IOException ex)
        {
            Logger.getLogger(GuildModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(GuildModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        allActiveGuilds = FXCollections.observableArrayList();
        allVolunteers = FXCollections.observableArrayList();
        allEmployees = FXCollections.observableArrayList();
        guildNames = FXCollections.observableArrayList();
        volunteersInGuild = FXCollections.observableArrayList();
        volunteersInCurrentGuild = FXCollections.observableArrayList();
        employeesInGuild = FXCollections.observableArrayList();
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
     * @return a list of all guilds
     */
    public ObservableList<Guild> getAllGuildsForTable()
    {
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
        manager.deleteGuild(guild);
    }

    public void addVolunteerToGuild(Guild selectedGuild, Volunteer selectedVolunteer) throws SQLException
    {
        selectedGuild.addVolunteer(selectedVolunteer);
        manager.addVolunteerToGuild(selectedGuild.getId(), selectedVolunteer.getId());

    }

    public void setVolunteersInGuild(Guild guild)
    {
        volunteersInGuild.clear();
        volunteersInGuild.addAll(guild.getVolunteers());
    }

    public void setEmployeesInGuild(Guild guild)
    {
        employeesInGuild.clear();
        employeesInGuild.addAll(guild.getEmployees());
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
        return volunteersInGuild;
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

            for (Guild guild : getAllGuildsForTable())
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

            for (Guild guild : getAllGuildsForTable())
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
            guildNames.add("Alle Laug");
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

}
