package controller;

import com.sun.corba.se.spi.activation.Server;
import controller.model.ThreadServer;
import controller.view.ServerOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * Created by luigi on 5/14/16.
 */
public class MainApp extends Application{

    private Stage primaryStage;
    private AnchorPane root;
    private boolean isServer = true;

    private ServerOverviewController controller;

    private void rootLayout()
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ServerOverview.fxml"));
            root = (AnchorPane) loader.load();

            controller = loader.getController();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void start(Stage primaryStage) throws Exception
    {
        int port = 44444;
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Server");
        rootLayout();
        controller.setPort(port);
        ThreadServer ts = new ThreadServer(port, controller);
    }

    public static void main(String args[])
    {
        launch(args);
    }
}
