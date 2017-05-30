package frivilligetimer.gui.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class adds for the use of generating new views.
 *
 * @author sBirke
 */
public class ViewGenerator
{

    private Stage primaryStage;
    private Stage newStage;

    /**
     * This is the default constructor for the ViewGenerator class.
     *
     * @param primaryStage The primary stage, this is the parent stage to the
     * new view being instantiated.
     */
    public ViewGenerator(Stage primaryStage)
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
        }
        catch (IOException ex)
        {
            Logger.getLogger(ViewGenerator.class.getName()).log(Level.SEVERE, null, ex);
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
}
