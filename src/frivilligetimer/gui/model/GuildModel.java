/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Guild;
import frivilligetimer.bll.GuildManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thomas
 */
public class GuildModel
{

    private static GuildModel instance;
    GuildManager manager;

    private final ObservableList<Guild> allGuilds;

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
        } catch (IOException ex)
        {
            Logger.getLogger(GuildModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(GuildModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        allGuilds = FXCollections.observableArrayList();
    }

    /**
     * Gets all guilds in tableview "laug"
     *
     * @return a list of all guilds
     */
    public ObservableList<Guild> getAllGuildForTable()
    {
        for (Guild guild : manager.getAllGuilds())
        {
            allGuilds.add(guild);
        }
        return allGuilds;
    }


}
