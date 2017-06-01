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
import java.util.Date;
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
    public List<Guild> getAllActiveGuilds()
    {
        return dbManager.getAllActiveGuilds();
    }

    public List<Guild> getAllGuilds()
    {
        return dbManager.getAllGuilds();
    }

    public void addGuild(Guild guild) throws SQLException
    {
        dbManager.addGuild(guild);
    }

    public void deleteGuild(Guild guild) throws SQLException
    {
        dbManager.removeVolunteersFromAssignedGuild(guild);
        dbManager.deleteGuild(guild);
    }

    public void addVolunteerToGuild(int laugid, int uid) throws SQLException
    {
        dbManager.addVolunteerToGuild(laugid, uid);
    }

    public List<String> getVolunteersInGuild()
    {
        return dbManager.getVolunteersInGuild();
    }

    public void updateGuild(Guild guild) throws SQLException
    {
        dbManager.updateGuild(guild);
    }

    public void addEmployeeToGuild(int laugid, int uid) throws SQLException
    {
        dbManager.addEmployeeToGuild(laugid, uid);

    }

    public List<String> getEmployeesInGuild()
    {
        return dbManager.getEmployeesInGuild();
    }

    public int getWorkedHoursInPeriodForGuild(Date from, Date to, int id) throws SQLException, IOException
    {
        return dbManager.getWorkedHoursInPeriodForGuild(from, to, id);
    }

    public void deleteInactiveGuilds() throws SQLException
    {
        dbManager.deleteInactiveGuilds();
    }
}
