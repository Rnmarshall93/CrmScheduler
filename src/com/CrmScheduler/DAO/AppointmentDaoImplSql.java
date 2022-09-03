package com.CrmScheduler.DAO;

import com.CrmScheduler.HibernateConf;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.CrmScheduler.entity.Appointment;
import org.hibernate.Session;
import org.hibernate.query.Query;

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

        try
        {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(newAppointment);

            session.getTransaction().commit();
            session.close();
        } catch (Exception exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exc.getLocalizedMessage(), ButtonType.OK);
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

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            Appointment foundAppointment = session.get(Appointment.class, appointmentId);

            session.getTransaction().commit();
            session.close();
            return foundAppointment;
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
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            Query query = session.createQuery("from Appointment");
            ArrayList<Appointment> allAppointments = new ArrayList<>();
            allAppointments.addAll(query.list());
            session.getTransaction().commit();
            session.close();
            return allAppointments;
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
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.update(newAppointment);

            session.getTransaction().commit();
            session.close();


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

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();
            Appointment foundAppointment = session.get(Appointment.class, appointmentId);
            session.delete(foundAppointment);

            session.getTransaction().commit();
            session.close();
        }
        catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

}
