package frivilligetimer.gui.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class adds for the use of generating new views.
 *
 * @author sBirke
 */
public class ViewHandler
{

    private Stage primaryStage;
    private Stage newStage;

    private int listSize;

    /**
     * This is the default constructor for the ViewGenerator class.
     *
     * @param primaryStage The primary stage, this is the parent stage to the
     * new view being instantiated.
     */
    public ViewHandler(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    /**
     * Generates a new view.
     *
     * @param viewPath The viewpath to the new view schema. e.g.
     * frivilligetimer/gui/view/AddVolunteer.fxml Note: the .fxml extension must
     * be added to the viewpath string.
     * @param closeCurrent Whether the primary window this new view is opened
     * upon should be closed.
     * @param style The style of the stage, e.g. StageStyle.DECORATED.
     * @param isModal If false the parent stage can be interacted with
     * regardless of which view is in focus.
     * @param title
     * @throws IOException
     */
    public void generateView(String viewPath, boolean closeCurrent, StageStyle style, boolean isModal, String title)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = null;
        try
        {
            root = loader.load();
        } catch (IOException ex)
        {
            Logger.getLogger(ViewHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (closeCurrent)
        {
            primaryStage.close();
        }

        newStage = new Stage(style);

        newStage.setScene(new Scene(root));

        newStage.setTitle(title);
        newStage.getIcons().add(new Image("frivilligetimer/gui/image/windowLogo.png"));

        if (isModal)
        {
            newStage.initModality(Modality.WINDOW_MODAL);
        }

        newStage.initOwner(primaryStage);

        newStage.show();
    }

    public void setMaximized(boolean bool)
    {
        newStage.setMaximized(bool);
    }

    public void showAlertBox(Alert.AlertType type, String title, String header, String content)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    public void setErrorRedLines(TextField firstName, TextField lastName, TextField email)
    {

        HashMap<TextField, Integer> list = new HashMap<>();
        firstName.setStyle("-fx-border-color: #0000");
        lastName.setStyle("-fx-border-color: #0000");
        email.setStyle("-fx-border-color: #0000");

        if (firstName.getText().isEmpty())
        {
            list.put(firstName, 0);
        }
        if (lastName.getText().isEmpty())
        {
            list.put(lastName, 0);
        }
        if (email.getText().isEmpty())
        {
            list.put(email, 0);
        }

        {
            for (Map.Entry<TextField, Integer> entry : list.entrySet())
            {
                if (entry.getValue() == 0)
                {
                    entry.getKey().setStyle("-fx-border-color : red");
                }
            }
        }

        listSize = list.size();
    }

    public int getErrorRedLines()
    {
        return listSize;
    }

    public void closeWindow(Stage stage, TextField txtField)
    {
        stage = (Stage) txtField.getScene().getWindow();
        stage.close();
    }

}
