package controller;


import DAO.AppointmentDaoImplSql;
import DAO.IAppointmentDao;
import DAO.IContactsDao;
import DAO.IContactsDaoImplSql;
import Utilities.ImpendingAppointmentSingleton;
import Utilities.TimeTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.DetailedAppointment;
import model.User;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller for AppointmentManagerForm
 */
public class AppointmentManagerController {

    /**
     * Tablecolumn responsible for keeping track of the appointment ID
     */
    public TableColumn columnAppointmentId;
    /**
     * Tablecolumtn responsible for keeping track of the appointment Title
     */
    public TableColumn columnTitle;
    /**
     * Tablecolumn responsible for keeping track of the appointments description
     */
    public TableColumn columnDescription;
    /**
     * tablecolumn responsible for keeping track of the appointment's location
     */
    public TableColumn columnLocation;
    /**
     * tablecolumn responsible for keeping track of the contact (Actual name), this was included as a bonus since its friendlier to look at than the ID.
     * Makes use of the DetailedAppointment model class
     */
    public TableColumn columnContact;
    /**
     * Tablecolumn responsible for keeping track of the appointments type
     */
    public TableColumn columnType;
    /**
     * Tablecolumn responsible for keeping track of the appointments start time. Displayed in local time. Values are stored in UTC.
     */
    public TableColumn columnStart;
    /**
     * Tablecolumn responsible for keeping track of the appointments end time. Displayed in local time. Values are stored in UTC.
     */
    public TableColumn columnEnd;
    /**
     * Tablecolumn responsible for keeping track of the autogenerated customerId
     */
    public TableColumn columnCustomerId;
    /**
     * Tablecolumn responsible for keeping track of the user who created the appointment.
     */
    public TableColumn columnUserId;
    /**
     * Table that holds all tablecolumns for customers. Makes use of the DetailedCustomer which also includes the customer's name for display purposes instead of only a Customer ID (Which is also included)
     */
    public TableView tableAppointments;
    /**
     * Takes the user to the CreateEditAppointment form
     */
    public Button buttonNewAppointment;
    /**
     * used to warn the user of upcoming appointments or states that there are no upcoming appointments (Within 15 minutes)
     */
    public Label labelImpendingAppointment;
    /**
     * Used to keep track of the logged in user so that it can be autogenerated throughout the form.
     */

    /**
     * used to keep track of the all appointments radio button
     */
    public RadioButton radioAll;


    /**
     * used to keep track of weekly appointments radio button
     */
    public RadioButton radioFilterWeek;

    /**
     * used to keep track of the monthly appointments radio button
     */
    public RadioButton radioFilterMonth;

    public TextField textboxFilterAppointments;
    private User loggedInUser;
    /**
     * getter for the loggedInUser
     * @return the logged in user
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * setter for the loggedInUser
     * @param loggedInUser the logged in user
     */
    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * Takes an observable list of type DetailedAppointment and builds the appointments table from it.
     * @param appointments the DetailedAppointments observable list to build the table from
     */
    public void buildTable(ObservableList<DetailedAppointment> appointments) {
        columnAppointmentId.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, Integer>("appointmentId")
        );
        columnTitle.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, String>("title")
        );
        columnDescription.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, String>("description")
        );
        columnLocation.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, String>("location")
        );
        columnContact.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, String>("contactName")
        );
        columnType.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, String>("type")
        );
        columnStart.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, Date>("start")
        );
        columnEnd.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, Date>("end")
        );
        columnCustomerId.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, Integer>("customerId")
        );
        columnUserId.setCellValueFactory(
                new PropertyValueFactory<DetailedAppointment, Integer>("userId")
        );

        tableAppointments.setItems(appointments);
    }


    /**
     * Sets the window title, as well as checks if there are any impending appointments and adjusts the labels as needed.
     */
    @FXML
    public void initialize() {

        Stage openWindow = (Stage) Stage.getWindows().stream().filter((window) -> window.isShowing()).findFirst().get();
        openWindow.setTitle("Appointment Manager");
        if (ImpendingAppointmentSingleton.getInstance().getFoundAppointment() != null) {
            Appointment displayAppointment = ImpendingAppointmentSingleton.getInstance().getFoundAppointment();
            labelImpendingAppointment.setText("As of login, there was an appointment within 15 minutes. Appointment ID:" + displayAppointment.getAppointmentId() + " starting at " + ImpendingAppointmentSingleton.getInstance().getFoundAppointment().getStart());
        } else
            labelImpendingAppointment.setText("As of login, there were no impending appointments");
    }

    private ObservableList<Appointment> getAppointmentsMatchingTitle()
    {
        List<Appointment> filteredAppointments = FXCollections.observableArrayList();
        IAppointmentDao iAppointmentDao = new AppointmentDaoImplSql();
        if(textboxFilterAppointments.getText().length() > 0)
        {
            String matchingSequence = textboxFilterAppointments.getText();
            filteredAppointments = iAppointmentDao.getAllAppointments().stream().filter(appointment -> appointment.getTitle().toLowerCase().contains(matchingSequence.toLowerCase())).collect(Collectors.toList());
        }

        ObservableList<Appointment> matchingAppointments = FXCollections.observableArrayList();
        filteredAppointments.forEach(appointment -> matchingAppointments.add(new DetailedAppointment(appointment)));
        return matchingAppointments;
    }


    public void filterAppointments()
    {
        ObservableList<Appointment> appointmentsFound = FXCollections.observableArrayList();
        //Deal with the text filter first, if there is one
        if(textboxFilterAppointments.getText().length() > 0)
        {
            appointmentsFound = getAppointmentsMatchingTitle();
        }
        else
        {
            IAppointmentDao appointmentDao = new AppointmentDaoImplSql();
            for (Appointment dbAppointment: appointmentDao.getAllAppointments()
                 ) {
                appointmentsFound.add(dbAppointment);
            }
        }

        if(radioFilterMonth.isSelected())
        {
            appointmentsFound = filterAppointmentsWithinMonth(appointmentsFound);
        }

        if(radioFilterWeek.isSelected())
        {
            appointmentsFound = filterAppointmentsWithinWeek(appointmentsFound);
        }

        ObservableList<DetailedAppointment> filteredDetailedAppointments = FXCollections.observableArrayList();
        appointmentsFound.forEach(appointment -> filteredDetailedAppointments.add(new DetailedAppointment(appointment)));
        buildTable(filteredDetailedAppointments);

    }

    public ObservableList<Appointment> filterAppointmentsWithinWeek(ObservableList <Appointment> appointments) {
        ObservableList<Appointment> appointmentsWithinWeek = FXCollections.observableArrayList();

        Calendar currentDateCal = Calendar.getInstance();
        Instant timeInstant = TimeTools.ConvertUtcToSystemTime(Timestamp.from(Instant.now())).toInstant();
        currentDateCal.setTime(Date.from(timeInstant));

        IAppointmentDao IAppointmentDao = new AppointmentDaoImplSql();

        for (Appointment appointment : appointments) {
            //convert the current time to calendar
            Calendar appointmentCalTime = Calendar.getInstance();
            appointmentCalTime.setTime(Date.from(appointment.getStart().toInstant()));

            if (appointmentCalTime.get(Calendar.WEEK_OF_YEAR) == currentDateCal.get(Calendar.WEEK_OF_YEAR)) {
                appointmentsWithinWeek.add(new DetailedAppointment(appointment));
            }

        }
        return appointmentsWithinWeek;
    }

    public ObservableList<Appointment> filterAppointmentsWithinMonth(ObservableList<Appointment> appointments) {
        ObservableList<Appointment> appointmentsWithinMonth = FXCollections.observableArrayList();
        appointments.stream().filter((appointment) -> TimeTools.ConvertUtcToSystemTime(appointment.getStart()).toLocalDateTime().getMonth() == LocalDateTime.now().getMonth()
                && TimeTools.ConvertUtcToSystemTime(appointment.getStart()).toLocalDateTime().getYear() == LocalDateTime.now().getYear()).forEach(appointment -> appointmentsWithinMonth.add(new DetailedAppointment(appointment)));
        return appointmentsWithinMonth;
    }



    /**
     * Opens the CreatedEditAppointmentForm with all blank values to allow the user to create a new appointment. populates the contacts and fills out the userID. Event handler for the new appointment button.
     */
    public void createNewAppointment() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/CreateEditAppointmentForm.fxml"));
        try {
            Parent appointmentManagerWindow = fxmlLoader.load();
            Scene addEditAppointmentScene = new Scene(appointmentManagerWindow);
            Stage window = (Stage) buttonNewAppointment.getScene().getWindow();
            CreateEditAppointmentController controller = fxmlLoader.getController();
            controller.setLoggedInUser(this.loggedInUser);
            window.setScene(addEditAppointmentScene);
            window.show();
            IContactsDao IContactsDao = new IContactsDaoImplSql();
            ObservableList<Contact> contactsList = FXCollections.observableArrayList();
            contactsList.addAll(IContactsDao.getAllContacts());
            ObservableList<String> contactNames = FXCollections.observableArrayList();
            for (Contact c : contactsList
            ) {
                contactNames.add(c.getContactName());
            }
            controller.comboContact.setItems(contactNames);
            controller.inputUserId.setText(String.valueOf(getLoggedInUser().getUserId()));
            controller.setContactsList(contactsList);
        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }


    }

    /**
     * takes the selected appointment and imports it into the CreateEditAppointmentController, filling out all values. Also populates the comboboxes with their information. Event handler for the edit appointment button.
     */
    public void editAppointment() {
        Appointment selectedAppointment = (Appointment) tableAppointments.getSelectionModel().getSelectedItem();
        try
        {
            if (selectedAppointment != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/CreateEditAppointmentForm.fxml"));
                Parent appointmentManagerWindow = fxmlLoader.load();
                Scene addEditAppointmentScene = new Scene(appointmentManagerWindow);
                Stage window = (Stage) buttonNewAppointment.getScene().getWindow();
                CreateEditAppointmentController controller = fxmlLoader.getController();
                controller.setLoggedInUser(this.loggedInUser);

                controller.inputAppointmentId.setText(String.valueOf(selectedAppointment.getAppointmentId()));
                controller.inputTitle.setText(selectedAppointment.getTitle());
                controller.inputDescription.setText(selectedAppointment.getDescription());
                controller.inputLocation.setText(selectedAppointment.getLocation());
                controller.inputType.setText(selectedAppointment.getType());
                controller.inputStart.setText(selectedAppointment.getStart().toString().split("\\.")[0]);
                controller.inputEnd.setText(selectedAppointment.getEnd().toString().split("\\.")[0]);
                controller.comboCustomerIds.getSelectionModel().select(String.valueOf(selectedAppointment.getCustomerId()));
                controller.inputUserId.setText(String.valueOf(selectedAppointment.getUserId()));
                controller.setExistingAppointment(selectedAppointment);

                IContactsDao IContactsDao = new IContactsDaoImplSql();
                controller.getContactsList().addAll(IContactsDao.getAllContacts());

                ObservableList<String> contactNames = FXCollections.observableArrayList();
                for (Contact c : controller.getContactsList()
                ) {
                    contactNames.add(c.getContactName());
                }
                controller.comboContact.setItems(contactNames);
                Contact currentContact = IContactsDao.getAllContacts().stream().filter(contact -> contact.getContactId() == selectedAppointment.getContactId()).findFirst().orElse(null);
                controller.comboContact.getSelectionModel().select(currentContact.getContactName());
                window.setScene(addEditAppointmentScene);
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to edit or create one if there aren't any.", ButtonType.OK);
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
     * Deletes the selected appointment. Will display a warning to the user, and if they choose to proceed, will go through with the delete. If the user selects cancel, nothing happens and the delete doesn't occur.
     * Event handler for the Delete appointment button
     */
    public void deleteAppointment() {
        Appointment selectedAppointment = (Appointment) tableAppointments.getSelectionModel().getSelectedItem();
        if (selectedAppointment != null) {
            IAppointmentDao IAppointmentDao = new AppointmentDaoImplSql();
            Alert deleteWarning = new Alert(Alert.AlertType.WARNING, "are you sure you want to cancel appointment with ID of  " + selectedAppointment.getAppointmentId() + " and of appointment type " +
                    selectedAppointment.getType() + "?", ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> alertInput = deleteWarning.showAndWait();
            if (alertInput.get() == ButtonType.OK) {
                IAppointmentDao.deleteAppointments(selectedAppointment.getAppointmentId());
                ObservableList<DetailedAppointment> remainingAppointments = FXCollections.observableArrayList();
                IAppointmentDao.getAllAppointments().forEach((appointment -> remainingAppointments.add(new DetailedAppointment(appointment))));
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment with ID of " + selectedAppointment.getAppointmentId() + " of appointment type " +
                        selectedAppointment.getType() + " was cancelled", ButtonType.OK);
                alert.showAndWait();
                filterAppointments();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select an appointment to delete, or create one first.");
            alert.showAndWait();
        }

    }


    /**
     * Opens the ManagementForm to manage customers. Event handler for the manage customers button.
     */
    public void openCustomerWindow(){
        try
        {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/ManagementForm.fxml"));
            Parent managementWindow = fxmlLoader.load();
            Scene ModifyPartScene = new Scene(managementWindow);
            Stage window = (Stage) buttonNewAppointment.getScene().getWindow();
            CustomerManagerController controller = fxmlLoader.getController();
            //pass the logged in user around
            controller.setLoggedInUser(getLoggedInUser());
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
     * Opens the ReportsForm for the user. Event handler for the View reports window.
     */
    public void viewReportsWindow() {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/ReportsForm.fxml"));
            Parent managementWindow = fxmlLoader.load();
            Scene ModifyPartScene = new Scene(managementWindow);
            Stage window = (Stage) buttonNewAppointment.getScene().getWindow();
            ReportsController controller = fxmlLoader.getController();
            controller.setLoggedInUser(getLoggedInUser());
            window.setScene(ModifyPartScene);
            window.show();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.showAndWait();
        }
    }


}