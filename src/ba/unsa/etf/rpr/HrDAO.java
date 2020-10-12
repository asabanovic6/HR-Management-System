package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HrDAO {
    private static HrDAO instance;
    private Connection conn;
    private PreparedStatement getEmployeePS,getEmployeesPS,getDepartmentPS;

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

    private Employee getEmployeeFromResultSet(ResultSet rs) throws SQLException {
        return  new Employee(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(7),rs.getInt(8),rs.getDouble(9),rs.getString(10));
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
