package model;

/**
 * The model class for a Contact
 */
public class Contact {


    /**
     * the unique Id that identified the contact.
     */
    private int contactId;
    /**
     * the name of the contact
     */
    private String contactName;
    /**
     * The email of the contact
     */
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
