package ba.unsa.etf.rpr.utilities;

import ba.unsa.etf.rpr.exception.NonExistentDepartment;
import ba.unsa.etf.rpr.exception.NonExistentLocation;
import ba.unsa.etf.rpr.model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface IHumanResource {

    //Edit - methods
    void editEmployee(Employee employee);

    //Add - methods
    void addNewManagerToDepartment (Manager employee, Department department) throws NonExistentDepartment;
    void addManager (Manager employee);
    void addWorker (Worker employee);
    void addLocation (Location location);
    void addJob (Job job);
    void addDepartment (Department department);

    //Get - methods
    ArrayList<Department> getDepartmentsOnLocation (int locationId) throws NonExistentLocation;
    ArrayList<Worker> getWorkersFromManager (int managerId);
    ArrayList<Employee> getEmployeesFromDepartment(int departmentId) throws NonExistentDepartment;
    Manager getManagerFromDepartment(int departmentId) throws NonExistentDepartment;
    ArrayList<Department> getDepartments();
    ArrayList<String> getDepartmentsByNames();
    ArrayList<Location> getLocations();
    ArrayList<String> getLocationsName();
    ArrayList<String> getManagers();
    ArrayList<String> getJobs();
    HashMap<String,String> getLogInData() ;  //HashMap with all emails and passwords from database
    Job getJob(int id);
    Job getJobbyName(String name);
    Employee getEmployee(int id);
    Manager getManager (int id);
    Department getDepartment (int id);
    Department getDepartmentByName (String name);
    Location getLocation (int id);
    Employee searchEmployeeByName (String  name);
    Department searchDepartmentbyName(String name);
    Location searchLocationsByName (String  name);

    //Delete - methods
    void deleteDepartment(String departmentName);
    void deleteManager (String managerName) throws SQLException;
    void deleteWorker(String employeeName);


}
