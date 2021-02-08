package ba.unsa.etf.rpr.utilities;

import java.util.Objects;

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

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return getDepartmentId() == that.getDepartmentId() &&
                getManagerId() == that.getManagerId() &&
                getLocationId() == that.getLocationId() &&
                Objects.equals(getDepartmentName(), that.getDepartmentName());
    }


}
