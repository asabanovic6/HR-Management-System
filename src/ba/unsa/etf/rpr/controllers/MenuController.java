package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.exception.NonExistentDepartment;
import ba.unsa.etf.rpr.model.Employee;

import ba.unsa.etf.rpr.model.Location;
import ba.unsa.etf.rpr.report.Report;
import ba.unsa.etf.rpr.utilities.HrDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuController {
    public Label label1;
   private  Employee employee;
   private HrDAO dao ;
   public Stage stage ;

    public MenuController(Employee employee) {

        this.employee=employee;
        this.dao = HrDAO.getInstance();
        this.stage= new Stage();
    }

    public void departments (ActionEvent actionEvent) {

        Parent root = null;
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/Departments.fxml" ), bundle);            DepartmentsController departmentsController = new DepartmentsController(employee);
            loader.setController(departmentsController);
            root = loader.load();
            Locale.setDefault(new Locale("bs", "BA"));
            stage.setTitle(bundle.getString("Departments"));
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void employees (ActionEvent actionEvent) {
        Parent root = null;
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/EmployeesInDepartment.fxml" ), bundle);            EmployeesFromDepController employeesFromDepController = new EmployeesFromDepController(employee);
            loader.setController(employeesFromDepController);
            root = loader.load();
            Locale.setDefault(new Locale("bs", "BA"));
            stage.setTitle(bundle.getString("EmployeesStage"));
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();
        } catch (IOException | NonExistentDepartment e) {
            e.printStackTrace();
        }
    }
   public void managers (ActionEvent actionEvent) {

       Parent root = null;
       try {

           ResourceBundle bundle = ResourceBundle.getBundle("Translation");
           FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/EmployeesInDepartment.fxml" ), bundle);           ManagersController managersController = new ManagersController(employee);
           loader.setController(managersController);
           root = loader.load();
           Locale.setDefault(new Locale("bs", "BA"));
           stage.setTitle(bundle.getString("ManagersStage"));
           stage.setScene(new Scene(root));
           stage.setMaximized(true);
           stage.setResizable(true);
           stage.show();
           Stage thisStage = (Stage) label1.getScene().getWindow();
           thisStage.close();
       } catch (IOException | NonExistentDepartment e) {
           e.printStackTrace();
       }
   }
    public void report ( ActionEvent actionEvent) {
        try {
            new Report().showReport(dao.getConnection());
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }
    public void selfProfile (ActionEvent actionEvent) {

        Parent root = null;
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/Profile.fxml" ), bundle);
            ProfileController profileController = new ProfileController(employee);
            loader.setController(profileController);
            root = loader.load();
            Locale.setDefault(new Locale("bs", "BA"));
            stage.setTitle(bundle.getString("ProfileStage"));
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logOut (ActionEvent actionEvent) {

        Parent root = null;
        try {

            ResourceBundle bundle = ResourceBundle.getBundle("Translation");
            FXMLLoader loader = new FXMLLoader( getClass().getResource("/fxml/LogIn.fxml" ), bundle);
            LogInController logInController = new LogInController();
            loader.setController(logInController);
            root = loader.load();
            Locale.setDefault(new Locale("bs", "BA"));
            stage.setTitle(bundle.getString("LogInStage"));
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setResizable(true);
            stage.show();
            Stage thisStage = (Stage) label1.getScene().getWindow();
            thisStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
