/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Employee;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.VolunteerModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class AdminViewController implements Initializable {
    @FXML
    private ImageView imageLogo;
    @FXML
    private TableView<Volunteer> tbhFrivillige;
    @FXML
    private TableView<?> tbhTovholdere;
    @FXML
    private TableView<?> tbhLaug;
    @FXML
    private MenuBar btnMenu;
    
    VolunteerModel model;
    private final ObservableList<Volunteer> allVolunteers;
    @FXML
    private TableColumn<Volunteer, String> colVolunteer;
    @FXML
    private TableColumn<Employee, String> colGuldManager;
    @FXML
    private TableColumn<Guild, String> colGuild;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       setLogo();
       colVolunteer.setCellValueFactory(new PropertyValueFactory<>("fullName"));
       
       setVolunteersTable();
    } 

    
    public AdminViewController()
    {
        model = VolunteerModel.getInstance();
        allVolunteers = FXCollections.observableArrayList();
    }
    
    
    /**
     * set the logo on AdimView
     */
    private void setLogo()
    {
        Image imageMlogo = new Image("frivilligetimer/gui/image/Mlogo.png");
        imageLogo.setImage(imageMlogo);
//        imageLogo.setFitHeight(80);
//        imageLogo.setFitWidth(150);
    }
    
    /**
     * Sets all volunteers in the tableview "Frivillige"
     */
    private void setVolunteersTable()
    {
          for (Volunteer volunteer : model.getAllVolunteers())
        {
            allVolunteers.add(volunteer);
        }
          tbhFrivillige.setItems(allVolunteers);
          
    }
}
