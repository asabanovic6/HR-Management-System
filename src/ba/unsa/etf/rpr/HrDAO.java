package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HrDAO {
    private static HrDAO instance;
    private Connection conn;
    private PreparedStatement getEmployeePS,getDepartmentPS,getEmployeesFromDepartmentPS,getManagerFromDepartmentPS,getWorkersFromManagerPS,getDepartmentsPS;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Employee> getEmployeesFromDepartment(int departmentId) throws NonExistentDepartment {
        if (!doesDepartmentExist(departmentId)) throw new NonExistentDepartment("This department doesn't exist!");
        ArrayList<Employee> result = new ArrayList();
        try {
            getEmployeesFromDepartmentPS.setInt(1,departmentId);
            ResultSet rs = getEmployeesFromDepartmentPS.executeQuery();
            while (rs.next()) {
                Employee employee = getEmployeeFromResultSet(rs);
                result.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean doesDepartmentExist (int departmentId) {
        ArrayList<Department> deps = new ArrayList<>();
        deps = getDepartments();
        for (Department dep : deps) {
            if (dep.getDepartmentId()==departmentId) return true;
        }
        return false;
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
    private Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException {
        if (rs.getInt(6) == 0)
            return new Manager(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(7), rs.getInt(8), rs.getDouble(9), rs.getString(10), rs.getInt(6));
        else {
            Manager manager = getManager(rs.getInt(6));
            return new Worker(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(7), rs.getInt(8), rs.getDouble(9), rs.getString(10), manager);
        }
    }

    private Manager getManagerFromResultSet(ResultSet rs) throws SQLException {
        return new Manager (rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(7), rs.getInt(8), rs.getDouble(9), rs.getString(10),rs.getInt(6));
    }

    private Department getDepartmentFromResultSet ( ResultSet rs) throws SQLException {
        return new Department(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
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
