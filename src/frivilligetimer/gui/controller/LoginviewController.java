/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Manager;
import frivilligetimer.gui.model.StaffModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jacob Enemark
 */
public class LoginViewController implements Initializable
{

    public final StaffModel model;
   

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    public LoginViewController() throws IOException, SQLException
    {
        this.model = StaffModel.getInstance();
    }

    @FXML
    private void handleLogin()
    {
        checkLoginInformation(txtEmail.getText(), txtPassword.getText());
    }

    private void checkLoginInformation(String email, String password)
    {
         boolean succes = false;
        
        if (model.getAllEmployees() != null)
        {
            for (Employee employee : model.getAllEmployees())
            {
                if (employee.getEmail() != null && employee.getPassword() != null)
                {
                    if (email.matches(employee.getEmail()) && password.matches(employee.getPassword()))
                    {
                        model.setLevel(1);
                        succes = true;
                        close();
                        break;
                    }
                 }
            }
        }
         if (model.getAllManagers() != null)
        {
            for (Manager manager : model.getAllManagers())
            {

                if (email.matches(manager.getEmail()) && password.matches(manager.getPassword()))
                {
                    model.setLevel(0);

                    ViewGenerator vg = new ViewGenerator((Stage) txtEmail.getScene().getWindow());

                    vg.generateView("/frivilligetimer/gui/view/AdminView.fxml", true, StageStyle.DECORATED, false, "Admin View");
                    succes = true;
                    close();
                    break;
                }

            }
        }
         
         if(!succes)
         {
                showErrorDialog("Login Error", "User not found", "Either the username or the password you provided"
                + " could not be found in our database.");
         }
       
       
       
      

    }

        /**
     * Shows an error dialog.
     *
     * @param title The title of the error.
     * @param header The header - subtitle.
     * @param content The error message.
     */
    private void showErrorDialog(String title, String header, String content)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    
    @FXML 
    private void closeButton()
    {
        close();
    }
    
    private void close()
    {
        Stage stage = (Stage) txtEmail.getScene().getWindow();
        stage.close();
    }
}
