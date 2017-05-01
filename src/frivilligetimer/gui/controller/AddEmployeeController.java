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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jacob Enemark
 */
public class AddEmployeeController implements Initializable {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtPhoneNummer;
    
    StaffModel model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = StaffModel.getInstance();
    }    

   
    @FXML
    private void addEmployee()
    {
        Employee employee = new Employee(txtFirstName.getText(), txtLastName.getText(),  txtEmail.getText(), txtPhoneNummer.getText());
        try {
            model.addEmployee(employee);
        } catch (SQLException ex) {
            Logger.getLogger(AddEmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

      cancel();

    }

    @FXML
    private void cancel()
    {
        Stage stage = (Stage) txtFirstName.getScene().getWindow();
        stage.close();
    }
    
}
