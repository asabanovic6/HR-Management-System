package ba.unsa.etf.rpr.utilities;

import ba.unsa.etf.rpr.utilities.Department;
import ba.unsa.etf.rpr.utilities.Employee;
import ba.unsa.etf.rpr.utilities.Job;
import ba.unsa.etf.rpr.utilities.Manager;

public class Worker extends Employee {

    private Manager manager;

    public Worker() {
    }

    public Worker(int employeeId, String employeeName, String email, String hireDate, Department department, Job job, int salary, double cmp, String expireDate, Manager manager) {
        super(employeeId, employeeName, email, hireDate, department, job, salary, cmp, expireDate);
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
