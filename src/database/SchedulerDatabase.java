package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connector class that works in conjunction with the MySQL driver to open and close connections with the Scheduler Database.
 *
 * @author Harrison Thacker
 */
public class SchedulerDatabase {

    private static final String JDBC_PROTOCOL = "jdbc";
    private static final String dbVendor = ":mysql:";
    private static final String schedulerIP = "//localhost:3306/";
    private static final String DB_Name = "client_schedule";
    private static final String JDBC_URL = JDBC_PROTOCOL + dbVendor + schedulerIP + DB_Name + "?connectionTimeZone=SERVER";
    public static Connection schedulerConnection;
    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String schedulerUsername = "sqlUser";
    private static final String schedulerPassword = "Passw0rd!";
    private static PreparedStatement schedulerPreparedStatement;

    /**
     * Uses the JDBC driver along with the URL + username and password to connect to the database.
     * Throws a ClassNotFoundException if anything goes wrong.
     * @return schedulerConnection
     */
    public static Connection DBOpenConnection()
    {

        try
        {
            Class.forName(MYSQL_JDBC_DRIVER);
            schedulerConnection = DriverManager.getConnection(JDBC_URL, schedulerUsername, schedulerPassword);
        }
        catch (ClassNotFoundException | SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedulerConnection;
    }

    /**
     * Getter for the scheduler Connection object.
     * @return The connection to the scheduler database.
     */
    public static Connection getSchedulerConnection()
    {
        return schedulerConnection;
    }

    /**
     * Closes out the connection to the C195 Scheduler database.
     */
    public static void closeOutConnection()
    {

        try
        {
            schedulerConnection.close();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    /**
     * Getter for the scheduler PreparedStatement object.
     * @return The PreparedStatement of the scheduler database.
     */
    public static PreparedStatement getSchedulerPreparedStatement()
    {
        return schedulerPreparedStatement;
    }

    /**
     * Setter for the scheduler PreparedStatement object.
     * @param connection = instance of Connection object.
     * @param preparedStatement = casts PreparedStatement as a String object.
     * @throws SQLException = in case of an unforeseen error.
     */
    public static void setPreparedStatement(Connection connection, String preparedStatement) throws SQLException
    {
        schedulerPreparedStatement = connection.prepareStatement(preparedStatement);
    }

}
