package DAO;

import model.User;

import java.util.ArrayList;

/**
 * Interface for the User DAO.
 */
public interface IUserDao {
    /**
     * Adds a user.
     * @param user the User to add.
     */
    void addUser(User user);

    /**
     * Gets a user
     * @param username the username of the user.
     * @param password the passsword of the user.
     * @return a user if a matching user is found.
     */
    User getUser(String username, String password);
    /**
     * returns an ArrayList of all Users.
     * @return returns an ArrayList of Users.
     */
    ArrayList<User> getAllUsers();

    /**
     * updates a user with a set username and password.
     * @param userId the userId to get the matching User.
     * @param newUser the newUser information.
     */
    void updateUser(int userId, User newUser);
    /**
     * Deletes a user
     * @param userId the userId of the user to delete
     */
    void deleteUser(int userId);
}
