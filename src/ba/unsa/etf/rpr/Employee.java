package ba.unsa.etf.rpr;

import java.time.LocalDateTime;
import java.util.Date;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String email;
    private Date hireDate;
    private int departmentId;
    private int jobId;
    private int Salary;
    private double cmp;
    private Date expireDate;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String email, Date hireDate, int departmentId, int jobId, int salary, double cmp, Date expireDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.hireDate = hireDate;
        this.departmentId = departmentId;
        this.jobId = jobId;
        Salary = salary;
        this.cmp = cmp;
        this.expireDate = expireDate;
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

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
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

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

}
