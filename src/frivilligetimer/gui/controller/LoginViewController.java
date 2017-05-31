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

    /**
     *
     */
    public final StaffModel staffModel;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    }

    public LoginViewController() throws IOException, SQLException
    {
        this.staffModel = StaffModel.getInstance();
    }

    @FXML
    private void handleLogin()
    {
        checkLoginInformation(txtEmail.getText(), txtPassword.getText());
    }

    private void checkLoginInformation(String email, String password)
    {

        boolean succes = false;

        if (staffModel.getAllEmployees() != null)

        {
            for (Employee employee : staffModel.getAllEmployees())
            {
                if (employee.getEmail() != null && employee.getPassword() != null)
                {
                    if (email.matches(employee.getEmail()) && password.matches(employee.getPassword()))
                    {
                        succes = true;
                        staffModel.setLoggedInAs(employee);
                        staffModel.setLevel(1);
                        close();
                        break;
                    }
                }
            }
        }
        if (staffModel.getAllManagers() != null)
        {
            for (Manager manager : staffModel.getAllManagers())
            {

                if (email.matches(manager.getEmail()) && password.matches(manager.getPassword()))
                {
                    staffModel.setLevel(0);

                    ViewGenerator vg = new ViewGenerator((Stage) txtEmail.getScene().getWindow());

                    vg.generateView("/frivilligetimer/gui/view/AdminView.fxml", true, StageStyle.DECORATED, false, "Admin View");
                    vg.setMaximized(true);
                    succes = true;
                    close();
                    break;
                }

            }
        }

        if (!succes)
        {
            showErrorDialog("Login fejl", "Brugeren blev ikke fundet", "Emailen eller koden"
                    + " kunne ikke findes i databasen.");
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
