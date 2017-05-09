/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.GuildManager;
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
public final class GuildModel
{
    
    public Guild selectedGuild;
    private static GuildModel instance;
    private GuildManager manager;
    private VolunteerManager volunteerManager;
    private final ObservableList<Guild> allGuilds;
    private final ObservableList<Volunteer> allVolunteers;
    private final ObservableList<String> guildNames;
    private ObservableList<Volunteer> volunteersInGuild;
    private ObservableList<Volunteer> volunteersInCurrentGuild;

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
        guildNames = FXCollections.observableArrayList();
        volunteersInGuild = FXCollections.observableArrayList();
        volunteersInCurrentGuild = FXCollections.observableArrayList();
        
        setAllGuilds();
        populateGuilds();
        
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
    
    private ObservableList<Volunteer> getAllGuilds()
    {
        allVolunteers.clear();
        allVolunteers.addAll(volunteerManager.getAllVolunteers());
        return allVolunteers;
    }

    public void addGuild(Guild guild) throws SQLException
    {
        allGuilds.add(guild);
        manager.addGuild(guild);
    }

    public void deleteGuild(Guild guild)
    {
        allGuilds.remove(guild);
        manager.removeGuild(guild);
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
    public void populateGuilds()
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
                    for (Volunteer volunteer : getAllGuilds())
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
  

}
