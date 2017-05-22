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
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class for AdminView
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
    @FXML
    private MenuItem menuItemRemoveVolunteer;
    @FXML
    private Menu menuAddEmployeeToGuild;
    @FXML
    private MenuItem menuItemRemoveEmployee;

    private final VolunteerModel volunteerModel;
    private final GuildModel guildModel;
    private final StaffModel staffModel;

    private Volunteer selectedVolunteer;
    private Employee selectedEmployee;

    private List<MenuItem> guildsSubMenu;

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

        colGuild.prefWidthProperty().bind(tableGuild.widthProperty());
        colVolunteer.prefWidthProperty().bind(tableVolunteer.widthProperty());
        colGuildManager.prefWidthProperty().bind(tableEmployee.widthProperty());

        populateTables();

    }

    /**
     * Default contructor
     */
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
        tableVolunteer.setItems(volunteerModel.getAllVolunteersForTable());
        tableEmployee.setItems(staffModel.getAllGuildManagersForTable());
        tableGuild.setItems(guildModel.getAllGuildsForTable());
    }

    @FXML
    private void addVolunteer()
    {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());

        vg.generateView("/frivilligetimer/gui/view/AddVolunteer.fxml", false, StageStyle.DECORATED, true, "Tilføj Person");

    }

    @FXML
    private void addEmployee()
    {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());

        vg.generateView("/frivilligetimer/gui/view/AddEmployee.fxml", false, StageStyle.DECORATED, true, "Tilføj Person");

    }

    @FXML
    private void addGuild()
    {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());

        vg.generateView("/frivilligetimer/gui/view/AddGuild.fxml", false, StageStyle.DECORATED, true, "Tilføj Laug");

    }

    @FXML
    private void handleDeleteVolunteer()
    {
        selectedVolunteer = tableVolunteer.getSelectionModel().getSelectedItem();
        tableVolunteer.getItems().remove(selectedVolunteer);
        tableVolunteer.getSelectionModel().clearSelection();
        volunteerModel.deleteVolunteer(selectedVolunteer);

        int selectedID = selectedVolunteer.getId();
        for (Guild guild : guildModel.getAllGuildsForTable())
        {
            for (Volunteer volunteer : guild.getVolunteers())
            {
                if (volunteer.getId() == selectedID)
                {
                    guild.removeVolunteer(volunteer);
                    break;
                }
            }
        }
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

    @FXML
    private void editVolunteer()
    {

        Volunteer selectedItem = tableVolunteer.getSelectionModel().getSelectedItem();
        tableVolunteer.getSelectionModel().clearSelection();
        volunteerModel.setSelectedVolunteer(selectedItem);

        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());

        vg.generateView("/frivilligetimer/gui/view/EditVolunteer.fxml", false, StageStyle.DECORATED, true, "Ændrer Person");

    }

    @FXML
    private void editEmployee()
    {

        Employee selectedItem = tableEmployee.getSelectionModel().getSelectedItem();
        tableEmployee.getSelectionModel().clearSelection();
        staffModel.setSelectedEmployee(selectedItem);

        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());

        vg.generateView("/frivilligetimer/gui/view/EditEmployee.fxml", false, StageStyle.DECORATED, true, "Ændrer Medarbejder");

    }

    @FXML
    private void editGuild()
    {

        Guild selectedItem = tableGuild.getSelectionModel().getSelectedItem();
        tableGuild.getSelectionModel().clearSelection();
        guildModel.setSelectedGuild(selectedItem);

        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());

        vg.generateView("/frivilligetimer/gui/view/EditGuild.fxml", false, StageStyle.DECORATED, true, "Ændrer Laug");

    }

    @FXML
    private void handleStatClick()
    {
        ViewGenerator vg = new ViewGenerator((Stage) btnMenu.getScene().getWindow());

        vg.generateView("/frivilligetimer/gui/view/StatisticView.fxml", false, StageStyle.DECORATED, true, "Statistik");

    }

    /**
     * Sets all the guilds in the contextMenu, rightClick in the table. Adds the
     * selected volunteer to selected guild the metod is called in
     * handleContextGuildMenu
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
                    boolean isUnique = true;
                    if (item.getText().equals(guild.getName()))
                    {
                        for (Volunteer volunteer : guild.getVolunteers())
                        {

                            if (selectedVolunteer.getId() == volunteer.getId())
                            {
                                isUnique = false;
                            }
                        }
                        if (isUnique)
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

    /**
     * sets the menuitems with each guild in the contextmenu Adds the selected
     * employee to the selected guild method is called in
     * handleContextGuildMenuForEmployee()
     *
     * @param menu
     */
    private void addEmployeeToGuild(Menu menu)
    {
        Employee selectedEmployee = tableEmployee.selectionModelProperty().getValue().getSelectedItem();
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
                    boolean isUnique = true;
                    if (item.getText().equals(guild.getName()))
                    {
                        for (Employee employee : guild.getEmployees())
                        {
                            if (selectedEmployee.getId() == employee.getId())
                            {
                                isUnique = false;
                            }
                        }
                        if (isUnique)
                        {
                            try
                            {
                                guildModel.addEmployeeToGuild(guild, selectedEmployee);
                                
                                if (!colGuildManager.getText().equals("Medarbejdere"))
                                {
                                    populateTablesForCurrentGuild();
                                    showEmployeesAssignedToGuild();
                                }
                                
                            } catch (SQLException ex)
                            {
                                Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }

            });

        }
        menu.getItems().setAll(guildsSubMenu);

    }

    @FXML
    private void handleContextGuildMenuForEmployee()
    {
        addEmployeeToGuild(menuAddEmployeeToGuild);
    }

    /**
     * Removes the selected volunteer from the selected assigned guild removes
     * trough volunteermodel/manager -> db, and the list <volunteers> in be
     * Guild
     */
    private void removeVolunteerFromAssignedGuild()
    {
        Guild selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
        if (guildModel.getVolunteersInCurrentGuild().contains(selectedVolunteer))
        {
            volunteerModel.removeVolunteerFromAssignedGuild(selectedVolunteer, selectedGuild);
            tableVolunteer.getItems().remove(selectedVolunteer);

            int selectedID = selectedVolunteer.getId();
            for (Guild guild : guildModel.getAllGuildsForTable())
            {
                for (Volunteer volunteer : guild.getVolunteers())
                {
                    if (guild.getId() == selectedGuild.getId())
                    {
                        if (volunteer.getId() == selectedID)
                        {
                            guild.removeVolunteer(volunteer);
                            break;
                        }
                    }
                }
            }
        }
    }

    @FXML
    private void removeVolunteerFromGuild()
    {
        removeVolunteerFromAssignedGuild();
    }

    /**
     * remove selected Employee from the selected assigned guild removes trough
     * staffmodel/manager -> database, and and the list <employees> in BE guild
     */
    private void removeEmployeeFromAssignedGuild()
    {
        selectedEmployee = tableEmployee.getSelectionModel().getSelectedItem();
        Guild selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
        if (guildModel.getEmployeesInCurrentGuild().contains(selectedEmployee));
        {
            staffModel.removeVolunteerFromAssignedGuild(selectedEmployee, selectedGuild);

            int selectedID = selectedEmployee.getId();
            for (Guild guild : guildModel.getAllGuildsForTable())
            {
                for (Employee employee : guild.getEmployees())
                {
                    if (guild.getId() == selectedGuild.getId())
                    {
                        if (employee.getId() == selectedID)
                        {
                            guild.removeEmployee(employee);
                            break;
                        }
                    }
                }
            }
        }

        populateTablesForCurrentGuild();
        showEmployeesAssignedToGuild();

    }

    @FXML
    private void removeEmployeeFromGuild(ActionEvent event)
    {
        removeEmployeeFromAssignedGuild();
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

        guildModel.getEmployeesInCurrentGuild().clear();
        tableEmployee.setItems(staffModel.getAllGuildManagersForTable());
        colGuildManager.setText("Medarbejdere");

    }

    @FXML
    private void ShowVolunteersInCurrentGuild(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            populateTablesForCurrentGuild();
            showEmployeesAssignedToGuild();
        }
    }

    /**
     * Sets the employee and volunteer table for chosen guild
     */
    private void populateTablesForCurrentGuild()
    {
        Guild selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
        colVolunteer.setText("Frivillige i " + selectedGuild.getName());
        colGuildManager.setText("Medarbejdere i " + selectedGuild.getName());

        guildModel.getVolunteersInCurrentGuild().clear();
        guildModel.getVolunteersInCurrentGuild().addAll(selectedGuild.getVolunteers());

        guildModel.getEmployeesInCurrentGuild().clear();
        guildModel.getEmployeesInCurrentGuild().addAll(selectedGuild.getEmployees());

        tableVolunteer.setItems(guildModel.getVolunteersInCurrentGuild());

    }

    /**
     * Selects the employees who is assigned to the chosen guild the table sets
     * them
     */
    private void showEmployeesAssignedToGuild()
    {
        tableEmployee.getItems().clear();
        tableEmployee.setItems(staffModel.getAllGuildManagersForTable());
        for (Employee item : tableEmployee.getItems())
        {
            for (Employee employee : guildModel.getEmployeesInCurrentGuild())
            {
                if (item.getId() == employee.getId())
                {

                    setTextSizeOnEmployeesInCurrentGuild();
                }
            }
        }
    }

    private void setTextSizeOnEmployeesInCurrentGuild()
    {
        colGuildManager.setCellFactory(new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>()
        {
            @Override
            public TableCell<Employee, String> call(TableColumn<Employee, String> param)
            {
                return new TableCell<Employee, String>()
                {

                    @Override
                    public void updateItem(String item, boolean empty)
                    {
                        super.updateItem(item, empty);
                        if (!isEmpty())
                        {
                            for (Employee employeeToMark : guildModel.getEmployeesInCurrentGuild())
                            {

                                if (item.equals(employeeToMark.getFullName()))
                                {
                                    this.setTextFill(Color.GREEN);
                                    this.setFont(Font.font(16));

                                }

                            }
                        } else
                        {
                            this.setTextFill(Color.valueOf("#323232"));
                            this.setFont(Font.font(USE_COMPUTED_SIZE));

                        }
                        setText(item);
                    }
                };
            }
        });
    }

    @FXML
    private void deleteVolunteers()
    {
        deleteInactiveVolunteers();

    }

    private void deleteInactiveVolunteers()
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Fjern inaktive");
        alert.setHeaderText("Er du sikker på du vil fjerne alle inaktive personer og laug?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            volunteerModel.deleteInactiveVolunteers();
            try
            {
                guildModel.deleteInactiveGuilds();
            } catch (SQLException ex)
            {
                Logger.getLogger(AdminViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            // ... user chose CANCEL or closed the dialog
        }

    }

    /**
     * Shows an error dialog.
     *
     * @param title The title of the error.
     * @param header The header - subtitle.
     * @param content The error message.
     */
    private void showErrorDialog(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }

}
