package com.CrmScheduler.controller;

import com.CrmScheduler.DAO.IAppointmentDao;
import com.CrmScheduler.DAO.ICustomerDao;
import com.CrmScheduler.SpringConf;
import com.CrmScheduler.entity.CrmUser;
import com.CrmScheduler.entity.DetailedCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import com.CrmScheduler.entity.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The com.CrmScheduler.controller class for ManagementForm
 */
public class CustomerManagerController {


    /**
     *  Button for creating new customers. Opens the CreateEditCustomerForm for creating a blank customer.
     */
    public Button buttonNewCustomer;
    public AnchorPane anchorManagementForm;

    /**
     * used to track the loggedInUser throughout the application.
     */
    private CrmUser loggedInUser = null;

    /**
     * getter for loggedInUser
     * @return the logged in user from the original login screen
     */
    public CrmUser getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * setter for loggedInUser
     * @param loggedInUser the user that logged into the application
     */
    public void setLoggedInUser(CrmUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    /**
     * Tablecolumn responsible for keeping track of customer Id. Autogenerated.
     */
    public TableColumn columnCustomerId;
    /**
     * Tablecolumn responsible for keeping track of the customer's name.
     */
    public TableColumn columnName;
    /**
     * Tablecolumn responsible for keeping track of the customer's address.
     */
    public TableColumn columnAddress;
    /**
     * Tablecolumn responsible for keeping track of the customer's postal code.
     */
    public TableColumn columnPostalCode;
    /**
     * Tablecolumn responsible for keeping track of the customer's phone number.
     */
    public TableColumn columnPhone;
    /**
     * Tablecolumne responsible for keeping track of the customer's createdate. The value is of a timestamp
     */
    public TableColumn columnCreated;
    /**
     * Tablecolumn responsible for keeping track of the creator of the customer. Automatically set to the logged in user's username.
     */
    public TableColumn columnCreatedBy;
    /**
     * Tablecolumn responsible for keeping track of the
     */
    public TableColumn columnLastUpdated;
    /**
     * Tablecolumn responsible for keeping track of the last user to edit the customer. this value is updated behind the scenes when a customer is edited, or set to the creating user on creation.
     */
    public TableColumn columnLastUpdatedBy;
    /**
     * Tablecolumn responsible for keeping track of the customer's division ID
     */
    public TableColumn columnDivisionId;
    /**
     * Tablecolumn responsible for keeping the division name. This column is a bonus added to make the life easier of the user.
     */
    public TableColumn columnDivision;
    /**
     * TableView that holds all of the columns. Its data will be set by a list of DetailedCustomers
     */
    public TableView tableCustomers;
    /**
     * The label used to display the impending appointment to the user.
     */
    public Label labelImpendingAppointment;
    /**
     * all customers that will will listed in the table. DetailedCustomer is an extended version of Customer that allows for the division to be stored. In this use case it allows much easier use of the application.
     */

    /**
     * Textbox responsible for keeping track of entered text to filter customers by
     */
    public TextField textboxFilterCustomers;

    public ObservableList<DetailedCustomer> foundCustomers = FXCollections.observableArrayList();

    /**
     * Binds the table values to the detailedCustomers observableList
     * @param detailedCustomers
     */
    public void buildTable(ObservableList<DetailedCustomer> detailedCustomers) {
        columnCustomerId.setCellValueFactory(
                new PropertyValueFactory<Customer, Integer>("customerId")
        );
        columnName.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("customerName")
        );
        columnAddress.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("address")
        );
        columnPostalCode.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("postalCode")
        );
        columnPhone.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("phone")
        );
        columnCreated.setCellValueFactory(
                new PropertyValueFactory<Customer, Date>("createDate")
        );
        columnCreatedBy.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("createdBy")
        );
        columnLastUpdated.setCellValueFactory(
                new PropertyValueFactory<Customer, Date>("lastUpdate")
        );
        columnLastUpdatedBy.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("lastUpdatedBy")
        );
        columnDivisionId.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("divisionId")
        );
        columnDivision.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("division")
        );
        tableCustomers.setItems(detailedCustomers);
    }

    /**
     * Sets the title, and builds the tableCustomers display. Also displays the impending appointment as needed.
     */
    @FXML
    public void initialize() {
        Stage openWindow = (Stage) Stage.getWindows().stream().filter((window) -> window.isShowing()).findFirst().get();
        openWindow.setTitle("Customer Manager");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        ICustomerDao ICustomerDao = context.getBean(ICustomerDao.class);
        context.close();

        ICustomerDao.getAllCustomers().forEach(n -> foundCustomers.add(new DetailedCustomer(n)));
        buildTable(foundCustomers);
    }

    public void filterCustomers()
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        ICustomerDao ICustomerDao = context.getBean(ICustomerDao.class);
        context.close();
        String matchingSequence = textboxFilterCustomers.getText();

        List<Customer> foundCustomers = ICustomerDao.getAllCustomers().stream().filter(customer -> customer.getCustomerName().toLowerCase().contains(matchingSequence.toLowerCase())).collect(Collectors.toList());

        ObservableList<DetailedCustomer> matchingCustomers = FXCollections.observableArrayList();
        foundCustomers.forEach(customer -> matchingCustomers.add(new DetailedCustomer(customer)));
        buildTable(matchingCustomers);

    }

    /**
     * event handler for the new customer button. Opens the CreateEditCustomerForm
     */
    public void createNewCustomer() {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/CreateEditCustomerForm.fxml"));
            Parent addEditCustomerWindow = fxmlLoader.load();
            Scene addEditCustomerScene = new Scene(addEditCustomerWindow);
            Stage window = (Stage) buttonNewCustomer.getScene().getWindow();
            CreateEditCustomerFormController controller = fxmlLoader.getController();
            controller.setLoggedInUser(getLoggedInUser());
            window.setScene(addEditCustomerScene);
            window.setTitle("New Customer");
            window.show();
        }

        catch(Exception ex)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Event handler for the new customer button. Opens the CreateEditCustomerForm and populates the data with the selected customer. If null, will display an error message to the user.
     */
    public void editCustomer()
    {
        try
        {
            DetailedCustomer customer = (DetailedCustomer) tableCustomers.getSelectionModel().getSelectedItem();
            if (customer != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/CreateEditCustomerForm.fxml"));
                Parent addEditCustomerWindow = fxmlLoader.load();
                Scene addEditCustomerScene = new Scene(addEditCustomerWindow);
                Stage window = (Stage) buttonNewCustomer.getScene().getWindow();
                CreateEditCustomerFormController controller = fxmlLoader.getController();
                controller.setLoggedInUser(getLoggedInUser());
                controller.setupExistingCustomer(customer);

                window.setScene(addEditCustomerScene);
                window.setTitle("Edit customer");
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Customer to edit or create one if there aren't any.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Event handler for the delete customer button. Will throw a error message to the user when there is no customer selected.
     */
    public void deleteCustomer() {
        try
        {
            DetailedCustomer selectedCustomer = (DetailedCustomer) tableCustomers.getSelectionModel().getSelectedItem();

            if (selectedCustomer != null) {
                Alert deleteWarning = new Alert(Alert.AlertType.WARNING, "are you sure you want to delete customer with ID of  " + selectedCustomer.getCustomerId() + ", and, any associated appointments??", ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> alertInput = deleteWarning.showAndWait();
                if (alertInput.get() == ButtonType.OK) {
                    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
                    ICustomerDao iCustomerDao = context.getBean(ICustomerDao.class);
                    context.close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer with ID " + selectedCustomer.getCustomerId() + " has been deleted.", ButtonType.OK);
                    alert.showAndWait();
                    foundCustomers.remove(selectedCustomer);
                    iCustomerDao.deleteCustomer(selectedCustomer.getCustomerId());
                    filterCustomers();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a Customer to edit or create one if there aren't any.", ButtonType.OK);
                alert.showAndWait();
            }
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * Event handler for the Manage Appointments button
     */
    public void openAppointmentWindow () {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/AppointmentManagerForm.fxml"));
            Parent appointmentManagerWindow = fxmlLoader.load();
            Scene appointmentManagerScene = new Scene(appointmentManagerWindow);
            Stage window = (Stage) buttonNewCustomer.getScene().getWindow();
            AppointmentManagerController controller = fxmlLoader.getController();
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
            IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
            context.close();
            ObservableList<DetailedAppointment> appointmentsList = FXCollections.observableArrayList();
            iAppointmentDao.getAllAppointments().forEach(n -> appointmentsList.add(new DetailedAppointment(n)));
            controller.buildTable(appointmentsList);
            controller.setLoggedInUser(this.loggedInUser);
            window.setScene(appointmentManagerScene);
            window.show();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

}
