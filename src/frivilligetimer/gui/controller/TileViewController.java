/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.GuildModel;
import frivilligetimer.gui.model.StaffModel;
import frivilligetimer.gui.model.VolunteerCellModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author James
 */
public class TileViewController implements Initializable
{

//    private VolunteerCellBoardModel boardModel;
    private final VolunteerModel volunteerModel;
    private final GuildModel guildModel;
    private final StaffModel staffModel;

    @FXML
    private MenuBar btnMenu;
    @FXML
    private ImageView imageLogo;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ScrollPane containerForVolunteerBoard;
    @FXML
    private TilePane volunteerBoard;
    @FXML
    private ListView<String> listGuilds;
    @FXML
    private SplitPane splitPane;
    @FXML
    private AnchorPane listGuildsContainer;
    @FXML
    private Label lblWelcome;
    @FXML
    private Button btnLogOut;
    @FXML
    private ListView<String> listSearchResult;
    @FXML
    private TextField txtSearchField;
    @FXML
    private Label lblClearSearch;

    public TileViewController()
    {
//        boardModel = VolunteerCellBoardModel.getInstance();
        volunteerModel = VolunteerModel.getInstance();
        guildModel = GuildModel.getInstance();
        staffModel = StaffModel.getInstance();

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setLogo();
        volunteerBoard.prefWidthProperty().bind(containerForVolunteerBoard.widthProperty());
        btnLogOut.setVisible(false);
        lblWelcome.setVisible(false);
        listGuilds.setItems(guildModel.getAllGuildNames());
        addListener();
        addAllVolunteerCells();
        searchOnUpdate();
        txtSearchField.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> listener, Boolean oldValue, Boolean newValue)
            {
                if (!newValue && !listSearchResult.isFocused())
                {
                    listSearchResult.visibleProperty().set(false);
                }
            }
        });
    }

    private void addListener()
    {
        staffModel.levelProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
            {
                if (newValue.intValue() == 1)
                {
                    btnLogOut.setVisible(true);
                    lblWelcome.setVisible(true);
                    lblWelcome.setText("Velkommen " + staffModel.getLoggedInAs().getFullName());
                }
                else
                {
                    btnLogOut.setVisible(false);
                    lblWelcome.setVisible(false);
                }
            }
        });
    }

    private void addAllVolunteerCells()
    {
        Task task = new Task<Void>()
        {
            @Override
            public Void call() throws Exception
            {
                for (Volunteer volunteer : volunteerModel.getAllVolunteersForTable())
                {

                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (listGuilds.getSelectionModel().getSelectedIndex() == 0 || listGuilds.getSelectionModel().getSelectedItem() == null)
                            {
                                addNewVolunteerCellView(new VolunteerCellModel(volunteer));
                            }

                        }
                    });

                    Thread.sleep(100);
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    private void logOn()
    {
        ViewGenerator viewGen = new ViewGenerator((Stage) mainPane.getScene().getWindow());

        viewGen.generateView("/frivilligetimer/gui/view/Loginview.fxml", false, StageStyle.DECORATED, true, "Login");
    }

    /**
     * set the logo on MainView
     */
    private void setLogo()
    {
        Image imageMlogo = new Image("frivilligetimer/gui/image/Mlogo.png");
        imageLogo.setImage(imageMlogo);
    }

    /**
     * adds a new Tile with a volunteer
     *
     * @param model the model for the tile
     */
    private void addNewVolunteerCellView(VolunteerCellModel model)
    {
        try
        {
            volunteerBoard.getChildren().add(getVolunteerCellView(model));
        }
        catch (IOException ex)
        {
            Logger.getLogger(TileViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * creates the tile view
     *
     * @param model the model for the tile
     * @return returns a volunteer
     * @throws IOException
     */
    private Node getVolunteerCellView(VolunteerCellModel model) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frivilligetimer/gui/view/VolunteerSingleCell.fxml"));
        AnchorPane volunteer = loader.load();
        VolunteerSingleCellController controller = loader.getController();
        controller.setModel(model);

        TilePane.setMargin(volunteer, new Insets(5, 5, 5, 5));

        return volunteer;
    }

    @FXML
    private void updateVolunteerCells()
    {
        volunteerBoard.getChildren().remove(0, volunteerBoard.getChildren().size());
        if (listGuilds.getSelectionModel().getSelectedItem().equals("Alle Laug"))
        {
            guildModel.setSelectedGuild(null);
            addAllVolunteerCells();
        }
        else
        {
            addVolunteerCellForGuild();
        }
    }

    /**
     * Gets the selected guild and creates tiles for each volunteer in it
     */
    private void addVolunteerCellForGuild()
    {
        for (Guild guild : guildModel.getAllGuildsForTable())
        {
            if (listGuilds.getSelectionModel().getSelectedItem().equals(guild.getName()))
            {
                guildModel.setSelectedGuild(guild);
                guildModel.setVolunteersInGuild(guild);
            }
        }
        for (Volunteer volunteer : guildModel.getVolunteersInGuild())
        {
            addNewVolunteerCellView(new VolunteerCellModel(volunteer));
        }
    }

    @FXML
    private void handleLogOut()
    {
        staffModel.setLevel(2);
    }

    private void searchOnUpdate()
    {
        txtSearchField.textProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> listener, String oldString, String newVal)
            {
                volunteerModel.getSearchedVolunteers().clear();
                volunteerModel.setAllVolunteerInCurrentView(volunteerModel.getAllVolunteersForTable());

                for (Volunteer volunteer : volunteerModel.getAllVolunteerInCurrentView())
                {
                    if (volunteer.getFirstName().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                            || volunteer.getLastName().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                            || volunteer.getPhoneNum().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                            || volunteer.getFullName().toLowerCase().contains(newVal.toLowerCase())
                            && !volunteerModel.getSearchedVolunteers().contains(volunteer))
                    {
                        volunteerModel.getSearchedVolunteers().add(volunteer);
                    }
                }

                if (newVal != null && newVal.length() > 0)
                {
                    listSearchResult.visibleProperty().set(true);
                }
                else
                {
                    listSearchResult.visibleProperty().set(false);
                }

                listSearchResult.itemsProperty().set(volunteerModel.getSearchedVolunteerNames());

            }
        });
    }

    @FXML
    private void handleClearSearchField(MouseEvent event)
    {
        txtSearchField.clear();
    }

}
