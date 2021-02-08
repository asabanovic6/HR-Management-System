package ba.unsa.etf.rpr.model;


import ba.unsa.etf.rpr.model.Department;
import ba.unsa.etf.rpr.model.Employee;
import ba.unsa.etf.rpr.model.Job;

public class Manager extends Employee {

   private int managerId;

    public Manager() {
    }

    public Manager(int employeeId, String employeeName, String email, String hireDate, Department department, Job job, int salary, double cmp, String expireDate, int managerId) {
        super(employeeId, employeeName, email, hireDate, department, job, salary, cmp, expireDate);
        this.managerId = managerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
