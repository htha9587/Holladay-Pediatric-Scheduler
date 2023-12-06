package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Customers Model class that handles the logic and attributes of a given Customer in the scheduler.
 *
 * @author Harrison Thacker
 */
public class Customers {

    private int Customer_ID; //The ID of the given customer.
    private String Customer_Name; //The name of the given customer.
    private String Address; //The home address of the given customer.
    private String Postal_Code; //The postal code of the given customer.
    private String Phone; //The phone number of the given customer.
    private Date Create_Date; //The date the Customer was added as part of the database.
    private String Created_By; //The name of the user who created/added the Customer.
    private Timestamp Last_Update; //The timestamp when the Customer was last updated.
    private String Last_Updated_By; //The name of the user who last updated the Customer.
    private int Country_ID; //The ID of the Customer's given Country.
    private int Division_ID; //The ID of the given corresponding First-Level Division.
    private String Country; // The name of the given corresponding Country.
    private String Division; //The name of the given corresponding First-Level Division.


    /** Constructor for the Customers class.
     *
     * @param Customer_ID = The ID of the given customer.
     * @param Customer_Name = The name of the given customer.
     * @param Address = The home address of the given customer.
     * @param Postal_Code = The postal code of the given customer.
     * @param Phone = The phone number of the given customer.
     * @param Country_ID = The ID of the Customer's given Country.
     * @param Division_ID = The ID of the given corresponding First-Level Division.
     * @param Country =  The name of the given corresponding Country.
     * @param Division = The name of the given corresponding First-Level Division.
     */
    public Customers(int Customer_ID, String Customer_Name, String Address, String Postal_Code,
                     String Phone, int Country_ID, int Division_ID, String Country, String Division)
    {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Country_ID = Country_ID;
        this.Division_ID = Division_ID;
        this.Country = Country;
        this.Division = Division;
    }

    /**
     * Constructor for the instance object used to add/edit new customers.
     */
    public Customers()
    {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Country_ID = Country_ID;
        this.Division_ID = Division_ID;
        this.Country = Country;
        this.Division = Division;
    }

    /**
     * Constructor used for the Appointments page to lookup a specific Customer.
     */
    public Customers(int country_id, String country, java.sql.Date create_date, String created_by, Timestamp last_update, String last_updated_by)
    {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Country_ID = Country_ID;
        this.Division_ID = Division_ID;
        this.Country = Country;
        this.Division = Division;
    }

    /**
     * Getter for the Customer ID.
     *
     * @return Customer_ID
     */
    public int getCustomer_ID()
    {
        return Customer_ID;
    }

    /**
     * Setter for the Customer ID.
     */
    public void setCustomer_ID(int Customer_ID)
    {
        this.Customer_ID = Customer_ID;
    }

    /**
     * Getter for the Customer name.
     *
     * @return Customer_Name
     */
    public String getCustomer_Name()
    {
        return Customer_Name;
    }

    /**
     * Setter for the Customer name.
     */
    public void setCustomer_Name(String Customer_Name)
    {
        this.Customer_Name = Customer_Name;
    }

    /**
     * Getter for the Customer address.
     *
     * @return Address
     */
    public String getAddress()
    {
        return Address;
    }

    /**
     * Setter for the Customer address.
     */
    public void setAddress(String Address)
    {
        this.Address = Address;
    }

    /**
     * Getter for the Customer postal code.
     *
     * @return Postal_Code
     */
    public String getPostal_Code()
    {
        return Postal_Code;
    }

    /**
     * Setter for the Customer postal code..
     */
    public void setPostal_Code(String Postal_Code)
    {
        this.Postal_Code = Postal_Code;
    }

    /**
     * Getter for the Customer phone number..
     *
     * @return Phone
     */
    public String getPhone()
    {
        return Phone;
    }

    /**
     * Setter for the Customer phone number.
     */
    public void setPhone(String Phone)
    {
        this.Phone = Phone;
    }

    /**
     * Getter for the date the Customer was added.
     *
     * @return Create_Date
     */
    public Date getCreate_Date()
    {
        return Create_Date;
    }

    /**
     * Setter for the date the Customer was added.
     */
    public void setCreate_Date(Date Create_Date)
    {
        this.Create_Date = Create_Date;
    }

    /**
     * Getter for the individual who added the Customer.
     *
     * @return Created_By
     */
    public String getCreated_By()
    {
        return Created_By;
    }

    /**
     * Setter for the individual who added the Customer.
     */
    public void setCreated_By(String Created_By)
    {
        this.Created_By = Created_By;
    }

    /**
     * Getter for the date the Customer was last updated.
     *
     * @return Last_Update
     */
    public Timestamp getLast_Update()
    {
        this.Last_Update = Timestamp.valueOf(LocalDateTime.of(Last_Update.toLocalDateTime().toLocalDate(), Last_Update.toLocalDateTime().toLocalTime()));
        return this.Last_Update;
    }

    /**
     * Setter for the date the Customer was last updated.
     */
    public void setLast_Update(Timestamp Last_Update)
    {
        this.Last_Update = Last_Update;
    }

    /**
     * Getter for the user who last updated the Customer.
     *
     * @return Last_Updated_By
     */
    public String getLast_Updated_By()
    {
        return Last_Updated_By;
    }

    /**
     * Setter for the user who last updated the Customer.
     */
    public void setLast_Updated_By(String Last_Updated_By)
    {
        this.Last_Updated_By = Last_Updated_By;
    }

    /**
     * Getter for the corresponding Division ID.
     *
     * @return Division_ID
     */
    public int getDivision_ID()
    {
        return Division_ID;
    }

    /**
     * Setter for the corresponding Division ID.
     */
    public void setDivision_ID(int Division_ID)
    {
        this.Division_ID = Division_ID;
    }

    /**
     * Getter for the corresponding Division name.
     *
     * @return Division
     */
    public String getDivision()
    {
        return Division;
    }

    /**
     * Setter for the corresponding Division name.
     */
    public void setDivision(String Division)
    {
        this.Division = Division;
    }

    /**
     * Getter for the corresponding Country ID.
     *
     * @return Country_ID
     */
    public int getCountry_ID()
    {
        return Country_ID;
    }

    /**
     * Setter for the corresponding Country ID.
     */
    public void setCountry_ID(int Country_ID)
    {
        this.Country_ID = Country_ID;
    }

    /**
     * Getter for the corresponding Country name.
     *
     * @return Country
     */
    public String getCountry()
    {
        return Country;
    }

    /**
     * Setter for the corresponding Country name.
     */
    public void setCountry(String Country)
    {
        this.Country = Country;
    }

    /**
     * toString method for the name of the Customer.
     * @return Customer_Name
     */
    @Override
    public String toString()
    {
        return (Customer_Name);
    }

}
