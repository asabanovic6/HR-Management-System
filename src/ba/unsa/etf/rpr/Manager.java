package ba.unsa.etf.rpr;


public class Manager extends Employee {

   private int managerId;

    public Manager() {
    }

    public Manager(int employeeId, String employeeName, String email, String hireDate, int departmentId, int jobId, int salary, double cmp, String expireDate, int manager_id) {
        super(employeeId, employeeName, email, hireDate, departmentId, jobId, salary, cmp, expireDate);
        this.managerId = manager_id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}
