package com.CrmScheduler.entity;

import com.CrmScheduler.HelperUtilties.TimeTools;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.function.BiPredicate;
import java.util.function.Predicate;


/**
 * The Model class for appointment.
 */
@Entity
@Table(name = "appointments")
public class Appointment {

    /**
     ** Determines if the newAppointment is after the existingAppointment. Returns true if the newAppointment is after the existingAppointment.
     */
    @Transient
    public BiPredicate<Timestamp,Timestamp> isAfter = (newAppointment, existingAppointment) -> newAppointment.compareTo(existingAppointment) >= 0;
    /**
     * Determines if the newAppointment is before the existingAppointment/ Returns true if the newAppointment is before the existingAppointment
     */
    @Transient
    public BiPredicate<Timestamp,Timestamp> isBefore = (newAppointment, existingAppointment) -> newAppointment.compareTo(existingAppointment) <= 0;
    /**
     * Predicate used to validate that an appointments Start occurs after its end.
     */
    @Transient
    public Predicate<Appointment> endsAfterStart = (appointment -> appointment.getEnd().before(appointment.getStart()));
    /**
     * predicate that returns true if the appointment's start and end date are at the same time.
     */
    @Transient
    public Predicate<Appointment> startAndEndSame = (appointment -> appointment.getStart().equals(appointment.getEnd()));

    /**
     * Determines if the user already has an existing appointment scheduled that would be in the same time range.
     * @param newAppointment The new appointment being created
     * @param existingAppointment The existing appointment to check against
     * @return true if the user does not have a conflicting appointment. False if the user does.
     */
    public boolean isConflictingWithAnotherAppointment(Appointment newAppointment, Appointment existingAppointment)
    {
        if(newAppointment.isAfter.test(newAppointment.getStart(), existingAppointment.getStart()))
            if(newAppointment.isAfter.test(newAppointment.getEnd(), existingAppointment.getEnd()))
                if(newAppointment.getCustomerId() == existingAppointment.getCustomerId())
                return true;
        return false;
    }
    /**
     * the unique ID of the appointment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Appointment_ID")
    private int appointmentId;
    /**
     * the title of the appointment.
     */
    @Column(name = "Title")
    private String title;
    /**
     * the description of the appointment.
     */
    @Column(name = "Description")
    private String description;
    /**
     * the location the appointment is scheduled at.
     */
    @Column(name = "Location")
    private String location;
    /**
     * The type of appointment.
     */
    @Column(name = "Type")
    private String type;
    /**
     * The start time of the appointment.
     */
    @Column(name = "Start")
    private Timestamp start;
    /**
     * The End time of the appointment.
     */
    @Column(name = "End")
    private Timestamp end;
    /**
     * The create date of the timestamp.
     */
    @Column(name = "Create_Date")
    private Timestamp createDate;
    /**
     * The original user who created the Appointment.
     */
    @Column(name = "Created_By")
    private String createdBy;
    /**
     * The last time the user was updated.
     */
    @Column(name = "Last_Update")
    private Timestamp lastUpdate;
    /**
     * The last User to update the Appointment.
     */
    @Column(name="Last_Updated_By")
    private String lastUpdatedBy;
    /**
     * The unique CustomerID (for the customer) for the appointment.
     */
    @Column(name = "Customer_ID")
    private int customerId;
    /**
     * The unique userId of the user editing the Appointment.
     */
    @Column(name = "User_ID")
    private int userId;
    /**
     * The unique ContactId for the appointment's contact.
     */
    @Column(name = "Contact_ID")
    private int contactId;

    /**
     * helper function that determins if an appointment is within business hours. A appointment may be scheduled and end as late as 10, under the business case
     * that an emergency appointment may need to be made and worked on. While appointments may end at 10 P.M., they must end at 10 P.M. sharp, so the minutes and seconds must be 0. there can be no appointments starting at 7:59 A.M.
     * @param appointment the appointment to check.
     * @return returns true if the appointment is within 8 A.M. - 10 P.M. E.S.T.
     */
    public boolean isWithinBusinessHours(Appointment appointment)
    {
        TimeTools timeTools = new TimeTools();

        LocalDateTime start = timeTools.ConvertUtcToEst(appointment.getStart()).toLocalDateTime();
        LocalDateTime end = timeTools.ConvertUtcToEst(appointment.getEnd()).toLocalDateTime();

        //valid start hours are between 8 AM and 9:59 pm  ST since an appointment can't start at 10 PM
        if(start.getHour() >= 8 && start.getHour() < 22)
        {
            //start hours are valid, check if the ending hours are also valid. apointments may end at 10 pm.
            if(end.getHour() >= 8 && end.getHour() <= 22)
            {
                if(end.getHour() == 22)
                {
                    return end.getMinute() == 0 && end.getSecond() == 0;
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
