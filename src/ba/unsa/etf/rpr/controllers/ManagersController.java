package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.exception.NonExistentDepartment;
import ba.unsa.etf.rpr.model.*;
import ba.unsa.etf.rpr.utilities.HrDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static javafx.scene.layout.Region.USE_PREF_SIZE;


public class ManagersController {
    private Employee employee;
    private ObservableList<Manager> employees;
    private HrDAO dao;
    public TableView<Manager> tableViewEmployees;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colEmail;
    public TableColumn<Manager,String> colDepartment;
    public TableColumn<Manager,String> colJob;
    public TableColumn<Manager,String> colManager;
    public TableColumn colHireDate;
    public TableColumn colExpireDate;
    public TableColumn colEmployment;
    public TableColumn colSalary;
    public TableColumn colCmp;
    @FXML
    private ImageView imgView;

    public ManagersController(Employee employee) throws NonExistentDepartment {
        this.employee=employee;
        this.dao=HrDAO.getInstance();
            this.employees= FXCollections.observableArrayList(dao.getAllManagers());

    }

    @FXML
    public void initialize() {
        tableViewEmployees.setItems(employees);
        colId.setCellValueFactory(new PropertyValueFactory("employeeId"));
        colName.setCellValueFactory(new PropertyValueFactory("employeeName"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        if (employee instanceof Worker) colEmployment.setVisible(false);
        else   colEmployment.setCellValueFactory(new PropertyValueFactory("employment"));
        if (employee instanceof Worker) colHireDate.setVisible(false);
        else   colHireDate.setCellValueFactory(new PropertyValueFactory("hireDate"));
        if (employee instanceof Worker) colExpireDate.setVisible(false);
        else colExpireDate.setCellValueFactory(new PropertyValueFactory("expireDate"));
        if (employee instanceof Worker) colSalary.setVisible(false);
        else colSalary.setCellValueFactory(new PropertyValueFactory("salary"));
        if (employee instanceof  Worker) colCmp.setVisible(false);
        else   colCmp.setCellValueFactory(new PropertyValueFactory("cmp"));
      colManager.setVisible(false);
      colDepartment.setVisible(false);
   // colDepartment.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment().getDepartmentName()));
        colJob.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJob().getJobTitle()));

        imgView.setImage(
                new Image("images/application-exit.png")
        );
    }
    public void clikcCancel (ActionEvent actionEvent) {
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
            Stage thisstage = (Stage) tableViewEmployees.getScene().getWindow();
            thisstage.close();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee (ActionEvent actionEvent) {
        if (employee  instanceof Worker) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Onemogućena akcija");
            alert.setHeaderText("Onemogućena akcija");
            alert.setContentText("Dodati zaposlene može samo menadžer!");

            alert.showAndWait();
        }
        else {
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Employee.fxml"));
                EmployeeController employeeController = new EmployeeController();
                loader.setController(employeeController);
                root = loader.load();
                stage.setTitle("Dodaj novog zaposlenog");
                stage.setScene(new Scene(root));

                stage.setResizable(true);
                stage.show();
                stage.setOnHiding(event -> {
                    Manager emp = (Manager) employeeController.getEmployee();
                    if (emp != null) {
                        dao.addManager(emp);
                        tableViewEmployees.setItems(FXCollections.observableArrayList(dao.getAllManagers()));
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeEmployee (ActionEvent actionEvent) {
        Employee emp = tableViewEmployees.getSelectionModel().getSelectedItem();
        if (emp == null) return;
        if (employee  instanceof Worker) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Onemogućena akcija");
            alert.setHeaderText("Onemogućena akcija");
            alert.setContentText("Izmjene može vršiti samo menadžer!");

            alert.showAndWait();
        }
        else {
            Stage stage = new Stage();
            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChangeEmployee.fxml"));
                ChangeEmployeeController changeEmployeeController = new ChangeEmployeeController(emp);
                loader.setController(changeEmployeeController);
                root = loader.load();
                stage.setTitle("Izmijeni zaspolenika");
                stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
                stage.setResizable(true);
                stage.show();

                stage.setOnHiding(event -> {
                    Employee empl = changeEmployeeController.getEmployee();
                    if (empl != null) {
                        // Ovdje ne smije doći do izuzetka jer se prozor neće zatvoriti
                        try {
                            dao.editEmployee(empl);
                            employees.setAll(dao.getAllManagers());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void resignFromWork (ActionEvent actionEvent) throws SQLException {
        Employee emp = tableViewEmployees.getSelectionModel().getSelectedItem();
        if (emp == null) return;
        if (employee  instanceof Worker) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Onemogućena akcija");
            alert.setHeaderText("Onemogućena akcija");
            alert.setContentText("Otkaz s posla može dati samo menadžer!");

            alert.showAndWait();
        }

        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda otkaza");
            alert.setHeaderText("Potvrda otkaza " + emp.getEmployeeName());
            alert.setContentText("Da li ste sigurni da želite raskinuti ugovor sa  " + emp.getEmployeeName() + "?");
            alert.setResizable(true);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                if (emp instanceof Worker)
                    dao.deleteWorker(emp.getEmployeeName());
                else dao.deleteManager(emp.getEmployeeName());
                employees.setAll(dao.getAllManagers());
                tableViewEmployees.setItems(employees);
            }
        }
    }

}
