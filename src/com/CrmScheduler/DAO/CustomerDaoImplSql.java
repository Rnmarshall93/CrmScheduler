package com.CrmScheduler.DAO;

import com.CrmScheduler.HibernateConf;
import com.CrmScheduler.entity.Contact;
import com.CrmScheduler.entity.CrmUser;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.CrmScheduler.entity.Customer;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation of a Customer com.CrmScheduler.DAO in SQL.
 */
public class CustomerDaoImplSql implements ICustomerDao {

    /**
     * Adds a customer to the database.
     * @param newCustomer the new customer to add.
     */
    @Override
    public void addCustomer(Customer newCustomer) {


        try
        {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(newCustomer);

            session.getTransaction().commit();
            session.close();

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Gets a customer from the database.
     * @param customerId the customerId of the customer to get.
     * @return returns the customer with a matching customerId.
     */
    @Override
    public Customer getCustomer(int customerId) {
        try{
            Session session = HibernateConf.getSessionFactory().openSession();
            Customer foundCustomer = session.get(Customer.class, customerId);
            session.getTransaction().commit();
            session.close();
            return foundCustomer;
        }

        catch(Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
        return null;
    }

    /**
     * Returns all customers from the database.
     * @return returns an ArrayList of Customers.
     */
    @Override
    public ArrayList<Customer> getAllCustomers() {
        try {

            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Customer");
            ArrayList<Customer> customers = new ArrayList<>();
            q.list().forEach(customer -> customers.add((Customer)customer));
            session.getTransaction().commit();
            session.close();

            return customers;
        }
        catch(Exception ex)
        {

            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
            return null;
        }
    }

    /**
     * Updates a customer in the database.
     * @param customerId the Id of the customer to update.
     * @param newCustomer the updated customer information.
     */
    @Override
    public void updateCustomer(int customerId, Customer newCustomer) {
        try {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(newCustomer);
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
     * Deletes a customer from the database.
     * @param customerId The Id of the customer to delete.
     */
    @Override
    public void deleteCustomer(int customerId) {
        try {
            Session session = HibernateConf.getSessionFactory().openSession();
            Customer foundCustomer = session.get(Customer.class, customerId);
            session.delete(foundCustomer);
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
