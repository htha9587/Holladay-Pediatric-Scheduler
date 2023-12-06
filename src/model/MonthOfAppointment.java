package model;

/**
 * Appointment Month Reports Model class that handles the logic and attributes for the report(s) of a given appointment.
 *
 * @author Harrison Thacker
 */
public class MonthOfAppointment {

    private String monthOfAppointment; //The month the given appointment took place.
    private int appointmentMonthTotal; //The total count of appointments for the appointment month report TableView.


    /**
     * Constructor for the Appointment Month Model class.
     * @param monthOfAppointment = The month the given appointment took place.
     * @param appointmentMonthTotal = The total count of appointments for the appointment month report TableView.
     */
    public MonthOfAppointment(String monthOfAppointment, int appointmentMonthTotal)
    {
        this.monthOfAppointment = monthOfAppointment;
        this.appointmentMonthTotal = appointmentMonthTotal;
    }


    /**
     * Getter for the given month an appointment took place.
     *
     * @return monthOfAppointment
     */
    public String getMonthOfAppointment()
    {
        return monthOfAppointment;
    }


    /**
     * Getter for the total count of appointments for the appointment month report TableView.
     *
     * @return monthOfAppointmentTVTotal
     */
    public int getAppointmentMonthTotal()
    {
        return appointmentMonthTotal;
    }

    /**
     * Setter for the given month an appointment took place.
     *
     * @param monthOfAppointment = Month of given Appointment.
     */
    public void setMonthOfAppointment(String monthOfAppointment)
    {
        this.monthOfAppointment = monthOfAppointment;
    }

    /**
     * Setter for the total count of appointments for the appointment month report TableView.
     *
     * @param appointmentMonthTotal = Total count of given Appointments.
     */
    public void setAppointmentMonthTotal(int appointmentMonthTotal)
    {
        this.appointmentMonthTotal = appointmentMonthTotal;
    }

}
