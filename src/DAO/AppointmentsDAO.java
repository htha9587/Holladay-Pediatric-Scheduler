package DAO;

import java.sql.*;
import database.SchedulerDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.time.LocalDateTime;

/**
 * Appointments DAO class that handles the SQL C.R.U.D logic for the given Appointments in the Scheduler's database.
 *
 * @author Harrison Thacker
 */
public class AppointmentsDAO {

    public boolean appointmentResult; //Used to help retrieve a searched appointment by its ID or name.

    ObservableList<Appointments> appointmentIDFiltered = FXCollections.observableArrayList(); //ObservableList for Appointment ID.

    /**
     * Called when the user deletes an appointment, the SQL Query removes the appointment entry based on its corresponding ID number.
     * @param Appointment_ID = is assigned an integer from the SQL PreparedStatement object.
     * @param connection = instance of Connection object.
     * @return dropAppointmentOutput
     * @throws Exception = Outputs Error exception in the event of an unforeseen error.
     */
    public static int DropAppointment(int Appointment_ID, Connection connection) throws Exception
    {
        String dropAppointment = "DELETE FROM appointments WHERE Appointment_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(dropAppointment);

        preparedStatement.setInt(1, Appointment_ID);
        int dropAppointmentOutput = preparedStatement.executeUpdate();
        preparedStatement.close();

        return dropAppointmentOutput;
    }

    /**
     * This method calls the SQL Query that selects all columns/data from the Appointments table for the Appointments page TableView.
     *
     * @throws Exception = Outputs Error exception in the event of an unforeseen error.
     * @return listOfAppointments
     */
    public static ObservableList<Appointments> retrieveAppointments(Connection connection) throws Exception
    {
        ObservableList<Appointments> listOfAppointments = FXCollections.observableArrayList();
        String retrieveAppointments = "SELECT appointments.*, contacts.Contact_Name FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveAppointments);
        ResultSet AppointmentsRS = preparedStatement.executeQuery();

        while (AppointmentsRS.next())
        {
            int Appointment_ID = AppointmentsRS.getInt("Appointment_ID");
            String Title = AppointmentsRS.getString("Title");
            String Description = AppointmentsRS.getString("Description");
            String Location = AppointmentsRS.getString("Location");
            String Contact = AppointmentsRS.getString("Contact_Name");
            String Type = AppointmentsRS.getString("Type");
            LocalDateTime Start = AppointmentsRS.getTimestamp("Start").toLocalDateTime();
            LocalDateTime End = AppointmentsRS.getTimestamp("End").toLocalDateTime();
            int Customer_ID = AppointmentsRS.getInt("Customer_ID");
            int User_ID = AppointmentsRS.getInt("User_ID");
            int Contact_ID = AppointmentsRS.getInt("Contact_ID");

            Appointments appointments = new Appointments(Appointment_ID, Title, Description, Location, Contact, Type, Start, End, Customer_ID, User_ID, Contact_ID);
            listOfAppointments.add(appointments);//Retrieves all necessary columns and adds entry to ObservableList.
        }
        return listOfAppointments;
    }

    /**
     * Called when the user adds a new appointment and hits the Add new Appointment button, the SQL Query adds the new Appointment to the scheduler records.
     */
    public static int AddAppointment(Appointments appointments) throws SQLException
    {
        //SQL Insert statement for adding the new Appointment.
        String insertNewAppointmentStatement = "INSERT INTO appointments (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        SchedulerDatabase.setPreparedStatement(SchedulerDatabase.getSchedulerConnection(), insertNewAppointmentStatement);
        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerPreparedStatement();
        preparedStatement.setInt(1, appointments.getAppointment_ID());
        preparedStatement.setString(2, appointments.getTitle());
        preparedStatement.setString(3, appointments.getDescription());
        preparedStatement.setString(4, appointments.getLocation());
        preparedStatement.setString(5, appointments.getType());
        preparedStatement.setTimestamp(6, Timestamp.valueOf(appointments.getStart()));
        preparedStatement.setTimestamp(7, Timestamp.valueOf(appointments.getEnd()));
        preparedStatement.setTimestamp(8, Timestamp.valueOf(appointments.getCreate_Date()));
        preparedStatement.setString(9, appointments.getCreated_By());
        preparedStatement.setTimestamp(10, Timestamp.valueOf(appointments.getLast_Update()));
        preparedStatement.setString(11, appointments.getLast_Updated_By());
        preparedStatement.setInt(12, appointments.getCustomer_ID());
        preparedStatement.setInt(13, appointments.getUser_ID());
        preparedStatement.setInt(14, appointments.getContact_ID());
        int addAppointmentOutput = preparedStatement.executeUpdate();

        return addAppointmentOutput;
    }

    /**
     * Called when the user makes edits to an Appointment and clicks the Save Records button, the SQL Query updates the Appointment entry based on its corresponding ID number.
     */
    public static int UpdateAppointment(Appointments appointments) throws SQLException
    {
        //SQL Update statement for saving edits to an existing Appointment.
        String editAppointment = "UPDATE appointments SET Appointment_ID = ?, Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        SchedulerDatabase.setPreparedStatement(SchedulerDatabase.getSchedulerConnection(), editAppointment);

        //PreparedStatement sets the newly updated appointment values by retrieving all attributes the user filled out, saving them as part of the appointment record.
        PreparedStatement updateAppointmentPS = SchedulerDatabase.getSchedulerPreparedStatement();
        updateAppointmentPS.setInt(1, appointments.getAppointment_ID());
        updateAppointmentPS.setString(2, appointments.getTitle());
        updateAppointmentPS.setString(3, appointments.getDescription());
        updateAppointmentPS.setString(4, appointments.getLocation());
        updateAppointmentPS.setString(5, appointments.getType());
        updateAppointmentPS.setTimestamp(6, Timestamp.valueOf(appointments.getStart()));
        updateAppointmentPS.setTimestamp(7, Timestamp.valueOf(appointments.getEnd()));
        updateAppointmentPS.setTimestamp(8, Timestamp.valueOf(appointments.getCreate_Date()));
        updateAppointmentPS.setString(9, appointments.getCreated_By());
        updateAppointmentPS.setTimestamp(10, Timestamp.valueOf(appointments.getLast_Update()));
        updateAppointmentPS.setString(11, appointments.getLast_Updated_By());
        updateAppointmentPS.setInt(12, appointments.getCustomer_ID());
        updateAppointmentPS.setInt(13, appointments.getUser_ID());
        updateAppointmentPS.setInt(14, appointments.getContact_ID());
        updateAppointmentPS.setInt(15,appointments.getAppointment_ID());
        int editAppointmentOutput = updateAppointmentPS.executeUpdate();

        return editAppointmentOutput;
    }

    /**
     * Searches the retrieveAppointments ObservableList for a given ID match.
     *
     * @param Appointment_ID = Uses the given Appointment ID as a variable.
     * @return schedulerAppointments
     */
    public Appointments retrieveAppointmentID(int Appointment_ID) throws SQLException
    {
        ObservableList<Appointments> listOfAppointments = FXCollections.observableArrayList();
        String retrieveAppointments = "SELECT appointments.*, contacts.Contact_Name FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveAppointments);
        ResultSet AppointmentsRS = preparedStatement.executeQuery();

        while (AppointmentsRS.next())
        {
            int Appointments_ID = AppointmentsRS.getInt("Appointment_ID");
            String Title = AppointmentsRS.getString("Title");
            String Description = AppointmentsRS.getString("Description");
            String Location = AppointmentsRS.getString("Location");
            String Contact = AppointmentsRS.getString("Contact_Name");
            String Type = AppointmentsRS.getString("Type");
            LocalDateTime Start = AppointmentsRS.getTimestamp("Start").toLocalDateTime();
            LocalDateTime End = AppointmentsRS.getTimestamp("End").toLocalDateTime();
            int Customer_ID = AppointmentsRS.getInt("Customer_ID");
            int User_ID = AppointmentsRS.getInt("User_ID");
            int Contact_ID = AppointmentsRS.getInt("Contact_ID");

            Appointments appointments = new Appointments(Appointments_ID, Title, Description, Location, Contact, Type, Start, End, Customer_ID, User_ID, Contact_ID);
            listOfAppointments.add(appointments);//Retrieves all necessary columns and adds entry to ObservableList.
        }
        appointmentResult = false;

        for (Appointments schedulerAppointments : listOfAppointments)
        {
            if (schedulerAppointments.getAppointment_ID() == Appointment_ID)
            {
                appointmentResult = true;
                return schedulerAppointments;
            }
        }
        return null;
    }

    /**
     * Searches the retrieveAppointments ObservableList for a given Appointment name match.
     *
     * @param appointmentsName = Appointment name passed as a variable for this method
     * @return appointmentNamesFiltered
     */
    public ObservableList<Appointments> retrieveAppointmentName(String appointmentsName) throws SQLException
    {
        ObservableList<Appointments> listOfAppointments = FXCollections.observableArrayList();
        String retrieveAppointments = "SELECT appointments.*, contacts.Contact_Name FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveAppointments);
        ResultSet AppointmentsRS = preparedStatement.executeQuery();

        while (AppointmentsRS.next())
        {
            int Appointments_ID = AppointmentsRS.getInt("Appointment_ID");
            String Title = AppointmentsRS.getString("Title");
            String Description = AppointmentsRS.getString("Description");
            String Location = AppointmentsRS.getString("Location");
            String Contact = AppointmentsRS.getString("Contact_Name");
            String Type = AppointmentsRS.getString("Type");
            LocalDateTime Start = AppointmentsRS.getTimestamp("Start").toLocalDateTime();
            LocalDateTime End = AppointmentsRS.getTimestamp("End").toLocalDateTime();
            int Customer_ID = AppointmentsRS.getInt("Customer_ID");
            int User_ID = AppointmentsRS.getInt("User_ID");
            int Contact_ID = AppointmentsRS.getInt("Contact_ID");

            Appointments appointments = new Appointments(Appointments_ID, Title, Description, Location, Contact, Type, Start, End, Customer_ID, User_ID, Contact_ID);
            listOfAppointments.add(appointments);//Retrieves all necessary columns and adds entry to ObservableList.
        }

        appointmentResult = false;
        ObservableList<Appointments> appointmentNamesFiltered = FXCollections.observableArrayList();

        for(Appointments appointments : listOfAppointments)
        {
            if(appointments.getTitle().toLowerCase().contains(appointmentsName.toLowerCase()))
            {
                appointmentNamesFiltered.add(appointments);
            }
        }

        if(appointmentNamesFiltered.isEmpty())
        {
            return listOfAppointments;
        }

        appointmentResult = true;
        return appointmentNamesFiltered;
    }

    /**
     * Getter for the Appointment ID ObservableList.
     * @return appointmentIDFiltered = The filtered Appointment ID ObservableList.
     */
    public ObservableList<Appointments> getAppointmentIDFiltered()
    {
        return appointmentIDFiltered;
    }

}
