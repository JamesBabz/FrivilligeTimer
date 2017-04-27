package frivilligetimer.gui.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author sBirke
 */
public class ViewGenerator
{

    private Stage primaryStage;
    private Stage newStage;

    public ViewGenerator(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    /**
     * Generates a new view.
     * @param viewPath The viewpath to the new view schema. e.g. frivilligetimer/gui/view/AddVolunteer.fxml
     * Note: the .fxml extension must be added to the viewpath string.
     * @param closeCurrent Whether the primary window this new view is opened upon should be closed.
     * @param decorated Whether the newly generated view should have a decoration (A Window Border) or not.
     * @throws IOException 
     */
    public void generateView(String viewPath, boolean closeCurrent, boolean decorated) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = loader.load();
        if (closeCurrent)
        {
            primaryStage.close();
        }

        if (decorated)
        {
            newStage = new Stage(StageStyle.DECORATED);
        }
        else
        {
            newStage = new Stage(StageStyle.UNDECORATED);
        }
        newStage.setScene(new Scene(root));

        newStage.initOwner(primaryStage);

        newStage.show();
    }
}
