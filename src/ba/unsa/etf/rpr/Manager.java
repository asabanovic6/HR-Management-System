package ba.unsa.etf.rpr;

import org.assertj.core.internal.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

public class Manager extends Employee {

    private int managerId;


    public Manager(int employeeId, String employeeName, String email, LocalDateTime hireDate, Department department, Job job, int salary, double cmp,LocalDateTime expireDate, int managerId) {
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
