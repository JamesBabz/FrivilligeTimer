/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Volunteer;
import frivilligetimer.bll.StaffManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author thomas
 */
public class StaffModel
{
    private static StaffModel instance;
    StaffManager manager;
    
     private final ObservableList<Employee> allEmployees;
 
    
        public static StaffModel getInstance()
    {
        if (instance == null)
        {
            instance = new StaffModel();
        }
        return instance;
    }

    private StaffModel() 
    {
        try
        {
            manager = new StaffManager();
        } catch (IOException ex)
        {
            Logger.getLogger(StaffModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(StaffModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        allEmployees = FXCollections.observableArrayList();
    }
    
        /**
     * Gets all employees in the tableview "Tovholdere"
     * @return a list of all employees
     */
    public ObservableList<Employee> getAllGuildManagersForTable()
    {
       allEmployees.addAll(manager.getAllEmployees());
       return allEmployees;
    }
    
        public void addEmployee(Employee employee) throws SQLException
    {
        allEmployees.add(employee);
        manager.addEmployee(employee);
    }
    
}
