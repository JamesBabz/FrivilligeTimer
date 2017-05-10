/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Employee;
import frivilligetimer.bll.StaffManager;
import frivilligetimer.gui.model.StaffModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Bruger
 */
public class EditEmployeeController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhoneNumber;
    
    private StaffManager manager;
    private StaffModel model;
    private Employee employee;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        model = StaffModel.getInstance();
        employee = model.getSelectedEmployee();
        
        model.getSelectedEmployee();
        
        
       getCurrentInfo();
    }    
    
    private void getCurrentInfo()
    {
        txtFirstName.setText(employee.getFirstName());
        txtLastName.setText(employee.getLastName());
        txtEmail.setText(employee.getEmail());
        txtPhoneNumber.setText(employee.getPhoneNum());
    }
    
        @FXML
    private void handleUpdate() {
        employee.setFirstName(txtFirstName.getText());
        employee.setLastName(txtLastName.getText());
        employee.setEmail(txtEmail.getText());
        employee.setPhoneNum(txtPhoneNumber.getText());
        try
        {
            model.editEmployee(employee);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(EditVolunteerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void cancel(ActionEvent event) {
    }
}
