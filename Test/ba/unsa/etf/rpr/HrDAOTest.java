package ba.unsa.etf.rpr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class HrDAOTest {
    private HrDAO dao = HrDAO.getInstance();

    @BeforeEach
    public void resetDataBase() throws SQLException {
        dao.returnBaseOnDefault();
    }

    @Test
   public void regenerateFile() {
        // Testiramo da li će fajl ponovo biti kreiran nakon brisanja
        // Ovaj test može padati na Windowsu zbog lockinga, u tom slučaju pokrenite ovaj test
        // odvojeno od ostalih
       HrDAO.removeInstance();
        File dbfile = new File("baza.db");
        dbfile.delete();
        this.dao = HrDAO.getInstance();
        Employee emp = dao.getEmployee(1);
        assertEquals("Amina Sabanovic", emp.getEmployeeName());
    }
   @Test
    public void test1Date () {
       Employee emp = dao.getEmployee(1);
       LocalDateTime date = LocalDateTime.of(2010, 10 , 05, 00, 00);
       assertEquals(date, emp.getHireDate());
   }

   @Test
   public void testGetManager () {
       Manager man = dao.getManager(1);
       assertEquals("Amina Sabanovic", man.getEmployeeName());
   }
    @Test
    public void testGetManagerExceptionEdition () {
        AtomicReference<Manager> man = null;
      assertThrows(NullPointerException.class,()-> man.set(dao.getManager(2)),"This employee is not manager!" );
    }

   @Test
    public void testGetEmployeesFromDepartment () throws NonExistentDepartment {
       ArrayList<Employee> employees = dao.getEmployeesFromDepartment(1);
       assertEquals("Adnan Tomic ", employees.get(0).getEmployeeName());
       assertEquals("Amina Sabanovic", employees.get(1).getEmployeeName());
       assertEquals("Hana Veladzic", employees.get(2).getEmployeeName());
   }

    @Test
    public void testGetEmployeesFromDepartmentExceptionEdition () {
        AtomicReference<ArrayList<Employee>> emp = null;
        assertThrows(NonExistentDepartment.class,()-> emp.set(dao.getEmployeesFromDepartment(99)),"This department doesn't exist!");
    }

   @Test
    public void testGetManagerFromDepartment () throws NonExistentDepartment {
        Manager manager = dao.getManagerFromDepartment(1);
        assertEquals("Amina Sabanovic", manager.getEmployeeName());
   }

    @Test
    public void testGetManagerFromDepartmentExceptionEdition () {
        AtomicReference<Manager> man = null;
        assertThrows(NonExistentDepartment.class,()-> man.set(dao.getManagerFromDepartment(99)),"This department doesn't exist!");
    }

    @Test
    public void testGetEmployeesFromManager () {
        ArrayList<Employee> employees = dao.getEmployeesFromManager(1);
        assertEquals("Adnan Tomic ", employees.get(0).getEmployeeName());
        assertEquals("Hana Veladzic", employees.get(1).getEmployeeName());
    }

    @Test
    public void testGetDepartments () {
        ArrayList<Department> dep = dao.getDepartments();
        assertEquals("Restoran", dep.get(0).getDepartmentName());
        assertEquals("Kafić", dep.get(1).getDepartmentName());
    }

}
