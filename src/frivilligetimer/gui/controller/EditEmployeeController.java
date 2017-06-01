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
 * @author Bruger
 */
public class EditEmployeeController implements Initializable
{

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;

    private StaffModel model;
    private Employee employee;
    private ViewHandler viewHandler;

    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        model = StaffModel.getInstance();
        employee = model.getSelectedEmployee();
        viewHandler = new ViewHandler(stage);

        model.getSelectedEmployee();

        getCurrentInfo();
    }

    /**
     * Gets the current info on the employee
     */
    private void getCurrentInfo()
    {
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtEmail.setText(employee.getEmail());
        txtPhoneNumber.setText(employee.getPhoneNum());
    }

    @FXML
    private void handleUpdate()
    {

        viewHandler.setErrorRedLines(txtFirstName, txtLastName, txtEmail);
        if (viewHandler.getErrorRedLines() == 0)
        {
            employee.setFirstName(txtFirstName.getText());
            employee.setLastName(txtLastName.getText());
            employee.setEmail(txtEmail.getText());
            employee.setPhoneNum(txtPhoneNumber.getText());
            try
            {
                model.editEmployee(employee);
            } catch (SQLException ex)
            {
                viewHandler.showAlertBox(Alert.AlertType.ERROR, "Fejl", "Der skete en database fejl", "Ingen forbindelse til database");
            }

            viewHandler.closeWindow(stage, txtEmail);
        }
    }

    @FXML
    private void cancel()
    {
        viewHandler.closeWindow(stage, txtEmail);
    }
}
