package DAO;


import model.Appointment;

import java.util.ArrayList;

/**
 * Interface for Appointment DAO.
 */
public interface IAppointmentDao {
    /**
     * Creates a new appointment.
     * @param appointment the appointment to create.
     */
    public void createAppointment(Appointment appointment);

    /**
     * gets an appointment using its appointmentId.
     * @param appointmentId the appointmentId to match to an appointment.
     * @return returns the found appointment.
     */
    public Appointment getAppointment(int appointmentId);

    /**
     * Gets all appointments and returns them as an ArrayList with type Appointment
     * @return returns an ArrayList with type Appointment
     */
    public ArrayList<Appointment> getAllAppointments();

    /**
     * updates an appointment based on its Id
     * @param appointmentId the appointmentId of the appointment to update
     * @param newAppointment the new appointment information
     */
    public void updateAppointment(int appointmentId, Appointment newAppointment);

    /**
     * Deletes an appointment based on its Id.
     * @param appointmentId the appointmentId of the appointment to delete.
     */
    public void deleteAppointments(int appointmentId);
}
