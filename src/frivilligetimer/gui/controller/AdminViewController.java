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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
