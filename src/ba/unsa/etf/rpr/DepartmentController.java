package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.awt.*;

public class DepartmentController {
    public TextField fieldDepName;
    public ChoiceBox<String> choiceManager;
    public ChoiceBox<String> choiceLocation;
    public ObservableList<String> managers;
    public  ObservableList<String> locations;
    private Department department;
    private HrDAO dao;

    @FXML
    private ImageView imgView;

    public DepartmentController() {
        dao = HrDAO.getInstance();
        managers = FXCollections.observableArrayList(dao.getManagers());
        locations = FXCollections.observableArrayList(dao.getLocationsName());

    }

    public DepartmentController(Department department) {
        dao = HrDAO.getInstance();
        this.department = department;
        this.managers =  FXCollections.observableArrayList(dao.getManagers());
        this.locations = FXCollections.observableArrayList(dao.getLocationsName());
    }

    public Department getDepartment() {
        return department;
    }

    @FXML
    public void initialize () {
        choiceLocation.setItems(locations);
        choiceManager.setItems(managers);
        if (department!=null) {
           fieldDepName.setText(department.getDepartmentName());
            for (String manager : managers)
                if (manager.equals(dao.getManager(department.getManagerId()).getEmployeeName()))
                    choiceManager.getSelectionModel().select(manager);
            for (String location : locations)
                if (location.equals(dao.getLocation(department.getLocationId()).getCity()))
                    choiceLocation.getSelectionModel().select(location);
        }
        else {
            choiceLocation.getSelectionModel().selectFirst();
            choiceManager.getSelectionModel().selectFirst();
        }
        imgView.setImage(
                new Image("images/application-exit.png")
        );
        fieldDepName.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && ValidateDepName(newName) ) {
                fieldDepName.getStyleClass().removeAll("poljeNijeIspravno");
                fieldDepName.getStyleClass().add("poljeIspravno");
            } else {
                fieldDepName.getStyleClass().removeAll("poljeIspravno");
                fieldDepName.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    private boolean ValidateDepName(String newName) {
        if (newName.length()>35) return false;
        if (!((newName.charAt(0) >= 'A' && newName.charAt(0) <= 'Z') || (newName.charAt(0) >= 'a' && newName.charAt(0) <= 'z'))) return false;

        for (int i=1;i<newName.length();i++) {
            if (!((newName.charAt(i) >= 'A' && newName.charAt(i) <= 'Z') || (newName.charAt(i) >= 'a' && newName.charAt(i) <= 'z') || (newName.charAt(i)>=48 && newName.charAt(i)<=57) || newName.charAt(i)==32 )) return false;
        }
        return true;
    }
    public void clickCancel(ActionEvent actionEvent) {
        department = null;
        Stage stage = (Stage) fieldDepName.getScene().getWindow();
        stage.close();
    }
    public void clickOk(ActionEvent actionEvent) {
        boolean Ok = true;

        if (fieldDepName.getText().trim().isEmpty()) {
            fieldDepName.getStyleClass().removeAll("poljeIspravno");
            fieldDepName.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldDepName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldDepName.getStyleClass().add("poljeIspravno");
        }

        if (!Ok) return;

        if (department==null) this.department = new Department();
        this.department.setDepartmentName(fieldDepName.getText());
       this.department.setManagerId(dao.searchEmployeeByName(choiceManager.getSelectionModel().getSelectedItem()).getEmployeeId());
        this.department.setLocationId(dao.searchLocationsByName(choiceLocation.getSelectionModel().getSelectedItem()).getLocationId());
       Stage stage = (Stage) fieldDepName.getScene().getWindow();
        stage.close();

    }
}
