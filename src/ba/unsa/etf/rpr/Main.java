package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.controllers.EmployeeController;
import ba.unsa.etf.rpr.controllers.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogIn.fxml"));
        LogInController ctrl = new LogInController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Prijavi se");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
