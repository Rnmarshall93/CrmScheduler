package com.CrmScheduler.DAO;

import com.CrmScheduler.HibernateConf;
import com.CrmScheduler.HelperUtilties.LanguageSettings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import com.CrmScheduler.entity.CrmUser;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;


/**
 * Implementation of a User com.CrmScheduler.DAO using SQL.
 */
public class UsersDaoImplSql implements IUserDao {



    /**
     * Adds a user to the database. This method is implemented but never used.
     *
     * @param newUser the user to add to the database.
     */
    @Override
    public void addUser(CrmUser newUser) {
        //todo implement in hibernate
    }

    /**
     * gets a user from the database. Translates any error messages to english or french depending on the users language setting.
     *
     * @param username the username of the user.
     * @param password the passsword of the user.
     * @return returns a matching user if both the user's username and password match.
     */


    @Override
    public CrmUser getUser(String username, String password) {

        try
        {
            Session session = HibernateConf.getSessionFactory().openSession();
            session.beginTransaction();

            Query q = session.createQuery("FROM CrmUser u WHERE u.password = :pwd AND u.userName = :username");


            q.setParameter("pwd", password);
            q.setParameter("username", username);
            CrmUser user = (CrmUser) q.getSingleResult();

            session.getTransaction().commit();
            session.close();

            if (user != null) {
                return user;
            }

            else {
                LanguageSettings languageSettings = LanguageSettings.getInstance();
                if (languageSettings.getSystemLanguage() == "english") {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No accounts found, please check your username and password.", ButtonType.OK);
                    alert.setTitle("No accounts found");
                    alert.showAndWait();
                } else if (languageSettings.getSystemLanguage() == "french") {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun compte trouvé, veuillez vérifier votre nom d'utilisateur et votre mot de passe.", ButtonType.OK);
                    alert.setTitle("Aucun compte trouvé");
                    alert.showAndWait();
                }
                //default to english
                else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No accounts found, please check your username and password.", ButtonType.OK);
                    alert.setTitle("No accounts found");
                    alert.showAndWait();
                }
            }
        }

            catch(Exception ex)
    {
        if (LanguageSettings.getInstance().getSystemLanguage() == "english") {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't get the user because the database couldn't be established. ", ButtonType.OK);
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Impossible d'obtenir l'utilisateur car la base de données n'a pas pu être établie.", ButtonType.OK);
            alert.showAndWait();
        }
    }
            return null;
}

    /**
     * Gets all users from the database. Translates any error messages to english or french depending on the users language setting. This method is implemented but unused.
     *
     * @return returns an ArrayList of Users from the database.
     */
    @Override
    public ArrayList<CrmUser> getAllUsers() {
        //todo implement in hibernate
        return null;
    }

    /**
     * Updates a user in the database. Translates any error messages to english or french depending on the users language setting. This method is implemented but not used.
     *
     * @param userToUpdateID the Id the User to update.
     * @param updatedUser    The updated User information.
     */
    @Override
    public void updateUser(int userToUpdateID, CrmUser updatedUser) {
            }

    /**
     * Deletes a user from the database. Translates any error messages to english or french depending on the users language setting. This method is implemented but unused.
     *
     * @param foundUserID the UserId used to select the User that will be deleted.
     */
    @Override
    public void deleteUser(int foundUserID) {
        //todo implement in hibernate

        }
}
