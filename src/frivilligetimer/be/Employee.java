/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

import java.awt.image.BufferedImage;

/**
 * The Buisness entity of the employee. This class extends Person
 *
 * @author thomas
 */
public class Employee extends Person
{
  
    String password;
    
    /**
     * The contructor
     *
     * @param id Gets the id of the employee
     * @param firstName Gets the firstname of the employee
     * @param lastName Gets the lastname of the employee
     * @param phoneNum Gets the phonenumber of the employee
     * @param email Gets the emailadress of the employee
     * @param image
     */
    public Employee(int id, String firstName, String lastName, String phoneNum, String email, String password, BufferedImage image)
    {
        super(id, firstName, lastName, phoneNum, email, image);
   
    }

    public Employee(String firstName, String lastName, String phoneNum, String email, BufferedImage image) {
        super(0, firstName, lastName, phoneNum, email, image);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   
    
}
