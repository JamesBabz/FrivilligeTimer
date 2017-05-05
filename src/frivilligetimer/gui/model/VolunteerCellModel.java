/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Volunteer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author James
 */
public class VolunteerCellModel
{

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phoneNum = new SimpleStringProperty();
    
    private final Volunteer volunteer;

    public VolunteerCellModel(Volunteer volunteer)
    {
        this.volunteer = volunteer;
        name.set(volunteer.getFullName());
        email.set(volunteer.getEmail());
        phoneNum.set(volunteer.getPhoneNum());
    }
    
    public String getPhoneNum()
    {
        return phoneNum.get();
    }

    public void setPhoneNum(String value)
    {
        phoneNum.set(value);
    }

    public StringProperty PhoneNumProperty()
    {
        return phoneNum;
    }
    
    public String getEmail()
    {
        return email.get();
    }

    public void setEmail(String value)
    {
        email.set(value);
    }

    public StringProperty emailProperty()
    {
        return email;
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

    
    
}
