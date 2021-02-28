package ba.unsa.etf.rpr.controllers;


import ba.unsa.etf.rpr.model.Employee;
import ba.unsa.etf.rpr.model.Location;
import ba.unsa.etf.rpr.utilities.HrDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class LogInController {
    public TextField fieldEmail;
    public PasswordField fieldPassword;
    private Employee employee;
    private HrDAO dao;
    public Stage stage;
    public LogInController() {
        this.dao= HrDAO.getInstance();
        this.stage = new Stage();
    }

    @FXML
    public void initialize() {
        if (employee != null) {
            fieldPassword.setText(employee.getPassword());
            fieldEmail.setText(employee.getEmail());
        }
        fieldEmail.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() ) {
                fieldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fieldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fieldEmail.getStyleClass().removeAll("poljeIspravno");
                fieldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });
            fieldPassword.textProperty().addListener((obs, oldName, newName) -> {

                if (!newName.isEmpty()  ) {
                    fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                    fieldPassword.getStyleClass().add("poljeIspravno");
                } else {
                    fieldPassword.getStyleClass().removeAll("poljeIspravno");
                    fieldPassword.getStyleClass().add("poljeNijeIspravno");
                }
        });


    }





    public void logIn (ActionEvent actionEvent) {
        boolean Ok = true;
        if (fieldEmail.getText().trim().isEmpty()) {
            fieldEmail.getStyleClass().removeAll("poljeIspravno");
            fieldEmail.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldEmail.getStyleClass().removeAll("poljeNijeIspravno");
            fieldEmail.getStyleClass().add("poljeIspravno");
        }
        if (fieldPassword.getText().trim().isEmpty()) {
            fieldPassword.getStyleClass().removeAll("poljeIspravno");
            fieldPassword.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
            fieldPassword.getStyleClass().add("poljeIspravno");
        }
        // Try to check if employee with this logIn  data exist in database
        HashMap<String,String> allLogs = new HashMap<>();
        allLogs= dao.getLogInData();
        if (!allLogs.containsKey(fieldEmail.getText())) Ok = false;
        else if (!allLogs.get(fieldEmail.getText()).equals(fieldPassword.getText())) Ok = false;

        if (!Ok) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravni podaci!");
            alert.setHeaderText("Neispravni podaci!");
            alert.setContentText("Unijeli ste neispravne podatke!");
            alert.showAndWait();
        }
        else  {
            if (employee == null) employee = new Employee();
            employee= dao.getEmployee(fieldEmail.getText());
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Menu.fxml"));
                MenuController menuController = new MenuController(employee);
                loader.setController(menuController);
                root = loader.load();
                stage.setTitle("Dodaj novu lokaciju");
                stage.setScene(new Scene(root));
                stage.setMaximized(true);
                stage.setResizable(true);
                stage.show();
                Stage thisStage = (Stage) fieldEmail.getScene().getWindow();
                thisStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
