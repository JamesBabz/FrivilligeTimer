package frivilligetimer.be;

import java.awt.image.BufferedImage;

/**
 * The Buisness entity of the manager. This class extends Person
 *
 * @author Stephan, Jacob, Jens, Simon, Thomas
 */
public class Manager extends Person
{

    private String password;

    /**
     * The default contructor
     *
     * @param id Sets the id of the manager
     * @param firstName Sets the firstname of the manager
     * @param lastName Sets the lastname of the manager
     * @param phoneNum Sets the phonenumber of the manager
     * @param email Sets the emailadress of the manager
     * @param password The manager's password
     * @param image A buffered image.
     */
    public Manager(int id, String firstName, String lastName, String phoneNum, String email, String password, BufferedImage image)
    {
        super(id, firstName, lastName, phoneNum, email, image);
        this.password = password;
    }

    /**
     * Gets the manager's password as a non-encrypted literal string.
     *
     * @return the password.
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the manager's password as a non-encrypted literal string.
     *
     * @param password The password to set for the employee.
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

}
