package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * FXML Controller class for the navigator page of the Holladay Pediatric Scheduler.
 *
 * @author Harrison Thacker
 */
public class navigatorController implements Initializable {

    @FXML
    private Button navigatorReportsButton; //Opens the Report page when the user clicks.

    @FXML
    private Button navigatorCustomerButton; //Opens the Customers page when the user clicks.

    @FXML
    private Button navigatorAppointmentsButton; //Opens the Appointments page when the user clicks.

    @FXML
    private Button navigatorExitSchedulerButton; //Exits the C195 scheduler and closes the program.

    /**
     * Initializes the controller class. On launch, all the navigator buttons are set up.
     * All buttons make use of lambda expression functionality.
     *
     * @param url = instance of URL object.
     * @param resourceBundle = instance of ResourceBundle object.
     *<p>
     * LAMBDA - The four lambda expressions are for when the navigator page buttons are clicked. Each of which call the void "handler" methods.
     * This way, each button's lambda statements have event handlers that act as input for the expression. The lambdas take the button action event handler, and have the executable method called within the expression statement.
     * There isn't as much code required and it's more concise and readable this way.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Lambda expression which calls the event method, opening the Customers page.
        navigatorCustomerButton.setOnAction(event ->
        {
            try
            {
                navigatorViewCustomers();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });

        //Lambda expression which calls the event method, opening the Reports page.
        navigatorReportsButton.setOnAction(event ->
        {
            try
            {
                navigatorViewReports();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });

        //Lambda expression which calls the event method, opening the Appointments page.
        navigatorAppointmentsButton.setOnAction(event ->
        {
            try
            {
                navigatorViewAppointments();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });

        //Lambda expression which calls the event method, asking if the user wishes to exit the scheduler, and closing out if the user clicks yes on the alert.
        navigatorExitSchedulerButton.setOnAction(event ->
                exitScheduler());

    }

    /**
     * Opens up the Customers page in the Scheduler, works in conjunction with the lambda expression.
     *
     * @throws Exception = Outputs Error exception in the event of an unforeseen error.
     */
    public void navigatorViewCustomers() throws Exception
    {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/customersPage.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Holladay Pediatric Scheduler");
        stage.getIcons().add(new Image("file:src/images/SchedulerIcon.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens up the Reports page in the Scheduler, works in conjunction with the lambda expression.
     *
     * @throws Exception = Outputs Error exception in the event of an unforeseen error.
     */
    public void navigatorViewReports() throws Exception
    {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/reportsPage.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Holladay Pediatric Scheduler");
        stage.getIcons().add(new Image("file:src/images/SchedulerIcon.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Opens up the Appointments page in the Scheduler, works in conjunction with the lambda expression.
     *
     * @throws IOException = Outputs Error exception in the event of an unforeseen error.
     */
    public void navigatorViewAppointments() throws Exception
    {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../view/appointmentsPage.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Holladay Pediatric Scheduler");
        stage.getIcons().add(new Image("file:src/images/SchedulerIcon.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Closes out the scheduler and ends the program, works in conjunction with the lambda expression.
     */
    public void exitScheduler()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit?");
        alert.setHeaderText("Exit Holladay Pediatric Scheduler?");
        alert.setContentText("Are you sure you wish to exit the Scheduler?");
        alert.showAndWait().ifPresent(response ->
        {
            if (response == ButtonType.OK)
            {
                Platform.exit();
            }

        });
    }

}
