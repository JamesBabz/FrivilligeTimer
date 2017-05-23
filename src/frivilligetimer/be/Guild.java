/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author thomas
 */
public class Guild
{

    private int id;
    private final StringProperty name = new SimpleStringProperty();
    private ArrayList<Volunteer> volunteers = new ArrayList<>(); 
    private ArrayList<Employee> employees = new ArrayList<>();
    private final IntegerProperty hoursInCurrentPeriod = new SimpleIntegerProperty();

    public Guild(int id, String name)
    {
        this.id = id;
        this.name.set(name);
        
    }

    public Guild(String name)
    {
        this.name.set(name);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String value)
    {
        name.set(value);
    }

    public StringProperty nameProperty()
    {
        return name;
    }
    
    public void addVolunteer(Volunteer volunteer)
    {
        volunteers.add(volunteer);
    }
    
    public void removeVolunteer(Volunteer volunteer)
    {
        volunteers.remove(volunteer);
    }
    public ArrayList<Volunteer> getVolunteers()
    {
        return volunteers;
    }

    public void addEmployee(Employee employee)
    {
        employees.add(employee);
    }
    
    public void removeEmployee(Employee employee)
    {
        employees.remove(employee);
    }
    
    public ArrayList<Employee> getEmployees()
    {
        return employees;
    }
    
    @Override
    public String toString()
    {
        return name.get();
    }
    
    public int getHoursInCurrentPeriod()
    {
        return hoursInCurrentPeriod.get();
    }

    public void setHoursInCurrentPeriod(int value)
    {
        hoursInCurrentPeriod.set(value);
    }

    public IntegerProperty hoursInCurrentPeriodProperty()
    {
        return hoursInCurrentPeriod;
    }
    

}
