package com.CrmScheduler.controller;


import com.CrmScheduler.DAO.IUserDao;
import com.CrmScheduler.HelperUtilties.LanguageSettings;
import com.CrmScheduler.SpringConf;
import com.CrmScheduler.entity.CrmUser;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.ZoneId;

/**
 * The com.CrmScheduler.controller for LoginForm. All controls will display in english or french depending on the users language setting. This includes the window title.
 */
public class LoginFormController {


    /**
     * The textfield responsible for getting the username to attempt a login with
     */
    public TextField inputUsername;
    /**
     * The textfield responsible for getting the password to attempt a login with
     */
    public TextField inputPassword;
    /**
     * the label that will display in english or french, a welcome message in the users ZoneId
     */
    public Label labelZoneId;
    /**
     * The label that indicates the nearby input is for username. Will display in english or french.
     */
    public Label labelUsername;
    /**
     * The label that indicates the nearby input is for password. Will display in english or french.
     */
    public Label labelPassword;
    /**
     * The login button that attempts to login. The text on it will display in english or french.
     */
    public Button buttonLogin;


    /**
     * Checks if the username and password are valid (not blank)
     * @return true if the username and password are valid. false otherwise.
     */
    private boolean isFormInputValid()
    {
        return inputUsername.getText().length() > 0 && inputPassword.getLength() > 0;
    }

    /**
     * Helper method to inform the user has put in bad input for the username or password. Also displays in english or french
     */
    private void handleBadInput()
    {
        String languageSetting = LanguageSettings.getInstance().getSystemLanguage();
        String alertTitle = "";
        String alertMessage = "";
        if(languageSetting == "english")
        {
            alertTitle = "Bad form input";
            alertMessage = "Please ensure that the username and password are filled out.";
        }
        else if(languageSetting == "french")
        {
            alertTitle = "Mauvaise saisie du formulaire";
            alertMessage = "Veuillez vous assurer que le nom d'utilisateur et le mot de passe sont renseignés.";
        }

        Alert alert = new Alert(Alert.AlertType.ERROR, alertMessage , ButtonType.OK);
        alert.setTitle(alertTitle);
        alert.showAndWait();
    }

    /**
     * Ran when the form loads. Sets all controls to display in english or french, as well as sets up the ZoneId welcome message.
     */
    @FXML
    public void initialize() {
        LanguageSettings languageSettings = LanguageSettings.getInstance();
        ZoneId zoneId = ZoneId.systemDefault();

        //setup the controls based on whether or not the user's language is english or french
        if(languageSettings.getSystemLanguage() == "english")
        {
            labelZoneId.setText("Hello to our user in " + zoneId.getId());
            labelUsername.setText("Username");
            labelPassword.setText("Password");
            buttonLogin.setText("Login");
        }
        else if(languageSettings.getSystemLanguage() == "french")
        {
            labelZoneId.setText("Bonjour à notre utilisateur de " + zoneId.getId());
            labelUsername.setText("Nom d'utilisateur ");
            labelPassword.setText("Mot de passe");
            buttonLogin.setText("Connexion");
        }
    }

    /**
     * Event handler for the Login button. verifies that a user exists for the username and password provided. Will also log all login attempts to login_activity.txt.
     * When the user logs in, if there is an existing appointment that is approaching within 15 minutes, they will be alerted immediately after login.
     */
    public void Login() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SpringConf.class);
        try {
            CrmUser verifiedUser;
            if (isFormInputValid()) {
                IUserDao userdao = ctx.getBean(IUserDao.class);
                verifiedUser = userdao.getUser(inputUsername.getText(), inputPassword.getText());
                ctx.close();
            }
            else {
                handleBadInput();
                return;
            }
            if (verifiedUser != null) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/ManagementForm.fxml"));
                Parent managementWindow = fxmlLoader.load();
                Scene ModifyPartScene = new Scene(managementWindow);
                Stage window = (Stage) inputUsername.getScene().getWindow();
                CustomerManagerController controller = fxmlLoader.getController();
                controller.setLoggedInUser(verifiedUser);
                window.setScene(ModifyPartScene);
                window.show();
            }

        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage() , ButtonType.OK);
            alert.showAndWait();
        }
    }
    }


