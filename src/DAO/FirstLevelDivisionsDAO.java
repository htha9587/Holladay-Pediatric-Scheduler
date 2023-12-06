package DAO;

import database.SchedulerDatabase;
import model.FirstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * First-Level Divisions DAO class that handles the SQL C.R.U.D logic for the given User Divisions in the Scheduler's database.
 *
 * @author Harrison Thacker
 */
public class FirstLevelDivisionsDAO implements SchedulerDAO<FirstLevelDivisions> {

    ObservableList<FirstLevelDivisions> listOfDivisionsByCountry = FXCollections.observableArrayList(); //ObservableList that returns Divisions that match a selected Country.

    /**
     * This method calls the SQL Query that selects all columns/data from the Division table.
     * The ResultSet while loop iterates through all the Division table columns.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @return listOfDivisions
     */
    @Override
    public ObservableList<FirstLevelDivisions> retrieveAll() throws SQLException, Exception
    {
        try
        {
            ObservableList<FirstLevelDivisions> listOfDivisions = FXCollections.observableArrayList();
            String retrieveDivisions = "SELECT first_level_divisions.*, countries.* FROM first_level_divisions JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";

            PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveDivisions);
            ResultSet DivisionsRS = preparedStatement.executeQuery();

            while (DivisionsRS.next())
            {
                listOfDivisions.add(
                        new FirstLevelDivisions(
                                DivisionsRS.getInt("Division_ID"),
                                DivisionsRS.getString("Division"),
                                DivisionsRS.getDate("Create_Date"),
                                DivisionsRS.getString("Created_By"),
                                DivisionsRS.getTimestamp("Last_Update"),
                                DivisionsRS.getString("Last_Updated_By"),
                                DivisionsRS.getString("Country"),
                                DivisionsRS.getInt("Country_ID")
                        ));
            }

            return listOfDivisions;
        }

        catch(Exception exception)
        {
            exception.printStackTrace();
        }

        return FXCollections.observableArrayList();

    }

    /**
     * This method calls the SQL Query that selects a specific Division based on its given ID.
     * The ResultSet while loop iterates through all the Division table columns.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @return specificList
     */
    public FirstLevelDivisions retrieveSpecific(int Division_ID) throws SQLException, Exception
    {
            FirstLevelDivisions specificRetrieval = new FirstLevelDivisions();
            String retrieveDivisions = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID AND first_level_divisions.Division_ID = ?";

            PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveDivisions);
            preparedStatement.setInt(1, Division_ID);
            ResultSet DivisionsRS = preparedStatement.executeQuery();

            if (DivisionsRS.next())
            {
                specificRetrieval = new FirstLevelDivisions(
                                DivisionsRS.getInt("Division_ID"),
                                DivisionsRS.getString("Division"),
                                DivisionsRS.getInt("Country_ID"),
                                DivisionsRS.getString("Country")
                        );
            }

        return specificRetrieval;
    }

    /**
     * This method calls the SQL Query that selects a specific Division based on its unique ID.
     * The ResultSet selects the matching Division record if called.
     *
     * @return firstLevelDivisionsRetrieval
     * @throws SQLException  = Outputs SQL Error exception in the event of an unforeseen error.
     */
    public FirstLevelDivisions lookupCountryAndDivision(FirstLevelDivisions firstLevelDivisions) throws SQLException, Exception
    {
        FirstLevelDivisions firstLevelDivisionsRetrieval = new FirstLevelDivisions();
        String retrieveComboBoxDivisions = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID AND Country_ID = ?";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveComboBoxDivisions);
        preparedStatement.setInt(1, firstLevelDivisions.getDivision_ID());
        ResultSet ComboBoxRS = preparedStatement.executeQuery();

        if (ComboBoxRS.next())
        {
            firstLevelDivisionsRetrieval = new FirstLevelDivisions(
             ComboBoxRS.getInt("Division_ID"),
             ComboBoxRS.getString("Division"),
             ComboBoxRS.getDate("Create_Date"),
             ComboBoxRS.getString("Created_By"),
             ComboBoxRS.getTimestamp("Last_Update"),
             ComboBoxRS.getString("Last_Updated_By"),
             ComboBoxRS.getString("Country"),
             ComboBoxRS.getInt("Country_ID")
            );
        }

        return firstLevelDivisionsRetrieval;
    }

    /**
     * This method calls the SQL Query that selects the Divisions by their respective Country ID.
     * Made for use by the Customers Country and Division ComboBoxes.
     *
     * @return listOfDivisionsByCountry
     * @throws SQLException  = Outputs SQL Error exception in the event of an unforeseen error.
     */
    public ObservableList<FirstLevelDivisions> retrieveDivisionByCountryName(int Country_ID) throws SQLException, Exception
    {
        try
        {
            String retrieveComboBoxDivisions = "SELECT * FROM first_level_divisions, countries WHERE first_level_divisions.Country_ID = countries.Country_ID AND first_level_divisions.Country_ID = ?";

            PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveComboBoxDivisions);
            preparedStatement.setInt(1, Country_ID);
            ResultSet ComboBoxRS = preparedStatement.executeQuery();

            while(ComboBoxRS.next())
            {
                int Division_ID = ComboBoxRS.getInt("Division_ID");
                Country_ID = ComboBoxRS.getInt("Country_ID");
                String Division = ComboBoxRS.getString("Division");
                String Country = ComboBoxRS.getString("Country");
                FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(Division_ID, Country_ID, Division, Country);
                listOfDivisionsByCountry.add(firstLevelDivisions);
            }
        }

        catch(Exception exception)
        {
            System.out.println("Error: " + exception.getMessage());
        }

        return listOfDivisionsByCountry;
    }

    /**
     * Getter for the retrieve Divisions By Country ObservableList.
     *
     * @return listOfDivisionsByCountry
     */
    public ObservableList<FirstLevelDivisions> getListOfDivisionsByCountry()
    {
        return listOfDivisionsByCountry;
    }

}
