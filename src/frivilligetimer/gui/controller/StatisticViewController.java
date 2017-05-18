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
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 * FXML Controller class
 *
 * @author James
 */
public class StatisticViewController implements Initializable
{

    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
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
    private ComboBox<?> cmbGuilds;
    @FXML
    private DatePicker dpFrom;
    @FXML
    private DatePicker dpTo;

    public StatisticViewController()
    {
        this.barGuilds = new BarChart<>(xAxis, yAxis);
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

        XYChart.Series allGuilds = new XYChart.Series();
        allGuilds.setName("Laug");
        guildModel.populateGuilds();
        for (Guild guild : guildModel.getAllGuildsForTable())
        {
            guildModel.setVolunteersInGuild(guild);
            String guildName = guild.getName();
            int hours = 0;
            for (Volunteer volunteer : guildModel.getVolunteersInGuild())
            {
                int id = volunteer.getId();
                System.out.println(id);
//                try
//                {
//                    hours += volunteerModel.getTodaysHours(id);
//                }
//                catch (SQLException ex)
//                {
//                    Logger.getLogger(StatisticViewController.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
            allGuilds.getData().add(new XYChart.Data(guildName, hours));
        }

        barGuilds.getData().add(allGuilds);

    }

}
