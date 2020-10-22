package ba.unsa.etf.rpr;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String email;
    private LocalDate hireDate;
    private int departmentId;
    private int jobId;
    private int Salary;
    private double cmp;
    private LocalDate expireDate;
    private TypeOfEmployment employment;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String email, String hireDate, int departmentId, int job, int salary, double cmp, String expireDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.hireDate = LocalDate.parse(hireDate, formatter);

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.departmentId = departmentId;
        this.jobId = job;
        Salary = salary;
        this.cmp = cmp;

        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.expireDate = LocalDate.parse(expireDate, formatter1);

        if (expireDate!=null && expireDate!="") this.employment=TypeOfEmployment.PERMANENT;
        else this.employment=TypeOfEmployment.TEMPORARY;
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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int job) {
        this.jobId = job;
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
