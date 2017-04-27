/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
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
public class TileViewController implements Initializable
{

    @FXML
    private MenuBar btnMenu;
    @FXML
    private ImageView imageLogo;

    @FXML
    private AnchorPane mainPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setLogo();
    }

    @FXML
    private void logOn()
    {
        ViewGenerator viewGen = new ViewGenerator((Stage) mainPane.getScene().getWindow());
        try
        {
            viewGen.generateView("/frivilligetimer/gui/view/AdminView.fxml", true, StageStyle.DECORATED);
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

}
