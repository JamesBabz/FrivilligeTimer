/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.GuildModel;
import frivilligetimer.gui.model.StaffModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    private TableView<Volunteer> tableVolunteer;
    @FXML
    private TableView<Employee> tableEmployee;
    @FXML
    private TableView<Guild> tableGuild;
    @FXML
    private MenuBar btnMenu;
    @FXML
    private TableColumn<Volunteer, String> colVolunteer;
    @FXML
    private TableColumn<Employee, String> colGuildManager;
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

    private final VolunteerModel volunteerModel;
    private final GuildModel guildModel;
    private final StaffModel staffModel;
    private Volunteer selectedVolunteer;
    private List<MenuItem> guildsSubMenu;
    private ObservableList<Volunteer> volunteersInCurrentGuild;
    @FXML
    private Button btnTestGuild;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setLogo();
        colVolunteer.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGuildManager.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colGuild.setCellValueFactory(new PropertyValueFactory<>("name"));

        populateTables();
        populateGuilds();

    }

    /**
     * Default contructor
     */
    public AdminViewController()
    {
        volunteerModel = VolunteerModel.getInstance();
        guildModel = GuildModel.getInstance();
        staffModel = StaffModel.getInstance();
        volunteersInCurrentGuild = FXCollections.observableArrayList();

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
        tableVolunteer.setItems(volunteerModel.getAllVolunteersForTable());
        tableEmployee.setItems(staffModel.getAllGuildManagersForTable());
        tableGuild.setItems(guildModel.getAllGuildForTable());
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
    private void addEmployee()
    {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());
        try
        {
            vg.generateView("/frivilligetimer/gui/view/AddEmployee.fxml", false, StageStyle.DECORATED, true, "drpgjioerhg");
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

    /**
     * Sets all the guilds in the contextMenu, rightClick in the table.
     *
     * @param menu of all the guilds to be shown
     */
    private void addVolunteerToGuild(Menu menu)
    {
        selectedVolunteer = tableVolunteer.selectionModelProperty().getValue().getSelectedItem();
        guildsSubMenu = new ArrayList<>();

        for (Guild guild : tableGuild.getItems())
        {
            MenuItem item = new MenuItem(guild.getName());
            guildsSubMenu.add(item);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    if (item.getText().equals(guild.getName()))
                    {
                        try
                        {
                            guildModel.addVolunteerToGuild(guild, selectedVolunteer);
                        } catch (SQLException ex)
                        {
                            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });

        }
        menu.getItems().setAll(guildsSubMenu);

    }

    @FXML
    private void handleContextGuildMenu()
    {
        addVolunteerToGuild(menuAddVolToGuild);

    }

    @FXML
    private void handleDeleteVolunteer()
    {
        Volunteer selectedItem = tableVolunteer.getSelectionModel().getSelectedItem();
        tableVolunteer.getItems().remove(selectedItem);
        tableVolunteer.getSelectionModel().clearSelection();
        volunteerModel.deleteVolunteer(selectedItem);
    }

    @FXML
    private void handleDeleteGuild()
    {
        Guild selectedItem = tableGuild.getSelectionModel().getSelectedItem();
        tableGuild.getItems().remove(selectedItem);
        tableGuild.getSelectionModel().clearSelection();
        guildModel.deleteGuild(selectedItem);
    }

    @FXML
    private void handleDeleteEmployee()
    {
        Employee selectedItem = tableEmployee.getSelectionModel().getSelectedItem();
        tableEmployee.getItems().remove(selectedItem);
        tableEmployee.getSelectionModel().clearSelection();
        staffModel.deleteEmployee(selectedItem);
    }

    /**
     * Gets the volunteers in each guild
     */
    public void populateGuilds()
    {

        for (String string : guildModel.getAllVolunteersInGuilds())
        {
            String[] data = string.split(",");
            int uid = Integer.parseInt(data[0].trim());
            int laugid = Integer.parseInt(data[1].trim());

            for (Guild guild : guildModel.getAllGuildForTable())
            {
                if (laugid == guild.getId())
                {
                    for (Volunteer volunteer : volunteerModel.getAllVolunteersForTable())
                    {
                        if (uid == volunteer.getId())
                        {
                            guild.addVolunteer(volunteer);
                            volunteersInCurrentGuild.add(volunteer);
                        }
                    }
                }
            }
        }
    }



    /**
     * Shows all the volunteers in the table when the user click "Vis alle
     * personer"
     */
    @FXML
    private void ShowAllVolunteersInTable()
    {
        tableVolunteer.setItems(volunteerModel.getAllVolunteersForTable());
        colVolunteer.setText("Frivillige");
    }

    @FXML
    private void ShowVolunteersInCurrentGuild()
    {
        Guild selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
        colVolunteer.setText("Frivillige i " + selectedGuild.getName());
        for (Guild guild : guildModel.getAllGuildForTable())
        {
            if (guild == selectedGuild)
            {
                
                volunteersInCurrentGuild.clear();
                volunteersInCurrentGuild.addAll(selectedGuild.getVolunteers());
            }
        }
        tableVolunteer.setItems(volunteersInCurrentGuild);
        tableGuild.getSelectionModel().select(selectedGuild);
    }
    
        @FXML
    private void handleTestBtn(ActionEvent event)
    {

    }
    
    

    @FXML
    private void editVolunteer()
    {
        
        Volunteer selectedItem = tableVolunteer.getSelectionModel().getSelectedItem();
        tableVolunteer.getSelectionModel().clearSelection();
        volunteerModel.setSelectedVolunteer(selectedItem);
        
        
        
           ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());
        try
        {
            vg.generateView("/frivilligetimer/gui/view/EditVolunteer.fxml", false, StageStyle.DECORATED, true, "Ændrer person");
        } catch (IOException ex)
        {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    
    
        @FXML
        private void editEmployee()
        {
        
        Employee selectedItem = tableEmployee.getSelectionModel().getSelectedItem();
        tableEmployee.getSelectionModel().clearSelection();
        staffModel.setSelectedEmployee(selectedItem);
        
        
        
           ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());
        try
        {
            vg.generateView("/frivilligetimer/gui/view/EditEmployee.fxml", false, StageStyle.DECORATED, true, "Ændrer medarbejder");
        } catch (IOException ex)
        {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
                     
    }
        
        
        
        @FXML
        private void editGuild()
        {
        
        Guild selectedItem = tableGuild.getSelectionModel().getSelectedItem();
        tableGuild.getSelectionModel().clearSelection();
        guildModel.setSelectedGuild(selectedItem);
        
        
        
           ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());
        try
        {
            vg.generateView("/frivilligetimer/gui/view/EditGuild.fxml", false, StageStyle.DECORATED, true, "Ændrer medarbejder");
        } catch (IOException ex)
        {
            Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
      
                     
    }
       

}


