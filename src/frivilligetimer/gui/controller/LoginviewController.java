/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frivilligetimer.gui.controller;

import frivilligetimer.be.Employee;
import frivilligetimer.be.Manager;
import frivilligetimer.be.Person;
import frivilligetimer.gui.model.StaffModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jacob Enemark
 */
public class LoginviewController implements Initializable {

    public final StaffModel model;

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public LoginviewController() throws IOException, SQLException {
        this.model = StaffModel.getInstance();
    }

    @FXML
    private void handleLogin() {
        checkLoginInformation(txtEmail.getText(), txtPassword.getText());
    }

    private void checkLoginInformation(String email, String password) {
        if (model.getAllEmployees() != null) {
            for (Employee employee : model.getAllEmployees()) {

                if (email.matches(employee.getEmail()) && password.matches(employee.getPassword())) {
                    model.setLevel(1);
                    break;
                }
            }
        }
        if (model.getAllManagers() != null) {
            for (Manager manager : model.getAllManagers()) {

                if (email.matches(manager.getEmail()) && password.matches(manager.getPassword())) {
                    model.setLevel(0);

                    ViewGenerator vg = new ViewGenerator((Stage) txtEmail.getScene().getWindow());

                    vg.generateView("/frivilligetimer/gui/view/AdminView.fxml", false, StageStyle.DECORATED, true, "Admin View");
                    break;
                }

            }
        }

    }

}
