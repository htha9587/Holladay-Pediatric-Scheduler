package DAO;

import database.SchedulerDatabase;
import model.Countries;
import model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;

/**
 * Customers DAO class that handles the SQL C.R.U.D logic for the given Customers in the Scheduler's database.
 *
 * @author Harrison Thacker
 */
public class CustomersDAO {

    public boolean customerResult; //Used to help retrieve a searched customer by its ID or name.

    ObservableList<Customers> customerIDFiltered = FXCollections.observableArrayList(); //ObservableList for Customer ID.

    /**
     * Called when the user deletes a Customer, the SQL Query removes the Customer entry based on its corresponding ID number.
     *
     * @param Customer_ID      = is assigned an integer from the SQL PreparedStatement object.
     * @param connection = instance of Connection object.
     * @return dropCustomerOutput
     * @throws Exception = Outputs Error exception in the event of an unforeseen error.
     */
    public static int DropCustomer(int Customer_ID, Connection connection) throws Exception {
        //SQL DELETE statement for removing the Customer.
        String dropCustomer = "DELETE FROM customers WHERE Customer_ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(dropCustomer);

        preparedStatement.setInt(1, Customer_ID);
        int dropCustomerOutput = preparedStatement.executeUpdate();
        preparedStatement.close();

        return dropCustomerOutput;
    }

    /**
     * This method calls the SQL Query that selects the necessary columns/data from the Customers table for the Customers page TableView.
     *
     * @return listOfCustomers
     * @throws Exception = Outputs Error exception in the event of an unforeseen error.
     */
    public static ObservableList<Customers> retrieveCustomers(Connection connection) throws Exception {
        ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
        String retrieveCustomers = "SELECT * FROM customers, first_level_divisions, countries WHERE " +
                "customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveCustomers);
        ResultSet CustomersRS = preparedStatement.executeQuery();

        while (CustomersRS.next()) {
            int Customer_ID = CustomersRS.getInt("Customer_ID");
            String Customer_Name = CustomersRS.getString("Customer_Name");
            String Address = CustomersRS.getString("Address");
            String Postal_Code = CustomersRS.getString("Postal_Code");
            String Phone = CustomersRS.getString("Phone");
            int Country_ID = CustomersRS.getInt("Country_ID");
            int Division_ID = CustomersRS.getInt("Division_ID");
            String Country = CustomersRS.getString("Country");
            String Division = CustomersRS.getString("Division");

            Customers customers = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Country_ID, Division_ID, Country, Division);
            listOfCustomers.add(customers); //Retrieves all columns and adds entry to ObservableList.

        }

        return listOfCustomers;
    }

    /**
     * This method calls the SQL Query that selects a Customer that matches the given Customer_ID.
     * The ResultSet selects the matching Customer/Appointment record if called.
     *
     * @return customerRetrieval
     * @throws SQLException  = Outputs SQL Error exception in the event of an unforeseen error.
     */
    public Customers customersRetrieval(int Customer_ID) throws SQLException, Exception
    {
        Customers customerRetrieval = new Customers();
        String retrieveCountries = "SELECT * FROM customers, countries, first_level_divisions WHERE Customer_ID = ?";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveCountries);
        preparedStatement.setInt(1, Customer_ID);
        ResultSet CustomerRS = preparedStatement.executeQuery();

        if (CustomerRS.next())
        {
            customerRetrieval = new Customers(
             CustomerRS.getInt("Customer_ID"),
             CustomerRS.getString("Customer_Name"),
             CustomerRS.getString("Address"),
             CustomerRS.getString("Postal_Code"),
             CustomerRS.getString("Phone"),
             CustomerRS.getInt("Country_ID"),
             CustomerRS.getInt("Division_ID"),
             CustomerRS.getString("Country"),
             CustomerRS.getString("Division")
            );
        }

        return customerRetrieval;
    }

    /**
     * This method calls the SQL Query that selects a Country that matches the given Country_ID.
     * The ResultSet selects the matching Country record if called.
     *
     * @return countriesRetrieval
     * @throws SQLException  = Outputs SQL Error exception in the event of an unforeseen error.
     */
    public Countries lookupCountryAndDivision(int Country_ID) throws SQLException, Exception
    {
        Countries countriesRetrieval = new Countries();
        String retrieveCountries = "SELECT * FROM countries WHERE Country_ID = ?";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveCountries);
        preparedStatement.setInt(1, Country_ID);
        ResultSet CountriesRS = preparedStatement.executeQuery();

        if (CountriesRS.next())
        {
            countriesRetrieval = new Countries(
                    CountriesRS.getInt("Country_ID"),
                    CountriesRS.getString("Country"),
                    CountriesRS.getDate("Create_Date"),
                    CountriesRS.getString("Created_By"),
                    CountriesRS.getTimestamp("Last_Update"),
                    CountriesRS.getString("Last_Updated_By")
            );
        }

        return countriesRetrieval;
    }

    /**
     * Called when the user adds a new customer and hits the Add new Customer button, the SQL Query adds the new Customer to the scheduler records.
     */
    public static int AddCustomer(Customers customers) throws SQLException
    {
        //SQL Insert statement for adding the new Customer.
        String newCustomerStatement = "INSERT INTO customers (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";

        SchedulerDatabase.setPreparedStatement(SchedulerDatabase.getSchedulerConnection(), newCustomerStatement);
        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerPreparedStatement();
            preparedStatement.setInt(1, customers.getCustomer_ID());
            preparedStatement.setString(2, customers.getCustomer_Name());
            preparedStatement.setString(3, customers.getAddress());
            preparedStatement.setString(4, customers.getPostal_Code());
            preparedStatement.setString(5, customers.getPhone());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, "Scheduler Administrator");
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, "Scheduler Administrator");
            preparedStatement.setInt(10, customers.getDivision_ID());
            int addCustomerOutput = preparedStatement.executeUpdate();

        return addCustomerOutput;
    }

    /**
     * Called when the user makes edits to a Customer and clicks the Save Records button, the SQL Query updates the Customer entry based on its corresponding ID number.
     */
    public static int UpdateCustomer(Customers customers) throws SQLException
    {
        //SQL Update statement for saving edits to an existing customer.
        String editCustomer = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

        SchedulerDatabase.setPreparedStatement(SchedulerDatabase.getSchedulerConnection(), editCustomer);
        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerPreparedStatement();
            preparedStatement.setInt(1, customers.getCustomer_ID());
            preparedStatement.setString(2, customers.getCustomer_Name());
            preparedStatement.setString(3, customers.getAddress());
            preparedStatement.setString(4, customers.getPostal_Code());
            preparedStatement.setString(5, customers.getPhone());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, "Scheduler Administrator");
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, "Scheduler Administrator");
            preparedStatement.setInt(10, customers.getDivision_ID());
            preparedStatement.setInt(11, customers.getCustomer_ID());
            int editCustomerOutput = preparedStatement.executeUpdate();

        return editCustomerOutput;
    }

    /**
     * Searches the retrieveCustomers ObservableList for a given ID match.
     *
     * @param Customer_ID = Uses the given Customer ID as a variable.
     * @return customerIDFiltered
     */
    public Customers retrieveCustomerID(int Customer_ID) throws SQLException
    {
        ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
        String retrieveCustomers = "SELECT * FROM customers, first_level_divisions, countries WHERE " +
                "customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveCustomers);
        ResultSet retrieveCustomerID = preparedStatement.executeQuery();

        while (retrieveCustomerID.next()) {
            int Customers_ID = retrieveCustomerID.getInt("Customer_ID");
            String Customer_Name = retrieveCustomerID.getString("Customer_Name");
            String Address = retrieveCustomerID.getString("Address");
            String Postal_Code = retrieveCustomerID.getString("Postal_Code");
            String Phone = retrieveCustomerID.getString("Phone");
            int Country_ID = retrieveCustomerID.getInt("Country_ID");
            int Division_ID = retrieveCustomerID.getInt("Division_ID");
            String Country = retrieveCustomerID.getString("Country");
            String Division = retrieveCustomerID.getString("Division");

            Customers customers = new Customers(Customers_ID, Customer_Name, Address, Postal_Code, Phone, Country_ID, Division_ID, Country, Division);
            listOfCustomers.add(customers); //Retrieves all columns and adds entry to ObservableList.
        }
        customerResult = false;

        for (Customers customers : listOfCustomers)
        {
            if(customers.getCustomer_ID() == Customer_ID)
            {
                customerResult = true;
                return customers;
            }
        }
        return null;
    }

    /**
     * Searches the retrieveCustomers ObservableList for a given Customer name match.
     *
     * @param customersName = Customer name passed as a variable for this method
     * @return customerNamesFiltered
     */
    public ObservableList<Customers> retrieveCustomerName(String customersName) throws SQLException
    {
        ObservableList<Customers> listOfCustomers = FXCollections.observableArrayList();
        String retrieveCustomers = "SELECT * FROM customers, first_level_divisions, countries WHERE " +
                "customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveCustomers);
        ResultSet retrieveCustomerID = preparedStatement.executeQuery();

        while (retrieveCustomerID.next()) {
            int Customer_ID = retrieveCustomerID.getInt("Customer_ID");
            String Customer_Name = retrieveCustomerID.getString("Customer_Name");
            String Address = retrieveCustomerID.getString("Address");
            String Postal_Code = retrieveCustomerID.getString("Postal_Code");
            String Phone = retrieveCustomerID.getString("Phone");
            int Country_ID = retrieveCustomerID.getInt("Country_ID");
            int Division_ID = retrieveCustomerID.getInt("Division_ID");
            String Country = retrieveCustomerID.getString("Country");
            String Division = retrieveCustomerID.getString("Division");

            Customers customers = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Country_ID, Division_ID, Country, Division);
            listOfCustomers.add(customers); //Retrieves all columns and adds entry to ObservableList.
        }

        customerResult = false;
        ObservableList<Customers> customerNamesFiltered = FXCollections.observableArrayList();

        for(Customers customers : listOfCustomers)
        {
            if(customers.getCustomer_Name().toLowerCase().contains(customersName.toLowerCase()))
            {
                customerNamesFiltered.add(customers);
            }
        }

        if(customerNamesFiltered.isEmpty())
        {
            return listOfCustomers;
        }

        customerResult = true;
        return customerNamesFiltered;
    }

    /**
     * Getter for the Customer ID ObservableList.
     * @return customerIDFiltered = The filtered Customer ID ObservableList.
     */
    public ObservableList<Customers> getCustomerIDFiltered()
    {
        return customerIDFiltered;
    }

}
