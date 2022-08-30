package model;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * The model class for a Customer.
 */
public class Customer {

    /**
     * the unique Id for the Customer.
     */
    private int customerId;
    /**
     * the name for the customer
     */
    private String customerName;
    /**
     * the address for the customer
     */
    private String address;
    /**
     * the postalCode for the customer.
     */
    private String postalCode;
    /**
     * the phone number for the customer.
     */
    private String phone;
    /**
     * the createDate of the Customer object.
     */
    private Timestamp createDate;
    /**
     * the name of the user who created the Customer object.
     */
    private String createdBy;
    /**
     * the last updated time of the Customer object.
     */
    private Timestamp lastUpdate;
    /**
     * The user responsible for editing the Customer object last.
     */
    private String lastUpdatedBy;
    /**
     * the unique Id for the division the customer lives in.
     */
    private int divisionId;

    /**
     * getter for customerId.
     * @return returns customerId.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * setter for customerId.
     * @param customerId returns customerId.
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * getter for customerName.
     * @return returns customerName.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * setter for customerName.
     * @param customerName the value to set customerName to.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * getter for the address.
     * @return returns address of the Customer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter for address
     * @param address the value to set address to.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * getter for postalCode
     * @return returns postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * setter for the postal code.
     * @param postalCode the value to set postalCode to.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * getter for the phone number.
     * @return returns the phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * setter for the phone number
     * @param phone the value to set phone to.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * gets the creation date of the object.
     * @return createDate
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * setter for createDate.
     * @param createDate the value to set createDate to.
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * getter for createdBy.
     * @return createdBy.
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
     * @return lastUpdate.
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
     * getter for lastUpdatedBy.
     * @return lastUpdatedBy.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * setter for lastUpdatedBy
     * @param lastUpdatedBy the value to set lastUpdatedBy to
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * gets the unique divisionId for the division the customer belongs to.
     * @return divisionId.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * setter for the unique divisionId the customer belongs to.
     * @param divisionId the value to set divisionId to.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
