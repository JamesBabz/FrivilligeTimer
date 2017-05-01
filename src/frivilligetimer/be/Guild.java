/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author thomas
 */
public class Guild
{

    private int id;
    private final StringProperty name = new SimpleStringProperty();

    public Guild(int id, String name)
    {
        this.id = id;
        this.name.set(name);
        
    }

    public Guild(String name)
    {
        this.name.set(name);
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name.get();
    }

    public void setName(String value)
    {
        name.set(value);
    }

    public StringProperty nameProperty()
    {
        return name;
    }

}
