package DAO;

import Utilities.ConnectionFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * implementation in SQL of the Appointment Dao
 */
public class AppointmentDaoImplSql implements IAppointmentDao {

    /**
     * Creates a new appointment in the database from newAppointment
     * @param newAppointment the newAppointment to be created
     */
    @Override
    public void createAppointment(Appointment newAppointment) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_Id, User_Id, Contact_ID) " +
                "values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try
        {
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);

            preparedStatement.setString(1, newAppointment.getTitle());
            preparedStatement.setString(2, newAppointment.getDescription());
            preparedStatement.setString(3, newAppointment.getLocation());
            preparedStatement.setString(4, newAppointment.getType());
            preparedStatement.setTimestamp(5, newAppointment.getStart());
            preparedStatement.setTimestamp(6, newAppointment.getEnd());
            preparedStatement.setTimestamp(7, newAppointment.getCreateDate());
            preparedStatement.setString(8, newAppointment.getCreatedBy());
            preparedStatement.setTimestamp(9, newAppointment.getLastUpdate());
            preparedStatement.setString(10, newAppointment.getLastUpdatedBy());
            preparedStatement.setInt(11, newAppointment.getCustomerId());
            preparedStatement.setInt(12, newAppointment.getUserId());
            preparedStatement.setInt(13, newAppointment.getContactId());

            preparedStatement.execute();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * Gets an appointment from its unique Id
     * @param appointmentId the unique Id of the appointment.
     * @return returns the found appointment from appointmentId
     */
    @Override
    public Appointment getAppointment(int appointmentId) {
        try{

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_Id, User_Id, Contact_ID" +
                    " from appointments where Appointment_ID = ?";


            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, appointmentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt(1));
                appointment.setTitle(resultSet.getString(2));
                appointment.setDescription(resultSet.getString(3));
                appointment.setLocation(resultSet.getString(4));
                appointment.setType(resultSet.getString(5));
                appointment.setStart(resultSet.getTimestamp(6));
                appointment.setEnd(resultSet.getTimestamp(7));
                appointment.setCreateDate(resultSet.getTimestamp(8));
                appointment.setCreatedBy(resultSet.getString(9));
                appointment.setLastUpdate(resultSet.getTimestamp(10));
                appointment.setLastUpdatedBy(resultSet.getString(11));
                appointment.setCustomerId(resultSet.getInt(12));
                appointment.setUserId(resultSet.getInt(13));
                appointment.setContactId(resultSet.getInt(14));
                dbConn.close();
                return appointment;
            }
            dbConn.close();
        }

        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        return null;

    }

    /**
     * Gets all appointments from the database and ands them back as an array list.
     * @return returns an ArrayList with type Appointment
     */
    @Override
    public ArrayList<Appointment> getAllAppointments() {
        try {


            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID " +
                    "from appointments";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            ArrayList<Appointment> foundAppointments = new ArrayList<>();
            while(resultSet.next())
            {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt(1));
                appointment.setTitle(resultSet.getString(2));
                appointment.setDescription(resultSet.getString(3));
                appointment.setLocation(resultSet.getString(4));
                appointment.setType(resultSet.getString(5));
                appointment.setStart(resultSet.getTimestamp(6));
                appointment.setEnd(resultSet.getTimestamp(7));
                appointment.setCreateDate(resultSet.getTimestamp(8));
                appointment.setCreatedBy(resultSet.getString(9));
                appointment.setLastUpdate(resultSet.getTimestamp(10));
                appointment.setLastUpdatedBy(resultSet.getString(11));
                appointment.setCustomerId(resultSet.getInt(12));
                appointment.setUserId(resultSet.getInt(13));
                appointment.setContactId(resultSet.getInt(14));
                foundAppointments.add(appointment);
            }
            dbConn.close();

            return foundAppointments;
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }

    /**
     * Overwrites the data on an exisitng appointment with its updated information.
     * @param appointmentId the unique ID of the appointment being updates.
     * @param newAppointment the newAppointment which represents the updated data collected.
     */
    @Override
    public void updateAppointment(int appointmentId, Appointment newAppointment) {
        try {

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "update appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Create_Date = ?," +
                    " Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "where Appointment_ID = ?";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setString(1, newAppointment.getTitle());
            preparedStatement.setString(2, newAppointment.getDescription());
            preparedStatement.setString(3, newAppointment.getLocation());
            preparedStatement.setString(4, newAppointment.getType());
            preparedStatement.setTimestamp(5, newAppointment.getStart());
            preparedStatement.setTimestamp(6, newAppointment.getEnd());
            preparedStatement.setTimestamp(7, newAppointment.getCreateDate());
            preparedStatement.setString(8, newAppointment.getCreatedBy());
            preparedStatement.setTimestamp(9, newAppointment.getLastUpdate());
            preparedStatement.setString(10, newAppointment.getLastUpdatedBy());
            preparedStatement.setInt(11, newAppointment.getCustomerId());
            preparedStatement.setInt(12, newAppointment.getUserId());
            preparedStatement.setInt(13, newAppointment.getContactId());
            preparedStatement.setInt(14, appointmentId);
            preparedStatement.execute();
            dbConn.close();


        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Deletes the appointment from the database.
     * @param appointmentId the unique Id of the appointment to delete.
     */
    @Override
    public void deleteAppointments(int appointmentId) {
        try {

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "Delete from Appointments where ? = Appointment_ID";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, appointmentId);
            preparedStatement.execute();
        }
        catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

}
