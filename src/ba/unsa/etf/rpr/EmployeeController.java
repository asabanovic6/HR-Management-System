package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;


import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeController {

    public TextField fieldEmployeeName;
    public TextField fieldEmail;
    public TextField fieldManager;
    public TextField fieldSalary;
    public TextField fieldCmp;
    public DatePicker pickerHireDate;
    public DatePicker pickerExpireDate;
    public ChoiceBox<Job> choiceJob;
    public ObservableList<Job> jobs= FXCollections.observableArrayList();
    public ChoiceBox<Department> choiceDepartment;
    public ObservableList<Department> departments= FXCollections.observableArrayList();
    private Employee employee;
    @FXML
    private ImageView imgView;

    public EmployeeController() {
    }

    public EmployeeController(Employee employee, ArrayList<Job> jobs,ArrayList<Department> departments) {
        this.employee = employee;
        this.jobs = FXCollections.observableArrayList(jobs);
        this.departments=FXCollections.observableArrayList(departments);
    }

    @FXML
    public void initialize() {
        choiceJob.setItems(jobs);
        if (employee != null) {
            fieldEmployeeName.setText(employee.getEmployeeName());
            fieldEmail.setText(employee.getEmail());
            if (employee instanceof Worker)
                fieldManager.setText(((Worker) employee).getManager().getEmployeeName());
            for (Job job : jobs)
                if (job.getJobTitle() == employee.getJob().getJobTitle())
                    choiceJob.getSelectionModel().select(job);
            for (Department dep : departments)
                if (dep.getDepartmentName() == employee.getDepartment().getDepartmentName())
                    choiceDepartment.getSelectionModel().select(dep);
            fieldSalary.setText(Integer.toString(employee.getSalary()));
            pickerHireDate.setValue(employee.getHireDate());
            pickerExpireDate.setValue(employee.getExpireDate());
            fieldCmp.setText(Double.toString(employee.getCmp()));
        }
        else {
            choiceJob.getSelectionModel().selectFirst();
        }
        fieldEmployeeName.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && ValidateEmployeeName(newName) ) {
                fieldEmployeeName.getStyleClass().removeAll("poljeNijeIspravno");
                fieldEmployeeName.getStyleClass().add("poljeIspravno");
            } else {
                fieldEmployeeName.getStyleClass().removeAll("poljeIspravno");
                fieldEmployeeName.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldEmail.textProperty().addListener((obs, oldName, newName) -> {

            if (!newName.isEmpty() && ValidateEmail(newName) ) {
                fieldEmail.getStyleClass().removeAll("poljeNijeIspravno");
                fieldEmail.getStyleClass().add("poljeIspravno");
            } else {
                fieldEmail.getStyleClass().removeAll("poljeIspravno");
                fieldEmail.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldSalary.textProperty().addListener((obs, oldValue, newValue) -> {

            if (!newValue.isEmpty() && ValidateSalary(newValue) ) {
                fieldSalary.getStyleClass().removeAll("poljeNijeIspravno");
                fieldSalary.getStyleClass().add("poljeIspravno");
            } else {
                fieldSalary.getStyleClass().removeAll("poljeIspravno");
                fieldSalary.getStyleClass().add("poljeNijeIspravno");
            }
        });
        fieldCmp.textProperty().addListener((obs, oldValue, newValue) -> {

            if (!newValue.isEmpty() && ValidateCmp(newValue) ) {
                fieldCmp.getStyleClass().removeAll("poljeNijeIspravno");
                fieldCmp.getStyleClass().add("poljeIspravno");
            } else {
                fieldCmp.getStyleClass().removeAll("poljeIspravno");
                fieldCmp.getStyleClass().add("poljeNijeIspravno");
            }
        });
        imgView.setImage(
                new Image("images/application-exit.png")
        );
    }

    private boolean ValidateCmp(String newValue) {
        for (int i=1;i<newValue.length();i++)
            if (((newValue.charAt(i) >= 'A' && newValue.charAt(i) <= 'Z') || (newValue.charAt(i) >= 'a' && newValue.charAt(i) <= 'z'))) return false; // User cant use letter in cmp textfield
        double cmp = 0;
        try {
            cmp = Double.parseDouble(fieldCmp.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (cmp<0) return false;
        else return true;
    }

    private boolean ValidateSalary(String newValue) {
        for (int i=1;i<newValue.length();i++)
            if (((newValue.charAt(i) >= 'A' && newValue.charAt(i) <= 'Z') || (newValue.charAt(i) >= 'a' && newValue.charAt(i) <= 'z'))) return false; // User cant use letter in salary textfield
        int salary = 0;
        try {
            salary = Integer.parseInt(fieldSalary.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if(salary<=0) return false;
        else return true;
    }


    private boolean ValidateEmployeeName(String newName) {
        if (newName.length()>35) return false;
        if (!((newName.charAt(0) >= 'A' && newName.charAt(0) <= 'Z') || (newName.charAt(0) >= 'a' && newName.charAt(0) <= 'z'))) return false;

        for (int i=1;i<newName.length();i++) {
            if (!((newName.charAt(i) >= 'A' && newName.charAt(i) <= 'Z') || (newName.charAt(i) >= 'a' && newName.charAt(i) <= 'z') || (newName.charAt(i)>=48 && newName.charAt(i)<=57) || newName.charAt(i)==32 )) return false;
        }
        return true;
    }
    private boolean ValidateEmail (String email) {
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
        employee = null;
        Stage stage = (Stage) fieldEmployeeName.getScene().getWindow();
        stage.close();
    }
    public void AddNewJob (ActionEvent actionEvent) {
        employee = null;
        Stage stage = (Stage) fieldEmployeeName.getScene().getWindow();
        stage.close();
    }
    public void AddNewDepartment (ActionEvent actionEvent) {
        employee = null;
        Stage stage = (Stage) fieldEmployeeName.getScene().getWindow();
        stage.close();
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
        if (choiceJob.getItems().isEmpty()) {
            choiceJob.getStyleClass().removeAll("poljeIspravno");
            choiceJob.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            choiceJob.getStyleClass().removeAll("poljeNijeIspravno");
            choiceJob.getStyleClass().add("poljeIspravno");
        }


        int salary = 0;
        try {
            salary = Integer.parseInt(fieldSalary.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (salary <= 0) {
            fieldSalary.getStyleClass().removeAll("poljeIspravno");
            fieldSalary.getStyleClass().add("poljeNijeIspravno");
            Ok = false;
        } else {
            fieldSalary.getStyleClass().removeAll("poljeNijeIspravno");
            fieldSalary.getStyleClass().add("poljeIspravno");
        }

        double cmp = 0;
        try {
            cmp = Double.parseDouble(fieldCmp.getText());
        } catch (NumberFormatException e) {
            // ...
        }
        if (cmp < 0) {
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
        employee.setDepartment(choiceDepartment.getSelectionModel().getSelectedItem());
        employee.setJob(choiceJob.getSelectionModel().getSelectedItem());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String hireDate = formatter.format(employee.getHireDate());
        employee.setHireDate(hireDate);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String expireDate = formatter1.format(employee.getExpireDate());
        employee.setExpireDate(expireDate);

        Stage stage = (Stage) fieldEmployeeName.getScene().getWindow();
        stage.close();
    }
}

