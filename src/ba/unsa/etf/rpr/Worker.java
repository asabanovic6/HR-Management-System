package ba.unsa.etf.rpr;

public class Worker extends Employee {

    private Manager manager;

    public Worker() {
    }

    public Worker(int employeeId, String employeeName, String email, String hireDate, int departmentId, int job, int salary, double cmp, String expireDate, Manager manager) {
        super(employeeId, employeeName, email, hireDate, departmentId, job, salary, cmp, expireDate);
        this.manager = manager;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
