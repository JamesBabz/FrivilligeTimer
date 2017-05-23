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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author James
 */
public class StatisticViewController implements Initializable
{

    private final GuildModel guildModel;
    private final VolunteerModel volunteerModel;
    private LineChart lineVolunteers;

    @FXML
    private TableView<?> tblOverview;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colHours;
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
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        setInitialGraph();
        initiateComboBox();
    }

    private void initiateComboBox()
    {
        cmbGuilds.getItems().add("Alle Laug");
        cmbGuilds.getItems().addAll(guildModel.getAllGuilds());
        cmbGuilds.getSelectionModel().selectFirst();
        cmbListener();
    }

    private void cmbListener()
    {
        cmbGuilds.valueProperty().addListener(new ChangeListener<Object>()
        {
            @Override
            public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue)
            {
                Calendar from = Calendar.getInstance();
                Calendar to = Calendar.getInstance();
                from.setTime(Date.from(dpFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                to.setTime(Date.from(dpTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                if ("Alle Laug".equals(newValue))
                {
                    setBarChartForGuilds(from, to);
                }
                else
                {
                    setLineChartForVolunteers(from, to, (Guild) newValue);
                }
            }

        });
    }

    private void setInitialGraph()
    {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();

        from.set(Calendar.DATE, 1);
        int daysInMonth = to.getActualMaximum(Calendar.DATE);
        to.set(Calendar.DATE, daysInMonth);

        dpFrom.setValue(from.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dpTo.setValue(to.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        setBarChartForGuilds(from, to);
    }

    private void setBarChartForGuilds(Calendar from, Calendar to)
    {
        graphContainer.getChildren().clear();
        BarChart barGuilds;
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barGuilds = new BarChart<>(xAxis, yAxis);
        XYChart.Series allGuilds = new XYChart.Series();
        allGuilds.setName("Timer");
        for (Guild guild : guildModel.getAllGuilds())
        {
            guildModel.setVolunteersInGuild(guild);
            int hours = 0;
//            try
//            {
//                hours = guildModel.getWorkedHoursInPeriodForGuild(from.getTime(), to.getTime(), guild.getId());
//            }
//            catch (SQLException ex)
//            {
//                Logger.getLogger(StatisticViewController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            catch (IOException ex)
//            {
//                Logger.getLogger(StatisticViewController.class.getName()).log(Level.SEVERE, null, ex);
//            }
            allGuilds.getData().add(new XYChart.Data(guild.getName(), hours));
        }

        barGuilds.getData().add(allGuilds);
        graphContainer.getChildren().add(barGuilds);
        barGuilds.prefHeightProperty().bind(graphContainer.heightProperty());
    }

    private void setLineChartForVolunteers(Calendar from, Calendar to, Guild guild)
    {
        graphContainer.getChildren().clear();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        lineVolunteers = new LineChart<>(xAxis, yAxis);
        guildModel.setSelectedGuild(guild);
        guildModel.setVolunteersInGuild(guild);
        graphContainer.getChildren().add(lineVolunteers);
        lineVolunteers.prefHeightProperty().bind(graphContainer.heightProperty());

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
                            populateLineChart(volunteer, from, to, guild);
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

        Thread th = new Thread();

//        for (Volunteer volunteer : guildModel.getVolunteersInGuild())
//        {
//            populateLineChart(volunteer, from, to, guild);
//        }
    }

    private void populateLineChart(Volunteer volunteer, Calendar from, Calendar to, Guild guild)
    {
        XYChart.Series currentVolunteer = new XYChart.Series();
        currentVolunteer.setName(volunteer.getFullName());
        Calendar date = Calendar.getInstance();
        date.setTime(from.getTime());

        TreeMap<java.sql.Date, Integer> lineChartValues = null;
        try
        {
            lineChartValues = volunteerModel.getWorkedHoursInPeriodForVolunteer(from.getTime(), to.getTime(), volunteer.getId(), guild.getId());
        }
        catch (SQLException | IOException ex)
        {
            Logger.getLogger(StatisticViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Map.Entry<java.sql.Date, Integer> lineChartValue : lineChartValues.entrySet())
        {
            currentVolunteer.getData().add(new XYChart.Data<>(lineChartValue.getKey().toString(), lineChartValue.getValue()));
        }
        lineVolunteers.getData().add(currentVolunteer);
    }
}
