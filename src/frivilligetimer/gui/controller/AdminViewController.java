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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    VolunteerModel volunteerModel;
    GuildModel guildModel;
    StaffModel staffModel;
    
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
        volunteerModel = VolunteerModel.getInstance();
        guildModel = GuildModel.getInstance();
        staffModel = StaffModel.getInstance();
        
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

    /**
     * Sets the data from the model to the tables
     */
    private void populateTables()
    {
        tbhFrivillige.setItems(volunteerModel.getAllVolunteersForTable());
        tbhTovholdere.setItems(staffModel.getAllGuildManagersForTable());
        tbhLaug.setItems(guildModel.getAllGuildForTable());
    }

    /**
     * Sets all employees in the tableview "Tovholdere"
     */
    private void setGuildManagerTable()
    {
        for (Employee employee : model.getAllEmployees())
        {
            allEmployees.add(employee);
        }
        tbhTovholdere.setItems(allEmployees);
    }
    
    private void setGuildTable()
    {
        for (Guild guild : model.getAllGuilds())
        {
            allGuilds.add(guild);
        }
        tbhLaug.setItems(allGuilds);
    }

    @FXML
    private void addVolunteer(ActionEvent event) throws IOException {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());
        vg.generateView("/frivilligetimer/gui/view/AddVolunteer.fxml", false, StageStyle.UTILITY);
        
    }
}
