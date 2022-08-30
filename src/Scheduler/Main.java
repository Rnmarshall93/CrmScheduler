package Scheduler;

import Utilities.LanguageSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    //    Parent root = FXMLLoader.load(getClass().getResource("../view/LoginForm.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../main/java//view/LoginForm.fxml"));
        //old build settings!
//--module-path  C:\Users\Warp\Documents\code\School\javafx-sdk-11.0.2\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics
        //set the login for english or french
        if(LanguageSettings.getInstance().getSystemLanguage() == "english")
            primaryStage.setTitle("Login");
        else
            primaryStage.setTitle("Connexion");
        primaryStage.setScene(new Scene(root, 350, 200));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
