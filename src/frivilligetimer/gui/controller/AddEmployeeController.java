/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Employee;
import frivilligetimer.gui.model.StaffModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jacob Enemark
 */
public class AddEmployeeController implements Initializable
{

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNummer;

    private StaffModel model;
    private ViewHandler viewHandler;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = StaffModel.getInstance();
        viewHandler = new ViewHandler(stage);
        viewHandler.ReplaceFirstLetterInField(txtFirstName, txtLastName);
    }

    @FXML
    private void addEmployee()
    {
        viewHandler.setErrorRedLines(txtFirstName, txtLastName, txtEmail);
        if(viewHandler.getErrorRedLines() == 0)
        {
            addEmployeeToDB();
        }
    }

    private void addEmployeeToDB()
    {
        Employee employee = new Employee(txtFirstName.getText(), txtLastName.getText(), txtEmail.getText(), txtPhoneNummer.getText(), null);
        try
        {
            model.addEmployee(employee);
        } catch (SQLException ex)
        {
            viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
        }
        viewHandler.closeWindow(stage, txtEmail);
    }

    @FXML
    private void cancel()
    {
        viewHandler.closeWindow(stage, txtEmail);
    }

}
