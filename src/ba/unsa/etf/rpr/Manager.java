package ba.unsa.etf.rpr;


public class Manager extends Employee {

   private int managerId;

    public Manager() {
    }

    public Manager(int employeeId, String employeeName, String email, String hireDate, int departmentId, int jobId, int salary, double cmp, String expireDate, int managerId) {
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
