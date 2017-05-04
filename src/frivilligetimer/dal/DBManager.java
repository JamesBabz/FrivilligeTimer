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
import frivilligetimer.be.Volunteer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

        setAllPeople();
        setAllGuilds();
        setAllVolunteersInGuilds();
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
                switch (level)
                {
                    case 0:
                        Manager manager = new Manager(id, fName, lName, phonenum, email, password);
                        managers.add(manager);
                        break;
                    case 1:
                        Employee employee = new Employee(id, fName, lName, phonenum, email);
                        employees.add(employee);
                        break;
                    case 2:
                        Volunteer volunteer = new Volunteer(id, fName, lName, phonenum, email, note, preference);
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

    /**
     * Gets all the volunteers
     *
     * @return a list of all volunteers
     */
    public List<Volunteer> getAllVolunteers()
    {
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
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

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
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, guild.getId());
            ps.executeUpdate();

        }
    }

    public void removeEmployee(Employee employee) throws SQLServerException, SQLException
    {
        String sql = "DELETE from People WHERE ID = ?";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, employee.getId());
            ps.executeUpdate();
        }
    }

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

    public List<String> getVolunteersInGuild()
    {
        return volunteersInGuild;
    }

}
