package frivilligetimer.gui.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class adds for the use of generating new views.
 * @author sBirke
 */
public class ViewGenerator
{

    private Stage primaryStage;
    private Stage newStage;

    /**
     * This is the default constructor for the ViewGenerator class.
     * @param primaryStage The primary stage, this is the parent stage to the
     * new view being instantiated.
     */
    public ViewGenerator(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    /**
     * Generates a new view.
     * @param viewPath The viewpath to the new view schema. e.g. frivilligetimer/gui/view/AddVolunteer.fxml
     * Note: the .fxml extension must be added to the viewpath string.
     * @param closeCurrent Whether the primary window this new view is opened upon should be closed.
     * @param style The style of the stage, e.g. StageStyle.DECORATED.
     * @param isModal If false the parent stage can be interacted with regardless
     * of which view is in focus.
     * @throws IOException 
     */
    public void generateView(String viewPath, boolean closeCurrent, StageStyle style, boolean isModal) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = loader.load();
        if (closeCurrent)
        {
            primaryStage.close();
        }

        newStage = new Stage(style);

        newStage.setScene(new Scene(root));

        if (isModal)
        {
            newStage.initModality(Modality.WINDOW_MODAL);
        }
        
        newStage.initOwner(primaryStage);

        newStage.show();
    }
}
