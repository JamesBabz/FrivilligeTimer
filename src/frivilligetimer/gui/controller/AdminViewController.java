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
import java.util.ArrayList;
import java.util.List;
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
public class AdminViewController implements Initializable
{

    @FXML
    private ImageView imageLogo;
    @FXML
    private TableView<Volunteer> tbhFrivillige;
    @FXML
    private TableView<Employee> tbhTovholdere;
    @FXML
    private TableView<Guild> tbhLaug;
    @FXML
    private MenuBar btnMenu;
    @FXML
    private TableColumn<Volunteer, String> colVolunteer;
    @FXML
    private TableColumn<Employee, String> colGuldManager;
    @FXML
    private TableColumn<Guild, String> colGuild;

    VolunteerModel model;
    private List<Employee> allVolunteers;
    private List<Employee> allEmployees;
    private List<Guild> allGuilds;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setLogo();
        colVolunteer.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGuldManager.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGuild.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        populateTables();

    }

    public AdminViewController()
    {
        model = VolunteerModel.getInstance();
        allVolunteers = new ArrayList<>();
        allEmployees = new ArrayList<>();
        allGuilds = new ArrayList<>();

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
    
    private void  populateTables()
    {
       tbhFrivillige.setItems(model.getAllVolunteersForTable());
       tbhTovholdere.setItems(model.getAllGuildManagersForTable());
       tbhLaug.setItems(model.getAllGuildForTable());
    }

   
}
