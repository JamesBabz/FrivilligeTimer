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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
    private Menu menuAddVolToGuild;
    @FXML
    private MenuItem menuItemRemoveVolunteer;
    @FXML
    private Menu menuAddEmployeeToGuild;
    @FXML
    private MenuItem menuItemRemoveEmployee;
    @FXML
    private MenuItem showShowEmailGuild;
    @FXML
    private TextField txtSearchField;
    @FXML
    private Button btnInactive;
    @FXML
    private TableColumn<Volunteer, Date> colInactive;
    @FXML
    private MenuItem menuItemEditeVolunteer;
    @FXML
    private MenuItem menuItemDeleteInactive;
    @FXML
    private MenuItem menuItemActivetInactive;
    @FXML
    private MenuItem menuItemDeleteVolunteer;
    @FXML
    private Button btnShowAllPeople;

    private final VolunteerModel volunteerModel;
    private final GuildModel guildModel;
    private final StaffModel staffModel;

    private Volunteer selectedVolunteer;
    private Employee selectedEmployee;
    private Guild selectedGuild;
    private boolean isActiveShowing = true;
    private ViewHandler viewHandler;
    private Stage stage;

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
        colInactive.setCellValueFactory(new PropertyValueFactory<>("inactiveSinceString"));

        colGuild.prefWidthProperty().bind(tableGuild.widthProperty());
        colVolunteer.prefWidthProperty().bind(tableVolunteer.widthProperty());
        colGuildManager.prefWidthProperty().bind(tableEmployee.widthProperty());

        setMenuItemsVisible();

        populateTables();
        searchOnUpdate();
    }

    private void setMenuItemsVisible()
    {
        menuItemRemoveEmployee.setVisible(false);
        menuItemRemoveVolunteer.setVisible(false);
        showShowEmailGuild.setVisible(false);
        menuItemDeleteInactive.setVisible(false);
        menuItemActivetInactive.setVisible(false);
    }

    /**
     * Default contructor
     */
    public AdminViewController()
    {
        volunteerModel = VolunteerModel.getInstance();
        guildModel = GuildModel.getInstance();
        staffModel = StaffModel.getInstance();
        viewHandler = new ViewHandler(stage);
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
        tableVolunteer.setItems(volunteerModel.getAllVolunteersForTable(true));
        tableEmployee.setItems(staffModel.getAllGuildManagersForTable(true));
        tableGuild.setItems(guildModel.getAllGuildsForTable(true));
    }

    @FXML
    private void addVolunteer()
    {

        viewHandler.generateView("/frivilligetimer/gui/view/AddVolunteer.fxml", false, StageStyle.DECORATED, true, "Tilføj Frivillig");

    }

    @FXML
    private void addEmployee()
    {
      

        viewHandler.generateView("/frivilligetimer/gui/view/AddEmployee.fxml", false, StageStyle.DECORATED, true, "Tilføj Medarbejder");

    }

    @FXML
    private void addGuild()
    {
        viewHandler.generateView("/frivilligetimer/gui/view/AddGuild.fxml", false, StageStyle.DECORATED, true, "Tilføj Laug");

    }

    @FXML
    private void handleDeleteVolunteer()
    {
        selectedVolunteer = tableVolunteer.getSelectionModel().getSelectedItem();
        tableVolunteer.getItems().remove(selectedVolunteer);
        tableVolunteer.getSelectionModel().clearSelection();
        volunteerModel.deleteVolunteer(selectedVolunteer);

        int selectedID = selectedVolunteer.getId();
        for (Guild guild : guildModel.getAllGuildsForTable(false))
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

        showAllActiveVolunteersInTable();
        showAllActiveEmployeesInTable();
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


        viewHandler.generateView("/frivilligetimer/gui/view/EditVolunteer.fxml", false, StageStyle.DECORATED, true, "Ændrer Frivillig");

    }

    @FXML
    private void editEmployee()
    {

        Employee selectedItem = tableEmployee.getSelectionModel().getSelectedItem();
        tableEmployee.getSelectionModel().clearSelection();
        staffModel.setSelectedEmployee(selectedItem);


        viewHandler.generateView("/frivilligetimer/gui/view/EditEmployee.fxml", false, StageStyle.DECORATED, true, "Ændrer Medarbejder");

    }

    @FXML
    private void editGuild()
    {

        Guild selectedItem = tableGuild.getSelectionModel().getSelectedItem();
        tableGuild.getSelectionModel().clearSelection();
        guildModel.setSelectedGuild(selectedItem);


        viewHandler.generateView("/frivilligetimer/gui/view/EditGuild.fxml", false, StageStyle.DECORATED, true, "Ændrer Laug");

    }

    @FXML
    private void handleStatClick()
    {

        viewHandler.generateView("/frivilligetimer/gui/view/StatisticView.fxml", false, StageStyle.DECORATED, true, "Statistik");

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
                                viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Dublering", "Denne person er allerede i dette laug");
                            }
                        }
                        if (isUnique)
                        {

                            try
                            {
                                guildModel.addVolunteerToGuild(guild, selectedVolunteer);
                            } catch (SQLException ex)
                            {
                                 viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
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
        selectedEmployee = tableEmployee.selectionModelProperty().getValue().getSelectedItem();
        guildsSubMenu = new ArrayList<>();
        for (Guild guild : tableGuild.getItems())
        {
            MenuItem item = new MenuItem(guild.getName());
            guildsSubMenu.add(item);
            menu.getItems().setAll(guildsSubMenu);
            item.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent event)
                {
                    boolean isUnique = true;
                    for (Employee employee : guild.getEmployees())
                    {
                        if (selectedEmployee.getId() == employee.getId())
                        {
                            isUnique = false;
                            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Dublering", "Denne person er i dette laug");
                        }
                    }
                    if (isUnique)
                    {
                        addEmployeeToGuildInDatabase(guild, selectedEmployee);
                    }
                }
            });
        }
    }

    /**
     * Sends the guild and employee further to model -> manager -> database.
     *
     * @param guild
     * @param selectedEmployee
     */
    private void addEmployeeToGuildInDatabase(Guild guild, Employee selectedEmployee)
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
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
        }
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
        selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
        selectedVolunteer = tableVolunteer.getSelectionModel().getSelectedItem();
        if (guildModel.getVolunteersInCurrentGuild().contains(selectedVolunteer))
        {

            volunteerModel.removeVolunteerFromAssignedGuild(selectedVolunteer, selectedGuild);
            tableVolunteer.getItems().remove(selectedVolunteer);

            int selectedID = selectedVolunteer.getId();
            for (Guild guild : guildModel.getAllGuildsForTable(false))
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
        selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
        if (guildModel.getEmployeesInCurrentGuild().contains(selectedEmployee));
        {
            staffModel.removeVolunteerFromAssignedGuild(selectedEmployee, selectedGuild);

            int selectedID = selectedEmployee.getId();
            for (Guild guild : guildModel.getAllGuildsForTable(false))
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
    private void ShowVolunteersInCurrentGuild(MouseEvent event)
    {
        if (event.getClickCount() == 2)
        {
            populateTablesForCurrentGuild();
            showEmployeesAssignedToGuild();
            menuItemRemoveEmployee.setVisible(true);
            menuItemRemoveVolunteer.setVisible(true);
            menuAddVolToGuild.setVisible(false);
            showShowEmailGuild.setVisible(true);
            tableEmployee.getSelectionModel().clearSelection();
        }
    }

    /**
     * Sets the employee and volunteer table for chosen guild
     */
    private void populateTablesForCurrentGuild()
    {
        selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
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
        tableEmployee.refresh();
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
    private void deleteAllInActive()
    {
        deleteInactiveVolunteers();

    }

    private void deleteInactiveVolunteers()
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Slet");
        alert.setContentText("Du er igang med at slette");
        alert.setHeaderText("Er du sikker på, at du vil fjerne alle inaktive personer og laug?");
        

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            volunteerModel.deleteInactiveVolunteers();
            try
            {
                guildModel.deleteInactiveGuilds();
            } catch (SQLException ex)
            {
                 viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
            }
        } 
    }

    /**
     * makes it possibel for the admin to search for Volunteers on first name,
     * last name and phonenummber
     */
    private void searchOnUpdate()
    {
        txtSearchField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> listener, String oldVal, String newVal)
            {
                volunteerModel.getSearchedVolunteers().clear();

                for (Volunteer volunteer : volunteerModel.getAllVolunteerInCurrentView())
                {
                    if (volunteer.getFullName().toLowerCase().contains(newVal.toLowerCase())
                            || volunteer.getPhoneNum().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                            || volunteer.getEmail().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                            && !volunteerModel.getSearchedVolunteers().contains(volunteer))
                    {
                        volunteerModel.getSearchedVolunteers().add(volunteer);

                    }
                }

                tableVolunteer.setItems(volunteerModel.getSearchedVolunteers());
            }
        });

        txtSearchField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> listener, Boolean oldValue, Boolean newValue)
            {
                if (tableGuild.getSelectionModel().getSelectedItem() == null)
                {
                    volunteerModel.setAllVolunteerInCurrentView(volunteerModel.getAllVolunteersForTable(true));
                } else
                {
                    volunteerModel.setAllVolunteerInCurrentView(tableGuild.getSelectionModel().getSelectedItem().getVolunteers());
                }
            }
        });
    }

    @FXML
    private void handleDragDetected(MouseEvent event)
    {

        selectedVolunteer = tableVolunteer.getSelectionModel().getSelectedItem();
        if (selectedVolunteer != null)
        {
            Dragboard db = tableVolunteer.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(selectedVolunteer.toString());
            db.setContent(content);
            event.consume();
        }

    }

    @FXML
    private void handleDragOver(DragEvent event)
    {

        Dragboard db = event.getDragboard();
        if (event.getDragboard().hasString())
        {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();

    }

    @FXML
    private void handleDragDropped(DragEvent event) throws SQLException
    {
        Dragboard db = event.getDragboard();
        boolean success = false;

        List<Guild> allGuilds = guildModel.getAllActiveGuilds();
        Node node = event.getPickResult().getIntersectedNode();
        if (node.toString().startsWith("Text"))
        {
            node = node.getParent();
        }

        String st = node.toString();
        st = st.substring(st.indexOf("'") + 1, st.length() - 1);

        if (event.getDragboard().hasString())
        {
            for (Volunteer volunteer : tableVolunteer.getItems())
            {
                if (volunteer.toString().equals(db.getString()))
                {
                    if (event.getTarget() != null)
                    {
                        if (allGuilds.toString().contains(st))
                        {
                            for (Guild guild : allGuilds)
                            {
                                if (guild.getName().equals(st) && !guild.getVolunteers().toString().contains(volunteer.toString()))
                                {
                                    guildModel.addVolunteerToGuild(guild, volunteer);
                                }
                            }
                        }
                    }
                }

            }

            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    @FXML
    private void handleMailClick()
    {
        selectedGuild = tableGuild.getSelectionModel().getSelectedItem();
        if (selectedGuild != null)
        {

            guildModel.setSelectedGuild(selectedGuild);

            viewHandler.generateView("/frivilligetimer/gui/view/EmailView.fxml", false, StageStyle.DECORATED, true, "Emails");
        }
    }

    @FXML
    private void changeVolunteersInTable()
    {
        if (isActiveShowing)
        {
            showAllInactiveVolunteersInTable();
            showDateScinceInactive();
            tableEmployee.setDisable(true);
            tableGuild.setDisable(true);
            btnShowAllPeople.setDisable(true);
        } else
        {
            showAllActiveVolunteersInTable();
            showOnlyVolunteers();
            tableEmployee.setDisable(false);
            tableGuild.setDisable(false);
            btnShowAllPeople.setDisable(false);
        }
        isActiveShowing = !isActiveShowing;
        changeButtonText();

    }

    private void showAllInactiveVolunteersInTable()
    {
        tableVolunteer.setItems(volunteerModel.getAllInactiveVolunteers());
        colVolunteer.setText("Inaktive Frivillige");
        menuItemRemoveVolunteer.setVisible(false);
        menuAddVolToGuild.setVisible(false);
        menuItemDeleteVolunteer.setVisible(false);
        menuItemEditeVolunteer.setVisible(false);
        menuItemDeleteInactive.setVisible(true);
        menuItemActivetInactive.setVisible(true);
    }

    private void changeButtonText()
    {
        if (!isActiveShowing)
        {
            btnInactive.setText("Vis aktive");
        } else
        {
            btnInactive.setText("Vis inaktive");
        }

    }

    @FXML
    private void handleDeleteInactiveVolunteer()
    {
        selectedVolunteer = tableVolunteer.getSelectionModel().getSelectedItem();
        tableVolunteer.getItems().remove(selectedVolunteer);
        tableVolunteer.getSelectionModel().clearSelection();
        volunteerModel.deleteInactiveVolunteer(selectedVolunteer);
    }

    @FXML
    private void handleActivteVolunteer()
    {
        selectedVolunteer = tableVolunteer.getSelectionModel().getSelectedItem();
        tableVolunteer.getItems().remove(selectedVolunteer);
        tableVolunteer.getSelectionModel().clearSelection();
        volunteerModel.activeteVolunteer(selectedVolunteer);
    }

    /**
     * Shows all the volunteers in the table when the user click "Vis alle
     * personer"
     */
    @FXML
    private void showAllPeopleInTable()
    {
        showAllActiveVolunteersInTable();

        showAllActiveEmployeesInTable();

        showShowEmailGuild.setVisible(false);
        tableGuild.getSelectionModel().clearSelection();
        volunteerModel.setAllVolunteerInCurrentView(volunteerModel.getAllVolunteersForTable(true));
        isActiveShowing = true;
        changeButtonText();
    }

    private void showAllActiveEmployeesInTable()
    {
        guildModel.getEmployeesInCurrentGuild().clear();
        tableEmployee.getItems().clear();
        staffModel.setAllGuildManagersForTable();
        tableEmployee.setItems(staffModel.getAllGuildManagersForTable(true));
        colGuildManager.setText("Medarbejdere");
        menuItemRemoveEmployee.setVisible(false);
    }

    private void showAllActiveVolunteersInTable()
    {
        tableVolunteer.setItems(volunteerModel.getAllVolunteersForTable(true));
        colVolunteer.setText("Frivillige");
        menuItemRemoveVolunteer.setVisible(false);
        menuAddVolToGuild.setVisible(true);
        menuItemDeleteInactive.setVisible(false);
        menuItemActivetInactive.setVisible(false);
        menuItemDeleteVolunteer.setVisible(true);
        menuItemEditeVolunteer.setVisible(true);
    }

    private void showDateScinceInactive()
    {
        colVolunteer.prefWidthProperty().unbind();
        colVolunteer.prefWidthProperty().bind(tableVolunteer.widthProperty().divide(2));
        colInactive.prefWidthProperty().bind(tableVolunteer.widthProperty().divide(2));
    }

    private void showOnlyVolunteers()
    {
        colVolunteer.prefWidthProperty().bind(tableVolunteer.widthProperty());
    }

}
