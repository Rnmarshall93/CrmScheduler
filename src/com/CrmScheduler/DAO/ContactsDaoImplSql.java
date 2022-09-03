package com.CrmScheduler.DAO;

import com.CrmScheduler.HibernateConf;
import com.CrmScheduler.entity.FirstLevelDivision;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.CrmScheduler.entity.Contact;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Contact Dao implemented in sql
 */
public class ContactsDaoImplSql implements IContactsDao {
    /**
     * Adds a new contact to the database
     * @param newContact the new contact to add to the database.
     */
    @Override
    public void addContact(Contact newContact) {

        try {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(newContact);

            session.getTransaction().commit();
            session.close();
        }
        catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * Gets a contact from the database based on their contactId
     * @param contactId the contactId to find.
     * @return returns the found contact
     */
    @Override
    public Contact getContact(int contactId) {
        try{

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            Contact foundContact = session.get(Contact.class, contactId);

            session.getTransaction().commit();
            session.close();
            return foundContact;
        }

        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();        }
        return null;

    }

    /**
     * Gets all contacts from the database
     * @return returns all contacts from the database in an ArrayList of Contacts
     */
    @Override
    public ArrayList<Contact> getAllContacts() {
        try {


            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            ArrayList<Contact> foundContacts = new ArrayList<>();
            Query query = session.createQuery("from Contact");
            foundContacts.addAll(query.list());

            session.getTransaction().commit();
            session.close();
            return foundContacts;
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }

    /**
     * Updates a contact in the database
     * @param contactId the contactId of the appointment to update.
     * @param newContact the updated contact information.
     */
    @Override
    public void updateContacts(int contactId, Contact newContact) {
        try {

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.update(newContact);

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
     * deletes a contact from the database
     * @param contactId the contactId of the contact to be deleted.
     */
    @Override
    public void deleteContacts(int contactId) {

        try {


            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            Contact foundContact = session.get(Contact.class, contactId);
            session.delete(foundContact);

            session.getTransaction().commit();
            session.close();
        }

        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }
}
