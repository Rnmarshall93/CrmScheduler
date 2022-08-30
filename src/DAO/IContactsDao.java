package DAO;

import model.Contact;

import java.util.ArrayList;

/**
 * Interface for a Contact Dao.
 */
public interface IContactsDao {

    /**
     * Adds a contact.
     * @param contact the contact to add.
     */
    void addContact(Contact contact);

    /**
     * Gets a contact
     * @param contactId the contactId to find.
     * @return returns the contact.
     */
    Contact getContact(int contactId);

    /**
     * gets all contacts.
     * @return returns an ArrayList of Contacts.
     */
    ArrayList<Contact> getAllContacts();

    /**
     * Update a contact
     * @param contactId the contactId of the appointment to update.
     * @param newContact the updated contact information.
     */
    void updateContacts(int contactId, Contact newContact);

    /**
     * Deletes a contact
     * @param contactId the contactId of the contact to be deleted.
     */
    void deleteContacts(int contactId);

}
