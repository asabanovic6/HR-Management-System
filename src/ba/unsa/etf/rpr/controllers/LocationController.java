package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.utilities.HrDAO;
import ba.unsa.etf.rpr.model.Location;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class LocationController {
    public TextField fieldCity;
    private HrDAO dao;
    private Location location;
    public Stage stage;
    @FXML
    private ImageView imgView;



    public LocationController() {
        this.dao= HrDAO.getInstance();
        this.location = location;
        this.stage = new Stage();
    }

    public Location getLocation() {

        return location;
    }

    @FXML
    public void initialize() {
        if (location!=null){
            fieldCity.setText(location.getCity());
        }
        imgView.setImage(
                new Image("images/application-exit.png")
        );
        fieldCity.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && validateCity(newName) ) {
                fieldCity.getStyleClass().removeAll("poljeNijeIspravno");
                fieldCity.getStyleClass().add("poljeIspravno");
            } else {
                fieldCity.getStyleClass().removeAll("poljeIspravno");
                fieldCity.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }

    private boolean validateCity(String newName) {
        if (newName.length()>20) return false;
        if (!((newName.charAt(0) >= 'A' && newName.charAt(0) <= 'Z') || (newName.charAt(0) >= 'a' && newName.charAt(0) <= 'z'))) return false;

        for (int i=1;i<newName.length();i++) {
            if (!((newName.charAt(i) >= 'A' && newName.charAt(i) <= 'Z') || (newName.charAt(i) >= 'a' && newName.charAt(i) <= 'z') || (newName.charAt(i)>=48 && newName.charAt(i)<=57) || newName.charAt(i)==32 )) return false;
        }
        return true;
    }
    public void clickCancel(ActionEvent actionEvent) {
        location = null;
         stage = (Stage) fieldCity.getScene().getWindow();
        stage.close();
    }

    public void clickOk(ActionEvent actionEvent) {
        if (dao.searchLocationsByName(fieldCity.getText())!=null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravna lokacija");
            alert.setHeaderText("Neispravna lokacija");
            alert.setContentText("Lokacija koju pokušavate dodati već postoji!");

            alert.showAndWait();
        }
        else {
            boolean Ok = true;

            if (fieldCity.getText().trim().isEmpty()) {
                fieldCity.getStyleClass().removeAll("poljeIspravno");
                fieldCity.getStyleClass().add("poljeNijeIspravno");
                Ok = false;
            } else {
                fieldCity.getStyleClass().removeAll("poljeNijeIspravno");
                fieldCity.getStyleClass().add("poljeIspravno");
            }

            if (!Ok) return;

            if (location==null) this.location = new Location();
            this.location.setCity(fieldCity.getText());
             stage = (Stage) fieldCity.getScene().getWindow();
            stage.close();
        }
    }
}
