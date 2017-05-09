/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.gui.model.VolunteerCellModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author James
 */
public class VolunteerSingleCellController implements Initializable
{

    @FXML
    private Label lblPhone;
    
    private VolunteerCellModel model;
    @FXML
    private Label lblFName;
    @FXML
    private Label lblLName;
    @FXML
    private AnchorPane pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
        
    }    

    public VolunteerCellModel getModel()
    {
        return model;
    }

    public void setModel(VolunteerCellModel model)
    {
        this.model = model;
        lblFName.textProperty().bind(model.fNameProperty());
        lblLName.textProperty().bind(model.lNameProperty());
        lblPhone.textProperty().bind(model.PhoneNumProperty());
    }

    @FXML
    private void handleOpenHours()
    {
        System.out.println("fsehfshjdsfkhusfdhuisdfhusfihusf");
        ViewGenerator vg = new ViewGenerator((Stage) pane.getScene().getWindow());
        vg.generateView("/frivilligetimer/gui/view/AddVolunteerHours.fxml", false, StageStyle.DECORATED, true, "Tilføj Timer");
    }
    
    
    
}
