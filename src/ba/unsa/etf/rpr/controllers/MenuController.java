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
   private  Employee employee;


    public MenuController(Employee employee) {
        this.employee=employee;
    }

    public void Departments (ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Departments.fxml"));
            DepartmentsController departmentsController = new DepartmentsController(employee);
            loader.setController(departmentsController);
            root = loader.load();
            stage.setTitle("Odjeli");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Employees (ActionEvent actionEvent) {

    }

    public void Reports ( ActionEvent actionEvent) {

    }
    public void SelfProfile (ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            ProfileController profileController = new ProfileController(employee);
            loader.setController(profileController);
            root = loader.load();
            stage.setTitle("Profil");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
