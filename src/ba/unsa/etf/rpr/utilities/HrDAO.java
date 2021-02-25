package ba.unsa.etf.rpr.utilities;

import ba.unsa.etf.rpr.exception.NonExistentDepartment;
import ba.unsa.etf.rpr.exception.NonExistentLocation;
import ba.unsa.etf.rpr.model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HrDAO implements  IHumanResource{
    private static HrDAO instance;
    private Connection conn;
    private PreparedStatement getEmployeePS,getDepartmentPS,getEmployeesFromDepartmentPS,getManagerFromDepartmentPS,getWorkersFromManagerPS,
            getDepartmentsPS,getDepartmentsOnLocationPS,getLocationsPS,getLocationPS,getJobsPS,searchEmployeePS,deleteWorkerPS,
            searchDepartmentPS,deleteDepartmentPS,deleteManagerPS,addJobPS,addDepartmentPS,addLocationPS,addEmployeePS,determineJobIdPS,determineDepartmentIdPS,determineLocationIdPS,determineEmployeeIdPS,
            getJobPS,editEmployeePS,getJobbyNamePS,changePasswordPS,getEmployeeByEmailPS,getDepartmentbyNamePS,getManagersPs,searchLocationsPS, getPasswordPS,getPasswordOfEmployeePS,getEmployeesPS;

    public static HrDAO getInstance() {
        if (instance==null) instance= new HrDAO();
        return  instance;
    }

    private HrDAO () {
        try {

            conn= DriverManager.getConnection("jdbc:sqlite:baza.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getEmployeePS = conn.prepareStatement("SELECT * FROM employees WHERE employee_id=?");
        }
        catch (SQLException e) {
            e.printStackTrace();
            regenerateDataBase();
            try {
                getEmployeePS = conn.prepareStatement("SELECT * FROM employees WHERE employee_id=?");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        try {
            getEmployeesFromDepartmentPS = conn.prepareStatement("SELECT * FROM employees WHERE department_id=? ORDER BY employee_name");
            getManagerFromDepartmentPS = conn.prepareStatement("SELECT * FROM employees WHERE manager_id=0 AND department_id=?");
            getWorkersFromManagerPS = conn.prepareStatement("SELECT * FROM employees WHERE manager_id=? ORDER BY employee_name");
            getDepartmentsPS = conn.prepareStatement("SELECT * FROM departments");

            getDepartmentPS=conn.prepareStatement("SELECT * FROM departments WHERE department_id=?");
           getDepartmentbyNamePS =conn.prepareStatement("SELECT * FROM departments WHERE department_name=?");
            getDepartmentsOnLocationPS = conn.prepareStatement("SELECT * FROM departments WHERE location_id=?");
            getLocationsPS= conn.prepareStatement("SELECT * FROM locations");
            getLocationPS=conn.prepareStatement("SELECT * FROM locations WHERE location_id=?");
            getJobPS = conn.prepareStatement("SELECT * FROM jobs WHERE job_id=?");
            getJobsPS = conn.prepareStatement("SELECT * FROM jobs");
            getJobbyNamePS = conn.prepareStatement("SELECT * FROM jobs WHERE job_title=?");
            getPasswordPS = conn.prepareStatement("SELECT password FROM employees WHERE email=?");
            getEmployeesPS = conn.prepareStatement("SELECT * FROM employees");
            getEmployeeByEmailPS = conn.prepareStatement("SELECT * FROM employees WHERE email=?");
            getManagersPs = conn.prepareStatement("SELECT * FROM employees WHERE manager_id=0 ");
            searchEmployeePS = conn.prepareStatement("SELECT * FROM employees WHERE employee_name=?");
            searchLocationsPS = conn.prepareStatement("SELECT * FROM locations WHERE city=?");
            searchDepartmentPS = conn.prepareStatement("SELECT * FROM departments WHERE department_name=?");

            deleteWorkerPS = conn.prepareStatement("DELETE FROM employees WHERE employee_id=?");
            deleteManagerPS = conn.prepareStatement("DELETE FROM employees WHERE employee_id=?");
            deleteDepartmentPS = conn.prepareStatement("DELETE FROM departments WHERE department_id=?");

            addJobPS = conn.prepareStatement("INSERT INTO jobs VALUES(?,?,?,?)");
            addDepartmentPS = conn.prepareStatement("INSERT INTO departments VALUES(?,?,?,?)");
            addLocationPS = conn.prepareStatement("INSERT INTO locations VALUES(?,?)");
            addEmployeePS = conn.prepareStatement("INSERT INTO employees VALUES (?,?,?,?,?,?,?,?,?,?,?)");

            determineJobIdPS = conn.prepareStatement("SELECT MAX(job_id)+1 FROM jobs");
            determineDepartmentIdPS = conn.prepareStatement("SELECT MAX(department_id)+1 FROM departments");
            determineLocationIdPS = conn.prepareStatement("SELECT MAX(location_id)+1 FROM locations");
            determineEmployeeIdPS = conn.prepareStatement("SELECT MAX(employee_id)+1 FROM employees");

            editEmployeePS = conn.prepareStatement("UPDATE employees SET expire_date=?, salary=?, cmp=? WHERE employee_id=?");
            changePasswordPS = conn.prepareStatement("UPDATE employees SET password=? WHERE email=?");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //We use this method if we want to change salary, commission precentage or expireDate
    public void editEmployee (Employee employee)  {
        try {
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String expireDate = formatter1.format(employee.getExpireDate());
            editEmployeePS.setString(1,expireDate);
            editEmployeePS.setInt(2,employee.getSalary());
            editEmployeePS.setDouble(3,employee.getCmp());
            editEmployeePS.setInt(4,employee.getEmployeeId());
            editEmployeePS.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    public void changePassword (Employee employee,String newPassword)  {
        try {
            changePasswordPS.setString(1,newPassword);
           changePasswordPS.setString(2,employee.getEmail());
           changePasswordPS.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewManagerToDepartment (Manager employee, Department department) throws NonExistentDepartment {
        try {
            ResultSet rs = determineEmployeeIdPS.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addEmployeePS.setInt(1, id);
            addEmployeePS.setString(2,employee.getEmployeeName());
            addEmployeePS.setString(3,employee.getEmail());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String hireDate = formatter.format(employee.getHireDate());
            addEmployeePS.setString(4,hireDate);
            addEmployeePS.setInt(5,department.getDepartmentId());
            addEmployeePS.setInt(6,0);
            addEmployeePS.setInt(7,1);
            addEmployeePS.setInt(8,employee.getSalary());
            addEmployeePS.setDouble(9,employee.getCmp());
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String expireDate = formatter1.format(employee.getExpireDate());
            addEmployeePS.setString(10,expireDate);
            addEmployeePS.setString(11,employee.getPassword());
            addEmployeePS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Employee> employees = getEmployeesFromDepartment(department.getDepartmentId());
        for (Employee e:employees) {
            if (e instanceof Worker) ((Worker) e).setManager(employee);
        }
    }

    public void addManager (Manager employee) {
        try {
            ResultSet rs = determineEmployeeIdPS.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addEmployeePS.setInt(1, id);
            addEmployeePS.setString(2,employee.getEmployeeName());
            addEmployeePS.setString(3,employee.getEmail());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String hireDate = formatter.format(employee.getHireDate());
            addEmployeePS.setString(4,hireDate);
            addEmployeePS.setInt(5,employee.getDepartment().getDepartmentId());
            addEmployeePS.setInt(6,0);
            addEmployeePS.setInt(7,1);
            addEmployeePS.setInt(8,employee.getSalary());
            addEmployeePS.setDouble(9,employee.getCmp());
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String expireDate = formatter1.format(employee.getExpireDate());
            addEmployeePS.setString(10,expireDate);
            addEmployeePS.setString(11,employee.getPassword());
            addEmployeePS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addWorker (Worker employee) {
        try {
            ResultSet rs = determineEmployeeIdPS.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addEmployeePS.setInt(1, id);
            addEmployeePS.setString(2,employee.getEmployeeName());
            addEmployeePS.setString(3,employee.getEmail());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String hireDate = formatter.format(employee.getHireDate());
            addEmployeePS.setString(4,hireDate);
            addEmployeePS.setInt(5,employee.getDepartment().getDepartmentId());
            addEmployeePS.setInt(6,employee.getManager().getEmployeeId());
            addEmployeePS.setInt(7,employee.getJob().getJobId());
            addEmployeePS.setInt(8,employee.getSalary());
            addEmployeePS.setDouble(9,employee.getCmp());
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String expireDate = formatter1.format(employee.getExpireDate());
            addEmployeePS.setString(10,expireDate);
            addEmployeePS.setString(11,employee.getPassword());
            addEmployeePS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addLocation (Location location) {
        try {
            ResultSet rs = determineLocationIdPS.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addLocationPS.setInt(1, id);
            addLocationPS.setString(2,location.getCity());
            addLocationPS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addJob (Job job) {
        try {
            ResultSet rs = determineJobIdPS.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addJobPS.setInt(1, id);
            addJobPS.setString(2,job.getJobTitle());
            addJobPS.setInt(3, job.getMinSalary());
            addJobPS.setInt(4, job.getMaxSalary());
            addJobPS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addDepartment (Department department) {
        try {
            ResultSet rs = determineDepartmentIdPS.executeQuery();
            int id = 1;
            if (rs.next()) {
                id = rs.getInt(1);
            }

            addDepartmentPS.setInt(1,id);
            addDepartmentPS.setString(2,department.getDepartmentName());
            addDepartmentPS.setInt(3,department.getManagerId());
            addDepartmentPS.setInt(4,department.getLocationId());
            addDepartmentPS.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteDepartment(String departmentName) {
        try {
            searchDepartmentPS.setString(1, departmentName);
            ResultSet rs = searchDepartmentPS.executeQuery();
            if (!rs.next()) return;
            Department dep = getDepartmentFromResultSet(rs);
            deleteDepartmentPS.setInt(1, dep.getDepartmentId());
            deleteDepartmentPS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteManager (String managerName) throws SQLException {
        searchEmployeePS.setString(1, managerName);
        ResultSet rs = searchEmployeePS.executeQuery();
        if (!rs.next()) return;
        Manager manager = getManagerFromResultSet(rs);
        ArrayList<Worker> workers= new ArrayList<>();
        workers=getWorkersFromManager(manager.getEmployeeId());
        if (workers.isEmpty()) {
            deleteManagerPS.setInt(1, manager.getEmployeeId());
            deleteManagerPS.executeUpdate();
        }
        else {
            workers.forEach(worker->worker.setManager(null));
        }

    }
    public void deleteWorker(String employeeName) {
        try {
            searchEmployeePS.setString(1, employeeName);
            ResultSet rs = searchEmployeePS.executeQuery();
            if (!rs.next()) return;
            Worker worker = (Worker) getEmployeeFromResultSet(rs);
            deleteWorkerPS.setInt(1, worker.getEmployeeId());
            deleteWorkerPS.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Department> getDepartmentsOnLocation (int locationId) throws NonExistentLocation {
        if (!doesLocationExist(locationId)) throw new NonExistentLocation("This location doesn't exist in data base!");
        ArrayList<Department> result = new ArrayList<>();
        try {
            getDepartmentsOnLocationPS.setInt(1,locationId);
            ResultSet rs = getDepartmentsOnLocationPS.executeQuery();
            while (rs.next()) {
                Department department= getDepartmentFromResultSet(rs);
                result.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<Worker> getWorkersFromManager (int managerId) {
        ArrayList<Worker> result = new ArrayList<>();
        try {
            getWorkersFromManagerPS.setInt(1,managerId);
            ResultSet rs=getWorkersFromManagerPS.executeQuery();
            while (rs.next()) {
                Worker workers = (Worker) getEmployeeFromResultSet(rs);
                result.add(workers);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }



    public ArrayList<Employee> getEmployeesFromDepartment(int departmentId) throws NonExistentDepartment {
        if (!doesDepartmentExist(departmentId)) throw new NonExistentDepartment("This department doesn't exist!");
        ArrayList<Employee> result = new ArrayList();
        try {
            getEmployeesFromDepartmentPS.setInt(1,departmentId);
            ResultSet rs = getEmployeesFromDepartmentPS.executeQuery();
            while (rs.next()) {
                Employee employee =  getEmployeeFromResultSet(rs);
                result.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Manager getManagerFromDepartment(int departmentId) throws NonExistentDepartment {
        if (!doesDepartmentExist(departmentId)) throw new NonExistentDepartment("This department doesn't exist!");
        Manager manager = new Manager();
        try {
            getManagerFromDepartmentPS.setInt(1, departmentId);
            ResultSet rs = getManagerFromDepartmentPS.executeQuery();
            manager = getManagerFromResultSet(rs);

        } catch (SQLException  e) {
            e.printStackTrace();
        }
        return manager;
    }


    private boolean doesLocationExist (int locationId) {
        ArrayList<Location> locs = new ArrayList<>();
        locs = getLocations();
        for (Location loc : locs) {
            if (loc.getLocationId()==locationId) return true;
        }
        return false;
    }
    private boolean doesDepartmentExist (int departmentId) {
        ArrayList<Department> deps = new ArrayList<>();
        deps = getDepartments();
        for (Department dep : deps) {
            if (dep.getDepartmentId()==departmentId) return true;
        }
        return false;
    }
    public ArrayList<Department> getDepartments() {
        ArrayList<Department> result = new ArrayList();
        try {
            ResultSet rs = getDepartmentsPS.executeQuery();
            while (rs.next()) {
                Department dep = getDepartmentFromResultSet(rs);
                result.add(dep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<String> getDepartmentsByNames() {
        ArrayList<String> result = new ArrayList();
        try {
            ResultSet rs = getDepartmentsPS.executeQuery();
            while (rs.next()) {
                String dep = getDepartmentNameFromResultSet(rs);
                result.add(dep);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<Location> getLocations() {
        ArrayList<Location> result = new ArrayList();
        try {
            ResultSet rs = getLocationsPS.executeQuery();
            while (rs.next()) {
                Location loc = getLocationFromResultSet(rs);
                result.add(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<String> getEmails () {
        ArrayList<String> result = new ArrayList();
        try {
            ResultSet rs = getEmployeesPS.executeQuery();
            while (rs.next()) {
                Employee emp = getEmployeeFromResultSet(rs);
                result.add(emp.getEmail());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<String> getLocationsName() {
        ArrayList<String> result = new ArrayList();
        try {
            ResultSet rs = getLocationsPS.executeQuery();
            while (rs.next()) {
                Location loc = getLocationFromResultSet(rs);
                result.add(loc.getCity());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<String> getManagers() {
        ArrayList<String> result = new ArrayList();
        try {
            ResultSet rs = getManagersPs.executeQuery();
            while (rs.next()) {
                Manager manager = getManagerFromResultSet(rs);
                result.add(manager.getEmployeeName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<String> getJobs() {
        ArrayList<String> result = new ArrayList();
        try {
            ResultSet rs = getJobsPS.executeQuery();
            while (rs.next()) {
                String job = getJobsNameFromResultSet(rs);
                result.add(job);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public HashMap<String, String> getLogInData() {
        HashMap<String, String> logs = new HashMap<>();
        ArrayList<String> emails = new ArrayList<>();
        emails = getEmails();
        for (String email : emails) {
            logs.put(email,getPassword(email));
        }
        return logs;
    }

    public String getPassword (String email ) {
        try {
            getPasswordPS.setString(1, email);
            ResultSet rs = getPasswordPS.executeQuery();
            if (!rs.next()) return null;
            return getPasswordFromResultset(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }



    public Job getJob(int id) {
        try {
            getJobPS.setInt(1, id);
            ResultSet rs = getJobPS.executeQuery();
            if (!rs.next()) return null;
            return getJobFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Job getJobbyName(String name) {
        try {
            getJobbyNamePS.setString(1, name);
            ResultSet rs = getJobbyNamePS.executeQuery();
            if (!rs.next()) return null;
            return getJobFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Employee getEmployee(int id) {
        try {
            getEmployeePS.setInt(1, id);
            ResultSet rs = getEmployeePS.executeQuery();
            if (!rs.next()) return null;
            return getEmployeeFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Employee getEmployee(String email) {
        try {
            getEmployeeByEmailPS.setString(1, email);
            ResultSet rs = getEmployeeByEmailPS.executeQuery();
            if (!rs.next()) return null;
            return getEmployeeFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Employee searchEmployeeByName (String  name) {
        try {
            searchEmployeePS.setString(1, name);
            ResultSet rs = searchEmployeePS.executeQuery();
            if (!rs.next()) return null;
            return getEmployeeFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Department searchDepartmentbyName(String name) {
        try {
            searchDepartmentPS.setString(1, name);
            ResultSet rs = searchDepartmentPS.executeQuery();
            if (!rs.next()) return null;
            return getDepartmentFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Location searchLocationsByName (String  name) {
        try {
            searchLocationsPS.setString(1, name);
            ResultSet rs = searchLocationsPS.executeQuery();
            if (!rs.next()) return null;
            return getLocationFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Manager getManager (int id) {
        try {
            getEmployeePS.setInt(1, id);
            ResultSet rs = getEmployeePS.executeQuery();
            if (!rs.next()) return null;
            else if (rs.getInt(6)==0) return getManagerFromResultSet(rs); // If employee is manager then his/her ManagerID is 0
            else throw new NullPointerException("This employee is not manager!");    // If employee is not manager then he/she is worker and he/she has manager, and his/her ManagerId is EmployeeID of his/her manager
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Department getDepartment (int id) {
        try {
            getDepartmentPS.setInt(1, id);
            ResultSet rs = getDepartmentPS.executeQuery();
            if (!rs.next()) return null;
            return getDepartmentFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Department getDepartmentByName (String name) {
        try {
            getDepartmentbyNamePS.setString(1, name);
            ResultSet rs = getDepartmentPS.executeQuery();
            if (!rs.next()) return null;
            return getDepartmentFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Location getLocation (int id) {
        try {
            getLocationPS.setInt(1, id);
            ResultSet rs = getLocationPS.executeQuery();
            if (!rs.next()) return null;
            return getLocationFromResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String getLocationName (int id) {
        try {
            getLocationPS.setInt(1, id);
            ResultSet rs = getLocationPS.executeQuery();
            if (!rs.next()) return null;
            return getLocationFromResultSet(rs).getCity();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Department dep = getDepartment(rs.getInt(5));
        Job job = getJob(rs.getInt(7));
        if (rs.getInt(6) == 0) {
            return new Manager(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),dep, job, rs.getInt(8), rs.getDouble(9), rs.getString(10),rs.getString(11), rs.getInt(6));
        }else {

            Manager manager = getManager(rs.getInt(6));
            return new Worker(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), dep, job, rs.getInt(8), rs.getDouble(9), rs.getString(10),rs.getString(11), manager);
        }
    }
    private String getPasswordFromResultset(ResultSet rs) throws SQLException {
   return rs.getString(1);
    }

    private Manager getManagerFromResultSet(ResultSet rs) throws SQLException {
        Department dep = getDepartment(rs.getInt(5));
        Job job = getJob(rs.getInt(7));
        return new Manager (rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), dep, job, rs.getInt(8), rs.getDouble(9), rs.getString(10),rs.getString(11),rs.getInt(6));
    }

    private Department getDepartmentFromResultSet ( ResultSet rs) throws SQLException {
        return new Department(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
    }
    private String getDepartmentNameFromResultSet ( ResultSet rs) throws SQLException {
        Department d=  new Department(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
        return d.getDepartmentName();
    }
    private Location getLocationFromResultSet ( ResultSet rs) throws SQLException {
        return new Location (rs.getInt(1),rs.getString(2));
    }

    private Job getJobFromResultSet ( ResultSet rs) throws SQLException {
        return  new Job (rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
    }
    private String getJobsNameFromResultSet ( ResultSet rs) throws SQLException {
        Job j=  new Job (rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
        return j.getJobTitle();
    }


    public static void removeInstance() {
        if (instance == null) return;
        instance.close();
        instance = null;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void regenerateDataBase() {
        Scanner ulaz= null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));

            String sqlUpit = "";
            while (ulaz.hasNext()) {

                sqlUpit += ulaz.nextLine();
                if ( sqlUpit.length() > 1 && sqlUpit.charAt( sqlUpit.length()-1 ) == ';') {
                    try {
                        Statement stmt = conn.createStatement();
                        stmt.execute(sqlUpit);
                        sqlUpit = "";
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            ulaz.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //  This method will take a database to default
    public void returnBaseOnDefault() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("DELETE FROM employees");
        stmt.executeUpdate("DELETE FROM jobs");
        stmt.executeUpdate("DELETE FROM departments");
        stmt.executeUpdate("DELETE FROM locations");
        regenerateDataBase();
    }



}
