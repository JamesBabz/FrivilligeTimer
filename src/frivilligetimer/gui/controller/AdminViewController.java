/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Employee;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.GuildModel;
import frivilligetimer.gui.model.StaffModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    private MenuItem Volunteradd;
    @FXML
    private MenuItem guildAdd;
    @FXML
    private ContextMenu contextVolunteer;
    @FXML
    private ContextMenu contextEmployee;
    @FXML
    private ContextMenu contextGuild;
        @FXML
    private Menu menuAddVolToGuild;

    VolunteerModel volunteerModel;
    GuildModel guildModel;
    StaffModel staffModel;


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
        volunteerModel = VolunteerModel.getInstance();
        guildModel = GuildModel.getInstance();
        staffModel = StaffModel.getInstance();



    }

    /**
     * set the logo on AdimView
     */
    private void setLogo()
    {
        Image imageMlogo = new Image("frivilligetimer/gui/image/Mlogo.png");
        imageLogo.setImage(imageMlogo);
    }

    /**
     * Sets the data from the model to the tables
     */
    private void populateTables()
    {
        tbhFrivillige.setItems(volunteerModel.getAllVolunteersForTable());
        tbhTovholdere.setItems(staffModel.getAllGuildManagersForTable());
        tbhLaug.setItems(guildModel.getAllGuildForTable());
    }
    

    @FXML
    private void addVolunteer()
    {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());
        try
        {
            vg.generateView("/frivilligetimer/gui/view/AddVolunteer.fxml", false, StageStyle.DECORATED, true, "drpgjioerhg");
        } catch (IOException ex)
        {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void addGuild()
    {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());
        try
        {
            vg.generateView("/frivilligetimer/gui/view/AddGuild.fxml", false, StageStyle.DECORATED, true, "gnærongietrh");
        } catch (IOException ex)
        {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void addVolunteerToGuild(Menu menu)
    {
        List<MenuItem> guildsSubMenu = new ArrayList<>();
        
        for (Guild guild : tbhLaug.getItems())
        {
            MenuItem item = new MenuItem(guild.getName());
            guildsSubMenu.add(item);
        }
        menu.getItems().setAll(guildsSubMenu);
    }

    @FXML
    private void handleContextGuildMenu()
    {
        addVolunteerToGuild(menuAddVolToGuild);
        
    }

}
