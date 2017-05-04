/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.VolunteerCellBoardModel;
import frivilligetimer.gui.model.VolunteerCellModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author James
 */
public class TileViewController implements Initializable
{

    private VolunteerCellBoardModel boardModel;
    private VolunteerModel volunteerModel;

    @FXML
    private MenuBar btnMenu;
    @FXML
    private ImageView imageLogo;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ScrollPane containerForVolunteerBoard;
    @FXML
    private TilePane volunteerBoard;

    public TileViewController()
    {
        boardModel = VolunteerCellBoardModel.getInstance();
        volunteerModel = VolunteerModel.getInstance();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setLogo();
        volunteerBoard.prefWidthProperty().bind(containerForVolunteerBoard.widthProperty());
        boardModel.getAllVolunteers();
        for (Volunteer volunteer : volunteerModel.getAllVolunteersForTable())
        {
            addNewVolunteerCellView(new VolunteerCellModel(volunteer));
        }
    }

    @FXML
    private void logOn()
    {
        ViewGenerator viewGen = new ViewGenerator((Stage) mainPane.getScene().getWindow());
        try
        {
            viewGen.generateView("/frivilligetimer/gui/view/AdminView.fxml", true, StageStyle.DECORATED, false, "Admin");
        }
        catch (IOException ex)
        {
            Logger.getLogger(TileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * set the logo on MainView
     */
    private void setLogo()
    {
        Image imageMlogo = new Image("frivilligetimer/gui/image/Mlogo.png");
        imageLogo.setImage(imageMlogo);
    }
/**
 * adds a new Tile with a volunteer
 * @param model the model for the tile
 */
    private void addNewVolunteerCellView(VolunteerCellModel model)
    {
        try
        {
            volunteerBoard.getChildren().add(getVolunteerCellView(model));
        }
        catch (IOException ex)
        {
            Logger.getLogger(TileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * creates the tile view
     * @param model the model for the tile
     * @return returns a volunteer
     * @throws IOException 
     */
    private Node getVolunteerCellView(VolunteerCellModel model) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frivilligetimer/gui/view/VolunteerSingleCell.fxml"));
        AnchorPane volunteer = loader.load();
        VolunteerSingleCellController singleCellController = loader.getController();
        singleCellController.setModel(model);
        return volunteer;
    }

}
