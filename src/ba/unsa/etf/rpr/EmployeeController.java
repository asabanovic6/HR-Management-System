package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import org.assertj.core.internal.bytebuddy.asm.Advice;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeController {

    public TextField fieldEmployeeName;
    public TextField fieldEmail;
    public TextField fieldManager;
    public TextField fieldJobId;
    public TextField fieldDepartment;
    public TextField fieldSalary;
    public TextField fieldCmp;
    public DatePicker pickerHireDate;
    public DatePicker pickerExpireDate;
    public ChoiceBox<Job> choiceJob;
    public ObservableList<Job> jobs= FXCollections.observableArrayList();
    private Employee employee;


    public EmployeeController(Employee employee, ArrayList<Job> jobs) {
        this.employee = employee;
        this.jobs = FXCollections.observableArrayList(jobs);
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
              if (job.getJobId() == employee.getJobId())
                  choiceJob.getSelectionModel().select(job);
          fieldDepartment.setText(Integer.toString(employee.getDepartmentId()));
          fieldSalary.setText(Integer.toString(employee.getSalary()));
          pickerHireDate.setValue(employee.getHireDate());
          pickerExpireDate.setValue(employee.getExpireDate());
          fieldCmp.setText(Double.toString(employee.getCmp()));
      }
      else {
          choiceJob.getSelectionModel().selectFirst();
      }
  }

    public Employee getEmployee() {
        return employee;
    }

    public void clickCancel(ActionEvent actionEvent) {
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
        employee.setDepartmentId(Integer.parseInt(fieldDepartment.getText()));
        employee.setJobId(choiceJob.getValue().getJobId());
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

