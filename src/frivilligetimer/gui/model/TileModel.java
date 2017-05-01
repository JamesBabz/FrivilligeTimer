/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Volunteer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James
 */
public class TileModel
{

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty phone = new SimpleStringProperty();

    private Volunteer volunteer;

    public TileModel(Volunteer volunteer){
            this.volunteer = volunteer;
            name.set(volunteer.getFullName());
            email.set(volunteer.getEmail());
            phone.set(volunteer.getPhoneNum());
    }
    public String getPhone()
    {
        return phone.get();
    }

    public void setPhone(String value)
    {
        phone.set(value);
    }

    public StringProperty phoneProperty()
    {
        return phone;
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
