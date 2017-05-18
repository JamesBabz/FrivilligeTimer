/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import frivilligetimer.be.Volunteer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author James
 */
public class VolunteerCellModel
{

    private final StringProperty fName = new SimpleStringProperty();
    private final StringProperty lName = new SimpleStringProperty();
    private final StringProperty phoneNum = new SimpleStringProperty();
    private final Image image;

    private final Volunteer volunteer;

    public VolunteerCellModel(Volunteer volunteer)
    {
        this.volunteer = volunteer;
        fName.set(volunteer.getFirstName());
        lName.set(volunteer.getLastName());
        phoneNum.set(volunteer.getPhoneNum());
        if (volunteer.getImage() != null)
        {
            image = SwingFXUtils.toFXImage(volunteer.getImage(), null);
        }
        else
        {
            image = new Image("frivilligetimer/gui/image/profile-placeholder.jpg");
        }

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

    public String getLName()
    {
        return lName.get();
    }

    public void setLName(String value)
    {
        lName.set(value);
    }

    public StringProperty lNameProperty()
    {
        return lName;
    }

    public String getFName()
    {
        return fName.get();
    }

    public void setFName(String value)
    {
        fName.set(value);
    }

    public StringProperty fNameProperty()
    {
        return fName;
    }

    public Volunteer getVolunteer()
    {
        return volunteer;
    }

    public Image getImage()
    {
        return image;
    }
    
}
