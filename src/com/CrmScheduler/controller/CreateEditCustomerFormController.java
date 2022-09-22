package com.CrmScheduler.controller;


import com.CrmScheduler.DAO.ICountriesDao;
import com.CrmScheduler.DAO.ICustomerDao;
import com.CrmScheduler.DAO.IFirstLevelDivisionsDao;
import com.CrmScheduler.HelperUtilties.TimeTools;
import com.CrmScheduler.SpringConf;
import com.CrmScheduler.entity.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Controller for CreateEditCustomerForm
 */
public class CreateEditCustomerFormController {

    /**
     * Textfield that holds the customer ID. Will hold the value of auto generated or the actual ID of the edited customer.
     */
    public TextField inputCustomerId;
    /**
     * Textfield that holds the customer name. Valid as long as it isn't empty.
     */
    public TextField inputName;
    /**
     * The textfield that holds the customer's address. valid as long as it isn't empty.
     */
    public TextField inputAddress;
    /**
     * The textfield that holds the customer's zip code. Valid as long as it isn't empty.
     */
    public TextField inputPostalCode;

    /**
     * The textfield that holds the customer's phone number. Valid as long as it isn't empty.
     */
    public TextField inputPhone;
    /**
     * The textfield that holds the customer's divisionId. This field is disabled and autogenerated from the selected country and division comboboxes.
     */
    public TextField inputDivisionId;
    /**
     * The combobox containing all values of the selected country, generated from the country combobox selected value.
     */
    public ComboBox inputDivision;
    /**
     * The combobox that holds which contry the user is in. Changing this will populate the division combobox with the appropriate divisions of that country.
     */
    public ComboBox inputCountry;
    /**
     * keeps track of the customer being edited if there is one.
     */
    private DetailedCustomer existingCustomer;

    /**
     * keeps track of the logged in user obtained from the login form. gets passed around the application.
     */
    private CrmUser loggedInUser = null;

    /**
     * getter for the logged in user.
     * @return returns the logged in user.
     */
    public CrmUser getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * setter for the logged in user.
     * @param loggedInUser the logged in user to set the value to
     */
    public void setLoggedInUser(CrmUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * Checks if the inputs are valid and returns false if any of them aren't. All inputs are valid as long as there is some input.
     * @return
     */
    private boolean isFormInputValid()
    {
        if(inputName.getText().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Customer's Name is filled out",ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        if(inputAddress.getText().length() == 0)
           {
               Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Customers Address is filled out.",ButtonType.OK);
               alert.showAndWait();
               return  false;
           }
        if(inputPostalCode.getText().length() == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Customer's Postal-code is filled out",ButtonType.OK);
                alert.showAndWait();
                return false;
            }
        if(inputPhone.getText().length() == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Customer's phone number is filled out.",ButtonType.OK);
                alert.showAndWait();
                return false;
            }
        if(inputDivision.getSelectionModel().getSelectedItem() == null)

            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure a First Level Division is selected.",ButtonType.OK);
                alert.showAndWait();
                return false;
            }
        if(inputCountry.getSelectionModel().getSelectedItem() == null)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure a Country is selected..",ButtonType.OK);
                alert.showAndWait();
                return false;
            }
        return true;
    }

    /**
     * Helper method to determine if a customer is being edited.
     * @return returns true if the value of the customerID input is filled with a number. If it isnt, then the appointment is new.
     */
    private boolean creatingNewCustomer()
    {
        return inputCustomerId.getText().matches("\\d+");
    }


    /**
     * getter for the existing customer
     * @return returns the existing customer
     */
    public DetailedCustomer getExistingCustomer() {
        return existingCustomer;
    }

    /**
     * setter the existing customer.
     * @param existingCustomer the existingCustomer to set the value to.
     */
    public void setExistingCustomer(DetailedCustomer existingCustomer) {
        this.existingCustomer = existingCustomer;
    }


    /**
     * Returns to the Customer Management form
     */
    private void returnToManagementForm() {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/ManagementForm.fxml"));
            Parent managementWindow = fxmlLoader.load();
            Scene managmentScene = new Scene(managementWindow);
            Stage window = (Stage) inputCustomerId.getScene().getWindow();
            CustomerManagerController controller = fxmlLoader.getController();
            controller.setLoggedInUser(getLoggedInUser());
            window.setScene(managmentScene);
            window.show();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage() ,ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * populates the form with the existing customer and sets combobox values as well.
     * @param existingCustomer the existing customer carried over from the customer manager form.
     */
    public void setupExistingCustomer(DetailedCustomer existingCustomer)
    {
        inputCustomerId.setText(String.valueOf(existingCustomer.getCustomerId()));
        inputName.setText((existingCustomer.getCustomerName()));
        inputAddress.setText(existingCustomer.getAddress());
        inputPostalCode.setText(existingCustomer.getPostalCode());
        inputPhone.setText(existingCustomer.getPhone());
        inputDivisionId.setText((String.valueOf(existingCustomer.getCustomerId())));

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        ICountriesDao iCountriesDao = context.getBean(ICountriesDao.class);
        IFirstLevelDivisionsDao iFirstLevelDivisionsDao = context.getBean(IFirstLevelDivisionsDao.class);

        context.close();

        existingCustomer.setFirstLevelDivision(iFirstLevelDivisionsDao.getFirstLevelDivision(existingCustomer.getDivisionId()));
        inputDivision.getSelectionModel().select(existingCustomer.getFirstLevelDivision().getDivision());
        Country existingCountry = iCountriesDao.getAllCountries().stream().filter(country ->  country.getCountryId() == existingCustomer.getFirstLevelDivision().getCountry().getCountryId()).findFirst().orElse(null);
        inputCountry.getSelectionModel().select(existingCountry.getCountry());

        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        iFirstLevelDivisionsDao.getFirstLevelDivisionsInCountry(existingCountry.getCountryId()).forEach(firstLevelDivision -> divisionNames.add(firstLevelDivision.getDivision()));
        inputDivision.setItems(divisionNames);
        this.setExistingCustomer(existingCustomer);
    }

    /**
     * populates the combobox for country with its values.
     */
    private void setupCountryComboboxSource()
    {
        ObservableList<String> countryNames = FXCollections.observableArrayList();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        ICountriesDao iCountriesDao = context.getBean(ICountriesDao.class);
        context.close();

        iCountriesDao.getAllCountries().forEach((country) -> countryNames.add(country.getCountry()));
        inputCountry.setItems(countryNames);
    }


    /**
     * Runs on form load. Runs the setupCountryComboboxSource() method and also sets the title.
     */
    @FXML
    public void initialize()
    {
        Stage openWindow = (Stage) Stage.getWindows().stream().filter((window) -> window.isShowing()).findFirst().get();
        setupCountryComboboxSource();
    }


    /**
     * Saves the customer as a new customer to database or edits the already existing customer. if any of the inputs are invalid then an error message will be thrown from
     * the isFormInputValid() method. New customers will display that the value for ID is autogenerated and unusable. Edited customers will have their ID from the database in the field.
     * Event handler for the save button.
     */
    public void saveCustomer() {
        if(!isFormInputValid())
            return;

        try {

            TimeTools timeTools = new TimeTools();

            if (creatingNewCustomer()) {
                Customer newCustomer = new Customer();
                newCustomer.setCustomerId(Integer.parseInt(inputCustomerId.getText()));
                newCustomer.setCustomerName(inputName.getText());
                newCustomer.setAddress(inputAddress.getText());
                newCustomer.setPhone(inputPhone.getText());
                newCustomer.setPostalCode(inputPostalCode.getText());
                newCustomer.setCreateDate(timeTools.ConvertDateToUTC(Timestamp.from(Instant.now())));
                newCustomer.setCreateDate(getExistingCustomer().getCreateDate());
                newCustomer.setCreatedBy(getExistingCustomer().getCreatedBy());
                newCustomer.setLastUpdate(timeTools.ConvertDateToUTC(Timestamp.from(Instant.now())));
                newCustomer.setLastUpdatedBy(loggedInUser.getUserName());
                if (isFormInputValid()) {

                    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
                    ICustomerDao iCustomerDao = context.getBean(ICustomerDao.class);
                    IFirstLevelDivisionsDao iFirstLevelDivisionsDao = context.getBean(IFirstLevelDivisionsDao.class);
                    newCustomer.setFirstLevelDivision(iFirstLevelDivisionsDao.getFirstLevelDivision(Integer.parseInt(inputDivisionId.getText())));
                    context.close();
                    iCustomerDao.updateCustomer(newCustomer.getCustomerId(), newCustomer);
                }

            } else {
                Customer newCustomer = new Customer();
                newCustomer.setCustomerName(inputName.getText());
                newCustomer.setAddress(inputAddress.getText());
                newCustomer.setPhone(inputPhone.getText());
                newCustomer.setPostalCode(inputPostalCode.getText());
                newCustomer.setCreateDate(timeTools.ConvertDateToUTC(Timestamp.from(Instant.now())));
                newCustomer.setCreatedBy(loggedInUser.getUserName());
                newCustomer.setLastUpdate(timeTools.ConvertDateToUTC(Timestamp.from(Instant.now())));
                newCustomer.setLastUpdatedBy(loggedInUser.getUserName());

                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
                ICustomerDao iCustomerDao = context.getBean(ICustomerDao.class);
                IFirstLevelDivisionsDao iFirstLevelDivisionsDao = context.getBean(IFirstLevelDivisionsDao.class);
                newCustomer.setFirstLevelDivision(iFirstLevelDivisionsDao.getFirstLevelDivision(Integer.parseInt(inputDivisionId.getText())));
                context.close();
                iCustomerDao.addCustomer(newCustomer);

            }

            returnToManagementForm();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage() ,ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * event handler for the cancel button, returns to the management form
     */
    public void discardCustomer() {
        returnToManagementForm();
    }

    /**
     * Event handler for when a country is selected from the country combobox. sets the CountryId to the matching value, as well as sets up the data in the First Level Division text area
     */
    public void countrySelected(){
        inputDivision.setItems(null);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        ICountriesDao iCountriesDao = context.getBean(ICountriesDao.class);
        IFirstLevelDivisionsDao iFirstLevelDivisionsDao = context.getBean(IFirstLevelDivisionsDao.class);
        context.close();

        String selectedOption = (String)inputCountry.getValue();

        int selectedCountryId = iCountriesDao.getCountryMatchingName(selectedOption).getCountryId();
        ArrayList<FirstLevelDivision>  matchingFld = iFirstLevelDivisionsDao.getFirstLevelDivisionsInCountry(selectedCountryId);

        ObservableList<String> matchingFirstLevelDivisions = FXCollections.observableArrayList();
        matchingFld.forEach(firstLevelDivision -> matchingFirstLevelDivisions.add(firstLevelDivision.getDivision()));

        inputDivision.setItems(matchingFirstLevelDivisions);

    }


    /**
    *Event handler for when the combobox for when the user picks a value from the list. sets the divisionId for the user
     */
        public void divisionSelected() {

        String selectedOption = (String)inputDivision.getValue();
        if(selectedOption != null)
        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
            IFirstLevelDivisionsDao iFirstLevelDivisionsDao = context.getBean(IFirstLevelDivisionsDao.class);
            context.close();

            int divisionId = iFirstLevelDivisionsDao.getAllFirstLevelDivisions().stream().filter((x) -> x.getDivision().equals(selectedOption)).findFirst().get().getDivisionId();
            inputDivisionId.setText(String.valueOf(divisionId));
        }
    }
    }
