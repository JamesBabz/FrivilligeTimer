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
    private final ObservableList<Guild> allGuilds;
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
        }
        catch (IOException ex)
        {
            Logger.getLogger(GuildModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(GuildModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        allGuilds = FXCollections.observableArrayList();
        allVolunteers = FXCollections.observableArrayList();
        allEmployees = FXCollections.observableArrayList();
        guildNames = FXCollections.observableArrayList();
        volunteersInGuild = FXCollections.observableArrayList();
        volunteersInCurrentGuild = FXCollections.observableArrayList();
        employeesInGuild = FXCollections.observableArrayList();
        employeesInCurrentGuild = FXCollections.observableArrayList();
       
        
        setAllGuilds();
        populateGuildsWithVolunteers();
        populateGuildsWithEmployees();
        
    }

    /**
     * Gets all guilds in tableview "laug"
     *
     * @return a list of all guilds
     */
    public ObservableList<Guild> getAllGuildsForTable()
    {
        return allGuilds;
    }
    
    public void setAllGuilds()
    {
        allGuilds.clear();
        allGuilds.addAll(manager.getAllGuilds());
    }
    
    private ObservableList<Volunteer>getAllVolunteers()
    {
        allVolunteers.clear();
        allVolunteers.addAll(volunteerManager.getAllVolunteers());
        return allVolunteers;
    }
    
    private ObservableList<Employee> getAllEmployees()
    {
        allEmployees.clear();
        allEmployees.addAll(staffManager.getAllEmployees());
        return allEmployees;
    }

    public void addGuild(Guild guild) throws SQLException
    {
        allGuilds.add(guild);
        manager.addGuild(guild);
    }

    public void deleteGuild(Guild guild)
    {
        allGuilds.remove(guild);
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

    public  List<String> getEmployeesInguilds()
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
    
    public Guild getSelectedGuild() {
        return selectedGuild;
    }
    
    public void setSelectedGuild(Guild selectedGuild) {
        this.selectedGuild = selectedGuild;
    }
    
        /**
     * Gets the volunteers in each guild
     */
    public void populateGuildsWithVolunteers()
    {

        for (String string : getAllVolunteersInGuilds())
        {
            String[] data = string.split(",");
            int uid = Integer.parseInt(data[0].trim());
            int laugid = Integer.parseInt(data[1].trim());

            for (Guild guild : getAllGuildsForTable())
            {
                if (laugid == guild.getId())
                {
                    for (Volunteer volunteer : getAllVolunteers())
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

        for (String string : getEmployeesInguilds())
        {
            String[] data = string.split(",");
            int uid = Integer.parseInt(data[0].trim());
            int laugid = Integer.parseInt(data[1].trim());

            for (Guild guild : getAllGuildsForTable())
            {
                if (laugid == guild.getId())
                {
                    for (Employee employee : getAllEmployees())
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
     * @param includeAllField includes an All Guilds field for lists
     * @return Guild Names
     */
    public ObservableList<String> getAllGuildNames(boolean includeAllField)
    {
        guildNames.clear();
        if (includeAllField)
        {
            guildNames.add("Alle Laug");
        }
        for (Guild guild : manager.getAllGuilds())
        {
            guildNames.add(guild.getName());
        }
        return guildNames;
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
    
public int getWorkedHoursInPeriodForGuild(Date from, Date to, int id) throws SQLException, IOException{
        return manager.getWorkedHoursInPeriodForGuild(from, to, id);
    }
  

}
