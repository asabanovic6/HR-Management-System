package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.exception.NonExistentDepartment;
import ba.unsa.etf.rpr.model.*;
import ba.unsa.etf.rpr.utilities.HrDAO;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.Optional;


public class DepartmentsController {
   public TableView<Department> tableViewDepartments;
   public TableColumn colId;
   public TableColumn colName;

   private ObservableList<Department> departments;
   private HrDAO dao;
  private Employee employee;
  public Stage stage ;

    @FXML
    private ImageView imgView;


    public DepartmentsController(Employee employee) {
        this.employee=employee;
        this.dao= HrDAO.getInstance();
        this.departments= FXCollections.observableArrayList(dao.getDepartments());
        this.stage= new Stage();
    }

    @FXML
    public void initialize() {
       tableViewDepartments.setItems(departments);
        colId.setCellValueFactory(new PropertyValueFactory("departmentId"));
        colName.setCellValueFactory(new PropertyValueFactory("departmentName"));

        imgView.setImage(
                new Image("images/application-exit.png")
        );
    }

    public void addDepartment (ActionEvent actionEvent) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Department.fxml"));
            DepartmentController departmentController = new DepartmentController();
            loader.setController(departmentController);
            root = loader.load();
            stage.setTitle("Dodaj novi odjel");
            stage.setScene(new Scene(root));

            stage.setResizable(true);
            stage.show();
            stage.setOnHiding( event -> {
                Department dep = departmentController.getDepartment();
                if (dep != null) {
                    dao.addDepartment(dep);
                    tableViewDepartments.setItems(FXCollections.observableArrayList(dao.getDepartments()));
                }
            } );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDepartment (ActionEvent actionEvent) throws NonExistentDepartment {
        Department dep = tableViewDepartments.getSelectionModel().getSelectedItem();
        if (dep == null) return;
        if (employee  instanceof Worker) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Onemogućeno brisanje");
            alert.setHeaderText("Onemogućeno brisanje");
            alert.setContentText("Odjel može obrisati samo menadžer!");

            alert.showAndWait();
        }
        else if (dao.getEmployeesFromDepartment(dep.getDepartmentId()).size()>0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Onemogućeno brisanje");
            alert.setHeaderText("Onemogućeno brisanje");
            alert.setContentText("Ne možete obrisati odjel u kome ima zaposlenih!");

            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potvrda brisanja");
            alert.setHeaderText("Brisanje odjela " + dep.getDepartmentName());
            alert.setContentText("Da li ste sigurni da želite obrisati odjel " + dep.getDepartmentName() + "?");
            alert.setResizable(true);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                dao.deleteDepartment(dep.getDepartmentName());
                departments.setAll(dao.getDepartments());
                tableViewDepartments.setItems(departments);
            }
        }
    }
   public void clickCancel (ActionEvent actionEvent) {
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
           Stage thisstage = (Stage) tableViewDepartments.getScene().getWindow();
           thisstage.close();
           stage.show();

       } catch (IOException e) {
           e.printStackTrace();
       }

   }


}
