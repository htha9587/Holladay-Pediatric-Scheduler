package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * First-Level Divisions Model class that handles the logic and attributes of a given country's principal administrative division.
 *
 * @author Harrison Thacker
 */
public class FirstLevelDivisions {

    private int Division_ID; //The ID of the Country's First-Level Division.
    private String Division; //The name of the Country's First-Level Division.
    private Date Create_Date; //The date the First-Level Division was added as part of the database.
    private String Created_By; //The name of the user who created/added the First-Level Division.
    private Timestamp Last_Update; //The timestamp when the First-Level Division was last updated.
    private String Last_Updated_By; //The name of the user who last updated the First-Level Division.
    private String Country; //The name of the First-Level Division's respective Country.
    private int Country_ID; //The ID of the Division's Country(Foreign Key).


    /**
     * Constructor for the First-Level Divisions class.
     *
     * @param Division_ID = The ID of the Country's First-Level Division.
     * @param Division = The name of the Country's First-Level Division.
     * @param Create_Date = The date the First-Level Division was added as part of the database.
     * @param Created_By = The name of the user who created/added the First-Level Division.
     * @param Last_Update = The timestamp when the First-Level Division was last updated.
     * @param Last_Updated_By = The name of the user who last updated the First-Level Division.
     * @param Country = The name of the First-Level Division's respective Country.
     * @param Country_ID = The ID of the Division's Country(Foreign Key).
     */
    public FirstLevelDivisions(int Division_ID, String Division, Date Create_Date, String Created_By, Timestamp Last_Update, String Last_Updated_By, String Country, int Country_ID)
    {
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Country_ID = Country_ID;
        this.Division_ID = 0;
        this.Country = Country;
        this.Country_ID = 0;
    }

    //Null Constructor.
    public FirstLevelDivisions()
    {

    }

    //Constructor for Divisions based on IDs. Used for looking up corresponding Division IDs.
    public FirstLevelDivisions(int Division_ID)
    {
        this.Division_ID = Division_ID;
    }

    //Constructor for looking up Divisions based on their Country_ID.
    public FirstLevelDivisions(int Division_ID, int Country_iD, String Division, String Country)
    {
        this.Division_ID = Division_ID;
        this.Country_ID = Country_iD;
        this.Division = Division;
        this.Country = Country;
    }

    //Constructor for loading a Division when a user clicks a Customer to edit.
    public FirstLevelDivisions(int Division_ID, String Division, int Country_ID, String Country)
    {
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Country_ID = Country_ID;
        this.Country = Country;
    }

    /**
     * Getter for the Division ID.
     *
     * @return Division_ID
     */
    public int getDivision_ID()
    {

        return Division_ID;
    }

    /**
     * Getter for the Division name.
     *
     * @return Division
     */
    public String getDivision()
    {

        return Division;
    }

    /**
     * Setter for the Division name.
     */
    public void setDivision(String Division)
    {
        this.Division = Division;
    }

    /**
     * Getter for the date the division was added.
     *
     * @return Create_Date
     */
    public Date getCreate_Date()
    {
        return Create_Date;
    }

    /**
     * Getter for the user who added the division.
     *
     * @return Created_By
     */
    public String getCreated_By()
    {
        return Created_By;
    }

    /**
     * Getter for the timeframe when the division was last updated.
     *
     * @return Last_Update
     */
    public Timestamp getLast_Update()
    {
        this.Last_Update = Timestamp.valueOf(LocalDateTime.of(Last_Update.toLocalDateTime().toLocalDate(), Last_Update.toLocalDateTime().toLocalTime()));
        return this.Last_Update;
    }

    /**
     * Getter for the user who last updated the division.
     *
     * @return Last_Updated_By
     */
    public String getLast_Updated_By()
    {
        return Last_Updated_By;
    }

    /**
     * Getter for the name of the First-Level Division's respective Country.
     *
     * @return Country
     */
    public String getCountry()
    {
        return Country;
    }

    /**
     * Setter for the name of the First-Level Division's respective Country.
     */
    public void setCountry(String Country)
    {
        this.Country = Country;
    }

    /**
     * toString method for the name of the First-Level Division.
     * @return Division
     */
    @Override
    public String toString()
    {
        return (Division);
    }

    /**
     *Getter for the ID of the Division's Country(Foreign Key).
     *
     * @return Country_ID
     */
    public int getCountry_ID()
    {
        return Country_ID;
    }

}
