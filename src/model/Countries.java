package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Countries Model class that handles the logic and attributes of a given country in the scheduler.
 *
 * @author Harrison Thacker
 */
public class Countries {

    private int Country_ID; //The ID of the Country.
    private String Country; //The name of the Country.
    private Date Create_Date; //The date the country was added as part of the database.
    private String Created_By; //The name of the user who created/added the country.
    private Timestamp Last_Update; //The timestamp when the Country was last updated.
    private String Last_Updated_By; //The name of the user who last updated the Country.

    /**
     * Constructor for the Countries class.
     *
     * @param Country_ID = The ID of the Country.
     * @param Country = The name of the Country.
     * @param Create_Date = The date the country was added as part of the database.
     * @param Created_By = The name of the user who created/added the country.
     * @param Last_Update = The timestamp when the Country was last updated.
     * @param Last_Updated_By = The name of the user who last updated the Country.
     */
    public Countries(int Country_ID, String Country, Date Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By)
    {
        this.Country_ID = Country_ID;
        this.Country = Country;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
    }

    //Null Constructor.
    public Countries()
    {

    }

    //Constructor for Countries based on IDs. Used for looking up corresponding Country IDs since it's a foreign key for FirstLevelDivisions.
    public Countries(int Country_ID)
    {
        this.Country_ID = Country_ID;
    }


    /**
     * Getter for the Country ID.
     *
     * @return Country_ID
     */
    public int getCountry_ID()
    {

        return Country_ID;
    }

    /**
     *Getter for the Country name.
     *
     * @return Country
     */
    public String getCountry()
    {

        return Country;
    }

    /**
     * Getter for the date the country was added.
     *
     * @return Create_Date
     */
    public Date getCreate_Date()
    {
        return Create_Date;
    }

    /**
     * Getter for the user who added the country.
     *
     * @return Created_By
     */
    public String getCreated_By()
    {
        return Created_By;
    }

    /**
     * Getter for the timeframe when the country was last updated.
     *
     * @return Last_Update
     */
    public Timestamp getLast_Update()
    {
        this.Last_Update = Timestamp.valueOf(LocalDateTime.of(Last_Update.toLocalDateTime().toLocalDate(), Last_Update.toLocalDateTime().toLocalTime()));
        return this.Last_Update;
    }

    /**
     * Getter for the user who last updated the country.
     *
     * @return Last_Updated_By
     */
    public String getLast_Updated_By()
    {
        return Last_Updated_By;
    }

    /**
     * Country name toString method for use with the Customer Country ComboBox.
     *
     * @return Country
     */
    @Override
    public String toString()
    {
        return (Country);
    }

}
