/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

import java.awt.image.BufferedImage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author thomas
 */
public abstract class Person
{

    private int id;
    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final StringProperty fullName = new SimpleStringProperty();

    private String phoneNum;
    private String email;
    private BufferedImage image;


    /**
     * The contructor
     *
     * @param id Gets the id of the person
     * @param firstName Gets the firstname of the person
     * @param lastName Gets the lastname of the person
     * @param phoneNum Gets the phonenumber of the person
     * @param email Gets the emailadress of the person
     */
    public Person(int id, String firstName, String lastName, String phoneNum, String email, BufferedImage image)
    {
        this.id = id;
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNum = phoneNum;
        this.email = email;
        this.fullName.setValue(firstName + " " + lastName);
        this.image = image;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getFirstName()
    {
        return firstName.get();
    }

    public void setFirstName(String value)
    {
        firstName.set(value);
    }

    public StringProperty firstNameProperty()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName.get();
    }

    public void setLastName(String value)
    {
        lastName.set(value);
    }

    public StringProperty lastNameProperty()
    {
        return lastName;
    }

    public String getPhoneNum()
    {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFullName()
    {
        return fullName.get();
    }

    public void setFullName(String value)
    {
        fullName.set(value);
    }

    public StringProperty fullNameProperty()
    {
        return fullName;
    }
    
    public BufferedImage getImage()
    {
        return image;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

}
