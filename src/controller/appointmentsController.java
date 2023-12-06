package controller;

import database.SchedulerDatabase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.*;
import DAO.*;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import javafx.collections.*;
import java.io.IOException;
import javafx.fxml.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.*;

/**
 * FXML Controller class for the Appointments page of the Holladay Pediatric Scheduler.
 *
 * @author Harrison Thacker
 */
public class appointmentsController {

    @FXML
    private Button appointmentsNavigatorButton; //Button that redirects the user back to the navigator, works in conjunction with a lambda expression.

    @FXML
    private Button appointmentsSaveButton; //Button that saves edits made to an existing appointment, works in conjunction with a lambda expression.

    @FXML
    private Button appointmentsAddNewButton; //Button that adds new appointments to the database/table, works in conjunction with a lambda expression.

    @FXML
    private Button appointmentsEditButton; //Button that allows the user to make edits to an existing appointment, works in conjunction with a lambda expression.

    @FXML
    private Button appointmentsRemoveButton; //Button that removes appointments from the database/table, works in conjunction with a lambda expression.

    @FXML
    private TableView<Appointments> appointmentsControllerTableView; //TableView for the Scheduler Appointments.

    @FXML
    private TableColumn<?, ?> appointmentIDColumn; //Column for the Appointment ID.

    @FXML
    private TableColumn<?, ?> appointmentTitleColumn; //Column for the Appointment name.

    @FXML
    private TableColumn<?, ?> descriptionColumn; //Column for the Appointment Description.

    @FXML
    private TableColumn<?, ?> locationColumn; //Column for the Appointment location.

    @FXML
    private TableColumn<?, ?> contactColumn; //Column for the contact of the given Appointment.

    @FXML
    private TableColumn<?, ?> typeColumn; //Column for the Appointment type.

    @FXML
    private TableColumn<?, ?> sdColumn; //Column for the Appointment start date/time.

    @FXML
    private TableColumn<?, ?> edColumn; //Column for the Appointment end date/time.

    @FXML
    private TableColumn<?, ?> customerIDColumn; //Column for the Customer ID.

    @FXML
    private TableColumn<?, ?> userIDColumn; //Column for the User ID.

    @FXML
    private ComboBox<Contacts> appointmentsContactComboBox; //ComboBox for selecting a contact of choice for an Appointment.

    @FXML
    private ComboBox<LocalTime> startComboBox; //ComboBox for selecting the Appointment start time.

    @FXML
    private ComboBox<LocalTime> endComboBox; //ComboBox for selecting the Appointment end time.

    @FXML
    private TextField appointmentsIDField; //TextField for the Appointment ID.

    @FXML
    private TextField appointmentsTitleField; //TextField for the Appointment name.

    @FXML
    private TextField appointmentsDescriptionField; //TextField for the Appointment description.

    @FXML
    private TextField appointmentsLocationField; //TextField for the Appointment location.

    @FXML
    private TextField appointmentsTypeField; //TextField for the Appointment type.

    @FXML
    private ComboBox<Users> userComboBox; //ComboBox for selecting the Appointment User.

    @FXML
    private ComboBox<Customers> customerComboBox; //ComboBox for selecting the Appointment Customer.

    @FXML
    private TextField appointmentSearchField; //TextField for looking up Appointments by their ID or name.

    @FXML
    private DatePicker appointmentsStartDatePicker; //DatePicker for the Appointment start date.

    @FXML
    private DatePicker appointmentsEndDatePicker; //DatePicker for the Appointment end date.

    private Appointments appointments; //Instance of Appointments model object.

    private Users users; //Instance of Users model object.

    private UsersDAO usersDAO; //Instance Of Users DAO object.

    @FXML
    private RadioButton appointmentsSortMonthButton; //RadioButton for sorting the Appointment TableView by month.

    @FXML
    private RadioButton appointmentsSortWeekButton; //RadioButton for sorting the Appointment TableView by week.

    @FXML
    private RadioButton appointmentsAllRadioButton; //RadioButton for sorting the Appointment TableView by all entries.


    /**
     * Initializes the controller class. On launch, all the Appointment page attributes are set up.
     * All buttons make use of lambda expression functionality, as well as the Observable Lists.
     *
     * @throws Exception = thrown in the event of an unforeseen error.
     *<p>
     * LAMBDA - setOnAction: These lambda expressions work in conjunction with the void executable methods. When any of the page buttons or radio buttons are interacted with, the respective methods are called.
     * When called, the methods run the executable functionality for the features of the Appointments page.
     * All the buttons, normal and radio, have event handlers in the lambda expression statement which acts as an input.
     * This separation results in less code having to be written and is more readable and concise for others who may be working with the Scheduler program.
     */
    public void initialize() throws Exception
    {
        //Connects to the scheduler database.
        Connection connection = SchedulerDatabase.DBOpenConnection();

        usersDAO = new UsersDAO();

        ObservableList<LocalTime> timesForAppointment = FXCollections.observableArrayList();
        ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
        ObservableList<Users> listOfUsers = FXCollections.observableArrayList();
        ObservableList<Contacts> listOfContacts = FXCollections.observableArrayList();

        ObservableList<Customers> customers = CustomersDAO.retrieveCustomers(connection);
        ObservableList<Users> users = UsersDAO.retrieveUsers();
        ObservableList<Contacts> contacts = ContactsDAO.retrieveContacts();

        listOfCustomers.addAll(customers);
        listOfUsers.addAll(users);
        listOfContacts.addAll(contacts);

        //LocalTime objects that set the hours for the appointments.
        LocalTime appointmentBeginningOfDay = LocalTime.MIN.plusHours(8);
        LocalTime appointmentEndOfDay = LocalTime.MAX.minusHours(1).minusMinutes(45);

        if (!appointmentEndOfDay.equals(0) || !appointmentBeginningOfDay.equals(0))
        {
            while (appointmentBeginningOfDay.isBefore(appointmentEndOfDay))
            {
                timesForAppointment.add(appointmentBeginningOfDay);
                appointmentBeginningOfDay = appointmentBeginningOfDay.plusMinutes(15);
            }

        }

        appointmentsContactComboBox.setItems(listOfContacts);
        startComboBox.setItems(timesForAppointment);
        endComboBox.setItems(timesForAppointment);
        customerComboBox.setItems(listOfCustomers);
        userComboBox.setItems(listOfUsers);

        //Retrieves appointments and initializes the LocalDateTime, for the 15 minute Appointment alert.
        ObservableList<Appointments> retrieveAppointments = AppointmentsDAO.retrieveAppointments(connection);

        LocalDateTime ldtMinusMinutes = LocalDateTime.now().minusMinutes(15);
        LocalDateTime ldtPlusMinutes = LocalDateTime.now().plusMinutes(15);
        LocalDateTime beginningOfAppointment;

        boolean appointmentUpcomingAlert = false;
        LocalDateTime appointmentLDT = null;
        int retrieveAppointmentID = 0;
        String retrieveAppointmentName = " ";

        //Sets the table rows/columns for the Appointments TableView.
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        appointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("Location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        sdColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        edColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("User_ID"));
        appointmentsControllerTableView.setItems(retrieveAppointments);

        //Iterates through and checks if the Appointment start time is after 15 minutes of login.
        for (Appointments alertAppointment: retrieveAppointments)
        {

            beginningOfAppointment = alertAppointment.getStart();

            if (((beginningOfAppointment.isBefore(ldtPlusMinutes) || (beginningOfAppointment.isEqual(ldtPlusMinutes) && beginningOfAppointment.isAfter(ldtMinusMinutes) || beginningOfAppointment.isEqual(ldtMinusMinutes)))))
            {
                appointmentLDT = beginningOfAppointment;
                retrieveAppointmentID = alertAppointment.getAppointment_ID();
                retrieveAppointmentName = alertAppointment.getTitle();
                appointmentUpcomingAlert = true;
            }

        }

        //Alert dialog boxes that show up if a user has an appointment within 15 minutes after login, or no awaiting appointments.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (appointmentUpcomingAlert)
        {
            alert.setTitle("Appointment In 15 Minutes!");
            alert.setContentText("Attention User!  Appointment #" + retrieveAppointmentID + " is scheduled 15 minutes from now! Appointment: " + retrieveAppointmentName + " will be starting at " + appointmentLDT);
        }

        else
        {
            alert.setTitle("No Appointment Scheduled Soon ");
            alert.setContentText("There are no Appointments that are scheduled very soon. Just keeping you informed!");
        }
        alert.showAndWait();

        //Lambda expression which calls the event method, having the Appointments TableView show all appointments.
        appointmentsAllRadioButton.setOnAction(event ->
                sortAllAppointmentsClicked());

        //Lambda expression which calls the event method, having the TableView sort Appointments by week.
        appointmentsSortWeekButton.setOnAction(event ->
                sortAppointmentsWeekClicked());

        //Lambda expression which calls the event method, having the TableView sort Appointments by month.
        appointmentsSortMonthButton.setOnAction(event ->
                sortAppointmentsMonthClicked());

        //Lambda expression which calls the event method, adding a new Appointment to the scheduler records/database.
        appointmentsAddNewButton.setOnAction(event ->
        {

            try
            {
                createNewAppointmentClicked();
            }

            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        });

        //Lambda expression which calls the event method, allowing the user to make edits to an already existing Appointment.
        appointmentsEditButton.setOnAction(event ->
                editAppointmentClicked());

        //Lambda expression which calls the event method, removing an Appointment from the scheduler TableView/database.
        appointmentsRemoveButton.setOnAction(event ->
                removeAppointmentClicked());

        //Lambda expression which calls the event method, saving edits made to an Appointment.
        appointmentsSaveButton.setOnAction(event ->
                appointmentSaveToRecordsClicked());

        //Lambda expression which calls the event method, redirecting the user back to the navigator.
        appointmentsNavigatorButton.setOnAction(event ->
        {

            try
            {
                appointmentNavigatorClicked();
            }

            catch (IOException ioException)
            {
                ioException.printStackTrace();
            }
        });

    }

    /**
     * The search field looks up the appointment in the database by either the ID or name.
     * If successful, the Appointments TableView will populate with the given results.
     *
     * @param actionEvent = instance of ActionEvent object.
     */
    @FXML
    public void retrieveSearchedAppointment(ActionEvent actionEvent) throws Exception
    {

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                //Connects to the scheduler database.
                Connection connection = SchedulerDatabase.DBOpenConnection();

                AppointmentsDAO appointmentsDAO = new AppointmentsDAO();

                try
                {
                    appointmentsControllerTableView.setItems(AppointmentsDAO.retrieveAppointments(connection));
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

                String appointmentSearch = appointmentSearchField.getText();
                if(appointmentSearch.isEmpty())
                {
                    Alert noResultAlert = new Alert(Alert.AlertType.ERROR);
                    noResultAlert.setTitle("Error!");
                    noResultAlert.setContentText("Please enter either the Appointment ID or Name please!");
                    noResultAlert.showAndWait();
                }

                try
                {
                    int Appointment_ID = Integer.parseInt(appointmentSearchField.getText());
                    Appointments appointments = appointmentsDAO.retrieveAppointmentID(Appointment_ID);
                    appointmentsControllerTableView.requestFocus();
                    appointmentsControllerTableView.getSelectionModel().select(appointments);
                    appointmentsControllerTableView.scrollTo(appointments);
                }

                catch(Exception exception)
                {
                    String appointmentsName = appointmentSearchField.getText();

                    try
                    {
                        ObservableList<Appointments> appointmentsObservableList = appointmentsDAO.retrieveAppointmentName(appointmentsName);
                        appointmentsControllerTableView.setItems(appointmentsObservableList);
                    }

                    catch (SQLException sqlException)
                    {
                        sqlException.printStackTrace();
                    }
                }

                if(!appointmentsDAO.appointmentResult)
                {
                    Alert noResultAlert = new Alert(Alert.AlertType.ERROR);
                    noResultAlert.setTitle("Error!");
                    noResultAlert.setContentText("Your Appointment search didn't return any results.");
                    noResultAlert.showAndWait();
                }
            }
        });

    }


    /**
     * RadioButton that has the TableView show all Appointment entries, works in conjunction with the lambda expression.
     *<p>
     * LAMBDA - listEveryAppointment.forEach: Iterates through and sets the Appointment TableView to show all entries by setting items to the respective ObservableList.
     * A for loop would result in slightly more code having to be written. Using a lambda expression here is a neater and more readable implementation.
     */
    @FXML
    public void sortAllAppointmentsClicked()
    {

        try
        {
            //Connects to the scheduler database.
            Connection connection = SchedulerDatabase.DBOpenConnection();

            ObservableList<Appointments> listEveryAppointment = AppointmentsDAO.retrieveAppointments(connection);

            //For each lambda that iterates through and grabs all Appointment entries.
            listEveryAppointment.forEach(everyAppointment ->
                    appointmentsControllerTableView.setItems(listEveryAppointment));

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    /**
     * RadioButton that has the TableView sort Appointments by week, works in conjunction with the lambda expression.
     *<p>
     * LAMBDA - listEveryAppointment.forEach: Iterates through and sets the Appointment TableView to show the entries within the span of a week by setting items to the respective ObservableList.
     * A for loop would result in slightly more code having to be written. Using a lambda expression here is a neater and more readable implementation.
     */
    @FXML
    public void sortAppointmentsWeekClicked()
    {

        try
        {
            //Connects to the scheduler database.
            Connection connection = SchedulerDatabase.DBOpenConnection();

            ObservableList<Appointments> weekSortAppointments = FXCollections.observableArrayList();
            ObservableList<Appointments> listEveryAppointment = AppointmentsDAO.retrieveAppointments(connection);
            LocalDateTime sortWeekLDTStart = LocalDateTime.now().minusWeeks(1);
            LocalDateTime sortWeekLDTEnd = LocalDateTime.now().plusWeeks(1);

            //For each lambda that iterates through and grabs all Appointments, adding them to the week ObservableList if they're within the time span of 1 week.
            listEveryAppointment.forEach(appointmentsWeek ->
            {

                if (appointmentsWeek.getEnd().isBefore(sortWeekLDTEnd) && appointmentsWeek.getEnd().isAfter(sortWeekLDTStart))
                {
                    weekSortAppointments.add(appointmentsWeek);
                }

                appointmentsControllerTableView.setItems(weekSortAppointments);
            });

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    /**
     * RadioButton that has the TableView sort Appointments by month, works in conjunction with the lambda expression.
     *<p>
     * LAMBDA - listEveryAppointment.forEach: Iterates through and sets the Appointment TableView to show all entries within the span of a month by setting items to the respective ObservableList.
     * A for loop would result in slightly more code having to be written. Using a lambda expression here is a neater and more readable implementation.
     */
    @FXML
    public void sortAppointmentsMonthClicked()
    {

        try
        {
            //Connects to the scheduler database.
            Connection connection = SchedulerDatabase.DBOpenConnection();

            ObservableList<Appointments> monthSortAppointments = FXCollections.observableArrayList();
            ObservableList<Appointments> listEveryAppointment = AppointmentsDAO.retrieveAppointments(connection);
            LocalDateTime sortMonthLDTStart = LocalDateTime.now().minusMonths(1);
            LocalDateTime sortMonthLDTEnd = LocalDateTime.now().plusMonths(1);

            //For each lambda that iterates through and grabs all Appointments, adding them to the month ObservableList if they're within the time span of 1 month.
            listEveryAppointment.forEach(appointmentsMonth ->
            {

                if (appointmentsMonth.getEnd().isBefore(sortMonthLDTEnd) && appointmentsMonth.getEnd().isAfter(sortMonthLDTStart))
                {
                    monthSortAppointments.add(appointmentsMonth);
                }

                appointmentsControllerTableView.setItems(monthSortAppointments);
            });

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    /**
     * Validates the user input and adds a new appointment if successful, works in conjunction with the lambda expression.
     * @throws IOException = thrown in the event of an unforeseen error.
     *<p>
     * LAMBDA - retrieveUsers: Iterates through and filters the User IDs to find one that matches between the User object and the respective ObservableList. If successful, it gets added to the list.
     * The forEach expression contained within doesn't use as much code as a for loop would, resulting in a clean and concise expression.
     * retrieveCustomers: Iterates through and filters the Customer IDs to find one that matches between the Customer object and the respective ObservableList. If successful, it gets added to the list.
     * The forEach expression contained within doesn't use as much code as a for loop would, resulting in a clean and concise expression.
     */
    @FXML
    public void createNewAppointmentClicked() throws IOException
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Appointment?");
        alert.setHeaderText("Add New Appointment?");
        alert.setContentText("Are you sure you wish to add this new appointment to the records?");
        alert.showAndWait().ifPresent(response ->
                {
                    if(response == ButtonType.OK)
                    {

                        try
                        {

                            Connection connection = SchedulerDatabase.DBOpenConnection();

                            //Sets up and initializes all the page attributes if each element is empty or null.
                            if (!appointmentsTitleField.getText().isEmpty() && !appointmentsDescriptionField.getText().isEmpty() && !appointmentsLocationField.getText().isEmpty() && !appointmentsTypeField.getText().isEmpty() && !startComboBox.getSelectionModel().isEmpty() && appointmentsStartDatePicker.getValue() != null  && !endComboBox.getSelectionModel().isEmpty() && appointmentsEndDatePicker.getValue() != null  && !customerComboBox.getSelectionModel().isEmpty() && !userComboBox.getSelectionModel().isEmpty())
                            {
                                ObservableList<Appointments> retrieveAppointments = AppointmentsDAO.retrieveAppointments(connection);
                                ObservableList<Integer> UserID = FXCollections.observableArrayList();
                                ObservableList<Users> retrieveUsers = UsersDAO.retrieveUsers();
                                ObservableList<Integer> CustomerID = FXCollections.observableArrayList();
                                ObservableList<Customers> retrieveCustomers = CustomersDAO.retrieveCustomers(connection);

                                retrieveUsers.stream().map(Users::getUser_ID).forEach(UserID::add);
                                retrieveCustomers.stream().map(Customers::getCustomer_ID).forEach(CustomerID::add);

                                LocalDate appointmentStart = appointmentsStartDatePicker.getValue();
                                LocalDate appointmentEnd = appointmentsEndDatePicker.getValue();
                                DateTimeFormatter appointmentTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                                DateTimeFormatter appointmentDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                                LocalTime stComboBoxResult = startComboBox.getValue();
                                LocalTime etComboBoxResult = endComboBox.getValue();

                                LocalTime appointmentLTStart = (startComboBox.getValue());
                                LocalTime appointmentLTEnd = (endComboBox.getValue());

                                LocalDateTime appointmentDTStart = LocalDateTime.of(appointmentStart, appointmentLTStart);
                                LocalDateTime appointmentDTEnd = LocalDateTime.of(appointmentEnd, appointmentLTEnd);
                                ZonedDateTime appointmentZDTStart = ZonedDateTime.of(appointmentDTStart, ZoneId.systemDefault());
                                ZonedDateTime appointmentZDTEnd = ZonedDateTime.of(appointmentDTEnd, ZoneId.systemDefault());

                                ZonedDateTime appointmentBeginningEST = appointmentZDTStart.withZoneSameInstant(ZoneId.of("America/New_York"));
                                ZonedDateTime appointmentEndEST = appointmentZDTEnd.withZoneSameInstant(ZoneId.of("America/New_York"));
                                LocalTime appointmentBeginningLT = appointmentBeginningEST.toLocalTime();
                                LocalTime appointmentEndLT = appointmentEndEST.toLocalTime();

                                //Retrieves the local date day of the week.
                                DayOfWeek appointmentStartLTDay = appointmentBeginningEST.toLocalDate().getDayOfWeek();
                                DayOfWeek appointmentEndLTDay = appointmentEndEST.toLocalDate().getDayOfWeek();

                                int beginningOfSchedulerWeek = DayOfWeek.MONDAY.getValue();
                                int endOfSchedulerWeek = DayOfWeek.FRIDAY.getValue();
                                int verifyAppointmentSD = appointmentStartLTDay.getValue();
                                int verifyAppointmentED = appointmentEndLTDay.getValue();

                                LocalTime schedulerDoorsOpen = LocalTime.of(8, 0, 0);
                                LocalTime schedulerDoorsClose = LocalTime.of(22, 0, 0);

                                int AppointmentID = Integer.parseInt(String.valueOf((int) (Math.random() * 100)));
                                int CustomersID = customerComboBox.getSelectionModel().getSelectedItem().getCustomer_ID();

                                //For loop that iterates through the new appointment attributes and makes sure that none of them overlap with each other.
                                for (Appointments appointments: retrieveAppointments)
                                {
                                    LocalDateTime parseBeginning = appointments.getStart();
                                    LocalDateTime parseEnd = appointments.getEnd();

                                    //Checks if the appointment start time has an overlap with another preexisting one.
                                    if ((CustomersID == appointments.getCustomer_ID()) && (AppointmentID != appointments.getAppointment_ID()) && (appointmentDTStart.isAfter(parseBeginning)) && (appointmentDTStart.isBefore(parseEnd)))
                                    {
                                        Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                                        overlapAlert.setTitle("Error!");
                                        overlapAlert.setContentText("Unfortunately the start time for this appointment has an overlap with another preexisting one. Please get that sorted!");
                                        overlapAlert.show();
                                        return;
                                    }

                                    //Checks if the new appointment overlaps with another preexisting one.
                                    if ((CustomersID == appointments.getCustomer_ID()) && (AppointmentID != appointments.getAppointment_ID()) && (appointmentDTStart.isBefore(parseBeginning)) && (appointmentDTEnd.isAfter(parseEnd)))
                                    {
                                        Alert preexistingAlert = new Alert(Alert.AlertType.ERROR);
                                        preexistingAlert.setTitle("Error!");
                                        preexistingAlert.setContentText("Unfortunately this appointment overlaps with another preexisting one. Please get that sorted!");
                                        preexistingAlert.show();
                                        return;
                                    }

                                    //Checks if the appointment end time has an overlap with another preexisting one.
                                    if (CustomersID == appointments.getCustomer_ID() && (AppointmentID != appointments.getAppointment_ID()) && (appointmentDTEnd.isAfter(parseBeginning)) && (appointmentDTEnd.isBefore(parseEnd)))
                                    {
                                        Alert endTimeAlert = new Alert(Alert.AlertType.ERROR);
                                        endTimeAlert.setTitle("Error!");
                                        endTimeAlert.setContentText("Unfortunately the end time for this appointment has an overlap with another preexisting one. Please get that sorted!");
                                        endTimeAlert.show();
                                        return;
                                    }
                                }

                                //Checks if the start and end times are out of scope with the Scheduler company's business hours.
                                if (appointmentEndLT.isBefore(schedulerDoorsOpen) || appointmentEndLT.isAfter(schedulerDoorsClose) || appointmentBeginningLT.isBefore(schedulerDoorsOpen) || appointmentBeginningLT.isAfter(schedulerDoorsClose))
                                {
                                    Alert scopeAlert = new Alert(Alert.AlertType.ERROR);
                                    scopeAlert.setTitle("Error!");
                                    scopeAlert.setContentText("Your appointment time: " + appointmentBeginningLT + " through " + appointmentEndLT + " EST " + "does not fit within our business hours (8:00 AM through 10:00 PM EST. Try something else!");
                                    scopeAlert.show();
                                    return;
                                }

                                //Checks if the day the user wants to have an appointment doesn't line up with the work-week of the Scheduler company.
                                if ( verifyAppointmentED < beginningOfSchedulerWeek || verifyAppointmentED > endOfSchedulerWeek || verifyAppointmentSD < beginningOfSchedulerWeek || verifyAppointmentSD > endOfSchedulerWeek)
                                {
                                    Alert workWeekAlert = new Alert(Alert.AlertType.ERROR);
                                    workWeekAlert.setTitle("Error!");
                                    workWeekAlert.setContentText("You cannot have an appointment that day. Our work-week is Monday through Friday. Sorry!");
                                    workWeekAlert.show();
                                    return;
                                }

                                //Checks if the new appointment start and end time are the exact same, and puts up an alert dialog.
                                if (appointmentDTStart.isEqual(appointmentDTEnd))
                                {
                                    Alert exactSameAlert = new Alert(Alert.AlertType.ERROR);
                                    exactSameAlert.setTitle("Error!");
                                    exactSameAlert.setContentText("Your new appointment's start and end times are the exact same. That won't cut it!");
                                    exactSameAlert.show();
                                    return;
                                }

                                //Checks if the appointment start time takes place after the end time, and puts up an alert dialog if that's the case.
                                if (appointmentDTStart.isAfter(appointmentDTEnd))
                                {
                                    Alert startsAfterAlert = new Alert(Alert.AlertType.ERROR);
                                    startsAfterAlert.setTitle("Error!");
                                    startsAfterAlert.setContentText("Your new appointment starts after the end time. Please have it start a reasonable amount before the end time!");
                                    startsAfterAlert.show();
                                    return;
                                }

                                appointments = new Appointments();
                                users = new Users();

                                //Retrieves the Appointment parameters, which are the page attributes.
                                appointments.setAppointment_ID(AppointmentID);
                                appointments.setTitle(appointmentsTitleField.getText());
                                appointments.setDescription(appointmentsDescriptionField.getText());
                                appointments.setLocation(appointmentsLocationField.getText());
                                appointments.setType(appointmentsTypeField.getText());
                                appointments.setStart(LocalDateTime.of(appointmentsStartDatePicker.getValue(), startComboBox.getSelectionModel().getSelectedItem()));
                                appointments.setEnd(LocalDateTime.of(appointmentsEndDatePicker.getValue(), endComboBox.getSelectionModel().getSelectedItem()));
                                appointments.setCreate_Date(LocalDateTime.now());
                                appointments.setCreated_By(users.getUser_Name());
                                appointments.setLast_Update(LocalDateTime.now());
                                appointments.setLast_Updated_By(users.getUser_Name());
                                appointments.setCustomer_ID(customerComboBox.getSelectionModel().getSelectedItem().getCustomer_ID());
                                appointments.setUser_ID(userComboBox.getSelectionModel().getSelectedItem().getUser_ID());
                                appointments.setContact_ID(appointmentsContactComboBox.getSelectionModel().getSelectedItem().getContact_ID());

                                AppointmentsDAO.AddAppointment(appointments);

                                appointmentsIDField.clear();
                                appointmentsTitleField.clear();
                                appointmentsDescriptionField.clear();
                                appointmentsLocationField.clear();
                                appointmentsTypeField.clear();

                                //Sets and refreshes the Appointments TableView.
                                ObservableList<Appointments> listOfAppointments = AppointmentsDAO.retrieveAppointments(connection);
                                appointmentsControllerTableView.setItems(listOfAppointments);
                            }

                        }

                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                    }
                });

    }

    /**
     * Allows the user to make edits to an already existing appointment, works in conjunction with the lambda expression.
     *<p>
     * LAMBDA - retrieveAppointmentContacts: Iterates through and retrieves the contact names, as well as matching between the Contacts object and the respective ObservableList. If successful, it gets added to the list.
     * Using a for loop instead would require more a bit more code to be written and wouldn't be as readable and elegant as a lambda expression.
     */
    @FXML
    public void editAppointmentClicked()
    {

        try
        {
            SchedulerDatabase.DBOpenConnection();

            Appointments editGivenAppointment = appointmentsControllerTableView.getSelectionModel().getSelectedItem();

            //Sets an alert dialog if no appointment was selected and the Edit button was clicked.
            if(editGivenAppointment == null)
            {
                Alert editAppointmentNoSelected = new Alert(Alert.AlertType.ERROR);
                editAppointmentNoSelected.setTitle("Error!");
                editAppointmentNoSelected.setContentText("You must first select an Appointment in the TableView that you wish to make edits to.");
                editAppointmentNoSelected.showAndWait();
            }


            //Sets the page attributes for the given appointment to be edited, if one was selected.
            else
            {

                //Gathers the data from the selected Appointment attributes and sets them as part of the page elements.
                appointmentsIDField.setText(String.valueOf(editGivenAppointment.getAppointment_ID()));
                appointmentsTitleField.setText(String.valueOf(editGivenAppointment.getTitle()));
                appointmentsDescriptionField.setText(String.valueOf(editGivenAppointment.getDescription()));
                appointmentsLocationField.setText(String.valueOf(editGivenAppointment.getLocation()));
                appointmentsTypeField.setText(String.valueOf(editGivenAppointment.getType()));

                int Customer_ID = appointmentsControllerTableView.getSelectionModel().getSelectedItem().getCustomer_ID();
                int User_ID = appointmentsControllerTableView.getSelectionModel().getSelectedItem().getUser_ID();
                int Contact_ID = appointmentsControllerTableView.getSelectionModel().getSelectedItem().getContact_ID();

                ObservableList<String> listOfContacts = FXCollections.observableArrayList();
                ObservableList<Contacts> retrieveAppointmentContacts = ContactsDAO.retrieveContacts();
                retrieveAppointmentContacts.forEach(contacts -> listOfContacts.add(contacts.getContact_Name()));

                CustomersDAO customersDAO = new CustomersDAO();
                ContactsDAO contactsDAO = new ContactsDAO();
                Users usersCB = usersDAO.userRetrieval(User_ID);
                Customers customersCB = customersDAO.customersRetrieval(Customer_ID);
                Contacts contactsCB = contactsDAO.retrieveIDContact(Contact_ID);

                customerComboBox.setValue(customersCB);
                userComboBox.setValue(usersCB);
                startComboBox.getSelectionModel().select(editGivenAppointment.getStart().toLocalTime());
                appointmentsStartDatePicker.setValue(editGivenAppointment.getStart().toLocalDate());
                endComboBox.getSelectionModel().select(editGivenAppointment.getEnd().toLocalTime());
                appointmentsEndDatePicker.setValue(editGivenAppointment.getEnd().toLocalDate());
                appointmentsContactComboBox.setValue(contactsCB);
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }


    /**
     * Allows the user to remove an appointment from the scheduler records, works in conjunction with the lambda expression.
     */
    @FXML
    public void removeAppointmentClicked()
    {

        Appointments appointments = appointmentsControllerTableView.getSelectionModel().getSelectedItem();

        //Sets an alert dialog if no appointment was selected and the Remove button was clicked.
        if(appointments == null)
        {
            Alert removeAppointmentNoSelected = new Alert(Alert.AlertType.ERROR);
            removeAppointmentNoSelected.setTitle("Error!");
            removeAppointmentNoSelected.setContentText("You must first select an Appointment in the TableView that you wish to remove from the records.");
            removeAppointmentNoSelected.showAndWait();
        }

        else
        {

            //Alert dialog that removes the selected Appointment in the TableView if agreed to by the user.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Appointment?");
            alert.setHeaderText("Remove Appointment From Scheduler?");
            alert.setContentText("Are you sure you wish to remove the Appointment: " + appointmentsControllerTableView.getSelectionModel().getSelectedItem().getAppointment_ID() + " with the type " + appointmentsControllerTableView.getSelectionModel().getSelectedItem().getType() + " from the scheduler records?");
            alert.showAndWait().ifPresent(response ->
            {

                try
                {
                    //Connects to the scheduler database.
                    Connection connection = SchedulerDatabase.DBOpenConnection();

                    AppointmentsDAO.DropAppointment(appointmentsControllerTableView.getSelectionModel().getSelectedItem().getAppointment_ID(), connection);
                    ObservableList<Appointments> schedulerAppointments = AppointmentsDAO.retrieveAppointments(connection);

                    //Confirmation alert if an Appointment record is removed successfully.
                    Alert successfulDeletion = new Alert(Alert.AlertType.CONFIRMATION);
                    successfulDeletion.setTitle("Successful Deletion!");
                    successfulDeletion.setContentText("Appointment: " + appointmentsControllerTableView.getSelectionModel().getSelectedItem().getAppointment_ID() + " with the type " + appointmentsControllerTableView.getSelectionModel().getSelectedItem().getType() + " has been removed from the records.");
                    successfulDeletion.showAndWait();

                    appointmentsIDField.clear();
                    appointmentsTitleField.clear();
                    appointmentsDescriptionField.clear();
                    appointmentsLocationField.clear();
                    appointmentsTypeField.clear();

                    //Resets the Appointment TableView.
                    appointmentsControllerTableView.setItems(schedulerAppointments);
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

            });
        }

    }


    /**
     * Allows the user to save edits made to an existing appointment, works in conjunction with the lambda expression.
     *<p>
     * LAMBDA - retrieveUsersSave: Iterates through and filters the User IDs to find one that matches between the User object and the respective ObservableList. If successful, it gets added to the list.
     * The forEach expression contained within doesn't use as much code as a for loop would, resulting in a clean and concise expression.
     * retrieveCustomersSave: Iterates through and filters the Customer IDs to find one that matches between the Customer object and the respective ObservableList. If successful, it gets added to the list.
     * The forEach expression contained within doesn't use as much code as a for loop would, resulting in a clean and concise expression.
     */
    @FXML
    public void appointmentSaveToRecordsClicked()
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save To Records?");
        alert.setHeaderText("Save Edits To Appointment?");
        alert.setContentText("This will save edits to the selected appointment. Do you wish to proceed?");
        alert.showAndWait().ifPresent(response ->
        {

            if (response == ButtonType.OK)
            {

                try
                {

                    //Connects to the scheduler database.
                    Connection connection = SchedulerDatabase.DBOpenConnection();

                    //Saves edits made to the appointment record so long as all fields aren't left empty by the user.
                    if (!appointmentsTitleField.getText().isEmpty() || !appointmentsDescriptionField.getText().isEmpty() || !appointmentsLocationField.getText().isEmpty() || !appointmentsTypeField.getText().isEmpty() || !startComboBox.getSelectionModel().isEmpty() || appointmentsStartDatePicker.getValue() != null  || !endComboBox.getSelectionModel().isEmpty() || appointmentsEndDatePicker.getValue() != null  || !customerComboBox.getSelectionModel().isEmpty() || !userComboBox.getSelectionModel().isEmpty())
                    {

                        ObservableList<Appointments> retrieveAppointmentsSave = AppointmentsDAO.retrieveAppointments(connection);
                        ObservableList<Integer> retrieveUserIDSave = FXCollections.observableArrayList();
                        ObservableList<Users> retrieveUsersSave = UsersDAO.retrieveUsers();
                        ObservableList<Integer> retrieveCustomerIDSave = FXCollections.observableArrayList();
                        ObservableList<Customers> retrieveCustomersSave = CustomersDAO.retrieveCustomers(connection);

                        retrieveUsersSave.stream().map(Users::getUser_ID).forEach(retrieveUserIDSave::add);
                        retrieveCustomersSave.stream().map(Customers::getCustomer_ID).forEach(retrieveCustomerIDSave::add);

                        LocalDate ldStartSave = appointmentsStartDatePicker.getValue();
                        LocalDate ldEndSave = appointmentsEndDatePicker.getValue();
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                        DateTimeFormatter appointmentDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                        LocalTime startComboSave = startComboBox.getValue();
                        LocalTime endComboSave = endComboBox.getValue();

                        //String utcStartTimeSave = UTCToLDTConverter(appointmentsStartDatePicker.getValue().format(appointmentDateFormatter) + " " + startComboSave + ":00");
                        //String utcEndTimeSave = UTCToLDTConverter(appointmentsEndDatePicker.getValue().format(appointmentDateFormatter) + " " + endComboSave + ":00");
                        LocalTime stParseSave = (startComboBox.getValue());
                        LocalTime etParseSave = (endComboBox.getValue());
                        LocalDateTime ldtStartSave = LocalDateTime.of(ldStartSave, stParseSave);
                        LocalDateTime ldtEndSave = LocalDateTime.of(ldEndSave, etParseSave);
                        ZonedDateTime zdtStartSave = ZonedDateTime.of(ldtStartSave, ZoneId.systemDefault());
                        ZonedDateTime zdtEndSave = ZonedDateTime.of(ldtEndSave, ZoneId.systemDefault());
                        ZonedDateTime stSameInstant = zdtStartSave.withZoneSameInstant(ZoneId.of("America/New_York"));
                        ZonedDateTime etSameInstant = zdtEndSave.withZoneSameInstant(ZoneId.of("America/New_York"));

                        int userIDSave = userComboBox.getSelectionModel().getSelectedItem().getUser_ID();
                        int customerIDSave = customerComboBox.getSelectionModel().getSelectedItem().getCustomer_ID();

                        //For loop that iterates through the new appointment attributes and makes sure that none of them overlap with each other.
                        for (Appointments appointmentSave: retrieveAppointmentsSave)
                        {
                            LocalDateTime retrieveStartTime = appointmentSave.getStart();
                            LocalDateTime retrieveEndTime = appointmentSave.getEnd();

                            //Checks if the appointment start time has an overlap with another preexisting one.
                            if ((ldtStartSave.isAfter(retrieveStartTime)) && (ldtStartSave.isBefore(retrieveEndTime)) && (customerIDSave == appointmentSave.getCustomer_ID()) && (userIDSave != appointmentSave.getAppointment_ID()))
                            {
                                Alert overlapAlert = new Alert(Alert.AlertType.ERROR);
                                overlapAlert.setTitle("Error!");
                                overlapAlert.setContentText("Unfortunately the start time for this appointment has an overlap with another preexisting one. Please get that sorted!");
                                overlapAlert.show();
                                return;
                            }

                            //Checks if the new appointment overlaps with another preexisting one.
                            if ((ldtStartSave.isBefore(retrieveStartTime)) && (ldtEndSave.isAfter(retrieveEndTime)) && (customerIDSave == appointmentSave.getCustomer_ID()) && (userIDSave != appointmentSave.getAppointment_ID()))
                            {
                                Alert preexistingAlert = new Alert(Alert.AlertType.ERROR);
                                preexistingAlert.setTitle("Error!");
                                preexistingAlert.setContentText("Unfortunately this appointment overlaps with another preexisting one. Please get that sorted!");
                                preexistingAlert.show();
                                return;
                            }

                            //Checks if the appointment end time has an overlap with another preexisting one.
                            if ((ldtEndSave.isAfter(retrieveStartTime)) && (ldtEndSave.isBefore(retrieveEndTime)) && customerIDSave == appointmentSave.getCustomer_ID() && (userIDSave != appointmentSave.getAppointment_ID()))
                            {
                                Alert endTimeAlert = new Alert(Alert.AlertType.ERROR);
                                endTimeAlert.setTitle("Error!");
                                endTimeAlert.setContentText("Unfortunately the end time for this appointment has an overlap with another preexisting one. Please get that sorted!");
                                endTimeAlert.show();
                                return;
                            }

                        }

                        //Checks if the start and end times are out of scope with the Scheduler company's business hours.
                        if (stSameInstant.toLocalTime().isAfter(LocalTime.of(22, 0, 0)) || etSameInstant.toLocalTime().isBefore(LocalTime.of(8, 0, 0)) || etSameInstant.toLocalTime().isAfter(LocalTime.of(22, 0, 0)) || stSameInstant.toLocalTime().isBefore(LocalTime.of(8, 0, 0)))
                        {
                            Alert scopeAlert = new Alert(Alert.AlertType.ERROR);
                            scopeAlert.setTitle("Error!");
                            scopeAlert.setContentText("Your appointment time does not fit within our business hours (8:00 AM through 10:00 PM EST. Try something else!");
                            scopeAlert.show();
                            return;
                        }

                        //Checks if the day the user wants to have an appointment doesn't line up with the work-week of the Scheduler company.
                        if (stSameInstant.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) || etSameInstant.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()) || etSameInstant.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SUNDAY.getValue()) || stSameInstant.toLocalDate().getDayOfWeek().getValue() == (DayOfWeek.SATURDAY.getValue()))
                        {
                            Alert workWeekAlert = new Alert(Alert.AlertType.ERROR);
                            workWeekAlert.setTitle("Error!");
                            workWeekAlert.setContentText("You cannot have an appointment that day. Our work-week is Monday through Friday. Sorry!");
                            workWeekAlert.show();
                            return;
                        }

                        //Checks if the new appointment start and end time are the exact same, and puts up an alert dialog.
                        if (ldtStartSave.isEqual(ldtEndSave))
                        {
                            Alert exactSameAlert = new Alert(Alert.AlertType.ERROR);
                            exactSameAlert.setTitle("Error!");
                            exactSameAlert.setContentText("Your new appointment's start and end times are the exact same. That won't cut it!");
                            exactSameAlert.show();
                            return;
                        }

                        //Checks if the appointment start time takes place after the end time, and puts up an alert dialog if that's the case.
                        if (ldtStartSave.isAfter(ldtEndSave))
                        {
                            Alert startsAfterAlert = new Alert(Alert.AlertType.ERROR);
                            startsAfterAlert.setTitle("Error!");
                            startsAfterAlert.setContentText("Your new appointment starts after the end time. Please have it start a reasonable amount before the end time!");
                            startsAfterAlert.show();
                            return;
                        }

                        //Retrieves the Appointment parameters, which are the page attributes.
                        appointments.setAppointment_ID(Integer.parseInt(appointmentsIDField.getText()));
                        appointments.setTitle(appointmentsTitleField.getText());
                        appointments.setDescription(appointmentsDescriptionField.getText());
                        appointments.setLocation(appointmentsLocationField.getText());
                        appointments.setType(appointmentsTypeField.getText());
                        appointments.setStart(LocalDateTime.of(appointmentsStartDatePicker.getValue(),  startComboBox.getSelectionModel().getSelectedItem()));
                        appointments.setEnd(LocalDateTime.of(appointmentsEndDatePicker.getValue(), endComboBox.getSelectionModel().getSelectedItem()));
                        appointments.setCreate_Date(appointments.getCreate_Date());
                        appointments.setCreated_By(appointments.getCreated_By());
                        appointments.setLast_Update(LocalDateTime.now());
                        appointments.setLast_Updated_By(users.getUser_Name());
                        appointments.setCustomer_ID(customerComboBox.getSelectionModel().getSelectedItem().getCustomer_ID());
                        appointments.setUser_ID(userComboBox.getSelectionModel().getSelectedItem().getUser_ID());
                        appointments.setContact_ID(appointmentsContactComboBox.getSelectionModel().getSelectedItem().getContact_ID());

                        AppointmentsDAO.UpdateAppointment(appointments);

                        //Sets and refreshes the appointment TableView.
                        ObservableList<Appointments> resetAppointmentListSavedEdit = AppointmentsDAO.retrieveAppointments(connection);
                        appointmentsControllerTableView.setItems(resetAppointmentListSavedEdit);
                    }

                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

            }
        });

    }

    /**
     * Redirects the user back to the Navigator, works in conjunction with the lambda expression.
     */
    @FXML
    public void appointmentNavigatorClicked() throws IOException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Appointment Screen?");
        alert.setContentText("Are you sure you wish to exit the Appointment Screen?");
        alert.showAndWait().ifPresent(response ->
        {

            if(response == ButtonType.OK)
            {
                final Stage stage = (Stage) appointmentsNavigatorButton.getScene().getWindow();
                stage.close();
            }
        });
    }

}