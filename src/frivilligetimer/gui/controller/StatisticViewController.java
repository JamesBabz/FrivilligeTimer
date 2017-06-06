/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Guild;
import frivilligetimer.be.Volunteer;
import frivilligetimer.gui.model.GuildModel;
import frivilligetimer.gui.model.VolunteerModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class StatisticViewController implements Initializable
{

    private final GuildModel guildModel;
    private final VolunteerModel volunteerModel;
    private Stage stage;
    private ViewHandler viewHandler;

    @FXML
    private TableView<Guild> tblGuildsOverview;
    @FXML
    private TableView<Volunteer> tblVolunteersOverview;
    @FXML
    private TableColumn<Guild, String> colGuildName;
    @FXML
    private TableColumn<Guild, Integer> colGuildHours;
    @FXML
    private TableColumn<Volunteer, String> colVolunteerName;
    @FXML
    private TableColumn<Volunteer, Integer> colVolunteerHours;
    @FXML
    private ComboBox<Object> cmbGuilds;
    @FXML
    private DatePicker dpFrom;
    @FXML
    private DatePicker dpTo;
    @FXML
    private VBox graphContainer;

    public StatisticViewController()
    {
        guildModel = GuildModel.getInstance();
        volunteerModel = VolunteerModel.getInstance();
        viewHandler = new ViewHandler(stage);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        setInitialGraph();
        initiateComboBox();
        setTables();
    }

    /**
     * Sets the columns for each table and which items go into the tables
     */
    private void setTables()
    {
        colGuildName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGuildHours.setCellValueFactory(new PropertyValueFactory<>("hoursInCurrentPeriod"));
        tblGuildsOverview.setItems(guildModel.getAllGuildsForTable(true));
        colVolunteerName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colVolunteerHours.setCellValueFactory(new PropertyValueFactory<>("hoursInCurrentPeriod"));
        tblVolunteersOverview.setItems(guildModel.getVolunteersInGuild());
    }

    /**
     * Creates the combobox
     */
    private void initiateComboBox()
    {
        cmbGuilds.getItems().add("Alle Personer");
        cmbGuilds.getItems().addAll(guildModel.getAllGuilds());
        cmbGuilds.getSelectionModel().selectFirst();
    }

    /**
     * Sets the initial barchart to show for the current month
     */
    private void setInitialGraph()
    {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        from.set(Calendar.DATE, 1);
        int daysInMonth = to.getActualMaximum(Calendar.DATE);
        to.set(Calendar.DATE, daysInMonth);

        dpFrom.setValue(from.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dpTo.setValue(to.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        setValuesForGuilds(from.getTime(), to.getTime());
    }

    /**
     * Creates the barchart with bars for each guild in the model
     *
     * @param from - The date where the graph should start
     * @param to - the date where the graph should end
     */
    private void setValuesForGuilds(Date from, Date to)
    {
        BarChart barGuilds = (BarChart) initiateChart(true);
        List<Guild> allGuilds = guildModel.getAllGuilds();
        XYChart.Series allGuildsSeries = new XYChart.Series();
        allGuildsSeries.setName("Timer");
        barGuilds.getData().add(allGuildsSeries);

        Task task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                for (Guild guild : allGuilds)
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            populateBarChart(guild, from, to, allGuildsSeries);
                        }

                    });
                    Thread.sleep(200);
                }

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Sets each bar in the barchart
     *
     * @param guild - Current guild to be sat
     * @param from - The from date
     * @param to - The to date
     * @param allGuildsSeries - The XYChart for the graph where the data is put
     */
    private void populateBarChart(Guild guild, Date from, Date to, XYChart.Series allGuildsSeries)
    {
        guildModel.setVolunteersInGuild(guild);
        int hours = 0;
        try
        {
            hours = guildModel.getWorkedHoursInPeriodForGuild(from, to, guild.getId());
            guild.setHoursInCurrentPeriod(hours);
        }
        catch (SQLException | IOException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Diagram Fejl", "Fejl under oprettelse af diagram",
                    "Der skete en fejl under oprettelse af det valgte diagram. prøv venligst igen.");
        }
        allGuildsSeries.getData().add(new XYChart.Data(guild.getName(), hours));
    }

    /**
     * Initiates the threads to create the lines for the current guild
     *
     * @param from - The date where the graph should start
     * @param to - the date where the graph should end
     * @param guild - The current guild selected
     */
    private void setValuesForVolunteers(Date from, Date to, Guild guild)
    {
        LineChart lineVolunteers = (LineChart) initiateChart(false);

        guildModel.setSelectedGuild(guild);
        guildModel.setVolunteersInGuild(guild);

        Task task = new Task<Void>()
        {
            @Override
            protected Void call() throws Exception
            {
                for (Volunteer volunteer : guildModel.getVolunteersInGuild())
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            populateLineChart(volunteer, from, to, guild, lineVolunteers);
                        }
                    });
                    Thread.sleep(200);
                }

                return null;
            }
        };

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Creates each line for every volunteer in a guild
     *
     * @param volunteer - The current volunteer for whom the line will be
     * created
     * @param from - The from date
     * @param to - The to date
     * @param guild - The guild that contains the volunteers
     * @param lineVolunteers - The chart to add the line to
     */
    private void populateLineChart(Volunteer volunteer, Date from, Date to, Guild guild, LineChart lineVolunteers)
    {
        XYChart.Series currentVolunteer = new XYChart.Series();
        currentVolunteer.setName(volunteer.getFullName());
        Calendar date = Calendar.getInstance();
        date.setTime(from);
        int hoursInCurrentPeriod = 0;

        TreeMap<java.sql.Date, Integer> lineChartValues = null;
        try
        {
            lineChartValues = volunteerModel.getWorkedHoursInPeriodForVolunteer(from, to, volunteer.getId(), guild.getId());
        }
        catch (SQLException | IOException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Diagram Fejl", "Fejl under oprettelse af diagram",
                    "Der skete en fejl under oprettelse af det valgte diagram. prøv venligst igen.");
        }

        for (Map.Entry<java.sql.Date, Integer> lineChartValue : lineChartValues.entrySet())
        {
            hoursInCurrentPeriod += lineChartValue.getValue();
            currentVolunteer.getData().add(new XYChart.Data<>(lineChartValue.getKey().toString(), lineChartValue.getValue()));
        }
        volunteer.setHoursInCurrentPeriod(hoursInCurrentPeriod);
        lineVolunteers.getData().add(currentVolunteer);
    }

    /**
     * Initiates the charts
     *
     * @param isGuild - If it is chart for a guild it will create a barchart,
     * otherwise it will create a linechart
     * @return - XYChart that can be cast to bar or line
     */
    private XYChart initiateChart(boolean isGuild)
    {
        tblGuildsOverview.setVisible(isGuild);
        tblVolunteersOverview.setVisible(!isGuild);
        graphContainer.getChildren().clear();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        XYChart chart;
        if (isGuild)
        {
            chart = new BarChart<>(xAxis, yAxis);
        }
        else
        {
            chart = new LineChart<>(xAxis, yAxis);
        }
        graphContainer.getChildren().add(chart);
        chart.prefHeightProperty().bind(graphContainer.heightProperty());
        return chart;
    }

    @FXML
    private void showStat()
    {
        if (dpFrom.getValue() != null && dpTo.getValue() != null)
        {
            updateGraph();
        }
    }

    /**
     * Sets the parameters for either guild or volunteers depending on what has
     * been choosen in the combobox
     */
    private void updateGraph()
    {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.setTime(Date.from(dpFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        to.setTime(Date.from(dpTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if ("Alle Personer".equals(cmbGuilds.getValue()))
        {
            setValuesForGuilds(from.getTime(), to.getTime());
        }
        else
        {
            setValuesForVolunteers(from.getTime(), to.getTime(), (Guild) cmbGuilds.getValue());
        }
    }

}
