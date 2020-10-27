package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Job.fxml"));
        JobController ctrl = new JobController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Posao");
        primaryStage.setScene(new Scene(root, 500, 465));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
