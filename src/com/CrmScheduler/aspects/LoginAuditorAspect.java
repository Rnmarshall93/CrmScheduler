package com.CrmScheduler.aspects;

import com.CrmScheduler.entity.CrmUser;
import javafx.scene.control.Alert;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.time.Instant;
@Component
@Aspect
public class LoginAuditorAspect {


    /**
     * This method logs information any time a user logs in to the login_activity.txt file. It records the username, timestamp, and,
     * result of the attempt.
     * @param joinPoint Contains information about method signature, args, and other misc info.
     * @param result    the user being returned. If the user wasn't found, we will be dealing with a null value.
     */

    @AfterReturning(value = "execution(public * getUser(..))",
            returning = "result")
    public void doLogging(JoinPoint joinPoint, CrmUser result) {


        try {
            File logFile = new File("login_activity.txt");
            if (!logFile.isFile()) {
                if (!logFile.createNewFile()) {
                    throw new Exception("couldn't create security log file. Please check system read/write access.");
                }
            } else {
                FileOutputStream fileOutputStream = new FileOutputStream(logFile.getName(), true);
                String username = joinPoint.getArgs()[0].toString();
                boolean loginSuccess;
                if (result != null)
                    loginSuccess = true;
                else
                    loginSuccess = false;

                fileOutputStream.write(("User attempted login : " + username + " / " + "Date: " + Timestamp.from(Instant.now()) + " (UTC) / was successful? : " + loginSuccess + "\n").getBytes());
                fileOutputStream.close();
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage());
            alert.showAndWait();
        }
    }




}