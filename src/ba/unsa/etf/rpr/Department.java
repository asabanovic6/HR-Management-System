package ba.unsa.etf.rpr;

public class Department {
    private int departmentId;
    private String departmentName;
    private int managerId;
    private int locationId;

    public Department() {
    }

    public Department(int departmentId, String departmentName,int managerId, int locationId) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId=managerId;
        this.locationId = locationId;
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

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int location) {
        this.locationId = location;
    }
}
