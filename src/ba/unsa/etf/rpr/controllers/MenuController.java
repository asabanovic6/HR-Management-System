package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.model.Employee;

import ba.unsa.etf.rpr.model.Location;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    public Label label1;

    public MenuController() {
    }

    public MenuController(Employee employee) {
    }

    public void Departments (ActionEvent actionEvent) {

    }

    public void Employees (ActionEvent actionEvent) {

    }

    public void Reports ( ActionEvent actionEvent) {

    }
    public void SelfProfile (ActionEvent actionEvent) {

    }

    public void LogOut (ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LogIn.fxml"));
            LogInController logInController = new LogInController();
            loader.setController(logInController);
            root = loader.load();
            stage.setTitle("Prijavi se");
            stage.setScene(new Scene(root, 300, 180));
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
