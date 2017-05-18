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
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final List<Employee> employees;
    private final List<Manager> managers;
    private final List<Guild> guilds;
    private final List<String> volunteersInGuild;
    private final List<String> employeesInGuild;

    /**
     * The default constructor for the database manager.
     */
    public DBManager() throws IOException, SQLException
    {
        this.volunteers = new ArrayList<>();
        this.cm = new ConnectionManager();
        this.employees = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.guilds = new ArrayList<>();
        this.volunteersInGuild = new ArrayList<>();
        this.employeesInGuild = new ArrayList<>();

        setAllPeople();
        setAllGuilds();
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
                BufferedImage image = null;
                if (imageBytes != null)
                {
                    try
                    {
                        image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    } catch (IOException e)
                    {
                        e.printStackTrace();
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
                        Volunteer volunteer = new Volunteer(id, fName, lName, phonenum, email, preference, note, image);
                        volunteers.add(volunteer);
                        break;
                    default:
                        break;
                }

            }
        }
    }

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

                Guild guild = new Guild(id, name);
                guilds.add(guild);
            }

        }
    }

    public void setAllVolunteersInGuilds() throws SQLServerException, SQLException
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
                volunteersInGuild.add(string);
            }

        }
    }
    
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
     * Gets all the volunteers
     *
     * @return a list of all volunteers
     */
    public List<Volunteer> getAllVolunteers()
    {
        volunteers.clear();
        try
        {
            setAllPeople();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return volunteers;
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
     * Gets all guilds
     *
     * @return a list of all guilds
     */
    public List<Guild> getAllGuilds()
    {
        return guilds;
    }

    public List<String> getVolunteersInGuild()
    {
        return volunteersInGuild;
    }
    
    public List<String> getEmployeesInGuild()
    {
        return employeesInGuild;
    }

    public int getTodaysHours(int id) throws SQLException
    {
        String sql = "SELECT * FROM Hours";
        int hours = -1;
        Date today = new java.sql.Date(new Date().getTime());
        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int uid = rs.getInt("uid");
                Date date = rs.getDate("Date");
                int h = rs.getInt("hours");

                if (uid == id && date.toString().equals(today.toString()))
                {
                    hours = h;
                }
            }

        }
        return hours;
    }

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
    }

    public void deleteVolunteer(Volunteer volunteer) throws SQLException
    {
        String sql = "DELETE from People WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, volunteer.getId());
            ps.executeUpdate();
            volunteers.remove(volunteer);

        }
    }

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

    public void deleteGuild(Guild guild) throws SQLException
    {
        String sql = "DELETE from Guilds WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, guild.getId());
            ps.executeUpdate();
            guilds.remove(guild);

        }
    }
    
    public void removeVolunteersFromAssignedGuild(Guild guild) throws SQLException
    {
        String sql = "DELETE from AssignedGuilds WHERE laugid =?";
        
        try(Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, guild.getId());
            ps.executeUpdate();
        }
    }

        public void removeVolunteerFromAssignedGuild(Volunteer volunteer, Guild guild) throws SQLException
    {
        String sql = "DELETE from AssignedGuilds WHERE uid = ? AND laugid = ?";
        
        try(Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);
            
            ps.setInt(1, volunteer.getId());
            ps.setInt(2, guild.getId());
            ps.executeUpdate();
        }
    }
    public void deleteEmployee(Employee employee) throws SQLServerException, SQLException
    {
        String sql = "DELETE from People WHERE ID = ?";

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
     * Updates the database with a student image represented by binary data.
     *
     * @param person
     * @param localImagePath
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

    public void addHoursForVolunteer(int uid, Date date, int hours) throws SQLException
    {
        String sql = "INSERT INTO Hours (uid, date, hours) VALUES (?,?,?)";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, uid);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(2, sqlDate);
            ps.setInt(3, hours);
            ps.executeUpdate();
        }

    }

    public void updateHoursForVolunteer(int uid, Date date, int hours) throws SQLException
    {
        String sql = "UPDATE Hours SET hours = ? WHERE uid = ? AND date = ?";
        try (Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, hours);
            ps.setInt(2, uid);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(3, sqlDate);
            ps.executeUpdate();
        }
    }

    public void updateNoteAndPrefForVolunteer(int id, String pref, String note) throws SQLException
    {
        String sql = "UPDATE People SET Note = ?, Preference = ? WHERE id = ?";
        try(Connection con = cm.getConnection())
        {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, note);
            ps.setString(2, pref);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }



}
