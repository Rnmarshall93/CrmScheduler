package DAO;

import Utilities.ConnectionFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementation of a Customer DAO in SQL.
 */
public class ICustomerDaoImplSql implements ICustomerDao {

    /**
     * Adds a customer to the database.
     * @param newCustomer the new customer to add.
     */
    @Override
    public void addCustomer(Customer newCustomer) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "values (?,?,?,?,?,?,?,?,?)";

        try
        {
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);

            preparedStatement.setString(1, newCustomer.getCustomerName());
            preparedStatement.setString(2, newCustomer.getAddress());
            preparedStatement.setString(3, newCustomer.getPostalCode());
            preparedStatement.setString(4, newCustomer.getPhone());
            preparedStatement.setObject(5, newCustomer.getCreateDate());
            preparedStatement.setString(6, newCustomer.getCreatedBy());
            preparedStatement.setObject(7, newCustomer.getLastUpdate());
            preparedStatement.setString(8, newCustomer.getLastUpdatedBy());
            preparedStatement.setInt(9, newCustomer.getDivisionId());

            preparedStatement.execute();

        } catch (SQLException ex) {

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

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID " +
                    "from customers where Customer_ID = ?";


            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1,customerId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt(1));
                customer.setCustomerName(resultSet.getString(2));
                customer.setAddress(resultSet.getString(3));
                customer.setPostalCode(resultSet.getString(4));
                customer.setPhone(resultSet.getString(5));
                customer.setCreateDate(resultSet.getTimestamp(6));
                customer.setCreatedBy(resultSet.getString(7));
                customer.setLastUpdate(resultSet.getTimestamp(8));
                customer.setLastUpdatedBy(resultSet.getString(9));
                customer.setDivisionId(resultSet.getInt(10));
                dbConn.close();
                return customer;
            }
            dbConn.close();
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


            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_updated_by, Division_id from customers";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            ArrayList<Customer> foundCustomers = new ArrayList<>();
            while(resultSet.next())
            {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt(1));
                customer.setCustomerName(resultSet.getString(2));
                customer.setAddress(resultSet.getString(3));
                customer.setPostalCode(resultSet.getString(4));
                customer.setPhone(resultSet.getString(5));
                customer.setCreateDate(resultSet.getTimestamp(6));
                customer.setCreatedBy(resultSet.getString(7));
                customer.setLastUpdate(resultSet.getTimestamp(8));
                customer.setLastUpdatedBy(resultSet.getString(9));
                customer.setDivisionId(resultSet.getInt(10));
                foundCustomers.add(customer);
            }

            return foundCustomers;
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

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "update customers set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                    "where Customer_ID = ?";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setString(1 ,newCustomer.getCustomerName());
            preparedStatement.setString(2, newCustomer.getAddress());
            preparedStatement.setString(3, newCustomer.getPostalCode());
            preparedStatement.setString(4, newCustomer.getPhone());
            preparedStatement.setTimestamp(5, newCustomer.getCreateDate());
            preparedStatement.setString(6, newCustomer.getCreatedBy());
            preparedStatement.setTimestamp(7, newCustomer.getLastUpdate());
            preparedStatement.setString(8, newCustomer.getLastUpdatedBy());
            preparedStatement.setInt(9, newCustomer.getDivisionId());
            preparedStatement.setInt(10, customerId);
            preparedStatement.execute();

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

            Connection dbConn = ConnectionFactory.getSqlConnection();
            IAppointmentDao IAppointmentDao = new AppointmentDaoImplSql();
            IAppointmentDao.getAllAppointments().stream().filter(appointment -> appointment.getCustomerId() == customerId).forEach(appointment ->
                    IAppointmentDao.deleteAppointments(appointment.getContactId()));
            String query = "Delete from customers where ? = Customer_ID";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, customerId);
            preparedStatement.execute();
        }
        catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }
}
