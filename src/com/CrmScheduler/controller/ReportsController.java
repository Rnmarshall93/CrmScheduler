package com.CrmScheduler.controller;


import com.CrmScheduler.DAO.IAppointmentDao;
import com.CrmScheduler.DAO.IContactsDao;
import com.CrmScheduler.HelperUtilties.TimeTools;
import com.CrmScheduler.SpringConf;
import com.CrmScheduler.entity.Appointment;
import com.CrmScheduler.entity.CrmUser;
import com.CrmScheduler.entity.DetailedAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * com.CrmScheduler.controller for the ReportsForm
 */
public class ReportsController {

    /**
     * Textarea that displays the month and type results.
     */
    public TextArea textAreaMonthAndType;
    /**
     * Textarea that displays past appointments, approaching appointments, and some stats about them.
     */
    public TextArea textAreaStats;
    /**
     * Combobox that is responsible for selecting the contact name that will filter the schedule. calls filterTableByContact() with the selected contact.
     */
    public ComboBox comboContactFilter;
    /**
     * column that displays the appointment Id.
     */
    public TableColumn columnAppointmentId;
    /**
     * column that displays the appointment title.
     */
    public TableColumn columnTitle;
    /**
     * column that displays the appointment description.
     */
    public TableColumn columnDescription;
    /**
     * column that displays the contactId.
     */
    public TableColumn columnContact;
    /**
     * column that displays the type of appointment.
     */
    public TableColumn columnType;
    /**
     * column that displays the start of the appointment in local time with a timestamp.
     */
    public TableColumn columnStart;
    /**
     * column that displays the end of the appointment in local time with a timestamp.
     */
    public TableColumn columnEnd;
    /**
     * column that displays the customerId.
     */
    public TableColumn columnCustomerId;
    /**
     * The table responsible for listing all of the appointments. Can be filtered with the contact dropdown to product a schedule for that contact only.
     */
    public TableView tableAppointments;
    /**
     * Combobox that holds the months of the year january through december.
     */
    public ComboBox comboMonths;
    /**
     * Combobox that is populated with all of the different appointment types.
     */
    public ComboBox comboTypes;
    /**
     * loggedInUser starts null, assigned via com.CrmScheduler.controller from the form that calls the reports form.
     */
    private CrmUser loggedInUser = null;

    /**
     * getter for the loggedInUser which is passed around the application.
     *
     * @return returns the logged in user
     */
    public CrmUser getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * setter for the loggedInUser
     *
     * @param loggedInUser sets the loggedInUser to the new value.
     */
    public void setLoggedInUser(CrmUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * Builds a schedule for the tableView based on the list of appointments.
     *
     * @param appointments an ObservableList of appointments.
     */
    public void buildSchedule(ObservableList appointments) {
        columnAppointmentId.setCellValueFactory(
                new PropertyValueFactory<Appointment, Integer>("appointmentId")
        );
        columnTitle.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("title")
        );
        columnDescription.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("description")
        );
        columnType.setCellValueFactory(
                new PropertyValueFactory<Appointment, String>("type")
        );
        columnStart.setCellValueFactory(
                new PropertyValueFactory<Appointment, Date>("start")
        );
        columnEnd.setCellValueFactory(
                new PropertyValueFactory<Appointment, Date>("end")
        );
        columnCustomerId.setCellValueFactory(
                new PropertyValueFactory<Appointment, Integer>("customerId")
        );

        columnContact.setCellValueFactory(
                new PropertyValueFactory<Appointment, Integer>("contactId")
        );
        tableAppointments.setItems(appointments);
    }

    /**
     * Called whenever the combobox for type or month change. If both have valid values, builds a report in the textAreaMonthAndType textarea.
     * If either value is invalid, builds no report and displays that nothing was found. Event handler for the type and month comboboxes.
     */
    private void buildReportsByMonthAndType() {
        String selectedMonth = (String) comboMonths.getSelectionModel().getSelectedItem();
        String selectedType = (String) comboTypes.getSelectionModel().getSelectedItem();

        if (selectedMonth == null || selectedType == null) {
            textAreaMonthAndType.appendText("Please select values for both the Month and Type combo boxes to generate the report.");
        } else {

            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
            IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
            context.close();

            TimeTools timeTools = new TimeTools();

            ObservableList<Appointment> matchingAppointmentsList = FXCollections.observableArrayList();
            iAppointmentDao.getAllAppointments().stream().filter(appointment -> timeTools.ConvertUtcToSystemTime(appointment.getStart()).toLocalDateTime().getMonth().toString().equals(selectedMonth.toUpperCase())
                    && appointment.getType().equals(selectedType)).forEach(
                    matchingAppointment -> matchingAppointmentsList.add(matchingAppointment)
            );

            int amountOfAppointments = matchingAppointmentsList.size();


            textAreaMonthAndType.setText("Appointments found for month of " + selectedMonth + " and type of " + selectedType + ": " + amountOfAppointments);
            matchingAppointmentsList.forEach(matchingAppointment -> textAreaMonthAndType.appendText(("\nID:" + matchingAppointment.getAppointmentId() + "\tDescription: " + matchingAppointment
                    .getDescription() + "\tStart time (Local time) : " + timeTools.ConvertUtcToSystemTime(matchingAppointment.getStart()).toString())));

        }
    }

    /**
     * Helper method that fills the combobox for contacts with its data.
     */
    private void buildComboContactsData() {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        IContactsDao iContactsDao = context.getBean(IContactsDao.class);
        ObservableList<String> contactDetails = FXCollections.observableArrayList();
        iContactsDao.getAllContacts().forEach(contact -> contactDetails.add(contact.getContactId() + ": " + contact.getContactName()));
        comboContactFilter.setItems(contactDetails);
        comboContactFilter.getItems().add("All Contacts");
    }

    /**
     * Builds a report in textAreaStats containing info about Appointments that are expired (in the past) and upcoming appointments. Also gives a statistic about how many appointments per month.
     * This method makes use of 2 predicates I created for appointment which are isBefore() and isAfter(), which assist in filtering appointments that are within dates. This method makes heavy use
     * of lambdas as well, helping to refine and build lists as needed.
     */
    private void buildOverviewReport() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
        context.close();


        ObservableList<Appointment> expiredAppointments = FXCollections.observableArrayList();
        //Here I use a lambda to help filter the appropriate appointments using isBefore to see if an appointment has occured before a specified time
        TimeTools timeTools = new TimeTools();
        iAppointmentDao.getAllAppointments().stream().filter(appointment -> appointment.isBefore.test(timeTools.ConvertUtcToSystemTime(appointment.getStart()), Timestamp.from(Instant.now()))).forEach(appointment ->
                expiredAppointments.add(appointment));
        long appointmentsBeforeNow = expiredAppointments.size();
        textAreaStats.appendText("Appointments past expiration : " + appointmentsBeforeNow);
        expiredAppointments.forEach(appointment -> textAreaStats.appendText("\nID:" + appointment.getAppointmentId() + "\tDescription: " + appointment
                .getDescription() + "\tStart time (Local time) : " + timeTools.ConvertUtcToSystemTime(appointment.getStart()).toString()));

        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        //Here I use a lambda again, to help filter. This time its isAfter which helps determine is appointments are after a specified time.
        iAppointmentDao.getAllAppointments().stream().filter(appointment -> appointment.isAfter.test(appointment.getStart(), Timestamp.from(Instant.now()))).forEach(appointment ->
                upcomingAppointments.add(appointment));
        textAreaStats.appendText("\n\nUpcoming appointments");
        upcomingAppointments.forEach(appointment -> textAreaStats.appendText("\nID:" + appointment.getAppointmentId() + "\tDescription: " + appointment
                .getDescription() + "\tStart time (Local time) : " + timeTools.ConvertUtcToSystemTime(appointment.getStart()).toString()));
        long appointmentsUpcoming = upcomingAppointments.size();
        textAreaStats.appendText("\n\nTotal Appointments awaiting completion : " + appointmentsUpcoming);
        long totalAppointments = iAppointmentDao.getAllAppointments().size();
        double appointmentsPerMonth = (double) totalAppointments / 12;
        String formattedAppointmentsPerMonth = new DecimalFormat("#.###").format(appointmentsPerMonth);
        textAreaStats.appendText("\nAverage appointments per month (All-time) : " + formattedAppointmentsPerMonth);
    }

    /**
     * Called when the form loads. initializes the table that holds employee schedules, and populates all controls with their necessary data
     */
    @FXML
    public void initialize() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
        context.close();
        Stage openWindow = (Stage) Stage.getWindows().stream().filter((window) -> window.isShowing()).findFirst().get();
        openWindow.setTitle("Reports for internal use");
        tableAppointments.getSortOrder().add(columnContact);
        buildReportsByMonthAndType();
        ObservableList allAppointments = FXCollections.observableArrayList();
        allAppointments.addAll(iAppointmentDao.getAllAppointments());
        buildSchedule(allAppointments);
        buildComboContactsData();
        buildOverviewReport();

        ObservableList<String> months = FXCollections.observableArrayList();
        months.addAll("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
        comboMonths.setItems(months);

        HashSet<String> appointmentTypes = new HashSet<>();
        iAppointmentDao.getAllAppointments().forEach(appointment -> appointmentTypes.add(appointment.getType()));
        ObservableList<String> foundTypes = FXCollections.observableArrayList();
        appointmentTypes.forEach(type -> foundTypes.add(type));
        comboTypes.setItems(foundTypes);
        //load appointmentTypes with unique text from the appointment types.

    }

    /**
     * Filters the table based on the selected contact from the contacts combobox.
     */
    public void filterTableByContact() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConf.class);
        IAppointmentDao iAppointmentDao = context.getBean(IAppointmentDao.class);
        context.close();
        String selectedValue = (String) comboContactFilter.getValue();
        if (selectedValue == "All Contacts") {
            ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
            allAppointments.addAll(iAppointmentDao.getAllAppointments());
            buildSchedule(allAppointments);
            return;
        }
        int selectedId = Integer.parseInt(selectedValue.split(":")[0]);
        ObservableList<Appointment> appointmentsByContact = FXCollections.observableArrayList();
        appointmentsByContact.addAll(iAppointmentDao.getAllAppointments().stream().filter(appointment -> appointment.getContactId() == selectedId).collect(Collectors.toList()));
        buildSchedule(appointmentsByContact);
    }


    /**
     * Helper method to rebuild the report for Month and Type filtered appointments.
     */
    public void applyMonthlyTypeComboChanges() {
        buildReportsByMonthAndType();
    }


    /**
     * Event handler for the return to customer manager button. Returns to the customer management form.
     */
    public void returnToCustomerManager() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/ManagementForm.fxml"));
            Parent managementWindow = fxmlLoader.load();
            Scene ModifyPartScene = new Scene(managementWindow);
            Stage window = (Stage) textAreaStats.getScene().getWindow();
            CustomerManagerController controller = fxmlLoader.getController();
            controller.setLoggedInUser(getLoggedInUser());
            window.setScene(ModifyPartScene);
            window.show();
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * Event handler for the return to Appointment Manager button. Returns to the appointment manager form.
     */
    public void returnToAppointmentManager() {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/AppointmentManagerForm.fxml"));
            Parent appointmentManagerWindow = fxmlLoader.load();
            Scene appointmentManagerScene = new Scene(appointmentManagerWindow);
            Stage window = (Stage) textAreaMonthAndType.getScene().getWindow();
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
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage() ,ButtonType.OK);
            alert.showAndWait();
        }
    }

}
