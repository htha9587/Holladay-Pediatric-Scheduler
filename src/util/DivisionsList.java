package util;

import DAO.FirstLevelDivisionsDAO;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

/**
 * Util class that handles the Divisions list, and how the Customer Divisions ComboBox changes based on the Country selected.
 *
 * @author Harrison Thacker
 */
public class DivisionsList
{
    /**
     * Calls the DAO SQL statement that retrieves the list of Divisions associated with the Country(Country_ID)
     * the user selected with the ComboBox.
     */
    public static ObservableList<FirstLevelDivisions> retrieveFLDFiltered(int Country_ID) throws Exception
    {
        FirstLevelDivisionsDAO firstLevelDivisionsDAO = new FirstLevelDivisionsDAO();

        return firstLevelDivisionsDAO.retrieveDivisionByCountryName(Country_ID);
    }

}
