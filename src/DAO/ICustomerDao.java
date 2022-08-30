package DAO;

import model.Customer;

import java.util.ArrayList;

/**
 * Interface for a Customer DAO
 */
public interface ICustomerDao {

    /**
     * Adds a customer
     * @param customer the customer to add
     */
    void addCustomer(Customer customer);

    /**
     * gets a customer
     * @param customerId the customerId of the customer to get
     * @return returns the customer with a matching customerId
     */
    Customer getCustomer(int customerId);

    /**
     * gets all customers
     * @return returns an ArrayList of Customers
     */
    ArrayList<Customer> getAllCustomers();

    /**
     * Updates a customer
     * @param userId the Id of the User to update
     * @param newCustomer the updated customer information
     */
    void updateCustomer(int userId, Customer newCustomer);

    /**
     * Deletes a customer
     * @param userId the Id of the user to delete
     */
    void deleteCustomer(int userId);
}
