package ba.unsa.etf.rpr;

import java.time.LocalDateTime;

public class Manager extends Employee {
    private int managerId;


    public Manager(int employeeId, String employeeName, String email, LocalDateTime hireDate, Department department, Job job, int salary, double cmp, int managerId) {
        super(employeeId, employeeName, email, hireDate, department, job, salary, cmp);
        this.managerId = managerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
