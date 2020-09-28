package ba.unsa.etf.rpr;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class HrDAO {
    private static HrDAO instance;
    private Connection conn;
    private PreparedStatement getEmployeesPS;

    public static HrDAO getInstance() {
        if (instance==null) instance= new HrDAO();
        return  instance;
    }

    private HrDAO () {
        try {
            conn= DriverManager.getConnection("jdbc:sqlite.baza.db");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            getEmployeesPS = conn.prepareStatement("SELECT * FROM employees");
        }
        catch (SQLException e) {
            e.printStackTrace();
            regenerateDataBase();
            try {
                getEmployeesPS = conn.prepareStatement("select * from  employees");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void regenerateDataBase() {
        Scanner ulaz= null;
        try {
            ulaz = new Scanner(new FileInputStream("baza.db.sql"));

            String sqlUpit = "";
            while (ulaz.hasNext()) {

                sqlUpit += ulaz.nextLine();
                if (sqlUpit.charAt(sqlUpit.length() - 1) == ';') {
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


}
