/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.gui.model.TileModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author James
 */
public class VolunteerSingleCellController implements Initializable
{

    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPhone;
    
    private TileModel model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }    

    public void setTileModel(TileModel model)
    {
        this.model = model;
        lblName.textProperty().bind(model.nameProperty());
        lblName.requestLayout();
        lblEmail.textProperty().bind(model.emailProperty());
        lblEmail.requestLayout();
        lblPhone.textProperty().bind(model.phoneProperty());
        lblPhone.requestLayout();
    }
    
}
