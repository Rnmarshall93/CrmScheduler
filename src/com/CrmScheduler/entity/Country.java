package com.CrmScheduler.entity;


import javax.persistence.*;
import java.sql.Date;

/**
 * The model class for a Country
 */
@Entity
@Table(name = "countries")
public class Country {

    /**
     * the unique Id that identifies the country.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Country_ID")
    private int countryId;
    /**
     * the name of the country.
     */
    @Column(name = "Country")
    private String country;
    /**
     * the object creation date of the Country.
     */
    @Column(name = "Create_Date")
    private Date createDate;
    /**
     * the user who created the object of the country.
     */
    @Column(name = "Created_By")
    private String createdBy;
    /**
     * the last time this Country object was last updated.
     */
    @Column(name = "Last_Update")
    private Date lastUpdate;
    /**
     * the user who caused the last update.
     */
    @Column(name = "Last_Updated_By")
    private String lastUpdatedBy;

    /**
     * getter for the countryId.
     * @return returns the countryId.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * setter for countryId.
     * @param countryId sets countryId to the provided value.
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * getter for the country (Name).
     * @return returns the country (Name).
     */
    public String getCountry() {
        return country;
    }

    /**
     * setter for the country.
     * @param country sets the Country name to the provided value.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * getter for the createDate.
     * @return returns createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * setter for the createDate.
     * @param createDate the value to set createDate to.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * getter for createdBy.
     * @return gets createdBy.
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
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * setter for lastUpdate.
     * @param lastUpdate the value provided to update lastUpdate to.
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * getter for lastUpdatedBy.
     * @return returns lastUpdatedBy.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * setter for lastUpdatedBy
     * @param lastUpdatedBy the value to set lastUpdatedBy to.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
