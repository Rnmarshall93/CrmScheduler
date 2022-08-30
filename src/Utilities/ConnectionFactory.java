package Utilities;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * Provides connections on demand without the need for instantiating a class to access it. Built with DAO in mind, so it could be extended to get other types of connections other than a SQL database.
 */
public class ConnectionFactory {


    /**
     * gets the sql database exception. Displays error messages in english or french depending on the user's language setting.
     * @return returns the Connection object for use in a Sql database connection.
     */
    public static Connection getSqlConnection()
    {



        try {
            InputStream secretFile = new FileInputStream("secrets");
            Properties secretProps = new Properties();
            secretProps.load(secretFile);

            final String hostString = secretProps.getProperty("hostString");
            final String dbName = secretProps.getProperty("dbName");
            final String dbUser = secretProps.getProperty("dbUser");
            final String dbPass = secretProps.getProperty("dbPass");

            System.out.println(hostString);
            System.out.println(dbName);
            System.out.println(dbUser);
            System.out.println(dbPass);

            Connection con = DriverManager.getConnection("jdbc:mysql://" + hostString + "/" + dbName, dbUser, dbPass);
            return con;
        }
        catch(Exception ex)
        {

            Utilities.LanguageSettings languageSettings = Utilities.LanguageSettings.getInstance();

            String alertTitle = "";
            String alertMessage = "";
            if(languageSettings.getSystemLanguage() == "english")
            {
                alertTitle = "Database connection problem";
                alertMessage = "There appears to be an issue with the database connection. Please ensure the service is enabled and running. If this doesn't resolve the issue," +
                        "please check your connection string.";
            }
            else if(languageSettings.getSystemLanguage() == "french")
            {
                alertTitle = "problème de connexion à la base de données";
                alertMessage = "Il semble y avoir un problème avec la connexion à la base de données. Veuillez vous assurer que le service est activé et en cours d'exécution.";
            }

            Alert alert = new Alert(Alert.AlertType.ERROR, alertMessage , ButtonType.OK);
            alert.setTitle(alertTitle);
            alert.showAndWait();
            return null;
        }
    }


}
