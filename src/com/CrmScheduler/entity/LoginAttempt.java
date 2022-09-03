package com.CrmScheduler.entity;

import java.sql.Timestamp;

/**
 * model for a LoginAttempt. There was consideration for creating a com.CrmScheduler.DAO for this model, but I didn't want to murk up the requirements for the project by doing so, This model was intended to
 * be used for greater things such as building reports, but that was a misunderstanding of the requirements on my part. This class is implemented but unused.
 */
public class LoginAttempt {
    /**
     * the username of the attempted login.
     */
    private String username;
    /**
     * the timestamp of the attempted login.
     */
    private Timestamp time;
    /**
     * the result of the login.
     */
    private boolean successful;

    /**
     * getter for username.
     * @return username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * setter for username.
     * @param username the value to set username to.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * the getter for the time the login occured.
     * @return time.
     */
    public Timestamp getTime() {
        return time;
    }

    /**
     * the setter for the time the login occured.
     * @param time the value to set time to.
     */
    public void setTime(Timestamp time) {
        this.time = time;
    }

    /**
     * getter for successful.
     * @return successful.
     */
    public boolean isSuccessful() {
        return successful;
    }

    /**
     * setter for successful.
     * @param successful the value to set successful to.
     */
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
