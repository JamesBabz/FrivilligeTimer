/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.bll;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Manager;
import frivilligetimer.dal.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author thomas
 */
public class StaffManager
{
    DBManager dbManager;

    public StaffManager() throws IOException, SQLException
    {
        dbManager = new DBManager();
    }
    
        /**
     * Gets all managers from DAO
     * @return a list of managers
     */
    public List<Manager> getAllManagers()
    {
        return dbManager.getAllManagers();
    }
    
        /**
     * Gets all employees from DAO
     * @return a list of employees
     */
    public List<Employee> getAllEmployees()
    {
        return dbManager.getAllEmployees();
    }
}
