/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

/**
 * 
 * @author thomas
 */
public abstract class Person 
{
        private int id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNum;
    private String email;
    
    
/**
 * The contructor 
 * @param id Gets the id of the person
 * @param firstName Gets the firstname of the person
 * @param lastName Gets the lastname of the person
 * @param phoneNum Gets the phonenumber of the person
 * @param email Gets the emailadress of the person
 */
    public Person(int id, String firstName, String lastName, String phoneNum, String email)
    {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.email = email;
        this.fullName = firstName + " " + lastName;
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
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public String getFullName()
    {
        return fullName;
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
    
    
}

