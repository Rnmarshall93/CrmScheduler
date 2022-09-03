package com.CrmScheduler.entity;

import javax.persistence.*;

/**
 * The model class for a Contact
 */
@Entity
@Table(name = "contacts")
public class Contact {


    /**
     * the unique Id that identified the contact.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Contact_ID")
    private int contactId;
    /**
     * the name of the contact
     */
    @Column(name = "Contact_Name")
    private String contactName;
    /**
     * The email of the contact
     */
    @Column(name = "Email")
    private String email;

    /**
     * getter for the contact Id
     * @return returns contactId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * setter for the contact id.
     * @param contactId sets contactId to the provided value.
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * getter for contact name.
     * @return returns contactName.
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * setter for contactName.
     * @param contactName sets the contact name to the provided value.
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * getter for the email.
     * @return returns email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * seter for email.
     * @param email sets email to the provided value.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
