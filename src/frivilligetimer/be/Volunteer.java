/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

import java.awt.image.BufferedImage;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The Buisness entity of the volunteer. This class extends Person
 *
 * @author Stephan, Jacob, Jens, Simon, Thomas
 */
public class Volunteer extends Person
{
    private String preference;
    private String note;
    private BufferedImage image;
    private final IntegerProperty hoursInCurrentPeriod = new SimpleIntegerProperty();



    /**
     * The contructor
     *
     * @param id Gets the id of the volunteer
     * @param firstName Gets the firstname of the volunteer
     * @param lastName Gets the lastname of the volunteer
     * @param phoneNum Gets the phonenumber of the volunteer
     * @param email Gets the emailadress of the volunteer
     * @param preference, the prefernce the volunteer has, that the employee can write down
     * @param note, a note that the employee kan write about a volunteer
     */
    public Volunteer(int id, String firstName, String lastName, String phoneNum, String email, String preference, String note, BufferedImage image)
    {
        super(id, firstName, lastName, phoneNum, email, image);
        this.preference = preference;
        this.note = note;
        this.image = image;
    }

    public Volunteer(String firstName, String lastName, String phoneNum, String email, BufferedImage image) {
        super(0, firstName, lastName, phoneNum, email, image);
    }
    
    
    
        public String getPreference()
    {
        return preference;
    }

    public void setPreference(String preference)
    {
        this.preference = preference;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }


    @Override
    public String toString()
    {
        return getFullName();
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
