package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.model.Department;
import ba.unsa.etf.rpr.model.Employee;
import ba.unsa.etf.rpr.model.Job;
import ba.unsa.etf.rpr.model.Worker;
import ba.unsa.etf.rpr.utilities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class EmployeeController {

    public TextField fieldEmployeeName;
    public TextField fieldEmail;
    public TextField fieldManager;
    public TextField fieldSalary;
    public TextField fieldCmp;
    public TextField fieldPassword;
    public DatePicker pickerHireDate;
    public DatePicker pickerExpireDate;
    public ChoiceBox<String> choiceJob;
    public ObservableList<String> jobs;
    public ChoiceBox<String> choiceDepartment;
    public ObservableList<String> departments;
    private Employee employee;
    private HrDAO dao;
    public Stage stage;
    @FXML
    private ImageView imgView;

    public EmployeeController() {
        this.dao = HrDAO.getInstance();
        this.jobs = FXCollections.observableArrayList(dao.getJobs());
        this.departments = FXCollections.observableArrayList(dao.getDepartmentsByNames());
        this.stage= new Stage();
    }


    @FXML
    public void initialize() {
        choiceJob.setItems(jobs);
        choiceDepartment.setItems(departments);
        if (employee != null) {
            fieldEmployeeName.setText(employee.getEmployeeName());
            fieldEmail.setText(employee.getEmail());
            if (employee instanceof Worker)
                fieldManager.setText(((Worker) employee).getManager().getEmployeeName());
            for (String job : jobs)
                if (job == employee.getJob().getJobTitle())
                    choiceJob.getSelectionModel().select(job);
            for (String dep : departments)
                if (dep == employee.getDepartment().getDepartmentName())
                    choiceDepartment.getSelectionModel().select(dep);

            pickerHireDate.setValue(employee.getHireDate());
            pickerExpireDate.setValue(employee.getExpireDate());
            fieldCmp.setText(Double.toString(employee.getCmp()));
            fieldSalary.setText(Integer.toString(employee.getSalary()));
        }
        else {
            choiceJob.getSelectionModel().selectFirst();
            choiceDepartment.getSelectionModel().selectFirst();
        }
        fieldEmployeeName.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && validateEmployeeName(newName) ) {
                fieldEmployeeName.getStyleClass().removeAll("poljeNijeIspravno");
                fieldEmployeeName.getStyleClass().add("poljeIspravno");
            } else {
                fieldEmployeeName.getStyleClass().removeAll("poljeIspravno");
                fieldEmployeeName.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldEmail.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && validateEmail(newName) ) {
                fieldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fieldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fieldEmail.getStyleClass().removeAll("poljeIspravno");
                fieldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldPassword.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && newName.length()>6 ) {
                fieldPassword.getStyleClass().removeAll("poljeNijeIspravno");
                fieldPassword.getStyleClass().add("poljeIspravno");
            } else {
                fieldPassword.getStyleClass().removeAll("poljeIspravno");
                fieldPassword.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldCmp.textProperty().addListener((obs, oldValue, newValue) -> {

            if (!newValue.isEmpty() && validateCmp(newValue) ) {
                fieldCmp.getStyleClass().removeAll("poljeNijeIspravno");
                fieldCmp.getStyleClass().add("poljeIspravno");
            } else {
                fieldCmp.getStyleClass().removeAll("poljeIspravno");
                fieldCmp.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldSalary.textProperty().addListener((obs, oldValue, newValue) -> {

            if (!newValue.isEmpty() && validateSalary(newValue) ) {
                fieldSalary.getStyleClass().removeAll("poljeNijeIspravno");
                fieldSalary.getStyleClass().add("poljeIspravno");
            } else {
                fieldSalary.getStyleClass().removeAll("poljeIspravno");
                fieldSalary.getStyleClass().add("poljeNijeIspravno");
            }
        });
        imgView.setImage(
                new Image("images/application-exit.png")
        );
    }

    private boolean validateCmp(String newValue) {
        for (int i=1;i<newValue.length();i++)
            if (((newValue.charAt(i) >= 'A' && newValue.charAt(i) <= 'Z') || (newValue.charAt(i) >= 'a' && newValue.charAt(i) <= 'z'))) return false; // User cant use letter in cmp textfield
        double cmp = 0;
        try {
            cmp = Double.parseDouble(fieldCmp.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (cmp<0) return false;
        return true;
    }
    private boolean validateSalary(String newValue) {
      for (int i=1;i<newValue.length();i++)
            if (((newValue.charAt(i) >= 'A' && newValue.charAt(i) <= 'Z') || (newValue.charAt(i) >= 'a' && newValue.charAt(i) <= 'z'))) return false; // User cant use letter in salary textfield
        int cmp = 0;
        try {
            cmp = Integer.parseInt(fieldSalary.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (cmp<0 ) return false;
         return true;
    }



    private boolean validateEmployeeName(String newName) {
        if (newName.length()>35) return false;
        if (!((newName.charAt(0) >= 'A' && newName.charAt(0) <= 'Z') || (newName.charAt(0) >= 'a' && newName.charAt(0) <= 'z'))) return false;

        for (int i=1;i<newName.length();i++) {
            if (!((newName.charAt(i) >= 'A' && newName.charAt(i) <= 'Z') || (newName.charAt(i) >= 'a' && newName.charAt(i) <= 'z') || (newName.charAt(i)>=48 && newName.charAt(i)<=57) || newName.charAt(i)==32 )) return false;
        }
        return true;
    }
    private boolean validateEmail (String email) {
        int brojac=0;
        if (email.charAt(0)=='@' || email.charAt(email.length()-1)=='@') return false;
        for (int i =1;i<email.length();i++) {
            if (email.charAt(i)=='@') brojac++;
        }
        if (brojac==0) return false;
        return true;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void clickCancel(ActionEvent actionEvent) {
         stage = (Stage) fieldEmployeeName.getScene().getWindow();
        stage.close();
    }
    public void addNewJob (ActionEvent actionEvent) {

        Parent root = null;
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/Job.fxml" ), bundle);
            JobController jobController = new JobController(null);
            loader.setController(jobController);
            root = loader.load();
            Locale.setDefault(new Locale("bs", "BA"));
            stage.setTitle(bundle.getString("Add_new_job"));
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Job job = jobController.getJob();
                if (job != null) {
                    dao.addJob(job);
                    choiceDepartment.setItems(FXCollections.observableArrayList(dao.getJobs()));
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addNewDepartment (ActionEvent actionEvent) {

        Parent root = null;
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/Department.fxml" ), bundle);            DepartmentController departmentController = new DepartmentController(null);
            loader.setController(departmentController);
            root = loader.load();
            Locale.setDefault(new Locale("bs", "BA"));
            stage.setTitle(bundle.getString("Add_new_department"));
            stage.setScene(new Scene(root));
            stage.setResizable(true);
            stage.show();

            stage.setOnHiding( event -> {
                Department dep = departmentController.getDepartment();
                if (dep != null) {
                    dao.addDepartment(dep);
                    choiceDepartment.setItems(FXCollections.observableArrayList(dao.getDepartmentsByNames()));
                }
            } );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clickOk(ActionEvent actionEvent) {
        boolean Ok = true;

        if (fieldEmployeeName.getText().trim().isEmpty()) {
            fieldEmployeeName.getStyleClass().removeAll("poljeIspravno");
            fieldEmployeeName.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldEmployeeName.getStyleClass().removeAll("poljeNijeIspravno");
            fieldEmployeeName.getStyleClass().add("poljeIspravno");
        }

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

        if (fieldSalary.getText().trim().isEmpty()) {
            fieldSalary.getStyleClass().removeAll("poljeIspravno");
            fieldSalary.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldSalary.getStyleClass().removeAll("poljeNijeIspravno");
            fieldSalary.getStyleClass().add("poljeIspravno");
        }
        if (choiceJob.getItems().isEmpty()) {
            choiceJob.getStyleClass().removeAll("poljeIspravno");
            choiceJob.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            choiceJob.getStyleClass().removeAll("poljeNijeIspravno");
            choiceJob.getStyleClass().add("poljeIspravno");
        }



        double cmp = 0;
        try {
            cmp = Double.parseDouble(fieldCmp.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (cmp < 0 || fieldCmp.getText().trim().isEmpty()) {
            fieldCmp.getStyleClass().removeAll("poljeIspravno");
            fieldCmp.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldCmp.getStyleClass().removeAll("poljeNijeIspravno");
            fieldCmp.getStyleClass().add("poljeIspravno");
        }

        if (!Ok) return;

        if (employee == null) employee = new Employee();
        employee.setEmployeeName(fieldEmployeeName.getText());
        employee.setEmail(fieldEmail.getText());
        employee.setCmp(Double.parseDouble(fieldCmp.getText()));
        employee.setSalary(Integer.parseInt(fieldSalary.getText()));
        employee.setDepartment(dao.getDepartmentByName(choiceDepartment.getSelectionModel().getSelectedItem()));
        employee.setJob(dao.getJobbyName(choiceJob.getSelectionModel().getSelectedItem()));
        employee.setPassword(fieldPassword.getText());

        employee.setHireDate(pickerHireDate.getValue());
        employee.setExpireDate(pickerExpireDate.getValue());

         stage = (Stage) fieldEmployeeName.getScene().getWindow();
        stage.close();
    }
}

