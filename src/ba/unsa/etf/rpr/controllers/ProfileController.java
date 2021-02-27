package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.model.Employee;
import ba.unsa.etf.rpr.model.Job;
import ba.unsa.etf.rpr.model.Manager;
import ba.unsa.etf.rpr.model.Worker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    private Employee employee;
    private SimpleStringProperty name,email,job,dep,hdate,edate,salary,cmp,man,tye;
    public TextField fieldEmployeeName;
    public TextField fieldEmail;
    public TextField fieldJob;
    public TextField fieldDepartment;
    public TextField fieldManager;
    public TextField fieldHireDate;
    public TextField fieldExpireDate;
    public TextField fieldSalary;
    public  TextField fieldCmp;
    public TextField filedTypeOfEmployment;
    @FXML
    private ImageView imgView;

    public ProfileController(Employee employee) {
        this.employee = employee;
        this.name= new SimpleStringProperty(employee.getEmployeeName());
        this.email = new SimpleStringProperty(employee.getEmail());
        this.job = new SimpleStringProperty(employee.getJob().getJobTitle());
        this.dep = new SimpleStringProperty(employee.getDepartment().getDepartmentName());
        this.hdate = new SimpleStringProperty(employee.getHireDate().toString());
        this.edate= new SimpleStringProperty(employee.getExpireDate().toString());
        this.salary= new SimpleStringProperty( ""+employee.getSalary());
        this.cmp = new SimpleStringProperty(""+employee.getCmp());
        if (employee instanceof Manager) this.man= new SimpleStringProperty("/");
        else if (employee instanceof  Worker){
            this.man = new SimpleStringProperty( ((Worker) employee).getManager().getEmployeeName());
        }
        this.tye= new SimpleStringProperty(employee.getEmployment().toString());
    }

    @FXML
    public void initialize() {
        fieldEmployeeName.textProperty().bindBidirectional(name);
        fieldEmployeeName.setEditable(false);

        fieldDepartment.textProperty().bindBidirectional(dep);
        fieldDepartment.setEditable(false);

        fieldEmail.textProperty().bindBidirectional(email);
        fieldEmail.setEditable(false);

        fieldJob.textProperty().bindBidirectional(job);
        fieldJob.setEditable(false);

        fieldSalary.textProperty().bindBidirectional(salary);
        fieldSalary.setEditable(false);

        fieldHireDate.textProperty().bindBidirectional(hdate);
        fieldHireDate.setEditable(false);

        fieldExpireDate.textProperty().bindBidirectional(edate);
        fieldExpireDate.setEditable(false);

        fieldCmp.textProperty().bindBidirectional(cmp);
        fieldCmp.setEditable(false);

        fieldManager.textProperty().bindBidirectional(man);
        fieldManager.setEditable(false);

        filedTypeOfEmployment.textProperty().bindBidirectional(tye);
        filedTypeOfEmployment.setEditable(false);
        imgView.setImage(
                new Image("images/application-exit.png")
        );

    }

    public void clickCancel (ActionEvent actionEvent) {

            Stage stage = new Stage();
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
                Stage thisstage = (Stage) fieldEmployeeName.getScene().getWindow();
                thisstage.close();
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public void changePassword (ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChangePassword.fxml"));
            ChangePasswordController changePasswordController = new ChangePasswordController(employee);
            loader.setController(changePasswordController);
            root = loader.load();
            stage.setTitle("Promijeni password");
            stage.setScene(new Scene(root, 600, 300));
            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
