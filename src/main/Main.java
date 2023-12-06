package main;

import database.SchedulerDatabase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * The Holladay Pediatric Scheduler deals with Appointments, the Customers for those Appointments, and keeps track of reports for the two of them.
 *
 * The scheduler Javadoc folder is located in the src folder marked: javadoc.
 *
 *@author Harrison Thacker
 */
public class Main extends Application
{

    /**
     * Launches the Scheduler and connects to its MySQL database.
     * @param args = String array object.
     */
    public static void main(String[] args)
    {
        SchedulerDatabase.DBOpenConnection();
        launch(args);

        SchedulerDatabase.closeOutConnection();
    }

    /**
     * Initializes and sets the Login FXML file as the main page.
     * @param primaryStage = instance of Stage object.
     * @throws Exception = thrown should any error occur.
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/loginPage.fxml")));
        primaryStage.setTitle("Holladay Pediatric Scheduler");
        primaryStage.getIcons().add(new Image("file:src/images/SchedulerIcon.png"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}
