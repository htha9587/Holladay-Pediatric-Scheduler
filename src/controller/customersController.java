package controller;

import database.SchedulerDatabase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Callback;
import model.*;
import DAO.*;
import java.util.ResourceBundle;
import java.sql.*;
import java.net.URL;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.*;
import javafx.fxml.*;
import util.DivisionsList;

/**
 * FXML Controller class for the Customer page of the Holladay Pediatric Scheduler.
 *
 * @author Harrison Thacker
 */
public class customersController implements Initializable {

    @FXML
    private Button customersAddButton; //Button that adds new customers to the database/table. Works in conjunction with a lambda expression.

    @FXML
    private Button customersEditButton; //Button that populates the fields with the selected record, allowing you to edit its attributes. Works in conjunction with a lambda expression.

    @FXML
    private Button customersRemoveButton; //Button that deletes a selected customer, works in conjunction with a lambda expression.

    @FXML
    private Button customerNavigatorButton; //Button that redirects back to the navigator, works in conjunction with a lambda expression.

    @FXML
    private Button customerSaveRecordButton; //Button that saves new customer records to the database/table, works in conjunction with a lambda expression.

    @FXML
    private TableView<Customers> customersRecordTableView; //TableView for the Scheduler Customers.

    @FXML
    private TableColumn<?, ?> customerIDTableColumn; //Column for the Customer ID.

    @FXML
    private TableColumn<?, ?> customerNameTableColumn; //Column for the Customer Name.

    @FXML
    private TableColumn<?, ?> customerAddressTableColumn; //Column for the Customer Address.

    @FXML
    private TableColumn<?, ?> customerPostalCodeTableColumn; //Column for the Customer Postal Code.

    @FXML
    private TableColumn<?, ?> customerPhoneTableColumn; //Column for the Customer Phone number.

    @FXML
    private TableColumn<?, ?> customerProvinceTableColumn; //Column for the Customer Province/State.

    @FXML
    private TableColumn<?, ?> customerCountryTableColumn; //Column for the Customer Country.

    @FXML
    private ComboBox<Countries> customersCountryComboBox; //ComboBox for selecting a customer's home country.

    @FXML
    private ComboBox<FirstLevelDivisions> customersProvinceComboBox; //ComboBox for selecting a customer's province/state.

    @FXML
    private TextField customersIDField; //TextField for the customer ID.

    @FXML
    private TextField customersNameField; //TextField for the customer name.

    @FXML
    private TextField customersAddressField; //TextField for the customer address.

    @FXML
    private TextField customersPhoneField; //TextField for the customer phone number.

    @FXML
    private TextField customersPostalCodeField; //TextField for the customer postal code.

    @FXML
    private TextField customerSearchField; //TextField for looking up Customers by their ID or name.

    private Customers schedulerCustomers; //Instance of customers model object.

    private static CountriesDAO countriesDAO; //Instance of Countries DAO class.

    private static FirstLevelDivisionsDAO firstLevelDivisionsDAO; // Instance of Divisions DAO class.

    public int Country_ID; //Variable to be used for looking up Divisions that correspond to a selected Country.

    public int Division_ID; //Variable to be passed to the Division ID when a customer is added or updated.

    public int customerID; //Variable to be used for looking up given Customers.

    private final ObservableList<Countries> listOfCountries = FXCollections.observableArrayList(); //ObservableList for retrieving countries.

    private final ObservableList<FirstLevelDivisions> listOfDivisions = FXCollections.observableArrayList(); //ObservableList for retrieving divisions.


    /**
     * Initializes the controller class. On launch, all the Customer page attributes are set up.
     * All buttons make use of lambda expression functionality, as well as the Observable Lists.
     *
     * @param url = instance of URL object.
     * @param resourceBundle = instance of ResourceBundle object.
     *<p>
     * LAMBDA - setOnAction: These lambda expressions work in conjunction with the void executable methods.
     * When any of the page buttons or the country ComboBox are interacted with, the respective methods are called.
     * When called, the methods run the executable functionality for the features of the Customers page.
     * The buttons and ComboBox each have event handlers in the lambda expression statement which acts as an input.
     * This separation results in less code and ends up more readable and concise for others who may be working with the Scheduler program.
     */
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        try
        {
            //Connects to the scheduler database.
            Connection connection = SchedulerDatabase.DBOpenConnection();

            schedulerCustomers = new Customers();

            countriesDAO = new CountriesDAO();
            firstLevelDivisionsDAO = new FirstLevelDivisionsDAO();
            ObservableList<Countries> countries = countriesDAO.retrieveAll();
            ObservableList<FirstLevelDivisions> divisions = firstLevelDivisionsDAO.retrieveAll();
            listOfCountries.addAll(countries);
            listOfDivisions.addAll(divisions);

            //Has the Customers DAO retrieve the customer names for the ObservableList.
            ObservableList<Customers> schedulerCustomers = CustomersDAO.retrieveCustomers(connection);

            //Sets the table rows/columns for the Customer TableView.
            customerIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));
            customerNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_Name"));
            customerAddressTableColumn.setCellValueFactory(new PropertyValueFactory<>("Address"));
            customerPostalCodeTableColumn.setCellValueFactory(new PropertyValueFactory<>("Postal_Code"));
            customerPhoneTableColumn.setCellValueFactory(new PropertyValueFactory<>("Phone"));
            customerProvinceTableColumn.setCellValueFactory(new PropertyValueFactory<>("Division"));
            customerCountryTableColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));

            customersProvinceComboBox.setItems(listOfDivisions);
            //Sets list cell for the Province(Divisions) ComboBox.
            customersProvinceComboBox.setCellFactory(new Callback<ListView<FirstLevelDivisions>, ListCell<FirstLevelDivisions>>()
            {
                @Override
                public ListCell<FirstLevelDivisions> call(ListView<FirstLevelDivisions> firstLevelDivisionsListView)
                {
                    return new ListCell<>()
                    {
                        @Override
                        protected void updateItem(FirstLevelDivisions itemUpdate, boolean itemEmpty)
                        {
                            super.updateItem(itemUpdate, itemEmpty);

                            if(itemUpdate == null || itemEmpty)
                            {
                                setText(null);
                            }

                            else
                            {
                                setText(itemUpdate.getDivision());
                            }
                        }
                    };
                }
            });

            customersProvinceComboBox.setButtonCell(new ListCell<>(){
                @Override
                protected void updateItem(FirstLevelDivisions itemUpdate, boolean itemEmpty)
                {
                    super.updateItem(itemUpdate, itemEmpty);

                    if(itemUpdate != null)
                    {
                        setText(itemUpdate.getDivision());
                    }

                    else
                    {
                        setText(null);
                    }
                }

            });

            customersCountryComboBox.setItems(listOfCountries);

            //Sets the list cell for the Country ComboBox.
            customersCountryComboBox.setCellFactory(new Callback<ListView<Countries>, ListCell<Countries>>()
            {
                @Override
                public ListCell<Countries> call(ListView<Countries> countriesListView) {
                    return new ListCell<>()
                    {
                        @Override
                        protected void updateItem(Countries itemUpdate, boolean itemEmpty)
                        {
                            super.updateItem(itemUpdate, itemEmpty);

                            if(itemUpdate == null || itemEmpty)
                            {
                                setText(null);
                            }

                            else
                            {
                                setText(itemUpdate.getCountry());
                            }
                        }
                    };
                }
            });

            customersCountryComboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Countries itemUpdate, boolean itemEmpty)
                {
                    super.updateItem(itemUpdate, itemEmpty);

                    if(itemUpdate != null)
                    {
                        setText(itemUpdate.getCountry());
                    }

                    else
                    {
                        setText(null);
                    }
                }
            });

            //Populates the Division ComboBox(Province) with the Divisions that match the given Country ID depending
            // on the Country the user selected with the Country ComboBox.
            customersCountryComboBox.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try
                    {
                        Country_ID = customersCountryComboBox.getValue().getCountry_ID();
                        customersProvinceComboBox.setItems(DivisionsList.retrieveFLDFiltered(Country_ID));
                        customersProvinceComboBox.getSelectionModel().selectFirst();
                    }

                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            });

            customersRecordTableView.setItems(schedulerCustomers);

            //Lambda expression which calls the event method, adding a new customer to the scheduler records.
            customersAddButton.setOnAction(event ->
                    addCustomerClicked());

            //Lambda expression which calls the event method, allowing the user to make edits to the selected customer in the TableView.
            customersEditButton.setOnAction(event ->
                    editCustomerClicked());

            //Lambda expression which calls the event method, removing a customer from the scheduler records.
            customersRemoveButton.setOnAction(event ->
            {
                try
                {
                    removeCustomerClicked();
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            });

            //Lambda expression which calls the event method, saving the newly added customers to the scheduler records.
            customerSaveRecordButton.setOnAction(event ->
                    saveToRecordsClicked());

            //Lambda expression which calls the event method, redirecting the user back to the navigator page.
            customerNavigatorButton.setOnAction(event ->
                    returnToNavigatorClicked());
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    /**
     * The search field looks up the customer in the database by either the ID or name.
     * If successful, the Customer TableView will populate with the given results.
     *
     * @param actionEvent = instance of ActionEvent object.
     */
    @FXML
    public void retrieveSearchedCustomer(ActionEvent actionEvent) throws Exception
    {
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                //Connects to the scheduler database.
                Connection connection = SchedulerDatabase.DBOpenConnection();

                String customerSearch = customerSearchField.getText();
                if(customerSearch.isEmpty())
                {
                    Alert noResultAlert = new Alert(Alert.AlertType.ERROR);
                    noResultAlert.setTitle("Error!");
                    noResultAlert.setContentText("Please enter either the Customer ID or Name please!");
                    noResultAlert.showAndWait();
                }

                CustomersDAO customersDAO = new CustomersDAO();
                try
                {
                    customersRecordTableView.setItems(CustomersDAO.retrieveCustomers(connection));
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

                try
                {
                    int Customer_ID = Integer.parseInt(customerSearchField.getText());
                    Customers customers = customersDAO.retrieveCustomerID(Customer_ID);
                    customersRecordTableView.requestFocus();
                    customersRecordTableView.getSelectionModel().select(customers);
                    customersRecordTableView.scrollTo(customers);
                }

                catch(Exception exception)
                {
                    String customersName = customerSearchField.getText();

                    try
                    {
                        ObservableList<Customers> customersObservableList = customersDAO.retrieveCustomerName(customersName);
                        customersRecordTableView.setItems(customersObservableList);
                    }

                    catch (SQLException sqlException)
                    {
                        sqlException.printStackTrace();
                    }

                }

                if(!customersDAO.customerResult)
                {
                    Alert noResultAlert = new Alert(Alert.AlertType.ERROR);
                    noResultAlert.setTitle("Error!");
                    noResultAlert.setContentText("Your Customer search didn't return any results.");
                    noResultAlert.showAndWait();
                }

            }
        });

    }

    /**
     * Adds a new customer to the scheduler records, works in conjunction with the lambda expression.
     */
    @FXML
    public void addCustomerClicked()
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Customer?");
        alert.setHeaderText("Add New Customer?");
        alert.setContentText("Are you sure you wish to add this Customer to the records?");
        alert.showAndWait().ifPresent(response ->
        {
            if(response == ButtonType.OK)
            {
                try
                {
                    //Connects to the scheduler database.
                    Connection connection = SchedulerDatabase.DBOpenConnection();

                    //Adds new customer to the record so long as all fields aren't left empty by the user.
                    if (!customersNameField.getText().isEmpty() || !customersAddressField.getText().isEmpty() || !customersPhoneField.getText().isEmpty() || !customersPostalCodeField.getText().isEmpty() || !customersProvinceComboBox.getSelectionModel().isEmpty() || !customersCountryComboBox.getSelectionModel().isEmpty())
                    {
                        int Customer_ID = (int) (Math.random() * 100);

                        int nameOfDivision = 0;

                        //Iterates through the divisions, checks if the names match, and adds a new Division ID if that's the case.
                        for (FirstLevelDivisions firstLevelDivision : firstLevelDivisionsDAO.retrieveAll())
                        {
                            if (customersProvinceComboBox.getSelectionModel().getSelectedItem().toString().equals(firstLevelDivision.getDivision()))
                            {
                                nameOfDivision = firstLevelDivision.getDivision_ID();
                            }
                        }

                        //Retrieves the Customer parameters, which are the page attributes.
                        schedulerCustomers.setCustomer_ID(Customer_ID);
                        schedulerCustomers.setCustomer_Name(customersNameField.getText());
                        schedulerCustomers.setAddress(customersAddressField.getText());
                        schedulerCustomers.setPostal_Code(customersPostalCodeField.getText());
                        schedulerCustomers.setPhone(customersPhoneField.getText());
                        schedulerCustomers.setDivision_ID(customersProvinceComboBox.getSelectionModel().getSelectedItem().getDivision_ID());

                        CustomersDAO.AddCustomer(schedulerCustomers);

                        customersIDField.clear();
                        customersNameField.clear();
                        customersAddressField.clear();
                        customersPostalCodeField.clear();
                        customersPhoneField.clear();

                        //Sets and refreshes the customer TableView.
                        ObservableList<Customers> listOfCustomers = CustomersDAO.retrieveCustomers(connection);
                        customersRecordTableView.setItems(listOfCustomers);
                    }

                    else
                    {
                        Alert addCustomerEmpty = new Alert(Alert.AlertType.ERROR);
                        addCustomerEmpty.setTitle("Error!");
                        addCustomerEmpty.setContentText("Please fill out all the customer fields and drop down boxes before you add a new customer.");
                        addCustomerEmpty.showAndWait();
                    }

                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });

    }

    /**
     * Allows the user to make edits to an already existing customer, works in conjunction with the lambda expression.
     */
    @FXML
    public void editCustomerClicked()
    {

        try
        {
            SchedulerDatabase.DBOpenConnection();

            Customers customers = customersRecordTableView.getSelectionModel().getSelectedItem();

            //Sets an alert dialog if no customer was selected and the Edit button was clicked.
            if(customers == null)
            {
                Alert editCustomerNoSelected = new Alert(Alert.AlertType.ERROR);
                editCustomerNoSelected.setTitle("Error!");
                editCustomerNoSelected.setContentText("You must first select a Customer in the TableView that you wish to make edits to.");
                editCustomerNoSelected.showAndWait();
            }

            //Sets the page attributes for the given customer to be edited, if one was selected.
            else
            {

                customersIDField.setText(String.valueOf((customers.getCustomer_ID())));
                customersNameField.setText(String.valueOf((customers.getCustomer_Name())));
                customersAddressField.setText(String.valueOf((customers.getAddress())));
                customersPostalCodeField.setText(String.valueOf((customers.getPostal_Code())));
                customersPhoneField.setText(String.valueOf((customers.getPhone())));
                Customers customers1 = new Customers();
                Countries countries1 = new Countries();

                int Country_ID = customersRecordTableView.getSelectionModel().getSelectedItem().getCountry_ID();
                int Division_ID = customersRecordTableView.getSelectionModel().getSelectedItem().getDivision_ID();

                Countries countries = countriesDAO.lookupCountryAndDivision(Country_ID);

                customersCountryComboBox.setValue(countries);

                FirstLevelDivisions divisions = firstLevelDivisionsDAO.retrieveSpecific(Division_ID);
                //Iterates through to have the Province ComboBox select the Division that matches the Customer that was selected in the TableView.
                for(FirstLevelDivisions firstLevelDivisions: firstLevelDivisionsDAO.retrieveAll())
                {
                    if(firstLevelDivisions.getDivision_ID() == customers.getDivision_ID())
                    {
                        customersProvinceComboBox.getSelectionModel().select(divisions);
                        break;
                    }
                }
            }

        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }


    /**
     * Allows the user to remove a customer from the scheduler records, works in conjunction with the lambda expression.
     *
     * @throws Exception = thrown in the event of an unforeseen error.
     */
    @FXML
    public void removeCustomerClicked() throws Exception
    {
        //Connects to the scheduler database.
        Connection connection = SchedulerDatabase.DBOpenConnection();

        ObservableList<Appointments> retrieveCustomerAppointments = AppointmentsDAO.retrieveAppointments(connection);

        Customers customers = customersRecordTableView.getSelectionModel().getSelectedItem();

        //Sets an alert dialog if no customer was selected and the Remove button was clicked.
        if(customers == null)
        {
            Alert removeCustomerNoSelected = new Alert(Alert.AlertType.ERROR);
            removeCustomerNoSelected.setTitle("Error!");
            removeCustomerNoSelected.setContentText("You must first select a Customer in the TableView that you wish to remove from the records.");
            removeCustomerNoSelected.showAndWait();
        }

        else
        {

            //Alert dialog that removes the customer and its associated appointments if agreed to.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Customer?");
            alert.setHeaderText("Remove Customer From Scheduler?");
            alert.setContentText("This will remove Customer: " + customersRecordTableView.getSelectionModel().getSelectedItem().getCustomer_ID() + " and all appointments associated with him/her. Do you wish to proceed?");
            alert.showAndWait().ifPresent(response ->
            {
                int retrieveCustomerForDeletion = customersRecordTableView.getSelectionModel().getSelectedItem().getCustomer_ID();

                try
                {
                    CustomersDAO.DropCustomer(customersRecordTableView.getSelectionModel().getSelectedItem().getCustomer_ID(), connection);
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

                //Iterates through and removes all appointments the customer had in the records/scheduler database.
                for (Appointments customerAppointments: retrieveCustomerAppointments)
                {

                    int retrieveAppointmentCustomerID = customerAppointments.getCustomer_ID();

                    if (retrieveCustomerForDeletion == retrieveAppointmentCustomerID)
                    {
                        //SQL DELETE statement for removing the Customer's respective appointments.
                        String removeCustomersAppointmentsStatement = "DELETE FROM appointments WHERE Appointment_ID = ?";

                        try
                        {
                            SchedulerDatabase.setPreparedStatement(SchedulerDatabase.getSchedulerConnection(), removeCustomersAppointmentsStatement);
                        }

                        catch (SQLException sqlException)
                        {
                            sqlException.printStackTrace();
                        }

                    }
                }

                //Resets the Customer ObservableList after deletion of a customer.
                ObservableList<Customers> customerListDeletionReset = null;

                try
                {
                    customerListDeletionReset = CustomersDAO.retrieveCustomers(connection);
                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

                //Confirmation alert if a Customer record is removed successfully.
                Alert successfulDeletion = new Alert(Alert.AlertType.CONFIRMATION);
                successfulDeletion.setTitle("Successful Deletion!");
                successfulDeletion.setContentText("Customer: " + customersRecordTableView.getSelectionModel().getSelectedItem().getCustomer_ID() + " has been removed from the records along with the associated appointments.");
                successfulDeletion.showAndWait();

                customersIDField.clear();
                customersNameField.clear();
                customersAddressField.clear();
                customersPostalCodeField.clear();
                customersPhoneField.clear();

                //Resets the Customer TableView after deletion of a customer.
                customersRecordTableView.setItems(customerListDeletionReset);

            });
        }

    }


    /**
     * Allows the user to save edits made to an existing customer, works in conjunction with the lambda expression.
     */
    @FXML
    public void saveToRecordsClicked()
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save To Records?");
        alert.setHeaderText("Save Edits To Customer?");
        alert.setContentText("This will save edits to the selected customer. Do you wish to proceed?");
        alert.showAndWait().ifPresent(response ->
        {
            if (response == ButtonType.OK)
            {

                try {
                    //Connects to the scheduler database.
                    Connection connection = SchedulerDatabase.DBOpenConnection();

                    //Saves edits made to the customer record so long as all fields aren't left empty by the user.
                    if (!customersNameField.getText().isEmpty() || !customersAddressField.getText().isEmpty() || !customersPhoneField.getText().isEmpty() || !customersPostalCodeField.getText().isEmpty() || !customersProvinceComboBox.getSelectionModel().isEmpty() || !customersCountryComboBox.getSelectionModel().isEmpty())
                    {

                        int retrieveDivisionIDUpdate = 0;

                        //Iterates through the divisions, checks if the names match the ComboBox selection, and adds a new Division ID if that's the case.
                        for (FirstLevelDivisions firstLevelDivision : firstLevelDivisionsDAO.retrieveAll())
                        {
                            if ((customersProvinceComboBox.getSelectionModel().getSelectedItem().toString().equals(firstLevelDivision.getDivision())))
                            {
                                retrieveDivisionIDUpdate = firstLevelDivision.getDivision_ID();
                            }
                        }

                        //Has instance of Customers model object use the setters to get the Customer parameters, which are the page attributes.
                        schedulerCustomers.setCustomer_ID(Integer.parseInt(customersIDField.getText()));
                        schedulerCustomers.setCustomer_Name(customersNameField.getText());
                        schedulerCustomers.setAddress(customersAddressField.getText());
                        schedulerCustomers.setPostal_Code(customersPostalCodeField.getText());
                        schedulerCustomers.setPhone(customersPhoneField.getText());
                        schedulerCustomers.setDivision_ID(customersProvinceComboBox.getSelectionModel().getSelectedItem().getDivision_ID());

                        CustomersDAO.UpdateCustomer(schedulerCustomers);

                        customersIDField.clear();
                        customersNameField.clear();
                        customersAddressField.clear();
                        customersPhoneField.clear();
                        customersPostalCodeField.clear();

                        //Sets and refreshes the customer TableView.
                        ObservableList<Customers> resetCustomerListSavedEdit = CustomersDAO.retrieveCustomers(connection);
                        customersRecordTableView.setItems(resetCustomerListSavedEdit);

                    }

                    else
                    {
                        Alert updateCustomerEmpty = new Alert(Alert.AlertType.ERROR);
                        updateCustomerEmpty.setTitle("Error!");
                        updateCustomerEmpty.setContentText("Please fill out all the customer fields and drop down boxes before you save your edits.");
                        updateCustomerEmpty.showAndWait();
                    }

                }

                catch (Exception exception)
                {
                    exception.printStackTrace();
                }

            }
        });

    }


    /**
     * Redirects the user back to the Navigator, works in conjunction with the lambda expression.
     */
    @FXML
    public void returnToNavigatorClicked()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit Customer Screen?");
        alert.setContentText("Are you sure you wish to exit the Customer Screen?");
        alert.showAndWait().ifPresent(response ->
        {

            if(response == ButtonType.OK)
            {
                final Stage stage = (Stage) customerNavigatorButton.getScene().getWindow();
                stage.close();
            }
        });
    }

}
