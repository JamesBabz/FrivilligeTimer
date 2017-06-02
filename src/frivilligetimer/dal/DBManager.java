/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import frivilligetimer.be.Employee;
import frivilligetimer.be.Guild;
import frivilligetimer.be.Manager;
import frivilligetimer.be.Person;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.controller.ViewHandler;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * This class handles all the Data from the Database once a connection has been
 * established.
 *
 * @author Stephan Fuhlendorff, Jacob Enemark, Simon Birkedal, Thomas Hansen
 */
public final class DBManager
{

    private ConnectionManager cm;
    private final List<Volunteer> volunteers;
    private final List<Volunteer> inactiveVolunteers;
    private final List<Employee> employees;
    private final List<Manager> managers;
    private final List<Guild> guilds;
    private final List<Guild> inactiveGuilds;
    private final List<String> volunteersInGuild;
    private final List<String> employeesInGuild;
    private Stage stage;
    private final ViewHandler viewHandler;

    /**
     * The default constructor for the database manager.
     */
    public DBManager() throws IOException, SQLException
    {
        this.volunteers = new ArrayList<>();
        this.inactiveVolunteers = new ArrayList<>();
        this.cm = new ConnectionManager();
        this.employees = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.guilds = new ArrayList<>();
        this.inactiveGuilds = new ArrayList<>();
        this.volunteersInGuild = new ArrayList<>();
        this.employeesInGuild = new ArrayList<>();
        viewHandler = new ViewHandler(stage);
        setAllPeople();
        setAllGuilds();
        setAllEmployees();
        setAllVolunteersInGuilds();
        setAllEmployeesInGuilds();
    }

    /**
     * Gets the data from database adds the people to the right lists
     * (Volunteer, Employee, Manager)
     *
     * @throws SQLServerException
     * @throws SQLException
     */
    public void setAllPeople() throws SQLServerException, SQLException
    {
        String sql = "SELECT * FROM People";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int id = rs.getInt("ID");
                String fName = rs.getString("FirstName");
                String lName = rs.getString("LastName");
                String phonenum = rs.getString("PhoneNum");
                String email = rs.getString("Email");
                String note = rs.getString("Note");
                String preference = rs.getString("Preference");
                int level = rs.getInt("Position");
                String password = rs.getString("Password");
                byte[] imageBytes = rs.getBytes("ImageBinary");
                Boolean isActive = rs.getBoolean("isActive");
                Date inactiveSince = rs.getDate("InactiveSince");
                BufferedImage image = null;
                if (imageBytes != null)
                {
                    try
                    {
                        image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    }
                    catch (IOException e)
                    {
                        viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Fejl ved forbindelse til databasen", "Der skete en fejl i databasen" + e.getMessage());
                    }
                }

                switch (level)
                {
                    case 0:
                        Manager manager = new Manager(id, fName, lName, phonenum, email, password, image);
                        managers.add(manager);
                        break;
                    case 1:
                        Employee employee = new Employee(id, fName, lName, phonenum, email, password, image);
                        employees.add(employee);
                        break;
                    case 2:
                        if (isActive)
                        {
                            Volunteer volunteer = new Volunteer(id, fName, lName, phonenum, email, preference, note, image, inactiveSince);
                            volunteers.add(volunteer);
                        }
                        else
                        {
                            Volunteer volunteer = new Volunteer(id, fName, lName, phonenum, email, preference, note, image, inactiveSince);
                            inactiveVolunteers.add(volunteer);
                        }
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /**
     * Deletes all inactive volunteers in the database
     *
     * @throws SQLException
     */
    public void deleteInactiveVolunteers() throws SQLException
    {
        String sql = "DELETE from People WHERE isActive = 0";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.executeUpdate();
            inactiveVolunteers.clear();

        }
    }

    /**
     * Creates all the guilds from the database
     *
     * @throws SQLServerException
     * @throws SQLException
     */
    public void setAllGuilds() throws SQLServerException, SQLException
    {
        String sql = "SELECT * FROM Guilds";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int id = rs.getInt("ID");
                String name = rs.getString("Name");
                Boolean isActive = rs.getBoolean("isActive");

                if (isActive)
                {
                    Guild guild = new Guild(id, name);
                    guilds.add(guild);
                }
                else
                {
                    Guild guild = new Guild(id, name);
                    inactiveGuilds.add(guild);
                }

            }

        }
    }

    /**
     * Populates all the guilds with the volunteers
     *
     * @throws SQLServerException
     * @throws SQLException
     */
    public void setAllVolunteersInGuilds() throws SQLServerException, SQLException
    {
        String sql = " SELECT * FROM AssignedGuilds";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int uid = rs.getInt("uid");
                int laugid = rs.getInt("laugid");

                String string = uid + "," + laugid;
                volunteersInGuild.add(string);
            }

        }
    }

    /**
     * Populates all the guilds with the employees
     *
     * @throws SQLServerException
     * @throws SQLException
     */
    public void setAllEmployeesInGuilds() throws SQLServerException, SQLException
    {
        String sql = " SELECT* FROM AssignedGuilds";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int uid = rs.getInt("uid");
                int laugid = rs.getInt("laugid");

                String string = uid + "," + laugid;
                employeesInGuild.add(string);
            }

        }
    }

    /**
     * Gets all the active volunteers
     *
     * @return a list of all active volunteers
     */
    public List<Volunteer> getAllActiveVolunteers()
    {
        volunteers.clear();
        inactiveVolunteers.clear();
        try
        {
            setAllPeople();
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Fejl ved forbindelse til databasen", "Der skete en fejl i databasen" + ex.getMessage());
        }
        return volunteers;
    }

    /**
     * Gets all the inactive volunteers
     *
     * @return a list of all inactive volunteers
     */
    public List<Volunteer> getAllInactiveVolunteers()
    {

        volunteers.clear();
        inactiveVolunteers.clear();
        try
        {
            setAllPeople();
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Fejl ved forbindelse til databasen", "Der skete en fejl i databasen" + ex.getMessage());
        }
        return inactiveVolunteers;
    }

    /**
     * Gets all the volunteers
     *
     * @return a list of all volunteers
     */
    public List<Volunteer> getAllVolunteers()
    {
        volunteers.clear();
        inactiveVolunteers.clear();
        try
        {
            setAllPeople();
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Fejl ved forbindelse til databasen", "Der skete en fejl i databasen" + ex.getMessage());
        }
        List<Volunteer> returnList = new ArrayList<>();
        returnList.addAll(volunteers);
        returnList.addAll(inactiveVolunteers);
        return returnList;
    }

    /**
     * Gets all employees
     *
     * @return a list of employees
     */
    public List<Employee> getAllEmployees()
    {

        return employees;
    }

    public void setAllEmployees()
    {
        employees.clear();
        try
        {
            setAllPeople();
        }
        catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Fejl ved forbindelse til databasen", "Der skete en fejl i databasen" + ex.getMessage());
        }
    }

    /**
     * Gets all managers
     *
     * @return a list of all managers
     */
    public List<Manager> getAllManagers()
    {
        return managers;
    }

    /**
     * Gets all active guilds
     *
     * @return a list of all active guilds
     */
    public List<Guild> getAllActiveGuilds()
    {
        return guilds;
    }

    /**
     * Gets all active guilds
     *
     * @return a list of all active guilds
     */
    public List<Guild> getAllInactiveGuilds()
    {
        return inactiveGuilds;
    }

    /**
     * Gets all active guilds
     *
     * @return a list of all active guilds
     */
    public List<Guild> getAllGuilds()
    {
        List<Guild> returnList = new ArrayList<>();
        returnList.addAll(guilds);
        returnList.addAll(inactiveGuilds);
        return returnList;
    }

    /**
     * Gets all volunteers in guild
     *
     * @return - list of volunteers
     */
    public List<String> getVolunteersInGuild()
    {
        return volunteersInGuild;
    }

    /**
     * Gets all employees in guild
     *
     * @return - list of employees
     */
    public List<String> getEmployeesInGuild()
    {
        return employeesInGuild;
    }

    /**
     * Gets a volunteers hours for a specific day and guild
     *
     * @param id - The volunteers id
     * @param date - The date for the hours
     * @param guildid - The guild id
     * @return - amount of hours
     * @throws SQLException
     */
    public int getTodaysHours(int id, Date date, int guildid) throws SQLException
    {
        String sql = "SELECT hours FROM Hours WHERE uid = " + id + " AND date = '" + new java.sql.Date(date.getTime()).toString() + "' AND laugid = " + guildid;
        int hours = -1;
        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                hours = rs.getInt("hours");
            }

        }

        return hours;
    }

    /**
     *
     * @param from The start date for the hours to pull
     * @param to The end date for the hours to pull
     * @param id the id of the person
     * @param guildid - The guild id
     * @return - Returns a map of dates with corresponding hours
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public TreeMap<java.sql.Date, Integer> getWorkedHoursInPeriodForVolunteer(Date from, Date to, int id, int guildid) throws SQLException, IOException
    {
        java.sql.Date sqlFrom = new java.sql.Date(from.getTime());
        java.sql.Date sqlTo = new java.sql.Date(to.getTime());
        ConnectionManager cm;
        cm = new ConnectionManager();
        TreeMap<java.sql.Date, Integer> lineChartValues = new TreeMap();
        java.sql.Date date = new java.sql.Date(from.getTime());
        int hours = 0;
        do
        {
            lineChartValues.put((java.sql.Date) date.clone(), hours);
            date.setTime(date.getTime() + 86_400_000);
        }
        while (date.before(to));
        String sql = "SELECT date, hours from Hours WHERE date BETWEEN ? AND ? AND uid = ? AND laugid = ?";

        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, sqlFrom);
            ps.setDate(2, sqlTo);
            ps.setInt(3, id);
            ps.setInt(4, guildid);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                hours = rs.getInt("hours");
                java.sql.Date d = rs.getDate("date");
                lineChartValues.put(d, hours);
            }
        }
        return lineChartValues;
    }

    /**
     *
     * @param from The start date for the hours to pull
     * @param to The end date for the hours to pull
     * @param id the id of the guild
     * @return Returns amount of hours in the period
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public int getWorkedHoursInPeriodForGuild(Date from, Date to, int id) throws SQLException, IOException
    {
        java.sql.Date sqlFrom = new java.sql.Date(from.getTime());
        java.sql.Date sqlTo = new java.sql.Date(to.getTime());
        ConnectionManager cm;
        cm = new ConnectionManager();
        int hours = 0;

        String sql = "SELECT date, hours from Hours WHERE date BETWEEN ? AND ? AND laugid = ?";

        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, sqlFrom);
            ps.setDate(2, sqlTo);
            ps.setInt(3, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next())
            {
                hours += rs.getInt("hours");
            }
        }
        return hours;
    }

    /**
     * Adds a guild to the database
     *
     * @param guild - the guild to add
     * @throws SQLServerException
     * @throws SQLException
     */
    public void addGuild(Guild guild) throws SQLServerException, SQLException
    {
        String sql = "INSERT INTO Guilds (Name) VALUES( ?)";
        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, guild.getName());
            ps.executeUpdate();
            if (ps.getGeneratedKeys().next())
            {
                guild.setId(ps.getGeneratedKeys().getInt(1));
            }

        }

    }

    /**
     * Adds a volunteer to the database
     *
     * @param volunteer - The volunteer to add
     * @throws SQLServerException
     * @throws SQLException
     */
    public void addVolunteer(Volunteer volunteer) throws SQLServerException, SQLException
    {
        String sql = "INSERT INTO People (FirstName, LastName, PhoneNum, Email, Position) VALUES (?, ?, ?, ?, 2)";
        Connection con = cm.getConnection();
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, volunteer.getFirstName());
            ps.setString(2, volunteer.getLastName());
            ps.setString(3, volunteer.getEmail());
            ps.setString(4, volunteer.getPhoneNum());

            ps.executeUpdate();

            if (ps.getGeneratedKeys().next())
            {
                volunteer.setId(ps.getGeneratedKeys().getInt(1));
            }
        }
    }

    /**
     * Adds an employee to the database
     *
     * @param employee - The employee to add
     * @throws SQLServerException
     * @throws SQLException
     */
    public void addEmployee(Employee employee) throws SQLServerException, SQLException
    {
        String sql = "INSERT INTO People (FirstName, LastName, PhoneNum, Email, Position) VALUES (?, ?, ?, ?, 1)";
        Connection con = cm.getConnection();
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getEmail());
            ps.setString(4, employee.getPhoneNum());

            ps.executeUpdate();

            if (ps.getGeneratedKeys().next())
            {
                employee.setId(ps.getGeneratedKeys().getInt(1));
            }
        }
        employees.add(employee);
    }

    /**
     * Deletes a volunteer from the database
     *
     * @param volunteer - The volunteer to delete
     * @throws SQLException
     */
    public void deleteVolunteer(Volunteer volunteer) throws SQLException
    {
        String sql = "UPDATE People SET isActive = 0, InactiveSince = ? WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(2, volunteer.getId());
            java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
            ps.setDate(1, sqlDate);
            ps.executeUpdate();
            volunteers.remove(volunteer);

        }
    }

    /**
     * Reactivates a formerly deactivated volunteer
     *
     * @param volunteer - The volunteer to activate
     * @throws SQLException
     */
    public void activateVolunteer(Volunteer volunteer) throws SQLException
    {
        String sql = "UPDATE People SET isActive = 1 WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, volunteer.getId());
            ps.executeUpdate();
            inactiveVolunteers.remove(volunteer);

        }
    }

    /**
     * Delete a volunteer completely
     *
     * @param volunteer - The volunteer to delete
     * @throws SQLException
     */
    public void deleteInactiveVolunteer(Volunteer volunteer) throws SQLException
    {
        String sql = "DELETE from People WHERE Id = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, volunteer.getId());
            ps.executeUpdate();
            inactiveVolunteers.clear();

        }
    }

    /**
     * Remove a volunteer from all guilds
     *
     * @param volunteer - The volunteer to remove
     * @throws SQLException
     */
    public void removeVolunteerFromGuilds(Volunteer volunteer) throws SQLException
    {
        String sql = "DELETE from AssignedGuilds WHERE uid = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, volunteer.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Deactivates a guild
     *
     * @param guild - the guild to deactivate
     * @throws SQLException
     */
    public void deactivateGuild(Guild guild) throws SQLException
    {
        String sql = "UPDATE Guilds SET isActive = 0 WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, guild.getId());
            ps.executeUpdate();
            guilds.remove(guild);

        }
    }

    /**
     * Removes all volunteers from a guild
     *
     * @param guild - The guild to be emptied
     * @throws SQLException
     */
    public void removeVolunteersFromAssignedGuild(Guild guild) throws SQLException
    {
        String sql = "DELETE from AssignedGuilds WHERE laugid =?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, guild.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Remove a specific volunteer from a specific guild
     *
     * @param volunteer - The volunteer to remove
     * @param guild - The guild to remove from
     * @throws SQLException
     */
    public void removeVolunteerFromAssignedGuild(Volunteer volunteer, Guild guild) throws SQLException
    {
        String sql = "DELETE from AssignedGuilds WHERE uid = ? AND laugid = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, volunteer.getId());
            ps.setInt(2, guild.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Remove a specific employee from a specific guild
     *
     * @param employee - The employee to remove
     * @param guild - The guild to remove from
     * @throws SQLException
     */
    public void removeEmployeeFromAssignedGuild(Employee employee, Guild guild) throws SQLException
    {
        String sql = "DELETE from AssignedGuilds WHERE uid = ? AND laugid = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, employee.getId());
            ps.setInt(2, guild.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Deactivates an employee
     * @param employee - The employee to deactivate
     * @throws SQLServerException
     * @throws SQLException
     */
    public void deactivateEmployee(Employee employee) throws SQLServerException, SQLException
    {
        String sql = "UPDATE People SET isActive = 0 WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, employee.getId());
            ps.executeUpdate();
            employees.remove(employee);
        }
    }

    /**
     * Assigns volunteer to a guild
     *
     * @param laugid id of the guild
     * @param uid id of the volunteer
     * @throws SQLException
     */
    public void addVolunteerToGuild(int laugid, int uid) throws SQLException
    {
        String sql = "INSERT INTO AssignedGuilds (uid, laugid) VALUES (?,?) ";

        Connection con = cm.getConnection();
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, laugid);

            ps.executeUpdate();

        }
    }

    /**
     * Adds an employee to a guild
     * @param laugid - The guild id
     * @param uid - The employee id
     * @throws SQLException 
     */
    public void addEmployeeToGuild(int laugid, int uid) throws SQLException
    {
        String sql = "INSERT INTO AssignedGuilds (uid, laugid) VALUES (?,?) ";

        Connection con = cm.getConnection();
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.setInt(2, laugid);

            ps.executeUpdate();

        }
    }

    /**
     * Updates a specific volunteer
     * @param volunteer - The volunteer to update
     * @throws SQLException 
     */
    public void updateVolunteer(Volunteer volunteer) throws SQLException
    {
        String sql = "UPDATE People SET FIRSTNAME = ?, LASTNAME = ?, PHONENUM = ?, EMAIL = ? WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, volunteer.getFirstName());
            ps.setString(2, volunteer.getLastName());
            ps.setString(3, volunteer.getPhoneNum());
            ps.setString(4, volunteer.getEmail());
            ps.setInt(5, volunteer.getId());
            ps.executeUpdate();
        }
    }

    /**
     * Updates a specific employee
     * @param employee - The employee to update
     * @throws SQLException 
     */
    public void updateEmployee(Employee employee) throws SQLException
    {
        String sql = "UPDATE People SET FIRSTNAME = ?, LASTNAME = ?, PHONENUM = ?, EMAIL = ? WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getPhoneNum());
            ps.setString(4, employee.getEmail());
            ps.setInt(5, employee.getId());
            ps.executeUpdate();
        }

    }

    /**
     * Updates a specific guild
     * @param guild - The guild to update
     * @throws SQLException 
     */
    public void updateGuild(Guild guild) throws SQLException
    {
        String sql = "UPDATE Guilds SET Name = ? WHERE id = ?";

        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, guild.getName());
            ps.setInt(2, guild.getId());

            ps.executeUpdate();
        }

    }

    /**
     * Updates the database with an image represented by binary data.
     *
     * @param person - The person who needs the image set
     * @param localImagePath - The path of the image
     * @throws IOException
     * @throws SQLException
     */
    public void updateImage(Person person, String localImagePath) throws IOException, SQLException
    {
        String sql = "UPDATE People SET ImageBinary = ? WHERE ID = ?";
        int length;
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            File imageFile = new File(localImagePath);
            FileInputStream fis = new FileInputStream(imageFile);
            length = (int) imageFile.length();
            byte[] imageBytes = new byte[length];
            int i = fis.read(imageBytes);
            ps.setBytes(1, imageBytes);
            ps.setInt(2, person.getId());
            ps.executeUpdate();

        }
    }

    /**
     * Adds hours for the volunteer for a specific day
     * @param uid - The volunteer id
     * @param date - The date where the hours should be added
     * @param hours - the amount of hours to add
     * @param guildId - The id of the guild where the hours should be added
     * @throws SQLException 
     */
    public void addHoursForVolunteer(int uid, Date date, int hours, int guildId) throws SQLException
    {
        String sql = "INSERT INTO Hours (uid, date, hours, laugid) VALUES (?,?,?,?)";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(2, sqlDate);
            ps.setInt(3, hours);
            ps.setInt(4, guildId);
            ps.executeUpdate();
        }

    }

    /**
     * Updates already set hours
     * @param uid - The volunteer id
     * @param date - The date to change the hours for
     * @param hours - The new amount of hours to be set
     * @param guildId the value of guildId
     * @throws SQLException 
     */
    public void updateHoursForVolunteer(int uid, Date date, int hours, int guildId) throws SQLException
    {
        String sql = "UPDATE Hours SET hours = ? WHERE uid = ? AND date = ? AND laugid = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, hours);
            ps.setInt(2, uid);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(3, sqlDate);
            ps.setInt(4, guildId);
            ps.executeUpdate();
        }
    }

    /**
     * Updates a votlunteers notes and preferences
     * @param id - The volunteer id
     * @param pref - The String of preferences
     * @param note - The note to set
     * @throws SQLException 
     */
    public void updateNoteAndPrefForVolunteer(int id, String pref, String note) throws SQLException
    {
        String sql = "UPDATE People SET Note = ?, Preference = ? WHERE id = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, note);
            ps.setString(2, pref);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    /**
     * Permanently delete inactive guilds
     * @throws SQLServerException
     * @throws SQLException 
     */
    public void deleteInactiveGuilds() throws SQLServerException, SQLException
    {
        String sql = "DELETE from Guilds WHERE isActive = 0";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.executeUpdate();
            inactiveGuilds.clear();

        }
    }

}
