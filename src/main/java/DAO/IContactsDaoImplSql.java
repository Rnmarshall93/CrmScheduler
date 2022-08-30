package DAO;

import Utilities.ConnectionFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Contact;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Contact Dao implemented in sql
 */
public class IContactsDaoImplSql implements IContactsDao {
    /**
     * Adds a new contact to the database
     * @param newContact the new contact to add to the database.
     */
    @Override
    public void addContact(Contact newContact) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        String query = "INSERT INTO contacts (Contact_Name, Email) " +
                "values (?,?)";

        try
        {
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setString(1, newContact.getContactName());
            preparedStatement.setString(2, newContact.getEmail());
            preparedStatement.execute();
        } catch (SQLException ex) {

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

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Contact_Id, Contact_Name, Email" +
                    " from Contacts where Contact_ID = ?";


            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, contactId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Contact contact = new Contact();
                contact.setContactId(resultSet.getInt(1));
                contact.setContactName(resultSet.getString(2));
                contact.setEmail(resultSet.getString(3));
                dbConn.close();
                return contact;
            }
            dbConn.close();
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


            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Contact_Id, Contact_Name, Email " +
                    "from Contacts";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            ArrayList<Contact> foundContacts = new ArrayList<>();
            while(resultSet.next())
            {
                Contact contact = new Contact();
                contact.setContactId(resultSet.getInt(1));
                contact.setContactName(resultSet.getString(2));
                contact.setEmail(resultSet.getString(3));
                foundContacts.add(contact);
            }
            dbConn.close();

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

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "update Contacts set Contact_ID = ?, Contact_Name = ?, Email = ? " +
                    "where Contact_ID = ?";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, newContact.getContactId());
            preparedStatement.setString(2, newContact.getContactName());
            preparedStatement.setString(3, newContact.getEmail());
            preparedStatement.setInt(4, contactId);
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
     * deletes a contact from the database
     * @param contactId the contactId of the contact to be deleted.
     */
    @Override
    public void deleteContacts(int contactId) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        try{
            //delete all associated appointments
            IAppointmentDao IAppointmentDao = new AppointmentDaoImplSql();
            String query = "Delete from Contacts where ? = Contact_ID";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, contactId);
            preparedStatement.execute();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }
}
