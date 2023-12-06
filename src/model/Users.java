package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Users Model class that handles the logic and attributes of the scheduler user for the Login page.
 *
 * @author Harrison Thacker
 */
public class Users {

    private int User_ID; //The ID of the given user.
    private String User_Name; //The name of the given user.
    private String Password; //The password of the given user.
    private Date Create_Date; //The date the user was added as part of the database.
    private String Created_By; //The name of the individual who created/added the scheduler user.
    private Timestamp Last_Update; //The timestamp when the User was last updated.
    private String Last_Updated_By; //The name of the individual who last updated the User.

    /**
     *
     * @param User_ID = The ID of the given user.
     * @param User_Name = The name of the given user.
     * @param Password = The password of the given user.
     * @param Create_Date = The date the user was added as part of the database.
     * @param Created_By = The name of the individual who created/added the scheduler user.
     * @param Last_Update = The timestamp when the User was last updated.
     * @param Last_Updated_By = The name of the individual who last updated the User.
     */
    public Users(int User_ID, String User_Name, String Password, Date Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By)
    {
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    /**
     * Constructor used for the Appointments page looking up a specific User.
     */
    public Users()
    {
        this.User_ID = User_ID;
        this.User_Name = User_Name;
        this.Password = Password;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    /** Getter for the User ID.
     *
     * @return User_ID
     */
    public int getUser_ID()
    {
        return User_ID;
    }

    /** Getter for the User name.
     *
     * @return User_Name
     */
    public String getUser_Name()
    {
        return User_Name;
    }

    /** Getter for the User password.
     *
     * @return Password
     */
    public String getPassword()
    {
        return Password;
    }

    /**
     * Getter for the date the User was added.
     *
     * @return Create_Date
     */
    public Date getCreate_Date()
    {
        return Create_Date;
    }

    /**
     * Getter for the individual who added the User.
     *
     * @return Created_By
     */
    public String getCreated_By()
    {
        return Created_By;
    }

    /**
     * Getter for the timeframe when the User was last updated.
     *
     * @return Last_Update
     */
    public Timestamp getLast_Update()
    {
        this.Last_Update = Timestamp.valueOf(LocalDateTime.of(Last_Update.toLocalDateTime().toLocalDate(), Last_Update.toLocalDateTime().toLocalTime()));
        return this.Last_Update;
    }

    /**
     * Getter for the individual who last updated the User.
     *
     * @return Last_Updated_By
     */
    public String getLast_Updated_By()
    {
        return Last_Updated_By;
    }

    /**
     * toString method for the name of the Scheduler User.
     * @return User_Name
     */
    @Override
    public String toString()
    {
        return (User_Name);
    }

}