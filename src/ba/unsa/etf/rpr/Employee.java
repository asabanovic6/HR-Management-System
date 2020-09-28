package ba.unsa.etf.rpr;

import java.time.LocalDateTime;

public class Employee {
    private int employeeId;
    private String employeeName;
    private String email;
    private LocalDateTime hireDate;
    private Department department;
    private Job job;
    private int Salary;
    private double cmp;
    private LocalDateTime expireDate;

    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String email, LocalDateTime hireDate, Department department, Job job, int salary, double cmp, LocalDateTime expireDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.hireDate = hireDate;
        this.department = department;
        this.job = job;
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

    public LocalDateTime getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
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

    public LocalDateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDateTime expireDate) {
        this.expireDate = expireDate;
    }
}
