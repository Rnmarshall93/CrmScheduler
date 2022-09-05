package com.CrmScheduler.entity;

import com.CrmScheduler.DAO.IContactsDao;
import com.CrmScheduler.DAO.ContactsDaoImplSql;
import com.CrmScheduler.HelperUtilties.TimeTools;
import javafx.collections.ObservableList;
import com.CrmScheduler.controller.AppointmentManagerController;

/**
 * Extended version of the Appointment model. Allows for keeping the contact's name without knowing anything else about the contact. Useful for filtering lists using labmdas and
 * displaying information in a table. This class also maps 1:1 to the table used in AppointmentManagerController.
 * @see AppointmentManagerController#buildTable(ObservableList)
 */
public class DetailedAppointment extends Appointment {


    /**
     * the name of the contact matching the contactId of the appointment.
     */
    private String contactName;

    /**
     * getter for the contact name.
     * @return contactName.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Constructor for the DetailedAppointment. The only way the creation of a detailedAppointment is allowed is with an appointment parameter.
     * The DetailedAppointment is copied from an existing Appointment and a com.CrmScheduler.DAO is used to get the contact's name using their already known contactId.
     * @param baseAppointment the appointment to build the DetailedAppointment from.
     */
    public DetailedAppointment(Appointment baseAppointment)
    {
        TimeTools timeTools = new TimeTools();

        this.setAppointmentId(baseAppointment.getAppointmentId());
        this.setTitle(baseAppointment.getTitle());
        this.setDescription(baseAppointment.getDescription());
        this.setLocation(baseAppointment.getLocation());
        this.setType(baseAppointment.getType());
        this.setStart(timeTools.ConvertUtcToSystemTime(baseAppointment.getStart()));
        this.setEnd(timeTools.ConvertUtcToSystemTime(baseAppointment.getEnd()));
        this.setCreateDate(baseAppointment.getCreateDate());
        this.setCreatedBy(baseAppointment.getCreatedBy());
        this.setLastUpdate(baseAppointment.getLastUpdate());
        this.setLastUpdatedBy(baseAppointment.getLastUpdatedBy());
        this.setCustomerId(baseAppointment.getCustomerId());
        this.setUserId(baseAppointment.getUserId());
        this.setContactId(baseAppointment.getContactId());
        IContactsDao IContactsDao = new ContactsDaoImplSql();
        Contact associatedContact = IContactsDao.getContact(this.getContactId());
        this.contactName = associatedContact.getContactName();
    }
}
