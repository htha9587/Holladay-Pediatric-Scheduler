package controller;

import DAO.AppointmentsDAO;
import database.SchedulerDatabase;
import model.*;
import java.sql.Connection;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import DAO.ReportsDAO;
import DAO.ContactsDAO;
import java.time.Month;

/**
 * FXML Controller class for the Reports page of the Holladay Pediatric Scheduler.
 *
 * @author Harrison Thacker
 */
public class reportsController {

    @FXML
    private Button reportPageReturnButton; //Button that redirects back to the navigator, works in conjunction with a lambda expression.

    @FXML
    private TableView<MonthOfAppointment> appointmentTotalMonthTable; //TableView for the Appointment months.

    @FXML
    private TableColumn<?, ?> totalAppointmentMonthColumn; //Column for the Appointment months.

    @FXML
    private TableColumn<?, ?> monthTotalColumn; //Column for the total count of Appointment months.

    @FXML
    private TableView<TypeOfAppointment> appointmentTotalTypeTable; //TableView for the Appointment type.

    @FXML
    private TableColumn<?, ?> appointmentTotalType; //Column for the Appointment type.

    @FXML
    private TableColumn<?, ?> appointmentTypeTotalColumn; //Column for the total count of Appointment types.

    @FXML
    private ComboBox<String> appointmentScheduleContactComboBox; //ComboBox for selecting the Appointment schedule of a given contact.

    @FXML
    private TableView<Appointments> appointmentsReportTableView; //TableView for the Appointments Scheduler report.

    @FXML
    private TableColumn<?, ?> appointmentIDColumn; //Column for the Appointment ID.

    @FXML
    private TableColumn<?, ?> titleColumn; //Column for the Appointment name.

    @FXML
    private TableColumn<?, ?> typeColumn; //Column for the Appointment type.

    @FXML
    private TableColumn<?, ?> descriptionColumn; //Column for the Appointment description.

    @FXML
    private TableColumn<?, ?> contactColumn; //Column for the Appointment Contact.

    @FXML
    private TableColumn<?, ?> contactIDColumn; //Column for the Appointment Contact's ID.

    @FXML
    private TableColumn<?, ?> startDateTimeColumn; //Column for the Appointment Start Date/Time.

    @FXML
    private TableColumn<?, ?> endDateTimeColumn; //Column for the Appointment End Date/Time.

    @FXML
    private TableColumn<?, ?> customerIDColumn; //Column for the Appointment Customer's ID.

    @FXML
    private TableView<CustomerReports> reportCountryDivisionTable; //TableView for the Custom Scheduler Country + Division report.

    @FXML
    private TableColumn<?, ?> reportCountryNameColumn; //Column for the Country name in the custom report.

    @FXML
    private TableColumn<?, ?> reportDivisionColumn; //Column for the Division name in the custom report.

    @FXML
    private TableColumn<?, ?> reportTotalDivisionColumn; //Column for the sum total of the Country + Division.


    /**
     * Initializes the controller class. On launch, all the Report page attributes are set up.
     *
     * @throws Exception = thrown in the event of an unforeseen error.
     *<p>
     * LAMBDA - listOfContacts: The ObservableList forEach lambda iterates through and adds the names of all the appointment contacts for the contact ComboBox.
     * There's not as much code involved compared to a for loop which wouldn't be as neat and concise.
     * reportPageReturnButton: Used for when the "Return To Navigator" button is clicked. It calls the void "handler" method as an executable.
     * The button already has an event handler in the lambda expression statement which acts as input.
     * This separation results in slightly less code and is more readable this way.
     * appointmentScheduleContactComboBox: When the contact ComboBox is clicked, it calls the void "handler" method: initializeContactScheduleTV as an executable.
     * This allows the user to select the appointment schedule for the given contact, populating the report TableView below with the matching contact appointment info.
     * No FXML tag or attribute is required for the event handler lambda. Not only does this result in slightly less code, it's very elegant and readable to others.
     */
    public void initialize() throws Exception
    {
        //Has the contact ComboBox retrieve and display the available Appointment contacts.
        ObservableList<Contacts> listOfContacts = ContactsDAO.retrieveContacts();
        ObservableList<String> comboBoxContacts = FXCollections.observableArrayList();

        //Lambda expression that iterates through and has the ObservableList add the names of all the appointment contacts for the contact ComboBox.
        listOfContacts.forEach(contacts -> comboBoxContacts.add(contacts.getContact_Name()));
        appointmentScheduleContactComboBox.setItems(comboBoxContacts);

        //Sets the table rows/columns for the Appointment Month TableView.
        totalAppointmentMonthColumn.setCellValueFactory(new PropertyValueFactory<>("monthOfAppointment"));
        monthTotalColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentMonthTotal"));

        //Sets the table rows/columns for the Appointment Type TableView.
        appointmentTotalType.setCellValueFactory(new PropertyValueFactory<>("typeOfAppointment"));
        appointmentTypeTotalColumn.setCellValueFactory(new PropertyValueFactory<>("typeOfAppointmentTVTotal"));

        //Sets the table rows/columns for the Contact Appointment Schedule TableView.
        appointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("Appointment_ID"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        contactIDColumn.setCellValueFactory(new PropertyValueFactory<>("Contact_ID"));
        startDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
        endDateTimeColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
        customerIDColumn.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));

        //Sets the table rows/columns for the Custom Country + Division Report.
        reportCountryNameColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfCountry"));
        reportDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("nameOfDivision"));
        reportTotalDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("countryDivisionTotal"));

        //Lambda expression which calls the event method, closing out the stage and bringing the user back to the Navigator.
        reportPageReturnButton.setOnAction(event ->
                reportNavigatorRedirect());

        //Lambda expression which calls the event method, loading the Appointment Schedule report TableView with the given Appointment contact's records.
        appointmentScheduleContactComboBox.setOnAction(event ->
                initializeContactScheduleTV());

    }

    /**
     * Redirects the user back to the Navigator, works in conjunction with the lambda expression.
     */
    public void reportNavigatorRedirect()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Report Screen?");
        alert.setContentText("Are you sure you wish to exit the Scheduler Report Screen?");
        alert.showAndWait().ifPresent(response ->
        {

            if(response == ButtonType.OK)
            {
                final Stage stage = (Stage) reportPageReturnButton.getScene().getWindow();
                stage.close();
            }
        });
    }

    /**
     * Loads and fills out the columns for my custom Appointments in Countries + First Level Division report when the respective tab is clicked by the user.
     */
    @FXML
    public void customReportClicked()
    {

        try
        {
            ObservableList<CustomerReports> divisionsAndCountries = FXCollections.observableArrayList();
            ObservableList<CustomerReports> listOfCustomerReports = ReportsDAO.retrieveCustomCountryReport();

            divisionsAndCountries.addAll(listOfCustomerReports);
            reportCountryDivisionTable.setItems(divisionsAndCountries);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }


    /**
     * Initializes the Contact Schedule TableView with all the data from the required Database table columns.
     */
    public void initializeContactScheduleTV()
    {

        try
        {
            //Connects to the scheduler database.
            Connection connection = SchedulerDatabase.DBOpenConnection();

            //Initializes the ObservableLists for the Contacts and the respective information about the Appointments.
            int Contact_ID = 0;
            Appointments appointments;

            ObservableList<Appointments> retrieveAppointments = AppointmentsDAO.retrieveAppointments(connection);
            ObservableList<Appointments> contactAppointmentSchedule = FXCollections.observableArrayList();
            ObservableList<Contacts> retrieveContacts = ContactsDAO.retrieveContacts();

            String contactComboBoxList;
            contactComboBoxList = (appointmentScheduleContactComboBox.getSelectionModel().getSelectedItem());
            //Iterates through and retrieves the names of the Contacts for the Appointment schedule ComboBox.
            for (Contacts appointmentContact: retrieveContacts)
            {
                if (contactComboBoxList.equals(appointmentContact.getContact_Name()))
                {
                    Contact_ID = appointmentContact.getContact_ID();
                }

            }

            //Iterates through and makes sure the appointments match by checking the respective Contact ID. If a match is successful, the appointment is added to the ObservableList.
            for (Appointments schedulerAppointments: retrieveAppointments)
            {
                if (schedulerAppointments.getContact_ID() == Contact_ID)
                {
                    appointments = schedulerAppointments;
                    contactAppointmentSchedule.add(appointments);
                }

        }

            appointmentsReportTableView.setItems(contactAppointmentSchedule);

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }


    /**
     * Initializes the two Appointment TableView reports with all the data from the required Database table columns.
     *<p>
     * LAMBDA - availableAppointmentMonth: Iterates through and filters the appointment months to find one that matches between the month model object and the ObservableList.
     * The forEach expression contained within doesn't use as much code as a for loop would, resulting in a clean and concise expression.
     * retrieveAppointments: Appointments model object that converts to an appointment month ObservableList. Retrieves the appointment's respective start month.
     * The forEach expression contained within that adds new available months would be more elaborate and nowhere near as elegant if a for loop was used.
     * retrieveAppointments.forEach: Iterates through and adds appointment types to the respective ObservableList. Takes up less code than a for loop and is more cleaner and readable.
     */
    @FXML
    public void initializeAppointmentReportTV()
    {

        try
        {
            //Connects to the scheduler database.
            Connection connection = SchedulerDatabase.DBOpenConnection();

            //Loads the ObservableList with the data from the Appointment database.
            ObservableList<Appointments> retrieveAppointments = AppointmentsDAO.retrieveAppointments(connection);

            //Initializes the ObservableLists for the Appointment Month TableView and its attributes.
            ObservableList<MonthOfAppointment> monthOfAppointments = FXCollections.observableArrayList();
            ObservableList<Month> availableAppointmentMonth = FXCollections.observableArrayList();
            ObservableList<Month> appointmentRespectiveMonth = FXCollections.observableArrayList();

            //Initializes the ObservableLists for the Appointment Type TableView and its attributes.
            ObservableList<TypeOfAppointment> typeOfAppointments = FXCollections.observableArrayList();
            ObservableList<String> availableTypeOfAppointment = FXCollections.observableArrayList();
            ObservableList<String> appointmentRespectiveType = FXCollections.observableArrayList();


            //Lambda that iterates through and gets the start month of each appointment.
            retrieveAppointments.stream().map(appointment ->
                    appointment.getStart().getMonth()).forEach(availableAppointmentMonth::add);

            //Lambda that adds appointment months that match the given criteria.
            availableAppointmentMonth.stream().filter(appointmentMonth ->
                    !appointmentRespectiveMonth.contains(appointmentMonth)).forEach(appointmentRespectiveMonth::add);

            //Lambda that iterates through and gets the type of each appointment.
            retrieveAppointments.forEach(appointments ->
                    availableTypeOfAppointment.add(appointments.getType()));


            //Iterates through and retrieves the appointment types. If they match, then they're added to the TableView.
            for (Appointments appointmentType: retrieveAppointments)
            {

                String typeOfSchedulerAppointment = appointmentType.getType();

                if (!appointmentRespectiveType.contains(typeOfSchedulerAppointment))
                {
                    appointmentRespectiveType.add(typeOfSchedulerAppointment);
                }

            }

            //Iterates through and adds new Appointment types with aid of the Appointment Type model class, then sets the TableView with appointment type ObservableList.
            for (String schedulerAppointmentType: appointmentRespectiveType)
            {
                int appointmentTypeTableTotal = Collections.frequency(availableTypeOfAppointment, schedulerAppointmentType);

                TypeOfAppointment typeOfAppointment = new TypeOfAppointment(schedulerAppointmentType, appointmentTypeTableTotal);
                typeOfAppointments.add(typeOfAppointment);
            }

            appointmentTotalTypeTable.setItems(typeOfAppointments);

            //Iterates through and adds new Appointment months with aid of the Appointment Month model class, then sets the TableView with appointment month ObservableList.
            for (Month appointmentMonth: appointmentRespectiveMonth)
            {
                String titleOfAppointmentMonth = appointmentMonth.name();
                int appointmentMonthTableTotal = Collections.frequency(availableAppointmentMonth, appointmentMonth);

                MonthOfAppointment monthOfAppointment = new MonthOfAppointment(titleOfAppointmentMonth, appointmentMonthTableTotal);
                monthOfAppointments.add(monthOfAppointment);
            }

            appointmentTotalMonthTable.setItems(monthOfAppointments);

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

}
