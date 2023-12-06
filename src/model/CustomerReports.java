package model;

/**
 * My scheduler reports Model class that handles the logic and attributes of my custom Customer by Country + Division report/TableView.
 *
 * @author Harrison Thacker
 */
public class CustomerReports {

    private int countryDivisionTotal; //The total count of countries for my custom Country report TableView.
    private String nameOfCountry; //The name of the Customer's country.
    private String nameOfDivision; //The name of the Customer's Division.


    /**
     * Constructor for the First-Level Divisions class.
     *
     * @param countryDivisionTotal = //The total count of countries for my custom Country report TableView.
     * @param nameOfCountry = //The name of the Customer's country.
     * @param nameOfDivision = //The name of the Customer's Division.
     */
    public CustomerReports(int countryDivisionTotal, String nameOfCountry, String nameOfDivision)
    {
        this.countryDivisionTotal = countryDivisionTotal;
        this.nameOfCountry = nameOfCountry;
        this.nameOfDivision = nameOfDivision;
    }

    /**
     * Getter for the total count of Countries + Divisions.
     *
     * @return countryTotal
     */
    public int getCountryDivisionTotal()
    {

        return countryDivisionTotal;
    }

    /**
     * Getter for the name of the given Country.
     *
     * @return nameOfCountry
     */
    public String getNameOfCountry()
    {

        return nameOfCountry;
    }

    /**
     * Getter for the name of the given Division.
     *
     * @return nameOfDivision
     */
    public String getNameOfDivision()
    {
        return nameOfDivision;
    }

}
