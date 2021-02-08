package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.model.Job;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class JobController {

    public TextField fieldJobTitle;
    public TextField fieldMinSalary;
    public TextField fieldMaxSalary;
    private Job job;

    @FXML
    private ImageView imgView;

    public JobController() {
    }

    public JobController(Job job) {
        this.job = job;
    }
    public Job getJob() {
        return job;
    }

    @FXML
    public void  initialize() {
       if (job!=null) {
           fieldJobTitle.setText(job.getJobTitle());
           fieldMinSalary.setText(Integer.toString(job.getMinSalary()));
           fieldMaxSalary.setText(Integer.toString(job.getMaxSalary()));
       }
        imgView.setImage(
                new Image("images/application-exit.png")
        );
        fieldJobTitle.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && ValidateJobTitle(newName) ) {
                fieldJobTitle.getStyleClass().removeAll("poljeNijeIspravno");
                fieldJobTitle.getStyleClass().add("poljeIspravno");
            } else {
                fieldJobTitle.getStyleClass().removeAll("poljeIspravno");
                fieldJobTitle.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldMinSalary.textProperty().addListener((obs, oldValue, newValue) -> {

            if (!newValue.isEmpty() && ValidateSalaryMin(newValue) ) {
                fieldMinSalary.getStyleClass().removeAll("poljeNijeIspravno");
                fieldMinSalary.getStyleClass().add("poljeIspravno");
            } else {
                fieldMinSalary.getStyleClass().removeAll("poljeIspravno");
                fieldMinSalary.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldMaxSalary.textProperty().addListener((obs, oldValue, newValue) -> {

            if (!newValue.isEmpty() && ValidateSalaryMax(newValue) ) {
                fieldMaxSalary.getStyleClass().removeAll("poljeNijeIspravno");
                fieldMaxSalary.getStyleClass().add("poljeIspravno");
            } else {
                fieldMaxSalary.getStyleClass().removeAll("poljeIspravno");
                fieldMaxSalary.getStyleClass().add("poljeNijeIspravno");
            }
        });

    }

    private boolean ValidateJobTitle(String newName) {
        if (newName.length()>35) return false;
        if (!((newName.charAt(0) >= 'A' && newName.charAt(0) <= 'Z') || (newName.charAt(0) >= 'a' && newName.charAt(0) <= 'z'))) return false;

        for (int i=1;i<newName.length();i++) {
            if (!((newName.charAt(i) >= 'A' && newName.charAt(i) <= 'Z') || (newName.charAt(i) >= 'a' && newName.charAt(i) <= 'z') || (newName.charAt(i)>=48 && newName.charAt(i)<=57) || newName.charAt(i)==32 )) return false;
        }
        return true;
    }

    private boolean ValidateSalaryMin(String newValue) {
        for (int i=1;i<newValue.length();i++)
            if (((newValue.charAt(i) >= 'A' && newValue.charAt(i) <= 'Z') || (newValue.charAt(i) >= 'a' && newValue.charAt(i) <= 'z'))) return false; // User cant use letter in cmp textfield
        int salary = 0;

        try {
            salary = Integer.parseInt(fieldMinSalary.getText());

        } catch (NumberFormatException e) {
            // ...
        }
        if (salary<=0) return false;
        else return true;
    }
    private boolean ValidateSalaryMax(String newValue) {
        for (int i=1;i<newValue.length();i++)
            if (((newValue.charAt(i) >= 'A' && newValue.charAt(i) <= 'Z') || (newValue.charAt(i) >= 'a' && newValue.charAt(i) <= 'z'))) return false; // User cant use letter in cmp textfield
        int salary = 0;
        try {
            salary=Integer.parseInt(fieldMaxSalary.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (salary<=0 ) return false;
        else return true;
    }

    public void clickCancel(ActionEvent actionEvent) {
        job = null;
        Stage stage = (Stage) fieldJobTitle.getScene().getWindow();
        stage.close();
    }
    public void clickOk(ActionEvent actionEvent) {
        boolean Ok = true;

        if (fieldJobTitle.getText().trim().isEmpty()) {
            fieldJobTitle.getStyleClass().removeAll("poljeIspravno");
            fieldJobTitle.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldJobTitle.getStyleClass().removeAll("poljeNijeIspravno");
            fieldJobTitle.getStyleClass().add("poljeIspravno");
        }
        int salaryMin = 0;
        try {
            salaryMin= Integer.parseInt(fieldMinSalary.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (salaryMin <= 0 || fieldMinSalary.getText().trim().isEmpty()) {
            fieldMinSalary.getStyleClass().removeAll("poljeIspravno");
            fieldMinSalary.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldMinSalary.getStyleClass().removeAll("poljeNijeIspravno");
            fieldMinSalary.getStyleClass().add("poljeIspravno");
        }
        int salaryMax = 0;
        try {
            salaryMax= Integer.parseInt(fieldMaxSalary.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (salaryMax <= 0  || salaryMax<=salaryMin || fieldMaxSalary.getText().trim().isEmpty()) {
            fieldMaxSalary.getStyleClass().removeAll("poljeIspravno");
            fieldMaxSalary.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldMaxSalary.getStyleClass().removeAll("poljeNijeIspravno");
            fieldMaxSalary.getStyleClass().add("poljeIspravno");
        }

        if (!Ok) return;

        if (job==null) this.job = new Job();
        this.job.setJobTitle(fieldJobTitle.getText());
       this.job.setMinSalary(Integer.parseInt(fieldMinSalary.getText()));
        this.job.setMaxSalary(Integer.parseInt(fieldMaxSalary.getText()));
        Stage stage = (Stage) fieldJobTitle.getScene().getWindow();
        stage.close();
    }

}
