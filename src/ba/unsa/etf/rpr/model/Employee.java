package ba.unsa.etf.rpr.model;

import ba.unsa.etf.rpr.enums.TypeOfEmployment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String email;
    private LocalDate hireDate;
    private Department department;
    private Job job;
    private int Salary;
    private double cmp;
    private LocalDate expireDate;
    private TypeOfEmployment employment;
    private String password;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String email, String hireDate, Department department, Job job, int salary, double cmp, String expireDate,String password) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.hireDate = LocalDate.parse(hireDate, formatter);

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.department = department;
        this.job= job;
        Salary = salary;
        this.cmp = cmp;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.expireDate = LocalDate.parse(expireDate, formatter1);

        this.employment=TypeOfEmployment.TEMPORARY;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.hireDate = LocalDate.parse(hireDate, formatter);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
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

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.expireDate = LocalDate.parse(expireDate, formatter1);
    }

    public TypeOfEmployment getEmployment() {
        return employment;
    }

    public void setEmployment(TypeOfEmployment employment) {
        this.employment = employment;
    }

}
