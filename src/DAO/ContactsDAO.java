package DAO;

import model.Contacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.SchedulerDatabase;

/**
 * Contacts DAO class that handles the SQL C.R.U.D logic for the given Appointment contacts in the Scheduler's database.
 *
 * @author Harrison Thacker
 */
public class ContactsDAO {

    /**
     * This query selects all IDs from the Contacts table and finds the Contact that corresponds to the given ID.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @param Contact_ID = ID of given Contact.
     * @return contactRetrieval
     */
    public Contacts retrieveIDContact(int Contact_ID) throws SQLException, Exception
    {
        Contacts contactRetrieval = new Contacts();

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement("SELECT * FROM contacts WHERE Contact_ID = ?");
        preparedStatement.setInt(1, Contact_ID);
        ResultSet ContactsRS = preparedStatement.executeQuery();

        if (ContactsRS.next())
        {
            contactRetrieval = new Contacts(
                    ContactsRS.getInt("Contact_ID"),
            ContactsRS.getString("Contact_Name"),
            ContactsRS.getString("Email")
            );
        }

        return contactRetrieval;
    }

    /**
     * This method calls the SQL Query that selects all columns/data from the Contacts table.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @return listOfContacts
     */
    public static ObservableList<Contacts> retrieveContacts() throws SQLException, Exception
    {
        ObservableList<Contacts> listOfContacts = FXCollections.observableArrayList();
        String retrieveContacts = "SELECT * FROM contacts";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveContacts);
        ResultSet AllContactsRS = preparedStatement.executeQuery();

        while (AllContactsRS.next())
        {
            int Contact_ID = AllContactsRS.getInt("Contact_ID");
            String Contact_Name = AllContactsRS.getString("Contact_Name");
            String Email = AllContactsRS.getString("Email");

            Contacts contacts = new Contacts(Contact_ID, Contact_Name, Email);
            listOfContacts.add(contacts); //Retrieves all columns and adds entry to ObservableList.
        }

        return listOfContacts;
    }

}