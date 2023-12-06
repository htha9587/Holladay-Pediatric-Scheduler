package model;

/**
 * Appointment Type Reports Model class that handles the logic and attributes for the report(s) of a given appointment.
 *
 * @author Harrison Thacker
 */
public class TypeOfAppointment {

    private String typeOfAppointment; //The type of appointment that took place.
    private int typeOfAppointmentTVTotal; //The total count of appointments for the appointment type report TableView.

    /**
     * Constructor for the Appointment Type Model class.
     * @param typeOfAppointment = The type of appointment that took place.
     * @param typeOfAppointmentTVTotal = The total count of appointments for the appointment type report TableView.
     */
    public TypeOfAppointment(String typeOfAppointment, int typeOfAppointmentTVTotal)
    {
        this.typeOfAppointment = typeOfAppointment;
        this.typeOfAppointmentTVTotal = typeOfAppointmentTVTotal;
    }


    /**
     * Getter for the type of appointment that took place.
     *
     * @return typeOfAppointment
     */
    public String getTypeOfAppointment()
    {
        return typeOfAppointment;
    }


    /**
     * Getter for the total count of appointments for the appointment type report TableView.
     *
     * @return typeOfAppointmentTVTotal
     */
    public int getTypeOfAppointmentTVTotal()
    {
        return typeOfAppointmentTVTotal;
    }

}
