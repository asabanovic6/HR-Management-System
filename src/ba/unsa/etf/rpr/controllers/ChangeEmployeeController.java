package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.exception.NonExistentDepartment;
import ba.unsa.etf.rpr.model.Employee;
import ba.unsa.etf.rpr.model.Manager;
import ba.unsa.etf.rpr.model.Worker;
import ba.unsa.etf.rpr.utilities.HrDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChangeEmployeeController {
    private Employee employee;
    private SimpleStringProperty name,email,job,dep,salary,cmp,man;
    private LocalDate edate,hdate;
    public TextField fieldEmployeeName;
    public TextField fieldEmail;
    public TextField fieldJob;
    public TextField fieldDepartment;
    public TextField fieldManager;
    public DatePicker pickerHireDate;
    public DatePicker pickerExpireDate;
    public TextField fieldSalary;
    public  TextField fieldCmp;
    private HrDAO dao;
    @FXML
    private ImageView imgView;

    public ChangeEmployeeController (Employee employee) {
        this.employee = employee;
        this.name= new SimpleStringProperty(employee.getEmployeeName());
        this.email = new SimpleStringProperty(employee.getEmail());
        this.job = new SimpleStringProperty(employee.getJob().getJobTitle());
        this.dep = new SimpleStringProperty(employee.getDepartment().getDepartmentName());
        this.salary= new SimpleStringProperty( ""+employee.getSalary());
        this.cmp = new SimpleStringProperty(""+employee.getCmp());
        this.hdate=employee.getHireDate();
        this.edate=employee.getExpireDate();
        if (employee instanceof Manager) this.man= new SimpleStringProperty("/");
        else if (employee instanceof Worker){
            this.man = new SimpleStringProperty( ((Worker) employee).getManager().getEmployeeName());
        }
       this.dao=HrDAO.getInstance();
    }

    @FXML
    public void initialize() {
        fieldEmployeeName.textProperty().bindBidirectional(name);

        fieldDepartment.textProperty().bindBidirectional(dep);


        fieldEmail.textProperty().bindBidirectional(email);


        fieldJob.textProperty().bindBidirectional(job);


        fieldSalary.textProperty().bindBidirectional(salary);


        fieldCmp.textProperty().bindBidirectional(cmp);

       pickerExpireDate.setValue(edate);
       pickerHireDate.setValue(hdate);
        fieldManager.textProperty().bindBidirectional(man);

        imgView.setImage(
                new Image("images/application-exit.png")
        );
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

            if (!newValue.isEmpty() && validateCmp(newValue) ) {
                fieldSalary.getStyleClass().removeAll("poljeNijeIspravno");
                fieldSalary.getStyleClass().add("poljeIspravno");
            } else {
                fieldSalary.getStyleClass().removeAll("poljeIspravno");
                fieldSalary.getStyleClass().add("poljeNijeIspravno");
            }
        });
    }
    public Employee getEmployee() {
        return employee;
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
        else return true;
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
    public void clickCancel (ActionEvent actionEvent) {

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EmployeesInDepartment.fxml"));
            EmployeesFromDepController employeesFromDepController = new EmployeesFromDepController(employee);
            loader.setController(employeesFromDepController);
            root = loader.load();
            stage.setTitle("Zaposleni");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            Stage thisstage = (Stage) fieldEmployeeName.getScene().getWindow();
            thisstage.close();
            stage.show();

        } catch (IOException | NonExistentDepartment e) {
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
        if (dao.getDepartmentsByNames().contains(dao.getDepartmentByName(fieldDepartment.getText()))) {
            Ok=false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravan odjel");
            alert.setHeaderText("Nesipravan odjel");
            alert.setContentText("Odjel ne postoji!");
            alert.showAndWait();
        }
        if (dao.getJobs().contains(dao.getJobbyName(fieldJob.getText()))) {
            Ok=false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neispravan posao");
            alert.setHeaderText("Nesipravan posao");
            alert.setContentText("Posao koji ste naveli ne postoji u bazi!");
            alert.showAndWait();
        }
        if (!Ok) return;

        if (employee == null) employee = new Employee();
        employee.setEmployeeName(fieldEmployeeName.getText());
        employee.setEmail(fieldEmail.getText());
        employee.setCmp(Double.parseDouble(fieldCmp.getText()));
        employee.setSalary(Integer.parseInt(fieldSalary.getText()));
        employee.setDepartment(dao.getDepartmentByName(fieldDepartment.getText()));
        employee.setJob(dao.getJobbyName(fieldJob.getText()));
        employee.setHireDate(pickerHireDate.getValue());
        employee.setExpireDate(pickerExpireDate.getValue());

        Stage stage = (Stage) fieldEmployeeName.getScene().getWindow();
        stage.close();
    }
}
