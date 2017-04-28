/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.bll;

import frivilligetimer.be.Guild;
import frivilligetimer.dal.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author thomas
 */
public class GuildManager
{

    DBManager dbManager;

    public GuildManager() throws IOException, SQLException
    {
        dbManager = new DBManager();
    }

    /**
     * Gets all guilds from DAO
     *
     * @return a list of guilds
     */
    public List<Guild> getAllGuilds()
    {
        return dbManager.getAllGuilds();
    }

    public void addGuild(Guild guild) throws SQLException
    {
        dbManager.addGuild(guild);
    }

}
