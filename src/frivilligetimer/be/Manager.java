/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

/**
 * The Buisness entity of the manager. This class extends Person
 *
 * @author Stephan, Jacob, Jens, Simon, Thomas
 */
public class Manager extends Person
{

    private String password;

    public Manager(int id, String firstName, String lastName, String phoneNum, String email, String password)
    {
        super(id, firstName, lastName, phoneNum, email);
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
