package frivilligetimer.be;

import java.awt.image.BufferedImage;
import javafx.beans.binding.Bindings;
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
     * The default contructor
     *
     * @param id Sets the id of the person
     * @param firstName Sets the firstname of the person
     * @param lastName Sets the lastname of the person
     * @param phoneNum Sets the phonenumber of the person
     * @param email Sets the emailadress of the person
     */
    public Person(int id, String firstName, String lastName, String phoneNum, String email, BufferedImage image)
    {
        this.id = id;
        this.firstName.set(firstName);
        this.lastName.set(lastName);
        this.phoneNum = phoneNum;
        this.email = email;
        this.image = image;
    }

    /**
     * Gets the id of this person.
     *
     * @return the id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Sets the id of this person.
     *
     * @param id The id to assign to this person.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * Gets the first name of this person.
     *
     * @return the first name.
     */
    public String getFirstName()
    {
        return firstName.get();
    }

    /**
     * Sets the first name of this person.
     *
     * @param value The first name to assign to this person.
     */
    public void setFirstName(String value)
    {
        firstName.set(value);
    }

    /**
     * Gets the first name of this person represented by an observable String
     * Property
     *
     * @return a string property that represents this person's first name.
     */
    public StringProperty firstNameProperty()
    {
        return firstName;
    }

    /**
     * Gets the last name of this person.
     *
     * @return the last name.
     */
    public String getLastName()
    {
        return lastName.get();
    }

    /**
     * Sets the last name of this person.
     *
     * @param lastName A string value representing this person's last name.
     */
    public void setLastName(String lastName)
    {
        this.lastName.set(lastName);
    }

    /**
     * Gets the last name of this person represented by an observable String
     * Property
     *
     * @return a string property that represents this person's last name.
     */
    public StringProperty lastNameProperty()
    {
        return lastName;
    }

    /**
     * Gets the phone number of this person.
     *
     * @return the phone number represented by a string.
     */
    public String getPhoneNum()
    {
        return phoneNum;
    }

    /**
     * Sets this person's phone number.
     *
     * @param phoneNum The phone number to be assigned to this person.
     */
    public void setPhoneNum(String phoneNum)
    {
        this.phoneNum = phoneNum;
    }

    /**
     * Gets the e-mail of this person.
     *
     * @return the e-mail.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets this person's e-mail.
     *
     * @param email The e-mail to be assigned to this person.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Gets the full name of this person.
     *
     * @return the full name.
     */
    public String getFullName()
    {
        return fullName.get();
    }

    /**
     * Sets the full name of this person.
     *
     * @param fullName A string value representing this person's full name.
     */
    public void setFullName(String fullName)
    {
        this.fullName.set(fullName);
    }

    /**
     * Gets the full name of this person represented by an observable String
     * Property
     *
     * @return a string property that represents this person's full name.
     */
    public StringProperty fullNameProperty()
    {
        this.fullName.bind(Bindings.concat(firstName , " " , lastName));
        return fullName;
    }

    /**
     * Gets the profile picture of this person.
     *
     * @return a buffered image representing this person's profile picture.
     */
    public BufferedImage getImage()
    {
        return image;
    }

    /**
     * Sets the profile picture of this person.
     *
     * @param image A buffered image representing this person's profile picture.
     */
    public void setImage(BufferedImage image)
    {
        this.image = image;
    }

}
