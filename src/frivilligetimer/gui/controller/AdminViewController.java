/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class AdminViewController implements Initializable {

    @FXML
    private TableView<?> tbhfrivillige;
    @FXML
    private TableView<?> tbhtovholdere;
    @FXML
    private TableView<?> tbhlaug;
    @FXML
    private MenuBar btnmenu;
    @FXML
    private ImageView imageLogo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setLogo();
    } 
    
    private void setLogo()
    {
        Image imageMlogo = new Image("frivilligetimer/gui/image/Mlogo.png");
        imageLogo.setImage(imageMlogo);
//        imageLogo.setFitHeight(80);
//        imageLogo.setFitWidth(150);
    }
}
