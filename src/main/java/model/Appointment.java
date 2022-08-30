package model;

import Utilities.TimeTools;

import java.sql.Timestamp;
import java.time.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


/**
 * The Model class for appointment.
 */
public class Appointment {


    /**
     ** Determines if the newAppointment is after the existingAppointment. Returns true if the newAppointment is after the existingAppointment.
     */
    public BiPredicate<Timestamp,Timestamp> isAfter = (newAppointment,existingAppointment) -> newAppointment.compareTo(existingAppointment) >= 0;
    /**
     * Determines if the newAppointment is before the existingAppointment/ Returns true if the newAppointment is before the existingAppointment
     */
    public BiPredicate<Timestamp,Timestamp> isBefore = (newAppointment,existingAppointment) -> newAppointment.compareTo(existingAppointment) <= 0;
    /**
     * Predicate used to validate that an appointments Start occurs after its end.
     */
    public Predicate<Appointment> endsAfterStart = (appointment -> appointment.getEnd().before(appointment.getStart()));
    /**
     * predicate that returns true if the appointment's start and end date are at the same time.
     */
    public Predicate<Appointment> startAndEndSame = (appointment -> appointment.getStart().equals(appointment.getEnd()));
    /**
     * Predicate used to determine if an appointment is conflicting with an already existing customer. If the customer is scheduled during the newly scheduled time, it will return false.
     * If the contact is not busy during the start and end times, the predicate will return true.
     */
    public BiPredicate<Appointment,Appointment> isConflictingWithCustomer = (newAppointment, existingAppointment) ->
            newAppointment.isAfter.test(newAppointment.getStart(),existingAppointment.getStart()) && newAppointment.isBefore.test(newAppointment.getStart(),existingAppointment.getEnd()) && newAppointment.getCustomerId() == existingAppointment.getCustomerId()
                    || newAppointment.isAfter.test(newAppointment.getEnd(),existingAppointment.getStart()) && newAppointment.isBefore.test(newAppointment.getEnd(),existingAppointment.getEnd()) && newAppointment.getCustomerId() == existingAppointment.getCustomerId();
    /**
     * Predicate used to determine if an appointment is within the warning period (Within 15 minutes). returns true if the appointment is occuring within 15 minutes of the users local time.
     */
    public Predicate<Appointment> isAppointmentWithinWarningPeriod = appointment -> appointment.getStart().after(TimeTools.ConvertDateToUTC(Timestamp.from(Instant.now()))) &&
            appointment.getStart().before(TimeTools.ConvertDateToUTC(Timestamp.valueOf(LocalDateTime.now().plusMinutes(15))));
    /**
     * the unique ID of the appointment.
     */
    private int appointmentId;
    /**
     * the title of the appointment.
     */
    private String title;
    /**
     * the description of the appointment.
     */
    private String description;
    /**
     * the location the appointment is scheduled at.
     */
    private String location;
    /**
     * The type of appointment.
     */
    private String type;
    /**
     * The start time of the appointment.
     */
    private Timestamp start;
    /**
     * The End time of the appointment.
     */
    private Timestamp end;
    /**
     * The create date of the timestamp.
     */
    private Timestamp createDate;
    /**
     * The original user who created the Appointment.
     */
    private String createdBy;
    /**
     * The last time the user was updated.
     */
    private Timestamp lastUpdate;
    /**
     * The last User to update the Appointment.
     */
    private String lastUpdatedBy;
    /**
     * The unique CustomerID (for the customer) for the appointment.
     */
    private int customerId;
    /**
     * The unique userId of the user editing the Appointment.
     */
    private int userId;
    /**
     * The unique ContactId for the appointment's contact.
     */
    private int contactId;

    /**
     * helper function that determins if an appointment is within business hours. A appointment may be scheduled and end as late as 10, under the business case
     * that an emergency appointment may need to be made and worked on. While appointments may end at 10 P.M., they must end at 10 P.M. sharp, so the minutes and seconds must be 0. there can be no appointments starting at 7:59 A.M.
     * @param appointment the appointment to check.
     * @return returns true if the appointment is within 8 A.M. - 10 P.M. E.S.T.
     */
    public boolean isWithinBusinessHours(Appointment appointment)
    {
        LocalDateTime start = TimeTools.ConvertUtcToEst(appointment.getStart()).toLocalDateTime();
        LocalDateTime end = TimeTools.ConvertUtcToEst(appointment.getEnd()).toLocalDateTime();

        //valid start hours are between 8 AM and 9:59 pm  ST since an appointment can't start at 10 PM
        if(start.getHour() >= 8 && start.getHour() < 22)
        {
            //start hours are valid, check if the ending hours are also valid. apointments may end at 10 pm.
            if(end.getHour() >= 8 && end.getHour() <= 22)
            {
                if(end.getHour() == 22)
                {
                    if(end.getMinute() != 0 || end.getSecond() != 0)
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * getter for the appointmentId.
     * @return returns appointmentId.
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * setter for appointmentId.
     * @param appointmentId the value to set appointmentId to.
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Getter for title.
     * @return returns title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for title.
     * @param title the title to set the value to.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for description.
     * @return returns description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * setter for description.
     * @param description the value to set description to.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * getter for location.
     * @return returns location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * setter for location.
     * @param location the value to set location to.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getter for type.
     * @return returns the appointment type.
     */
    public String getType() {
        return type;
    }

    /**
     * setter for type.
     * @param type sets the appointment type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * getter for the Appointment start time.
     * @return returns start.
     */
    public Timestamp getStart() {
        return start;
    }

    /**
     * setter for start.
     * @param start sets start to the new value.
     */
    public void setStart(Timestamp start) {
        this.start = start;
    }

    /**
     * getter for the Appointment End time.
     * @return returns end.
     */
    public Timestamp getEnd() {
        return end;
    }

    /**
     * setter for end.
     * @param end the value to set end to.
     */
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    /**
     * getter for the create date of the Appointment.
     * @return returns createDate.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * setter for the createDate.
     * @param createDate the value to set createDate to.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * getter for createdBy.
     * @return returns createdBy.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * setter for createdBy.
     * @param createdBy the value to set createdBy to.
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * getter for lastUpdate.
     * @return returns lastUpdate.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * setter for lastUpdate.
     * @param lastUpdate the value to set lastUpdate to.
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * getter for lastUpdateBy.
     * @return returns lastUpdatedBy.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * setter for lastUpdatedBy.
     * @param lastUpdatedBy the value to set lastUpdatedBy to.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * getter for the customerId.
     * @return returns customerId.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * setter for the customerId
     * @param customerId the value to set customerId to.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * getter for userId.
     * @return returns userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * setter for userId
     * @param userId the value to set userId to
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * getter for contactId.
     * @return returns contactId.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * setter for contactId
     * @param contactId the value to set contactId to.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
