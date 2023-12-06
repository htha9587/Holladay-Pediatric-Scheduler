package DAO;

import model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import database.SchedulerDatabase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Countries DAO class that handles the SQL C.R.U.D logic for the given Customer/User countries in the Scheduler's database.
 *
 * @author Harrison Thacker
 */
public class CountriesDAO implements SchedulerDAO<Countries> {

    /**
     * This method calls the SQL Query that selects all columns from the Countries table.
     * The ResultSet while loop iterates through and gathers all the Country table columns.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @return listOfCountries
     */
    @Override
    public ObservableList<Countries> retrieveAll() throws SQLException, Exception
    {
        ObservableList<Countries> listOfCountries = FXCollections.observableArrayList();
        try
        {

            String retrieveCountries = "SELECT * FROM countries";

            PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveCountries);
            ResultSet CountriesRS = preparedStatement.executeQuery();

            while (CountriesRS.next())
            {
                listOfCountries.add(new Countries(
                                CountriesRS.getInt("Country_ID"),
                                CountriesRS.getString("Country"),
                                CountriesRS.getDate("Create_Date"),
                                CountriesRS.getString("Created_By"),
                                CountriesRS.getTimestamp("Last_Update"),
                                CountriesRS.getString("Last_Updated_By")
                        )
                );
            }

        }

        catch(Exception exception)
        {
            exception.printStackTrace();
        }

        return listOfCountries;
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
     * This method calls the SQL Query that selects the Countries along with their associated Divisions. Intended for the Province ComboBox.
     * The ResultSet while loop iterates through and gathers all the Countries joined with the Divisions. Country_ID is joined on because it's the
     * respective foreign key.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @return listOfCountries
     */
    public ObservableList<Countries> retrieveDivisionMatch() throws SQLException, Exception
    {
        ObservableList<Countries> listOfCountries = FXCollections.observableArrayList();
        String retrieveCountries = "SELECT DISTINCT countries.* FROM countries JOIN first_level_divisions ON countries.Country_ID = first_level_divisions.Country_ID";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveCountries);
        ResultSet CountriesRS = preparedStatement.executeQuery();

        while (CountriesRS.next())
        {
            listOfCountries.add(new Countries(
                            CountriesRS.getInt("Country_ID"),
                            CountriesRS.getString("Country"),
                            CountriesRS.getDate("Create_Date"),
                            CountriesRS.getString("Created_By"),
                            CountriesRS.getTimestamp("Last_Update"),
                            CountriesRS.getString("Last_Updated_By")
                    )
            );
        }

        return listOfCountries;
    }

}
