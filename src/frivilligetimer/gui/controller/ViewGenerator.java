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

    public void generateView(String viewPath, boolean decorated) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root = loader.load();
        primaryStage.close();

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
