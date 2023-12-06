package model;

/**
 * Contacts Model class that handles the logic and attributes of the contact a customer meets with at a scheduled appointment.
 *
 * @author Harrison Thacker
 */
public class Contacts {

    private int Contact_ID; //The ID of the given Contact.
    private String Contact_Name; //The name of the given Contact.
    private String Email; //The Email of the given Contact.

    /**
     * Constructor for the Contacts class.
     *
     * @param Contact_ID = //The ID of the given Contact.
     * @param Contact_Name = //The name of the given Contact.
     * @param Email = //The Email of the given Contact.
     */
    public Contacts(int Contact_ID, String Contact_Name, String Email)
    {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;
    }

    /**
     * Constructor used to obtain a Contact that matches a given ID.(Appointment page)
     */
    public Contacts()
    {
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;
    }

    /**
     * Getter for the Contact ID.
     *
     * @return Contact_ID
     */
    public int getContact_ID()
    {
        return Contact_ID;
    }

    /**
     * Getter for the Contact name.
     *
     * @return Contact_Name
     */
    public String getContact_Name()
    {
        return Contact_Name;
    }

    /**
     * Getter for the Contact email.
     *
     * @return Email
     */
    public String getEmail()
    {
        return Email;
    }

    /**
     * toString method for the name of the Appointment Contact.
     * @return Contact_Name
     */
    @Override
    public String toString()
    {
        return (Contact_Name);
    }

}
