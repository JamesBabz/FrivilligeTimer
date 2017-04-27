/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

/**
 * The Buisness entity of the volunteer. This class extends Person
 * @author Stephan, Jacob, Jens, Simon, Thomas
 */
public class Volunteer extends Person
{
    /**
 * The contructor 
 * @param id Gets the id of the volunteer
 * @param firstName Gets the firstname of the volunteer
 * @param lastName Gets the lastname of the volunteer
 * @param phoneNum Gets the phonenumber of the volunteer
 * @param email Gets the emailadress of the volunteer
     */
    public Volunteer(int id, String firstName, String lastName, String phoneNum, String email)
    {
        super(id, firstName, lastName, phoneNum, email);
    }
    
}

