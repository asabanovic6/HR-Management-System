package ba.unsa.etf.rpr;


import java.time.LocalDateTime;
import java.util.Date;

public class Manager extends Employee {

    private int managerId;


    public Manager(int employeeId, String employeeName, String email, Date hireDate, int departmentId, int jobId, int salary, double cmp, Date expireDate, int managerId) {
        super(employeeId, employeeName, email, hireDate, departmentId, jobId, salary, cmp, expireDate);
        this.managerId = managerId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
