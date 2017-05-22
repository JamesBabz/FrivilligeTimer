/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.gui.model.VolunteerCellModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    private VolunteerCellModel cellModel;
    private VolunteerModel model;
    private final Thread dataThread;

    @FXML
    private Label lblPhone;
    @FXML
    private Label lblFName;
    @FXML
    private Label lblLName;
    @FXML
    private AnchorPane pane;
    @FXML
    private ImageView imgV;

    public VolunteerSingleCellController()
    {
        this.dataThread = new Thread(imageLoader());
        Platform.runLater(dataThread);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = VolunteerModel.getInstance();
    }

    @FXML
    private void handleOpenHours()
    {
        model.setTileVolunteer(getModel().getVolunteer());
        ViewGenerator vg = new ViewGenerator((Stage) pane.getScene().getWindow());
        vg.generateView("/frivilligetimer/gui/view/AddVolunteerHours.fxml", false, StageStyle.DECORATED, true, "Tilføj Timer");

    }
    
    public VolunteerCellModel getModel()
    {
        return cellModel;
    }

    public void setModel(VolunteerCellModel model)
    {
        this.cellModel = model;
        lblFName.textProperty().bind(model.fNameProperty());
        lblLName.textProperty().bind(model.lNameProperty());
        lblPhone.textProperty().bind(model.PhoneNumProperty());

    }

    private Runnable imageLoader()
    {
        return new Thread(() ->
        {
            Image img = this.cellModel.getImage();
            imgV.setImage(img);
        });
    }
}
