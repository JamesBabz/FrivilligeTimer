package frivilligetimer.be;

import java.awt.image.BufferedImage;

/**
 * The Buisness entity of the employee. This class extends Person
 *
 * @author thomas
 */
public class Employee extends Person
{

    private String password;

    /**
     * The default contructor
     *
     * @param id Sets the id of the employee
     * @param firstName Sets the firstname of the employee
     * @param lastName Sets the lastname of the employee
     * @param phoneNum Sets the phonenumber of the employee
     * @param email Sets the emailadress of the employee
     * @param password The password
     * @param image A buffered image.
     */
    public Employee(int id, String firstName, String lastName, String phoneNum, String email, String password, BufferedImage image)
    {
        super(id, firstName, lastName, phoneNum, email, image);
        this.password = password;

    }

    /**
     * Alternate constructor
     *
     * @param firstName The first name.
     * @param lastName The last name.
     * @param phoneNum The phone number.
     * @param email The e-mail.
     * @param image A buffered image.
     */
    public Employee(String firstName, String lastName, String phoneNum, String email, BufferedImage image)
    {
        super(0, firstName, lastName, email, phoneNum, image);
    }

    /**
     * Gets the employee's password as a non-encrypted literal string.
     *
     * @return the password.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the employee's password as a non-encrypted literal string.
     *
     * @param password The password to set for the employee.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }
}
