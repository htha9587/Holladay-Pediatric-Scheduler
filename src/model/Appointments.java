package model;

import java.time.LocalDateTime;

/**
 * Appointments Model class that handles the logic and attributes of a given scheduled Appointment in the scheduler.
 *
 * @author Harrison Thacker
 */
public class Appointments {

    private int Appointment_ID; //The ID of the given appointment.
    private String Title; //The name of the given appointment.
    private String Description;//The description of the given appointment.
    private String Location; //The location the appointment takes place.
    private String Type; //The type of the given appointment.
    private LocalDateTime Start; //The starting time of the given appointment.
    private LocalDateTime End; //The ending time of the given appointment.
    private LocalDateTime Create_Date; //The date the appointment was added as part of the database.
    private String Created_By; //The name of the user who created/added the appointment.
    private LocalDateTime Last_Update; //The timestamp when the appointment was last updated.
    private String Last_Updated_By; //The name of the user who last updated the appointment.
    private int Customer_ID; //The ID of the corresponding Customer.
    private int User_ID; //The ID of the corresponding User.
    private int Contact_ID; //The ID of the given Contact that will be met with at the appointment.
    private String Contact; //The name of the given Contact at the appointment.

    /** Constructor for the Appointments class.
     *
     * @param Appointment_ID = The ID of the given appointment.
     * @param Title = The name of the given appointment.
     * @param Description = The description of the given appointment.
     * @param Location = The location the appointment takes place.
     * @param Contact = The name of the given Contact at the appointment.
     * @param Type = The type of the given appointment.
     * @param Start = The starting time of the given appointment.
     * @param End = The ending time of the given appointment.
     * @param Customer_ID = The ID of the corresponding Customer.
     * @param User_ID = The ID of the corresponding User.
     * @param Contact_ID = The ID of the corresponding Contact.
     */
    public Appointments(int Appointment_ID, String Title, String Description, String Location, String Contact, String Type, LocalDateTime Start, LocalDateTime End,
                         int Customer_ID, int User_ID, int Contact_ID)
    {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Contact = Contact;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }

    /**
     * Constructor used for instance of object. It facilitates adding and editing Appointments.
     */
    public Appointments()
    {
        this.Appointment_ID = Appointment_ID;
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.Contact = Contact;
        this.Type = Type;
        this.Start = Start;
        this.End = End;
        this.Customer_ID = Customer_ID;
        this.User_ID = User_ID;
        this.Contact_ID = Contact_ID;
    }

    /**
     * Getter for the Appointment ID.
     *
     * @return Appointment_ID
     */
    public int getAppointment_ID()
    {
        return Appointment_ID;
    }

    /**
     * Setter for the Appointment ID.
     */
    public void setAppointment_ID(int Appointment_ID)
    {
        this.Appointment_ID = Appointment_ID;
    }

    /**
     * Getter for the Appointment name.
     *
     * @return Title
     */
    public String getTitle()
    {
        return Title;
    }

    /**
     * Setter for the Appointment name.
     */
    public void setTitle(String Title)
    {
        this.Title = Title;
    }

    /**
     * Getter for the Appointment description.
     *
     * @return Description
     */
    public String getDescription()
    {
        return Description;
    }

    /**
     * Setter for the Appointment description.
     */
    public void setDescription(String Description)
    {
        this.Description = Description;
    }

    /**
     * Getter for the Appointment location.
     *
     * @return Location
     */
    public String getLocation()
    {
        return Location;
    }

    /**
     * Setter for the Appointment location.
     */
    public void setLocation(String Location)
    {
        this.Location = Location;
    }

    /**
     * Getter for the Appointment contact.
     *
     * @return Contact
     */
    public String getContact()
    {
        return Contact;
    }

    /**
     * Setter for the Appointment contact.
     */
    public void setContact(String Contact)
    {
        this.Contact = Contact;
    }

    /**
     * Getter for the Appointment type.
     *
     * @return Type
     */
    public String getType()
    {
        return Type;
    }

    /**
     * Setter for the Appointment type.
     */
    public void setType(String Type)
    {
        this.Type = Type;
    }

    /**
     * Getter for the Appointment start time.
     *
     * @return Start
     */
    public LocalDateTime getStart()
    {
        return Start;
    }

    /**
     * Setter for the Appointment start time.
     */
    public void setStart(LocalDateTime Start)
    {
        this.Start = Start;
    }

    /**
     * Getter for the Appointment end time.
     *
     * @return End
     */
    public LocalDateTime getEnd()
    {
        return End;
    }

    /**
     * Setter for the Appointment end time.
     */
    public void setEnd(LocalDateTime End)
    {
        this.End = End;
    }

    /**
     * Getter for the date the Appointment was added.
     *
     * @return Create_Date
     */
    public LocalDateTime getCreate_Date()
    {
        return Create_Date;
    }

    /**
     * Setter for the date the Appointment was added.
     */
    public void setCreate_Date(LocalDateTime Create_Date)
    {
        this.Create_Date = Create_Date;
    }

    /**
     * Getter for the user who added the Appointment.
     *
     * @return Created_By
     */
    public String getCreated_By()
    {
        return Created_By;
    }

    /**
     * Setter for the user who added the Appointment.
     */
    public void setCreated_By(String Created_By)
    {
        this.Created_By = Created_By;
    }

    /**
     * Getter for the timeframe when the Appointment was last updated.
     *
     * @return Last_Update
     */
    public LocalDateTime getLast_Update()
    {
        return this.Last_Update;
    }

    /**
     * Setter for the timeframe when the Appointment was last updated.
     */
    public void setLast_Update(LocalDateTime Last_Update)
    {
        this.Last_Update = Last_Update;
    }

    /**
     * Getter for the user who last updated the Appointment.
     *
     * @return Last_Updated_By
     */
    public String getLast_Updated_By()
    {
        return Last_Updated_By;
    }

    /**
     * Setter for the user who last updated the Appointment.
     */
    public void setLast_Updated_By(String Last_Updated_By)
    {
        this.Last_Updated_By = Last_Updated_By;
    }

    /**
     * Getter for the corresponding Customer ID.
     *
     * @return Customer_ID
     */
    public int getCustomer_ID()
    {
        return Customer_ID;
    }

    /**
     * Setter for the corresponding Customer ID.
     */
    public void setCustomer_ID(int Customer_ID)
    {
        this.Customer_ID = Customer_ID;
    }

    /**
     * Getter for the corresponding User ID.
     *
     * @return User_ID
     */
    public int getUser_ID()
    {
        return User_ID;
    }

    /**
     * Setter for the corresponding User ID.
     */
    public void setUser_ID(int User_ID)
    {
        this.User_ID = User_ID;
    }

    /**
     * Getter for the corresponding Appointment Contact ID.
     *
     * @return Contact_ID
     */
    public int getContact_ID()
    {
        return Contact_ID;
    }

    /**
     * Getter for the corresponding Appointment Contact ID.
     */
    public void setContact_ID(int Contact_ID)
    {
        this.Contact_ID = Contact_ID;
    }

}

