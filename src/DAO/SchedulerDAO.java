package DAO;

import javafx.collections.ObservableList;
import java.sql.SQLException;

/**
 * Interface for the Database DAO. This is in service of FirstLevelDivisions with its Customer ComboBox(Province),
 * and the corresponding Country_ID (Countries).
 */
public interface SchedulerDAO<T>
{
    ObservableList<T> retrieveAll() throws SQLException, Exception;

}
