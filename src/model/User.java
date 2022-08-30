package model;


import java.sql.Date;

/**
 * The class for the User Model.
 */
public class User {

    /**
     * The unique Id that identifies a user.
     */
    private int userId;
    /**
     * the user's username.
     */
    private String userName;
    /**
     * the password for the user
     */
    private String password;
    /**
     * the createdDate of the user object
     */
    private Date createDate;
    /**
     * the creator of the user.
     */
    private String createdBy;
    /**
     * the last time the user was updated.
     */
    private Date lastUpdate;

    /**
     * the last user to update this user.
     */
    private String lastUpdatedBy;

    /**
     * getter for the userId.
     * @return userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * setter for the userId.
     * @param userId the value to set userId to.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * getter for the userName.
     * @return userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * setter for the userName.
     * @param userName the value to set userName to.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * getter for password.
     * @return password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password.
     * @param password the value to set password to.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for the createDate of the user.
     * @return createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * setter for the createDate of the user.
     * @param createDate the value to set createDate to.
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
     * Override for the toString method.
     * @return "User is null" if the user was null. If not, returns the userName of the user.
     */
    @Override
    public String toString() {
        if(this == null)
            return "User is null";
        else
            return this.getUserName();
    }

    /**
     * setter for lastUpdatedBy.
     * @param lastUpdatedBy the value to set lastUpdatedBy to.
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }


}
