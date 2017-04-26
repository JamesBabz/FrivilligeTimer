/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import frivilligetimer.be.Volunteer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    /**
     * The default constructor for the database manager.
     */
    public DBManager() throws IOException, SQLException
    {
        this.volunteers = new ArrayList<>();
        this.cm = new ConnectionManager();
        
        setAllVolenteers();
    }

    public void setAllVolenteers() throws SQLServerException, SQLException 
    {
        String sql = "SELECT * FROM Volunteer";

        try (Connection con = cm.getConnection())
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
            {
                int id = rs.getInt("ID");
                String fName = rs.getString("FirstName");
                String lName = rs.getString("LastName");
                String phonenum =  rs.getString("Phonenum");
                String email = rs.getString("Email");
                String note = rs.getString("Note");
                String preference = rs.getString("Preference");
                
                Volunteer volunteer = new Volunteer(id, fName, lName, phonenum, email);
                volunteers.add(volunteer);
                
            }
        }
    }
/**
 * Gets all the volunteers
 * @return a list of all volunteers
 */
    public List<Volunteer> getAllVolunteers()
    {
        return volunteers;
    }
}
