package ba.unsa.etf.rpr;

public class Department {
    private int departmentId;
    private String departmentName;
    private Location location;

    public Department() {
    }

    public Department(int departmentId, String departmentName, Location location) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
