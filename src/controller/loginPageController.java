package controller;

import DAO.UsersDAO;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;

/**
 * FXML Controller class for the Login page of the Holladay Pediatric Scheduler.
 *
 * @author Harrison Thacker
 */
public class loginPageController implements Initializable {

    Stage loginPageStage; //Instance of stage object. Used for opening the navigator page when the user logs in successfully.

    @FXML
    private Label loginHeader; //Header label for the login page.

    @FXML
    private Label usernameLabel; //Label for the Username text field.

    @FXML
    private Label passwordLabel; //Label for the user password text field.

    @FXML
    private Label locationLabel; //Label for the LocalTime location of the User.

    @FXML
    private TextField schedulerUsernameField; //Text field for the username.

    @FXML
    private TextField schedulerPasswordField; //Text field for the user password.

    @FXML
    private TextField schedulerLocationField; //Text field for the LocalTime location of the User. Is made to be uneditable.

    @FXML
    private Button schedulerLoginButton; //Logs the user in and verifies the given information. The details are output in: login_activity.txt.

    @FXML
    private Button loginCloseButton; //Exits the Scheduler.


    /**
     * Initializes the controller class. On launch, all the login page buttons and text fields are set up.
     * All buttons make use of lambda expression functionality.
     *
     * @param url = instance of URL object.
     * @param resourceBundle = instance of ResourceBundle object.
     *<p>
     * LAMBDA - The two lambda expressions are for when the respective login and exit buttons are clicked. They call the void "handler" methods.
     * By separating the two, your buttons have event handlers as input for the lambda statements.
     * This results in more concise and clear code because the executable method is called within the expression statement.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        try
        {
            //Locale object gets the default location of the user and determines whether the user is English/French based on location.
            Locale SchedulerLocale = Locale.getDefault();
            Locale.setDefault(SchedulerLocale);
            ZoneId SchedulerZoneID = ZoneId.systemDefault();

            //ResourceBundle retrieves loginPage.properties file and adjusts for locale accordingly.
            resourceBundle = ResourceBundle.getBundle("login/loginPage", Locale.getDefault());

            //Sets the text for the headers and buttons based on locale.
            loginHeader.setText(resourceBundle.getString("schedulerLogin"));

            usernameLabel.setText(resourceBundle.getString("username"));
            passwordLabel.setText(resourceBundle.getString("password"));
            locationLabel.setText(resourceBundle.getString("schedulerLocation"));

            schedulerLoginButton.setText(resourceBundle.getString("schedulerLogin"));
            loginCloseButton.setText(resourceBundle.getString("schedulerExit"));

            schedulerLocationField.setText(String.valueOf(SchedulerZoneID));
        }

        catch(MissingResourceException missingResourceException)
        {
            missingResourceException.printStackTrace();
        }


    //Lambda expression which calls the event method, verifying the user and logging them in if successful.
        EventHandler<ActionEvent> schedulerLoginButtonHandler = (event) -> {
        try
        {
            schedulerVerifyLogin();
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    };
        schedulerLoginButton.setOnAction(schedulerLoginButtonHandler);

        //Lambda expression which calls the event method, closing out the Scheduler.
        EventHandler<ActionEvent> schedulerExitHandler = (event) -> loginExit();
        loginCloseButton.setOnAction(schedulerExitHandler);

    }

    /**
     * Closes out the scheduler and ends the program, works in conjunction with the lambda expression.
     */
    public void loginExit()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit?");
        alert.setHeaderText("Exit Scheduler?");
        alert.setContentText("Are you sure you wish to exit the Scheduler?");
        alert.showAndWait().ifPresent(response ->
        {

            if (response == ButtonType.OK)
            {
                Platform.exit();
            }

        });

    }


    /**
     * Validates the user input and logs the user in if successful, works in conjunction with the lambda expression.
     */
    public void schedulerVerifyLogin()
    {

        try
        {
            String getSchedulerUsername = schedulerUsernameField.getText();
            String getSchedulerPassword = schedulerPasswordField.getText();

            //Retrieves the username and password fields data and outputs logger info to login_activity.txt.
            int User_ID = UsersDAO.loginVerifyUser(getSchedulerUsername, getSchedulerPassword);
            FileWriter loginFW = new FileWriter("login_activity.txt", true);
            PrintWriter loginWriter = new PrintWriter(loginFW);

            ResourceBundle resourceBundle = ResourceBundle.getBundle("login/loginPage_English", Locale.getDefault());

            //Alert dialog and exceptions in the event of the USER_ID being null.
            if (User_ID < 0)
            {
                loginWriter.print("Holladay Pediatric Scheduler User: " + getSchedulerUsername + " has been unsuccessful in logging in. Datetime: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");

                IOException ioException = new IOException();
                ioException.printStackTrace();

                //Alert dialog for no User_ID.
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(resourceBundle.getString("schedulerError"));
                alert.setContentText(resourceBundle.getString("schedulerWrong"));
                alert.show();
            }

            //Opens the navigator page in the event of a successful login, also outputs success to login_activity.txt.
           else if (User_ID > 0)
           {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/view/navigationPage.fxml"));
                fxmlLoader.setController(new navigatorController());
                Parent root = fxmlLoader.load();
                loginPageStage = (Stage) schedulerLoginButton.getScene().getWindow();
                Scene scene = new Scene(root);
                loginPageStage.setScene(scene);
                loginPageStage.show();

                loginWriter.print("Holladay Pediatric Scheduler User: " + getSchedulerUsername + " has been successful in logging in. Datetime: " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
            }
            loginWriter.close();

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
