package ba.unsa.etf.rpr.controllers;


import ba.unsa.etf.rpr.model.Employee;
import ba.unsa.etf.rpr.utilities.HrDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ChangePasswordController {
    public TextField fieldOldPassword;
    public TextField fieldNewPassword;
    public TextField fieldNewPasswordSec;
    private Employee employee;
    private HrDAO dao;
    public Stage stage;
    @FXML
    private ImageView imgView;
    public ChangePasswordController(Employee employee) {
        this.dao = HrDAO.getInstance();
        this.employee=employee;
        this.stage= new Stage();
    }

    @FXML
    public void initialize() {

        fieldOldPassword.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && validatePassword(newName)) {
                fieldOldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fieldOldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fieldOldPassword.getStyleClass().removeAll("poljeIspravno");
                fieldOldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldNewPassword.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && validatePassword(newName)) {
                fieldNewPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fieldNewPassword.getStyleClass().add("poljeIspravno");
            } else {
                fieldNewPassword.getStyleClass().removeAll("poljeIspravno");
                fieldNewPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldNewPasswordSec.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && validatePassword(newName)) {
                fieldNewPasswordSec.getStyleClass().removeAll("poljeNijeIspravno");
                fieldNewPasswordSec.getStyleClass().add("poljeIspravno");
            } else {
                fieldNewPasswordSec.getStyleClass().removeAll("poljeIspravno");
                fieldNewPasswordSec.getStyleClass().add("poljeNijeIspravno");
            }
        });
        imgView.setImage(
                new Image("images/application-exit.png")
        );

    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) return false;
        return true;
    }
    public void clickCancel(ActionEvent actionEvent) {
         stage = (Stage) fieldNewPasswordSec.getScene().getWindow();
        stage.close();
    }

    public void clickOk (ActionEvent actionEvent) {
        boolean Ok = true;
        if (fieldOldPassword.getText().trim().isEmpty()) {
            fieldOldPassword.getStyleClass().removeAll("poljeIspravno");
            fieldOldPassword.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldOldPassword.getStyleClass().removeAll("poljeNijeIspravno");
            fieldOldPassword.getStyleClass().add("poljeIspravno");
        }
        if (!fieldOldPassword.getText().equals(dao.getPassword(employee.getEmail()))) Ok=false;
        if (!fieldNewPasswordSec.getText().equals(fieldNewPassword.getText())) Ok = false;
        if (!Ok) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravni podaci!");
            alert.setHeaderText("Neispravni podaci!");
            alert.setContentText("Unijeli ste neispravne podatke!");
            alert.showAndWait();
        }
        else {
            dao.changePassword(employee,fieldNewPassword.getText());
             stage = (Stage) fieldNewPasswordSec.getScene().getWindow();
            stage.close();
        }
    }
}