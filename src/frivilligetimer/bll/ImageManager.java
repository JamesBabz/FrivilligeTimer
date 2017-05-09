/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.bll;

import frivilligetimer.be.Person;
import frivilligetimer.dal.DBManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author sBirke
 */
public class ImageManager
{
    private DBManager dbManager;

    public ImageManager() throws IOException, SQLException
    {
        this.dbManager = new DBManager();
    }
    
    /**
     * @param person
     * @param localImagePath
     * @throws SQLException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void updateImage(Person person, String localImagePath) throws SQLException, FileNotFoundException, IOException
    {
        dbManager.updateImage(person, localImagePath);
    }
}
