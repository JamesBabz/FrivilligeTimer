/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author James
 */
public class VolunteerCellBoardModel
{
    private static VolunteerCellBoardModel INSTANCE;

    private ObservableList<VolunteerCellModel> allVolunteers;

    public static synchronized VolunteerCellBoardModel getInstance()
    {
        if (INSTANCE == null)
        {
            INSTANCE = new VolunteerCellBoardModel();
        }
        return INSTANCE;
    }

    private VolunteerCellBoardModel()
    {
        allVolunteers = FXCollections.observableArrayList();
    }
    
    public ObservableList<VolunteerCellModel> getAllVolunteers(){
        return allVolunteers;
    }
    
}
