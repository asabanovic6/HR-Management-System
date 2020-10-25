package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Employee.fxml"));
        EmployeeController ctrl = new EmployeeController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Zaposlenik");
        primaryStage.setScene(new Scene(root, 500, 465));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
