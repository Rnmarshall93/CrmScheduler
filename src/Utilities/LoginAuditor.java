package Utilities;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.security.AccessControlException;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Utility class that writes logins to the textfile "login_activity.txt". Because file IO tends to produce errors, I caught more than just the basic exception being thrown.
 */
public class LoginAuditor {
    /**
     * Writes a login attempt to the login_activity.txt file
     * @param username the username of the attempted login
     * @param successful if the attempt was successful
     */
    public static void RecordLogin(String username, boolean successful) {
        try
        {
            File logFile = new File("login_activity.txt");
            if(!logFile.isFile())
            {
                if(!logFile.createNewFile())
                {
                    throw new Exception("couldn't create security log file. Please check system read write access.");
                }
            }
            else
            {
                FileOutputStream fileOutputStream = new FileOutputStream(logFile.getName(),true);
                fileOutputStream.write(("User attempted login : " + username + " / " + "Date: " + Timestamp.from(Instant.now()) + " (UTC) / was successful? : " + successful + "\n").getBytes());
                fileOutputStream.close();
            }
        }
        catch (AccessDeniedException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage());
            alert.showAndWait();
        }
        catch (AccessControlException ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage());
            alert.showAndWait();
        }
        catch (IOException ex )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage());
            alert.showAndWait();
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage());
            alert.showAndWait();
        }


    }

}
