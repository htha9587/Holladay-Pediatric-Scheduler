package DAO;

import database.SchedulerDatabase;
import model.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Users DAO class that handles the SQL C.R.U.D logic for the given Users in the Scheduler's database.
 *
 * @author Harrison Thacker
 */
public class UsersDAO {

    /**
     * This method calls the SQL Query that selects all columns/data from the Users table.
     *
     * @throws SQLException = Outputs SQL Error exception in the event of an unforeseen error.
     * @return listOfUsers
     */
    public static ObservableList<Users> retrieveUsers() throws SQLException, Exception
    {
        ObservableList<Users> listOfUsers = FXCollections.observableArrayList();
        String retrieveUsers = "SELECT * FROM users";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveUsers);
        ResultSet UsersRS = preparedStatement.executeQuery();

        while (UsersRS.next())
        {
            int User_ID = UsersRS.getInt("User_ID");
            String User_Name = UsersRS.getString("User_Name");
            String Password = UsersRS.getString("Password");
            Date Create_Date = UsersRS.getDate("Create_Date");
            String Created_By = UsersRS.getString("Created_By");
            Timestamp Last_Update = UsersRS.getTimestamp("Last_Update");
            String Last_Updated_By = UsersRS.getString("Last_Updated_By");

            Users users = new Users(User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By);
            listOfUsers.add(users); //Retrieves all User table columns and adds to the ObservableList.
        }

        return listOfUsers;
    }

    /**
     * This method calls the SQL Query that selects a User that matches the given User_ID.
     * The ResultSet selects the matching User/Appointment record if called.
     *
     * @return usersRetrieval
     * @throws SQLException  = Outputs SQL Error exception in the event of an unforeseen error.
     */
    public Users userRetrieval(int User_ID) throws SQLException, Exception
    {
        Users usersRetrieval = new Users();
        String retrieveUsers = "SELECT * FROM users WHERE User_ID = ?";

        PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(retrieveUsers);
        preparedStatement.setInt(1, User_ID);
        ResultSet UsersRS = preparedStatement.executeQuery();

        if (UsersRS.next())
        {
            usersRetrieval = new Users(
                    UsersRS.getInt("User_ID"),
                    UsersRS.getString("User_Name"),
                    UsersRS.getString("Password"),
                    UsersRS.getDate("Create_Date"),
                    UsersRS.getString("Created_By"),
                    UsersRS.getTimestamp("Last_Update"),
                    UsersRS.getString("Last_Updated_By")
            );
        }

        return usersRetrieval;
    }

    /**
     * On the Login page, this method parses the user input and runs login validation in the database.
     *
     * @param User_Name = Name of the user.
     * @param Password = Password of the user.
     */
    public static int loginVerifyUser(String User_Name, String Password)
    {
        try
        {
            String verifyUser = "SELECT * FROM users WHERE User_Name = '" + User_Name + "' AND Password = '" + Password + "'";
            PreparedStatement preparedStatement = SchedulerDatabase.getSchedulerConnection().prepareStatement(verifyUser);

            ResultSet VerifyRS = preparedStatement.executeQuery();
            VerifyRS.next();

            if (VerifyRS.getString("User_Name").equals(User_Name) && VerifyRS.getString("Password").equals(Password))
            {
                {
                    return VerifyRS.getInt("User_ID"); //Retrieves the User ID, given both the username and password are equal.
                }

            }
        }

        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

        return -1;
    }

//    /**
//     * Constructor/superclass for the UsersDAO + Users model object.
//     * @param User_ID = ID of the user.
//     * @param User_Name = Name of the user.
//     * @param Password = Password of the given user.
//     * @param Create_Date = The date the user was added as part of the database.
//     * @param Created_By = The name of the individual who created/added the scheduler user.
//     * @param Last_Update = The timestamp when the User was last updated.
//     * @param Last_Updated_By = The name of the individual who last updated the User.
//     */
//    public UsersDAO(int User_ID, String User_Name, String Password, Date Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By)
//    {
//        super(User_ID, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By);
//    }

}
