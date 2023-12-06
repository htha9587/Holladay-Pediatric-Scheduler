package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import database.SchedulerDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.CustomerReports;

/**
 * My custom Reports DAO class that handles the SQL C.R.U.D logic for the Custom Reports in the Scheduler's database.
 *
 * @author Harrison Thacker
 */
public class ReportsDAO extends Appointments {

    /**
     * This method calls the SQL Query that selects the Country tally count, Divisions, and Customer names for my custom Report TableView.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @return customCountryReportList
     */
    public static ObservableList<CustomerReports> retrieveCustomCountryReport() throws SQLException, Exception
    {
        ObservableList<CustomerReports> customCountryReportList = FXCollections.observableArrayList();

        String customReportSelectStatement = "SELECT countries.Country, first_level_divisions.Division, COUNT(*) as sumCountryDivisionTotal " +
                "FROM customers, first_level_divisions, countries WHERE customers.Division_ID = first_level_divisions.Division_ID " +
                "AND first_level_divisions.Country_ID = countries.Country_ID GROUP BY countries.Country, first_level_divisions.Division " +
                "ORDER BY countries.Country, first_level_divisions.Division DESC";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(customReportSelectStatement);
        ResultSet customReportRS = preparedStatement.executeQuery();


        while (customReportRS.next())
        {
            String nameOfCountry = customReportRS.getString("Country");
            String nameOfDivision = customReportRS.getString("Division");
            int countryTotal = customReportRS.getInt("sumCountryDivisionTotal");

            CustomerReports customerReports = new CustomerReports(countryTotal,nameOfCountry, nameOfDivision);
            customCountryReportList.add(customerReports); //Retrieves the necessary columns and adds the entry to the ObservableList.
        }
        return customCountryReportList;
    }

    /**
     * Constructor/superclass for the ReportsDAO/Appointments model object.
     * @param Appointment_ID = The ID of the given appointment.
     * @param Title = The name of the given appointment.
     * @param Description = The description of the given appointment.
     * @param Location = The location the appointment takes place.
     * @param Contact = The name of the given Contact at the appointment.
     * @param Type = The type of the given appointment.
     * @param Start = The starting time of the given appointment.
     * @param End = The ending time of the given appointment.
     * @param Customer_ID = The ID of the corresponding Customer.
     * @param User_ID = The ID of the corresponding User.
     */
    public ReportsDAO(int Appointment_ID, String Title, String Description, String Location, String Contact, String Type, LocalDateTime Start, LocalDateTime End,
                      Date Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By, int Customer_ID, int User_ID, int Contact_ID)
    {
        super(Appointment_ID, Title, Description, Location, Contact, Type, Start, End, Customer_ID, User_ID, Contact_ID);
    }

}
