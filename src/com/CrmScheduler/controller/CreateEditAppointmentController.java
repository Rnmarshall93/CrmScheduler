package com.CrmScheduler.controller;


import com.CrmScheduler.DAO.AppointmentDaoImplSql;
import com.CrmScheduler.DAO.IAppointmentDao;
import com.CrmScheduler.DAO.ICustomerDao;
import com.CrmScheduler.DAO.CustomerDaoImplSql;
import com.CrmScheduler.HelperUtilties.TimeTools;
import com.CrmScheduler.SpringConf;
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
import com.CrmScheduler.entity.Appointment;
import com.CrmScheduler.entity.Contact;
import com.CrmScheduler.entity.DetailedAppointment;
import com.CrmScheduler.entity.CrmUser;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.time.Instant;

/**
 * Controller for CreateEditAppointmentForm
 */
public class CreateEditAppointmentController {


    /**
     * Textfield that holds the appointment ID. This is set to auto generated and only displays an actual number when an appointment is being edited.
     */
    public TextField inputAppointmentId;
    /**
     * Textfield that holds the appointment title. Valid as long as it isn't empty.
     */
    public TextField inputTitle;
    /**
     * Textfield that holds the appointment description. Valid as long as it isn't empty.
     */
    public TextField inputDescription;
    /**
     * Textfield that holds the appointment location. valid as long as it isn't empty.
     */
    public TextField inputLocation;
    /**
     * Textfield that holds the appointment type. valid as long as it isn't empty.
     */
    public TextField inputType;
    /**
     * Textfield that holds the appointment start timestamp. The format is  yyyy-mm-dd hh:mm:ss or for example, 2022-1-12 10:00:00 for January 12, 2022 at 10:00 A.M. with 0 seconds passed.
     * Valid if the format matches, and the end time doesn't occur before the start. Also subject to business requirements such as being within business hours and not conflicting.
     */
    public TextField inputStart;
    /**
     * Textfield that holds the appointment end timestamp. The format is  yyyy-mm-dd hh:mm:ss or for example, 2022-1-12 10:00:00 for January 12, 2022 at 10:00 A.M. with 0 seconds passed.
     * Valid if the format matches, and the end time doesn't occur before the start. Also subject to business requirements such as being within business hours and not conflicting.
     */
    public TextField inputEnd;
    /**
     * Disabled Textfield that is Auto generated from the loggedInUser tracked throughout the application
     */
    public TextField inputUserId;
    /**
     * Combobox that contains a list of contacts the user may choose from. Autopopulated so the user can only select from preset values.
     */
    public ComboBox comboContact;
    /**
     * combobox  that keeps track of all the customer IDs. Values are auto generated from the customers existing in the database.
     */
    public ComboBox comboCustomerIds;

    /**
     * Validates all of the inputfields. All fields are valid if they have any values.
     * @return returns true if validation passes, false if any validation fails.
     */
    private boolean isFormInputValid()
    {
        if(inputTitle.getText().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Appointment's Title is filled out", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        if(inputDescription.getText().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Appointment's description is filled out.",ButtonType.OK);
            alert.showAndWait();
            return  false;
        }
        if(inputLocation.getText().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Appointments location is filled out",ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        if(comboContact.getSelectionModel().getSelectedItem() == null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure a contact is selected from the drop-down.",ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        if(inputType.getText().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Type of appointment is filled out.",ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        if(inputStart.getText().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Appointment's start date is filled out.",ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        if(inputEnd.getText().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure the Appointment's end date is filled out.",ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        if(comboCustomerIds.getSelectionModel().getSelectedItem().toString().length() == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please make sure an existing customer is listed in Customer ID.",ButtonType.OK);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * ObservableList that keeps tracks of all contacts from the database. used for filling out the form.
     */
    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    /**
     * Keeps track of the selected contact behind the scenes so the user doesn't have to worry about it.
     */
    private int selectedContactId;
    /**
     * Keeps track of the existing appointment being edited, if there is one.
     */
    private Appointment existingAppointment;
    /**
     * used for managing the user from the login form throughout the application.
     */
    private CrmUser loggedInUser;

    /**
     * getter for the contacts list.
     * @return returns the contactsList.
     */
    public ObservableList<Contact> getContactsList() {
        return contactsList;
    }

    /**
     * setter for the contactsList.
     * @param contactsList an ObservableList of contacts.
     */
    public void setContactsList(ObservableList<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    /**
     * getter for the existing appointment being edited.
     * @return returns the existing appointment if being edited. If none exists, returns null.
     */
    public Appointment getExistingAppointment() {
        return existingAppointment;
    }

    /**
     * setter for the existing appointment being edited.
     * @param existingAppointment the existing appointment to be set to.
     */
    public void setExistingAppointment(Appointment existingAppointment) {
        this.existingAppointment = existingAppointment;
    }

    /**
     * getter for the logged in user obtained from the login form.
     * @return loggedInUser the loggedInUser tracked throughout the application.
     */
    public CrmUser getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * setter for the logged in user from the login form.
     * @param loggedInUser the loggedInUser from the login form.
     */
    public void setLoggedInUser(CrmUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }


    /**
     * Sets inputAppointmentId's text value to "Auto" if the appointment is new.
     * @return true if the appointment is new. False if the appointment is an edit.
     */
    private boolean creatingNewAppointment() {
        return inputAppointmentId.getText().contains("Auto");
    }

    /**
     * Checks that an appointment is within business hours in EST (8 A.M to 10 P.M), has its end after its start, and that the customer isn't scheduled during the time period. Aditionally, the start time and end time
     * may not be at the same time.
     * @param appointment the appointment to validate
     * @return true if all validations pass, false if any fail.
     * @see Appointment#endsAfterStart
     * @see Appointment#startAndEndSame
     * @see Appointment#isWithinBusinessHours(Appointment)
     * @see Appointment#isConflictingWithCustomer
     */
    public boolean isValidAppointment(Appointment appointment) {

        if (!appointment.isWithinBusinessHours(appointment)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment is outside of business hours. Please make sure the start and end time are EST business hours. (8 A.M. to 10 P.M. EST)");
            alert.showAndWait();
            return false;
        }
        if(appointment.endsAfterStart.test(appointment))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment start time must be before the appointment's ending time");
            alert.showAndWait();
            return false;
        }
        if(appointment.startAndEndSame.test(appointment))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "The appointment start time and end time cannot be the same.");
            alert.showAndWait();
            return false;
        }
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
        context.close();

        Appointment conflictingCustomerAppointment = iAppointmentDao.getAllAppointments().stream().filter(existingAppointment -> existingAppointment.getAppointmentId() != appointment.getAppointmentId() && existingAppointment.isConflictingWithCustomer.test(appointment, existingAppointment)).findFirst().orElse(null);
        if (conflictingCustomerAppointment != null) {
            TimeTools timeTools = new TimeTools();

            Alert alert = new Alert(Alert.AlertType.ERROR, "Appointment with the ID " + conflictingCustomerAppointment.getAppointmentId()
                    + " was found to be conflicting with the new appointment. Please make sure the selected Customer is not scheduled during this time.\n" + "Start: "
                    + timeTools.ConvertUtcToSystemTime(conflictingCustomerAppointment.getStart()) + "\nEnd: "
                    + timeTools.ConvertUtcToSystemTime(conflictingCustomerAppointment.getEnd()));
            alert.showAndWait();
            return false;
        }

        return true;
    }

    /**
     * returns the user to the appointment form. Event handler for the cancel button.
     */
    private void returnToAppointmentForm() {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/AppointmentManagerForm.fxml"));
            Parent managementWindow = fxmlLoader.load();
            Scene ModifyPartScene = new Scene(managementWindow);
            Stage window = (Stage) inputType.getScene().getWindow();
            AppointmentManagerController controller = fxmlLoader.getController();
            controller.setLoggedInUser(this.loggedInUser);

            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
            IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
            context.close();

            ObservableList<DetailedAppointment> detailedAppointments = FXCollections.observableArrayList();
            iAppointmentDao.getAllAppointments().forEach((appointment -> detailedAppointments.add(new DetailedAppointment(appointment))));

            controller.buildTable(detailedAppointments);

            if (getExistingAppointment() != null) {
            }
            window.setScene(ModifyPartScene);
            window.show();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Runs when the form is loaded. Sets the title and populates the customerIds combobox data
     */
    @FXML
    public void initialize() {

        Stage openWindow = (Stage) Stage.getWindows().stream().filter((window) -> window.isShowing()).findFirst().get();
        openWindow.setTitle("Create/Edit Appointment");

        ObservableList<String> customerIds = FXCollections.observableArrayList();

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        ICustomerDao iCustomerDao = context.getBean(ICustomerDao.class);
        iCustomerDao.getAllCustomers().forEach(customer -> customerIds.add(String.valueOf(customer.getCustomerId())));
        comboCustomerIds.setItems(customerIds);

    }


    /**
     * Saves the appointment if its valid. If the appointment is a new appointment, then it is created as a new appointment in the database. If the appointment is existing then
     * the appointment is updated in the database instead. Event handler for the save button.
     */
    public void saveAppointment() {
        if(!isFormInputValid())
            return;

        TimeTools timeTools = new TimeTools();

        try {
            if (creatingNewAppointment()) {
                Appointment appointment = new Appointment();
                appointment.setTitle(inputTitle.getText());
                appointment.setDescription(inputDescription.getText());
                appointment.setLocation(inputLocation.getText());
                appointment.setType(inputType.getText());
                Timestamp startTimestamp = Timestamp.valueOf(inputStart.getText());
                appointment.setStart(timeTools.ConvertDateToUTC(startTimestamp));
                Timestamp endTimestamp = Timestamp.valueOf(inputEnd.getText());
                appointment.setEnd(timeTools.ConvertDateToUTC(endTimestamp));
                //The contact ID gets its value from the combobox for contacts being changed. It allows a more user friendly approach for selecting the contactID
                appointment.setContactId(this.selectedContactId);
                appointment.setCustomerId(Integer.parseInt(String.valueOf(comboCustomerIds.getSelectionModel().getSelectedItem())));
                appointment.setUserId(Integer.parseInt(inputUserId.getText()));
                appointment.setCreatedBy(loggedInUser.getUserName());
                appointment.setCreateDate(timeTools.ConvertDateToUTC(Timestamp.from(Instant.now())));
                appointment.setLastUpdatedBy(loggedInUser.getUserName());
                appointment.setLastUpdate(timeTools.ConvertDateToUTC(Timestamp.from(Instant.now())));
                if (!isValidAppointment(appointment))
                    return;

                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
                IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
                iAppointmentDao.createAppointment(appointment);
                returnToAppointmentForm();
            } else {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(Integer.parseInt(inputAppointmentId.getText()));
                appointment.setTitle(inputTitle.getText());
                appointment.setDescription(inputDescription.getText());
                appointment.setLocation(inputLocation.getText());
                appointment.setType(inputType.getText());
                Timestamp startTimestamp = Timestamp.valueOf(inputStart.getText());
                appointment.setStart(timeTools.ConvertDateToUTC(startTimestamp));
                Timestamp endTimestamp = Timestamp.valueOf(inputEnd.getText());
                appointment.setEnd(timeTools.ConvertDateToUTC(endTimestamp));
                //The contact ID gets its value from the combobox for contacts being changed. It allows a more user friendly approach for selecting the contactID
                appointment.setContactId(this.selectedContactId);
                appointment.setCustomerId(Integer.parseInt(String.valueOf(comboCustomerIds.getSelectionModel().getSelectedItem())));
                appointment.setUserId(Integer.parseInt(inputUserId.getText()));
                appointment.setCreateDate(timeTools.ConvertDateToUTC(getExistingAppointment().getCreateDate()));
                appointment.setCreatedBy(getExistingAppointment().getCreatedBy());
                appointment.setLastUpdatedBy(loggedInUser.getUserName());
                appointment.setLastUpdate(timeTools.ConvertDateToUTC(Timestamp.from(Instant.now())));
                appointment.setContactId(contactsList.filtered((x) -> x.getContactName().equals(comboContact.getValue())).get(0).getContactId());
                if (!isValidAppointment(appointment))
                    return;

                AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
                IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
                context.close();

                iAppointmentDao.updateAppointment(Integer.parseInt(inputAppointmentId.getText()), appointment);
                returnToAppointmentForm();
            }
        }
        //Will catch if the user has put in a bad timestamp, and warn them to use an appropriate one.
        catch (IllegalArgumentException illegalArgException)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please ensure the dates are of the format yyyy-mm-dd hh:mm:ss\n for example, december 1, 2022 at 1:30 pm and 20 seconds would be represented as 2022-12-1 13:30:20");
            alert.showAndWait();
        }
        catch (NullPointerException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(),ButtonType.OK);
            alert.showAndWait();
        }
    }


    /**
     * event handler for the cancel button for editing or creating new appointments. returns the user to the creation form.
     */
    public void discardAppointment() {
        returnToAppointmentForm();
    }

    /**
     * sets the value of selectedContactId to the matching comboContact's name. Event handler for what a contact is selected from the contact combobox.
     */
    public void contactSelected() {
        String selectedOption = (String) comboContact.getValue();
        this.selectedContactId = contactsList.filtered((x) -> x.getContactName().equals(selectedOption)).get(0).getContactId();
    }

}
