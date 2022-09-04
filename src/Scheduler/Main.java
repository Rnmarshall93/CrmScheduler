import com.CrmScheduler.HelperUtilties.LanguageSettings;
import com.CrmScheduler.SpringConf;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/LoginForm.fxml"));

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
