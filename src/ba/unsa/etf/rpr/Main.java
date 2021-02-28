package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controllers.EmployeeController;
import ba.unsa.etf.rpr.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle("Translation");
        FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/LogIn.fxml" ), bundle);
        Locale.setDefault(new Locale("bs", "BA"));
        LogInController ctrl = new LogInController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle(bundle.getString("LogInStage"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
