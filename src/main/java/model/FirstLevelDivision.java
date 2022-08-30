package model;


import java.sql.Date;

/**
 * Model class representing a FirstLevelDivision
 */
public class FirstLevelDivision {

    /**
     * the unique Id of the FirstLevelDivision.
     */
    private int divisionId;
    /**
     * the name of the division
     */
    private String division;
    /**
     * the create date of the FirstLevelDivision object.
     */
    private Date createDate;
    /**
     * the user who created the FirstLevelDivision object.
     */
    private String createdBy;
    /**
     * the date of the last update to this object.
     */
    private Date lastUpdate;
    /**
     * the username of the last user to update this object.
     */
    private String lastUpdatedBy;
    /**
     * the unique Id for the county associated with the FirstLevelDivision.
     */
    private int countyId;

    /**
     * getter for divisionId.
     * @return returns divisionId.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * setter for divisionId.
     * @param divisionId the value to set divisionId to.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * getter for division.
     * @return division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * setter for division.
     * @param division the value to set division to.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * getter for createDate
     * @return createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * setter for createDate
     * @param createDate the value to set createDate to
     */
    public void setCreateDate(Date createDate) {
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
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * setter for lastUpdate.
     * @param lastUpdate the value to set lastUpdate to.
     */
    public void setLastUpdate(Date lastUpdate) {
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
     * @param lastUpdatedBy the value to set lastUpdatedBy to.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * getter for countryId.
     * @return countryId.
     */
    public int getCountyId() {
        return countyId;
    }

    /**
     * setter for countryId.
     * @param countyId countryId.
     */
    public void setCountyId(int countyId) {
        this.countyId = countyId;
    }
}
