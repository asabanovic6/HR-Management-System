package ba.unsa.etf.rpr;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String email;
    private LocalDateTime hireDate;
    private int departmentId;
    private int jobId;
    private int Salary;
    private double cmp;
    private LocalDateTime expireDate;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String email, String hireDate, int departmentId, int jobId, int salary, double cmp, String expireDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.hireDate = LocalDateTime.parse(hireDate, formatter);

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.departmentId = departmentId;
        this.jobId = jobId;
        Salary = salary;
        this.cmp = cmp;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.expireDate = LocalDateTime.parse(expireDate, formatter1);
    }


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.hireDate = LocalDateTime.parse(hireDate, formatter);
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public double getCmp() {
        return cmp;
    }

    public void setCmp(double cmp) {
        this.cmp = cmp;
    }

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.expireDate = LocalDateTime.parse(expireDate, formatter1);
    }

}
