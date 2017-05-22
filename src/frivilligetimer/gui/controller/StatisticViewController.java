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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.HBox;
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

    @FXML
    private BarChart<String, Number> barGuilds;
    @FXML
    private LineChart<?, Volunteer> lineVolunteers;
    @FXML
    private TableView<?> tblOverview;
    @FXML
    private TableColumn<?, ?> colName;
    @FXML
    private TableColumn<?, ?> colHours;
    @FXML
    private ComboBox<String> cmbGuilds;
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
        lineVolunteers.setVisible(false);
        barGuilds.setVisible(false);

        setInitialGraph();

        cmbGuilds.getItems().addAll(guildModel.getAllGuildNames(true));
        cmbGuilds.getSelectionModel().selectFirst();
        cmbGuilds.valueProperty().addListener(new ChangeListener<String>()
        {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
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
//        lineVolunteers.setVisible(false);
//        barGuilds.setVisible(true);
        graphContainer.getChildren().clear();
        BarChart barGuild;
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barGuild = new BarChart<>(xAxis, yAxis);
        XYChart.Series allGuilds = new XYChart.Series();
        allGuilds.setName("Timer");
        for (Guild guild : guildModel.getAllGuilds())
        {
            guildModel.setVolunteersInGuild(guild);
            int hours = 0;
            try
            {
                hours = guildModel.getWorkedHoursInPeriodForGuild(from.getTime(), to.getTime(), guild.getId());
            }
            catch (SQLException ex)
            {
                Logger.getLogger(StatisticViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex)
            {
                Logger.getLogger(StatisticViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            allGuilds.getData().add(new XYChart.Data(guild.getName(), hours));
        }

        barGuild.getData().add(allGuilds);
        graphContainer.getChildren().add(barGuild);
        barGuild.prefHeightProperty().bind(graphContainer.heightProperty());
    }
}
