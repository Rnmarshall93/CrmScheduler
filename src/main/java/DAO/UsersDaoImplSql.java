package DAO;
import Utilities.ConnectionFactory;
import Utilities.LanguageSettings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementation of a User DAO using SQL.
 */
public class UsersDaoImplSql implements IUserDao {

    /**
     * Adds a user to the database. This method is implemented but never used.
     * @param newUser the user to add to the database.
     */
    @Override
    public void addUser(User newUser) {

        Connection dbConn = ConnectionFactory.getSqlConnection();

        String query = "INSERT INTO users (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) " +
                "values (?,?,?,?,?,?)";

        try
        {
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);

            preparedStatement.setString(1, newUser.getUserName());
            preparedStatement.setString(2 ,newUser.getPassword());
            preparedStatement.setDate(3, newUser.getCreateDate());
            preparedStatement.setString(4, newUser.getLastUpdatedBy());
            preparedStatement.setDate(5, newUser.getLastUpdate());
            preparedStatement.setString(6,newUser.getLastUpdatedBy());


            preparedStatement.execute();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }

    }

    /**
     * gets a user from the database. Translates any error messages to english or french depending on the users language setting.
     * @param username the username of the user.
     * @param password the passsword of the user.
     * @return returns a matching user if both the user's username and password match.
     */
    @Override
    public User getUser(String username, String password) {
            try{

                Connection dbConn = ConnectionFactory.getSqlConnection();

                String query = "SELECT User_id, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By " +
                        "from users where users.User_Name = ? AND users.Password = ?";


                PreparedStatement preparedStatement = dbConn.prepareStatement(query);

                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt(1));
                    user.setUserName(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setCreateDate(resultSet.getDate(4));
                    user.setCreatedBy(resultSet.getString(5));
                    user.setLastUpdate(resultSet.getDate(6));
                    user.setLastUpdatedBy(resultSet.getString(7));
                    dbConn.close();
                    return user;
                }
                else
                {
                    LanguageSettings languageSettings = LanguageSettings.getInstance();
                    if(languageSettings.getSystemLanguage() == "english")
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No accounts found, please check your username and password.", ButtonType.OK);
                        alert.setTitle("No accounts found");
                        alert.showAndWait();
                    }
                    else if(languageSettings.getSystemLanguage() == "french")
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun compte trouvé, veuillez vérifier votre nom d'utilisateur et votre mot de passe.", ButtonType.OK);
                        alert.setTitle("Aucun compte trouvé");
                        alert.showAndWait();
                    }
                    //default to english
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No accounts found, please check your username and password.", ButtonType.OK);
                        alert.setTitle("No accounts found");
                        alert.showAndWait();
                    }
                }
                dbConn.close();
            }
            catch(Exception ex) {
                if(LanguageSettings.getInstance().getSystemLanguage() == "english")
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't get the user because the database couldn't be established. ", ButtonType.OK);
                    alert.showAndWait();
                }
                else
                {

                    Alert alert = new Alert(Alert.AlertType.ERROR, "Impossible d'obtenir l'utilisateur car la base de données n'a pas pu être établie.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
            return null;
        }

    /**
     * Gets all users from the database. Translates any error messages to english or french depending on the users language setting. This method is implemented but unused.
     * @return returns an ArrayList of Users from the database.
     */
    @Override
    public ArrayList<User> getAllUsers() {
        try {


            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "SELECT User_id, User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By from users";

            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            ArrayList<User> foundUsers = new ArrayList<>();
            while(resultSet.next())
            {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setUserName(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setCreateDate(resultSet.getDate(4));
                user.setCreatedBy(resultSet.getString(5));
                user.setLastUpdate(resultSet.getDate(6));
                user.setLastUpdatedBy(resultSet.getString(7));
                foundUsers.add(user);
            }
            dbConn.close();
            return foundUsers;
        }
        catch(Exception ex)
        {
            LanguageSettings languageSettings = LanguageSettings.getInstance();
            if(languageSettings.getSystemLanguage() == "english")
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error with the database, please contact your system administrator", ButtonType.OK);
                alert.setTitle("No accounts found");
                alert.showAndWait();
            }
            else if(languageSettings.getSystemLanguage() == "french")
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a eu une erreur avec la base de données, veuillez contacter votre administrateur système", ButtonType.OK);
                alert.setTitle("erreur de la base de données");
                alert.showAndWait();
            }
            //default to english
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error with the database, please contact your system administrator", ButtonType.OK);
                alert.setTitle("No accounts found");
                alert.showAndWait();
            }
            return null;
        }
    }

    /**
     * Updates a user in the database. Translates any error messages to english or french depending on the users language setting. This method is implemented but not used.
     * @param userToUpdateID the Id the User to update.
     * @param updatedUser The updated User information.
     */
    @Override
    public void updateUser(int userToUpdateID, User updatedUser) {
        try {

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "update users set User_Name = ?, Password = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?" +
                    "where User_ID = ?";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setString(1 ,updatedUser.getUserName());
            preparedStatement.setString(2 ,updatedUser.getPassword());
            preparedStatement.setDate(3, updatedUser.getCreateDate());
            preparedStatement.setString(4, updatedUser.getLastUpdatedBy());
            preparedStatement.setDate(5, updatedUser.getLastUpdate());
            preparedStatement.setString(6,updatedUser.getLastUpdatedBy());
            preparedStatement.setInt(7,userToUpdateID);

            preparedStatement.execute();
            dbConn.close();

        }
        catch (Exception ex)
        {
            LanguageSettings languageSettings = LanguageSettings.getInstance();
            if(languageSettings.getSystemLanguage() == "english")
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error with the database, please contact your system administrator", ButtonType.OK);
                alert.setTitle("No accounts found");
                alert.showAndWait();
            }
            else if(languageSettings.getSystemLanguage() == "french")
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a eu une erreur avec la base de données, veuillez contacter votre administrateur système", ButtonType.OK);
                alert.setTitle("erreur de la base de données");
                alert.showAndWait();
            }
            //default to english
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error with the database, please contact your system administrator", ButtonType.OK);
                alert.setTitle("No accounts found");
                alert.showAndWait();
            }
        }
    }

    /**
     * Deletes a user from the database. Translates any error messages to english or french depending on the users language setting. This method is implemented but unused.
     * @param foundUserID the UserId used to select the User that will be deleted.
     */
    @Override
    public void deleteUser(int foundUserID) {
        try {

            Connection dbConn = ConnectionFactory.getSqlConnection();

            String query = "Delete from users where ? = User_ID";
            PreparedStatement preparedStatement = dbConn.prepareStatement(query);
            preparedStatement.setInt(1, foundUserID);

            preparedStatement.execute();
            dbConn.close();

        }
        catch (Exception ex) {
            LanguageSettings languageSettings = LanguageSettings.getInstance();
            if(languageSettings.getSystemLanguage() == "english")
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error with the database, please contact your system administrator", ButtonType.OK);
                alert.setTitle("No accounts found");
                alert.showAndWait();
            }
            else if(languageSettings.getSystemLanguage() == "french")
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Il y a eu une erreur avec la base de données, veuillez contacter votre administrateur système", ButtonType.OK);
                alert.setTitle("erreur de la base de données");
                alert.showAndWait();
            }
            //default to english
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR, "There was an error with the database, please contact your system administrator", ButtonType.OK);
                alert.setTitle("No accounts found");
                alert.showAndWait();
            }
        }

    }
}
